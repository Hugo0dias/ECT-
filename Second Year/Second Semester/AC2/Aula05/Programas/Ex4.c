#include <detpic32.h>
void delay(unsigned int ms) ;
void send2displays(unsigned char value) {

    static const char disp7Scodes[] = {

        0x3F, 0x06, 0x5B, 0x4F,
        0x66, 0x6D, 0x7D, 0x07,
        0x7F, 0x6F, 0x77, 0x7C,
        0x39, 0x5E, 0x79, 0x71

    };

    static char displayFlag = 0;

    // Write the values of RD5 and RD6 , escreve valores nos seletor de displa (H)
    // 0000 0000 0100 0000 
    char digit_hi = value >> 4;
    char digit_low = value & 0x0F;

    if(displayFlag = 0){

        LATD = (LATD & 0xFFBF ) | 0x0040;
        LATB = (LATB & 0x80FF) | disp7Scodes[digit_low] << 8; // Write the values of RB8-RB14
      
        displayFlag = 1;

    } else {

        LATD = (LATD &  0xFFBF ) | 0x0020;
        LATB = (LATB & 0x80FF) | disp7Scodes[digit_hi] << 8; // Write the values of RB8-RB1
        displayFlag = 0;

    }

/*

    LATB = (LATB & 0x80FF) | disp7Scodes[digit_hi] << 8; // Write the values of RB8-RB14
    LATD = ( TRISD && 0xFFBF ) | 0x0020; // Activate HI-D, disable LO-D
    LATB = (LATB & 0x80FF) | disp7Scodes[digit_low] << 8; // Write the values of RB8-RB14
    LATD = ( TRISD && 0xFFBF ) | 0x0040; // Activate HI-D, disable LO-D
*/
}




int main(void){
    // declare variables
    // initialize ports
    unsigned char counter = 0x00;
    while(1) {
        unsigned int i = 0;
    do{

        send2displays(counter);
        delay(20);
        // wait 20 ms (1/50Hz)
    } while(++i < 100);
        counter++;
        // increment counter (mod 256)
    }
    return 0;
}

void delay(unsigned int ms) {
    resetCoreTimer();
    while(readCoreTimer() < 20000 * ms);
}