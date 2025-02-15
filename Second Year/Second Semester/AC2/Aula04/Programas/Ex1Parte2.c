#include <detpic32.h>

void delay(unsigned int ms);

int main(void) {
    
    LATDbits.LATD5 = 1;
    LATDbits.LATD6 = 0;

    TRISD = TRISD & 0xFF9F // predefenir como saida : 1111 1111 1001 1111
    TRISB = TRISB & 0x80FF // predefenir como saida : 1000 0000 1111 1111

    while(1){

        char usr_input = getChar(); 
        putChar(usr_input);

        if(char == 'a' || char == 'A'){
            LATB = (LATB & 0x80FF) | 0x0100 // 1000 0000 1111 1111
        }

    }



    return 0;
}

void delay(unsigned int ms) {
    resetCoreTimer();
    while(readCoreTimer() < 20000 * ms);
}