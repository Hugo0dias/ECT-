#include <stdio.h>
#include <math.h>

int main(void) {

    int m;
    int M;
    int N;
    do{ 
        printf("Valor minimo do angulo (>0): ");
        scanf("%d", &m);
        printf("Valor maximo do angulo (>0): ");
        scanf("%d", &M);
        printf("Insira o espacamento: ");
        scanf("%d", &N);
    } while (N<1 && (M-m) % N != 0);

    printf("ang      sin(ang)       cos(ang)  \n");
    printf("----------------------------------\n");

    for(int i = m; i<=M; i=i+N){ 

        double rad = i * (M / 180);
        printf("%d        %f        %f \n", i, sin(rad), cos(rad));

    }
    
}

//valores do seno e do coseno errados
//escrita num ficheiro