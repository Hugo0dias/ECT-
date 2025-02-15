/*
 *  \author Hugo Dias - 402  
 */

#include "somm24.h"
#include <cstdlib>

namespace group 
{

// ================================================================================== //

    void memTerm() 
    {
        soProbe(402, "%s()\n", __func__);

        require(memAllocationPolicy != UndefMemoryAllocationPolicy, "Module is not in a valid open state!");
        require(memFreeList != UNDEF_MEM_NODE and memOccupiedList != UNDEF_MEM_NODE, "Module is not in a valid open state!");

        // Liberta todos os nós em memFreeList
        MemNode *currentNode = memFreeList;
        while (currentNode != nullptr && currentNode != UNDEF_MEM_NODE) {
            MemNode *next = currentNode->next;
            free(currentNode);
            currentNode = next;
        }

        // Liberta todos os nós em memOccupiedList
        currentNode = memOccupiedList;
        while (currentNode != UNDEF_MEM_NODE && currentNode != nullptr) {
            MemNode *next = currentNode->next;
            free(currentNode);
            currentNode = next;
        }

        // Reseta variaveis globais
        memFreeList = UNDEF_MEM_NODE;
        memOccupiedList = UNDEF_MEM_NODE;
        memAllocationPolicy = UndefMemoryAllocationPolicy;
    }

// ================================================================================== //

} // end of namespace group