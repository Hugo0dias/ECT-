#include <detpic32.h>

int main(void){

        T3CONbits.TCKPS = 7; // 1:256 prescaler (i.e. fout_presc = 625 KHz)
        PR3 = 39062;// Fout = 20MHz / (32 * (62499 + 1)) = 10 Hz
        TMR3 = 0;   // Clear timer T2 count register
        T3CONbits.TON = 1;  // Enable timer T2 (must be the last command of the
                            // timer configuration sequence)
        
        IEC0bits.T3IE = 1;
        IFS0bits.T3IF = 0;
        // Interrupt priority (must be in range [1..6])
        // Enable timer T2 interrupts
        // Reset timer T2 interrupt flag

        unsigned int count = 0;
    while(1){
        
        // Wait while T3IF = 0
        while (IFS0bits.T3IF == 0){}
        // Reset T3IF
        putChar('\r');
        printInt(count, 16);
        count++;
        IFS0bits.T3IF = 0;
    }
    return 0;
}