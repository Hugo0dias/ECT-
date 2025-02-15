/*
 *  \author Orlando Marinheiro - 114060
 */

#include "somm24.h"

#include <stdio.h>

#include <string.h>

namespace group
{

// ================================================================================== //

    void simConfig(FILE *fin, SimParameters *spp)
    {
        soProbe(104, "%s(\"%p\")\n", __func__, fin);

        require(simTime == UNDEF_TIME and stepCount == UNDEF_COUNT, "Module is not in a valid closed state!");
        require(submissionTime == UNDEF_TIME and runoutTime == UNDEF_TIME, "Module is not in a valid closed state!");
        require(runningProcess == UNDEF_PID, "Module is not in a valid closed state!");
        require(fin != nullptr and fileno(fin) != -1, "fin must be a valid file stream");
        require(spp != nullptr, "spp must be a valid pointer");

        char line[256];
        int isBeginJobs = 0;
        int hasjobs = 0;



        while (fgets(line, sizeof(line), fin) != nullptr) {
            //whitespace
            char *start = line;
            while (*start == ' ' || *start == '\t') start++;
            char *end = start + strlen(start) - 1;
            while (end > start && (*end == ' ' || *end == '\t' || *end == '\n')) end--;
            *(end + 1) = '\0';

            //skip comments and empty lines
            if (*start == '\0' || *start == '#') continue;

            //check if it is the beginning of the jobs
            if (strcmp(start, "Begin Jobs") == 0) {
                isBeginJobs = 1;
                continue;
            } else if (strcmp(start, "End Jobs") == 0) {
                isBeginJobs = 0;
                hasjobs = 1;
                continue;
            }

            if (isBeginJobs){
                continue;
            }

            //parse the line

            char *equalSign = strchr(start, '=');
            if (equalSign == nullptr) {
                continue;
            }

            // Extract key and value
            *equalSign = '\0'; // Split the string
            char *key = start;
            char *value = equalSign + 1;

            while (*key == ' ' || *key == '\t') key++;
            while (*value == ' ' || *value == '\t') value++;
            end = key + strlen(key) - 1;
            while (end > key && (*end == ' ' || *end == '\t')) end--;
            *(end + 1) = '\0';
            end = value + strlen(value) - 1;
            while (end > value && (*end == ' ' || *end == '\t')) end--;
            *(end + 1) = '\0';


            if (strcmp(key, "PIDStart") == 0) {
                spp->pidStart = (value[0] == '0' && value[1] == 'x') ? strtoul(value, nullptr, 16) : strtoul(value, nullptr, 10);
            } else if (strcmp(key, "PIDRandomSeed") == 0) {
                spp->pidRandomSeed = (value[0] == '0' && value[1] == 'x') ? strtoul(value, nullptr, 16) : strtoul(value, nullptr, 10);
            } else if (strcmp(key, "MemorySize") == 0) {
                spp->memorySize = (value[0] == '0' && value[1] == 'x') ? strtoul(value, nullptr, 16) : strtoul(value, nullptr, 10);
            } else if (strcmp(key, "MemoryKernelSize") == 0) {
                spp->memoryKernelSize = (value[0] == '0' && value[1] == 'x') ? strtoul(value, nullptr, 16) : strtoul(value, nullptr, 10);
            } else if (strcmp(key, "MemoryAllocationPolicy") == 0) {
                spp->memoryAllocPolicy = strcmp(value, "WorstFit") == 0 ? WorstFit : BestFit;
            } else if (strcmp(key, "SchedulingPolicy") == 0) {
                spp->schedulingPolicy = strcmp(value, "FCFS") == 0 ? FCFS : SPN;
            } else if (strcmp(key, "SwappingPolicy") == 0) {
                spp->swappingPolicy = strcmp(value, "FIFO") == 0 ? FIFO : FirstFit;
            } else if (strcmp(key, "JobMaxSize") == 0) {
                spp->jobMaxSize = (value[0] == '0' && value[1] == 'x') ? strtoul(value, nullptr, 16) : strtoul(value, nullptr, 10);
            } else if (strcmp(key, "JobCount") == 0) {
                spp->jobCount = (value[0] == '0' && value[1] == 'x') ? strtoul(value, nullptr, 16) : strtoul(value, nullptr, 10);
            } else if (strcmp(key, "JobRandomSeed") == 0) {
                spp->jobRandomSeed = (value[0] == '0' && value[1] == 'x') ? strtoul(value, nullptr, 16) : strtoul(value, nullptr, 10);
            } else {
                fprintf(stderr, "Warning: Unknown parameter: %s\n", key);
            }

        }


        if (hasjobs) {
            spp->jobLoadStream = fin;
        }

        rewind(fin);

    }

// ================================================================================== //

} // end of namespace group

