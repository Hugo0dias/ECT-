#include <detpic32.h>

volatile unsigned char voltage = 0;

void delay(unsigned int ms){

    resetCoreTimer();
    while(readCoreTimer() < ms * 20000);
}

unsigned char toBcd(unsigned char value)
{
    return ((value / 10) << 4) + (value % 10);
}

void send2display(unsigned char c){

    char disp7Scodes[16] = {0x3f, 0x06, 0x5b, 0x4f, 0x66, 0x6d, 0x7d, 0x07, 0x7f, 0x67, 0x77, 0x7c, 0x39, 0x5e, 0x79 ,0x71};    

    TRISD = (TRISD & 0x0000) | 0xFF9F;
    TRISB = (TRISB & 0x0000) | 0x80FF;

    static char flag = 0;

    if (flag == 0) {
        LATD = (LATD & 0x0000) | 0x0040;
        LATB = (LATB & 0x0000) | (disp7Scodes[c >> 4] << 8);
        flag = 1;
    } else {
        LATD = (LATD & 0x0000) | 0x0020;
        LATB = (LATB & 0x0000) | (disp7Scodes[c & 0x0F] << 8);
        flag = 0;
    }


}

int main(void){

    // configurar tudo ADC

    TRISBbits.TRISB4 = 1;
    AD1PCFGbits.PCFG4= 0;
    AD1CON1bits.SSRC = 7;
    AD1CON1bits.CLRASAM = 1; // Stop conversions when the 1st A/D converter
    AD1CON3bits.SAMC = 16;
    AD1CON2bits.SMPI = 7; // Interrupt is generated after N samples
    AD1CHSbits.CH0SA = 4;
    AD1CON1bits.ON = 1;

    // configurar interrupts ADC

    IFS1bits.AD1IF = 0;
    IEC1bits.AD1IE = 1;
    IPC6bits.AD1IP = 1;

    // configurar tudo TIMERs

    T1CONbits.TCKPS = 2; 
    PR1 = 62499;
    TMR1 = 0;
    T1CONbits.TON = 1;

    /*  fout = 5Hz
        k = 20000000/(5*65536)
        k = 64
        prx = (20000000/(5*64))-1
    */
    // ---------------

    T3CONbits.TCKPS = 2; 
    PR3 = 49999;
    TMR3 = 0;
    T3CONbits.TON = 1;

    /*
        k = 4
        prx = 49999
    */
    // configurar interrupts TIMERs

    IPC1bits.T1IP = 1;
    IEC0bits.T1IE = 1;
    IFS0bits.T1IF = 0;

    // -------------------

    IPC3bits.T3IP = 2;
    IEC0bits.T3IE = 1;
    IFS0bits.T3IF = 0;

    EnableInterrupts();
    // start A/D conversion
    
    while(1);
    return 0;

}


void _int_(27) isr_adc(void) {

    //read conversion result (ADC1BUF0 e printit)
    int *p = (int *) (&ADC1BUF0);
    voltage = (((p[0] + p[4] + p[8] + p[12] + p[16] + p[20] + p[24] + p[28]) / 8) * 33 + 511) / 1023; 
    IFS1bits.AD1IF = 0;
}

void _int_(12) isr_T3(void){

    send2display(toBcd(voltage));
    IFS0bits.T3IF = 0;

}

void _int_(4) isr_T1(void){

    AD1CON1bits.ASAM = 1;
    IFS0bits.T1IF = 0;

}