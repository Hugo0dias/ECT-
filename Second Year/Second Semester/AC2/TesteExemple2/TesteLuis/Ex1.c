#include <detpic32.h>

void delay(unsigned int us){
    resetCoreTimer();
    while(readCoreTimer() < 20000000 * us);
}

void setPWM(unsigned int Dutycycle){

    if (Dutycycle > 100) { return ; }
    OC1RS = ((PR3+1) * Dutycycle) / 100;
}


int main(void){

    TRISB = (TRISB & 0x0000) | 0x000F;

    // conf Timer 3

    T3CONbits.TCKPS = 2; // 1:32 prescaler (i.e. fout_presc = 625 KHz)
    PR3 = 41666;
    TMR3 = 0;
    T2CONbits.TON = 1;

    // conf OC

    OC3CONbits.OCM = 7; // PWM mode on OCx; fault pin disabled
    OC3CONbits.OCTSEL = 1;// Use timer T3 as the time base for PWM generation
    setPWM(75);
    OC3CONbits.ON = 1;

    // conf interrupts

    IPC3bits.T3IP = 1; // Interrupt priority (must be in range [1..6])
    IEC0bits.T3IE = 1; // Enable timer T3 interrupts
    IFS0bits.T3IF = 0; // Reset timer T3 interrupt flag

    while(1){

        int valor = (PORTB & 0x0000) | 0xFFFA;

        if (valor == 0) {
            setPWM(30);
        } else if (valor == 5) {
            setPWM(55);
        }

        printInt10(PORTDbits.RD1);

        delay(360);

    }

    return 0; 

}