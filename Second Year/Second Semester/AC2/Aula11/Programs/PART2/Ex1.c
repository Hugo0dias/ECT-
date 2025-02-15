#include<detpic32.h>

typedef struct
{
    char mem[100];
    int nchar; // numero de char para transmitir
    int posrd; // posi'cao do proximo char
} t_buf;

volatile t_buf txbuf;

void putstrInt(char *s){
    while(txbuf.nchar > 0){};
    for (; *s != '\0'; s++){
        txbuf.mem[txbuf.nchar++] = *s;
    }
    txbuf.posrd = 0;
    IEC1bits.U2TXIE = 1;
}

void _int_(32) isr_uart2(void){
    if (IFS1bits.U2TXIF == 1){
        if(txbuf.nchar > 0){
            U2TXREG = txbuf.mem[txbuf.posrd];
            txbuf.nchar--;
            txbuf.posrd++;
        } else {
            IEC1bits.U2TXIE = 0;
        }
        IFS1bits.U2TXIF = 0;
    }
}

int main(void){
    
    U2BRG = 10;
    U2MODEbits.PDSEL = 00;
    U2MODEbits.STSEL = 0;
    U2MODEbits.ON = 1;
    U2STAbits.UTXEN = 1;
    U2STAbits.URXEN = 1;

    IEC1bits.U2TXIE = 0;
    IPC8bits.U2IP = 1;
    U2STAbits.UTXISEL = 00;
    IFS1bits.U2TXIF = 0;

    EnableInterrupts();
    txbuf.nchar = 0;

    while(1){
        putstrInt("Esta String tem muitos e muitos carateres \n");
    };
    return 0;
}