#include <detpic32.h>

int main(void){

    U2BRG = 10;
    U2MODEbits.BRGH = 0; // taxa de 4

    U2MODEbits.STSEL = 0; // stop
    U2MODEbits.PDSEL = 00; // parity

    U2STAbits.UTXEN = 1; // trans
    U2STAbits.URXEN = 1; // rec

    U2MODEbits.ON = 1; //ligar

    while(1);
    return 0;
}

// baudrate = 115200