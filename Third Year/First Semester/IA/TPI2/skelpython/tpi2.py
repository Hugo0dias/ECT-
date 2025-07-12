#encoding: utf8

# YOUR NAME: Hugo Dias
# YOUR NUMBER: 114142

# COLLEAGUES WITH WHOM YOU DISCUSSED THIS ASSIGNMENT (names, numbers):
# - ...
# - ...

from semantic_network import *
from constraintsearch import *
from bayes_net import *
from itertools import product
from collections import defaultdict

class MySN(SemanticNetwork):

    def __init__(self):
        SemanticNetwork.__init__(self)
        # ADD CODE HERE IF NEEDED
        pass

    # General query method, processing different types of
    # relations according to their specificities
    def query(self,entity,relname):
        
        def filter_declarations(relation_type):
            return [
                d.relation.entity2
                for d in ldeclarations
                if isinstance(d.relation, relation_type) and d.relation.name == relname
            ]
        
        ldeclarations = self.query_local(e1=entity)
        rel_types = [type(d.relation) for d in ldeclarations if d.relation.name == relname]
        if not rel_types:
            return []

        Type = max(set(rel_types), key=rel_types.count)

        # Handle Member and Subtype cases
        if Type in [Member, Subtype]:
            return filter_declarations(Type)
    
        # Handle AssocOne case
        if Type == AssocOne:
            values = filter_declarations(AssocOne)
            return [max(set(values), key=values.count)] if values else []

        if Type == AssocNum:
            values = [d.relation.entity2 for d in ldeclarations if isinstance(d.relation, AssocNum) and d.relation.name == relname]
            if values:
                return [sum(values) / len(values)]
            return []

        if Type == AssocSome:
            values = [d.relation.entity2 for d in ldeclarations if isinstance(d.relation, AssocSome) and d.relation.name == relname]
            lparents = [d.relation.entity2 for d in ldeclarations if isinstance(d.relation, Subtype)]
            for p in lparents:
                values += self.query(p, relname)
            return list(set(values))

        return []
    
    # socrates likes mal

class MyBN(BayesNet):

    def __init__(self):
        BayesNet.__init__(self)
        # ADD CODE HERE IF NEEDED
        pass

    def get_ancestors(self, var):

        # Preprocess dependencies into an adjacency list
        adjacencies = defaultdict(list)
        for key, dependencies in self.dependencies.items():
            for mtrue, mfalse, _ in dependencies:
                adjacencies[key].extend(mtrue + mfalse)

        def dfs(node, visited):
            if node in visited:
                return set()
            visited.add(node)
            ancestors = set(adjacencies[node])
            for neighbor in adjacencies[node]:
                ancestors.update(dfs(neighbor, visited))
            return ancestors

        return dfs(var, set())


    def build_graph(self, v1, v2, given):
        graph = []
        variables = set([v1, v2] + given)
        copys = variables.copy()  # Create a copy of the set to iterate over
        for var in copys:
            ancestors = self.get_ancestors(var)
            for ancestor in ancestors:
                graph.append((var, ancestor))
                graph.append((ancestor, var))
                variables.add(ancestor)
        
        copys = variables.copy()  # Update the copy after adding ancestors
        for var in copys:
            if var in self.dependencies:
                mothers = set()
                for (mtrue, mfalse, _) in self.dependencies[var]:
                    mothers.update(mtrue)
                    mothers.update(mfalse)
                for m1 in mothers:
                    for m2 in mothers:
                        if m1 != m2:
                            graph.append((m1, m2))
                            graph.append((m2, m1))
        
        for g in given:
            graph = [edge for edge in graph if g not in edge]
        
        return graph

    def is_connected(self, graph, start, end):

        # Preprocess the graph into an adjacency list
        adjacencys = defaultdict(list)
        for n1, n2 in graph:
            adjacencys[n1].append(n2)

        visited = set()
        stack = [start]

        while stack:
            node = stack.pop()
            if node == end:
                return True
            if node not in visited:
                visited.add(node)
                stack.extend(neighbor for neighbor in adjacencys[node] if neighbor not in visited)

        return False


    def test_independence(self, v1, v2, given):
        graph = self.build_graph(v1, v2, given)
        independent = not self.is_connected(graph, v1, v2)
        return graph, independent

class MyCS(ConstraintSearch):

    def __init__(self,domains,constraints):
        ConstraintSearch.__init__(self,domains,constraints)
        # ADD CODE HERE IF NEEDED
        pass

    def search_all(self,domains=None):
        if domains is None:
            domains = self.domains

        # Start with an empty assignment
        assignment = {}
        solutions = []

        def is_consistent(var, value, assignment):
            """Check if assigning value to var is consistent with the constraints."""
            for v in assignment:
                if (var, v) in self.constraints:
                    constraint = self.constraints[(var, v)]
                    if not constraint(var, value, v, assignment[v]):
                        return False
                elif (v, var) in self.constraints:
                    constraint = self.constraints[(v, var)]
                    if not constraint(v, assignment[v], var, value):
                        return False
            return True

        def handle_ho_constraint(var, value, assignment):
            """Handle higher-order constraints for variables grouped in tuples."""
            for (key, constraint) in self.constraints.items():
                if not isinstance(key, tuple) or var not in key:
                    continue
                
                # Extract the tuple variable (e.g., ORX1, WX1UX2, TX2OF)
                tpvar = key[0]
                if tpvar not in assignment:
                    continue
                
                tpvalue = assignment[tpvar]
                idx = key.index(var) - 1  # Get the index of the variable in the tuple

                # Verify the constraint for the tuple variable
                if not constraint(tpvar, tpvalue, var, value):
                    return False

            return True


        def backtrack(assignment):
            """Recursive backtracking algorithm."""
            # If assignment is complete, save it as a solution
            if len(assignment) == len(domains):
                solutions.append(assignment.copy())
                return

            # Select an unassigned variable (heuristic: minimum remaining values)
            unassigned = [v for v in domains if v not in assignment]
            var = min(unassigned, key=lambda v: len(domains[v]))

            for value in domains[var]:
                if is_consistent(var, value, assignment) and handle_ho_constraint(var, value, assignment):
                    # Assign the value
                    assignment[var] = value

                    # Recursively backtrack
                    backtrack(assignment)

                    # Remove the assignment (backtrack)
                    del assignment[var]


        backtrack(assignment)
        return solutions



def handle_ho_constraint(domains,constraints,variables,constraint):
    v = "".join(variables)  # Create auxiliary variable name
    aux_domain = [t for t in product(*(domains[v] for v in variables)) if constraint(t)]

    # Add auxiliary variable domain
    domains[v] = aux_domain

    # Link the auxiliary variable to the original variables with binary constraints
    for i, var in enumerate(variables):
        def binary_constraint(v1, d1, v2, d2, idx=i):
            return d1[idx] == d2

        constraints[(v, var)] = binary_constraint

    # Remove the higher-order constraint
    if tuple(variables) in constraints:
        del constraints[tuple(variables)]

