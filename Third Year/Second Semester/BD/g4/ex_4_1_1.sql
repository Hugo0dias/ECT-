use p11g4
go
create schema rentcar
go

create table rentcar.Cliente(
nif int,
num_carta varchar(12),
nome varchar(30),
endereco varchar(40),
primary key (nif))


create table rentcar.Balcao(
numero int,
nome varchar(20),
endereco varchar(40),
primary key (numero))


create table rentcar.Aluguer(
numero int,
duracao int,
data varchar(20),
nifAluguer int,
nABalcao int,
matriculaA varchar(20),
foreign key (nifAluguer) references rentcar.Cliente(nif),
foreign key (nABalcao) references rentcar.Balcao(numero),
foreign key(matriculaA) references rentcar.Veiculo(matricula),
primary key (numero))

--foreign key 


create table rentcar.Veiculo(
matricula varchar(20),
ano int,
marca varchar(40),
codigoVeiculo int,
primary key (matricula),
foreign key (codigoVeiculo) references rentcar.TipoVeiculo(codigo))

create table rentcar.TipoVeiculo(
arCondicionado varchar(20),
Designacao varchar(20),
codigo int,
primary key (codigo))

create table rentcar.TipoVeiculoSimilaridade(
codigov1 int,
codigov2 int,
foreign key (codigov1) references rentcar.TipoVeiculo(codigo),
foreign key (codigov2) references rentcar.TipoVeiculo(codigo),
primary key(codigov1,codigov2))

-- fazer dps

create table rentcar.Ligeiro(
ñumeroLugares int,
portas int,
combustivel varchar(20),
codigoLigeiro int,
foreign key (codigoLigeiro) references rentcar.TipoVeiculo(codigo),
primary key (codigoLigeiro))

create table rentcar.Pesado(
peso int,
passageiros int,
codigoPesado int,
foreign key (codigoPesado) references rentcar.TipoVeiculo(codigo),
primary key (codigoPesado))


go