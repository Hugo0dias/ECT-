#include <detpic32.h>

void delay(unsigned int ms);

int main(void) {

    TRISE = TRISE & 0xFF87; 
    unsigned int counter = 0;

    while(1){

        LATE = LATE & 0xFF87; // mantem os leds que quero configurar todos a 0
        LATE = LATE | counter << 3; // mete tres 0 antes do numero 0x00 0xxx x000
        delay(500);
        counter = (counter + 1) % 10; // mantem o limite entre 0-9

    }

    return 0;

}

void delay(unsigned int ms) {
    resetCoreTimer();
    while(readCoreTimer() < 20000 * ms);
}