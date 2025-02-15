#include <detpic32.h>

#define freq 1/250

volatile int voltage = 0;

void delay( unsigned int ms ) {

    resetCoreTimer();
    while(readCoreTimer() < 20000 * ms);

}

void sendtodisplay(unsigned int value) {

    TRISB = (TRISB & 0x0000) | 0x80FF;
    TRISD = (TRISD & 0x0000) | 0xFF9F;

    unsigned char flag = 1;

    char disp7Scodes[16] = {0x3f, 0x06, 0x5b, 0x4f, 0x66, 0x6d, 
    0x7d, 0x07, 0x7f, 0x67, 0x77, 0x7c, 0x39, 0x5e, 0x79 ,0x71};

    if (flag == 1) {
        LATD = (LATD & 0x0000) | 0x0040 ; 
        LATB = (LATB & 0x0000) | disp7Scodes[ value >> 4 ] << 8;
        flag = 0;
    } else if (flag == 0) {
        LATD = (LATD & 0x0000) | 0x0020 ; 
        LATB = (LATB & 0x0000) | disp7Scodes[ value & 0x0F ] << 8;
        flag = 1;
    }

}

int toBCD(unsigned int value) {

    return ((value / 10) << 4) + (value % 10); 

}

void _int_(27) usr_adc(void){

    AD1CON1bits.ASAM = 1; // inicio da conversao da ADC
    IFS1bits.T5IF = 0;  // reset na flag

}

void _int_(32) usr_t5(void){

    voltage = 0;
    int *p = (int *) (&ADC1BUF0);
    voltage = (((p[0] + p[4] + p[12] + p[16]) / 4) * 20 + 511) / 1023;
    IFS1bits.AD1IF = 0; // reset na flag

}

int main(void){

    // conf Timer5

    T5CONbits.TCKPS = 4; // 1:16 prescaler (i.e Fout_presc = 625 KHz)
    PR5 = 49999; // Fout = 20MHz / (32 * (62499 + 1)) = 10 Hz
    TMR5 = 0; // Reset timer T2 count register
    T5CONbits.TON = 1; // Enable timer T2 (must be the last command of the
                       // timer configuration sequence)
    
    // conf ADC

    TRISBbits.TRISB4 = 1;   // RBx digital output disconnected
    AD1PCFGbits.PCFG4= 0;   // RBx configured as analog input
    AD1CON1bits.SSRC = 7;   // Conversion trigger selection bits: in this
                            // mode an internal counter ends sampling and
                            // starts conversion
    AD1CON1bits.CLRASAM = 1;    // Stop conversions when the 1st A/D converter
                                // interrupt is generated. At the same time,
                                // hardware clears the ASAM bit
    AD1CON3bits.SAMC = 16; // Sample time is 16 TAD (TAD = 100 ns)

    AD1CON2bits.SMPI = 3; // Interrupt is generated after N samples
                            // (replace N by the desired number of
                            // consecutive samples)
    AD1CHSbits.CH0SA = 4;   // replace x by the desired input
                            // analog channel (0 to 15)
    AD1CON1bits.ON = 1; // Enable A/D converter
                        // This must the last command of the A/D
                        // configuration sequence

    // conf interrupt ADC

    IPC6bits.AD1IP = 4; // configure priority of A/D interrupts
    IFS1bits.AD1IF = 0; // clear A/D interrupt flag
    IEC1bits.AD1IE = 1; // enable A/D interrupts


    // conf interrupt Timer

    IPC5bits.T5IP = 2; // configure priority of A/D interrupts
    IFS0bits.T5IF = 0; // clear A/D interrupt flag
    IEC0bits.T5IE = 1; // enable A/D interrupts
      

    EnableInterrupts();
    while(1){
        sendtodisplay(toBCD(voltage)); // manda para o display
        delay(freq); // refrescamento dos displays
    }
    
    return 0;

}
