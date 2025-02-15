/*
 *  \author Bernardo Marujo 107322 
 */

#include "somm24.h"

namespace group
{

// ================================================================================== //

    void rdyTerm()
    {
        soProbe(502, "%s()\n", __func__);

        require(schedulingPolicy == FCFS or schedulingPolicy == SPN, "Module is not in a valid open state!");
        require(rdyList != UNDEF_RDY_NODE and rdyTail != UNDEF_RDY_NODE, "Module is not in a valid open state!");
        RdyNode *current = rdyList;
        while (current != nullptr)
        {
            RdyNode *next = current->next;
            free(current);
            current = next;
        }

        rdyList = UNDEF_RDY_NODE;
        rdyTail = UNDEF_RDY_NODE;
        schedulingPolicy = static_cast<SchedulingPolicy>(-1);
    }

// ================================================================================== //

} // end of namespace group

