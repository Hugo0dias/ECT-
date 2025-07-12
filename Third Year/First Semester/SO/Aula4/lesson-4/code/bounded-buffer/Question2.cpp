#include <stdio.h>
#include <unistd.h>
#include <stdint.h>
#include <math.h>
#include <libgen.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <pthread.h>

void* Childthread(void *arg){

    int N2;
    printf("Insert a Item between 10-20: \n");
    scanf("%d", &N2);
    if ( N2 < 10 && N2 > 20) exit(0);

    int *counter = (int*)arg;

    // Increment the shared variable up to N2
    while (*counter < N2) {
        (*counter)++;
        printf("Counter value (incrementing) Cthread: %d\n", *counter);
        // sleep(1); // Optional delay for readability
    }

    return NULL;

}

int main(int argc, char *argv[]){

    pthread_t thread;
    int N1;
    printf("Insert a Item between 1-9: \n");
    scanf("%d", &N1);
    if ( N1 < 1 && N1 > 9) exit(0);

    int counter = N1;

    pthread_create(&thread, NULL, Childthread, &counter);
    pthread_join(thread, NULL);

    while (counter > 1) {
        counter--;
        printf("Counter value (decrementing) Pthread: %d\n", counter);
        // sleep(1); // Optional delay for readability
    }

    return 0;


}
