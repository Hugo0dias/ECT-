#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/wait.h>

#include "delays.h"
#include "process.h"

int main(void)
{
  pid_t ret = pfork(); // equivalent to fork(), dealing internally with error situations

  if (ret == 0){
    for(int i= 0; i < 11; i++){
      printf("%d", i);
    }
    printf("\n");
    printf("Child Process \n");
  } else {
    pwait(NULL);
    for(int i= 11; i < 21; i++){
      printf("%d", i);
    }
    printf("\n");
    printf("Parent Process \n");
  }
  return EXIT_SUCCESS;
}

