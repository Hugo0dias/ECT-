
class ConstraintSearch:

    # self.domains é um dicionário com o domínio de cada variável;
    # self.constraints e' um dicionário com a restrição aplicável a cada aresta;
    def __init__(self,domains,constraints):
        self.domains = domains
        self.constraints = constraints
        self.calls = 0

    # domains é um dicionário com os domínios actuais
    # de cada variável
    # ( ver acetato "Pesquisa com propagacao de restricoes
    #   em problemas de atribuicao - algoritmo" )
    def search(self,domains=None):
        self.calls += 1 
        
        if domains==None:
            domains = self.domains

        # se alguma variavel tiver lista de valores vazia, falha
        if any([lv==[] for lv in domains.values()]):
            return None

        # se todas as variaveis exactamente um valor, sucesso
        if all([len(lv)==1 for lv in list(domains.values())]):
            return { v:lv[0] for (v,lv) in domains.items() }
       
        # continuação da pesquisa
        for var in domains.keys():
            if len(domains[var])>1:
                for val in domains[var]:
                    newdomains = dict(domains)
                    newdomains[var] = [val]
                    self.propagate(newdomains,var)
                    solution = self.search(newdomains)
                    if solution != None:
                        return solution
        return None

    def propagate(self,domains,var):
        edges = [ e for e in self.constraints if e[1]==var ]
        while edges!=[]:
            (vj,vi) = edges.pop()
            constraint = self.constraints[vj,vi]
            newdomain = [ xj for xj in domains[vj] 
                             if any(constraint(vj,xj,vi,xi) for xi in domains[vi] ) ]
            if len(newdomain)<len(domains[vj]):
                domains[vj] = newdomain
                edges += [ e for e in self.constraints if e[1]==vj ]

