# BD: Guião 6

## Problema 6.1

### *a)* Todos os tuplos da tabela autores (authors);

```
SELECT * from authors;

```

### *b)* O primeiro nome, o último nome e o telefone dos autores;

```
SELECT au_fname,au_lname,phone from authors;
```

### *c)* Consulta definida em b) mas ordenada pelo primeiro nome (ascendente) e depois o último nome (ascendente); 

```
SELECT au_fname,au_lname,phone FROM authors
ORDER BY au_fname,au_lname;
```

### *d)* Consulta definida em c) mas renomeando os atributos para (first_name, last_name, telephone); 

```
SELECT au_fname as first_name, au_lname as last_name, phone as telephone FROM authors
ORDER BY first_name,last_name;

```

### *e)* Consulta definida em d) mas só os autores da Califórnia (CA) cujo último nome é diferente de ‘Ringer’; 

```
SELECT au_fname as first_name, au_lname as last_name, phone as telephone FROM authors
WHERE state='CA' AND au_lname!='Ringer'
ORDER BY first_name,last_name;

```

### *f)* Todas as editoras (publishers) que tenham ‘Bo’ em qualquer parte do nome; 

```
SELECT * from publishers
WHERE pub_name LIKE '%Bo%';

```

### *g)* Nome das editoras que têm pelo menos uma publicação do tipo ‘Business’; 

```

SELECT DISTINCT type, pub_name,titles.pub_id as publisherIDT from publishers,titles
WHERE titles.pub_id = publishers.pub_id AND type='Business'

```

### *h)* Número total de vendas de cada editora; 

```
SELECT DISTINCT publishers.pub_name,COUNT(sales.qty) as Vendas
FROM sales Inner Join titles on sales.title_id = titles.title_id
Inner Join publishers on titles.pub_id = publishers.pub_id
GROUP BY publishers.pub_name;
```

### *i)* Número total de vendas de cada editora agrupado por título; 

```

SELECT DISTINCT publishers.pub_name,COUNT(sales.qty) as Vendas,titles.title
FROM sales Inner Join titles on sales.title_id = titles.title_id
Inner Join publishers on titles.pub_id = publishers.pub_id
GROUP BY publishers.pub_name,titles.title;

```

### *j)* Nome dos títulos vendidos pela loja ‘Bookbeat’; 

```
SELECT stores.stor_name, titles.title
FROM titles Inner Join sales on titles.title_id = sales.title_id
Inner Join stores on sales.stor_id = stores.stor_id
WHERE stores.stor_name = 'Bookbeat';
```

### *k)* Nome de autores que tenham publicações de tipos diferentes; 


```
SELECT authors.au_fname, authors.au_lname, COUNT(titles.type) AS 'Número de Tipo de Publicações'
FROM titles Inner Join titleauthor on titles.title_id=titleauthor.title_id
Inner Join authors on titleauthor.au_id = authors.au_id
GROUP BY authors.au_fname, authors.au_lname
HAVING Count(titles.type) > 1;
```

### *l)* Para os títulos, obter o preço médio e o número total de vendas agrupado por tipo (type) e editora (pub_id);

```
SELECT titles.pub_id,titles.[type], AVG(titles.price) AS 'Preço médio', sum(sales.qty) AS 'N total de vendas'
FROM titles Join sales on titles.title_id = sales.title_id
GROUP BY titles.[type],titles.pub_id;
```

### *m)* Obter o(s) tipo(s) de título(s) para o(s) qual(is) o máximo de dinheiro “à cabeça” (advance) é uma vez e meia superior à média do grupo (tipo);

```
SELECT titles.[type]  as 'Tipo'
FROM titles
GROUP BY titles.[type]
HAVING MAX(titles.advance) >= 1.5*AVG(titles.advance);
```

### *n)* Obter, para cada título, nome dos autores e valor arrecadado por estes com a sua venda;

```
SELECT distinct titles.title AS 'Titulo' , authors.au_fname as 'Primeiro Nome',authors.au_lname as 'Ultimo Nome', 
( titles.price * (titles.royalty * titleauthor.royaltyper / 100) ) / 100 AS 'Valor arrecadado' 
FROM ( (titles JOIN titleauthor on titles.title_id=titleauthor.title_id) join authors on titleauthor.au_id = authors.au_id )```

### *o)* Obter uma lista que incluía o número de vendas de um título (ytd_sales), o seu nome, a faturação total, o valor da faturação relativa aos autores e o valor da faturação relativa à editora;

```
SELECT titles.title AS 'Titulo', titles.ytd_sales AS 'Numero de vendas', titles.price*titles.ytd_sales AS 'Faturação total',  price*ytd_sales*royalty/100 
as 'Faturacao Autores', price*ytd_sales-price*ytd_sales*royalty/100 as 'Faturacao Editoras'
FROM titles
WHERE titles.ytd_sales IS NOT NULL
```

### *p)* Obter uma lista que incluía o número de vendas de um título (ytd_sales), o seu nome, o nome de cada autor, o valor da faturação de cada autor e o valor da faturação relativa à editora;

```
SELECT titles.title AS 'Titulo', titles.ytd_sales AS 'Numero de vendas', CONCAT(authors.au_fname, ' ', authors.au_lname) as 'Autor', ( titles.price* titles.ytd_sales * (titles.royalty * titleauthor.royaltyper / 100) ) / 100 AS 'Faturacao Autor', titles.price*titles.ytd_sales - ( titles.price* titles.ytd_sales * titles.royalty ) / 100 AS 'Faturacao Editora'
FROM ( (titles join titleauthor on titles.title_id=titleauthor.title_id) join authors on titleauthor.au_id=authors.au_id )
WHERE titles.ytd_sales IS NOT NULL
ORDER BY 'Titulo'
```

### *q)* Lista de lojas que venderam pelo menos um exemplar de todos os livros;

```
SELECT stores.stor_id, stores.stor_name, COUNT(sales.title_id) AS count_id
FROM stores join sales on stores.stor_id=sales.stor_id
GROUP BY stores.stor_id, stores.stor_name
HAVING COUNT(sales.title_id)=(SELECT COUNT(title_id) FROM titles)
```

### *r)* Lista de lojas que venderam mais livros do que a média de todas as lojas;

```
SELECT stores.stor_name AS 'Loja', SUM(sales.qty) AS 'Numero livros vendidos'
FROM stores join sales on stores.stor_id=sales.stor_id 
GROUP BY stores.stor_id , stores.stor_name
HAVING SUM(sales.qty) > (	SELECT AVG(sum_qty)
							FROM (	SELECT sum(sales.qty) AS sum_qty, stor_id
									FROM sales
									GROUP BY stor_id
									)
									as T
						);```

### *s)* Nome dos títulos que nunca foram vendidos na loja “Bookbeat”;

```
SELECT titles.title_id, titles.title
FROM 
	titles LEFT JOIN (
		SELECT sales.title_id
		FROM sales
		INNER JOIN stores ON sales.stor_id = stores.stor_id
		WHERE stores.stor_name = 'Bookbeat'
	) AS T 
	ON titles.title_id = T.title_id
WHERE T.title_id IS NULL;
```

### *t)* Para cada editora, a lista de todas as lojas que nunca venderam títulos dessa editora; 

```
(SELECT publishers.pub_name, stores.stor_name
FROM stores, publishers )
EXCEPT
(SELECT pub_name, stor_name
FROM publishers JOIN 
(	SELECT pub_id AS ppid, sales.stor_id, stor_name
	FROM titles JOIN sales
	ON titles.title_id=sales.title_id
	JOIN stores
	ON sales.stor_id=stores.stor_id) AS T ON pub_id=ppid);
```

## Problema 6.2

### ​5.1

#### a) SQL DDL Script
 
[a) SQL DDL File](ex_6_2_1_ddl.sql "SQLFileQuestion")

#### b) Data Insertion Script

[b) SQL Data Insertion File](ex_6_2_1_data.sql "SQLFileQuestion")

#### c) Queries

##### *a)*

```
Select Company.Project.Pnumber, Company.Employee.Fname, 
Company.Employee.Minit, Company.Employee.Lname
from Company.Project, Company.Works_On, Company.Employee
Where Pnumber = Pno And Essn = Ssn
```

##### *b)* 

```
Select employee.Fname, employee.Minit, employee.Lname
FROM Company.employee JOIN 
(Select Company.Employee.Ssn from Company.Employee
WHERE Company.employee.Fname='Carlos' AND Company.employee.Minit='D' 
AND Company.employee.Lname='Gomes') as SSn_C
on Company.Employee.Super_Ssn = SSn_C.Ssn
```

##### *c)* 

```
Select Company.Project.Pname, T.HorasPP From Company.Project
join(
Select Pno, sum([Hours]) as HorasPP from Company.Works_On
group by Pno) as T on T.Pno = Company.Project.Pnumber
```

##### *d)* 

```
Select T.Fname, T.Minit, T.Lname, Works_On.[Hours] 
From Company.Works_On
Join (Select * From Company.Employee where Dno = 3) as T 
on Works_On.Essn = T.Ssn
where [Hours] > 20
```

##### *e)* 

```
Select T.Fname, T.Minit, T.Lname, Works_On.[Hours] 
From Company.Works_On
right Join (Select * From Company.Employee) as T 
on Works_On.Essn = T.Ssn
where [Hours] is Null
```

##### *f)* 

```
Select Department.Dname, Avg(Salary) as salario_Medio
from Company.Employee join Company.Department on Dno = Company.Department.Dnumber
Where Employee.Sex = 'F'
Group By Department.Dname
```

##### *g)* 

```
Select Company.Employee.Fname, count(Company.Dependents.Dependent_name) as contagem --Company.Employee.Fname  
from Company.Dependents
join Company.Employee
on Employee.Ssn = Company.Dependents.Essn
Group by Employee.Fname
having COUNT(Company.Dependents.Dependent_name) > 2
```

##### *h)* 

```
Select Company.Employee.Fname, Company.Employee.Lname, Company.Dependents.Dependent_name
From Company.Department
join Company.Employee on Mgr_Ssn = Ssn
left join Company.Dependents on Essn = Ssn
where Essn is Null
```

##### *i)* 

```
SELECT distinct Fname, Minit, Lname
FROM Company.Employee JOIN Company.Works_On ON Ssn=Essn
JOIN Company.Project ON Pnumber=Pno
JOIN Company.Department ON Dno=Dnum
JOIN Company.Dept_Locations ON Dno=Dnum
where Plocation = 'Aveiro' and Dlocation <> 'Aveiro'
```

### 5.2

#### a) SQL DDL Script
 
[a) SQL DDL File](ex_6_2_2_ddl.sql "SQLFileQuestion")

#### b) Data Insertion Script

[b) SQL Data Insertion File](ex_6_2_2_data.sql "SQLFileQuestion")

#### c) Queries

##### *a)*

```
SELECT fornecedor.nif, fornecedor.nome
FROM encomenda 
RIGHT OUTER JOIN fornecedor on encomenda.fornecedor = fornecedor.nif
WHERE encomenda.numero is NULL
```

##### *b)* 

```
SELECT produto.nome, AVG(item.unidades) AS avg_units
FROM produto 
JOIN item ON produto.codigo = item.codProd
GROUP BY produto.nome
```


##### *c)* 

```
SELECT AVG(subquery.count_products) AS avg_products
FROM (
  SELECT numEnc, COUNT(*) AS count_products
  FROM item
  GROUP BY numEnc
) AS subquery;
```


##### *d)* 

```
SELECT 
    fornecedor.nome, 
    produto.nome, 
    COUNT(produto.nome) AS count_products  
FROM 
    fornecedor
		JOIN 
				encomenda ON fornecedor.nif = encomenda.fornecedor
		JOIN 
				item ON item.numEnc = encomenda.numero
		JOIN 
				produto ON item.codProd = produto.codigo
GROUP BY 
    fornecedor.nome, produto.nome
```

### 5.3

#### a) SQL DDL Script
 
[a) SQL DDL File](ex_6_2_3_ddl.sql "SQLFileQuestion")

#### b) Data Insertion Script

[b) SQL Data Insertion File](ex_6_2_3_data.sql "SQLFileQuestion")

#### c) Queries

##### *a)*

```
Select * From PEM.Paciente
left join PEM.Prescricao 
on Num_Utente = Num_Utente_Presc
Where Numero is null
```

##### *b)* 

```
Select Especialidade, count(Num_Utente_Presc) as Contagem 
From PEM.Prescricao
join PEM.Medico 
on SNS_ID_Medico = SNSID
group by Especialidade
```


##### *c)* 

```
Select Nome, count(Num_Utente_Presc) as Contagem_de_Prescricoes
From PEM.Prescricao
join PEM.Farmacia 
on NIF = NIF_Farm
group By Nome
```


##### *d)* 

```
( select PEM.Farmaco.nome, Num_Reg_Nac_Farmaco
From PEM.Farmaco
where Num_Reg_Nac_Farmaco = 906 )
except
( Select PEM.FarmacosPrescritos.Nome_Farmaco, Num_Reg_Nac_Farmaco_Presc 
From PEM.FarmacosPrescritos
where Num_Reg_Nac_Farmaco_Presc = 906 )
```

##### *e)* 

```
Select PEM.Farmacia.Nome, Farmaceutica.nome, COUNT(farmaceutica.nome) AS count_farm
From PEM.Prescricao
Join PEM.Farmacia
on NIF = NIF_Farm
join PEM.FarmacosPrescritos
on N_Prescricao = PEm.Prescricao.Numero
join PEM.Farmaceutica
on Num_Reg_Nac = Num_Reg_Nac_Farmaco_Presc
group by Farmacia.Nome, Farmaceutica.nome
order by Farmacia.Nome
```

##### *f)* 

```
Select distinct PEM.Paciente.Nome
From PEM.Medico
join PEM.Prescricao on Medico.SNSID = PEM.Prescricao.SNS_ID_Medico
join PEM.Paciente on Paciente.Num_Utente = Num_Utente_Presc
group by PEM.Medico.Nome, PEM.Paciente.Nome
having count(PEM.Medico.Nome) > 1
order by PEM.Paciente.Nome
```
