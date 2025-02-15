/*
 *  \author Orlando Marinheiro 114060 : 207
            Hugo Dias 114142 : 206
 */

#include "somm24.h"

namespace group
{

// ================================================================================== //

    double jdtNextSubmission()
    {
        soProbe(206, "%s()\n", __func__);

        require(jdtIn != UNDEF_JOB_INDEX and jdtOut != UNDEF_JOB_INDEX, "Module is not in a valid open state!");
        require(jdtCount != UNDEF_JOB_COUNT, "Module is not in a valid open state!");

        if (jdtCount == 0)
            return NEVER;
        else
            return jdtTable[jdtOut].submissionTime;
            
    }

// ================================================================================== //

    Job jdtFetchNext()
    {
        soProbe(207, "%s()\n", __func__);

        require(jdtIn != UNDEF_JOB_INDEX and jdtOut != UNDEF_JOB_INDEX, "Module is not in a valid open state!");
        require(jdtCount != UNDEF_JOB_COUNT, "Module is not in a valid open state!");

        if (jdtCount == 0){
            throw Exception(ENOSYS, __func__);
        }

        Job nextJob = jdtTable[jdtOut];
        jdtOut ++;
        jdtCount --;

        return nextJob;
    }

// ================================================================================== //

} // end of namespace group


