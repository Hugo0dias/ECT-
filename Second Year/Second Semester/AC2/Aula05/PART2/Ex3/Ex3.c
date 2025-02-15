#include <detpic32.h>

void delay(unsigned int ms){
    resetCoreTimer();
    while(readCoreTimer() < 20000 * ms);
}

void send2display(unsigned char value){

    char disp7Scodes[16] = {0x3f, 0x06, 0x5b, 0x4f, 0x66, 0x6d, 0x7d, 0x07, 0x7f, 0x67, 0x77, 0x7c, 0x39, 0x5e, 0x79 ,0x71};
    static char flag = 0x0001;

    if(!flag){
        LATD = (LATD & 0x0000) | 0xFFDF;
        LATB = (LATB & 0x0000) | (disp7Scodes[value >> 4] << 8);
    } else {
        LATD = (LATD & 0x0000) | 0xFFBF;
        LATB = (LATB & 0x0000) | (disp7Scodes[value & 0x0F] << 8);
    }

    flag = !flag; // 0000 0000 0110 0000
}

unsigned char toBcd(unsigned char value)
{
    return ((value / 10) << 4) + (value % 10);
}

int main(void) {

    TRISD = (TRISD & 0x0000) | 0xFF9F;
    TRISB = (TRISB & 0x0000) | 0x80FF;
    unsigned int i = 0;
    char counter = 0x00;

    while(1){

        while (PORTBbits.RB0 == 0){
            i = 0;
            do {
                send2display(toBcd(counter));
                delay(10);
            } while(++i < 20); //
            counter++;
            counter = counter % 60;
        }
        
        
        while (PORTBbits.RB0 == 1){
            i = 0;
            do {
                send2display(toBcd(counter));
                delay(10);
            } while(++i < 50);
            if (counter == 0){
                counter = 60;
            }
            counter--;
            counter = counter % 60;
        }

        

    }

}
