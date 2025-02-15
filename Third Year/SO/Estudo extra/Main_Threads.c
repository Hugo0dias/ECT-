#include <stdio.h>
#include   <pthread.h>
#include <unistd.h>
#include <stdlib.h>

// Duas maneiras de passar argumentos para a main (pthread create / pthread join)
/* pthread create atraves do arg e da 4 posicao da funcao, no qual e dado o endereco da variavel que quer que seja
alterada */
/* pthread join consegue o valor de retorno de uma funcao no seu segundo elemento (void **)*/

void *thread_function(void *arg) { // passar value para a thread, "arg" usado na pthread_create ultimo elemento
                                   // a variavel altera-se pois e dado o endereco, nao precisa de retornar    
    
    int *iptr2 = (int*)arg;
    int *iptr = (int *)malloc(sizeof(int));
     *iptr = 5 ;
    for (int i = 0; i < 10; i++){
        
        printf("I value: %d Iptr value: %d \n", i, *iptr);
        sleep(1);
        (*iptr2) ++;
        (*iptr)++;
    }  

    return  iptr; // retornado para a pthread join no segundo elemento 
}

void second_thread_function() {
    for (int j = 0; j < 5; j++){
        sleep(1);
        printf("J value: %d \n", j);
    }
}

int main() {
    // Create a thread
    pthread_t thread;
    int *v;
    int v2 = 5;
    pthread_create(&thread, NULL, thread_function, &v2);
    second_thread_function();
    // Wait for the thread to finish
    pthread_join(thread, (void *)&v); // segundo argumento permite retornar valor de uma funcao
    printf(" Thread Done V1: %d, Thread Done V2: %d \n", *v, v2);
    return 0;
}