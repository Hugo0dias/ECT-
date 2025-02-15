#include <detpic32.h>

void delay(unsigned int ms);

int main(void) {

    TRISC = TRISC & 0xBFFF; // configura entradas 1011 1111 1111 1111
                            // entrada 14 como output (saida) 

    while(1){

        LATC = LATC & 0xBFFF; // 1011 1111 1111 1111 - desliga
        delay(500);
        LATC = LATC | 0x4000; // 0100 0000 0000 0000 - liga 
        delay(500);

    }
    return 0;

}

void delay(unsigned int ms) {
    resetCoreTimer();
    while(readCoreTimer() < 20000 * ms);
}