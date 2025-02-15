#include <detpic32.h>

void delay(unsigned int ms);

int main(void){
    TRISE = TRISE & 0xFFC2; //configurar portos
    int counter = 10;
    while(1){ // ciclo
        LATE = (LATE & 0xFFC2) /* 1111 1111 1100 0011 - */ | counter << 2; //
        resetCoreTimer(); 
        while( readCoreTimer() < 20000000 );
        if (counter>0){
        counter = (counter - 1) ; // limitar
        } else {
            counter = 10;
        }
    }
}

void delay(unsigned int ms) {
    resetCoreTimer();
    while(readCoreTimer() < 20000 * ms);
}