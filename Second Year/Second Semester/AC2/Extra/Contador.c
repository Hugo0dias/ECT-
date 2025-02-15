#include <detpic32.h>

void delay(unsigned int ms){
    resetCoreTimer();
    while(readCoreTimer() < 20000*ms);
}

int main(void){
    unsigned int i = 0;
    TRISE = (TRISE & 0x0000) | 0xFFF0;
    unsigned char count = 0x00;
    while(1){
        for (i = 0; i<16; i++){
            LATE = (LATE & 0x0000) | count;
            delay(250);
            count++;
        }
    }

    return 0;

}