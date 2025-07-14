use Guiao_6
go

Create Schema PEM
go

create table PEM.Prescricao(
Numero int,
Data varchar(15),
Num_Utente_Presc int,
SNS_ID_Medico int,
NIF_Farm int,
foreign key (Num_Utente_Presc) references PEM.Paciente(Num_Utente),
foreign key (SNS_ID_Medico) references PEM.Medico(SNSID),
foreign key (NIF_Farm) references PEM.Farmacia(NIF),
primary key (Numero)

)

create table PEM.Farmaco(
nome varchar(30),
Process_Data varchar(8),
Formula varchar(50),
Num_Reg_Nac_Farmaco int
foreign key (Num_Reg_Nac_Farmaco) references PEM.Farmaceutica(Num_Reg_Nac),
primary key (Nome, Num_Reg_Nac_Farmaco))

create table PEM.Paciente(
Num_Utente int,
Nome varchar(30),
Endereco varchar(30),
Data_Nascimento varchar(15),
primary key (Num_Utente))

create table PEM.Farmaceutica(
Num_Reg_Nac int,
Nome varchar(30),
Endereco varchar(60),
Telefone int,
primary key (Num_Reg_Nac),
unique (Telefone))

create table PEM.Medico(
SNSID int,
Nome varchar(30),
Especialidade varchar(30),
primary key (SNSID))

create table PEM.Farmacia(
NIF int,
Nome varchar(30),
Endereco varchar(30),
Telefone int,
primary key (NIF),
unique (Telefone))

create table PEM.FarmacosPrescritos(
Nome_Farmaco varchar(30),
N_Prescricao int,
Num_Reg_Nac_Farmaco_Presc int,
foreign key (N_Prescricao) references PEM.Prescricao(Numero),
foreign key (Nome_Farmaco, Num_Reg_Nac_Farmaco_Presc) references PEM.Farmaco(Nome, Num_Reg_Nac_Farmaco),
primary key (N_Prescricao, Nome_Farmaco))