INSERT INTO PEM.Medico (SNSID, Nome, Especialidade) VALUES
(101, 'Joao Pires Lima', 'Cardiologia'),
(102, 'Manuel Jose Rosa', 'Cardiologia'),
(103, 'Rui Luis Caraca', 'Pneumologia'),
(104, 'Sofia Sousa Silva', 'Radiologia'),
(105, 'Ana Barbosa', 'Neurologia');

INSERT INTO PEM.Paciente (Num_Utente, Nome, Data_Nascimento, Endereco) VALUES
(1, 'Renato Manuel Cavaco', '1980-01-03', 'Rua Nova do Pilar 35'),
(2, 'Paula Vasco Silva', '1972-10-30', 'Rua Direita 43'),
(3, 'Ines Couto Souto', '1985-05-12', 'Rua de Cima 144'),
(4, 'Rui Moreira Porto', '1970-12-12', 'Rua Zig Zag 235'),
(5, 'Manuel Zeferico Polaco', '1990-06-05', 'Rua da Baira Rio 1135');

INSERT INTO PEM.Farmacia (NIF, Nome, Telefone, Endereco) VALUES
(1, 'Farmacia BelaVista', 221234567, 'Avenida Principal 973'),
(2, 'Farmacia Central', 234370500, 'Avenida da Liberdade 33'),
(3, 'Farmacia Peixoto', 234375111, 'Largo da Vila 523'),
(4, 'Farmacia Vitalis', 229876543, 'Rua Visconde Salgado 263');

INSERT INTO PEM.Farmaceutica (Num_Reg_Nac, Nome, Endereco, Telefone) VALUES
(905, 'Roche', 'Estrada Nacional 249', 123456789),
(906, 'Bayer', 'Rua da Quinta do Pinheiro 5', 987654321),
(907, 'Pfizer', 'Empreendimento Lagoas Park - Edificio 7', 111222333),
(908, 'Merck', 'Alameda Fernão Lopes 12', 444555666);

INSERT INTO PEM.Farmaco (Num_Reg_Nac_Farmaco, Nome, Formula) VALUES
(905, 'Boa Saude em 3 Dias', 'XZT9'),
(906, 'Voltaren Spray', 'PLTZ32'),
(906, 'Xelopironi 350', 'FRR-34'),
(906, 'Gucolan 1000', 'VFR-750'),
(907, 'GEROaero Rapid', 'DDFS-XEN9'),
(908, 'Aspirina 1000', 'BIOZZ02');

INSERT INTO PEM.Prescricao (Numero, Num_Utente_Presc, SNS_ID_Medico, NIF_Farm, Data) VALUES
(10001, 1, 105, 2, '2015-03-03'),
(10002, 1, 105, NULL, NULL),
(10003, 3, 102, 2, '2015-01-17'),
(10004, 3, 101, 1, '2015-02-09'),
(10005, 3, 102, 2, '2015-01-17'),
(10006, 4, 102, 4, '2015-02-22'),
(10007, 5, 103, NULL, NULL),
(10008, 1, 103, 2, '2015-01-02'),
(10009, 3, 102, 3, '2015-02-02');

INSERT INTO PEM.FarmacosPrescritos (N_Prescricao, Nome_Farmaco, Num_Reg_Nac_Farmaco_Presc) VALUES
(10001, 'Boa Saude em 3 Dias', 905),
(10002, 'GEROaero Rapid', 907),
(10003, 'Voltaren Spray', 906),
(10003, 'Xelopironi 350', 906),
(10003, 'Aspirina 1000', 908),
(10004, 'Boa Saude em 3 Dias', 905),
(10004, 'Aspirina 1000', 908),
(10005, 'Voltaren Spray', 906),
(10006, 'Boa Saude em 3 Dias', 905),
(10006, 'Voltaren Spray', 906),
(10006, 'Xelopironi 350', 906),
(10006, 'Aspirina 1000', 908),
(10007, 'Voltaren Spray', 906),
(10008, 'Boa Saude em 3 Dias', 905),
(10008, 'Aspirina 1000', 908),
(10009, 'Boa Saude em 3 Dias', 905),
(10009, 'Voltaren Spray', 906),
(10009, 'Aspirina 1000', 908);