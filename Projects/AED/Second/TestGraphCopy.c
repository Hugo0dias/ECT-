#include <stdio.h>
#include <stdlib.h>
#include "Graph.h"

// testar a funçao graph copy

// grafo digrafo (ou nao) , grafo pesado (ou nao) 
// com diferentes numeros de vertices e arestas 

int main(void) {

  FILE *f = fopen("GRAPHS/SWmediumEWD.txt", "r");
  if (f == NULL) {
    perror("fopen");
    exit(2);
  }

  Graph* gg = GraphFromFile(f);
  GraphDisplay(gg);
  Graph* g1 = GraphCopy(gg);
    GraphDisplay(g1);

  printf("--------------------------------------------");

  Graph* p = GraphCreateComplete(10,0);   //problema na contagem de arestas
  GraphDisplay(p);
  Graph* p1 = GraphCopy(p);
  GraphDisplay(p1);


  printf("--------------------------------------------");

  Graph* q = GraphCreateComplete(10,1);   //problema na contagem de arestas com grafo não dígrafo
  GraphDisplay(q);
  Graph* q1 = GraphCopy(q);
  GraphDisplay(q1);
    return 0;
}