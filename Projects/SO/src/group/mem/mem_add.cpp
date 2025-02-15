/*
 *  \author Lázaro Sá: 407, 408
 */

#include "somm24.h"

#include <stdint.h>

namespace group 
{

// ================================================================================== //

    void memAddNodeToFreeList(MemNode *p)
    {
        soProbe(407, "%s(%p)\n", __func__, p);

        require(memAllocationPolicy != UndefMemoryAllocationPolicy, "Module is not in a valid open state!");
        require(memFreeList != UNDEF_MEM_NODE and memOccupiedList != UNDEF_MEM_NODE, "Module is not in a valid open state!");
        require(p != nullptr, "p must be a valid pointer");

        p->next = nullptr;

        if (memFreeList == nullptr) {
            memFreeList = p;
            return;
        }

        MemNode *prev = nullptr;
        MemNode *current = memFreeList;

        while (current != nullptr && current->block.start < p->block.start) {
            prev = current;
            current = current->next;
        }

        if (prev == nullptr) {
            p->next = memFreeList;
            memFreeList = p;
        } else {
            prev->next = p;
            p->next = current;
        }

        if (p->next != nullptr && (p->block.start + p->block.size) == p->next->block.start) {
            p->block.size += p->next->block.size;
            p->next = p->next->next;
        }

        if (prev != nullptr && (prev->block.start + prev->block.size) == p->block.start) {
            prev->block.size += p->block.size;
            prev->next = p->next;
            free(p);
        }
  

       /**
         * \brief Add given node to the list of free blocks
         * \details
         *  - The given node must be added to the list of free blocks;
         *  - if the added block is adjacent, in terms of memory to the next, a merging must be done;
         *  - if the added block is adjacent, in terms of memory to the previous, a merging must be done;
         *  - In case of an error, an appropriate exception must be thrown.
         *  - All exceptions must be of the type defined in this project (Exception).
         *
         * \param p Pointer to the node to be added to the free list
         */
    }

// ================================================================================== //

    void memAddNodeToOccupiedList(MemNode *p)
    {
        soProbe(408, "%s(%p)\n", __func__, p);

        require(memAllocationPolicy != UndefMemoryAllocationPolicy, "Module is not in a valid open state!");
        require(memFreeList != UNDEF_MEM_NODE and memOccupiedList != UNDEF_MEM_NODE, "Module is not in a valid open state!");
        require(p != nullptr, "p must be a valid pointer");

        p->next = nullptr;

        if (!memOccupiedList) {
            memOccupiedList = p;
            return;
        }

        MemNode *prev = nullptr;
        MemNode *current = memOccupiedList;

        // Insert the node in ascending order based on the start of the block
        while (current != nullptr && current->block.start < p->block.start) {
            prev = current;
            current = current->next;
        }

        if (prev == nullptr) {
            // Insert at the head of the list
            p->next = memOccupiedList;
            memOccupiedList = p;
        } else {
            // Insert in the middle or end
            prev->next = p;
            p->next = current;
        }

        // Ensure consistency by checking for possible overlap or errors
        if (p->next != nullptr && (p->block.start + p->block.size) > p->next->block.start) {
            throw Exception(ENOMEM,"Overlapping memory blocks detected in occupied list");
        }
        if (prev != nullptr && (prev->block.start + prev->block.size) > p->block.start) {
            throw Exception(ENOMEM,"Overlapping memory blocks detected in occupied list");
        }


        /**
         * \brief Add given node to the list of occupied blocks
         * \details
         *  - The given node must be added to the list of occupied blocks;
         *  - In case of an error, an appropriate exception must be thrown.
         *  - All exceptions must be of the type defined in this project (Exception).
         *
         * \param p Pointer to the node to be added to the occupied list
         */
    }

// ================================================================================== //

} // end of namespace group

