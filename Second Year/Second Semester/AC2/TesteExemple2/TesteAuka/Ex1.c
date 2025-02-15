#include <detpic32.h>

void delay(unsigned int ms){
    resetCoreTimer();
    while(readCoreTimer()<20000 * ms);
}

void setDutyCycle(unsigned int dc){
    OC3RS = ((PR3 + 1) * dc) / 100;
}

int main(void){
    TRISB = TRISB | 0x0006;

    T3CONbits.TCKPS = 2;
    PR3 = 38461;
    TMR3 = 0;
    T3CONbits.TON = 1;

    OC4CONbits.OCM = 7;
    OC4CONbits.OCTSEL =1;
    setDutyCycle(50);
    OC4CONbits.ON = 1;
    
    while(1){
        value = (PORTB & 0x0002);
        if(value == 0 && state == 0){
            while(PORTBbits.RB2 == 1);
            setDutyCycle(25);
        } else if(value == 0 && state == 1){
            setDutyCycle(75);
        }
        state = !state;
        delay(1300);
        if (value == 2) {
            if (state == 0) {
                setDutyCycle(25);
            } else if (state == 1) {
                setDutyCycle(75);
            }
        }
    }

    return 0;
}

// 0000 0001 0010 0011 0100 0101 0110 0111 1000 1001 1010 1011 1100 1101 1110 1111
//  0     1    2    3    4    5    6    7    8   9    A    B    C     D    E    F

//RB2 e RB0
//0000 0000 0000 0011       0x0003

//Prescaler = 20000000 / (130*65536) = 2.34750601
//Prescaler = 4 => 2
//PR3 = (20000000 / 4) / 130 = 38461.538461538