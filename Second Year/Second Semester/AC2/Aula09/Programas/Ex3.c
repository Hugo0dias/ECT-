#include <detpic32.h>

void setPWM(unsigned int dutyCycle)
{
    if (dutyCycle < 0 && dutyCycle >100) { return; } 
    unsigned int OC1RS = (49999 * dutyCycle) / 100;
}

int main(void) {
    // Configure OC1 as output
    // TRISDbits.TRSID0 = 0;

    // T3 configuration
    T3CONbits.TCKPS = 2;
    PR3 = 49999; TMR3 = 0;
    IPC3bits.T3IP = 2;
    IEC0bits.T3IE = 1;
    IFS0bits.T3IF = 0;
    T3CONbits.TON = 1;

    OC1CONbits.OCM = 6;
    OC1CONbits.OCTSEL = 1; 
    OC1RS = 12500;
    OC1CONbits.ON = 1;

    while (1) {};
    return 0;
}