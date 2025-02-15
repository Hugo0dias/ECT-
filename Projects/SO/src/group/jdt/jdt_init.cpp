/*
 *  \author Bernardo Marujo 107322
 */

#include "somm24.h"

namespace group
{

// ================================================================================== //

    void jdtInit()
    {
        soProbe(201, "%s()\n", __func__);

        require(jdtIn == UNDEF_JOB_INDEX and jdtOut == UNDEF_JOB_INDEX, "Module is not in a valid closed state!");
        require(jdtCount == UNDEF_JOB_COUNT, "Module is not in a valid closed state!");

        jdtCount = 0;
        jdtIn = 0;
        jdtOut = 0;

    }

// ================================================================================== //

} // end of namespace group

