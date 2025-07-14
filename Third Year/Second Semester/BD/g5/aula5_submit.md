# BD: Guião 5


## ​Problema 5.1
 
### *a)*

```
π Ssn,Fname,Lname,Minit,Pname (employee ⨝ (Essn=Ssn) (works_on ⨝ (Pno = Pnumber) project))
```


### *b)* 

```
(employee ⨝ Super_ssn=SuperEmp (ρ SuperEmp←Ssn (π Ssn (σ Fname='Carlos' ∧ Minit='D' ∧ Lname='Gomes' (employee)))))
```

### *c)* 

```
π Pname, Soma_Horas ((γ Pno; sum(Hours) -> Soma_Horas (works_on)) ⨝ Pno=Pnumber project)
```

### *d)* 

```
π Fname,Minit,Lname (σ Dno = 3 ∧ Hours > 20 ∧ Pname = 'Aveiro Digital' ((employee ⨝ Ssn = Essn works_on) ⨝ Pno=Pnumber project))
```


### *e)* 

```
π Fname, Minit, Lname (σ Essn = null (works_on ⟗ Essn = Ssn employee))
```


### *f)* 

```
γ Dname; avg(Salary) -> avg_Salary (σ Sex='F' (employee ⨝ Dno=Dnumber department))
```


### *g)* 

```
σ Dependentes > 2 (γ Fname, Minit, Lname; count(Fname) -> Dependentes (π Fname, Minit, Lname, Ssn, Dependent_name, Essn (employee ⨝ Ssn = Essn dependent)))
```


### *h)* 

```
σ Essn=null ((employee ⨝ Ssn=Mgr_ssn department) ⟕ Ssn = Essn dependent)
```


### *i)* 

```
π Fname,Minit,Lname,Address (σ Dlocation≠'Aveiro' ∧ Plocation='Aveiro' (( project ⨝Pnumber=Pno (employee ⨝Ssn=Essn works_on) ) ⨝Dno=Dnumber (π department.Dnumber,Dname,Dlocation (department ⨝department.Dnumber=dept_location.Dnumber dept_location)) ))
```


## ​Problema 5.2

### *a)*

```
π nif,nome (σ numero=NULL ((encomenda) ⟖ fornecedor=nif (fornecedor)))
```

### *b)* 

```
π nome,avg_units ((γ codProd; avg(unidades) -> avg_units (item)) ⨝ codProd=codigo (produto))
```


### *c)* 

```
γ avg(count_products) -> avg_products (γ numEnc; count(numEnc)->count_products (item))
```

### *d)* 

```
π fornecedor.nome, produto.nome, count_products ((γ nif, nome,codProd; count(codProd)->count_products (fornecedor ⨝ nif=fornecedor (item ⨝ numEnc=numero encomenda))) ⨝ codProd=codigo (produto))
```

## ​Problema 5.3

### *a)*
```
π nome, Utente (σ Utente = null (paciente ⟗ Utente = numUtente (ρ Utente <- numUtente (prescricao))))
```

### *b)* 

```
 γ especialidade; count(especialidade) -> Contagem (prescricao ⨝ numSNS = numMedico medico)
```

### *c)* 

```
γ farmacia; count(farmacia) -> Count (prescricao ⨝ nome = farmacia farmacia) 
```


### *d)* 

```
 (π nomeFarmaco (presc_farmaco)) - (π nomeFarmaco (σ numRegFarm = 906 presc_farmaco))
```

### *e)* 

```
π farmaceutica.nome, prescricao.farmacia, vendas (farmaceutica ⨝ farmaceu = farmaceutica.numReg ( γ farmaceu,farmacia; count(farmaceu) -> vendas (ρ farmaceu <- farmaco.numRegFarm (farmaco ⨝ nomeFarmaco = farmaco.nome (σ farmacia ≠ null (π numPresc, farmacia, dataProc, numRegFarm, PrescricaoN, nomeFarmaco (presc_farmaco ⨝ PrescricaoN = numPresc (ρ PrescricaoN <- numPresc (prescricao)))))))))
```

### *f)* 

```
π paciente.nome (σ Count > 1 (γ nome; count(nome) -> Count (π numUtente, NomeMedico,paciente.nome  (ρ NomeMedico <- medico.nome	(medico ⨝ numSNS = numMedico (paciente ⨝ numUtente = Utente (ρ Utente <- numUtente (prescricao))))))))
```
