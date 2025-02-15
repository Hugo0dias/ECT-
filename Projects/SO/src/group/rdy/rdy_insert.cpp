/*
 *  \author Bernardo Marujo 107322 
 */

#include "somm24.h"

namespace group
{

// ================================================================================== //

    void rdyInsert(uint16_t pid, double lifetime)
    {
        soProbe(504, "%s(%hu, %.1f)\n", __func__, pid, lifetime);

        require(schedulingPolicy == FCFS or schedulingPolicy == SPN, "Module is not in a valid open state!");
        require(rdyList != UNDEF_RDY_NODE and rdyTail != UNDEF_RDY_NODE, "Module is not in a valid open state!");
        require(pid != 0, "a valid process ID must be greater than zero");
        require(lifetime > 0, "a valid process lifetime must be greater than zero");


        RdyNode *newnode = (RdyNode *)malloc(sizeof(RdyNode));
        RdyProcessData newprocess;
        newprocess.pid = pid;
        newprocess.lifetime = lifetime;
        newnode->process = newprocess;
        newnode->next = nullptr;

        switch (schedulingPolicy)
        {
        case FCFS:
            if (rdyList == nullptr)
            {
                rdyList = newnode;
                rdyTail = newnode;
            }
            else
            {
                rdyTail->next = newnode;
                rdyTail = newnode;
            }
            break;

        case SPN:
            if (rdyList == nullptr)
            {
                rdyList = newnode;
                rdyTail = newnode;
            }

            else
            {
                RdyNode *current = rdyList;
                RdyNode *previous = nullptr;

                while (current != nullptr && 
                    (current->process.lifetime < lifetime || 
                    (current->process.lifetime == lifetime && previous != nullptr)))
                {
                    previous = current;
                    current = current->next;
                }

                if (previous == nullptr)
                {
                    newnode->next = rdyList;
                    rdyList = newnode;
                }
                else
                {
                    previous->next = newnode;
                    newnode->next = current;
                }

                if (current == nullptr)
                {
                    rdyTail = newnode;
                }
            }
            break;

        default:
            throw Exception(EINVAL, "Invalid scheduling policy");
            break;
        }

        
    }

// ================================================================================== //

} // end of namespace group

