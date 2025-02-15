#include <detpic32.h>
int main(void) {

    while(1){
    TRISE = TRISE & 0x0000 | 0xFFFB;
    LATE = LATE & 0x0000 | 0xFFFF;
    }
}