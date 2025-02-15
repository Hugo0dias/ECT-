/*
 *  \author Lázaro Sá 
 */

#include "somm24.h"

namespace group 
{

// ================================================================================== //

    void simTerm() 
    {
        soProbe(102, "%s()\n", __func__);

        require(simTime != UNDEF_TIME and stepCount != UNDEF_COUNT, "Module not in a valid open state!");
        require(submissionTime != UNDEF_TIME and runoutTime != UNDEF_TIME, "Module is not in a valid open state!");
        require(runningProcess != UNDEF_PID, "Module is not in a valid open state!");

        simTime = UNDEF_TIME;
        stepCount = UNDEF_COUNT;
        submissionTime = UNDEF_TIME;
        runoutTime = UNDEF_TIME;
        runningProcess = UNDEF_PID;

        jdtTerm();
        pctTerm();
        memTerm();
        rdyTerm();
        swpTerm();
    }

// ================================================================================== //

} // end of namespace group

