#include <detpic32.h>

void SetPWM( unsigned int DC){
    if (DC < 0 && DC > 100) { return; }
    unsigned int OC3RS = (49999 * DC) / 100 ;
}

int main() {

    // ports
    TRISC = TRISC & 0x0000 | 0xBFFF;

    T3CONbits.TCKPS = 2;
    PR3 = 49999; TMR3 = 0;
    IPC3bits.T3IP = 2;
    IEC0bits.T3IE = 1;
    IFS0bits.T3IF = 0;
    T3CONbits.TON = 1;

    OC1CONbits.OCM = 6;
    OC1CONbits.OCTSEL = 1; 
    
    SetPWM(70);

    OC1CONbits.ON = 1;

    while (1) {
        // LATC = LATC & 0x0000 | 0x4000 ;
        LATCbits.LATC14 = PORTDbits.RD0;
    };
    return 0;

}