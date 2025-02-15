#include <detpic32.h>

void configUART(){
    U2MODEbits.BRGH = 0;
    U2BRG = 129;
    U2MODEbits.PDSEL = 2;
    U2MODEbits.STSEL = 1;
    U2STAbits.UTXEN = 1;
    U2STAbits.URXEN = 1;
    U2MODEbits.ON = 1;

    IEC1bits.U2RXIE = 1;
    IPC8bits.U2IP = 2;
    IFS1bits.U2RXIF = 0;
    IFS1bits.U2TXIF = 0;
    IFS1bits.U2EIF = 0;
    EnableInterrupts();
}

void putc(char c){
    while(U2STAbits.UTXBF == 1);
    U2TXREG = c;
}

void puts(char *str){
    while(*str){
        putc(*str++);
    }
}

char getc(void){
    if(U2STAbits.OERR == 1){
        U2STAbits.OERR = 0;
    }
    while(U2STAbits.URXDA == 0);
    return U2RXREG;
}

volatile int count = 0;

int main(void){
    configUART();
    TRISE = TRISE & 0xFF7F;
    TRISB = TRISB & 0x0009;
    while(1);

    return 0;
}

void int(32) uart(void){
    if(IFS1bits.U2RXIF){
        char c;
        c = U2RXREG;
        putc(c); 
        puts("\n");
        if(c == 'D'){
            PORTEbits.RB7 != PORTEbits.RB7;
            puts("SDS=\n");
        }
    }
    IFS1bits.U2RXIF = 0;
}

Pterm 9600,o,8,2