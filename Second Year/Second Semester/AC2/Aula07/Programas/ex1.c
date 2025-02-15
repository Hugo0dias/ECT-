#include <detpic32.h>

int main(void) {
    
    TRISBbits.TRISB4 = 1;
    AD1PCFGbits.PCFG4 = 0;
    AD1CON1bits.SSRC = 7;
    AD1CON1bits.CLRASAM = 1; //?
    AD1CON3bits.SAMC = 16; //Sample time is 16 TAD (TAD = 100ns)
    AD1CON2bits.SMPI = 0; // interrupt is generated after 1 sample
    AD1CHSbits.CH0SA = 4; // select analog channel 4
    AD1CON1bits.ON = 1; // Enable AD converter


    IPC6bits.AD1IP = 2;
    IFS1bits.AD1IF = 0;
    IEC1bits.AD1IE = 1;
    EnableInterrupts();
    
    AD1CON1bits.ASAM = 1; // start AD conversion

    while (1) {}
    return 0;
}

void _int_(27) isr_adc(void){

        putChar('\n');
        printInt(ADC1BUF0, 16 | 3 << 16 );
        AD1CON1bits.ASAM = 1; //start AD conversion
        IFS1bits.AD1IF = 0; //reset flag
}   