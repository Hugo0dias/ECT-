#include <detpic32.h>

void delay(unsigned int ms);
void send2displays(unsigned char value);

void send2displays(unsigned char value){

    char disp7Scodes[16] = {0x3f, 0x06, 0x5b, 0x4f, 0x66, 0x6d, 0x7d, 0x07, 0x7f, 0x67, 0x77, 0x7c, 0x39, 0x5e, 0x79 ,0x71};    
    static char displayFlag = 0x0000;

    if (displayFlag == 0){
        LATD = (LATD & 0x0000) | 0x0040; // disp high
        LATB = ((LATB & 0x0000)) | (disp7Scodes[value >> 4] << 8);
    } else if (displayFlag == 1) {
        LATD = (LATD & 0x0000) | 0x0020; // disp low
        LATB = ((LATB & 0x0000)) | (disp7Scodes[value & 0x0F] << 8);
    }

    displayFlag = displayFlag ^ 0x0001;

}

void delay(unsigned int ms){

    resetCoreTimer();
    while (readCoreTimer < 20000 * ms);

}

int main(void){

    TRISB = (TRISB & 0x0000) | 0x80FF;
    TRISD = (TRISD & 0x0000) | 0xFF9F;

    while(1){
        
        send2displays(0x15);
        delay(200);

    }

    return 0;

}