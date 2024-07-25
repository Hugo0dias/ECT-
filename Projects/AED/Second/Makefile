# To compile all programs, run:
#   make
#
# AED, ua, 2023

CC = gcc
CFLAGS += -g -Wall -Wextra
CPPFLAGS += -MMD

TARGETS = example1 example2 example3

all: $(TARGETS)

example1: example1.o Graph.o SortedList.o

example2: example2.o Graph.o GraphTopologicalSorting.o \
 IntegersQueue.o SortedList.o

example3: example3.o Graph.o GraphTopologicalSorting.o \
 IntegersQueue.o SortedList.o instrumentation.o

TestGraphFile: TestGraphFile.o Graph.o SortedList.o

TestRemove: TestRemove.o Graph.o SortedList.o

TestGraphCopy: TestGraphCopy.o Graph.o SortedList.o

TestGraphCheckInvariants: TestGraphCheckInvariants.o Graph.o SortedList.o

TestV1: TestV1.o Graph.o GraphTopologicalSorting.o \
 IntegersQueue.o SortedList.o instrumentation.o

TestV2: TestV2.o Graph.o GraphTopologicalSorting.o \
 IntegersQueue.o SortedList.o instrumentation.o

TestV3: TestV3.o Graph.o GraphTopologicalSorting.o \
 IntegersQueue.o SortedList.o instrumentation.o

 TestGetSequence: TestGetSequence.o Graph.o GraphTopologicalSorting.o \
 IntegersQueue.o SortedList.o instrumentation.o
# Include dependencies (generated with gcc -MMD)
-include *.d


# Cleanup
clean:
	rm -f *.o *.d
	rm -f $(TARGETS)

