#include <detpic32.h>

void delay(unsigned int ms);

int main(void) {

    TRISCbits.TRISC14 = 0; // RC14 as output

    LATCbits.LATC14 = 0; // RC14 = 0

    while(1) {
    // Alternative solution

    /*    resetCoreTimer();
        while(readCoreTimer() < 1000000); // Wait 0.5s
        LATC = LATC ^ 0x4000; // invert RC14*/

        delay(2000);
        LATC = LATC ^ 0x4000; // invert RC14

    }
    return 0;
}

void delay(unsigned int ms) {
    resetCoreTimer();
    while(readCoreTimer() < 20000 * ms);
}