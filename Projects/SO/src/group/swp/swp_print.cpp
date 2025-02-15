/*
 *  \author Orlando Marinheiro - 114060
 */

#include "somm24.h"

#include <stdio.h>
#include <stdint.h>

#include <string.h>

namespace group 
{

// ================================================================================== //

    void swpPrint(FILE *fout)
    {
        soProbe(603, "%s(%p)\n", __func__, fout);

        require(swappingPolicy == FIFO or swappingPolicy == FirstFit, "Module is not in a valid open state!");
        require(swpList != UNDEF_SWP_NODE and swpTail != UNDEF_SWP_NODE, "Module is not in a valid open state!");
        require(fout != NULL and fileno(fout) != -1, "fout must be a valid file stream");

        /* TODO POINT: Replace next instruction with your code */
        //throw Exception(ENOSYS, __func__);

        fprintf(fout, "+=====================+\n");
        fprintf(fout, "|  SWP Module State   |\n");

        const char *swpPolicy;
        if (swappingPolicy == FIFO) {
            swpPolicy = "FIFO";
        } else {
            swpPolicy = "FirstFit";
        }

        // Centralização do WorstFit/BestFit
        int total_len = 21; 
        int memPolicy_len = strlen(swpPolicy) + 2; 
        int padd_left = (total_len - memPolicy_len) / 2; 
        int padd_right = total_len - memPolicy_len - padd_left; 


        fprintf(fout, "|%*s(%s)%*s|\n",padd_left, "", swpPolicy, padd_right, "");

        fprintf(fout, "+-------+-------------+\n");
        fprintf(fout, "|  PID  | memory size |\n");
        fprintf(fout, "+-------+-------------+\n");


        // print swp list
        SwpNode *swp = swpList; 
        while (swp != NULL)

        {

            char swpProcSize[50];
            snprintf(swpProcSize, sizeof(swpProcSize), "0x%lx", (unsigned long)swp->process.size);

            fprintf(fout, "| %5d | %11s |\n", 
                swp->process.pid, 
                swpProcSize);

            swp = swp->next;
        }
        

        

        fprintf(fout, "+=====================+\n");


    }

// ================================================================================== //

} // end of namespace group

