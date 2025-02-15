
#include <detpic32.h>

void putc(char byte){
    while (U2STAbits.UTXBF == 1){}
    U2TXREG = byte;
}

void putstr(char *str) {
    // use putc() function to send each charater ('\0' should not be sent)
    while (*str != '\0') putc(*str++);
}

void _int_(32) isr_uart2(void){

    if (U2STAbits.OERR == 1){
        U2STAbits.OERR == 0;
    }

    while(U2STAbits.URXDA == 0);

    if (IFS1bits.U2RXIF) {
        char c;
        c = U2RXREG;
        if (c == 'T'){ LATC = (LATC & 0x0000) | 0x4000;}
        else if (c == 't'){ LATC = (LATC & 0x0000) | 0xBFFF; }
        if (c == 'p') {
            putstr("EEEUUUU");
        }
        putc(c);
        IFS1bits.U2RXIF = 0;
    }
}

int main(void) {

    TRISC = (TRISC & 0x0000) | 0xBFFF;
    // Configuração base UART
    U2BRG = ((PBCLK + 8 * 115200) / (16 * 115200)) - 1;
    U2MODEbits.PDSEL = 0;
    U2MODEbits.STSEL = 0;
    U2STAbits.URXEN = 1;
    U2STAbits.UTXEN = 1;
    U2MODEbits.ON = 1;
    // Configuração módulo receção
    U2STAbits.UTXISEL = 0;
    U2STAbits.URXISEL = 0;
    IEC1bits.U2RXIE = 1;
    IEC1bits.U2TXIE = 0;
    IPC8bits.U2IP = 1;
    IFS1bits.U2RXIF = 0;
    
    EnableInterrupts();
    while (1) {};
    return 0;
}

/*
// Configuração do Baud Generator
// baudrate = 115200 = 20000000/16*(U2BRRG + 1)
//
// assim,
//
// U2BRG = ((20000000/115200)/16) - 1 = 10
*/  