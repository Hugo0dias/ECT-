#include <stdio.h>
#include <stdlib.h>
#include "Graph.h"

// testar a funçao checkinvariants

// grafo digrafo (ou nao) , grafo pesado (ou nao) 
// com diferentes numeros de vertices e arestas 

int main(void) {


    Graph* g2 = GraphCreateComplete(6, 0); 
    GraphCheckInvariants(g2);
    GraphDisplay(g2);

    // grafo digrafo
    Graph* g = GraphCreateComplete(6, 1); 
    GraphCheckInvariants(g);
    GraphDisplay(g);

    return 0;
}