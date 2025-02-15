/*
 *  \authors Bernardo Marujo 107322  contribuição: 50%
             Lázaro Sá 115884        contribuição: 50%
 */

#include "somm24.h"

#include <stdio.h>
#include <unistd.h>
#include <time.h>

namespace group
{

// ================================================================================== //

    void simInit(FILE *fin)
    {
        soProbe(101, "%s(%p)\n", __func__, fin);

        require(simTime == UNDEF_TIME and stepCount == UNDEF_COUNT, "Module is not in a valid closed state!");
        require(submissionTime == UNDEF_TIME and runoutTime == UNDEF_TIME, "Module is not in a valid closed state!");
        require(runningProcess == UNDEF_PID, "Module is not in a valid closed state!");
        require(fin == nullptr or fileno(fin) != -1, "fin must be NULL or a valid file pointer");

        SimParameters *simP = (SimParameters *)malloc(sizeof(SimParameters));
        simP->jobMaxSize = 0x10000;
        simP->jobRandomSeed = UNDEF_SEED;
        simP->jobCount = 0;
        simP->pidStart = 1001;
        simP->pidRandomSeed = UNDEF_SEED;
        simP->memorySize = 0x100000;
        simP->memoryKernelSize = 0x10000;
        simP->memoryAllocPolicy = WorstFit;
        simP->schedulingPolicy = FCFS;
        simP->swappingPolicy = FIFO;
        simP->jobLoadStream = nullptr;

        if (fin) {
            simConfig(fin, simP);
        }

        if (simP->jobRandomSeed == UNDEF_SEED) {
            simP->jobRandomSeed = getpid();
        }

        if (simP->pidRandomSeed == UNDEF_SEED) {
            simP->pidRandomSeed = time(NULL);
        }

        jdtInit();
        memInit(simP->memorySize, simP->memoryKernelSize, simP->memoryAllocPolicy);

        uint16_t count = 0;
        if (simP->jobLoadStream == nullptr) {
            count = jdtRandomFill(simP->jobRandomSeed, simP->jobCount, simP->jobMaxSize);
        } else {
            count = jdtLoad(fin, simP->jobMaxSize);
        }


        simP->jobCount = count;

        pctInit(simP->pidStart, simP->jobCount, simP->pidRandomSeed);
        rdyInit(simP->schedulingPolicy);
        swpInit(simP->swappingPolicy);
        stepCount = 0;
        simTime = 0.0;
        submissionTime = jdtTable[jdtOut].submissionTime;
        runoutTime = NEVER;
        runningProcess = 0;

    }
// ================================================================================== //

} // end of namespace group
