#include <detpic32.h>

volatile static int counter; 

void putc( char c) {

    while(U2STAbits.UTXBF == 1);
    U2TXREG = c;

}

unsigned char toBcd(unsigned char value)
{
    return ((value / 10) << 4) + (value % 10);
}


void printstr(char *c) {

    while (*c != '\0'){
        putc(*c);
        c++;
    }
}

int main(void){

    U2BRG = 520;

    U2MODEbits.BRGH = 0;

    U2MODEbits.PDSEL = 1;

    U2MODEbits.STSEL = 1;

    U2STAbits.UTXEN = 1;
    U2STAbits.URXEN = 1;

    U2MODEbits.ON = 1;

    IPC8bits.U2IP = 2; // Interrupt priority (must be in range [1..6])
    IEC1bits.U2RXIE = 1; // Enable timer U2 interrupts
    IFS1bits.U2RXIF = 0; // Reset timer U2 interrupt flag

    TRISE = TRISE & 0xfff0;

    EnableInterrupts();
    
    counter = 0;


    while(1){}
    return 0;
}


void _int_(32) isr_uart2(void){

    if (U2STAbits.OERR == 1)
        U2STAbits.OERR = 0;

    if (IFS1bits.U2RXIF){

        while(U2STAbits.URXDA == 0);

        char c;
        c = U2RXREG;
        
        if (c == 'F') {
            counter = (counter +1) % 10;
            LATE = (LATE & 0xfff0) | counter;
            char v = '0' + counter;
            putc(v);  
        } else if (c == '~') {
            counter = 0;
            printstr("VALOR MINIMO");
            LATE = (LATE & 0xfff0) | counter;
        }
    
        IFS1bits.U2RXIF = 0;
        
    }

    

}