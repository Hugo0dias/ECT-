
Create SCHEMA Encomendas

go



Create Table Encomendas.Products(
Codigo int,
Preco float not null,
IVA float,
Nome varchar(30),
NSStock int,
primary key (Codigo),
Check (Preco > 0.0))

Create Table Encomendas.ProdEnc(
Quantity int,
CodigoProduct int,
IDEncomenda int,
foreign key (CodigoProduct) references Encomendas.Products(Codigo),
foreign key (IDEncomenda) references Encomendas.Encomenda(ID),
primary key (CodigoProduct, IDEncomenda))

Create Table Encomendas.Encomenda(
Data varchar(30),
ID int,
primary key (ID),
NIFFornecedor int,
foreign key (NIFFornecedor) references Encomendas.Fornecedor (NIF))

Create Table Encomendas.Fornecedor(
Nome varchar(30),
Fax int,
PayConditions varchar(50),
NIF int,
Endereco varchar(30),
CodigoFornecedor int,
primary key (NIF),
foreign key (CodigoFornecedor) references Encomendas.TipoFornecedor (Codigo),
unique (Fax))

Create Table Encomendas.TipoFornecedor(
Deseignacao int,
Codigo int,
primary key (Codigo))
