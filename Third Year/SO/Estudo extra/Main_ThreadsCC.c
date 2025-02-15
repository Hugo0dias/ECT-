#include <stdio.h>
#include   <pthread.h>
#include <unistd.h>
#include <stdlib.h>
pthread_mutex_t lock = PTHREAD_MUTEX_INITIALIZER;

// mutex lock nao permite race condition, porem o tempo de run e muito grande

void *thread_function(void *arg) {
                                      
    
    int *iptr2 = (int*)arg;
    for (int i = 0; i < 10000000; i++){
        pthread_mutex_lock(&lock);
        (*iptr2) ++;
        pthread_mutex_unlock(&lock);
    }  

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
    int v2 = 5;
    pthread_create(&thread, NULL, thread_function, &v2);
    thread_function(&v2);
    // Wait for the thread to finish
    pthread_join(thread, NULL); // segundo argumento permite retornar valor de uma funcao
    printf("Thread Done V2: %d \n", v2);
    return 0;
}