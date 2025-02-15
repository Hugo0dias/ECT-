#include <detpic32.h>

void delay(unsigned int ms){
    resetCoreTimer();
    while(readCoreTimer() < 20000 * ms);
}

void putc(char byte){
    while (U2STAbits.UTXBF == 1){}
    U2TXREG = byte; // registo para escrever
}

void putstr(char *str){
    for (; *str != '\0'; str++) {
        putc(*str);
    }
}

char getc(void){
    while(U2STAbits.URXDA == 0){}
    return U2RXREG; // ler registo
}

int main(void){

    U2BRG = 10; 
    U2MODEbits.BRGH = 0; // taxa de 4

    U2MODEbits.STSEL = 0; // stop
    U2MODEbits.PDSEL = 00; // parity

    U2STAbits.UTXEN = 1; // trans
    U2STAbits.URXEN = 1; // rec

    U2MODEbits.ON = 1; // ligar

    while(1){
        char x = getc();
        
        putc(x);
        
    }
    return 0;
}

// baudrate = 115200