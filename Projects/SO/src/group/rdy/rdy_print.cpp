/*
 *  \author Bernardo Marujo 107322
 */

#include "somm24.h"

#include <stdio.h>
#include <stdint.h>

namespace group 
{

// ================================================================================== //

    void rdyPrint(FILE *fout)
    {
        soProbe(503, "%s(%p)\n", __func__, fout);

        require(schedulingPolicy == FCFS or schedulingPolicy == SPN, "Module is not in a valid open state!");
        require(rdyList != UNDEF_RDY_NODE and rdyTail != UNDEF_RDY_NODE, "Module is not in a valid open state!");

        RdyNode *node = rdyList;

        fprintf(fout, "+====================+\n");
        fprintf(fout, "|  RDY Module State  |\n");

        switch (schedulingPolicy)
        {
        case FCFS:
            fprintf(fout, "|       (%s)       |\n", "FCFS");
            break;
        case SPN:
            fprintf(fout, "|       (%s)        |\n", "SPN");
            break;
        default:
            throw Exception(EINVAL, "Invalid scheduling policy");
            break;
        }

        fprintf(fout, "+-------+------------+\n");
        fprintf(fout, "|  PID  |  lifetime  |\n");
        fprintf(fout, "+-------+------------+\n");
         

        while (node)
        {
            fprintf(fout, "|  %d |      %5.1f |\n", node->process.pid, node->process.lifetime);

            node = node->next;
        }
        
        fprintf(fout, "+====================+\n");
        
    }

// ================================================================================== //

} // end of namespace group

