#include <detpic32.h>


volatile unsigned char count = 0;

typedef struct
{
    char mem[100];
    int nchar; // numero de char para transmitir
    int posrd; // posi'cao do proximo char
} t_buf;

volatile t_buf txbuf;

void putc(char byte){
    while (U2STAbits.UTXBF == 1);
    U2TXREG = byte;
}

void putStrInt(char *s) {
    while (txbuf.nchar > 0) {};
    for (; *s != '\0'; s++) {
        txbuf.mem[txbuf.nchar++] = *s;
    }
    txbuf.posrd = 0;
    IEC1bits.U2TXIE = 1;
}


void _int_(32) isr_uart2(void){
    unsigned char c = U2RXREG;
    putc(c);
}

int main(void){

    // conf UARTR

    U2BRG = 130;
    U2MODEbits.BRGH = 0;
    U2MODEbits.PDSEL = 01;
    U2MODEbits.STSEL = 0;
    U2MODEbits.ON = 1;
    
    // conf Inter

    U2STAbits.URXEN = 1;
    U2STAbits.UTXEN = 1;
    U2STAbits.URXISEL = 0;
    U2STAbits.UTXISEL = 0;
    IFS1bits.U2RXIF = 1;
    IEC1bits.U2RXIE = 1;
    IPC8bits.U2IP = 1;

    EnableInterrupts();

    while(1){}
    return 0;

}