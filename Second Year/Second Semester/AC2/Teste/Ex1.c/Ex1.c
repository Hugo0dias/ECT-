#include <detpic32.h>

void delay(unsigned int ms){
    resetCoreTimer();
    while(readCoreTimer() < 20000 * ms);
}

int main(void) {

    TRISE = (TRISE & 0x0000) | 0xFF03; //111 1111 0000 0011
    TRISB = (TRISB & 0x0000) | 0x0005; //000 0000 0000 0101
    LATE = (LATE & 0x0000) | 0x00C0;   //000 0000 1100 0000
    int state = 0;

    while(1){

        if (LATE == 0x000C >> 1){
            LATE = ((LATE & 0x0000) | 0x00C0);
            if (state = 0){
                delay(217);
            }
            else{
                delay(137);
            }
            continue;
        }

        if((PORTBbits.RB0 == 1) & (PORTBbits.RB2 == 1)){

            delay(217);
            LATE = LATE >> 1;
            state = 0;  

        } else if((PORTBbits.RB0 == 0) & (PORTBbits.RB2 == 0)) {

            delay(137);
            LATE = LATE >> 1;
            state = 1;

        } else if (state == 1){

            delay(137);
            LATE = LATE >> 1;
            state = 1;

        } else  { 

            delay(217);
            LATE = LATE >> 1;
            state = 0;
            
        }

}
}
        


