#include <detpic32.h>

int main(void) {
    // Configure OC1 as output
    // TRISDbits.TRSID0 = 0;

    // T3 configuration
    T3CONbits.TCKPS = 2;
    PR3 = 49999; TMR3 = 0;
    IPC3bits.T3IP = 2;
    IEC0bits.T3IE = 1;
    IFS0bits.T3IF = 0;
    T3CONbits.TON = 1;

    OC1CONbits.OCM = 6;
    OC1CONbits.OCTSEL = 1; 
    OC1RS = 12500;
    OC1CONbits.ON = 1;

    while (1) {};
    return 0;
}

/* ======== TIMER 3 (1,2,4,8,16,32,64,256) ========
// Quero obter uma frequência de 100Hz
// PRx   = (f_in / f_out) - 1
//       = (5000000 / 100) - 1 = 49999 < 65535
// 
// f_in  = 20*10^6 / 4 = 5000000
// f_out = 100Hz
//
// Então, pre_scaler = 4  e couter = 49999
//
// t_on = 0.25 * (1/100) = 0.0025 s
// f_out_prescaler = 20000000 / 4 = 5000000
// t_out_prescaler = 1 / 5000000 = 2*10^-7
// OC3RS = 5000000 / 2*10^-7 = 12500
*/