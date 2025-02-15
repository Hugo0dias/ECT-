#include <detpic32.h>

void delay(unsigned int ms);

int main(void) {
    LATDbits.LATD5 = 1; // Enable LO-D
    LATDbits.LATD6 = 0; // Disable HI-D
    TRISD = TRISD & 0xFF9F; // 1111 1111 1001 1111
    TRISB = TRISB & 0x80FF; // 1000 0000 1111 1111
    while (1) {
        char usr_input = getChar(); putChar(usr_input);
        switch (usr_input)
        {
        case 0x41: // A
        case 0x61: // a
            LATB = (LATB & 0x80FF) | 0x7700; // 0111011100000000 | 1000 0000 1111 1111
            break;
        case 0x42: // B
        case 0x62: // b
            LATB = (LATB & 0x80FF) | 0x7F00; // 0000001000000000 | 1000 0000 1111 1111
            break;
        case 0x43: // C
        case 0x63: // c
            LATB = (LATB & 0x80FF) | 0x3900; // 0011100100000000
            break;
        case 0x44: // D
        case 0x64: // d
            LATB = (LATB & 0x80FF) | 0x3F00; // 0000100000000000
            break;
        case 0x45: // E
        case 0x65: // e
            LATB = (LATB & 0x80FF) | 0x7900; // 0001000000000000
            break;
        case 0x46: // F
        case 0x66: // f
            LATB = (LATB & 0x80FF) | 0x7100; // 0111000000000000
            break;
        case 0x47: // G
        case 0x67: // g
            LATB = (LATB & 0x80FF) | 0x7D00; // 0100000000000000
            break;
        // case enter key
        case 0x0A: // enter
            putChar('s');
            LATDbits.LATD5 = !LATDbits.LATD5;
            LATDbits.LATD6 = !LATDbits.LATD6;
        default:
            break;
        }
    }
    return 0;
}

void delay(unsigned int ms) {
    resetCoreTimer();
    while(readCoreTimer() < 20000 * ms);
}