#include <stdio.h>
#include <math.h>

#include <stdio.h>

void factorial(){

    int fact = 1;
    int i;
    int array[10];
    int index;

    for (int a = 0; a<10; a++){
        if(a == 0) {
            fact = 1;
            array[a] = fact;
        } else if (a == 1) {
            fact = 1;
            array[a] = fact;
        } else {
            for(index=a; index >= 1 ;index--){
                fact=fact*index;
                array[a] = fact;
            }
        }
    }





    for (i = 0; i < 10; i++)
        printf("%d ", array[i]);
    printf("\n");


}
/* A funçao fatorial calcula os fatoriais até 9 e forma um array que e possivel aceder  */

void factoriao(){


}



int main(){

    factorial();

    return 0;
}