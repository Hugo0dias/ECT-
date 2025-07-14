CREATE DATABASE Stock_Management;
GO
USE Stock_Management;
GO

CREATE TABLE Produto(
    codigo              INT NOT NULL,
    nome                VARCHAR(30) NOT NULL,
    preco               DECIMAL(4,2) NOT NULL,
    iva                 INT NOT NULL,
    unidades              INT NOT NULL,
    PRIMARY KEY (codigo),
    UNIQUE (nome)
);  

CREATE TABLE TipoFornecedor(
    codigo                    INT NOT NULL,
    designacao                VARCHAR(20) NOT NULL,
    PRIMARY KEY (codigo)
);

CREATE TABLE Fornecedor(
    nif                 INT NOT NULL,
    nome                VARCHAR(20) NOT NULL,
    fax                INT NOT NULL,
    endereco                 VARCHAR(40),
    condpag      INT NOT NULL,
    tipo              INT NOT NULL,
    PRIMARY KEY (nif),
    FOREIGN KEY (tipo) REFERENCES TipoFornecedor(codigo)
);    


CREATE TABLE Encomenda(
    numero                INT NOT NULL,
    [data]             VARCHAR(20) NOT NULL,
    fornecedor               INT NOT NULL,
    PRIMARY KEY (numero),
    FOREIGN KEY (fornecedor) REFERENCES Fornecedor(nif)
);    



CREATE TABLE item(
    numEnc             INT NOT NULL,
    codProd                    INT NOT NULL,
    unidades              INT NOT NULL,
    PRIMARY KEY (numEnc,codProd),
    FOREIGN KEY (numEnc) REFERENCES Encomenda(numero),
    FOREIGN KEY (codProd) REFERENCES Produto(codigo),
);