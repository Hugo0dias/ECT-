#include <detpic32.h>

int main(void) {
    
    // Timer 3

    T3CONbits.TCKPS = 2; // 1:32 prescaler (i.e. fout_presc = 625 KHz)
    PR3 = 49999;
    TMR3 = 0;

    IPC3bits.T3IP = 2;
    IEC0bits.T3IE = 1;
    IFS0bits.T3IF = 0;

    OC1CONbits.OCM = 6; // PWM mode on OCx; fault pin disabled
    OC1CONbits.OCTSEL = 1;// Use timer T3 as the time base for PWM generation
    OC1RS = 12500;
    // Ton constant
    OC1CONbits.ON = 1;

    T3CONbits.TON = 1;
    
    ////////////////////////////////////

    
    while (1);
    return 0;
}
