
from tpi2 import *

from itertools import product

import time
import itertools

# ----------------------------------------------------------------------
# Semantic networks
# ----------------------------------------------------------------------

print("\n-- ## Ex. 1 --------------------------------")

z = MySN()

z.insert('descartes',Subtype('mammal','vertebrate'))
z.insert('darwin',Subtype('mammal','vertebrate'))
z.insert('darwin',AssocSome('mammal','likes','milk'))

z.insert('descartes',Subtype('man','mammal'))
z.insert('darwin',Subtype('man','mammal'))
z.insert('darwin',AssocSome('man','likes','meat'))
z.insert('bacon',AssocOne('man','likes','vegetables'))
z.insert('descartes',AssocNum('man','hasWeight',80))
z.insert('descartes',AssocNum('man','hasWeight',70))
z.insert('descartes',AssocNum('man','hasHeight',1.75))
z.insert('descartes',AssocNum('man','hasHeight',1.80))

z.insert('bacon',AssocSome('philosopher','likes','philosophy'))

z.insert('descartes',Member('socrates','man'))
z.insert('damasio',Member('socrates','philosopher'))
z.insert('descartes',AssocSome('socrates','professorOf','philosophy'))
z.insert('descartes',AssocSome('socrates','professorOf','mathematics'))
z.insert('simoes',AssocNum('socrates','professorOf','mathematics'))
z.insert('simao',AssocSome('socrates','professorOf','mathematics'))
z.insert('descartes',AssocNum('socrates','hasHeight',1.75))
z.insert('nunes',AssocOne('socrates','hasHeight',1.70))
z.insert('bacon',AssocNum('socrates','hasHeight',1.80))
z.insert('simao',AssocOne('socrates','hasFather','sophroniscus'))
z.insert('nunes',AssocOne('socrates','hasFather','sophroniscus'))
z.insert('aristotle',AssocOne('socrates','hasFather','plato'))
z.insert('bacon',AssocNum('socrates','hasFather','plato'))
z.insert('simao',AssocOne('socrates','hasMother','phaenarete'))
z.insert('socrates',AssocSome('socrates','likes','sophroniscus'))
z.insert('sophroniscus',AssocSome('socrates','likes','phaenarete'))
z.insert('bacon',AssocSome('socrates','likes','mathematics'))
z.insert('bacon',AssocSome('socrates','dislikes','meat'))


z.insert('descartes',Member('plato','man'))
z.insert('descartes',AssocSome('plato','professorOf','philosophy'))
z.insert('simao',AssocSome('plato','professorOf','philosophy'))
z.insert('simao',AssocSome('aristotle','hasFather','ariston'))

z.insert('descartes',Member('aristotle','man'))
z.insert('simao',AssocOne('aristotle','hasFather','nicomachus'))


print('\nsocrates member:',z.query('socrates','member'))

print('\nsocrates subtype:',z.query('socrates','subtype'))

print('\nsocrates hasHeight:',z.query('socrates','hasHeight'))

print('\nsocrates hasWeight:',z.query('socrates','hasWeight'))

print('\nsocrates likes:',z.query('socrates','likes'))

print('\nsocrates hasFather:',z.query('socrates','hasFather'))


# ----------------------------------------------------------------------
# Bayesian networks
# ----------------------------------------------------------------------

print("\n-- ## Ex. 2 --------------------------------")




bn = MyBN()

bn.add('a',[],[],0.003)

bn.add('b_a',[],[],0.002)

bn.add('c_s',['a'],[],0.48)
bn.add('c_s',[],['a'],0.08)

bn.add('d',[],[],0.01)

bn.add('m_f',[],[],0.01)

bn.add('b_v',['c_s','b_a'],[],0.18)
bn.add('b_v',['c_s'],['b_a'],0.02)
bn.add('b_v',['b_a'],['c_s'],0.90)
bn.add('b_v',[],['c_s','b_a'],0.68)

bn.add('s_m',[],[],0.05)

bn.add('s_p',[],[],0.3)

bn.add('v_p',['m_f','d','b_v'],[],0.003)
bn.add('v_p',['m_f','d'],['b_v'],0.12)
bn.add('v_p',['m_f','b_v'],['d'],0.08)
bn.add('v_p',['m_f'],['d','b_v'],0.01)
bn.add('v_p',['d','b_v'],['m_f'],0.04)
bn.add('v_p',['d'],['m_f','b_v'],0.07)
bn.add('v_p',['b_v'],['m_f','d'],0.13)
bn.add('v_p',[],['m_f','d','b_v'],0.09)

bn.add('h',['b_v'],[],0.44)
bn.add('h',[],['b_v'],0.89)

bn.add('s_s',['s_m','m_f','b_v'],[],0.30)
bn.add('s_s',['s_m','m_f'],['b_v'],0.21)
bn.add('s_s',['s_m','b_v'],['m_f'],0.34)
bn.add('s_s',['m_f','b_v'],['s_m'],0.15)
bn.add('s_s',['s_m'],['m_f','b_v'],0.12)
bn.add('s_s',['m_f'],['s_m','b_v'],0.14)
bn.add('s_s',['b_v'],['s_m','m_f'],0.132)
bn.add('s_s',[],['s_m','m_f','b_v'],0.44)

bn.add('s_t',['d'],[],0.08)
bn.add('s_t',[],['d'],0.002)

bn.add('s_q',['s_p','v_p'],[],0.008)
bn.add('s_q',['s_p'],['v_p'],0.4)
bn.add('s_q',['v_p'],['s_p'],0.51)
bn.add('s_q',[],['s_p','v_p'],0.13)

bn.add('f_s',[],[],0.1)

bn.add('c_c',['s_s'],[],0.49)
bn.add('c_c',[],['s_s'],0.023)

bn.add('car_s',['c_c','s_t','s_q' ,'f_s' ],[],0.091)
bn.add('car_s',['c_c','s_t','s_q'],['f_s'],0.081)
bn.add('car_s',['c_c','s_t','f_s'],['s_q'],0.045)
bn.add('car_s',['s_t','s_q','f_s'],['c_c'],0.052)
bn.add('car_s',['c_c','f_s','s_q'],['s_t'],0.087)
bn.add('car_s',['c_c','s_t'],['s_q' ,'f_s'],0.065)
bn.add('car_s',['c_c','s_q'],['s_t','f_s'],0.043)
bn.add('car_s',['c_c','f_s'],['s_t','s_q'],0.035)
bn.add('car_s',['s_t','s_q'],['c_c','f_s'],0.054)
bn.add('car_s',['s_t','f_s'],['c_c','s_q'],0.056)
bn.add('car_s',['s_q','f_s'],['c_c','s_t'],0.045)
bn.add('car_s',['c_c'],['s_t','s_q','f_s'],0.067)
bn.add('car_s',['s_t'],['c_c','s_q','f_s'],0.078)
bn.add('car_s',['s_q'],['c_c','s_t','f_s'],0.031)
bn.add('car_s',['f_s'],['c_c','s_t','s_q'],0.034)
bn.add('car_s',[],['c_c','s_t','s_q','f_s'],0.023)

print('\ns_t, c_c, [d, m_f, b_v, s_m]:',bn.test_independence('s_t','c_c',['d','m_f','b_v','s_m']))
print('\ns_t, c_c, [d]:',bn.test_independence('s_t','c_c',['d']))
print('\ns_t, s_q, [d]:',bn.test_independence('s_t','s_q',['d']))
print('\nd, b_v, [car_s]:',bn.test_independence('d','b_v',['car_s']))
print('\nd, b_v, [a]:',bn.test_independence('d','b_v',['a']))

# ---------------------------------------------------------------------
#             T W O
#           + T W O
#         ----------
#           F O U R        ( basic formulation from TP classes )
 
print("\n-- ## Ex. 3) --------------------------------")

variables = [ 'T','W','O','F','U','R' ]

# domains
# -------

digits = list(range(10))

domains = { x:digits for x in variables if x!='F' }
domains|= { x:[0,1] for x in ['F','X1','X2'] }

# tuple variables for higher-order constraints

domains['ORX1'] = product(domains['O'],domains['R'],domains['X1'])
domains['ORX1'] = [ t for t in domains['ORX1'] if 2*t[0] == t[1] + 10*t[2] ]

domains['WX1UX2']=product(domains['W'],domains['X1'],domains['U'],domains['X2'])
domains['WX1UX2']=[t for t in domains['WX1UX2'] if 2*t[0]+t[1]==t[2]+10*t[3]]

domains['TX2OF'] = product(domains['T'],domains['X2'],domains['O'],domains['F'])
domains['TX2OF'] = [t for t in domains['TX2OF'] if 2*t[0]+t[1] == t[2]+10*t[3]]

# constraints
# ------------

# binary constraints: all original variables must be different

constraints = { (v1,v2):(lambda v1,x1,v2,x2: x1!=x2)
                for v1 in variables for v2 in variables if v1!=v2 }

# handling higher order constraints

constraint0 = lambda vt,xt,v,x: xt[0]==x
constraint1 = lambda vt,xt,v,x: xt[1]==x
constraint2 = lambda vt,xt,v,x: xt[2]==x
constraint3 = lambda vt,xt,v,x: xt[3]==x

extra = {}

extra['ORX1','O'] = constraint0
extra['ORX1','R'] = constraint1
extra['ORX1','X1'] = constraint2

extra['WX1UX2','W'] = constraint0
extra['WX1UX2','X1'] = constraint1
extra['WX1UX2','U'] = constraint2
extra['WX1UX2','X2'] = constraint3

extra['TX2OF','T'] = constraint0
extra['TX2OF','X2'] = constraint1
extra['TX2OF','O'] = constraint2
extra['TX2OF','F'] = constraint3

constraints |= extra

def invert_constraint(constraints,e):
    return lambda w2,x2,w1,x1:constraints[e](w1,x1,w2,x2)

constraints |= { (v2,v1):invert_constraint(extra,(v1,v2)) for v1,v2 in extra }

# Constraint search object
cs = MyCS(domains,constraints)

# Solving
cs = MyCS(domains,constraints)

import time
t0 = time.process_time()
solutions = cs.search_all()
print(time.process_time()-t0,'seconds')

for s in solutions:
    print({v:s[v] for v in variables})
print(len(solutions))


# ---------------------------------------------------------------------
#             T W O
#           + T W O
#         ----------       ( much simplified formulation
#           F O U R          using function handle_ho_constraint() )

print("\n-- ## Ex. 4) --------------------------------")
 
variables = [ 'T','W','O','F','U','R' ]

domains = { v:digits for v in variables if v!='F' }
domains|= { v:[0,1] for v in ['F','X1','X2'] }

# constraints
# ------------

# Binary constraints: all original variables must be different
constraints = { (v1,v2):(lambda v1,x1,v2,x2: x1!=x2)
                for v1 in variables for v2 in variables if v1!=v2 }

# Higher order constraints
handle_ho_constraint(domains,constraints,['O','R','X1'],lambda t : 2*t[0] == t[1]+10*t[2])
handle_ho_constraint(domains,constraints,['W','X1','U','X2'],lambda t : 2*t[0] + t[1] == t[2]+10*t[3])
handle_ho_constraint(domains,constraints,['T','X2','O','F'],lambda t : 2*t[0] + t[1] == t[2]+10*t[3])

print('Variables:',list(domains.keys()))
print('Edges:',list(constraints.keys()))

# Constraint search object
cs = MyCS(domains,constraints)

# Solving
import time
t0 = time.process_time()
solutions = cs.search_all()
print(time.process_time()-t0,'seconds')

for s in solutions:
    print({v:s[v] for v in variables})
print(len(solutions))


