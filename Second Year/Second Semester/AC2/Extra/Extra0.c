			#include <detpic32.h>

// Semaforo simulator :

// 3 estados (vermelho (RE0) | Amarelo (RE1) | Verde (RE2))
		
	// Vermelho dura 10 segundos
	// Amarelo dura 7 segundos
	// Verde 15 segundos
	// 
	// Aplicar uma contagem decrescente com timer de interrupcao e sem timer de interrupcao
	// 
	// Apos aplicar butao (RD8), passado 5 segundos passa para vermelho e liga o led(RC14) Durante 10 segundos

unsigned char toBcd(unsigned char value)
{
    return ((value / 10) << 4) + (value % 10);
}

void SendToDisplay(unsigned char value) {
    static const char disp7Scodes[] = {
        0x3F, 0x06, 0x5B, 0x4F,
        0x66, 0x6D, 0x7D, 0x07,
        0x7F, 0x6F, 0x77, 0x7C,
        0x39, 0x5E, 0x79, 0x71
    };
    static char displayFlag = 1; // 1 for HI-D, 0 for LO-D

    if (displayFlag) {
        LATD = ( LATD & 0xFF9F ) | 0x0040; // Activate HI-D, disable LO-D
        LATB = ( LATB & 0x80FF ) | disp7Scodes[value >> 4] << 8; // Write the values of RB8-RB14
        displayFlag = 0;
    } else {
        LATD = ( LATD & 0xFFBF ) | 0x0020; // Disable HI-D, activate LO-D
        LATB = ( LATB & 0x80FF ) | disp7Scodes[value & 0x0F] << 8; // Write the values of RB8-RB14
        displayFlag = 1;
    }
}

void delay(unsigned int ms){
    resetCoreTimer();
    while(readCoreTimer() < ms * 20000);
}



int main() {

    TRISB = (TRISB & 0x0000) | 0x80FF ;
    TRISD = (TRISD & 0x0000) | 0xFF9F ;
    TRISE = (TRISE & 0x0000) | 0xFFF0 ;
    TRISC = (TRISC & 0x0000) | 0xBFFF ;

    unsigned int StateChange = 0;
    unsigned int freq = 0;
    unsigned char counter = 0;
    unsigned int flag = 0;
    unsigned int PassDelay = 0;

    while(1){
        freq = 0;
        if (StateChange == 0) { // quando vermelho
            
            do {
                LATE = (LATE & 0x0000) | 0x0001 ;
                SendToDisplay(toBcd(counter));
                delay(10);
                printInt(counter, 10);
                if (PORTDbits.RD8 == 0){
                    flag = 1;
                }
            } while (++freq < 100);

            counter ++;
            if(flag == 1){
                PassDelay++;
            }

            printInt(counter, 10);

            if (PassDelay == 5){
                PassDelay = 0;
                counter = 0;
                StateChange = 4;
            }

            if(counter == 11){
                StateChange = 1;
                counter = 0;
            }
        } else if (StateChange == 1) {
            
            do {
                LATE = (LATE & 0x0000) | 0x0002 ;
                SendToDisplay(toBcd(counter));
                delay(10);
                printInt(counter, 10);
                if (PORTDbits.RD8 == 0){
                    flag = 1;
                }
            } while (++freq < 100);

            counter ++;
            if(flag == 1){
                PassDelay++;
            }
            printInt(counter, 10);

            if (PassDelay == 5){
                PassDelay = 0;
                counter = 0;
                StateChange = 4;
            }

            if(counter == 8){
                StateChange = 2;
                counter = 0;
            }
            
        } else if (StateChange == 2) {
           
            do {
                LATE = (LATE & 0x0000) | 0x0004 ;
                SendToDisplay(toBcd(counter));
                delay(10);
                printInt(counter, 10);
                if (PORTDbits.RD8 == 0){
                    flag = 1;
                }
            } while (++freq < 100);

            counter ++;
            if(flag == 1){
                PassDelay++;
            }
            printInt(counter, 10);

            if (PassDelay == 5){
                PassDelay = 0;
                counter = 0;
                StateChange = 4;
            }

            if(counter == 16){
                StateChange = 0;
                counter = 0;
            }
        } else if (StateChange == 4) {
            flag = 0;
            do {
                LATE = (LATE & 0x0000) | 0x0008 ;
                LATC = (LATC & 0x0000) | 0x4000 ;
                SendToDisplay(toBcd(counter));
                delay(10);
                printInt(counter, 10);
            } while (++freq < 100);

            counter ++;
            printInt(counter, 10);

            if(counter == 18){
                LATC = (LATC & 0x0000) | 0x0000 ;
                StateChange = 2;
                counter = 0;
            }

        }
    }
    return 0;
}
