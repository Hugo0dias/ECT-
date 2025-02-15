/*
 *  \author Rúben Costa
 */

#include "somm24.h"

namespace group
{

// ================================================================================== //

    uint16_t jdtRandomFill(uint32_t seed, uint16_t cnt, uint32_t maxSize)
    {
        soProbe(205, "%s(%u, %u, %#x)\n", __func__, seed, cnt, maxSize);

        require(jdtIn != UNDEF_JOB_INDEX and jdtOut != UNDEF_JOB_INDEX, "Module is not in a valid open state!");
        require(jdtCount != UNDEF_JOB_COUNT, "Module is not in a valid open state!");
        require(cnt == 0 or (cnt >= 2 and cnt <= MAX_JOBS), "Number of jobs is invalid");

        /* TODO POINT: Replace next instruction with your code */
        //throw Exception(ENOSYS, __func__);
        srand(seed);

        // funçao auxiliar
        // dado um valor minimo e um valor max
        // e seed e num steps
        // calcular [] max - min
        // /pelo num steps
        //rand%(nmax steps)

        uint16_t random_jobs;
        if (cnt == 0)
        {
            // the actual number of jobs must be randomly generated in the range [2,MAX_JOBS]
            random_jobs = rand() % (MAX_JOBS - 1) + 2;
        } else {
            random_jobs = cnt;
        }

        uint16_t last_sub_time = 0;
        for (int i = 0; i < random_jobs; i++)
        {
            // range [0,100] in steps of 0.1
            uint16_t random_intervalo = (uint16_t)((rand() % 1001) / 10);
            // para ser crescente
            uint16_t random_time = random_intervalo + last_sub_time;
            // range [10,1000] in steps of 0.1
            uint16_t random_lifetime = (uint16_t)((rand() % 10001 + 10) / 10);
            // must be greater than zero and not greater the maximum allowed size
            uint16_t random_size = rand() % maxSize + 1;

            jdtTable[jdtIn].submissionTime = random_time;
            jdtTable[jdtIn].lifetime = random_lifetime;
            jdtTable[jdtIn].memSize = random_size;

            last_sub_time = random_time;

            jdtIn = (jdtIn + 1) % MAX_JOBS;
            jdtCount++;
        }
        
        return jdtCount;
    }

// ================================================================================== //

} // end of namespace group

