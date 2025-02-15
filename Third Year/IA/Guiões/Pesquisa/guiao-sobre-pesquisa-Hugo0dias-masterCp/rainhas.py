
from constraintsearch import *


def queen_constraint(r1,c1,r2,c2):
    l1 = int(r1[1:])
    l2 = int(r2[1:])
    if c1==c2:
        return False
    if abs(l1-l2)==abs(c1-c2):
        return False
    return True

def make_constraint_graph(n):
    queens = [ 'R'+str(i+1) for i in range(n) ]
    return { (X,Y):queen_constraint for X in queens for Y in queens if X!=Y }

def make_domains(n):
    queens = [ 'R'+str(i+1) for i in range(n) ]
    cols = [ i+1 for i in range(n) ]
    return { r:cols for r in queens }

def map_constraint(r1,c1,r2,c2):
    return c1 != c2

def make_constraint(mapa):
    return {(X,Y): map_constraint for X in mapa for Y in mapa[X] if X != Y}

def make_domains(region, colors):
    return {r: colors for r in region}

cs = ConstraintSearch(make_domains(4),make_constraint_graph(4))

print(cs.search())

