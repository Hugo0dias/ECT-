//
// Algoritmos e Estruturas de Dados --- 2023/2024
//
// Topological Sorting
//

#include "GraphTopologicalSorting.h"
#include "Graph.h" // Add this line
#include "SortedList.h"

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>

#include "IntegersQueue.h"
#include "instrumentation.h"

struct _GraphTopoSort {
  int* marked;                     // Aux array
  unsigned int* numIncomingEdges;  // Aux array
  unsigned int* vertexSequence;    // The result
  int validResult;                 // 0 or 1
  unsigned int numVertices;        // From the graph
  Graph* graph;
};

// AUXILIARY FUNCTION
// Allocate memory for the struct
// And for its array fields
// Initialize all struct fields
//
static GraphTopoSort* _create(Graph* g) {
  assert(g != NULL);
  
  GraphTopoSort* GTS = (GraphTopoSort*)malloc(sizeof(GraphTopoSort));
  GTS->numVertices = GraphGetNumVertices(g);

  GTS->marked = (int*)malloc(GTS->numVertices * sizeof(int)); // reserva espaço
  GTS->numIncomingEdges = (unsigned int*)malloc(GTS->numVertices * sizeof(unsigned int)); // reserva espaço
  GTS->vertexSequence = (unsigned int*)malloc(GTS->numVertices * sizeof(unsigned int)); // reserva espaço
  GTS->validResult = 0; // inicializo em 0 ou 1 (qual a diferença) ?
  GTS->graph = g;

  return GTS;
}

//
// Computing the topological sorting, if any, using the 1st algorithm:
// 1 - Create a copy of the graph
// 2 - Successively identify vertices without incoming edges and remove their
//     outgoing edges
// Check if a valid sorting was computed and set the isValid field
// For instance, by checking if the number of elements in the vertexSequence is
// the number of graph vertices
//
GraphTopoSort* GraphTopoSortComputeV1(Graph* g) {
  assert(g != NULL && GraphIsDigraph(g) == 1);
  // Create a copy of the graph
  Graph* copy = GraphCopy(g);
  unsigned int* Adjacente;
  // Create and initialize the struct
  GraphTopoSort* topoSort = _create(copy);
  unsigned int i;
  unsigned int index = 0;
  // aplicação do algoritmo
  while(index < GraphGetNumVertices(copy)){
    // procura um vertice sem arestas incidentes
    for(i = 0; i < GraphGetNumVertices(copy); i++){
      if (GraphGetVertexInDegree(copy, i) == 0 && topoSort->marked[i] == 0){
        printf("%d", i); // colocar i na lista do vertex sequence
        topoSort->vertexSequence[index++] = i;  //  adiciona o vertice sem arestas incidentes ao vertex sequence
        topoSort->marked[i] = 1; // este vertice ja entrou na sequencia (foi apagado)
        Adjacente = GraphGetAdjacentsTo(copy,i);
        for (unsigned int k = 1; k <= Adjacente[0]; k++){ // ver vertices adjacentes e remover
          printf("a remover a aresta de %d para %d\n", i, Adjacente[k]);
          GraphRemoveEdge(copy, i, Adjacente[k]);
        }
        free(Adjacente);
        break; 
      }
    }
    printf("i : %d", i);
    if( i == GraphGetNumVertices(copy)){
      printf("acabou a ordenacao");
      break;  // garante que nao existe ciclo infinito quando nao ha vertices sem arestas incidentes
    }
  } // endwhile
  if(GraphGetNumEdges(copy) == 0){ // se o numero de arestas for 0, a ordenação é valida
      topoSort ->validResult = 1;
  }
  free(copy);   
  return topoSort;
}


//
// Computing the topological sorting, if any, using the 2nd algorithm
// Check if a valid sorting was computed and set the isValid field
// For instance, by checking if the number of elements in the vertexSequence is
// the number of graph vertices
//
GraphTopoSort* GraphTopoSortComputeV2(Graph* g) {
  assert(g != NULL && GraphIsDigraph(g) == 1);
  unsigned int* Adjacente;
  GraphTopoSort* topoSort = _create(g);
  // Lista que contém os bumero de vértices incidentes do respetivo vértice
  unsigned int ix;
  for(ix = 0; ix < GraphGetNumVertices(g); ix++) {
    int IndegreeVertex = GraphGetVertexInDegree(g, ix);
    topoSort->numIncomingEdges[ix] = IndegreeVertex;
    topoSort->marked[ix] = 0;
  }
  // aplicação do algoritmo 
  unsigned int i;
  unsigned int index = 0;
  while(index < GraphGetNumVertices(g)){
    // algoritmo igual ao da V1 
    // apenas atualiza uma lista conforme a ausencia de arestas incidentes
    for(i = 0; i < GraphGetNumVertices(g); i++){        
      if (topoSort->numIncomingEdges[i] == 0 && topoSort->marked[i] == 0){
        printf("%d", i); // colocar i na lista do vertex sequence
        topoSort->vertexSequence[index++] = i;
        topoSort->marked[i] = 1; // este vertice ja entrou na sequencia (foi apagado)
        Adjacente = GraphGetAdjacentsTo(g,i);
        for (unsigned int k = 1; k <= Adjacente[0]; k++){ // estou no vertice 0, ver os adjacentes, 
          int indice = Adjacente[k];
          topoSort->numIncomingEdges[indice] = topoSort->numIncomingEdges[indice] - 1; //remove uma aresta na lista ao respetivo vertice porque e adjacente
          /*for (unsigned int idx = 0; idx < GraphGetNumVertices(g); idx ++){
              printf(" %d : %d \n", idx, topoSort->numIncomingEdges[idx]);
            }*/
        }
        free(Adjacente);
        break; 
      }
    }
    if( i == GraphGetNumVertices(g)){
      printf("acabou a ordenacao\n");
      break; // garante que nao existe ciclo infinito quando nao ha vertices sem arestas incidentes
    }
  } // endwhile
  if(index == topoSort->numVertices){
      topoSort ->validResult = 1;
  }   
  return topoSort;
}

//
// Computing the topological sorting, if any, using the 3rd algorithm
// Check if a valid sorting was computed and set the isValid field
// For instance, by checking if the number of elements in the vertexSequence is
// the number of graph vertices
//


GraphTopoSort* GraphTopoSortComputeV3(Graph* g) {
  assert(g != NULL && GraphIsDigraph(g) == 1);
  unsigned int* Adjacente;
  GraphTopoSort* topoSort = _create(g);
  // Lista que contém os numero de vértices incidentes do respetivo vértice
  unsigned int ix;
  for(ix = 0; ix < GraphGetNumVertices(g); ix++) {
    int IndegreeVertex = GraphGetVertexInDegree(g, ix);
    topoSort->numIncomingEdges[ix] = IndegreeVertex;
  }
  // criação da fila 
  Queue* queue = QueueCreate(GraphGetNumVertices(g));
  unsigned int i;
  unsigned int index = 0;
  while(index < GraphGetNumVertices(g)){
    // Algoritmo igual ao V2
    // apenas de adiciona as arestas a uma fila
    for(i = 0; i < GraphGetNumVertices(g); i++){
      if (topoSort->numIncomingEdges[i] == 0 && topoSort->marked[i] == 0){
        printf("%d", i); // colocar i na lista do vertex sequence
        topoSort->vertexSequence[index++] = i;
        topoSort->marked[i] = 1; // este vertice ja entrou na sequencia (foi apagado)
        Adjacente = GraphGetAdjacentsTo(g,i);
        for (unsigned int k = 1; k <= Adjacente[0]; k++){ // estou no vertice 0, ver os adjacentes, 
          printf("a remover a aresta de %d para %d\n", i, Adjacente[k]);
          int indice = Adjacente[k];
          topoSort->numIncomingEdges[indice] = topoSort->numIncomingEdges[indice] - 1; //remove uma aresta na lista ao respetivo vertice porque e adjacente
          if (topoSort->numIncomingEdges[indice] = 0){
            QueueEnqueue(queue, indice); // se o numero de arestas de incidente de um vertice for 0, adiciona-o a uma queue
          }
        }
        free(Adjacente);
        break; 
      }
    }
    if( i == GraphGetNumVertices(g)){
      printf("acabou a ordenacao");
      break; // garante que nao existe ciclo infinito quando nao ha vertices sem arestas incidentes
    }
  } // endwhile
  if(index == topoSort->numVertices){
        topoSort ->validResult = 1;
  }   
  return topoSort;
}



void GraphTopoSortDestroy(GraphTopoSort** p) {
  assert(*p != NULL);

  GraphTopoSort* aux = *p;

  free(aux->marked);
  free(aux->numIncomingEdges);
  free(aux->vertexSequence);

  free(*p);
  *p = NULL;
}

//
// A valid sorting was computed?
//
int GraphTopoSortIsValid(const GraphTopoSort* p) { return p->validResult; }

//
// Getting an array containing the topological sequence of vertex indices
// Or NULL, if no sequence could be computed
// MEMORY IS ALLOCATED FOR THE RESULTING ARRAY
//
unsigned int* GraphTopoSortGetSequence(const GraphTopoSort* p) {
  assert(p != NULL);
  unsigned int* Sequence_Array[] = {(unsigned int*)malloc(p->numVertices * sizeof(unsigned int))};
  if (p->validResult == 0) {
    printf(" *** The topological sorting could not be computed!! *** \n");
    return NULL;
  }

  printf("Topological Sorting - Vertex indices:\n");
  for (unsigned int i = 0; i < GraphGetNumVertices(p->graph); i++) {
    Sequence_Array[i] = p->vertexSequence[i];
    printf(" %d ", Sequence_Array[i]);
  }
  printf("\n");
  return Sequence_Array;
}

// DISPLAYING on the console

//
// The toplogical sequence of vertex indices, if it could be computed
//
void GraphTopoSortDisplaySequence(const GraphTopoSort* p) {
  assert(p != NULL);

  if (p->validResult == 0) {
    printf(" *** The topological sorting could not be computed!! *** \n");
    return;
  }

  printf("Topological Sorting - Vertex indices:\n");
  for (unsigned int i = 0; i < GraphGetNumVertices(p->graph); i++) {
    printf("%d ", p->vertexSequence[i]);
  }
  printf("\n");
}

//
// The toplogical sequence of vertex indices, if it could be computed
// Followed by the digraph displayed using the adjecency lists
// Adjacency lists are presented in topologic sorted order
//
void GraphTopoSortDisplay(const GraphTopoSort* p) {
  assert(p != NULL);

  // The topological order
  GraphTopoSortDisplaySequence(p);

  if (p->validResult == 0) {
    return;
  }

  // The Digraph
  for (unsigned int i = 0; i < GraphGetNumVertices(p->graph); i++) {
    GraphListAdjacents(p->graph, p->vertexSequence[i]);
  }
  printf("\n");
}
