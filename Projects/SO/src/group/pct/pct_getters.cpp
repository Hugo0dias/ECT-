/*
 *  \author Lázaro Sá: 305, 306
    \author Orlando Marinheiro : 114060 -> 307
 */

#include "somm24.h"

#include <stdint.h>

namespace group
{
    /* ======================================================================== */

    double pctLifetime(uint16_t pid)
    {
        soProbe(305, "%s(%u)\n", __func__, pid);

        require(pctList != UNDEF_PCT_NODE, "The PCT linked list must exist");
        require(pid > 0, "a valid process ID must be greater than zero");

        //the head is the current node, i.e the given node pctList
        struct PctNode *head = pctList;
        for (struct PctNode *current = head; current != NULL; current = current->next) {
        // Access the process control block (pcb) of the current node
        PctBlock *pcb = &current->pcb;
        if(pcb->pid == pid){
            return pcb->lifetime;
            }
        }
        throw Exception(EINVAL, "No entry associated to the given pid");
    }

    /* ======================================================================== */

    uint32_t pctMemSize(uint16_t pid)
    {
        soProbe(306, "%s(%u)\n", __func__, pid);

        require(pctList != UNDEF_PCT_NODE, "The PCT linked list must exist");
        require(pid > 0, "a valid process ID must be greater than zero");

        //the head is the current node, i.e the given node pctList
        struct PctNode *head = pctList;
        for (struct PctNode *current = head; current != NULL; current = current->next) {
        // Access the process control block (pcb) of the current node
        PctBlock *pcb = &current->pcb;
        if(pcb->pid == pid){
            return pcb->memSize;
            }
        }
        throw Exception(EINVAL, "No entry associated to the given pid");
    }

    /* ======================================================================== */

    uint32_t pctMemAddress(uint16_t pid)
    {
        soProbe(307, "%s(%u)\n", __func__, pid);

        require(pctList != UNDEF_PCT_NODE, "The PCT linked list must exist");
        require(pid > 0, "a valid process ID must be greater than zero");


        /* replace with your code */

        PctNode *current = pctList;

        while (current != NULL) {
            if (current->pcb.pid == pid) {
                return current->pcb.memStart;
            }
            current = current->next;
        }
        
        throw Exception(EINVAL, "PID not found!");
    }

    /* ======================================================================== */

} // end of namespace somm22
