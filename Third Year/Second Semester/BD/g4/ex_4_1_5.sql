create database Gestao_conferencias;
go
use Gestao_conferencias;
go

create table Conferencia (
    nome varchar(20),
    primary key (nome)
);

create table Artigo (
    numRegisto int,
    titulo varchar(30),
    nomeConferencia varchar(20),
    primary key (numRegisto),  
    foreign key (nomeConferencia) references Conferencia(nome)
);

create table Instituicao (
    nome varchar(20),
    endereco varchar(20),
    primary key (nome)
);

create table Pessoa (
    email varchar(20),
    nome varchar(20),
    primary key (email),
    unique (nome)
);

create table Autor (
    nome varchar(20),
    nomeInst varchar(20),
    email varchar(20),
    primary key (email),
    foreign key (nomeInst) references Instituicao(nome),
    foreign key (email) references Pessoa(email),
    foreign key (nome) references Pessoa(Nome)
);

create table Têm (
    numRegistoArtigo int,
    emailAutor varchar(20),
    foreign key (numRegistoArtigo) references Artigo(numRegisto),
    foreign key (emailAutor) references Autor(email)
);

create table Participante(
    email varchar(20),
    morada varchar(20),
    dataInscricao varchar(20),
    nomeConferencia varchar(20),
    primary key (email),
    foreign key (nomeConferencia) references Conferencia(nome)
);

create table Estudante(
    participanteEmail varchar(20),
    primary key (participanteEmail),
    foreign key (participanteEmail) references Participante(email)
);

create table Comprovativo(
    localizacaoEletronica varchar(20),
    instNome varchar(20),
    estudanteEmail varchar(20),
    primary key (localizacaoEletronica, estudanteEmail),
    foreign key (instNome) references Instituicao(nome),
    foreign key (estudanteEmail) references Estudante(participanteEmail)
);

create table NaoEstudante(
    participanteEmail varchar(20),
    referenciaTransacao varchar(20),
    primary key (participanteEmail),
    foreign key (participanteEmail) references Participante(email)
);
