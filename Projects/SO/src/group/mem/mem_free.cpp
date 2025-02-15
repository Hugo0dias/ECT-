/*
 *  \author Orlando Marinheiro - 114060
 */

#include "somm24.h"

#include <stdint.h>

namespace group 
{

// ================================================================================== //

    void memFree(uint32_t address)
    {
        soProbe(405, "%s(%#x)\n", __func__, address);

        require(memAllocationPolicy != UndefMemoryAllocationPolicy, "Module is not in a valid open state!");
        require(memFreeList != UNDEF_MEM_NODE and memOccupiedList != UNDEF_MEM_NODE, "Module is not in a valid open state!");

        /* TODO POINT: Replace next instruction with my code */
        

        // encontrar o bloco na memoria ocupada
        MemNode *ocupied_node = memRetrieveNodeFromOccupiedList(address); 

        if (ocupied_node == NULL)
        {
            throw Exception(EINVAL, "Address not found in occupied list");
        }

        // adicionar o bloco à memoria livre
        try {
            memAddNodeToFreeList(ocupied_node);

        } catch (const Exception &e) {

            throw Exception(EFAULT, "Failed to add node to free list");
        }

        


    }

// ================================================================================== //

} // end of namespace group

