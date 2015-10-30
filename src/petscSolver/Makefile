CFLAGS=-Wall $(INC) -std=c99
SRCDIR = src
VPATH = $(SRCDIR)
.SILENT:

# Always rebuild. Not great, but Gradle should detect whether or not the source
# files are out of date
.PHONY: test ../../lib/libpetscsolver.dylib

include ${PETSC_DIR}/lib/petsc/conf/variables
include ${PETSC_DIR}/lib/petsc/conf/rules

# Define platform-specific stuff, not worth using autoconf yet...
UNAME := $(shell uname)
ifeq ($(UNAME), Darwin)
JAVA_HOME=$(shell /usr/libexec/java_home)
INC = -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/darwin \
	-I$(JAVA_HOME)/include/linux -I include
LIB_COMPILE = -${CLINKER} -dynamiclib
LIB_SUFFIX = dylib
endif

ifeq ($(UNAME), Linux)
INC = -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/linux -I include
CFLAGS += -fpic
LIB_COMPILE = -${CLINKER} -shared
LIB_SUFFIX = so
endif

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
	${LIB_COMPILE} -o ../../lib/libpetscsolver.${LIB_SUFFIX} petsc_solver.o \
		${PETSC_KSP_LIB}
	${RM} petsc_solver.o

