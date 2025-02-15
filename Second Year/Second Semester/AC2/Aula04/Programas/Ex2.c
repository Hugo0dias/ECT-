#include <detpic32.h>

void delay(unsigned int ms);

int main(void){
    TRISE = TRISE & 0xFFC2; //configurar portos
    int counter = 0;
    while(1){ // ciclo
        LATE = (LATE & 0xFFC2) /* 1111 1111 1100 0011 - */ | counter << 3; //
        resetCoreTimer(); 
        while( readCoreTimer() < 20000000 );
        counter = (counter + 1) % 10; // limitar
    }
}

void delay(unsigned int ms) {
    resetCoreTimer();
    while(readCoreTimer() < 20000 * ms);
}