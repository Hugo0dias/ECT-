/*
 *  \author Hugo Dias 114142 - 302
 */

#include "somm24.h"
#include <cstdlib> 

namespace group 
{

// ================================================================================== //

    void pctTerm() 
    {
        soProbe(302, "%s()\n", __func__);

        require(pctList != UNDEF_PCT_NODE, "Module is not in a valid open state!");

        PctNode* current = pctList;
        while (current != nullptr)
        {
            PctNode* next = current->next; 
            free(current);                 
            current = next;                
        }

        pctList = UNDEF_PCT_NODE;

        for (uint16_t i = 0; i < MAX_JOBS; ++i)
        {
            pctPID[i] = 0; 
        }
    }

// ================================================================================== //

} // end of namespace group
