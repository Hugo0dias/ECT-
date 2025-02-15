#include <detpic32.h>

void delay(unsigned int ms);

int main(void) {

    unsigned int i;
    unsigned int segment;

    TRISD = TRISD & 0xFF9F; // 1111 1111 1001 1111
    TRISB = TRISB & 0x80FF; // 1000 0000 1111 1111
    LATD = (LATD & 0x0000) | 0xFFBF; // 1111 1111 1011 1111
    LATD = (LATD & 0xffdf) | 0x0040; // 0000 0000 0100 0000
    
    while(1){

        segment = 1;
        for(i = 0; i < 7; i++){
            LATB = ((LATB & 0x0000) | 0x80FF) | segment << 8;
            delay(20);
            segment = segment << 1;
        }
        
        LATD = LATD ^ 0x0060; // 0000 0000 0110 0000
        
    }

    return 0;
}

void delay(unsigned int ms){
    resetCoreTimer();
    while(readCoreTimer() < 20000 * ms);
}

// 110 0000
// 010 0000
// 