#include <detpic32.h>

int main(void){

    unsigned int ns = 4;
    TRISBbits.TRISB4 = 1; // RBx digital output disconnected
    AD1PCFGbits.PCFG4= 0; // RBx configured as analog input
    AD1CON1bits.SSRC = 7; // Conversion trigger selection bits: in this
    // mode an internal counter ends sampling and
    // starts conversion
    AD1CON1bits.CLRASAM = 1; // Stop conversions when the 1st A/D converter
    // interrupt is generated. At the same time,
    // hardware clears the ASAM bit
    AD1CON3bits.SAMC = 16; // Sample time is 16 TAD (TAD = 100 ns)
    AD1CON2bits.SMPI = 4-1; // Interrupt is generated after N samples
    // (replace N by the desired number of
    // consecutive samples)
    AD1CHSbits.CH0SA = 4; // replace x by the desired input
    // analog channel (0 to 15)
    AD1CON1bits.ON = 1; // Enable A/D converter
    // This must the last command of the A/D
    // configuration sequence

    TRISB = TRISB & 0x0000 | 0x80F2;
    TRISD = TRISD & 0x0000 | 0xFF9F;    

    while(1) {
        int media = 0;
        
        AD1CON1bits.ASAM = 1; // Start conversion
        while (IFS1bits.AD1IF == 0);// Wait while conversion not done (AD1IF == 0)
        int *p = (int *)(&ADC1BUF0);
        int i;
        printf("\r");
        for( i = 0; i < ns; i++ ) {
            
            media += p[i*4];
        }
        media = media/ns;
        printf("\n media: %.1f", media);

        if(PORTBbits.RB1 == 0){
            LATD = (LATD & 0x0000) | 0x0020;
            LATB = (LATB & 0x0000) | media;
        } else {
            LATD = (LATD & 0x0000) | 0x0040;
            LATB = (LATB & 0x0000) | media;
        }
        
        IFS1bits.AD1IF = 0; // Reset AD1IF
    }
    return 0;
}

// 0x1024 

