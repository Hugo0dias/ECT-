//
// Algoritmos e Estruturas de Dados --- 2023/2024
//
// Joaquim Madeira, Joao Manuel Rodrigues - June 2021, Nov 2023
//
// Graph - Using a list of adjacency lists representation
// hello

#include "Graph.h"

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>

#include "SortedList.h"
#include "instrumentation.h"

struct _Vertex {
  unsigned int id;
  unsigned int inDegree;
  unsigned int outDegree;
  List* edgesList;
};

struct _Edge {
  unsigned int adjVertex;
  double weight;
};

struct _GraphHeader {
  int isDigraph;
  int isComplete;
  int isWeighted;
  unsigned int numVertices;
  unsigned int numEdges;
  List* verticesList;
};

// The comparator for the VERTICES LIST

int graphVerticesComparator(const void* p1, const void* p2) {
  unsigned int v1 = ((struct _Vertex*)p1)->id;
  unsigned int v2 = ((struct _Vertex*)p2)->id;
  int d = v1 - v2;
  return (d > 0) - (d < 0);
}

// The comparator for the EDGES LISTS

int graphEdgesComparator(const void* p1, const void* p2) {
  unsigned int v1 = ((struct _Edge*)p1)->adjVertex;
  unsigned int v2 = ((struct _Edge*)p2)->adjVertex;
  int d = v1 - v2;
  return (d > 0) - (d < 0);
}

Graph* GraphCreate(unsigned int numVertices, int isDigraph, int isWeighted) {
  Graph* g = (Graph*)malloc(sizeof(struct _GraphHeader));
  if (g == NULL) abort();

  g->isDigraph = isDigraph;
  g->isComplete = 0;
  g->isWeighted = isWeighted;

  g->numVertices = numVertices;
  g->numEdges = 0;

  g->verticesList = ListCreate(graphVerticesComparator);

  for (unsigned int i = 0; i < numVertices; i++) {
    struct _Vertex* v = (struct _Vertex*)malloc(sizeof(struct _Vertex));
    if (v == NULL) abort();

    v->id = i;
    v->inDegree = 0;
    v->outDegree = 0;

    v->edgesList = ListCreate(graphEdgesComparator);

    ListInsert(g->verticesList, v);
  }

  assert(g->numVertices == ListGetSize(g->verticesList));

  return g;
}

Graph* GraphCreateComplete(unsigned int numVertices, int isDigraph) {
  Graph* g = GraphCreate(numVertices, isDigraph, 0);

  g->isComplete = 1;

  List* vertices = g->verticesList;
  ListMoveToHead(vertices);
  unsigned int i = 0;
  for (; i < g->numVertices; ListMoveToNext(vertices), i++) {
    struct _Vertex* v = ListGetCurrentItem(vertices);
    List* edges = v->edgesList;
    for (unsigned int j = 0; j < g->numVertices; j++) {
      if (i == j) {
        continue;
      }
      struct _Edge* new = (struct _Edge*)malloc(sizeof(struct _Edge));
      if (new == NULL) abort();
      new->adjVertex = j;
      new->weight = 1;

      ListInsert(edges, new);
    }
    if (g->isDigraph) {
      v->inDegree = g->numVertices - 1;
      v->outDegree = g->numVertices - 1;
    } else {
      v->outDegree = g->numVertices - 1;
    }
  }
  if (g->isDigraph) {
    g->numEdges = numVertices * (numVertices - 1);
  } else {
    g->numEdges = numVertices * (numVertices - 1) / 2;
  }

  return g;
}

void GraphDestroy(Graph** p) {
  assert(*p != NULL);
  Graph* g = *p;

  List* vertices = g->verticesList;
  if (ListIsEmpty(vertices) == 0) {
    ListMoveToHead(vertices);
    unsigned int i = 0;
    for (; i < g->numVertices; ListMoveToNext(vertices), i++) {
      struct _Vertex* v = ListGetCurrentItem(vertices);

      List* edges = v->edgesList;
      if (ListIsEmpty(edges) == 0) {
        unsigned int i = 0;
        ListMoveToHead(edges);
        for (; i < ListGetSize(edges); ListMoveToNext(edges), i++) {
          struct _Edge* e = ListGetCurrentItem(edges);
          free(e);
        }
      }
      ListDestroy(&(v->edgesList));
      free(v);
    }
  }

  ListDestroy(&(g->verticesList));
  free(g);

  *p = NULL;
}

Graph* GraphCopy(const Graph* g) {

  assert(g != NULL);
  assert(g->verticesList != NULL);
  assert(g != NULL);
  int count = 0;

  Graph* newGraph = GraphCreate(g->numVertices, g->isDigraph, g->isWeighted); // crias o grafo copia
  
  assert(newGraph != NULL);
  assert(newGraph->verticesList != NULL);
  
  newGraph -> numEdges = g->numEdges; // copia do numero de arestas do grafo original para o grafo de copia

  ListMoveToHead(g->verticesList);  // confirmar que a lista de vertices do grafo original se encontra no primeiro elemento
  ListMoveToHead(newGraph->verticesList); // confirmar que a lista de vertices do grafo copia se encontra no primeiro elemento
  unsigned int iterator = 0;

  for(iterator = 0; iterator < g->numVertices ; iterator++){ // iterar sobre a lista de vertices

    struct _Vertex* OriginalVertex = ListGetCurrentItem(g->verticesList); // vertice original
    struct _Vertex* CopyVertex = ListGetCurrentItem(newGraph->verticesList); // vertice copia
  
    // verificar null
    if (CopyVertex == NULL || OriginalVertex == NULL){
      printf('few');
      return NULL;
    };

    if (g->verticesList == NULL || newGraph->verticesList == NULL) {
      printf("Error: One of the vertex lists is NULL.\n");
      return NULL;
    }

    CopyVertex -> id = OriginalVertex -> id; // copia do id do grafo original para o grafo de copia
    CopyVertex -> inDegree = OriginalVertex -> inDegree; // copia do indegree do grafo original para o grafo de copia
    CopyVertex -> outDegree = OriginalVertex -> outDegree; // copia do outdegree do grafo original para o grafo de copia
    
    unsigned int iterator2 = 0;
    ListMoveToHead(OriginalVertex->edgesList);
    for( iterator2 = 0; iterator2 < ListGetSize(OriginalVertex->edgesList); iterator2++){ // iterar sobre a lista de arestas de cada vertice

      struct _Edge* OriginalEdge =ListGetCurrentItem(OriginalVertex->edgesList); // aresta original (do grafo original
      struct _Edge* copyEdge = (struct _Edge*)malloc(sizeof(struct _Edge));   // objeto de copia da aresta

      // verificar null
      if (copyEdge == NULL || OriginalEdge == NULL){
        printf('few');
        return NULL;
      };

      copyEdge ->adjVertex = OriginalEdge->adjVertex; // copia do vertice adjacente do grafo original para o grafo de copia
      copyEdge ->weight = OriginalEdge->weight; // copia do peso do grafo original para o grafo de copia
      ListInsert(CopyVertex ->edgesList, copyEdge); // insere os dados atribuidos na lista vazia do grafo copia

      count++;
      ListMoveToNext(OriginalVertex->edgesList); // passa ao proximo elemento de arestas
      
    }


     
    ListMoveToNext(g->verticesList); // passa ao proximo elemento na lista de vertices do grafo original
    ListMoveToNext(newGraph->verticesList); // passa ao proximo elemento na lista de vertices do grafo copia
    
  }
  
  if(g->isDigraph==0){
    count = count / 2;
  }
  printf("count: %d\n", count); // print do numero de arestas do grafo copia (para verificar se é igual ao numero de arestas do grafo original
  printf("numEdges: %d\n", newGraph->numEdges); // print do numero de arestas do grafo copia (para verificar se é igual ao numero de arestas do grafo original
  
  return newGraph;

}

Graph* GraphFromFile(FILE* f) {
  assert(f != NULL);

  // Le o cabeçalho do ficheiro

  unsigned int isDigraph; 
  fscanf(f, "%u\n", &isDigraph);
  unsigned int isWeighted;
  fscanf(f, "%u\n", &isWeighted);
  unsigned int numVertices;
  fscanf(f, "%u\n", &numVertices);
  unsigned int numEdges;
  fscanf(f, "%u\n", &numEdges);

  Graph* g = GraphCreate(numVertices, isDigraph, isWeighted);

  // Le os dados do ficheiro
  // caso seja pesado ou nao

  unsigned int vinitial, vfinal;
  if(!isWeighted){
    for(unsigned int i=0;i<numEdges;i++){
      fscanf(f, "%u %u\n", &vinitial, &vfinal);
      if(vinitial!=vfinal)
        GraphAddEdge(g, vinitial, vfinal);
    }
  }
  else{
    float weight;
    for(int i=0;i<numEdges;i++){
      fscanf(f, "%u %u %f\n", &vinitial, &vfinal, &weight);
      if(vinitial!=vfinal)
        GraphAddWeightedEdge(g, vinitial, vfinal, weight);
    }
  }

  return g;

  
}

// Graph

int GraphIsDigraph(const Graph* g) { return g->isDigraph; }

int GraphIsComplete(const Graph* g) { return g->isComplete; }

int GraphIsWeighted(const Graph* g) { return g->isWeighted; }

unsigned int GraphGetNumVertices(const Graph* g) { return g->numVertices; }

unsigned int GraphGetNumEdges(const Graph* g) { return g->numEdges; }

//
// For a graph
//
double GraphGetAverageDegree(const Graph* g) {
  assert(g->isDigraph == 0);
  return 2.0 * (double)g->numEdges / (double)g->numVertices;
}

static unsigned int _GetMaxDegree(const Graph* g) {
  List* vertices = g->verticesList;
  if (ListIsEmpty(vertices)) return 0;

  unsigned int maxDegree = 0;
  ListMoveToHead(vertices);
  unsigned int i = 0;
  for (; i < g->numVertices; ListMoveToNext(vertices), i++) {
    struct _Vertex* v = ListGetCurrentItem(vertices);
    if (v->outDegree > maxDegree) {
      maxDegree = v->outDegree;
    }
  }
  return maxDegree;
}

//
// For a graph
//
unsigned int GraphGetMaxDegree(const Graph* g) {
  assert(g->isDigraph == 0);
  return _GetMaxDegree(g);
}

//
// For a digraph
//
unsigned int GraphGetMaxOutDegree(const Graph* g) {
  assert(g->isDigraph == 1);
  return _GetMaxDegree(g);
}

// Vertices

//
// returns an array of size (outDegree + 1)
// element 0, stores the number of adjacent vertices
// and is followed by indices of the adjacent vertices
//
unsigned int* GraphGetAdjacentsTo(const Graph* g, unsigned int v) {
  assert(v < g->numVertices);

  // Node in the list of vertices
  List* vertices = g->verticesList;
  ListMove(vertices, v);
  struct _Vertex* vPointer = ListGetCurrentItem(vertices);
  unsigned int numAdjVertices = vPointer->outDegree;

  unsigned int* adjacent =
      (unsigned int*)calloc(1 + numAdjVertices, sizeof(unsigned int));

  if (numAdjVertices > 0) {
    adjacent[0] = numAdjVertices;
    List* adjList = vPointer->edgesList;
    ListMoveToHead(adjList);
    for (unsigned int i = 0; i < numAdjVertices; ListMoveToNext(adjList), i++) {
      struct _Edge* ePointer = ListGetCurrentItem(adjList);
      adjacent[i + 1] = ePointer->adjVertex;
    }
  }

  return adjacent;
}

//
// returns an array of size (outDegree + 1)
// element 0, stores the number of adjacent vertices
// and is followed by the distances to the adjacent vertices
//
double* GraphGetDistancesToAdjacents(const Graph* g, unsigned int v) {
  assert(v < g->numVertices);

  // Node in the list of vertices
  List* vertices = g->verticesList;
  ListMove(vertices, v);
  struct _Vertex* vPointer = ListGetCurrentItem(vertices);
  unsigned int numAdjVertices = vPointer->outDegree;

  double* distance = (double*)calloc(1 + numAdjVertices, sizeof(double));

  if (numAdjVertices > 0) {
    distance[0] = numAdjVertices;
    List* adjList = vPointer->edgesList;
    ListMoveToHead(adjList);
    for (unsigned int i = 0; i < numAdjVertices; ListMoveToNext(adjList), i++) {
      struct _Edge* ePointer = ListGetCurrentItem(adjList);
      distance[i + 1] = ePointer->weight;
    }
  }

  return distance;
}

//
// For a graph
//
unsigned int GraphGetVertexDegree(Graph* g, unsigned int v) {
  assert(g->isDigraph == 0);
  assert(v < g->numVertices);

  ListMove(g->verticesList, v);
  struct _Vertex* p = ListGetCurrentItem(g->verticesList);

  return p->outDegree;
}

//
// For a digraph
//
unsigned int GraphGetVertexOutDegree(Graph* g, unsigned int v) {
  assert(g->isDigraph == 1);
  assert(v < g->numVertices);

  ListMove(g->verticesList, v);
  struct _Vertex* p = ListGetCurrentItem(g->verticesList);

  return p->outDegree;
}

//
// For a digraph
//
unsigned int GraphGetVertexInDegree(Graph* g, unsigned int v) {
  assert(g->isDigraph == 1);
  assert(v < g->numVertices);

  ListMove(g->verticesList, v);
  struct _Vertex* p = ListGetCurrentItem(g->verticesList);

  return p->inDegree;
}

// Edges

static int _addEdge(Graph* g, unsigned int v, unsigned int w, double weight) {
  struct _Edge* edge = (struct _Edge*)malloc(sizeof(struct _Edge));
  edge->adjVertex = w;
  edge->weight = weight;

  ListMove(g->verticesList, v);
  struct _Vertex* vertex = ListGetCurrentItem(g->verticesList);
  int result = ListInsert(vertex->edgesList, edge);

  if (result == -1) {
    return 0;
  } else {
    g->numEdges++;
    vertex->outDegree++;

    ListMove(g->verticesList, w);
    struct _Vertex* destVertex = ListGetCurrentItem(g->verticesList);
    destVertex->inDegree++;
  }

  if (g->isDigraph == 0) {
    // Bidirectional edge
    struct _Edge* edge = (struct _Edge*)malloc(sizeof(struct _Edge));
    edge->adjVertex = v;
    edge->weight = weight;

    ListMove(g->verticesList, w);
    struct _Vertex* vertex = ListGetCurrentItem(g->verticesList);
    result = ListInsert(vertex->edgesList, edge);

    if (result == -1) {
      return 0;
    } else {
      // g->numEdges++; // Do not count the same edge twice on a undirected
      // graph !!
      vertex->outDegree++;
    }
  }

  return 1;
}

int GraphAddEdge(Graph* g, unsigned int v, unsigned int w) {
  assert(g->isWeighted == 0);
  assert(v != w);
  assert(v < g->numVertices);
  assert(w < g->numVertices);

  return _addEdge(g, v, w, 1.0);
}

int GraphAddWeightedEdge(Graph* g, unsigned int v, unsigned int w,
                         double weight) {
  assert(g->isWeighted == 1);
  assert(v != w);
  assert(v < g->numVertices);
  assert(w < g->numVertices);

  return _addEdge(g, v, w, weight);
}

Graph* GraphRemoveVertex(Graph* g, unsigned int v){
  assert(g->verticesList != NULL);
  assert(g->numVertices != 0);
  assert(g != NULL);
  unsigned int i;
  unsigned int* Adjacente = (unsigned int*)malloc(g->numVertices * sizeof(unsigned int));
  ListMoveToHead(g->verticesList);

  for (i = 0; i < g->numVertices; i++){

    struct _Vertex* VertexV = ListGetCurrentItem(g->verticesList);

    if( i == v){
      for (unsigned int j = 0; j < g->numVertices; j++) {
        for (unsigned int k = 0; k < ListGetSize(GraphGetAdjacentsTo(g,v)); k++)
        {
          Adjacente = GraphGetAdjacentsTo(g,v)[k];
        
          GraphRemoveEdge(g, v, Adjacente);}
        
      }

      g->numVertices--;

    } else {
      ListMoveToNext(g->verticesList);
    }
    
  }
  return g;
}

int GraphRemoveEdge(Graph* g, unsigned int v, unsigned int w) {
  
  assert(g != NULL);
  assert(v < g->numVertices);
  assert(w < g->numVertices);

  
  ListMove(g->verticesList, w);
  struct _Vertex* vertexW = ListGetCurrentItem(g->verticesList);
  ListMove(g->verticesList, v);
  struct _Vertex* vertexV = ListGetCurrentItem(g->verticesList);

  ListMoveToHead(vertexV->edgesList);

  // percorrer a lista de arestas do vertice v
  for (unsigned int i = 0; i < ListGetSize(vertexV->edgesList); i++) {
    struct _Edge* EdgeP = ListGetCurrentItem(vertexV->edgesList);

    // se o vertice adjacente for igual ao vertice w entao remove a aresta
    if (EdgeP->adjVertex == w) {
      
      ListRemoveCurrent(vertexV->edgesList);
      free(EdgeP);

      
      vertexW->inDegree--;
      vertexV->outDegree--;
      g->numEdges--;

      // se o grafo nao for orientado, remover a aresta do vertice w
      if (g->isDigraph == 0) {
        
        ListMoveToHead(vertexW->edgesList);
        for (unsigned int j = 0; j < ListGetSize(vertexW->edgesList); ListMoveToNext(vertexW->edgesList), j++) {
          struct _Edge* EdgeS = ListGetCurrentItem(vertexW->edgesList);
          if (EdgeS->adjVertex == v){
            ListRemoveCurrent(vertexW->edgesList);
            free(EdgeS);
            vertexW->outDegree--;
            g->numEdges--;
            break;
          }
        }
      }

      return g; 
    }
    ListMoveToNext(vertexV->edgesList);
  }

  return 0; 
}

// CHECKING


// Que condições devem ser invariantes? Por exemplo:
// O tamanho da lista de vértices deve ser sempre igual ao campo numVertices do grafo.
// Num grafo orientado (digrafo), a soma dos outDegrees deve ser igual à soma dos inDegrees e devem igualar o numEdges.

int GraphCheckInvariants(const Graph* g) {

  assert(g != NULL);


  int inDegreeSum = 0;
  int outDegreeSum = 0;
  
  ListMoveToHead(g->verticesList);

  /*Verificar vertices e edges*/
  
  
  if(g->isDigraph==1){
    for (int i = 0; i < g->numVertices; i++) {
      struct _Vertex* vertex = ListGetCurrentItem(g->verticesList);
      inDegreeSum += vertex -> inDegree; // contar de todos os vertices o numero de indegrees
      outDegreeSum += vertex->outDegree; // contar de todos os vertices o numero de outdegrees
      ListMoveToNext(g->verticesList);
    }
      
    if (inDegreeSum == outDegreeSum && inDegreeSum == g->numEdges && ListGetSize(g->verticesList) == g->numVertices){
      // verifica : soma dos outDegrees deve ser igual à soma dos inDegrees
      // verifica : a soma dos outdegrees e dos indegrees = numero de arestas
      printf("verifica");
      return 1;
    }

  } else {

    for (int i = 0; i < g->numVertices; i++) {
      struct _Vertex* vertex = ListGetCurrentItem(g->verticesList);
      outDegreeSum += vertex->outDegree; // contar de todos os vertices o numero de outdegrees
      ListMoveToNext(g->verticesList);
    }
    
    if (outDegreeSum/2 == g->numEdges){ // verifica se o numero de vertices num grafo nao orientado é igual ao número de vertices com out degree
      printf("verifica");
      return 1;
    }

  }


  printf("não verifica");
  return 0;

}



// 0 se alguma falhar se nao 1

// DISPLAYING on the console

void GraphDisplay(const Graph* g) {
  printf("---\n");
  if (g->isWeighted) {
    printf("Weighted ");
  }
  if (g->isComplete) {
    printf("COMPLETE ");
  }
  if (g->isDigraph) {
    printf("Digraph\n");
    printf("Max Out-Degree = %d\n", GraphGetMaxOutDegree(g));
  } else {
    printf("Graph\n");
    printf("Max Degree = %d\n", GraphGetMaxDegree(g));
  }
  printf("Vertices = %2d | Edges = %2d\n", g->numVertices, g->numEdges);

  List* vertices = g->verticesList;
  ListMoveToHead(vertices);
  unsigned int i = 0;
  for (; i < g->numVertices; ListMoveToNext(vertices), i++) {
    printf("%2d ->", i);
    struct _Vertex* v = ListGetCurrentItem(vertices);
    if (ListIsEmpty(v->edgesList)) {
      printf("\n");
    } else {
      List* edges = v->edgesList;
      unsigned int i = 0;
      ListMoveToHead(edges);
      for (; i < ListGetSize(edges); ListMoveToNext(edges), i++) {
        struct _Edge* e = ListGetCurrentItem(edges);
        if (g->isWeighted) {
          printf("   %2d(%4.2f)", e->adjVertex, e->weight);
        } else {
          printf("   %2d", e->adjVertex);
        }
      }
      printf("\n");
    }
  }
  printf("---\n");
}

void GraphListAdjacents(const Graph* g, unsigned int v) {
  printf("---\n");

  unsigned int* array = GraphGetAdjacentsTo(g, v);

  printf("Vertex %d has %d adjacent vertices -> ", v, array[0]);

  for (unsigned int i = 1; i <= array[0]; i++) {
    printf("%d ", array[i]);
  }

  printf("\n");

  free(array);

  printf("---\n");
}
