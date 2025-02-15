/*
 *  \author Bernardo Marujo 107322
 */

#include "somm24.h"

#include <stdio.h>
#include <string.h>

namespace group
{

// ================================================================================== //

    uint16_t jdtLoad(FILE *fin, uint32_t maxSize)
    {
        soProbe(204, "%s(%p, %#x)\n", __func__, fin, maxSize);

        require(jdtIn != UNDEF_JOB_INDEX and jdtOut != UNDEF_JOB_INDEX, "Module is not in a valid open state!");
        require(jdtCount != UNDEF_JOB_COUNT, "Module is not in a valid open state!");
        require(fin != nullptr and fileno(fin) != -1, "fin must be a valid file stream");

        char line[256];
        uint16_t jobsRead = 0;
        double lastSubmissionTime = -1;
        int isBeginJobs = 0;

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
                break;
            }

            //job properties
            char* token[3];
            token[0] = strtok(start, ";");     
            token[1] = strtok(NULL, ";");      
            token[2] = strtok(NULL, ";");    
            
            double submissionTime = -1;
            double lifetime = -1;
            uint32_t memSize = 0;
            char* endptr;
            if (isBeginJobs){
                try {
                    if (token[0] == nullptr) throw Exception(EINVAL,"Missing submission time");
                    submissionTime = strtod(token[0], &endptr);

                    if (token[1] == nullptr) throw Exception(EINVAL,"Missing lifetime");
                    lifetime = strtod(token[1], &endptr);

                    if (token[2] == nullptr) throw Exception(EINVAL,"Missing memory size");
                    memSize = strtol(token[2], &endptr, 16);

                    //validate job properties
                    if (submissionTime < 0 || submissionTime < lastSubmissionTime)
                        throw Exception(EINVAL,"Invalid or out-of-order submission time");
                    if (lifetime <= 0) throw Exception(EINVAL,"Invalid lifetime");
                    if (memSize == 0 || memSize > maxSize)
                        throw Exception(EINVAL, "Invalid memory size");

                    if (jdtCount >= MAX_JOBS) throw Exception(EINVAL,"Job table full");

                    //add job to the table
                    jdtTable[jdtIn] = {submissionTime, lifetime, memSize};
                    jdtIn = (jdtIn + 1) % MAX_JOBS;
                    jdtCount++;
                    jobsRead++;

                    lastSubmissionTime = submissionTime;

                } catch (const Exception &e) {
                    fprintf(stderr, "Error parsing job: %s\n", e.what());
                    errno = EINVAL;
                    continue;
                }
            }
        }

        if (jobsRead == 0) throw Exception(EINVAL,"No valid jobs loaded");
        rewind(fin);
        return jobsRead;
    }

// ================================================================================== //

} // end of namespace group

