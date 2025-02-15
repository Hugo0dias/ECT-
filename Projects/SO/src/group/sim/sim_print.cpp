/*
 *  \author Rúben Costa
 */

#include "somm24.h"

#include <stdio.h>
#include <stdint.h>

namespace group 
{

// ================================================================================== //
    void simPrint(FILE *fout, uint32_t satelliteModules)
    {
        soProbe(103, "%s(\"%p\")\n", __func__, fout);

        require(simTime != UNDEF_TIME and stepCount != UNDEF_COUNT, "Module not in a valid open state!");
        require(submissionTime != UNDEF_TIME and runoutTime != UNDEF_TIME, "Module is not in a valid open state!");
        require(runningProcess != UNDEF_PID, "Module is not in a valid open state!");
        require(fout != NULL and fileno(fout) != -1, "fout must be a valid file stream");

        /* TODO POINT: Replace next instruction with your code */
        //throw Exception(ENOSYS, __func__);

        if (satelliteModules) jdtPrint(fout);
        fprintf(fout, "\n");
        if (satelliteModules) pctPrint(fout);
        fprintf(fout, "\n");
        if (satelliteModules) memPrint(fout);
        fprintf(fout, "\n");
        if (satelliteModules) rdyPrint(fout);
        fprintf(fout, "\n");
        if (satelliteModules) swpPrint(fout);
        fprintf(fout, "\n");

        // ver para runoutTime, submissionTime, runningProcess
        int rt = 0, st = 0, rp = 0;
        if (runoutTime == NEVER)
        {
            rt = 1;
        }
        if (submissionTime == NEVER)
        {
            st = 1;
        }
        if (runningProcess == 0)
        {
            rp = 1;
        }

        fprintf(fout, "+====================================================================================+\n");
        fprintf(fout, "+ -------------------------------- SIM Module State -------------------------------- +\n");
        fprintf(fout, "+====================================================================================+\n");        
        fprintf(fout, "| simulation time |  step count  | running process | next submission |  next runout  |\n");
        fprintf(fout, "+-----------------+--------------+-----------------+-----------------+---------------+\n");
        if (rt && st && rp) {
            fprintf(fout, "| %15.1f | %12d | %15s | %15.5s | %13.5s |\n", 
                    simTime, stepCount, "---", "NEVER", "NEVER");
        } else if (rt && st) {
            fprintf(fout, "| %15.1f | %12d | %15d | %15.5s | %13.5s |\n", 
                    simTime, stepCount, runningProcess, "NEVER", "NEVER");
        } else if (rt && rp) {
            fprintf(fout, "| %15.1f | %12d | %15s | %15.1f | %13.5s |\n", 
                    simTime, stepCount, "---", submissionTime, "NEVER");
        } else if (st && rp) {
            fprintf(fout, "| %15.1f | %12d | %15s | %15.5s | %13.1f |\n", 
                    simTime, stepCount, "---", "NEVER", runoutTime);
        } else if (rt) {
            fprintf(fout, "| %15.1f | %12d | %15d | %15.1f | %13.5s |\n", 
                    simTime, stepCount, runningProcess, submissionTime, "NEVER");
        } else if (st) {
            fprintf(fout, "| %15.1f | %12d | %15d | %15.5s | %13.1f |\n", 
                    simTime, stepCount, runningProcess, "NEVER", runoutTime);
        } else if (rp) {
            fprintf(fout, "| %15.1f | %12d | %15s | %15.1f | %13.1f |\n", 
                    simTime, stepCount, "---", submissionTime, runoutTime);
        } else {
            fprintf(fout, "| %15.1f | %12d | %15d | %15.1f | %13.1f |\n", 
                    simTime, stepCount, runningProcess, submissionTime, runoutTime);
        }
        fprintf(fout, "+====================================================================================+\n");  
    }

// ================================================================================== //

} // end of namespace group

