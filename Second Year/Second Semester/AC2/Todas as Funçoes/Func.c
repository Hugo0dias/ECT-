void delay(float tempo_s){

    float tempo = PBCLK*tempo_s;
    resetCoreTimer();
    while(readCoreTimer() <= (int)tempo);

}

char disp7Scodes[16] = {0x3f, 0x06, 0x5b, 0x4f, 0x66, 0x6d, 0x7d, 0x07, 0x7f, 0x67, 0x77, 0x7c, 0x39, 0x5e, 0x79 ,0x71};

void send2displays(unsigned char value){

    char disp7Scodes[16] = {0x3f, 0x06, 0x5b, 0x4f, 0x66, 0x6d, 0x7d, 0x07, 0x7f, 0x67, 0x77, 0x7c, 0x39, 0x5e, 0x79 ,0x71};    
    static char displayFlag = 0x0000;

    if (displayFlag == 0){
        LATD = (LATD & 0x0000) | 0x0040; // disp high
        LATB = ((LATB & 0x0000)) | (disp7Scodes[value >> 4] << 8);
    } else if (displayFlag == 1) {
        LATD = (LATD & 0x0000) | 0x0020; // disp low
        LATB = ((LATB & 0x0000)) | (disp7Scodes[value & 0x0F] << 8);
    }

    displayFlag = displayFlag ^ 0x0001;

}

void putc(char byte) 
{ 
    while (U2STAbits.UTXBF == 1) { }
    U2TXREG = byte;
}

unsigned char toBcd(unsigned char value)
{
    return ((value / 10) << 4) + (value % 10);
}

void putc(char byte) {
    // wait while UTXBF == 1
    while (U2STAbits.UTXBF == 1);
    // Copy byte to the UxTXREG register
    U2TXREG = byte;
}

void putstr(char *str) {
    // use putc() function to send each charater ('\0' should not be sent)
    while (*str != '\0') putc(*str++);
}
