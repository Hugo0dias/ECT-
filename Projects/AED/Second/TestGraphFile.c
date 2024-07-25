//
// Algoritmos e Estruturas de Dados --- 2023/2024
//
// Joaquim Madeira, Joao Manuel Rodrigues - June 2021, Nov 2023
//
// Graph EXAMPLE : Creating and displaying graphs
//

// o fichero 4 testa a função GraphFromFile !!

#include <stdio.h>
#include <stdlib.h>
#include "Graph.h"

int main(void) {

  FILE *f = fopen("GRAPHS/SWmediumEWD.txt", "r");
  if (f == NULL) {
    perror("fopen");
    exit(2);
  }

  Graph* gg = GraphFromFile(f);
  GraphDisplay(gg);
  

  printf("------------------------\n");
  printf("------------------------\n");
  printf("------------------------\n");
  printf("------------------------\n");
  printf("------------------------\n");
  printf("------------------------\n");
  printf("------------------------\n");
  printf("------------------------\n");
  printf("------------------------\n");

  FILE *f1 = fopen("GRAPHS/SWtinyG.txt", "r");
  if (f1 == NULL) {
    perror("fopen");
    exit(2);
  }

  Graph* gg1 = GraphFromFile(f1);
  GraphDisplay(gg1);
  

  printf("------------------------\n");
  printf("------------------------\n");
  printf("------------------------\n");
  printf("------------------------\n");
  printf("------------------------\n");
  printf("------------------------\n");
  printf("------------------------\n");
  printf("------------------------\n");
  printf("------------------------\n");

  FILE *f2 = fopen("GRAPHS/SWtinyDAG.txt", "r");
  if (f2 == NULL) {
    perror("fopen");
    exit(2);
  }

  Graph* gg2 = GraphFromFile(f2);
  GraphDisplay(gg2);
  return 0;
}
