/*
 *  \author Rúben Costa
 */

#include "somm24.h"

#include <stdio.h>
#include <stdint.h>

namespace group 
{

// ================================================================================== //

    void jdtPrint(FILE *fout)
    {
        soProbe(203, "%s(%p)\n", __func__, fout);

        require(jdtIn != UNDEF_JOB_INDEX and jdtOut != UNDEF_JOB_INDEX, "Module is not in a valid open state!");
        require(jdtCount != UNDEF_JOB_COUNT, "Module is not in a valid open state!");
        require(fout != NULL and fileno(fout) != -1, "fout must be a valid file stream");

        /* TODO POINT: Replace next instruction with your code */
        //throw Exception(ENOSYS, __func__);
        

        fprintf(fout, "+=====================================+\n");
        fprintf(fout, "|          JDT module state           |\n");
        fprintf(fout, "+------------+------------+-----------+\n");
        fprintf(fout, "| submission |  lifetime  |  memory   |\n");
        fprintf(fout, "|    time    |            | requested |\n");
        fprintf(fout, "+------------+------------+-----------+\n");

        // Iterate over the jobs in the circular queue
        for (int i = 0, idx = jdtOut; i < jdtCount; i++, idx = (idx + 1) % MAX_JOBS)
        {
            // Format submission time
            char submissionTimeBuff[50];
            snprintf(submissionTimeBuff, sizeof(submissionTimeBuff), "%.1f", jdtTable[idx].submissionTime);

            // Format lifetime
            char lifetimeBuff[50];
            snprintf(lifetimeBuff, sizeof(lifetimeBuff), "%.1f", jdtTable[idx].lifetime);

            // Format memory size
            char memoryBuff[50];
            snprintf(memoryBuff, sizeof(memoryBuff), "0x%lx", (unsigned long)jdtTable[idx].memSize);

            // Print row
            fprintf(fout, "| %10s | %10s | %9s |\n",
                    submissionTimeBuff,
                    lifetimeBuff,
                    memoryBuff);
        }

        fprintf(fout, "+=====================================+\n");
    }

// ================================================================================== //

} // end of namespace group

