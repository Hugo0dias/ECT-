#include<detpic32.h>

#define islower(c) ((c) >= 'a' && (c) <= 'z')
#define isNotDigit(c) ((c) < '0' || (c) > '9')

volatile unsigned char count = 0;

void putc(char byte){
    while (U2STAbits.UTXBF == 1){}
    U2TXREG = byte; 
}

void putstr(char *c) {
    while(*c != '\0'){
        putc(*c++);
    } 
}

void _int_(32) isr_uart2(void){
    char c;
    c = U2RXREG;
    if(IFS1bits.U2RXIF == 1){
        putc(c);
        if (c == '\n') {
            putc('\n');
            putstr("O número de minúsculas digitado foi ");
            putc(count);
            printInt(count, 10);
            putc('\n');
            count = 0;
        } else if (islower(c) & isNotDigit(c)) {
            count++;
        }
        
        IFS1bits.U2RXIF = 0;
    }
}

int main(void){

    // conf UARTR

    U2BRG = ((PBCLK + 8 * 115200) / (16 * 115200)) - 1;
    U2MODEbits.PDSEL = 1;
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

    while(1){}
    return 0;

}

// usar o enter
// print no count sem system calls

// 10 = 8-bit data, odd parity
// 01 = 8-bit data, even parity
// 00 = 8-bit data, no parity
