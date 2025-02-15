/*
 *  \author Hugo Dias
 */

#include "somm24.h"

namespace group 
{

// ================================================================================== //

    void pctInit(uint16_t pid0, uint16_t cnt, uint32_t seed) 
    {
        soProbe(301, "%s(%hu, %hu, %u)\n", __func__, pid0, cnt, seed);

        require(pctList == UNDEF_PCT_NODE, "The module is not in a valid closed state");
        require(cnt > 1 and cnt <= MAX_JOBS, "cnt must be > 1 and <= MAX_JOBS");


        if (pctList != UNDEF_PCT_NODE) {
            throw Exception(EINVAL, "The module is not in a valid closed state"); 
        }

        if (cnt <= 1 || cnt > MAX_JOBS) {
            throw Exception(ERANGE, "cnt must be > 1 and <= MAX_JOBS"); 
        }


        pctList = nullptr;
        for (uint16_t i = 0; i < MAX_JOBS; ++i) {
            pctPID[i] = 0;
        }

        srand(seed);

        uint16_t temp[cnt];
        for (uint16_t i = 0; i < cnt; i++) {
            temp[i] = pid0 + i;
        }

        for (uint16_t i = cnt - 1; i > 0; i--) {
            uint16_t j = rand() % (i + 1);  
            uint16_t tempVal = temp[i];
            temp[i] = temp[j];
            temp[j] = tempVal;
        }

        for (uint16_t i = 0; i < cnt; i++) {
            pctPID[i] = temp[i];
        }
    }

// ================================================================================== //

} // end of namespace group

