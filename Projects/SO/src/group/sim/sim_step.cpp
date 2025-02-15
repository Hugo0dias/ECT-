/*
 *  \author Hugo Dias 114142
 */

#include "somm24.h"

namespace group
{

// ================================================================================== //

/**
 * \brief Processes one step of the simulation
 * \return true if a step was processed, false otherwise
 */



    bool simStep() {

        static uint16_t IDLE = 0;
        bool Processed = false;

        soProbe(105, "%s()\n", __func__);

        // Preconditions
        require(simTime != UNDEF_TIME and stepCount != UNDEF_COUNT, "Simulation is not in a valid open state!");
        require(submissionTime != UNDEF_TIME and runoutTime != UNDEF_TIME, "Event times not initialized!");
        require(runningProcess != UNDEF_PID, "Running process state is invalid!");

        if (submissionTime < runoutTime) {
            
            Job Current_Job = jdtFetchNext(); 
            uint16_t NewProcess = pctNewProcess(Current_Job.submissionTime, Current_Job.lifetime, Current_Job.memSize);
            simTime = submissionTime;
            if (memBiggestFreeBlockSize() > Current_Job.memSize) {
                uint32_t Alocation = memAlloc(Current_Job.memSize);
                rdyInsert(NewProcess, Current_Job.lifetime);
                pctUpdateState(NewProcess, READY, simTime, Alocation);
            } else {
                swpInsert(NewProcess, pctMemSize(NewProcess));
                pctUpdateState(NewProcess, SWAPPED, UNDEF_TIME, UNDEF_ADDRESS);
            } 
            submissionTime = jdtNextSubmission();
            Processed = true;

        } else if (runoutTime != NEVER){

            simTime = runoutTime;  
            memFree(pctMemAddress(runningProcess));
            pctUpdateState(runningProcess, TERMINATED, simTime, UNDEF_ADDRESS);
            IDLE = 0;
            while (uint16_t process_to_load = swpFetch(memBiggestFreeBlockSize())) {
                uint32_t mem_address = memAlloc(pctMemSize(process_to_load));
                rdyInsert(process_to_load, pctLifetime(process_to_load));
                pctUpdateState(process_to_load, READY, simTime, mem_address);
            }
            Processed = true;

        } 

        if (IDLE == 0) {

            runningProcess = rdyFetch();
            if (runningProcess != 0) {
                runoutTime = simTime + pctLifetime(runningProcess);
                pctUpdateState(runningProcess, RUNNING, simTime, UNDEF_ADDRESS);
                IDLE = 1;
                Processed = true;
            } else {
                runoutTime = NEVER;
            }

        }

        stepCount++;
        return Processed;
    }
// ================================================================================== //

} // end of namespace group


