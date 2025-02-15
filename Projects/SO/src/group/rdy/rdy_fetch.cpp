/*
 *  \author Lázaro Sá
 */

#include "somm24.h"

namespace group
{

// ================================================================================== //

    uint16_t rdyFetch()
    {
        soProbe(505, "%s()\n", __func__);

        require(schedulingPolicy == FCFS or schedulingPolicy == SPN, "Module is not in a valid open state!");
        require(rdyList != UNDEF_RDY_NODE and rdyTail != UNDEF_RDY_NODE, "Module is not in a valid open state!");

        // - If the list is empty, 0 must be returned;
        if (rdyList == nullptr)
        {
            return 0; 
        }

        // get a pointer to the node that will be removed
        // and it's pid
        RdyNode *nodeToRemove = rdyList;
        uint16_t pid = nodeToRemove->process.pid;

        // update rdyList
        rdyList = rdyList->next;

        // update's the tail case the list becomes empty after the fetch
        if (rdyList == nullptr)
        {
            rdyTail = nullptr; 
        }

        // remove the node from the list
        delete nodeToRemove;

        return pid;

        //the brief says to throw an exception in case of error but i don't really see where

        /**
         * \brief Remove and return an entry from the queue
         * \details
         *  - If the list is empty, 0 must be returned;
         *  - otherwise, the first entry must be removed from the list and its PID returned;
         *  - In case of an error, an appropriate exception should be thrown.
         *  - All exceptions must be of the type defined in this project (Exception).
         *
         * \return The PID of the process fetched from the list; 0 if list is empty
         */
    }

// ================================================================================== //

} // end of namespace group

