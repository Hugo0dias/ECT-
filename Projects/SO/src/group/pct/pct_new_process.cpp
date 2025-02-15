/*
 *  \author Hugo Dias 114142 - 304
 */

#include "somm24.h"
#include <cstdlib>
#include <cstring> 
#include <stdexcept> 

namespace group 
{

// ================================================================================== //

    uint16_t pctNewProcess(double admissionTime, double lifetime, uint32_t memSize)
    {
        soProbe(304, "%s(%0.1f, %0.1f, %#x)\n", __func__, admissionTime, lifetime, memSize);

        require(pctList != UNDEF_PCT_NODE, "Module is not in a valid open state!");
        require(admissionTime >= 0, "Bad admission time");
        require(lifetime > 0, "Bad lifetime");
        require(memSize > 0, "Bad memory size");

        static uint16_t pidIndex = 0;

        if (pctList == nullptr)
        {
            pidIndex = 0;
        }

        if (pidIndex >= MAX_JOBS || pctPID[pidIndex] == 0)
        {
            throw Exception(ENOSYS, "No more PIDs available");
        }
        uint16_t newPID = pctPID[pidIndex++]; 


        PctNode* newNode = (PctNode*)malloc(sizeof(PctNode));
        if (!newNode)
        {
            throw Exception(ENOMEM, "Failed to allocate memory for new process node");
        }

        memset(newNode, 0, sizeof(PctNode)); 
        newNode->pcb.pid = newPID;
        newNode->pcb.state = NEW;
        newNode->pcb.admissionTime = admissionTime;
        newNode->pcb.lifetime = lifetime;
        newNode->pcb.storeTime = UNDEF_TIME;
        newNode->pcb.startTime = UNDEF_TIME;
        newNode->pcb.finishTime = UNDEF_TIME;
        newNode->pcb.memStart = UNDEF_ADDRESS;
        newNode->pcb.memSize = memSize;
        newNode->next = nullptr;

        if (pctList == nullptr || pctList->pcb.pid > newPID) 
        {
            newNode->next = pctList;
            pctList = newNode;
        } 
        else 
        {
            PctNode* current = pctList;
            while (current->next != nullptr && current->next->pcb.pid < newPID) 
            {
                current = current->next;
            }
            newNode->next = current->next;
            current->next = newNode;
        }

        return newPID;
    }

// ================================================================================== //

} // end of namespace group
