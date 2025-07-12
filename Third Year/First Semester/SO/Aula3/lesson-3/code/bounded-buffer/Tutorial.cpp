#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <unistd.h>

void clean();

int main(){

    atexit(clean);
    printf("Programa a correr \n");
    int value = 0;
    printf("Insira o valor: \n");
    scanf("Enter %d: \n", &value);
    printf("Valor: %d \n", value);

}

void clean(){

    printf("Doing some clean \n");
}
