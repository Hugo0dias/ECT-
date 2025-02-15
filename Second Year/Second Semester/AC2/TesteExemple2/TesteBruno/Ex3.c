#include <detpic32.h>

volatile char count = 0;
void putc(char byte){
    while(U2STAbits.UTXBF);             // registo transmissao cheio
    U2TXREG = byte;                     // copia o valor para o registo de transmissao
}

void putsrt(char *c){   // imprime string
    while(*c != '\0'){
        putc(*c);
        c++;
    }
}

void _int_(32) usr_uart2(void){

    if (U2STAbits.OERR == 1) {  // verificar erro de overrun
        U2STAbits.OERR =0;
    }
    while(U2STAbits.URXDA == 0);   // espera enquanto nao esta disponivel para receber carateres
    
    char c;
    c = U2RXREG; // copia o valor recebido Reg de receção para uma variavel
    if ( c == 'D') {
        LATE = (LATE & 0x0000) | (count << 2); // liga leds
        count = (count - 1) % 16 ; // contador decrescente modulo 16
    } else if (c == 'R') {
        count = 15; // reseta o valor do counter para o valor maximo
        LATE = (LATE & 0x0000) | (count << 2); // liga leds
        putsrt("MAXIMO");
    }

    IFS1bits.U2RXIF = 0;

}

int main(void){

    TRISE = (TRISE & 0x0000) | 0xFFC3;

    // conf UART 

    U2BRG = ((PBCLK + (8 * 38400)) / (16 * 38400)) - 1;
    U2MODEbits.BRGH = 0;
    U2MODEbits.PDSEL = 1; // paridade
    U2MODEbits.STSEL = 1; // stop bits

    U2STAbits.UTXEN = 1;
    U2STAbits.URXEN = 1;

    // interrupt UART

    U2STAbits.UTXISEL = 0;
    U2STAbits.URXISEL = 0;

    IPC8bits.U2IP = 2; // configure priority of UART interrupts
    IEC1bits.U2TXIE = 1; // enable UART RX interrupts
    IEC1bits.U2RXIE = 0; // disable UART TX interrupts

    U2MODEbits.ON = 1;

    EnableInterrupts();

    while(1){}

    return 0;

}
