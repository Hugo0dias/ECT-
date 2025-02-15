#include <detpic32.h>

void putc(char byte){
    while(U2STAbits.UTXBF == 1)
    U2TXREG = byte;
}

int main(void){

    TRISC = (TRISC & 0x0000) | 0xBFFF;

    // Config UART

    U2BRG = 10;
    U2MODEbits.PDSEL = 00;
    U2MODEbits.STSEL = 0;
    U2STAbits.UTXEN = 1;
    U2STAbits.URXEN = 1;
    U2MODEbits.ON = 1;

    // Config Buffers

    U2STAbits.UTXISEL = 0;
    U2STAbits.URXISEL = 0;
    IEC1bits.U2RXIE = 1;
    IPC8bits.U2IP = 1;
    IFS1bits.U2RXIF = 0;

    EnableInterrupts();
    LATCbits.LATC14 = 0;
    while(1);
}

void _int_(32) isr_uart2(void){
    char input = U2RXREG;
    if(IFS1bits.U2RXIF == 1){
        if(input == 't'){LATCbits.LATC14 = 0; }
        if(input == 'T'){LATCbits.LATC14 = 1; }
        IFS1bits.U2RXIF = 0;
    }
}

// baudrate == 115200
