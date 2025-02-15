#include <detpic32.h>



int main(void){

    T3CONbits.TCKPS = 4; // 1:256 prescaler (i.e. fout_presc = 625 KHz)
    PR3 = 49999;// Fout = 20MHz / (32 * (62499 + 1)) = 10 Hz
    TMR3 = 0;   // Clear timer T2 count register
    T3CONbits.TON = 1;  // Enable timer T2 (must be the last command of the
                        // timer configuration sequence)1
    T1CONbits.TCKPS = 2; // 1:256 prescaler (i.e. fout_presc = 625 KHz)
    PR1 = 62499;// Fout = 20MHz / (32 * (62499 + 1)) = 10 Hz
    TMR1 = 0;   // Clear timer T2 count register
    T1CONbits.TON = 1;  // Enable timer T2 (must be the last command of the
                        // timer configuration sequence)

    
    IPC3bits.T3IP = 2;
    IEC0bits.T3IE = 1;
    IFS0bits.T3IF = 0;
    // Interrupt priority (must be in range [1..6])
    // Enable timer T2 interrupts
    // Reset timer T2 interrupt flag
    
    IPC1bits.T1IP = 1;
    IEC0bits.T1IE = 1;
    IFS0bits.T1IF = 0;
    // Interrupt priority (must be in range [1..6])
    // Enable timer T2 interrupts
    // Reset timer T2 interrupt flag

    EnableInterrupts();
    while(1);
    return 0;
}

void _int_(4)isr_T1(void) {
    putChar('r');
    IFS0bits.T1IF = 0;
}

void _int_(12)isr_T3(void) {
    putChar('h');
    IFS0bits.T3IF = 0;
}

