create database GestaoATL;
GO
USE GestaoATL;
GO

create table Pessoa (
    cc int primary key,
    morada varchar(100),
    dataNascimento date,
    nome varchar(100),
    telefone varchar(15),
    mail varchar(100)
);

create table Professor (
    cc int primary key,
    num_func int UNIQUE,
    foreign key (cc) references Pessoa(cc)
);

create table EncEducacao (
    cc int primary key,
    foreign key (cc) references Pessoa(cc)
);

create table EncRelacao (
    encRelacao varchar(50),
    encCC int,
    primary key (encRelacao, encCC),
    foreign key (encCC) references EncEducacao(cc)
);

create table PessoaAutorizada (
    cc int primary key,
    foreign key (cc) references Pessoa(cc)
);

create table Turma (
    id int primary key,
    anoLetivo int,
    nMax int,
    designacao varchar(50),
    professorCC int,
    foreign key (professorCC) references Professor(cc)
);

create table T_Classe (
    tClass int primary key,
    tID int,
    foreign key (tID) references Turma(id)
);

create table Aluno (
    cc int,
    morada varchar(100),
    dataNascimento date,
    nome varchar(100),
    t_ID int,
    ccEncEducacao int,
    foreign key (t_ID) references Turma(id),
    foreign key (ccEncEducacao) references EncEducacao(cc),
    primary key (cc)
);

create table AutorizacaoAluno (
    cc_PA int,
    cc_Aluno int,
    primary key (cc_PA, cc_Aluno),
    foreign key (cc_PA) references PessoaAutorizada(cc),
    foreign key (cc_Aluno) references Aluno(cc)
);

create table Atividade (
    id int,
    designacao varchar(20),
    custo int,
    primary key (id)
);

create table Turma_Atividade (
    id_Turma int,
    id_Atividade int,
    primary key (id_Turma, id_Atividade),
    foreign key (id_Turma) references Turma(id),
    foreign key (id_Atividade) references Atividade(id)
);

create table Aluno_Atividade (
    cc_Aluno int,
    id_Atividade int,
    primary key (cc_Aluno, id_Atividade),
    foreign key (cc_Aluno) references Aluno(cc),
    foreign key (id_Atividade) references Atividade(id)
);

create table ProcessosFinanceiros (
    id_PF int,
    desconto_Fam int,
    pagamentos int,
    mensalidade int,
    cc_Enc int,
    id_Atividade int,
    foreign key (cc_Enc) references EncEducacao(cc),
    foreign key (id_Atividade) references Atividade(id),
    primary key (id_PF)
);

create table PF_Pagamentos (
    pf_Pagamento int,
    id_PF int,
    primary key (pf_Pagamento, id_PF),
    foreign key (id_PF) references ProcessosFinanceiros(id_PF)
);

create table Atividade_ProcessoFinanceiro (
    id_Atividade int,
    id_Processo int,
    primary key (id_Atividade, id_Processo),
    foreign key (id_Atividade) references Atividade(id),
    foreign key (id_Processo) references ProcessosFinanceiros(id_PF)
);
