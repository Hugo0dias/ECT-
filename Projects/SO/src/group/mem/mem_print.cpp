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

    void memPrint(FILE *fout)
    {
        soProbe(403, "%s(\"%p\")\n", __func__, fout);

        require(memAllocationPolicy != UndefMemoryAllocationPolicy, "Module is not in a valid open state!");
        require(memFreeList != UNDEF_MEM_NODE and memOccupiedList != UNDEF_MEM_NODE, "Module is not in a valid open state!");
        require(fout != NULL and fileno(fout) != -1, "fout must be a valid file stream");

        /* TODO POINT: Replace next instruction with my code */
        //throw Exception(ENOSYS, __func__); VER ERRO   

        const char *memPolicy;
        if (memAllocationPolicy == BestFit) {
            memPolicy = "BestFit";
        } else if (memAllocationPolicy == WorstFit) {
            memPolicy = "WorstFit";
        }

        fprintf(fout, "+===============================+\n");
        fprintf(fout, "|       MEM module state        |\n");

        // Centralização do WorstFit/BestFit
        int total_len = 31; 
        int memPolicy_len = strlen(memPolicy) + 2; 
        int padd_left = (total_len - memPolicy_len) / 2; 
        int padd_right = total_len - memPolicy_len - padd_left; 


        fprintf(fout, "|%*s(%s)%*s|\n",padd_left, "", memPolicy, padd_right, ""); 


        fprintf(fout, "+-------------------------------+\n");
        fprintf(fout, "|         occupied list         |\n");
        fprintf(fout, "+---------------+---------------+\n");
        fprintf(fout, "|  start frame  |     size      |\n");
        fprintf(fout, "+---------------+---------------+\n");

        // print occupied list
        MemNode *occup = memOccupiedList;
        while(occup != NULL)
        {   
            char OccupStartBuff[50];
            snprintf(OccupStartBuff, sizeof(OccupStartBuff), "0x%lx", (unsigned long)occup->block.start);

            char OccupSizeBuff[50];
            snprintf(OccupSizeBuff, sizeof(OccupSizeBuff), "0x%lx", (unsigned long)occup->block.size);

            fprintf(fout, "| %13s | %13s |\n", 
                OccupStartBuff, 
                OccupSizeBuff);
            occup = occup->next;
        }

        fprintf(fout, "+---------------+---------------+\n");
        fprintf(fout, "|            free list          |\n");
        fprintf(fout, "+---------------+---------------+\n");
        fprintf(fout, "|  start frame  |     size      |\n");
        fprintf(fout, "+---------------+---------------+\n");

        // print free list
        MemNode *free = memFreeList;
        while(free != NULL)
        {   
            char memStartBuff[50];
            snprintf(memStartBuff, sizeof(memStartBuff), "0x%lx", (unsigned long)free->block.start);

            char memSizeBuff[50];
            snprintf(memSizeBuff, sizeof(memSizeBuff), "0x%lx", (unsigned long)free->block.size);

            fprintf(fout, "| %13s | %13s |\n", 
                memStartBuff, 
                memSizeBuff);
            free = free->next;
        }

        fprintf(fout, "+===============================+\n");



    }

// ================================================================================== //

} // end of namespace group

