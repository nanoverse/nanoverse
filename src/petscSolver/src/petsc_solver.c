#include "nanoverse_runtime_layers_continuum_solvers_EquilibriumPetscSolver.h"
#include <jni.h>
#include <petscksp.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <time.h>
#include <unistd.h>

/**
 * Gets the length of a diagonal in a square matrix

 * @param n the 1-based size of the matrix
 * @param diagonal the diagonal number
 * @return the length of the diagonal
 */
int _get_length(int n, int diagonal) {
    return n - abs(diagonal);
}

/**
 * Gets the row index of an element in MTJ's CompDiagMatrix internal storage
 *
 * @param diagonal the diagonal number from CompDiagMatrix.getIndex()
 * @param index the index of the element in the diagonal array from
 *        CompDiagMatrix.getDiagonals()
 */
int _get_row(int diagonal, int index) {
    if (diagonal > 0) {
        return index;
    } else {
        return index - diagonal;
    }
}

/**
 * Gets the column index of an element in MTJ's CompDiagMatrix internal storage
 *
 * @param diagonal the diagonal number from CompDiagMatrix.getIndex()
 * @param index the index of the element in the diagonal array from
 *        CompDiagMatrix.getDiagonals()
 */
int _get_col(int diagonal, int index) {
    if (diagonal > 0) {
        return index + diagonal;
    } else {
        return index;
    }
}

/**
 * Fills in a square PETSc matrix from MTJ's CompDiagMatrix internal storage
 * 
 * @param env a pointer to the JNI environment
 * @param A a pointer to the the PETSc matrix to fill
 * @param index a pointer to the indices from CompDiagMatrix.getIndex()
 * @param diagonals a pointer to the diagonals from 
 *        CompDiagMatrix.getDiagonals()
 * @return 0 on success, PetscErrorCode on failure
 */
PetscErrorCode _fill_matrix(JNIEnv *env, Mat *A, jintArray *index,
		 jobjectArray *diagonals) {
    // Get matrix dimension
    PetscInt n;
    PetscErrorCode ierr;
    ierr = MatGetSize(*A, &n, NULL); CHKERRQ(ierr);

    // Get size and base pointer for array of diagonal indices
    jsize index_length = (*env) -> GetArrayLength(env, *index);
    jint *index_array = (*env) -> GetIntArrayElements(env, *index, 0);

    // Loop through the array of diagonals
    for (int i = 0; i < index_length; i++) {
        // Get the current diagonal, its size and its base pointer
        jdoubleArray diag = (jdoubleArray) (*env) -> GetObjectArrayElement(
            env, *diagonals, i);
        jsize diag_length = (*env) -> GetArrayLength(env, diag);
        jdouble *diag_array = (*env) -> GetDoubleArrayElements(env, diag, 0);

        // Loop through current diagonal
        for (int j = 0; j < diag_length; j++) {
            int row = _get_row(index_array[i], j);
            int col = _get_col(index_array[i], j);
            ierr = MatSetValue(*A, row, col, diag_array[j], INSERT_VALUES);
            CHKERRQ(ierr);
        }

        (*env) -> ReleaseDoubleArrayElements(env, diag, diag_array, 0);
    }
    
    (*env) -> ReleaseIntArrayElements(env, *index, index_array, 0);
    return 0;
}

/**
 * Fills in a PETSc vector from a JNI double array. They must be the same size.
 * 
 * @param env a pointer to the JNI environment
 * @param b a pointer to the the PETSc vector to fill
 * @param values a pointer to the JNI double array to use
 * @return 0 on success, PetscErrorCode on failure
 */
PetscErrorCode _fill_vector(JNIEnv *env, Vec *b, jdoubleArray *values) {
    PetscErrorCode ierr;

    // Get size and base pointer for array of values
    jsize value_length = (*env) -> GetArrayLength(env, *values);
    PetscScalar *value_array = (*env) -> GetDoubleArrayElements(env,
        *values, 0);
  
    // Make array of indices
    int indices[value_length];
    for (int i = 0; i < value_length; i++) {
       	indices[i] = i;
    }

    // Fill with values
    ierr = VecSetValues(*b, value_length, indices, value_array, INSERT_VALUES);
    CHKERRQ(ierr);
    
    (*env) -> ReleaseDoubleArrayElements(env, *values, value_array, 0);
    return 0;
}

/**
 * Copies a PETSc vector into a JNI double array. They must be the same size
 * 
 * @param env a pointer to the JNI environment
 * @param x a pointer to the normal double array (same length as jx)
 * @param jx a pointer to the JNI double array
 */
void _array_to_jarray(JNIEnv *env, double *x, jdoubleArray *jx) {

    // Get size and base pointer for JNI double array
    jsize jx_length = (*env) -> GetArrayLength(env, *jx);
    jdouble* jx_array = (*env) -> GetDoubleArrayElements(env, *jx, 0);

    // Copy values into the memory JNI pinned for us
    for (int i = 0; i < jx_length; i++) {
        jx_array[i] = x[i];
    }
    
    (*env) -> ReleaseDoubleArrayElements(env, *jx, jx_array, 0);
}

/**
 * Copies a PETSc vector into an array of doubles. They must be the same size
 *
 * @param vec the PETSc vector
 * @param array a pointer to the double array
 */
PetscErrorCode _pvec_to_array(Vec *vec, double *array) {
    PetscErrorCode ierr;
    PetscInt size;
    ierr = VecGetSize(*vec, &size); CHKERRQ(ierr);
	
    // Make array of indices
    int indices[size];
    for (int i = 0; i < size; i++) {
        indices[i] = i;
    }

    // Get values from PETSc
    ierr = VecGetValues(*vec, size, indices, array); CHKERRQ(ierr);

    return 0;
}


/**
 * Routine for solving a linear equation with PETSc.
 *
 * Note that this routine calls MPI teardown routines, so it should
 * not be called in the main process unless you want to break MPI for
 * the remainder of the process lifetime.
 * 
 */
PetscErrorCode _solve_routine(JNIEnv *env, jint n, jintArray *index,
			     jobjectArray *diagonals, jobjectArray *options,
			     double *solution, jdoubleArray *rhs) {
    PetscErrorCode ierr;
    KSP ksp;
    PC pc;
    Mat A;
    Vec b;
    Vec x;
    PetscInitialize(0, NULL, (char*) NULL, NULL);
    
    // Set up matrix A in Ax = b
    int num_diags = (*env) -> GetArrayLength(env, *index);
    ierr = MatCreateSeqAIJ(PETSC_COMM_WORLD, n, n, num_diags, NULL,
        &A); CHKERRQ(ierr);
    ierr = MatSetUp(A); CHKERRQ(ierr);
    ierr = _fill_matrix(env, &A, index, diagonals);
    CHKERRQ(ierr);
    ierr = MatAssemblyBegin(A, MAT_FINAL_ASSEMBLY); CHKERRQ(ierr);
    ierr = MatAssemblyEnd(A, MAT_FINAL_ASSEMBLY); CHKERRQ(ierr);

    // Set up vectors b and x in Ax = b
    ierr = VecCreate(PETSC_COMM_WORLD, &b); CHKERRQ(ierr);
    ierr = VecSetSizes(b, PETSC_DECIDE, n); CHKERRQ(ierr);
    ierr = VecSetType(b, VECSEQ);
    ierr = VecDuplicate(b, &x);
    ierr = _fill_vector(env, &b, rhs); CHKERRQ(ierr);
    ierr = VecAssemblyBegin(b); CHKERRQ(ierr);
    ierr = VecAssemblyEnd(b); CHKERRQ(ierr);
    
    // Set up solver
    ierr = KSPCreate(PETSC_COMM_WORLD, &ksp); CHKERRQ(ierr);
    ierr = KSPSetOperators(ksp, A, A); CHKERRQ(ierr);

    // Solver options
    ierr = KSPGetPC(ksp, &pc); CHKERRQ(ierr);
    for(int i = 0; i < (*env)->GetArrayLength(env, *options); i++) {
        jobject option = (*env)->GetObjectArrayElement(env, *options, i);
        jstring key = (*env)->GetObjectArrayElement(env, option, 0);
        jstring value = (*env)->GetObjectArrayElement(env, option, 1);
        const jchar *keyChars = (*env)->GetStringChars(env, key, NULL);
        const jchar *valChars = (*env)->GetStringChars(env, value, NULL);
        PetscOptionsSetValue((char*) keyChars, (char*) valChars);
        (*env)->ReleaseStringChars(env, key, keyChars);
        (*env)->ReleaseStringChars(env, value, valChars);
    }
    ierr = KSPSetFromOptions(ksp); CHKERRQ(ierr);
    ierr = KSPSetUp(ksp); CHKERRQ(ierr);

    // Solve
    ierr = KSPSolve(ksp, b, x); CHKERRQ(ierr);

    // Copy solution into JNI allocated memory
    ierr = _pvec_to_array(&x, solution); CHKERRQ(ierr);
    
    // Clean up and return
    ierr = MatDestroy(&A); CHKERRQ(ierr);
    ierr = VecDestroy(&b); CHKERRQ(ierr);
    ierr = VecDestroy(&x); CHKERRQ(ierr);
    ierr = KSPDestroy(&ksp);
    
    PetscFinalize();
    return ierr;
}

/**
 * Solves a linear system Ax = b using PETSc
 * 
 * @param n the dimension of the system
 * @param index the diagonal indices of A from CompDiagMatrix.getIndex()
 * @param diagonals the diagonal storage of A from CompDiagMatrix.getDiagonals()
 * @param options a 2D key-value array of strings that contain the PETSc solver
 *   options passed to PetscOptionsSetValue()
 * @param solution a double array that will be overwritten with the solution
 * @param rhs the vector b (right-hand side of Ax = b)
 * @return 0 on success, PetscErrorCode error code on failute
 */
JNIEXPORT jint JNICALL Java_nanoverse_runtime_layers_continuum_solvers_EquilibriumPetscSolver_solve(
        JNIEnv *env, jobject obj, jint n, jintArray index,
	    jobjectArray diagonals, jobjectArray options, jdoubleArray solution,
	    jdoubleArray rhs) {

    // Throw a tantrum if PetscScalar and jdouble aren't the same size
    if (sizeof(PetscScalar) != sizeof(jdouble)) {
        fprintf(stderr,
            "PETSc: type conversion error. Is PetscScalar double precison?\n");
        exit(1);
    }

    /* 
     * We need to solve our system in a new process so that we can
     * tear down PETSc without blocking all subsequent MPI calls. So
     * we will create a shared memory space to hold the solution so that
     * the parent process can access it
     */

    int memid;
    memid = shmget(IPC_PRIVATE, sizeof(double) * n, 0666);
    double *solution_array = shmat(memid, NULL, 0);

    // Make new process
    pid_t child;
    child = fork();

    if (child == 0) {  // Fork succeeded
        // Solve in child process
        PetscErrorCode ierr;
        ierr = _solve_routine(env, n, &index, &diagonals, &options,
            solution_array, &rhs);
        exit(ierr);
    } else if (child < 0) { // Fork failed
        fprintf(stderr, "PETSc: process fork failed");
        return 1;
    } else {  // Parent process
        // Wait for child process to solve the system
        int status;
        waitpid(child, &status, 0);

        // Transfer the solution from shared memory to the Java heap
        _array_to_jarray(env, solution_array, &solution);

        // Clean up and return
        shmctl(memid, IPC_RMID, NULL);
        shmdt(solution_array);
        return status;
    }
}
