#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>

pthread_mutex_t lock;
pthread_cond_t cond;
int sem = 0;


// Thread function for child 1
void* threadChild1(void* arg) {

    int *ptr = (int*) arg;
    while(1){

        pthread_mutex_lock(&lock);

        while (sem != 1) {
            pthread_cond_wait(&cond, &lock);
        }

        (*ptr)--;
        
        printf("Value (C1) : %d | Thread ID: %ld \n", *ptr, pthread_self());
        sem = 0;
        pthread_cond_broadcast(&cond);

        pthread_mutex_unlock(&lock);
        sleep(1);
        if ( *ptr <= 1) {
            pthread_mutex_unlock(&lock);  // Liberar o mutex antes de sair
            pthread_cond_broadcast(&cond);
            return NULL;
        }

    }

    return NULL;
}

// Thread function for child 2
void* threadChild2(void* arg) {

    int *ptr = (int*) arg;
    while(1){

        pthread_mutex_lock(&lock);

        while (sem != 0) {
            pthread_cond_wait(&cond, &lock);
        }

        (*ptr)--;

        printf("Value (C2) : %d | Thread ID: %ld \n", *ptr, pthread_self());

        sem = 1;
        
        pthread_cond_broadcast(&cond);

        pthread_mutex_unlock(&lock);
        sleep(1);

        if ( *ptr <= 1) {
            pthread_mutex_unlock(&lock);  // Liberar o mutex antes de sair
            pthread_cond_broadcast(&cond);
            return NULL;
        }

    }

    return NULL;
}

int main() {
    pthread_t thread1, thread2;
    int N;

    // Get a valid counter value from the user
    printf("Insert a value between 10 and 20: ");
    scanf("%d", &N);
    if (N < 10 || N > 20) {
        printf("Error: Value must be between 10 and 20.\n");
        return EXIT_FAILURE;
    }

    int counter = N;

    // Initialize mutex and condition variable
    pthread_mutex_init(&lock, NULL);
    pthread_cond_init(&cond, NULL);

    // Create child threads
    pthread_create(&thread1, NULL, threadChild1, &counter);
    pthread_create(&thread2, NULL, threadChild2, &counter);

    // Wait for both threads to finish
    pthread_join(thread1, NULL);
    pthread_join(thread2, NULL);

    // Destroy mutex and condition variable
    pthread_mutex_destroy(&lock);
    pthread_cond_destroy(&cond);

    return 0;
}
