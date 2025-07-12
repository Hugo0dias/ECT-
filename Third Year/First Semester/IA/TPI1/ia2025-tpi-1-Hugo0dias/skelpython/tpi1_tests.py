
from tpi1 import *
from cidades import *

city_connections = [ # Ligacoes por estrada
            ('Coimbra', 'Leiria', 73),
            ('Aveiro', 'Agueda', 35),
            ('Porto', 'Agueda', 79),
            ('Agueda', 'Coimbra', 45),
            ('Viseu', 'Agueda', 78),
            ('Aveiro', 'Porto', 78),
            ('Aveiro', 'Coimbra', 65),
            ('Figueira', 'Aveiro', 77),
            ('Braga', 'Porto', 57),
            ('Viseu', 'Guarda', 75),
            ('Viseu', 'Coimbra', 91),
            ('Figueira', 'Coimbra', 52),
            ('Leiria', 'Castelo Branco', 169),
            ('Figueira', 'Leiria', 62),
            ('Leiria', 'Santarem', 78),
            ('Santarem', 'Lisboa', 82),
            ('Santarem', 'Castelo Branco', 160),
            ('Castelo Branco', 'Viseu', 174),
            ('Santarem', 'Evora', 122),
            ('Lisboa', 'Evora', 132),
            ('Evora', 'Beja', 105),
            ('Lisboa', 'Beja', 178),
            ('Faro', 'Beja', 147),
            ('Braga', 'Guimaraes', 25),
            ('Porto', 'Guimaraes', 44),
            ('Guarda', 'Covilha', 46),
            ('Viseu', 'Covilha', 57),
            ('Castelo Branco', 'Covilha', 62),
            ('Guarda', 'Castelo Branco', 96),
            ('Lamego','Guimaraes', 88),
            ('Lamego','Viseu', 47),
            ('Lamego','Guarda', 64),
            ('Portalegre','Castelo Branco', 64),
            ('Portalegre','Santarem', 157),
            ('Portalegre','Evora', 194) 
]

city_coordinates = {  # City coordinates
             'Aveiro': (41,215),
             'Figueira': ( 24, 161),
             'Coimbra': ( 60, 167),
             'Agueda': ( 58, 208),
             'Viseu': ( 104, 217),
             'Braga': ( 61, 317),
             'Porto': ( 45, 272),
             'Lisboa': ( 0, 0),
             'Santarem': ( 38, 59),
             'Leiria': ( 28, 115),
             'Castelo Branco': ( 140, 124),
             'Guarda': ( 159, 204),
             'Evora': (120, -10),
             'Beja': (125, -110),
             'Faro': (120, -250),
             'Guimaraes': ( 71, 300),
             'Covilha': ( 130, 175),
             'Lamego' : (125,250),
             'Portalegre': (130,170) 
}


cidades_portugal = Cidades(city_connections,city_coordinates)

p = SearchProblem(cidades_portugal,'Braga','Faro')

t = MyTree(p,'depth')
print('(depth)',t.search2())
print('open=',t.num_open,"solution=",t.num_solution,'skipped=',t.num_skipped,'closed=',t.num_closed)
n = t.solution
print('Solution node data:',n.action,n.depth,n.cost,n.heuristic)
print('\n')

t = MyTree(p,'depth',True)
print('(depth,improve)',t.search2())
print('open=',t.num_open,"solution=",t.num_solution,'skipped=',t.num_skipped,'closed=',t.num_closed)
n = t.solution
print('Solution node data:',n.action,n.depth,n.cost,n.heuristic)
print('open_nodes=',t.open_nodes)
print('\n')

t = MyTree(p,'informeddepth')
print('(informeddepth)',t.search2())
print('open=',t.num_open,"solution=",t.num_solution,'skipped=',t.num_skipped,'closed=',t.num_closed)
n = t.solution
print('Solution node data:',n.action,n.depth,n.cost,n.heuristic)
print('\n')

t = MyTree(p,'informeddepth',True)
print('(informeddepth,improve)',t.search2())
print('open=',t.num_open,"solution=",t.num_solution,'skipped=',t.num_skipped,'closed=',t.num_closed)
n = t.solution
print('Solution node data:',n.action,n.depth,n.cost,n.heuristic)
print('open_nodes=',t.open_nodes)
print('\n')

t = MyTree(p,'A*')
print('(A*)',t.search2())
print('open=',t.num_open,"solution=",t.num_solution,'skipped=',t.num_skipped,'closed=',t.num_closed)
n = t.solution
print('Solution node data:',n.action,n.depth,n.cost,n.heuristic)
print('Admissible:',t.check_admissible(n))
print('\n')


cidades2 = CitiesWithInflatedHeuristic(city_connections,city_coordinates)
p = SearchProblem(cidades2,'Braga','Faro')
t = MyTree(p,'A*')
print('(A*,inflated)',t.search2())
print('open=',t.num_open,"solution=",t.num_solution,'skipped=',t.num_skipped,'closed=',t.num_closed)
n = t.solution
print('Solution node data:',n.action,n.depth,n.cost,n.heuristic)
print('Admissible:',t.check_admissible(n))
print('\n')


bw = MyBlocksWorld()

a='a'
b='b'
c='c'
d='d'
e='e'
f='f'
g='g'
h='h'

initial_state = { Floor(a), Floor(b), #    _
        Holds(e), On(c,d), Free(a),   #   / \
        Floor(d), Free(c), Free(b)  } #  |  (e)
                                      #  |
                                      #  |               |c|
                                      # _|___|a|___|b|___|d|_    
                                      # 

goal_state    = { Floor(c), On(d,c),  #    _
        On(e,d), On(a,e), Floor(b) }  #   / \
                                      #  |  ( )           |a|
                                      #  |                |e|
                                      #  |                |d|
                                      # _|__________|b|___|c|___    
                                      #

p = SearchProblem(bw,initial_state,goal_state)
print('\nheuristic=',bw.heuristic(initial_state,goal_state))

t = MyTree(p)
t.search2()
print('(breadth) open=',t.num_open,"solution=",t.num_solution,'skipped=',t.num_skipped,'closed=',t.num_closed)
print(t.plan,len(t.plan))
print('Admissible:',t.check_admissible(t.solution))

t = MyTree(p,'A*')
t.search2()
print('(A*) open=',t.num_open,"solution=",t.num_solution,'skipped=',t.num_skipped,'closed=',t.num_closed)
print(t.plan,len(t.plan))
print('Admissible:',t.check_admissible(t.solution))

##

initial_state = { Floor(a), Floor(b),  #    _
        Floor(d), Holds(e), On(c,d),   #   / \
        Free(a), Free(b), On(f,c),     #  |  (e)             |g|
        On(g,f), Free(g) }             #  |                  |f|
                                       #  |                  |c|
                                       # _|___|a|____|b|_____|d|_    
                                       # 

goal_state    = { Floor(c), On(d,c),   #    _
        On(e,d), On(a,e), Floor(b),    #   / \
        On(f,g), On(g,b) }             #  |  ( )           |a|
                                       #  |          |f|   |e|
                                       #  |          |g|   |d|
                                       # _|__________|b|___|c|___    
                                       #

print('\nheuristic=',bw.heuristic(initial_state,goal_state))

p = SearchProblem(bw,initial_state,goal_state)
t = MyTree(p,'A*')
t.search2()
print('(A*) open=',t.num_open,"solution=",t.num_solution,'skipped=',t.num_skipped,'closed=',t.num_closed)
print(t.plan,len(t.plan))
print('Admissible:',t.check_admissible(t.solution))

##

initial_state = { Floor(a), Floor(b),       #    _
        Floor(d), Holds(e), On(c,d),        #   / \               |h|
        Free(a), Free(b), On(f,c), On(g,f), #  |  (e)             |g|
        On(h,g), Free(h) }                  #  |                  |f|
                                            #  |                  |c|
                                            # _|___|a|____|b|_____|d|_    
                                            # 

goal_state    = { Floor(d), Floor(b),       #    _
     On(f,g), On(g,b), Free(d), Free(f) }   #   / \
                                            #  |  ( )
                                            #  |          |f|
                                            #  |          |g|
                                            # _|__________|b|___|d|___    
                                            #

print('\nheuristic=',bw.heuristic(initial_state,goal_state))
p = SearchProblem(bw,initial_state,goal_state)
t = MyTree(p,'A*')
t.search2()
print('(A*) open=',t.num_open,"solution=",t.num_solution,'skipped=',t.num_skipped,'closed=',t.num_closed)
print(t.plan,len(t.plan))
print('Admissible:',t.check_admissible(t.solution))


##

initial_state = { Floor(a), Floor(b),  #    _
    Floor(d), Holds(e), On(c,d),       #   / \               |h|
    Free(a), Free(b), On(f,c),         #  |  (e)             |g|
    On(g,f), On(h,g), Free(h) }        #  |                  |f|
                                       #  |                  |c|
                                       # _|___|a|____|b|_____|d|_    
                                       # 

goal_state    = { Floor(d), Floor(b),  #    _
        Free(d), Free(b) }             #   / \
                                       #  |  ( )
                                       #  |
                                       #  |
                                       # _|__________|b|___|d|___    
                                       #

print('\nheuristic=',bw.heuristic(initial_state,goal_state))
p = SearchProblem(bw,initial_state,goal_state)
t = MyTree(p,'A*')
t.search2()
print('(A*) open=',t.num_open,"solution=",t.num_solution,'skipped=',t.num_skipped,'closed=',t.num_closed)
print(t.plan,len(t.plan))
print('Admissible:',t.check_admissible(t.solution))


