#include "Graph.h"
#include "GraphTopologicalSorting.h"

int main(void) {

  // Sem ciclos 

  Graph* g = GraphCreate(7, 1, 0);
  GraphAddEdge(g, 0, 1);
  GraphAddEdge(g, 0, 2);
  GraphAddEdge(g, 1, 3);
  GraphAddEdge(g, 3, 6);
  GraphAddEdge(g, 4, 3);
  GraphAddEdge(g, 6, 5);
  GraphAddEdge(g, 2, 4);
  
  GraphDisplay(g);

  printf(" *** 1st Algorithm ***\n");

  GraphTopoSort* result = GraphTopoSortComputeV2(g);

  GraphTopoSortDisplaySequence(result);

  GraphTopoSortDestroy(&result); 

  // Com ciclos 

  Graph* g2 = GraphCreate(2, 1, 0);
  GraphAddEdge(g2, 0, 1);
  GraphAddEdge(g2, 1, 0);

  printf(" *** 2st Algorithm ***\n");

  GraphTopoSort* result2 = GraphTopoSortComputeV2(g2);

  GraphTopoSortDisplaySequence(result2);

  GraphTopoSortDestroy(&result2); 
  

  GraphDestroy(&g);
  GraphDestroy(&g2);

  return 0;
}