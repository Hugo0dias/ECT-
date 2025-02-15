/*
 *  \author Rúben Costa
 */

#include "somm24.h"

namespace group 
{

// ================================================================================== //

    void jdtTerm() 
    {
        soProbe(202, "%s()\n", __func__);

        require(jdtIn != UNDEF_JOB_INDEX and jdtOut != UNDEF_JOB_INDEX, "Module is not in a valid open state!");
        require(jdtCount != UNDEF_JOB_COUNT, "Module is not in a valid open state!");

        /* TODO POINT: Replace next instruction with your code */
        //throw Exception(ENOSYS, __func__);

        // Resetar variáveis
        jdtIn = UNDEF_JOB_INDEX;
        jdtOut = UNDEF_JOB_INDEX;
        jdtCount = UNDEF_JOB_COUNT;
 
    }

// ================================================================================== //

} // end of namespace group

