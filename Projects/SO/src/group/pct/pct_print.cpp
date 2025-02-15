/*
 *  \author Orlando Marinheiro - 114060
 */

#include "somm24.h"
#include <stdio.h>
#include <stdint.h>

namespace group 
{

// ================================================================================== //

    void pctPrint(FILE *fout)
    {
        soProbe(303, "%s(%p)\n", __func__, fout);

        require(pctList != UNDEF_PCT_NODE, "Module is not in a valid open state!");
        require(fout != NULL and fileno(fout) != -1, "fout must be a valid file stream");
        

        /*if (pctList == UNDEF_PCT_NODE || fout == NULL || fileno(fout) == -1) {
            throw Exception(ENOSYS, __func__); 
        } VER ERRO*/
        


        fprintf(fout, "+======================================================================================================================+\n");
        fprintf(fout, "|                                                   PCT module state                                                   |\n");
        fprintf(fout, "+-------+-------------+-------------+-------------+------------+------------+-------------+--------------+-------------+\n");
        fprintf(fout, "|  PID  |    state    |  admission  |  lifetime   | store time | start time | finish time | memory start | memory size |\n");
        fprintf(fout, "+-------+-------------+-------------+-------------+------------+------------+-------------+--------------+-------------+\n");

        PctNode *current = pctList;
        while (current != NULL)
        {

            const char* stateStr;
            if (current->pcb.state == NEW) {
                stateStr = "NEW";
            } else if (current->pcb.state == RUNNING) {
                stateStr = "RUNNING";
            } else if (current->pcb.state == READY) {
                stateStr = "READY";
            } else if (current->pcb.state == SWAPPED) {
                stateStr = "SWAPPED";
            } else {
                stateStr = "TERMINATED";
            }

            const char* finishTimeStr;
            if (current->pcb.finishTime == UNDEF_TIME) { 
                finishTimeStr = "UNDEF";
            } else {
                char str[50]; 
                snprintf(str, sizeof(str), "%.1f", current->pcb.finishTime);
                finishTimeStr = str;  
            }


            const char* startTimeStr;
            if (current->pcb.startTime == UNDEF_TIME) { 
                startTimeStr = "UNDEF";
            } else {
                char str[50]; 
                snprintf(str, sizeof(str), "%.1f", current->pcb.startTime);
                startTimeStr = str;  
            }


            const char* storeTimeStr;
            if (current->pcb.storeTime == UNDEF_TIME) { 
                storeTimeStr = "UNDEF";
            } else {
                char str[50]; 
                snprintf(str, sizeof(str), "%.1f", current->pcb.storeTime);
                storeTimeStr = str;  
            }



            const char* memStartStr;
            char memStartBuff[50];
            if (current->pcb.memStart == UNDEF_ADDRESS) { 
                memStartStr = "UNDEF";
            } else {
                snprintf(memStartBuff, sizeof(memStartBuff), "0x%lx", (unsigned long)current->pcb.memStart);
                memStartStr = memStartBuff;  
            }

            const char* memSizeStr;
            char memSizeBuff[50];
            snprintf(memSizeBuff, sizeof(memSizeBuff), "0x%lx", (unsigned long)current->pcb.memSize);
            memSizeStr = memSizeBuff;



            fprintf(fout, "| %5d | %-11s | %11.1f | %11.1f | %10s | %10s | %11s | %12s | %11s |\n",
                    current->pcb.pid,
                    stateStr,
                    current->pcb.admissionTime,
                    current->pcb.lifetime,
                    storeTimeStr,
                    startTimeStr,
                    finishTimeStr,
                    memStartStr,
                    memSizeStr
            );  

            current = current->next;
        }

        fprintf(fout, "+======================================================================================================================+\n");
    }

// ================================================================================== //

} // end of namespace group

