#STUDENT NAME: Hugo Marques Dias
#STUDENT NUMBER: 114142

#DISCUSSED TPI-1 WITH: (names and numbers):


from tree_search import *
from strips import *
from blocksworld import *

class MyNode(SearchNode):

    def __init__(self,state,parent,action=None,cost=0, heuristic=0):
        super().__init__(state,parent)
        self.action = action # accao que levou ao estado
        self.depth = parent.depth + 1 if parent else 0 # depth da node na arvore
        self.cost = cost # custo do caminho da raiz ate a node
        self.heuristic = heuristic # valor da heuristica
class MyTree(SearchTree):

    @property
    def cost(self):
        return self.solution.cost if self.solution else 0

    def __init__(self,problem, strategy='breadth',improve=False):
        super().__init__(problem,strategy)
        self.num_open = 0 # number of open nodes, i.e. nodes in the queue
        self.num_solution = 0 # number of solutions found
        self.num_skipped = 0 # number of nodes removed from the queue but not expanded
        self.num_closed = 0 # number of nodes that were expanded
        self.improve = improve # flag to improve the solution
        root = MyNode(problem.initial, None, action=None, cost=0, heuristic=problem.domain.heuristic(problem.initial, problem.goal))
        self.open_nodes = [root] # open nodes
        self.plan = [] 

    def astar_add_to_open(self,lnewnodes):
        self.open_nodes.extend(lnewnodes) # Adiciona os novos nodes a lista de open nodes
        self.open_nodes.sort(key=lambda node: (node.cost + node.heuristic, node.depth, node.state)) # Ordena a lista de open nodes por prioridade

    def informeddepth_add_to_open(self,lnewnodes):
        lnewnodes.sort(key=lambda node: (node.cost + node.heuristic, node.depth, node.state)) # Ordena a lista de new nodes por prioridade
        self.open_nodes = lnewnodes + self.open_nodes # Adiciona os novos nodes a lista de open nodes

    def check_admissible(self,node):
        #Assume that the given "node" is a solution node
        print("node cost: ", node.cost)
        current = node
        while current.parent: # Enquanto houver parent
            if current.parent.heuristic > node.cost: # Compara os valores da heuristica do parent com o custo do node
                return False # Se o custo do node for menor que a heuristica do parent, retorna False porque nao e admissivel
            current = current.parent # Se nao, continua a verificar os outros parent nodes
        return True

    def get_plan(self, node):
        self.plan = []
        while node != None: # Enquanto houver node
            if node.action != None:  # Se houver accao
                self.plan.append(node.action) # Adiciona a accao ao plano
            node = node.parent # Verifica todos os parents
        self.plan.reverse() # Inverte o plano para que a accao mais recente seja a primeira
        return self.plan


    def search2(self):
        while self.open_nodes:
            node = self.open_nodes.pop(0)
            self.num_open = len(self.open_nodes)
            if self.problem.goal_test(node.state):
                if self.improve == False:  # Se nao for para melhorar
                    self.solution = node   # retorna logo a primeira solucao encontrada
                    self.num_solution = 1 # Apenas uma solucao
                    if self.check_admissible(self.solution): # verifica se a solucao e admissivel          
                        return self.get_plan(node)  # Return the plan if a solution is found
                else: # Se for para melhorar
                    if self.solution is None or node.cost < self.solution.cost: # Se a solucao for None ou o custo do node for menor que o custo da solucao
                        self.solution = node # A solucao passa a ser o node
                        self.num_solution += 1  # Incrementa o numero de solucoes

                if self.strategy in ['astar']: # Se a estrategia for astar
                    if self.solution is None or node.cost < self.solution.cost: # Metodo igual ao de cima
                        self.solution = node
                        self.num_solution += 1
                        if not self.improve: # Se nao for para melhorar
                            if self.check_admissible(self.solution): # verifica se a solucao e admissivel
                                return self.get_plan(self.solution) # Retorna o plano
                            else:
                                return None
                            
            if self.improve == False: # Se nao for para melhorar
                self.num_closed += 1 # Incrementa o numero de nodes fechados
            

            if self.improve and self.solution and (node.cost + node.heuristic) >= self.solution.cost: # Se for para melhorar e houver solucao e o custo do node mais a heuristica for maior ou igual ao custo da solucao
                self.num_skipped += 1 # Incrementa o numero de nodes ignorados
                continue
                
                

            lnewnodes = []
            for a in self.problem.domain.actions(node.state):
                newstate = self.problem.domain.result(node.state, a)
                if newstate not in self.get_path(node):
                    newnode = MyNode(newstate, node, action=a, cost=node.cost + self.problem.domain.cost(node.state, a), heuristic=self.problem.domain.heuristic(newstate, self.problem.goal))
                    lnewnodes.append(newnode)

            if not lnewnodes: # Se nao houver novos nodes
                self.num_skipped += 1 # Incrementa o numero de nodes ignorados (para o A* + nao improve)
            if self.improve == True: # Se for para melhorar
                self.num_closed += 1 # Incrementa o numero de nodes fechados

            if self.strategy == 'astar': # Se a estrategia for astar
                self.astar_add_to_open(lnewnodes) # Chama a funcao astar_add_to_open
            elif self.strategy == 'informeddepth': # Se a estrategia for informeddepth
                self.informeddepth_add_to_open(lnewnodes) # Chama a funcao informeddepth_add_to_open
            else:
                self.add_to_open(lnewnodes) # Se nao for nenhuma das acima adiciona os novos nodes a lista de open nodes

        if self.improve and self.solution: # Se a flag de improve estiver ativa e encontrar a melhor solucao
            print(4)
            return self.get_plan(self.solution) # Retorna o plano
        return None
    
        
    # if needed, auxiliary methods can be added here

class MyBlocksWorld(STRIPS):


    def heuristic(self, state, goal):
        out_of_place = 0
        for predicate in goal:
            if predicate not in state:
                out_of_place += 1 # numero de blocos fora do lugar
        return out_of_place * 2 # multiplicado por dado o facto de o necessitar do hold sempre que muda o bloco
    
    # Heuristica nao muito ideal mas realista
