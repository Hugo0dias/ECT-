cmake_minimum_required(VERSION 3.26)
project(P3res C)

set(CMAKE_C_STANDARD 11)

include_directories(.)

add_executable(P3res
        elapsed_time.h
        examples.c
        functions_iterations_count.c
        functions_timing.c)
