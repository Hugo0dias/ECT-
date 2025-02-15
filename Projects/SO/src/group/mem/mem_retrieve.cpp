/*
 *  \author Rúben Costa -> memRetrieveNodeFromOccupiedList
    \author Lázaro Sá -> memRetrieveNodeFromFreeList
 */

#include "somm24.h"

#include <stdint.h>

namespace group 
{

// ================================================================================== //

    MemNode *memRetrieveNodeFromFreeList(uint32_t size, MemoryAllocationPolicy policy)
    {
        soProbe(409, "%s(%#x, %d)\n", __func__, size, policy);

        require(memAllocationPolicy != UndefMemoryAllocationPolicy, "Module is not in a valid open state!");
        require(memFreeList != UNDEF_MEM_NODE and memOccupiedList != UNDEF_MEM_NODE, "Module is not in a valid open state!");
        require(policy == BestFit or policy == WorstFit, "Allocation policy must be 'BestFit' or 'WorstFit'");

        MemNode *current = memFreeList;
        MemNode *prev = NULL;
        MemNode *selectedNode = NULL;
        MemNode *selectedPrev = NULL;
        uint32_t selectedSize = (policy == BestFit) ? UINT32_MAX : 0;

        // select block by the policy
        while (current)
        {
            if (current->block.size >= size)
            {
                if ((policy == BestFit && current->block.size < selectedSize) ||
                    (policy == WorstFit && current->block.size > selectedSize))
                {
                    selectedNode = current;
                    selectedPrev = prev;
                    selectedSize = current->block.size;
                }
            }

            prev = current;
            current = current->next;
        }

        if (!selectedNode)
        {
            throw Exception(ENOENT, __func__);
        }

        // Remove the selected node from the free list
        if (selectedNode->block.size > size)
        {
            // Create a new node for the remaining memory
            MemNode *remainingNode = new MemNode();
            remainingNode->block.start = selectedNode->block.start + size;
            remainingNode->block.size = selectedNode->block.size - size;
            remainingNode->next = selectedNode->next;

            // Update the selected node's size to the requested size
            selectedNode->block.size = size;

            // Update the free list to point to the remaining node
            if (selectedPrev)
            {
                selectedPrev->next = remainingNode;
            }
            else
            {
                memFreeList = remainingNode;
            }
        }
        else
        {
            // If the selected block is exactly the size needed, just remove it from the free list
            if (selectedPrev)
            {
                selectedPrev->next = selectedNode->next;
            }
            else
            {
                memFreeList = selectedNode->next;  
            }
        }

        selectedNode->next = NULL;
        return selectedNode;
    }

// ================================================================================== //

    MemNode *memRetrieveNodeFromOccupiedList(uint32_t address)
    {
        soProbe(410, "%s(%#x)\n", __func__, address);

        require(memAllocationPolicy != UndefMemoryAllocationPolicy, "Module is not in a valid open state!");
        require(memFreeList != UNDEF_MEM_NODE and memOccupiedList != UNDEF_MEM_NODE, "Module is not in a valid open state!");

        /* TODO POINT: Replace next instruction with my code */
        //throw Exception(ENOSYS, __func__);

        MemNode *current = memOccupiedList;
        MemNode *prev = NULL;

        while (current)
        {   
            //uint32_t end_address = current->block.start + current->block.size;
            //if (current->block.start <= address && end_address > address)
            if (current->block.start == address)

            {
                if (prev == NULL)
                {
                    memOccupiedList = current->next;
                } else
                {
                    prev->next = current->next;
                }

                //MemNode *current2 = current;
                //free(current);

                return current;
            }

            prev = current;
            current = current->next;
        }

        throw Exception(ENOENT, __func__);
    }

// ================================================================================== //

} // end of namespace group

