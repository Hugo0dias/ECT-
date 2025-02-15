#include <detpic32.h>

void delay(unsigned int ms);

int main(void) {

    unsigned int i;
    unsigned int segment;

    TRISD = TRISD & 0xFF9F; // 1111 1111 1001 1111
    TRISB = TRISB & 0x80FF; // 1000 0000 1111 1111
    LATDbits.LATD5 = 1;
    LATDbits.LATD6 = 0;

    while(1){

        segment = 1;
        for(i = 0; i < 7; i++){
            LATB = ((LATB & 0x0000) | 0x80FF) | segment << 8;
            delay(500);
            segment = segment << 1;
        }

        if (LATD == 0xFFDF){
            LATD = (LATD & 0x0000) | 0xFFBF; 
        } else {
            LATD = (LATD & 0x0000) | 0xFFDF;
        }
    }

    return 0;
}

void delay(unsigned int ms){
    resetCoreTimer();
    while(readCoreTimer() < 20000 * ms);
}
