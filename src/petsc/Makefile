CC=icc
INC = -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/darwin \
	-I$(JAVA_HOME)/include/linux -I include
CFLAGS=-Wall $(INC) -std=c99
SRCDIR = src
VPATH = $(SRCDIR)
.SILENT:

include ${PETSC_DIR}/lib/petsc/conf/variables
include ${PETSC_DIR}/lib/petsc/conf/rules

## Builds the JNI library and runs tests
test: petsc_solver.o test_petsc_solver.o lib/libsolver.dylib chkopts
	mkdir -p bin
	-${CLINKER} -o bin/test_petsc_solver petsc_solver.o \
		test_petsc_solver.o ${PETSC_KSP_LIB}
	@printf "\n"
	./bin/test_petsc_solver
	${RM} petsc_solver.o test_petsc_solver.o


# Build the JNI library
lib/libsolver.dylib: petsc_solver.o chkopts
	mkdir -p lib
	-${CLINKER} -dynamiclib -o lib/libsolver.dylib petsc_solver.o \
		${PETSC_KSP_LIB}
