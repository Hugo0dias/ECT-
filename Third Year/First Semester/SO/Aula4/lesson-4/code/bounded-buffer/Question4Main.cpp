#include <stdio.h>
#include <unistd.h>
#include <stdint.h>
#include <math.h>
#include <libgen.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>

#include "utils.h"
#include "thread.h"
#include "fifo.h"

#define N1 5

pthread_mutex_t lock;
pthread_cond_t cond;
static Fifo *Fifo_available;
static Fifo *Fifo_pend;

void* ThreadClient(void *arg){

    int* ptr = (int*) arg;

    while(1){

        pthread_mutex_lock(&lock);
        
        if("cond") {
            pthread_cond_wait(&cond, &lock);
        }
        Item buffer = fifoRetrieve(Fifo_available);
        buffer.data[0] = *ptr;
        printf("Buffer ID: %d, Buffer Data %d", buffer.id, buffer.data[0]);
        fifoInsert(Fifo_pend, buffer);

        // esperar que server coloque mensagem
        // ...

        Item response = fifoRetrieve(Fifo_pend);

        fifoInsert(Fifo_available, buffer);
    
        pthread_mutex_unlock(&lock);
        
    }

    return NULL;
}


void* ThreadServer(void *arg){



    return NULL;
}


int main(int argc, char *argv[]){

    printf("N = 0\n");        // Option 1: Add newline
    
    int nc = 5;
    int cdata[nc];
    pthread_t threadClient[nc], threadServer;
    Item Buffer = {0,0};

    Fifo_available = (Fifo*)mem_alloc(sizeof(Fifo));
    fifoInit(Fifo_available);

    printf("N = 1\n"); 

    Fifo_pend = (Fifo*)mem_alloc(sizeof(Fifo));
    fifoInit(Fifo_pend);

    for (int i = 0; i < N1; i++){
        Buffer.id = i++;
        fifoInsert(Fifo_available, Buffer);
    }

    printf("N = 2\n"); 

    pthread_create(&threadServer, NULL, ThreadServer, NULL);

    for(int i =0; i<nc ; i++){
        cdata[i] = i+1;
        pthread_create(&threadClient[i], NULL, ThreadClient, &cdata[i]);
    }

    printf("N = 3\n"); 

    pthread_join(threadServer, NULL);

    for(int i =0; i<nc ; i++){
        thread_join(threadClient[i], NULL);
    }
    printf("4");
    return 0;
}