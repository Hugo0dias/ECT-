/*
 *  \author Bernardo Marujo 107322
 */

#include "somm24.h"

#include <stdint.h>

namespace group 
{

// ================================================================================== //

    void pctUpdateState(uint16_t pid, ProcessState state, double time = UNDEF_TIME, uint32_t address = UNDEF_ADDRESS)
    {
        bool validState = true;
        const char *sas = "UNKOWN";
        switch (state) {
            case NEW: sas = "NEW"; break;
            case RUNNING: sas = "RUNNING"; break;
            case READY: sas = "READY"; break;
            case SWAPPED: sas = "SWAPPED"; break;
            case TERMINATED: sas = "TERMINATED"; break;
            default: validState = false; break;
        }
        soProbe(308, "%s(%hu, %s, %.1f, %#x)\n", __func__, pid, sas, time, address);

        require(pctList != UNDEF_PCT_NODE, "Module is not in valid open state");
        require(pid != 0, "PID can't be zero");
        require(state != NEW, "on updating, state can not be NEW");
        require(validState, "Wrong state value");
        
        PctNode *node = pctList;
        while (node != NULL) {
            if (node->pcb.pid == pid) {
                break;
            }
            node = node->next;
        }


        if (node == NULL) {
            throw Exception(EINVAL, "Process with given PID not found");
        }

        if (state == TERMINATED) {
            node->pcb.state = TERMINATED;
            node->pcb.finishTime = time;
            return;
        }
        else if (state == READY) {
            node->pcb.state = READY;
            node->pcb.storeTime = time;
            node->pcb.memStart = address;
            return;
        }
        else if (state == RUNNING) {
            node->pcb.state = RUNNING;
            node->pcb.startTime = time;
            return;
        }
        else if (state == SWAPPED) {
            node->pcb.state = SWAPPED;
            return;
        }
        else {
            throw Exception(EINVAL, "Invalid state");
        }

    }

// ================================================================================== //

} // end of namespace group

