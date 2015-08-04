#include "EquilibriumPetscSolver.h"
#include <jni.h>
#include <math.h>
#include <petscksp.h>
#include <pthread.h>
#include <time.h>

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
int _fill_matrix(JNIEnv *env, Mat *A, jintArray *index,
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
int _fill_vector(JNIEnv *env, Vec *b, jdoubleArray *values) {
    PetscErrorCode ierr;

    // Get size and base pointer for array of values
    jsize value_length = (*env) -> GetArrayLength(env, *values);
    PetscScalar *value_array = (*env) -> GetDoubleArrayElements(env, *values, 0);
  
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
 * @param x a pointer to the PETSc vector
 * @param jx a pointer to the JNI double array
 */
int _pvec_to_jarray(JNIEnv *env, Vec *x, jdoubleArray *jx) {
    PetscErrorCode ierr;

    // Get size and base pointer for JNI double array
    jsize jx_length = (*env) -> GetArrayLength(env, *jx);
    jdouble* jx_array = (*env) -> GetDoubleArrayElements(env, *jx, 0);

    // Make array of indices
    int indices[jx_length];
    for (int i = 0; i < jx_length; i++) {
	indices[i] = i;
    }

    // Get values from PETSc
    PetscScalar temp_values[jx_length];
    ierr = VecGetValues(*x, jx_length, indices, temp_values); CHKERRQ(ierr);

    // Copy values into the memory JNI pinned for us
    for (int i = 0; i < jx_length; i++) {
	jx_array[i] = temp_values[i];
    }
    
    (*env) -> ReleaseDoubleArrayElements(env, *jx, jx_array, 0);
    return 0;
}


/**
 * Solves a linear system Ax = b using PETSc
 * 
 * @param n the dimension of the system
 * @param index the diagonal indices of A from CompDiagMatrix.getIndex()
 * @param diagonals the diagonal storage of A from CompDiagMatrix.getDiagonals()
 * @param solution a double array that will be overwritten with the solution
 * @param rhs the vector b (right-hand side of Ax = b)
 * @return 0 on success, PetscErrorCode error code on failute
 */
JNIEXPORT int JNICALL
Java_layers_continuum_solvers_EquilibriumPetscSolver_solve(
        JNIEnv *env, jobject obj, jint n, jintArray index,
	jobjectArray diagonals, jdoubleArray solution, jdoubleArray rhs) {
    
    // Throw a tantrum if PetscScalar and jdouble aren't the same size
    if (sizeof(PetscScalar) != sizeof(jdouble)) {
	fprintf(stderr,
		"Type conversion error. Is PetscScalar double precison?\n");
	exit(1);
    }

    PetscErrorCode ierr;
    KSP ksp;
    PC pc;
    Mat A;
    Vec b;
    Vec x;
    PetscInitialize(0, NULL, (char*) NULL, NULL);
    
    // Set up matrix A in Ax = b
    int num_diags = (*env) -> GetArrayLength(env, index);
    ierr = MatCreateSeqAIJ(PETSC_COMM_WORLD, n, n, num_diags, NULL, &A);
    CHKERRQ(ierr);
    ierr = MatSetUp(A); CHKERRQ(ierr);
    _fill_matrix(env, &A, &index, &diagonals);
    ierr = MatAssemblyBegin(A, MAT_FINAL_ASSEMBLY); CHKERRQ(ierr);
    ierr = MatAssemblyEnd(A, MAT_FINAL_ASSEMBLY); CHKERRQ(ierr);

    // Set up vectors b and x in Ax = b
    ierr = VecCreate(PETSC_COMM_WORLD, &b); CHKERRQ(ierr);
    ierr = VecSetSizes(b, PETSC_DECIDE, n); CHKERRQ(ierr);
    ierr = VecSetType(b, VECSEQ);
    ierr = VecDuplicate(b, &x);
    _fill_vector(env, &b, &rhs);
    ierr = VecAssemblyBegin(b); CHKERRQ(ierr);
    ierr = VecAssemblyEnd(b); CHKERRQ(ierr);
    
    // Set up solver
    ierr = KSPCreate(PETSC_COMM_WORLD, &ksp); CHKERRQ(ierr);
    ierr = KSPSetOperators(ksp, A, A); CHKERRQ(ierr);
    // ierr = KSPMonitorSet(ksp, *KSPMonitorDefault, NULL, NULL);

    // Solver options
    ierr = KSPSetType(ksp, KSPCG); CHKERRQ(ierr);
    ierr = KSPGetPC(ksp, &pc); CHKERRQ(ierr);
    ierr = PCSetType(pc, PCICC); CHKERRQ(ierr);
    
    ierr = KSPSolve(ksp, b, x); CHKERRQ(ierr);

    // Copy solution into JNI allocated memory
    _pvec_to_jarray(env, &x, &solution);
    
    // Clean up and return
    ierr = MatDestroy(&A); CHKERRQ(ierr);
    ierr = VecDestroy(&b); CHKERRQ(ierr);
    ierr = VecDestroy(&x); CHKERRQ(ierr);
    ierr = KSPDestroy(&ksp);
    
    PetscFinalize();
    return 0;
}
