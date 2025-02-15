#include <detpic32.h>

int main(void) {
    
    TRISBbits.TRISB4 = 1;
    AD1PCFGbits.PCFG4 = 0;
    AD1CON1bits.SSRC = 7;
    AD1CON1bits.CLRASAM = 1;
    AD1CON3bits.SAMC = 16;
    AD1CON2bits.SMPI = 0;
    AD1CHSbits.CH0SA = 4;
    AD1CON1bits.ON = 1;
    TRISDbits.TRISD11 = 0;


    while (1) {

        PORTDbits.RD11 = 1;
        AD1CON1bits.ASAM = 1; //ordem de inicio de conversao 
        while (IFS1bits.AD1IF == 0);
        PORTDbits.RD11 = 0;
        putChar('\n');
        printInt(ADC1BUF0, 16 | 3 << 1  6);
        IFS1bits.AD1IF = 0; // esperar que o sinal que indica o fim de conversão fique ativo 
        AD1CON1bits.ASAM = 0; // fim da conversão 
                    
    }
    
    return 0;
}   