#include <detpic32.h>

void delay(unsigned int ms){
    resetCoreTimer();
    while(readCoreTimer < 20000 * ms);
}

int main(void){

    TRISB = TRISB & 0x80FF; // 1000 0000 1111 1111
    TRISD = TRISD & 0xFF9F; // 1111 1111 1001 1111
    
    LATD = (LATD & 0x0000) | 0xFFDF;

    char disp7Scodes[16] = {0x3f, 0x06, 0x5b, 0x4f, 0x66, 0x6d, 0x7d, 0x07, 0x7f, 0x67, 0x77, 0x7c, 0x39, 0x5e, 0x79 ,0x71};

    while(1){
        int i = PORTB & 0x000F;
        LATB = (LATB & 0x0000) | (disp7Scodes[i] << 8); 
    }

    return 0;
}