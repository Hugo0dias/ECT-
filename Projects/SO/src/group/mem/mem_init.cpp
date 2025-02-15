/*
 *  \author Rúben Costa
 */

#include "somm24.h"

#include <stdint.h>
#include <math.h>

namespace group 
{

// ================================================================================== //

    void memInit(uint32_t memSize, uint32_t memKernelSize, MemoryAllocationPolicy policy)
    {
        const char *pas;
        switch (policy)
        {
            case UndefMemoryAllocationPolicy: pas = "UndefMemoryAllocationPolicy"; break;
            case BestFit: pas = "BestFit"; break;
            case WorstFit: pas = "WorstFit"; break;
            default: pas = "InvalidPattern"; break;
        }
        soProbe(401, "%s(%#x, %#x, %s)\n", __func__, memSize, memKernelSize, pas);

        require(memAllocationPolicy == UndefMemoryAllocationPolicy, "Module is not in a valid closed state!");
        require(memFreeList == UNDEF_MEM_NODE and memOccupiedList == UNDEF_MEM_NODE, "Module is not in a valid closed state!");
        require(memSize > memKernelSize, "total memory size must be bigger than the one use by the kernel of OS");
        require(policy == BestFit or policy == WorstFit, "policy must be BestFit or WorstFit");

        /* TODO POINT: Replace next instruction with my code */
        
        // Inicializar policy 
        memAllocationPolicy = policy;

        // criar lista de memória livre
        MemNode *InitialmemFreeList = (MemNode*)malloc(sizeof(MemNode));
        if (!InitialmemFreeList)
        {
            throw Exception(ENOMEM, "Failed to allocate memory for InitialmemFreeList in mem_init.cpp");
        }

        // inicializar start, size e next na lista
        // 'The kernel must occupy the lower part of the total available main memory.'
        InitialmemFreeList->block.start = memKernelSize;
        // memória livre
        InitialmemFreeList->block.size = memSize - memKernelSize;
        InitialmemFreeList->next = NULL;

        // configurar listas globais
        memFreeList = InitialmemFreeList;
        memOccupiedList = NULL;
    }

// ================================================================================== //

} // end of namespace group

