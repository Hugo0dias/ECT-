#include <detpic32.h>



int main(void){

    T3CONbits.TCKPS = 7; // 1:256 prescaler (i.e. fout_presc = 625 KHz)
    PR3 = 39062;// Fout = 20MHz / (32 * (62499 + 1)) = 10 Hz
    TMR3 = 0;   // Clear timer T2 count register
    T3CONbits.TON = 1;  // Enable timer T2 (must be the last command of the
                        // timer configuration sequence)
    IPC3bits.T3IP = 2;
    IEC0bits.T3IE = 1;
    IFS0bits.T3IF = 0;
    // Interrupt priority (must be in range [1..6])
    // Enable timer T2 interrupts
    // Reset timer T2 interrupt flag
    
    EnableInterrupts();
    while(1){}

    
    return 0;
}

void _int_(_TIMER_3_VECTOR)isr_T3(void) {
    unsigned int count = 0;
    if(count == 0){
        putChar('.');
    }
    count = (count+1) % 2
    IFS0bits.T3IF = 0;
}