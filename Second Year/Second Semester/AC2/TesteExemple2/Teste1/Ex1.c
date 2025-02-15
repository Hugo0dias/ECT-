#include <detpic32.h>

void delay(unsigned int ms){
    resetCoreTimer();
    while(readCoreTimer() < 20000 * ms);
}

int main(void){

    TRISB = (TRISB & 0x0000) | 0x000F;
    TRISE = (TRISE & 0x0000) | 0xFF00;
    unsigned int flag = 0;
    // D1 D2 D3 D4 - nos leds

    EnableInterrupts();

    while(1){
        unsigned int j;
        PORTB = (PORTB & 0x0000) | 0x000F;
        int i = PORTB;
        unsigned int reversedInput = ((i & 0x1) << 3) | ((i & 0x2) << 1) | ((i & 0x4) >> 1) | ((i & 0x8) >> 3);
        LATE = ((LATE & 0x0000) | reversedInput);
        if (flag == 0){
            for(j = 0; ; j++) {
                delay(1000);
                LATE = LATE << 1;
                LATE = LATE | 0x1; 
                if (LATE == 0xFF){
                    delay(1000);
                    break;
                }
            }
        }
    }
    return 0;

}