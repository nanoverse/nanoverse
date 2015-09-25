UNAME := $(shell uname)
ifeq ($(UNAME), Darwin)
JAVA_HOME=$(shell /usr/libexec/java_home)
INC = -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/darwin \
	-I$(JAVA_HOME)/include/linux -I include
endif
ifeq ($(UNAME), Linux)
JAVA_HOME=$(shell export JAVA_HOME=$(dirname $(dirname $(readlink -e \
		$(which javac)))))
INC = -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/linux -I include
endif

CFLAGS=-Wall $(INC) -std=c99
SRCDIR = src
VPATH = $(SRCDIR)
.SILENT:

# Always rebuild. Not great, but Gradle should detect whether or not the source
# files are out of date
.PHONY: test ../../lib/libpetscsolver.dylib

include ${PETSC_DIR}/lib/petsc/conf/variables
include ${PETSC_DIR}/lib/petsc/conf/rules

## Builds the JNI library and runs tests
test: build/petscArch petsc_solver.o test_petsc_solver.o \
		../../lib/libpetscsolver.dylib chkopts
	mkdir -p bin
	-${CLINKER} -o bin/test_petsc_solver petsc_solver.o \
		test_petsc_solver.o ${PETSC_KSP_LIB}
	@printf "\n"
	./bin/test_petsc_solver
	${RM} petsc_solver.o test_petsc_solver.o

../../lib/libpetscsolver.dylib: petsc_solver.o chkopts
	mkdir -p ../../lib
	-${CLINKER} -dynamiclib -o ../../lib/libpetscsolver.dylib petsc_solver.o \
		${PETSC_KSP_LIB}
	${RM} petsc_solver.o

