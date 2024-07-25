#include <stdio.h>
#include <stdlib.h>
#include "Graph.h"

// testar funcao graph remove 

int main(void) {

    // grafo digrafo
    Graph* g = GraphCreateComplete(6, 1); 
    GraphRemoveEdge(g, 2, 3);
    GraphRemoveEdge(g, 2, 4);
    GraphRemoveEdge(g, 1, 5);
    GraphDisplay(g);

    printf("--------------------------------");

    Graph* g1 = GraphCreateComplete(6, 0); 
    GraphRemoveEdge(g1, 3, 1);
    GraphDisplay(g1);

    return 0;
}