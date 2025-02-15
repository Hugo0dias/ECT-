/*
 *  \author Rúben Costa
 */

#include "somm24.h"

namespace group
{

// ================================================================================== //

    void swpInsert(uint16_t pid, uint32_t size)
    {
        soProbe(604, "%s(%hu, %u)\n", __func__, pid, size);

        require(swappingPolicy == FIFO or swappingPolicy == FirstFit, "Module is not in a valid open state!");
        require(swpList != UNDEF_SWP_NODE and swpTail != UNDEF_SWP_NODE, "Module is not in a valid open state!");
        require(pid != 0, "a valid process ID must be greater than zero");

        /* TODO POINT: Replace next instruction with your code */
        //throw Exception(ENOSYS, __func__);

        SwpNode *NewswpList = (SwpNode*)malloc(sizeof(SwpNode));
        if (!NewswpList)
        {
            throw Exception(ENOSYS, "Failed to allocate memory for NewswpList in swp_insert.cpp");
        }

        NewswpList->process.pid = pid;
        NewswpList->process.size = size;
        NewswpList->next = NULL;

        // se tiver vazio
        if (swpList == NULL)
        {
            swpList = NewswpList;
            swpTail = NewswpList;
        } else {
            // se não tiver vazio
            swpTail->next = NewswpList;
            swpTail = NewswpList;
        }
    }

// ================================================================================== //

} // end of namespace group

