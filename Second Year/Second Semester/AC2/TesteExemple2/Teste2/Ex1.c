// Duty Cycle

#include <detpic32.h>

#define PR3x ((1/(120/20000000))/2)-1

void setPWM(unsigned int dutyCycle) {
    if (dutyCycle > 100) { return; }
    OC1RS = (PR3x * dutyCycle) / 100;
}

void delay(unsigned int us){
    resetCoreTimer();
    while(readCoreTimer() < 20000000 * us);
}

int main(void){

    TRISB = (TRISB & 0x0000) | 0x000F;

    // conf Timer 3

    T3CONbits.TCKPS = 2;
    PR3 = PR3x;
    TMR3 = 0;
    
    // conf dutycycle

    OC1CONbits.OCM = 6;
    OC1CONbits.OCTSEL = 1;
    setPWM(75);

    OC1CONbits.ON = 1;
    T3CONbits.TON = 1;


    while(1){
        delay(360);
        if((PORTB == 0x00) | (PORTB == 0x0A) | (PORTB == (0x08)) | (PORTB == (0x02))){
            setPWM(30);
        } else if ((PORTB == (0x0F)) | (PORTB == (0x0D)) | (PORTB == (0x05)) | (PORTB == (0x07))) {
            setPWM(55);
        } else {
            setPWM(75);
        }
        printInt(PR3x, 16 | 16  << 3);
    }
    return 0;

}

// 1010 0010 0000 1000
// 0101 1101 1111 0111


// TPWM = 120Hz
// DC = 0.75
// meter resolucao no maximo
