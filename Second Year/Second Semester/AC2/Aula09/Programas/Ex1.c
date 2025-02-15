#include <detpic32.h>

volatile int voltage;

unsigned char toBcd(unsigned char value)
{
    return ((value / 10) << 4) + (value % 10);
}

void send2displays(unsigned char value) {
    static const char disp7Scodes[] = {
        0x3F, 0x06, 0x5B, 0x4F,
        0x66, 0x6D, 0x7D, 0x07,
        0x7F, 0x6F, 0x77, 0x7C,
        0x39, 0x5E, 0x79, 0x71
    };
    static char displayFlag = 1; // 1 for HI-D, 0 for LO-D

    if (displayFlag) {
        LATD = ( LATD & 0xFF9F ) | 0x0040; // Activate HI-D, disable LO-D
        LATB = ( LATB & 0x80FF ) | disp7Scodes[value >> 4] << 8; // Write the values of RB8-RB14
        displayFlag = 0;
    } else {
        LATD = ( LATD & 0xFFBF ) | 0x0020; // Disable HI-D, activate LO-D
        LATB = ( LATB & 0x80FF ) | disp7Scodes[value & 0x0F] << 8; // Write the values of RB8-RB14
        displayFlag = 1;
    }
}

void _int_(27) isr_adc(void){

        int average = 0;
        int *p = (int *)(&ADC1BUF0);
        average = (p[0] + p[4] + p[8] + p[12] + p[16] + p[20] + p[24] + p[28]) / 8;
        voltage = (average * 33 + 511) / 1023, 10;
        AD1CON1bits.ASAM = 0; 
        IFS1bits.AD1IF = 0; //reset flag

}   

void _int_(4) isr_T1(void)
{
    AD1CON1bits.ASAM = 1;  // começa a conversão   
    IFS0bits.T1IF = 0;

}

void _int_(12) isr_T3(void)
{
    send2displays(toBcd(voltage));
    IFS0bits.T3IF = 0;
}

int main(void) {

    // Display configuration
    TRISD = TRISD & 0xFF9F; // Mark RD6 - RD5 as outputs
    TRISC = TRISC & 0xBFFF; // Mark RC14 as output
    TRISB = ( TRISB & 0x80FF ); // Mark RB14 - RB8 as outputs

    // Configure ADC
    TRISBbits.TRISB4 = 1;
    AD1PCFGbits.PCFG4 = 0;
    AD1CON1bits.SSRC = 7;
    AD1CON1bits.CLRASAM = 1;
    AD1CON3bits.SAMC = 16;
    AD1CON2bits.SMPI = 7;
    AD1CHSbits.CH0SA = 4;
    AD1CON1bits.ON = 1;

    IPC6bits.AD1IP = 2;
    IEC1bits.AD1IE = 1;
    IFS1bits.AD1IF = 0;

    // Timer 1

    T1CONbits.TCKPS = 2; // 1:64 prescaler (i.e. fout_presc = 625 KHz)
    PR1 = 62499;
    TMR1 = 0;
    T1CONbits.TON = 1;
    
    IPC1bits.T1IP = 2;
    IEC0bits.T1IE = 1;
    IFS0bits.T1IF = 0;

    // Timer 3

    T3CONbits.TCKPS = 2; // 1:32 prescaler (i.e. fout_presc = 625 KHz)
    PR3 = 49999;
    TMR3 = 0;

    IPC3bits.T3IP = 2;
    IEC0bits.T3IE = 1;
    IFS0bits.T3IF = 0;

    T3CONbits.TON = 1;
    T1CONbits.TON = 1;
    
    ////////////////////////////////////

    EnableInterrupts();
    while (1);
    return 0;
}
