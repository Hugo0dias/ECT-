#include <stdio.h>
#include <math.h>

int main(void) {

    int N;
    do{
        printf("Insira o limite do número: ");
        scanf("%d", &N);
    } while (N<1);

    printf("N       N^2       sqrt(N)  \n");

    for(int i = 0; i<=N; i++){ 

        printf("%d %6d %8f \n ", i, i*i, sqrt(i));

    }
    
}