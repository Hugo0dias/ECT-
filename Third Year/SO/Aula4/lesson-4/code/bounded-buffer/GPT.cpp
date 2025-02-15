#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>

pthread_mutex_t lock;
pthread_cond_t cond;
int turn = 1;  // To control turn between the two threads

// Thread function for child 1
void* threadChild1(void* arg) {
    int* counter = (int*)arg;

    while (1) {
        pthread_mutex_lock(&lock);

        // Wait until it's thread 1's turn
        while (turn != 1) {
            pthread_cond_wait(&cond, &lock);
        }

        // Check if counter has reached 1 and exit if so
        if (*counter <= 1) {
            pthread_mutex_unlock(&lock);
            pthread_cond_broadcast(&cond);
            break;
        }

        // Decrement and print counter
        (*counter)--;
        printf("Thread 1 decremented, counter = %d\n", *counter);

        // Pass turn to thread 2 and signal
        turn = 2;
        pthread_cond_signal(&cond);

        pthread_mutex_unlock(&lock);
    }

    return NULL;
}

// Thread function for child 2
void* threadChild2(void* arg) {
    int* counter = (int*)arg;

    while (1) {
        pthread_mutex_lock(&lock);

        // Wait until it's thread 2's turn
        while (turn != 2) {
            pthread_cond_wait(&cond, &lock);
        }

        // Check if counter has reached 1 and exit if so
        if (*counter <= 1) {
            pthread_mutex_unlock(&lock);
            pthread_cond_broadcast(&cond);
            break;
        }

        // Decrement and print counter
        (*counter)--;
        printf("Thread 2 decremented, counter = %d\n", *counter);

        // Pass turn to thread 1 and signal
        turn = 1;
        pthread_cond_signal(&cond);

        pthread_mutex_unlock(&lock);
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
