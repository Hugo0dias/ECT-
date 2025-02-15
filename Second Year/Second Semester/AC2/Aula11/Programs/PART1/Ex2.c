#include <detpic32.h>

// #define _UART_2_VECTOR 32

int main(void) {
    // Configuração base UART
    U2BRG = 10;
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

void putc(char byte) 
{ 
    while (U2STAbits.UTXBF == 1) { }
    U2TXREG = byte;
}

void _int_(32) isr_uart2(void){
    if(IFS1bits.U2RXIF == 1){
        putc(U2RXREG);
        IFS1bits.U2RXIF = 0;
    }
}
