#include <assert.h>
#include <jni.h>
#include <petscmat.h>
#include "petsc_solver.h"
#include <stdio.h>

#define run_test(fn_name)\
    printf("%s\n", #fn_name);\
    fn_name();

void test__get_length() {
    assert(2 == _get_length(4, 2));   // above diagonal
    assert(4 == _get_length(4, 0));   // on diagonal
    assert(3 == _get_length(4, -1));  // below diagonal
}

void test__get_row() {
    assert(2 == _get_row(1, 2));   // above diagonal
    assert(1 == _get_row(0, 1));   // on diagonal
    assert(2 == _get_row(-1, 1));  // below digaonal
}

void test__get_col() {
    assert(2 == _get_col(1, 1));   // above diagonal
    assert(1 == _get_col(0, 1));   // on diagonal
    assert(2 == _get_col(-1, 2));  // below diagonal
}

int main(int argc, char* argv[]) {
    run_test(test__get_row);
    run_test(test__get_col);
    run_test(test__get_length);

    // Note: We can't automatically test _fill_matrix, _fill_vector,
    // or _pvec_to_jarray because we cannot mock JNI
    
    printf("\n----- OK -----\n");
}
