#include <detpic32.h>

void delay( unsigned int us ) {

    resetCoreTimer();
    while(readCoreTimer() < 20000000 * us);

}

void setPWM( unsigned int DC ){

    if ( (DC < 0) | (DC > 100) ) {
        return;
    }
    OC1RS = ((PR3 + 1) * DC) / 100; // formula
}

int main(void){

    TRISB = (TRISB & 0x0000) | 0x000A;

    T3CONbits.TCKPS = 1; // 1:2 prescaler (i.e Fout_presc = 625 KHz)
    PR3 = 35713; 
    TMR3 = 0; // Reset timer T2 count register
    T3CONbits.TON = 1; // Enable timer T2 (must be the last command of the
                       // timer configuration sequence)
    OC3CONbits.OCM = 7; // PWM mode on OCx; fault pin disabled
    OC3CONbits.OCTSEL = 1; // Use timer T2 as the time base for PWM generation
    setPWM(10);
    OC3CONbits.ON = 1; // Enable OC1 module

    while(1){

        int value = (PORTB & 0x0000) | 0xFFF6; // le valor dos switches
        if(value == 8) { // se for os switches forem 1000
            setPWM(60); // duty cycle 60
        } else if (value == 2) { // se for os switches forem 0010
            setPWM(10); // duty cycle 10
        }

        delay(150);

    }

    return 0;

}
