from constraintsearch import *

amigos = ["Andre", "Bernardo", "Claudio"]



def make_constraint_graph(n):
    return { (X,Y):map_constraint for X in amigos for Y in amigos if X!=Y }


def make_domains(n):
    return {amigo: [(chapeu, bicicleta) for chapeu in amigos for bicicleta in amigos] for amigo in amigos}

def map_constraint(a1,t1,a2,t2):
    b1,c1 = t1
    b2, c2 = t2
    if b1 == b2 or c1 == c2:
        return False
    if a1 == b1 or a1 == c1 or a2 == c2 or a2 == b2:
        return False
    if b1 == c1 or b2==c2:
        return False
    if c1 == 'Claudio' and b1 != 'Bernardo':
        return False
    if c2 == 'claudio' and b2 != 'Bernardo':
        return False
    return True

cs = ConstraintSearch(make_domains(amigos), make_constraint_graph(amigos))

print(cs.search())