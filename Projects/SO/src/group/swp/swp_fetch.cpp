/*
 *  \author Rúben Costa
 */

#include "somm24.h"

namespace group
{

// ================================================================================== //

    uint16_t swpFetch(uint32_t sizeAvailable)
    {
        soProbe(605, "%s(%#x)\n", __func__, sizeAvailable);

        require(swappingPolicy == FIFO or swappingPolicy == FirstFit, "Module is not in a valid open state!");
        require(swpList != UNDEF_SWP_NODE and swpTail != UNDEF_SWP_NODE, "Module is not in a valid open state!");

        /* TODO POINT: Replace next instruction with your code */
        //throw Exception(ENOSYS, __func__);

        //if (swpList == NULL)
        //{
        //    throw Exception(EINVAL, "No process fits in the available memory size.");
        //}

        SwpNode *current = swpList;


        //If the active policy is FIFO than remove the first entry in the list 
        //if the value of field size is not bigger than the specified
        // Só ve o 1o elemento se cumprir retorn pid, senao nao faz nada
        if (swappingPolicy == FIFO) {
            if (current && current->process.size <= sizeAvailable)
            {
                uint16_t pid = current->process.pid;
                // atualiza lista para o proximo
                swpList = current->next;

                // se for o ultimo elemento
                // lista so tinha 1 nó
                if (swpList == NULL) {
                    swpTail = NULL;
                }

                // libertar memoria
                free(current);
                return pid;
            }
        
        // If the active policy is FirstFit than remove 
        // the first process that fits in the available memory
        // procura por uma possivel soluçao em toda a lista e retorna a 1a
        } else if (swappingPolicy == FirstFit)
        {
            // serve para ver se é cabeça ou nao
            SwpNode *prev = NULL;

            while (current)
            {
                // se o processo cabe na memoria
                if (current->process.size <= sizeAvailable)
                {
                    uint16_t pid = current->process.pid;

                    // ver se é cabeça
                    // swplist aponta para a cabeça
                    // se é retirada passa para o proximo no
                    if (prev == NULL)
                    {
                        swpList = current->next;
                    } else
                    {
                        prev = current->next;
                    }
                    
                    // atualizar Tail se for removido ultimo
                    // se for meter o anterior tornasse o ultimo
                    if (swpTail == current) {
                        swpTail = prev;
                    }

                    // libertar memoria
                    free(current);
                    return pid;
                }

                // caso nao seja avançar
                prev= current;
                current = current->next;
                
            }
        }

        return 0;    
    }

// ================================================================================== //

} // end of namespace group

