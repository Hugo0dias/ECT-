/*
 *  \author Bernardo Marujo 107322 - 406 
 *  \author Hugo Dias 114142 - 404
 */

#include "somm24.h"

#include <stdint.h>
#include <string.h>

namespace group 
{

// ================================================================================== //
    // return the size of the biggest free block in the memory
    uint32_t memBiggestFreeBlockSize()
    {
        soProbe(406, "%s()\n", __func__);

        require(memAllocationPolicy != UndefMemoryAllocationPolicy, "Module is not in a valid open state!");
        require(memFreeList != UNDEF_MEM_NODE and memOccupiedList != UNDEF_MEM_NODE, "Module is not in a valid open state!");

        MemNode *node = memFreeList;

        uint32_t biggest = 0;

        while (node)
        {
            if (node->block.size > biggest)
            {
                biggest = node->block.size;
            }

            node = node->next;
        }

        return biggest;
    }

// ================================================================================== //

    uint32_t memAlloc(uint32_t size) {
        soProbe(404, "%s(%#x)\n", __func__, size);

        require(memAllocationPolicy != UndefMemoryAllocationPolicy, "Module is not in a valid open state!");
        require(memFreeList != UNDEF_MEM_NODE && memOccupiedList != UNDEF_MEM_NODE, "Module is not in a valid open state!");

        if (size == 0) {
            return UNDEF_ADDRESS;
        }

        MemNode *node = nullptr;

        switch (memAllocationPolicy) {
            case BestFit:
                node = memRetrieveNodeFromFreeList(size, BestFit); // tira dos free
                break;
            case WorstFit:
                node = memRetrieveNodeFromFreeList(size, WorstFit);
                break;
            default:
                throw Exception(ENOSYS, "Invalid memory allocation policy!");
        }
        if (node == nullptr) {
            throw Exception(ENOSYS, "Memory allocation failed: no suitable block found.");
        }

        memAddNodeToOccupiedList(node); // adiciona aos ocupados
        return node->block.start; // Retorna endereço do bloco
    }

// ================================================================================== //

} // end of namespace group

