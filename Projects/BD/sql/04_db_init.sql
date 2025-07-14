--use Project
--go

INSERT INTO [Numismatics].[User] (NIF, [Password], Fname, Lname, Email, BDate, Phone)
VALUES 
(101234567, EncryptByPassPhrase('Numismatics', '3') , 'Inês',     'Martins',  'ines.martins@gmail.com', '1995-12-30', '912345678'),
(102345678, EncryptByPassPhrase('Numismatics', '4') , 'João',     'Costa',    'joao.costa@outlook.com', '1985-11-20', '965432198'),
(103456789, EncryptByPassPhrase('Numismatics', '5') , 'Maria',    'Silva',    'maria.silva@gmail.com',  '1990-05-15', '912345999'),
(104567890, EncryptByPassPhrase('Numismatics', '6') , 'Ana',      'Ferreira', 'ana.ferreira@yahoo.com', '1992-07-08', '931234567'),
(105678901, EncryptByPassPhrase('Numismatics', '7') , 'Ricardo',  'Lopes',    'ricardo.lopes@mail.com', '1988-03-25', '919876543'),
(106789012, EncryptByPassPhrase('Numismatics', '8') , 'Sofia',    'Rocha',    'sofia.rocha@hotmail.com','1993-06-22', '934567890'),
(107890123, EncryptByPassPhrase('Numismatics', '9') , 'Miguel',   'Alves',    'miguel.alves@gmail.com', '1987-01-17', '923456789'),
(108901234, EncryptByPassPhrase('Numismatics', '10') , 'Beatriz',  'Pinto',    'beatriz.pinto@live.com', '1994-04-12', '936789012'),
(109012345, EncryptByPassPhrase('Numismatics', '11') , 'Diogo',    'Gomes',    'diogo.gomes@gmail.com',  '1996-08-05', '918273645'),
(110123456, EncryptByPassPhrase('Numismatics', '12') , 'Joana',    'Moura',    'joana.moura@gmail.com',  '1997-03-18', '912345987');

go

INSERT INTO [Numismatics].[Type] (Code_Type, Designation) VALUES
(0, 'Global'),
(1, 'Euro Collection'),
(2, 'Mundial Collection'),
(3, 'Ancient Coins');
go

INSERT INTO [Numismatics].Colection (NIF_User, Creation_Date, Code_Type) VALUES
(109012345, '20240115', 0),
(105678901, '20240210', 1),
(103456789, '20240305', 2),
(101234567, '20240401', 3),
(104567890, '20240420', 0),
(103456789, '20240425', 0);


INSERT INTO [Numismatics].[Events] (ID, [Name], [Start_Date], End_Date, Localization) VALUES
(1, 'Euro Fair Lisbon', '2024-06-01', '2024-06-03', 'Lisbon'),
(2, 'Coin Expo Madrid', '2024-07-10', '2024-07-12', 'Madrid'),
(3, 'Vintage Coin Show', '2024-08-15', '2024-08-16', 'Berlin'),
(4, 'Collectors Meetup', '2024-09-05', '2024-09-05', 'Brussels'),
(5, 'Numismatics Week', '2024-10-01', '2024-10-07', 'Paris'),
(6, 'Coins Everywhere', '2025-10-12', '2025-10-17', 'Tallin');

go

INSERT INTO [Numismatics].Participates_Event (ID_Colection, ID_Event) VALUES
(1, 1),
(1, 3),
(2, 1),
(2, 5),
(1, 6);

go

INSERT INTO [Numismatics].Country ([Name], Number_Unique_CC_Coins) VALUES -- Europeus
('Germany', 33),
('France', 35),
('Italy', 38),
('Spain', 27),
('Portugal', 35),
('Austria', 8),
('Netherlands', 9),
('Belgium', 359),
('Finland', 41),
('Ireland', 8),
('Slovakia', 20),
('Slovenia', 21),
('Estonia', 18),
('Latvia', 18),
('Lithuania', 19),
('Cyprus', 8),
('Malta', 52),
('Greece', 31),
('Luxembourg', 86),
('Vatican', 31),
('San Marino', 34),
('Monaco', 14),
('Andorra', 21),
('Croatia', 3);

INSERT INTO [Numismatics].Currency (Currency_Name, Country_Name, ISO_Code, Date_First_Edition, Date_Last_Edition) VALUES -- Europeus

('Euro', 'Germany', 'EUR', 2002, null),
('Euro', 'France', 'EUR', 1999, null),
('Euro', 'Italy', 'EUR', 1999, null),
('Euro', 'Spain', 'EUR', 1999, null),
('Euro', 'Portugal', 'EUR', 2002, null),
('Euro', 'Austria', 'EUR', 2002, null),
('Euro', 'Netherlands', 'EUR', 1999, null),
('Euro', 'Belgium', 'EUR', 1999, null),
('Euro', 'Finland', 'EUR', 1999, null),
('Euro', 'Ireland', 'EUR', 2002, null),
('Euro', 'Slovakia', 'EUR', 2009, null),
('Euro', 'Slovenia', 'EUR', 2007, null),
('Euro', 'Estonia', 'EUR', 2011, null),
('Euro', 'Latvia', 'EUR', 2014, null),
('Euro', 'Lithuania', 'EUR', 2015, null),
('Euro', 'Cyprus', 'EUR', 2008, null),
('Euro', 'Malta', 'EUR', 2008, null),
('Euro', 'Greece','EUR' , 2002, null),
('Euro', 'Luxembourg','EUR' , 2002, null),
('Euro', 'Vatican','EUR' , 2002, null),
('Euro', 'San Marino','EUR' , 2002, null),
('Euro', 'Monaco','EUR' , 2001, null),
('Euro', 'Andorra','EUR' , 2014, null),
('Euro', 'Croatia','EUR' , 2023, null);

go

INSERT INTO [Numismatics].Country ([Name], Number_Unique_CC_Coins) VALUES -- Outros
('United States', 350),
('Canada', 350),
('Mexico', 220),
('Brazil', 180),
('Argentina', 160),
('Chile', 140),
('Colombia', 130),
('Peru', 120),
('Venezuela', 150),
('Panama', 90),
('Costa Rica', 80),
('Cuba', 110),
('Dominican Republic', 100),
('South Africa', 200),
('Nigeria', 120),
('Egypt', 100),
('Kenya', 90),
('Ghana', 85),
('Morocco', 95),
('Ethiopia', 70),
('Japan', 150),
('China', 275),
('South Korea', 180),
('North Korea', 40),
('Taiwan', 160),
('Mongolia', 60),
('Indonesia', 130),
('Malaysia', 140),
('Thailand', 150),
('Vietnam', 135),
('Philippines', 120),
('Singapore', 110),
('India', 300),
('Pakistan', 160),
('Bangladesh', 110),
('Sri Lanka', 90),
('Nepal', 70),
('Saudi Arabia', 140),
('Iran', 150),
('Iraq', 125),
('United Arab Emirates', 130),
('Israel', 115),
('Turkey', 200),
('Qatar', 80),
('Australia', 400),
('New Zealand', 210),
('Fiji', 75),
('Papua New Guinea', 60),
('Russia', 290),
('Kazakhstan', 110),
('South Sudan', 30),
('Uzbekistan', 90),
('Afghanistan', 70);

go


INSERT INTO [Numismatics].Currency (Currency_Name, Country_Name, ISO_Code, Date_First_Edition, Date_Last_Edition) VALUES

-- Antigas

('Escudo Português', 'Portugal','PTE' , 1911, 2001),

-- América do Norte
('United States Dollar', 'United States', 'USD', 1892, null),
('Canadian Dollar', 'Canada', 'CAD', 1935, null),
('Mexican Peso', 'Mexico', 'MXN', 1955, null),

-- América do Sul
('Brazilian Real', 'Brazil', 'BRL', 1972, null),
('Argentine Peso', 'Argentina', 'ARS', 1960, null),
('Chilean Peso', 'Chile', 'CLP', 1975, null),
('Colombian Peso', 'Colombia', 'COP', 1963, null),
('Peruvian Sol', 'Peru', 'PEN', 1991, null),
('Venezuelan Bolívar', 'Venezuela', 'VES', 1965, null),

-- América Central e Caribe
('Panamanian Balboa', 'Panama', 'PAB', 1904, null),
('Costa Rican Colón', 'Costa Rica', 'CRC', 1950, null),
('Cuban Peso', 'Cuba', 'CUP', 1961, null),
('Dominican Peso', 'Dominican Republic', 'DOP', 1952, null),

-- África
('South African Rand', 'South Africa', 'ZAR', 1961, null),
('Nigerian Naira', 'Nigeria', 'NGN', 1973, null),
('Egyptian Pound', 'Egypt', 'EGP', 1950, null),
('Kenyan Shilling', 'Kenya', 'KES', 1966, null),
('Ghanaian Cedi', 'Ghana', 'GHS', 1965, null),
('Moroccan Dirham', 'Morocco', 'MAD', 1959, null),
('Ethiopian Birr', 'Ethiopia', 'ETB', 1945, null),

-- Ásia Oriental
('Japanese Yen', 'Japan', 'JPY', 1951, null),
('Chinese Yuan', 'China', 'CNY', 1955, null),
('South Korean Won', 'South Korea', 'KRW', 1959, null),
('North Korean Won', 'North Korea', 'KPW', 1947, null),
('New Taiwan Dollar', 'Taiwan', 'TWD', 1949, null),
('Mongolian Tögrög', 'Mongolia', 'MNT', 1925, null),

-- Sudeste Asiático
('Indonesian Rupiah', 'Indonesia', 'IDR', 1971, null),
('Malaysian Ringgit', 'Malaysia', 'MYR', 1967, null),
('Thai Baht', 'Thailand', 'THB', 1957, null),
('Vietnamese Dong', 'Vietnam', 'VND', 1978, null),
('Philippine Peso', 'Philippines', 'PHP', 1949, null),
('Singapore Dollar', 'Singapore', 'SGD', 1967, null),

-- Sul da Ásia
('Indian Rupee', 'India', 'INR', 1950, null),
('Pakistani Rupee', 'Pakistan', 'PKR', 1948, null),
('Bangladeshi Taka', 'Bangladesh', 'BDT', 1972, null),
('Sri Lankan Rupee', 'Sri Lanka', 'LKR', 1951, null),
('Nepalese Rupee', 'Nepal', 'NPR', 1955, null),

-- Médio Oriente
('Saudi Riyal', 'Saudi Arabia', 'SAR', 1952, null),
('Iranian Rial', 'Iran', 'IRR', 1948, null),
('Iraqi Dinar', 'Iraq', 'IQD', 1947, null),
('UAE Dirham', 'United Arab Emirates', 'AED', 1973, null),
('Israeli Shekel', 'Israel', 'ILS', 1960, null),
('Turkish Lira', 'Turkey', 'TRY', 1923, null),
('Qatari Riyal', 'Qatar', 'QAR', 1973, null),

-- Oceania
('Australian Dollar', 'Australia', 'AUD', 1910, null),
('New Zealand Dollar', 'New Zealand', 'NZD', 1967, null),
('Fijian Dollar', 'Fiji', 'FJD', 1969, null),
('Papua New Guinean Kina', 'Papua New Guinea', 'PGK', 1975, null),

-- Outros
('Russian Ruble', 'Russia', 'RUB', 1921, null),
('Kazakhstani Tenge', 'Kazakhstan', 'KZT', 1993, null),
('South Sudanese Pound', 'South Sudan', 'SSP', 2011, null),
('Uzbekistani Som', 'Uzbekistan', 'UZS', 1994, null),
('Afghan Afghani', 'Afghanistan', 'AFN', 1925, null);
-- Moedas comemorativas Europeias

-- Moeda 1
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Portugal', 1500000, 'UNC', 'CuproNiquel', 'Normal', 4.00, 1);
DECLARE @ID1 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID1, 2007, '50º Aniversário do Tratado de Roma', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID1, 'Mapa da UE B2008', 'B2008', 1);

-- Moeda 2
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Portugal', 1250000, 'G', 'CuproNiquel', 'Normal', 3.50, 1);
DECLARE @ID2 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID2, 2007, 'Presidência Portuguesa do Conselho da UE', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID2, 'Mapa da UE B2008', 'B2008', 1);

-- Moeda 3
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Portugal', 1250000, 'F', 'CuproNiquel', 'Normal', 3.50, 1);
DECLARE @ID3 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID3, 2009, '10º Aniversário da União Económica e Monetária', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID3, 'Mapa da UE A2008', 'A2008', 1);

-- Moeda 4
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Portugal', 500000, 'VF', 'CuproNiquel', 'Normal', 4.50, 1);
DECLARE @ID4 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID4, 2012, '10 Anos de Notas e Moedas em Euro', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID4, 'Mapa da UE A2008', 'A2008', 1);

-- Moeda 5
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Portugal', 500000, 'B', 'CuproNiquel', 'Normal', 3.50, 1);
DECLARE @ID5 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID5, 2014, 'Ano Tradicional da Agricultura Familiar', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID5, 'Mapa da UE A2008', 'A2008', 1);

-- Moeda 6
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Portugal', 500000, 'VB', 'CuproNiquel', 'Normal', 3.00, 1);
DECLARE @ID6 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID6, 2015, '30º Aniversário da Bandeira da Europa', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID6, 'Mapa da UE A2008', 'A2008', 1);

-- Moeda 7
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Portugal', 630000, 'F', 'CuproNiquel', 'Normal', 3.50, 1);
DECLARE @ID7 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID7, 2016, 'Rio 2016 - Equipa Olímpica de Portugal', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID7, 'Mapa da UE A2008', 'A2008', 1);

-- Moeda 8
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Portugal', 474000, 'F', 'CuproNiquel', 'Normal', 4.00, 1);
DECLARE @ID8 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID8, 2017, '150 Anos do Nascimento de Raúl Solnado Brandao', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID8, 'Mapa da UE A2008', 'A2008', 1);

-- Moeda 9
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'France', 9400000, 'F', 'CuproNiquel', 'Normal', 2.50, 1);
DECLARE @ID9 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID9, 2007, '50º Aniversário do Tratado de Roma', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID9, 'Mapa da UE B2008', 'B2008', 1);

-- Moeda 10
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'France', 20000000, 'UNC', 'CuproNiquel', 'Normal', 2.70, 1);
DECLARE @ID10 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID10, 2008, 'Presidência Francesa do Conselho da UE', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID10, 'Mapa da UE A2008', 'A2008', 1);

-- Moeda 11
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'France', 20000000, 'VF', 'CuproNiquel', 'Normal', 2.50, 1);
DECLARE @ID11 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID11, 2010, '70º Aniversário do Apelo de 18 de Junho', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID11, 'Mapa da UE A2008', 'A2008', 1);

-- Moeda 12
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'France', 10000000, 'B', 'CuproNiquel', 'Normal', 2.00, 1);
DECLARE @ID12 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID12, 2011, '30º Aniversário da Festa da Música', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID12, 'Mapa da UE A2008', 'A2008', 1);

-- Moeda 13
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'France', 10000000, 'VB', 'CuproNiquel', 'Normal', 2.00, 1);
DECLARE @ID13 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID13, 2012, '10 Anos de Notas e Moedas em Euro', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID13, 'Mapa da UE A2008', 'A2008', 1);

-- Moeda 14
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'France', 1000000, 'G', 'CuproNiquel', 'Normal', 4.40, 1);
DECLARE @ID14 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID14, 2013, 'Pierre de Coubertin', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID14, 'Mapa da UE A2008', 'A2008', 1);

-- Moeda 15
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'France', 3000000, 'B', 'CuproNiquel', 'Normal', 2.50, 1);
DECLARE @ID15 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID15, 2014, 'Dia D - Desembarque na Normandia', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID15, 'Mapa da UE A2008', 'A2008', 1);

-- Moeda 16
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'France', 4000000, 'G', 'CuproNiquel', 'Normal', 3.00, 1);
DECLARE @ID16 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID16, 2015, '225º Aniversário da Federação', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID16, 'Mapa da UE A2008', 'A2008', 1);

-- Moeda 17
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'France', 4000000, 'VG', 'CuproNiquel', 'Normal', 3.20, 1);
DECLARE @ID17 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID17, 2015, '30 Anos da Bandeira Europeia', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID17, 'Mapa da UE A2008', 'A2008', 1);

-- Moeda 18
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'France', 10000000, 'G', 'CuproNiquel', 'Normal', 2.20, 1);
DECLARE @ID18 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID18, 2016, 'Euro 2016 - Campeonato de Futebol', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID18, 'Mapa da UE A2008', 'A2008', 1);

-- Moeda 19
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'France', 10000000, 'VB', 'CuproNiquel', 'Normal', 2.00, 1);
DECLARE @ID19 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID19, 2016, 'François Mitterrand', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID19, 'Mapa da UE A2008', 'A2008', 1);

-- Moeda 20
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'France', 10000000, 'B', 'CuproNiquel', 'Normal', 2.00, 1);
DECLARE @ID20 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID20, 2017, '200º Aniversário da Morte de Auguste Rodin', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID20, 'Mapa da UE A2008', 'A2008', 1);

-- Moeda 21
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'France', 300000, 'UNC', 'CuproNiquel', 'Normal', 25.00, 1);
DECLARE @ID21 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID21, 2019, '60 Anos de Astérix', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID21, 'Mapa da UE A2008', 'A2008', 1);

-- Moeda 23
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Germany', 8500000, 'VF', 'CuproNiquel', 'Normal', 2.90, 1);
DECLARE @ID23 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID23, 2006, 'Holstentor em Lübeck (Schleswig-Holstein)', 1, 'D', 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID23, 'Mapa da UE B2008', 'B2008', 1);

-- Moeda 24
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Germany', 7500000, 'F', 'CuproNiquel', 'Normal', 2.50, 1);
DECLARE @ID24 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID24, 2007, 'Igreja de São Miguel em Hamburgo', 1, 'D', 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID24, 'Mapa da UE B2008', 'B2008', 1);

-- Moeda 25
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Germany', 7500000, 'F', 'CuproNiquel', 'Normal', 2.50, 1);
DECLARE @ID25 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID25, 2008, 'Castelo de Neuschwanstein (Baviera)', 1, 'F', 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID25, 'Mapa da UE A2008', 'A2008', 1);

-- Moeda 26
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Germany', 10000000, 'G', 'CuproNiquel', 'Normal', 2.50, 1);
DECLARE @ID26 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID26, 2009, 'Igreja de São Ludgerus (Saarland)', 1, 'A', 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID26, 'Mapa da UE A2008', 'A2008', 1);

-- Moeda 27
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Germany', 8500000, 'VG', 'CuproNiquel', 'Normal', 2.50, 1);
DECLARE @ID27 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID27, 2010, 'Catedral de Bremen', 1, 'F', 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID27, 'Mapa da UE A2008', 'A2008', 1);

-- Moeda 28
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Germany', 7500000, 'VG', 'CuproNiquel', 'Normal', 2.50, 1);
DECLARE @ID28 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID28, 2011, 'Catedral de Colônia (Nordrhein-Westfalen)', 1, 'G', 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID28, 'Mapa da UE A2008', 'A2008', 1);

-- Moeda 29
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Germany', 8500000, 'G', 'CuproNiquel', 'Normal', 2.50, 1);
DECLARE @ID29 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID29, 2012, 'Palácio de Würzburg (Bayern)', 1, 'D', 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID29, 'Mapa da UE A2008', 'A2008', 1);

-- Moeda 30
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Germany', 8000000, 'B', 'CuproNiquel', 'Normal', 2.20, 1);
DECLARE @ID30 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID30, 2013, 'Mosteiro de Maulbronn (Baden-Württemberg)', 1, 'F', 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID30, 'Mapa da UE A2008', 'A2008', 1);

-- Moeda 31
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Germany', 8500000, 'VB', 'CuproNiquel', 'Normal', 2.20, 1);
DECLARE @ID31 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID31, 2014, 'Igreja de São Miguel em Hildesheim (Niedersachsen)', 1, 'J', 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID31, 'Mapa da UE A2008', 'A2008', 1);

-- Moeda 32
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Germany', 7500000, 'G', 'CuproNiquel', 'Normal', 2.50, 1);
DECLARE @ID32 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID32, 2015, 'Igreja de São Paulo em Frankfurt', 1, 'D', 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID32, 'Mapa da UE A2008', 'A2008', 1);

-- Moeda 33
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Germany', 7500000, 'B', 'CuproNiquel', 'Normal', 2.20, 1);
DECLARE @ID33 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID33, 2016, 'Zwinger em Dresden (Saxônia)', 1, 'G', 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID33, 'Mapa da UE A2008', 'A2008', 1);

-- Moeda 34
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Germany', 7500000, 'F', 'CuproNiquel', 'Normal', 2.50, 1);
DECLARE @ID34 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID34, 2017, 'Porta de Brandemburgo', 1, 'F', 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID34, 'Mapa da UE A2008', 'A2008', 1);

-- Moeda 35
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Germany', 4000000, 'G', 'CuproNiquel', 'Normal', 2.50, 1);
DECLARE @ID35 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID35, 2018, 'Castelo de Charlottenburg (Berlim)', 1, 'J', 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID35, 'Mapa da UE A2008', 'A2008', 1);

-- Moeda 36
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Spain', 4000000, 'B', 'CuproNiquel', 'Normal', 2.70, 1);
DECLARE @ID36 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID36, 2010, 'Mesquita de Córdoba (Património Mundial)', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID36, 'Mapa da UE A2008', 'A2008', 1);

-- Moeda 37
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Spain', 4000000, 'G', 'CuproNiquel', 'Normal', 3.00, 1);
DECLARE @ID37 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID37, 2014, 'Parque Güell – Gaudí (Património Mundial)', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID37, 'Mapa da UE A2008', 'A2008', 1);

-- Moeda 38
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Spain', 4100000, 'VF', 'CuproNiquel', 'Normal', 3.50, 1);
DECLARE @ID38 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID38, 2015, 'Caverna de Altamira (Património Mundial)', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID38, 'Mapa da UE A2008', 'A2008', 1);

-- Moeda 39
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Finland', 2500000, 'F', 'CuproNiquel', 'Normal', 4.00, 1);
DECLARE @ID39 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID39, 2017, '100 Anos da Independência da Finlândia', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID39, 'Mapa da UE A2008', 'A2008', 1);

-- Moeda 40
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Italy', 9000000, 'VB', 'CuproNiquel', 'Normal', 2.10, 1);
DECLARE @ID40 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID40, 2011, '150º Aniversário da Unificação Italiana', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID40, 'Mapa da UE A2008', 'A2008', 1);

-- Moeda 41
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Italy', 6475000, 'VB', 'CuproNiquel', 'Normal', 2.10, 1);
DECLARE @ID41 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID41, 2014, '450º Aniversário do nascimento de Galileu Galilei', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID41, 'Mapa da UE A2008', 'A2008', 1);

-- Moeda 42
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.01, 'Portugal', 278000000, 'B', 'CuproNiquel', 'Normal', 0.01, 1);
DECLARE @ID42 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID42, 2002, 'Selo de 1134', 0, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID42, 'Mapa da UE B2008', 'B2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.02, 'Portugal', 85000, 'UNC', 'CuproNiquel', 'Normal', 5.00, 1);
DECLARE @ID43 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID43, 2003, 'Selo de 1134', 0, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID43, 'Mapa da UE B2008', 'B2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.05, 'Portugal', 40000000, 'G', 'CuproNiquel', 'Normal', 0.05, 1);
DECLARE @ID44 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID44, 2004, 'Selo de 1134', 0, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID44, 'Mapa da UE B2008', 'B2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.10, 'Portugal', 1000000, 'VG', 'CuproNiquel', 'Normal', 0.20, 1);
DECLARE @ID45 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID45, 2005, 'Real Arms', 0, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID45, 'Mapa da UE B2008', 'B2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.20, 'Portugal', 20000000, 'VF', 'CuproNiquel', 'Normal', 0.20, 1);
DECLARE @ID46 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID46, 2006, 'Real Arms', 0, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID46, 'Mapa da UE B2008', 'B2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.50, 'Portugal', 21500, 'UNC', 'CuproNiquel', 'Normal', 42.00, 1);
DECLARE @ID47 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID47, 2007, 'Real Arms', 0, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID47, 'Mapa da UE B2008', 'B2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (1.00, 'Portugal', 5000000, 'F', 'CuproNiquel', 'Normal', 1.00, 1);
DECLARE @ID48 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID48, 2008, 'Royal Seal', 0, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID48, 'Mapa da UE A2008', 'A2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.00, 'Portugal', 34000, 'UNC', 'CuproNiquel', 'Normal', 15.00, 1);
DECLARE @ID49 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID49, 2009, 'Royal Seal', 0, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID49, 'Mapa da UE A2008', 'A2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.10, 'Portugal', 39500, 'UNC', 'CuproNiquel', 'Normal', 1.00, 1);
DECLARE @ID50 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID50, 2010, 'Real Arms', 0, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID50, 'Mapa da UE A2008', 'A2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.20, 'Portugal', 10000000, 'G', 'CuproNiquel', 'Normal', 0.20, 1);
DECLARE @ID51 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID51, 2011, 'Real Arms', 0, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID51, 'Mapa da UE A2008', 'A2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.50, 'Portugal', 39500, 'UNC', 'CuproNiquel', 'Normal', 4.30, 1);
DECLARE @ID52 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID52, 2012, 'Real Arms', 0, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID52, 'Mapa da UE A2008', 'A2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (1.00, 'Portugal', 44500, 'UNC', 'CuproNiquel', 'Normal', 6.00 , 1);
DECLARE @ID53 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID53, 2013, 'Royal Seal', 0, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID53, 'Mapa da UE A2008', 'A2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.01, 'Germany', 960000000, 'VG', 'CuproNiquel', 'Normal', 0.01, 1);
DECLARE @ID54 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID54, 2002, 'Águia Federal', 0, 'F', 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID54, 'Mapa da UE B2008', 'B2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.02, 'Germany', 507800000, 'F', 'CuproNiquel', 'Normal', 0.02, 1);
DECLARE @ID55 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID55, 2002, 'Águia Federal', 0, 'F', 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID55, 'Mapa da UE B2008', 'B2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.05, 'Germany', 480000000, 'G', 'CuproNiquel', 'Normal', 0.05, 1);
DECLARE @ID56 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID56, 2002, 'Águia Federal', 0, 'A', 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID56, 'Mapa da UE B2008', 'B2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.10, 'Germany', 721950000, 'F', 'CuproNiquel', 'Normal', 0.10, 1);
DECLARE @ID57 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID57, 2002, 'Águia Federal', 0, 'D', 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID57, 'Mapa da UE B2008', 'B2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.20, 'Germany', 251900000, 'VB', 'CuproNiquel', 'Normal', 0.20, 1);
DECLARE @ID58 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID58, 2002, 'Águia Federal', 0, 'G', 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID58, 'Mapa da UE B2008', 'B2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.50, 'Germany', 337745000, 'VB', 'CuproNiquel', 'Normal', 0.50, 1);
DECLARE @ID59 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID59, 2002, 'Águia Federal', 0, 'A', 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID59, 'Mapa da UE B2008', 'B2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (1.00, 'Germany', 	372308000, 'F', 'CuproNiquel', 'Normal', 1.00, 1);
DECLARE @ID60 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID60, 2002, 'Águia Federal', 0, 'J', 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID60, 'Mapa da UE B2008', 'B2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.00, 'Germany', 257820000, 'G', 'CuproNiquel', 'Normal', 2.00, 1);
DECLARE @ID61 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID61, 2002, 'Águia Federal', 0, 'J', 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID61, 'Mapa da UE B2008', 'B2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.10, 'Germany', 140000, 'UNC', 'CuproNiquel', 'BU', 3.00, 1);
DECLARE @ID62 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID62, 2004, 'Águia Federal', 0, 'J', 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID62, 'Mapa da UE B2008', 'B2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.50, 'Germany', 100000, 'UNC', 'CuproNiquel', 'BU', 2.0, 1);
DECLARE @ID63 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID63, 2005, 'Águia Federal', 0, 'A', 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID63, 'Mapa da UE B2008', 'B2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.01, 'Belgium', 100000, 'UNC', 'CuproNiquel', 'BU', 0.30, 1);
DECLARE @ID64 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID64, 2002, 'Rei Albert II', 0, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID64, 'Mapa da UE B2008', 'B2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.02, 'Belgium', 140000, 'UNC', 'CuproNiquel', 'BU', 0.50, 1);
DECLARE @ID65 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID65, 2002, 'Rei Albert II', 0, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID65, 'Mapa da UE B2008', 'B2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.10, 'Belgium', 100000, 'UNC', 'CuproNiquel', 'BU', 1.00, 1);
DECLARE @ID66 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID66, 2003, 'Rei Albert II', 0, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID66, 'Mapa da UE B2008', 'B2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (1.00, 'Belgium', 15000000, 'F', 'CuproNiquel', 'Normal', 1.00, 1);
DECLARE @ID67 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID67, 2004, 'Rei Albert II', 0, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID67, 'Mapa da UE B2008', 'B2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.00, 'Belgium', 1700000, 'G', 'CuproNiquel', 'Normal', 2.00, 1);
DECLARE @ID68 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID68, 2014, 'Rei Philippe', 0, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID68, 'Mapa da UE A2008', 'A2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.05, 'Netherlands', 900000, 'VB', 'CuproNiquel', 'Normal', 0.40, 1);
DECLARE @ID69 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID69, 2002, 'Rainha Beatrix', 0, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID69, 'Mapa da UE B2008', 'B2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (1.00, 'Netherlands', 393000, 'VF', 'CuproNiquel', 'Normal', 1.50, 1);
DECLARE @ID70 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID70, 2006, 'Rainha Beatrix', 0, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID70, 'Mapa da UE B2008', 'B2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.00, 'Netherlands', 5000000, 'F', 'CuproNiquel', 'Normal', 2.00, 1);
DECLARE @ID71 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID71, 2014, 'Rei Willem-Alexander', 0, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID71, 'Mapa da UE A2008', 'A2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.01, 'Italy', 1348599500, 'G', 'CuproNiquel', 'Normal', 0.01, 1);
DECLARE @ID72 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID72, 2002, 'Castel del Monte', 0, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID72, 'Mapa da UE B2008', 'B2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.02, 'Italy', 1098866250, 'F', 'CuproNiquel', 'Normal', 0.02, 1);
DECLARE @ID73 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID73, 2002, 'Mole Antonelliana', 0, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID73, 'Mapa da UE B2008', 'B2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.05, 'Italy', 1341442204, 'F', 'CuproNiquel', 'Normal', 0.05, 1);
DECLARE @ID74 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID74, 2002, 'Coliseu', 0, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID74, 'Mapa da UE B2008', 'B2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.10, 'Italy', 1142083000, 'F', 'CuproNiquel', 'Normal', 0.10, 1);
DECLARE @ID75 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID75, 2002, 'Nascimento de Vénus', 0, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID75, 'Mapa da UE B2008', 'B2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.20, 'Italy', 1411536000, 'VG', 'CuproNiquel', 'Normal', 0.20, 1);
DECLARE @ID76 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID76, 2002, 'Umberto Boccioni', 0, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID76, 'Mapa da UE B2008', 'B2008', 1); 

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.50, 'Italy', 1136418000, 'VB', 'CuproNiquel', 'Normal', 0.50, 1);
DECLARE @ID77 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID77, 2002, 'Marco Aurelio', 0, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID77, 'Mapa da UE B2008', 'B2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (1.00, 'Italy', 965725300, 'VB', 'CuproNiquel', 'Normal', 1.0, 1);
DECLARE @ID78 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID78, 2002, 'Da Vinci', 0, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID78, 'Mapa da UE B2008', 'B2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.00, 'Italy', 463402000, 'VG', 'CuproNiquel', 'Normal', 2.0, 1);
DECLARE @ID79 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID79, 2002, 'Dante Alighieri', 0, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID79, 'Mapa da UE B2008', 'B2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.10, 'Italy', 4925000, 'B', 'CuproNiquel', 'Normal', 0.10, 1);
DECLARE @ID80 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID80, 2004, 'Nascimento de Vénus', 0, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID80, 'Mapa da UE B2008', 'B2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.20, 'Italy', 4943900 	, 'B', 'CuproNiquel', 'Normal', 0.20, 1);
DECLARE @ID81 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID81, 2005, 'Boccioni', 0, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID81, 'Mapa da UE B2008', 'B2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.50, 'Italy', 4945900, 'G', 'CuproNiquel', 'Normal', 0.50, 1);
DECLARE @ID82 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID82, 2006, 'Marco Aurelio', 0, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID82, 'Mapa da UE B2008', 'B2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (1.00, 'Italy', 134955490, 'B', 'CuproNiquel', 'Normal', 1.0, 1);
DECLARE @ID83 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID83, 2007, 'Da Vinci', 0, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID83, 'Mapa da UE B2008', 'B2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.00, 'Italy', 2540000, 'VG', 'CuproNiquel', 'Normal', 2.0, 1);
DECLARE @ID84 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID84, 2008, 'Dante Alighieri', 0, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID84, 'Mapa da UE A2008', 'A2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.01, 'Italy', 125448100, 'G', 'CuproNiquel', 'Normal', 0.01, 1);
DECLARE @ID85 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID85, 2010, 'Castel del Monte', 0, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID85, 'Mapa da UE A2008', 'A2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.00, 'Estonia', 11131800, 'UNC', 'CuproNiquel', 'Normal', 2.20, 1);
DECLARE @ID86 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID86, 2011, 'Mapa da Estónia', 0, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID86, 'Mapa da UE A2008', 'A2008', 1);

-- Non European Countries (A adicionar)

-- Moeda 1 - Estados Unidos
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.25, 'United States', 248600000, 'F', 'CuproNiquel', 'Normal', 0.31, 1);
DECLARE @ID87 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID87, 2015, 'Homestead National Monument', 0, 'D', 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID87, 'George Washington Portrait', null, 1);

-- Moeda 2 - Estados Unidos
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (1.00, 'United States', 219025, 'UNC', 'Silver', 'Proof', 58.00, 1);
DECLARE @ID88 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID88, 2019, 'Silver Dollar - Apollo 11 50th Anniversary', 1, 'P', 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID88, 'Eagle Landing on Moon', null, 1);

-- Moeda 3 - Canadá
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.25, 'Canada', 500, 'G', 'Nickel', 'Normal',2000, 1);
DECLARE @ID89 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID89, 2017, '150th Anniversary of Confederation', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID89, 'Caribou Design', null, 1);

-- Moeda 4 - Canadá
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (20.00, 'Canada', 8000, 'UNC', 'Silver', 'Proof', 110.00, 1);
DECLARE @ID90 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID90, 2022, 'Tribute to Queen Elizabeth II', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID90, '4 Portraits of Queen Elizabeth II', null, 1);

-- Moeda 5 - México
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (10.00, 'Mexico', 54822000, 'VF', 'Bicolor Clad', 'Normal', 0.47, 1);
DECLARE @ID91 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID91, 2010, '10 Pesos - Bicentenario de la Independencia', 0, 'Mo', 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID91, 'Ángel de la Independencia', null, 1);

-- Moeda 6 - Brasil
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (1.00, 'Brazil', 19984500, 'VG', 'CuproNiquel', 'Normal', 0.34, 1);
DECLARE @ID92 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID92, 2022, 'Vinicius mascot and Rio 2016 logo', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID92, 'Southern Cross', null, 1);

-- Moeda 7 - Japão
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (500.00, 'Japan', 595743000, 'F', 'Nickel-brass', 'Normal', 3.60, 1);
DECLARE @ID93 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID93, 2000, 'Heisei', 0, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID93, 'Paulownia Flowers Design', null, 1);

-- Moeda 8 - China
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (1.00, 'China', 150000000, 'UNC', 'Nickel', 'Normal', 3.40, 1);
DECLARE @ID94 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID94, 2019, '70th Anniversary of PRC', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID94, 'National Emblem with Flowers', null, 1);

-- Moeda 9 - Austrália
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.50, 'Australia', 148120, 'UNC', 'CuproNiquel', 'BU', 3.30, 1);
DECLARE @ID95 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID95, 2018, 'Elizabeth II', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID95, '4th portrait of Queen Elizabeth II', null, 1);

-- Moeda 10 - África do Sul
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (5.00, 'South Africa', 10000000, 'F', 'CuproNiquel', 'Normal', null, 1);
DECLARE @ID96 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID96, 2013, 'Nelson Mandela', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID96, 'Mandela childhood in Qunu', null, 1);

-- Moeda 11 - Índia
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (10.00, 'India', null, 'VF', 'Bicolor Clad', 'Normal', 0.41, 1);
DECLARE @ID97 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID97, 2011, 'Rabindranath Tagore', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID97, 'Asoka Lion pedestal', null, 1);

-- Moeda 12 - Nova Zelândia
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (1.00, 'New Zealand', 30000, 'UNC', 'Silver', 'Normal', 3.30, 1);
DECLARE @ID98 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID98, 1971, 'Shield of Arms', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID98, 'Elizabeth II - 2nd Portrait', null, 1);

-- Adicionar + 1 Coleçao (Euro)

-- 1. Irlanda
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Ireland', 4500000, 'VF', 'CuproNiquel', 'Normal', 4.50, 2);
DECLARE @ID1001 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID1001, 2016, 'Harpa céltica com estrelas europeias', 1, null, 2);
INSERT INTO [Numismatics].Common_Face VALUES (@ID1001, 'Mapa da UE A2008', 'A2008', 2);

-- 2. Áustria
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Austria', 750000, 'F', 'CuproNiquel', 'Normal', 2.00, 2);
DECLARE @ID1002 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID1002, 2018, 'Athena: Símbolo do parlamentarismo austríaco', 1, null, 2);
INSERT INTO [Numismatics].Common_Face VALUES (@ID1002, 'Mapa da UE A2008', 'A2008', 2);

-- 3. Portugal
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Portugal', 500000, 'VF', 'CuproNiquel', 'BU', 3.80, 2);
DECLARE @ID1003 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID1003, 2013, 'Torre dos Clérigos', 1, null, 2);
INSERT INTO [Numismatics].Common_Face VALUES (@ID1003, 'Mapa da UE A2008', 'A2008', 2);

-- 4. Espanha
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Spain', 4000000, 'G', 'CuproNiquel', 'Normal', 2.00, 2);
DECLARE @ID1004 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID1004, 2012, 'Catedral de Burgos', 1, null, 2);
INSERT INTO [Numismatics].Common_Face VALUES (@ID1004, 'Mapa da UE A2008', 'A2008', 2);

-- 5. Espanha
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Spain', 3981500, 'UNC', 'CuproNiquel', 'Normal', 3.00, 2);
DECLARE @ID1005 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID1005, 2021, 'Cidade Histórica de Toledo', 1, 'M', 2);
INSERT INTO [Numismatics].Common_Face VALUES (@ID1005, 'Mapa da UE A2008', 'A2008', 2);

-- 6. Alemanha (Série dos Estados Federados)
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Germany', 7200000, 'F', 'CuproNiquel', 'Normal', 2.00, 2);
DECLARE @ID1006 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID1006, 2019, 'Castelo de Augustusburg', 1, 'F', 2);
INSERT INTO [Numismatics].Common_Face VALUES (@ID1006, 'Mapa da UE A2008', 'A2008', 2);

-- 7. Alemanha
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Germany', 1040000, 'VF', 'CuproNiquel', 'Normal', 4.00, 2);
DECLARE @ID1007 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID1007, 2007, 'Palácio de Schwerin', 1, 'A', 2);
INSERT INTO [Numismatics].Common_Face VALUES (@ID1007, 'Mapa da UE A2008', 'A2008', 2);

-- 8. Alemanha (Evento Especial)
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Germany',3570000, 'G', 'CuproNiquel', 'Normal', 2.00, 2);
DECLARE @ID1008 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID1008, 2022, 'Programa Erasmus', 1, 'D', 2);
INSERT INTO [Numismatics].Common_Face VALUES (@ID1008, 'Mapa da UE A2008', 'A2008', 2);

-- 9. Alemanha
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Germany', 6300000, 'UNC', 'CuproNiquel', 'Normal', 2.70, 2);
DECLARE @ID1009 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID1009, 2022, 'Turíngia', 1, 'J', 2);
INSERT INTO [Numismatics].Common_Face VALUES (@ID1009, 'Mapa da UE A2008', 'A2008', 2);

-- 11. Portugal (adicional)
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Portugal', 350000, 'UNC', 'CuproNiquel', 'Normal', 3.50, 2);
DECLARE @ID1010 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID1010, 2020, 'Universidade de Coimbra', 1, null, 2);
INSERT INTO [Numismatics].Common_Face VALUES (@ID1010, 'Mapa da UE A2008', 'A2008', 2);

-- 12. Áustria (adicional)
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.0, 'Austria', 15940000, 'UNC', 'CuproNiquel', 'Normal', 3.10, 2);
DECLARE @ID1011 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID1011, 2016, 'Banco Nacional da Áustria', 1, null, 2);
INSERT INTO [Numismatics].Common_Face VALUES (@ID1011, 'Mapa da UE A2008', 'A2008', 2);

-- Adicionar + 1 Colecao (Mundial) - 10 moedas

-- 1. Estados Unidos (Série National Park)
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.25, 'United States', 2000000, 'VF', 'CuproNiquel', 'Normal', 0.25, 3);
DECLARE @ID2001 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID2001, 2019, 'War in the Pacific National Park', 1, 'W', 3);
INSERT INTO [Numismatics].Common_Face VALUES (@ID2001, 'George Washington Portrait', null, 3);

-- 2. Canadá (Special Edition)
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.00, 'Canada', 775000, 'UNC', 'Bimetallic', 'Normal', 4.00, 3);
DECLARE @ID2002 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID2002, 2019, '75th Anniversary of D-Day', 1, null, 3);
INSERT INTO [Numismatics].Common_Face VALUES (@ID2002, 'Queen Elizabeth II Portrait', null, 3);

-- 3. México (Prata Comemorativa)
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (20.00, 'Mexico', 2831000, 'AU', 'Gold', 'Normal', 1500.00, 3);
DECLARE @ID2003 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID2003, 1918, 'Aztec Calendar', 0, null, 3);
INSERT INTO [Numismatics].Common_Face VALUES (@ID2003, 'National Coat of Arms', null, 3);

-- 4. Brasil (Série Real)
INSERT INTO [Numismatics].Coin([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (1.00, 'Brazil', 41728000, 'G', 'Bimetallic', 'Normal', 1.00, 3);
DECLARE @ID2004 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID2004, 2021, '200 Anos da Independência', 0, null, 3);
INSERT INTO [Numismatics].Common_Face VALUES (@ID2004, 'Southern Cross Constellation', null, 3);

-- 5. Japão (Ano Novo)
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (500.00, 'Japan', 184651000, 'F', 'Nickel-brass', 'Normal', 500.00, 3);
DECLARE @ID2005 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID2005, 2021, 'Reiwa 5 New Year', 0, null, 3);
INSERT INTO [Numismatics].Common_Face VALUES (@ID2005, 'Paulownia Flowers', null, 3);

-- 7. Austrália (Fauna Nativa)
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.05, 'Australia', 179016000, 'UNC', 'CuproNiquel', 'Normal', 0.50, 3);
DECLARE @ID2006 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID2006, 1999, 'Elizabeth II', 0, null, 3);
INSERT INTO [Numismatics].Common_Face VALUES (@ID2006, 'Queen Elizabeth 4th Portrait', null, 3);

-- 8. África do Sul (Big Five)
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.10, 'South Africa', 101000000, 'VF', 'Steel', 'Normal', 0.30, 3);
DECLARE @ID2007 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID2007, 2000, 'Motto in Bochimans', 0, null, 3);
INSERT INTO [Numismatics].Common_Face VALUES (@ID2007, 'Motto in Bochimans', null, 3);

-- 9. Índia (Gandhi Series)
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2, 'India', null, 'G', 'Steel', 'Normal', 0.03, 3);
DECLARE @ID2008 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID2008, 2011, 'Ashoka Pillar', 0, 'Diamond', 3);
INSERT INTO [Numismatics].Common_Face VALUES (@ID2008, 'Lothus Flowers', null, 3);

-- 10. Nova Zelândia (Bird Series)
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (1.00, 'New Zealand', 10000000, 'UNC', 'Aluminum Bronze', 'Normal', null, 3);
DECLARE @ID2009 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID2009, 1991, 'Kiwi surrounded by leaves and native ferns', 0, null, 3);
INSERT INTO [Numismatics].Common_Face VALUES (@ID2009, 'Queen Elizabeth II Portrait 3', null, 3);

-- 11. Turquia (Histórica)
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (1.00, 'Turkey', 585240000, 'VF', 'Bimetallic', 'Normal', 0.10, 3);
DECLARE @ID2010 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID2010, 2023, 'Head of Mustafa Kemal Atatürk facing left', 0, null, 3);
INSERT INTO [Numismatics].Common_Face VALUES (@ID2010, 'Crescent and Star', null, 3);

-- 12. Egito (Antiguidades)
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (1.00, 'Egypt', null, 'VF', 'Bimetallic', 'Normal', 0.50, 3);
DECLARE @ID2011 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID2011, 2018, 'Tutankhamun Mask', 0, null, 3);
INSERT INTO [Numismatics].Common_Face VALUES (@ID2011, 'Eagle of Saladin', null, 3);

-- 13. Argentina (Patriotismo)
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.50, 'Argentina', 58400000, 'VG', 'Aluminum Bronze', 'Normal', 0.25, 3);
DECLARE @ID2012 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID2012, 2010, 'Tucuman Building', 0, 'fine letters', 3);
INSERT INTO [Numismatics].Common_Face VALUES (@ID2012, '50 Centavos Value', null, 3);

-- 14. Rússia (Eventos Esportivos)
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (5.00, 'Russia', null, 'UNC', 'CuproNiquel', 'Normal', 1.30, 3);
DECLARE @ID2013 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID2013, 2008, 'Stylized vegetable ornament', 0, 'ММД', 3);
INSERT INTO [Numismatics].Common_Face VALUES (@ID2013, 'Double-Headed Eagle', null, 3);

-- Adicionar + 1 Colecao (Antiga) - 3 moedas

-- 1. Portugal (1932 - República)
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.50, 'Portugal', 2592000, 'G', 'Silver', 'Normal', 2.00, 4);
DECLARE @ID3001 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID3001, 1932, 'Boat sailing to the left', 0, null, 4);
INSERT INTO [Numismatics].Common_Face VALUES (@ID3001, 'Portuguese shield', null, 4);

-- 2. Brasil (1942 - Estado Novo)
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (1.00, 'Brazil', 381000, 'VG', 'Aluminum Bronze', 'Normal', 3.00, 4);
DECLARE @ID3002 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID3002, 1942, 'Topographical map of Brazil', 0, null, 4);
INSERT INTO [Numismatics].Common_Face VALUES (@ID3002, 'Efígie da República', null, 4);

-- 3. Estados Unidos (1943 - Guerra)
INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (0.05, 'United States', 57873000, 'F', 'Steel', 'Normal', 1.40, 4);
DECLARE @ID3003 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID3003, 1943, 'Jefferson Wartime Nickel', 0, 'P', 4);
INSERT INTO [Numismatics].Common_Face VALUES (@ID3003, 'Monticello', null, 4);

-- Moedas Comemorativas Portuguesas Não 2 Euros (A adicionar)

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (7.50, 'Portugal', 30000, 'UNC', 'Silver', 'Normal', 7.50, 1);
DECLARE @ID99 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID99, 2022, 'José Saramago 100 Years', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID99, 'José Saramago 100 Years', null, 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (5.00, 'Portugal', 4000, 'UNC', 'Silver', 'Proof', 65.00, 1);
DECLARE @ID100 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID100, 2024, 'UEFA Euro 2024', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID100, 'UEFA Euro 2024', null, 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (7.50, 'Portugal', 30000, 'UNC', 'Silver', 'Normal', 7.50, 1);
DECLARE @ID101 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID101, 2025, '900 years of Ponte de Lima charter', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID101, '900 years of Ponte de Lima charter', null, 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (7.50, 'Portugal', 30000, 'UNC', 'Silver', 'Normal', 7.50, 1);
DECLARE @ID102 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID102, 2022, '111th Anniversary of ISEG', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID102, 'ISEG OPEN MINDS GRAB THE FUTURE', null, 1);

-- Material and Finishing Variations (A adicionar)

--      Proof - 2

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (7.50, 'Portugal', 1500, 'UNC', 'Gold', 'Proof', 2850.00, 1);
DECLARE @ID103 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID103, 2025, '900 years of Ponte de Lima charter', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID103, '900 years of Ponte de Lima charter', null, 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (10.00, 'Portugal', 3950, 'UNC', 'Silver', 'Proof', 97.00, 1);
DECLARE @ID104 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID104, 2024, 'Liberdade, Liberdade!', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID104, 'Esta é a madrugada que eu esperava', null, 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (5.00, 'Portugal', 2000, 'UNC', 'Gold', 'Proof', 1395.00, 1);
DECLARE @ID105 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID105, 2024, 'Mobiliário Indo-Português — Portugal e o Oriente (Portugal e Índia)', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID105, 'Mobiliário Indo-Português', null, 1);

--	    FDC - 3

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (1.00, 'Portugal', 3000, 'UNC', 'CuproNiquel', 'FDC', 6.00, 1);
DECLARE @ID106 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID106, 2023, 'Moeda da Comemoração', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID106, 'Moeda da Comemoração', null, 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (1.00, 'Portugal', 3000, 'UNC', 'CuproNiquel', 'FDC', 6.00, 1);
DECLARE @ID107 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID107, 2024, 'Moeda da Comemoração', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID107, 'Moeda da Comemoração', null, 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (1.00, 'Portugal', 3000, 'UNC', 'CuproNiquel', 'FDC', 6.00, 1);
DECLARE @ID108 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID108, 2025, 'Moeda da Comemoração', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID108, 'Moeda da Comemoração', null, 1);

--	    BNC - 2

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.00, 'Portugal', 7500, 'UNC', 'CuproNiquel', 'BNC', 9.50, 1);
DECLARE @ID109 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID109, 2022, 'Centenário da Travessia do Atlântico-Sul', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID109, 'Mapa da UE A2008', 'A2008', 1);

INSERT INTO [Numismatics].Coin ([Value], Country_Name, Mintage, Condition, Material, Finishing, Market_Price, ID_Colection)
VALUES (2.00, 'Portugal', 7500, 'UNC', 'CuproNiquel', 'BNC', 9.50, 1);
DECLARE @ID110 INT = SCOPE_IDENTITY();
INSERT INTO [Numismatics].Nacional_Face VALUES (@ID110, 2022, '35.° Aniversário do Programa Erasmus', 1, null, 1);
INSERT INTO [Numismatics].Common_Face VALUES (@ID110, 'Mapa da UE A2008', 'A2008', 1);

INSERT INTO [Numismatics].Author (NIF, Fname, Lname, Nacionality, BDate) VALUES
(991001, 'Maria', 'Silva', 'Portugal', '1975-03-22'),
(991002, 'João', 'Costa', 'Portugal', '1980-07-15'),
(991003, 'Anna', 'Schmidt', 'Germany', '1965-11-09'),
(991004, 'Jean', 'Dubois', 'France', '1970-05-30'),
(991005, 'Elena', 'Garcia', 'Spain', '1988-02-14'),
(991006, 'Luca', 'Rossi', 'Italy', '1972-06-11'),
(991072, 'Luc', 'Luycx', 'Belgium', '1958-04-11'),
(991073, 'João', 'Silva', 'Portugal', '1985-03-12'),
(991074, 'Sofia', 'Martins', 'Portugal', '1990-07-22'),
(991075, 'Carlos', 'Gomes', 'Portugal', '1978-11-04'),
(991076, 'Lucía', 'Fernández', 'Espanha', '1989-05-19'),
(991077, 'Miguel', 'Rodríguez', 'Espanha', '1983-01-30'),
(991078, 'Isabelle', 'Moreau', 'França', '1992-09-15'),
(991079, 'Pierre', 'Dubois', 'França', '1980-12-02'),
(991080, 'Laura', 'Schmidt', 'Alemanha', '1987-06-10'),
(991081, 'Hans', 'Müller', 'Alemanha', '1975-10-25'),
(991082, 'Giulia', 'Rossi', 'Itália', '1995-04-08'),
(991083, 'Luca', 'Bianchi', 'Itália', '1982-08-14'),
(991084, 'Eva', 'Janssen', 'Países Baixos', '1991-03-29'),
(991085, 'Thomas', 'De Vries', 'Países Baixos', '1984-07-01'),
(991086, 'Anna', 'Mäkinen', 'Finlândia', '1990-12-17'),
(991087, 'Matti', 'Virtanen', 'Finlândia', '1979-02-05'),
(991088, 'Elena', 'Papadopoulou', 'Grécia', '1988-09-21'),
(991089, 'Nikos', 'Georgiou', 'Grécia', '1981-06-11'),
(991090, 'Katrin', 'Meier', 'Áustria', '1993-11-03'),
(991091, 'Florian', 'Huber', 'Áustria', '1986-05-27'),
(991092, 'Eva', 'Lukács', 'Eslováquia', '1989-08-09'),
(991009, 'Sophie', 'Lemoine', 'France', '1979-10-03'),
(991010, 'Miguel', 'Almeida', 'Portugal', '1991-01-20'),
(991011, 'Laura', 'Müller', 'Germany', '1985-04-12'),
(991013, 'Ivana', 'Novak', 'Croatia', '1982-06-19'),
(991014, 'Petros', 'Papadopoulos', 'Greece', '1968-08-23'),
(111007, 'Emma', 'Johnson', 'United Kingdom', '1990-09-01'),
(111012, 'Tomasz', 'Kowalski', 'Poland', '1977-07-08'),
(111015, 'Katarzyna', 'Nowak', 'Poland', '1993-03-01'),
(111016, 'Andrei', 'Popescu', 'Romania', '1974-11-29'),
(111018, 'Erik', 'Andersson', 'Sweden', '1980-02-17'),
(331019, 'John', 'Smith', 'United States', '1976-08-12'),
(331109, 'James', 'Adams', 'United States', '1971-02-15'),
(331020, 'Robert', 'Williams', 'Canada', '1982-01-05'),
(331021, 'Luis', 'Hernandez', 'Mexico', '1979-04-21'),
(441017, 'Natalia', 'Ivanova', 'Russia', '1987-05-25'),
(441039, 'Hiroshi', 'Tanaka', 'Japan', '1969-11-02'), 
(441040, 'Wei', 'Zhang', 'China', '1981-10-19'),
(441041, 'Min-Jun', 'Kim', 'South Korea', '1986-03-16'),
(441042, 'Jong-Su', 'Ri', 'North Korea', '1978-07-20'),
(441043, 'Chih-Hao', 'Lin', 'Taiwan', '1984-02-24'),
(441044, 'Bat', 'Erdene', 'Mongolia', '1980-08-31'),
(441045, 'Adi', 'Putra', 'Indonesia', '1983-12-14'),
(441046, 'Ahmad', 'Zulkifli', 'Malaysia', '1985-11-28'),
(441047, 'Somsak', 'Chaiyapan', 'Thailand', '1976-09-09'),
(441048, 'Nguyen', 'Van Hoa', 'Vietnam', '1988-07-04'),
(441049, 'Jose', 'Santos', 'Philippines', '1989-01-12'),
(441050, 'Wei', 'Tan', 'Singapore', '1977-05-06'),
(441051, 'Raj', 'Kumar', 'India', '1973-03-17'),
(441052, 'Ali', 'Khan', 'Pakistan', '1982-06-22'),
(441053, 'Hasan', 'Rahman', 'Bangladesh', '1984-08-25'),
(441054, 'Sunil', 'Perera', 'Sri Lanka', '1975-12-18'),
(441055, 'Dinesh', 'Thapa', 'Nepal', '1987-04-03'),
(441056, 'Fahad', 'Al-Mutairi', 'Saudi Arabia', '1981-07-30'),
(441057, 'Reza', 'Mohammadi', 'Iran', '1978-10-01'),
(441058, 'Ali', 'Hussein', 'Iraq', '1983-11-15'),
(441059, 'Omar', 'Al-Farsi', 'United Arab Emirates', '1986-09-07'),
(441060, 'David', 'Cohen', 'Israel', '1972-02-11'),
(441061, 'Mehmet', 'Yildiz', 'Turkey', '1979-06-16'),
(441062, 'Hamad', 'Al-Ansari', 'Qatar', '1985-01-22'),
(441067, 'Ivan', 'Petrov', 'Russia', '1985-08-12'),
(441068, 'Nurbolat', 'Amanov', 'Kazakhstan', '1976-07-01'),
(441070, 'Dilshod', 'Karimov', 'Uzbekistan', '1983-01-09'),
(441071, 'Ahmad', 'Nazari', 'Afghanistan', '1980-05-19'),
(771008, 'Carlos', 'Santos', 'Brazil', '1983-12-05'),
(771022, 'Bruno', 'Souza', 'Brazil', '1984-11-07'), 
(771023, 'Juan', 'Martinez', 'Argentina', '1981-02-18'),
(771024, 'Diego', 'Torres', 'Chile', '1987-10-09'),
(771025, 'Andres', 'Gomez', 'Colombia', '1989-05-11'),
(771026, 'Marco', 'Salas', 'Peru', '1975-07-29'),
(771027, 'Alejandro', 'Morales', 'Venezuela', '1973-09-14'),
(771028, 'Ricardo', 'Lopez', 'Panama', '1988-01-03'),
(771029, 'Daniel', 'Jimenez', 'Costa Rica', '1976-06-26'),
(221030, 'Ernesto', 'Reyes', 'Cuba', '1980-12-20'),
(221031, 'Raul', 'Mendez', 'Dominican Republic', '1972-08-30'),
(221032, 'Thabo', 'Mokoena', 'South Africa', '1971-03-11'),
(221033, 'Chinedu', 'Okafor', 'Nigeria', '1986-10-17'),
(221034, 'Omar', 'Hassan', 'Egypt', '1985-04-06'),
(221035, 'Joseph', 'Mwangi', 'Kenya', '1979-12-01'),
(221036, 'Kwame', 'Boateng', 'Ghana', '1977-09-13'),
(221037, 'Youssef', 'El Amrani', 'Morocco', '1983-06-08'),
(221038, 'Abebe', 'Bekele', 'Ethiopia', '1974-05-27'),
(221069, 'James', 'Gatluak', 'South Sudan', '1989-09-29'),
(001063, 'Jack', 'Brown', 'Australia', '1974-03-29'), 
(001064, 'Liam', 'Taylor', 'New Zealand', '1980-12-10'),
(001065, 'Jone', 'Koro', 'Fiji', '1982-10-14'),
(001066, 'Ala', 'Tua', 'Papua New Guinea', '1977-11-05');

INSERT INTO [Numismatics].Author (NIF, Fname, Lname, Nacionality, BDate) VALUES
(991067, 'Aili', 'Kallas', 'Estonia', '1985-01-20');

-- 11 Europe without Euro
-- 99 Europe Zone Euro
-- 44 Asia
-- 77 South America and Central America
-- 00 Oceania
-- 22 Africa
-- 33 North America

-- CF Has Authors Table

INSERT into [Numismatics].CF_Has_Author (Coin_ID ,[Description], NIF_Author_CF, ID_Colection) VALUES

-- Moedas Europeias
-- Coins with "Mapa da UE B2008" (author 991072)
(@ID1, 'Mapa da UE B2008', 991072, 1),
(@ID2, 'Mapa da UE B2008', 991072, 1),
(@ID9, 'Mapa da UE B2008', 991072, 1),
(@ID23, 'Mapa da UE B2008', 991072, 1),
(@ID24, 'Mapa da UE B2008', 991072, 1),
(@ID42, 'Mapa da UE B2008', 991072, 1),
(@ID43, 'Mapa da UE B2008', 991072, 1),
(@ID44, 'Mapa da UE B2008', 991072, 1),
(@ID45, 'Mapa da UE B2008', 991072, 1),
(@ID46, 'Mapa da UE B2008', 991072, 1),
(@ID47, 'Mapa da UE B2008', 991072, 1),
(@ID54, 'Mapa da UE B2008', 991072, 1),
(@ID55, 'Mapa da UE B2008', 991072, 1),
(@ID56, 'Mapa da UE B2008', 991072, 1),
(@ID57, 'Mapa da UE B2008', 991072, 1),
(@ID58, 'Mapa da UE B2008', 991072, 1),
(@ID59, 'Mapa da UE B2008', 991072, 1),
(@ID60, 'Mapa da UE B2008', 991072, 1),
(@ID61, 'Mapa da UE B2008', 991072, 1),
(@ID62, 'Mapa da UE B2008', 991072, 1),
(@ID63, 'Mapa da UE B2008', 991072, 1),
(@ID64, 'Mapa da UE B2008', 991072, 1),
(@ID65, 'Mapa da UE B2008', 991072, 1),
(@ID66, 'Mapa da UE B2008', 991072, 1),
(@ID67, 'Mapa da UE B2008', 991072, 1),
(@ID69, 'Mapa da UE B2008', 991072, 1),
(@ID70, 'Mapa da UE B2008', 991072, 1),
(@ID72, 'Mapa da UE B2008', 991072, 1),
(@ID73, 'Mapa da UE B2008', 991072, 1),
(@ID74, 'Mapa da UE B2008', 991072, 1),
(@ID75, 'Mapa da UE B2008', 991072, 1),
(@ID76, 'Mapa da UE B2008', 991072, 1),
(@ID77, 'Mapa da UE B2008', 991072, 1),
(@ID78, 'Mapa da UE B2008', 991072, 1),
(@ID79, 'Mapa da UE B2008', 991072, 1),
(@ID80, 'Mapa da UE B2008', 991072, 1),
(@ID81, 'Mapa da UE B2008', 991072, 1),
(@ID82, 'Mapa da UE B2008', 991072, 1),
(@ID83, 'Mapa da UE B2008', 991072, 1);

INSERT into [Numismatics].CF_Has_Author (Coin_ID ,[Description], NIF_Author_CF, ID_Colection) VALUES

-- Coins with "Mapa da UE A2008" (author 991071)
(@ID3, 'Mapa da UE A2008', 991072, 1),
(@ID4, 'Mapa da UE A2008', 991072, 1),
(@ID5, 'Mapa da UE A2008', 991072, 1),
(@ID6, 'Mapa da UE A2008', 991072, 1),
(@ID7, 'Mapa da UE A2008', 991072, 1),
(@ID8, 'Mapa da UE A2008', 991072, 1),
(@ID10, 'Mapa da UE A2008', 991072, 1),
(@ID11, 'Mapa da UE A2008', 991072, 1),
(@ID12, 'Mapa da UE A2008', 991072, 1),
(@ID13, 'Mapa da UE A2008', 991072, 1),
(@ID14, 'Mapa da UE A2008', 991072, 1),
(@ID15, 'Mapa da UE A2008', 991072, 1),
(@ID16, 'Mapa da UE A2008', 991072, 1),
(@ID17, 'Mapa da UE A2008', 991072, 1),
(@ID18, 'Mapa da UE A2008', 991072, 1),
(@ID19, 'Mapa da UE A2008', 991072, 1),
(@ID20, 'Mapa da UE A2008', 991072, 1),
(@ID21, 'Mapa da UE A2008', 991072, 1),
(@ID25, 'Mapa da UE A2008', 991072, 1),
(@ID26, 'Mapa da UE A2008', 991072, 1),
(@ID27, 'Mapa da UE A2008', 991072, 1),
(@ID28, 'Mapa da UE A2008', 991072, 1),
(@ID29, 'Mapa da UE A2008', 991072, 1),
(@ID30, 'Mapa da UE A2008', 991072, 1),
(@ID31, 'Mapa da UE A2008', 991072, 1),
(@ID32, 'Mapa da UE A2008', 991072, 1),
(@ID33, 'Mapa da UE A2008', 991072, 1),
(@ID34, 'Mapa da UE A2008', 991072, 1),
(@ID35, 'Mapa da UE A2008', 991072, 1),
(@ID36, 'Mapa da UE A2008', 991072, 1),
(@ID37, 'Mapa da UE A2008', 991072, 1),
(@ID38, 'Mapa da UE A2008', 991072, 1),
(@ID39, 'Mapa da UE A2008', 991072, 1),
(@ID40, 'Mapa da UE A2008', 991072, 1),
(@ID41, 'Mapa da UE A2008', 991072, 1),
(@ID48, 'Mapa da UE A2008', 991072, 1),
(@ID49, 'Mapa da UE A2008', 991072, 1),
(@ID50, 'Mapa da UE A2008', 991072, 1),
(@ID51, 'Mapa da UE A2008', 991072, 1),
(@ID52, 'Mapa da UE A2008', 991072, 1),
(@ID53, 'Mapa da UE A2008', 991072, 1),
(@ID68, 'Mapa da UE A2008', 991072, 1),
(@ID71, 'Mapa da UE A2008', 991072, 1),
(@ID84, 'Mapa da UE A2008', 991072, 1),
(@ID85, 'Mapa da UE A2008', 991072, 1),
(@ID86, 'Mapa da UE A2008', 991072, 1),
(@ID1001, 'Mapa da UE A2008', 991072, 2),
(@ID1002, 'Mapa da UE A2008', 991072, 2),
(@ID1003, 'Mapa da UE A2008', 991072, 2),
(@ID1004, 'Mapa da UE A2008', 991072, 2),
(@ID1005, 'Mapa da UE A2008', 991072, 2),
(@ID1006, 'Mapa da UE A2008', 991072, 2),
(@ID1007, 'Mapa da UE A2008', 991072, 2),
(@ID1008, 'Mapa da UE A2008', 991072, 2),
(@ID1009, 'Mapa da UE A2008', 991072, 2),
(@ID1010, 'Mapa da UE A2008', 991072, 2),
(@ID1011, 'Mapa da UE A2008', 991072, 2);

INSERT into [Numismatics].CF_Has_Author (Coin_ID ,[Description], NIF_Author_CF, ID_Colection) VALUES

-- Non-European coins with other common face designs
(@ID87, 'George Washington Portrait', 331019, 1),
(@ID88, 'Eagle Landing on Moon', 331019, 1),
(@ID89, 'Caribou Design', 331020, 1),
(@ID90, '4 Portraits of Queen Elizabeth II', 331020, 1),
(@ID91, 'Ángel de la Independencia', 331021, 1),
(@ID92, 'Southern Cross', 771008, 1),
(@ID93, 'Paulownia Flowers Design', 441039, 1),
(@ID94, 'National Emblem with Flowers', 441040, 1),
(@ID95, '4th portrait of Queen Elizabeth II', 001063, 1),
(@ID96, 'Mandela childhood in Qunu', 221032, 1),
(@ID97, 'Asoka Lion pedestal', 441051, 1),
(@ID98, 'Elizabeth II - 2nd Portrait', 001064, 1),
(@ID2001, 'George Washington Portrait', 331019, 3),
(@ID2002, 'Queen Elizabeth II Portrait', 331020, 3);

INSERT into [Numismatics].CF_Has_Author (Coin_ID ,[Description], NIF_Author_CF, ID_Colection) VALUES

(@ID2003, 'National Coat of Arms', 331021, 3),
(@ID2004, 'Southern Cross Constellation', 771008, 3),
(@ID2005, 'Paulownia Flowers', 441039, 3),
(@ID2006, 'Queen Elizabeth 4th Portrait', 001063, 3),
(@ID2007, 'Motto in Bochimans', 221032, 3);

INSERT into [Numismatics].CF_Has_Author (Coin_ID ,[Description], NIF_Author_CF, ID_Colection) VALUES

(@ID2008, 'Lothus Flowers', 441051, 3);

INSERT into [Numismatics].CF_Has_Author (Coin_ID ,[Description], NIF_Author_CF, ID_Colection) VALUES

(@ID2009, 'Queen Elizabeth II Portrait 3', 001064, 3),
(@ID2010, 'Crescent and Star', 441061, 3),
(@ID2011, 'Eagle of Saladin', 221034, 3),
(@ID2012, '50 Centavos Value', 771023, 3),
(@ID2013, 'Double-Headed Eagle', 441067, 3),
(@ID3001, 'Portuguese shield', 991001, 4),
(@ID3002, 'Efígie da República', 771008, 4),
(@ID3003, 'Monticello', 331019, 4),
(@ID99, 'José Saramago 100 Years', 991001, 1),
(@ID100, 'UEFA Euro 2024', 991002, 1),
(@ID101, '900 years of Ponte de Lima charter', 991073, 1),
(@ID102, 'ISEG OPEN MINDS GRAB THE FUTURE', 991074, 1),
(@ID103, '900 years of Ponte de Lima charter', 991075, 1),
(@ID104, 'Esta é a madrugada que eu esperava', 991010, 1),
(@ID105, 'Mobiliário Indo-Português', 991001, 1),
(@ID106, 'Moeda da Comemoração', 991002, 1),
(@ID107, 'Moeda da Comemoração', 991002, 1),
(@ID108, 'Moeda da Comemoração', 991002, 1);

INSERT into [Numismatics].CF_Has_Author (Coin_ID ,[Description], NIF_Author_CF, ID_Colection) VALUES

(@ID109, 'Mapa da UE A2008', 991072, 1),
(@ID110, 'Mapa da UE A2008', 991072, 1);

-- NF Has Authors Table

INSERT into [Numismatics].NF_Has_Author (Coin_ID, [Description], NIF_Author_NF, ID_Colection) VALUES
    -- Moedas Europeias COMEMORATIVAS

    -- Moedas de Portugal
    (@ID1, '50º Aniversário do Tratado de Roma', 991001, 1),
    (@ID2, 'Presidência Portuguesa do Conselho da UE', 991073, 1),
    (@ID3, '10º Aniversário da União Económica e Monetária', 991074, 1),
    (@ID4, '10 Anos de Notas e Moedas em Euro', 991075, 1),
    (@ID5, 'Ano Tradicional da Agricultura Familiar', 991001, 1),
    (@ID6, '30º Aniversário da Bandeira da Europa', 991073, 1),
    (@ID7, 'Rio 2016 - Equipa Olímpica de Portugal', 991074, 1),
    (@ID8, '150 Anos do Nascimento de Raúl Solnado Brandao', 991075, 1);

INSERT into [Numismatics].NF_Has_Author (Coin_ID, [Description], NIF_Author_NF, ID_Colection) VALUES

    -- Moedas de França
    (@ID9, '50º Aniversário do Tratado de Roma', 991004, 1),
    (@ID10, 'Presidência Francesa do Conselho da UE', 991078, 1),
    (@ID11, '70º Aniversário do Apelo de 18 de Junho', 991079, 1),
    (@ID12, '30º Aniversário da Festa da Música', 991004, 1),
    (@ID13, '10 Anos de Notas e Moedas em Euro', 991078, 1),
    (@ID14, 'Pierre de Coubertin', 991079, 1),
    (@ID15, 'Dia D - Desembarque na Normandia', 991004, 1),
    (@ID16, '225º Aniversário da Federação', 991078, 1),
    (@ID17, '30 Anos da Bandeira Europeia', 991079, 1),
    (@ID18, 'Euro 2016 - Campeonato de Futebol', 991004, 1),
    (@ID19, 'François Mitterrand', 991078, 1),
    (@ID20, '200º Aniversário da Morte de Auguste Rodin', 991079, 1),
    (@ID21, '60 Anos de Astérix', 991004, 1);

INSERT into [Numismatics].NF_Has_Author (Coin_ID, [Description], NIF_Author_NF, ID_Colection) VALUES

    -- Moedas da Alemanha
    (@ID23, 'Holstentor em Lübeck (Schleswig-Holstein)', 991003, 1),
    (@ID24, 'Igreja de São Miguel em Hamburgo', 991003, 1),
    (@ID25, 'Castelo de Neuschwanstein (Baviera)', 991003, 1),
    (@ID26, 'Igreja de São Ludgerus (Saarland)', 991003, 1),
    (@ID27, 'Catedral de Bremen', 991003, 1),
    (@ID28, 'Catedral de Colônia (Nordrhein-Westfalen)', 991003, 1),
    (@ID29, 'Palácio de Würzburg (Bayern)', 991003, 1),
    (@ID30, 'Mosteiro de Maulbronn (Baden-Württemberg)', 991003, 1),
    (@ID31, 'Igreja de São Miguel em Hildesheim (Niedersachsen)', 991003, 1),
    (@ID32, 'Igreja de São Paulo em Frankfurt', 991003, 1),
    (@ID33, 'Zwinger em Dresden (Saxônia)', 991003, 1),
    (@ID34, 'Porta de Brandemburgo', 991003, 1),
    (@ID35, 'Castelo de Charlottenburg (Berlim)', 991003, 1),

    -- Moedas da Espanha
    (@ID36, 'Mesquita de Córdoba (Património Mundial)', 991005, 1),
    (@ID37, 'Parque Güell – Gaudí (Património Mundial)', 991076, 1),
    (@ID38, 'Caverna de Altamira (Património Mundial)', 991077, 1),

    -- Moeda da Finlândia
    (@ID39, '100 Anos da Independência da Finlândia', 991072, 1),

    -- Moedas da Itália
    (@ID40, '150º Aniversário da Unificação Italiana', 991006, 1),
    (@ID41, '450º Aniversário do nascimento de Galileu Galilei', 991006, 1);

INSERT into [Numismatics].NF_Has_Author (Coin_ID, [Description], NIF_Author_NF, ID_Colection) VALUES

    --MOEDAS europeias Não Comemorativas


    -- Moedas de Portugal
    (@ID42, 'Selo de 1134', 991001, 1),
    (@ID43, 'Selo de 1134', 991001, 1),
    (@ID44, 'Selo de 1134', 991001, 1),
    (@ID45, 'Real Arms', 991074, 1),
    (@ID46, 'Real Arms', 991074, 1),
    (@ID47, 'Real Arms', 991074, 1),
    (@ID48, 'Royal Seal', 991002, 1),
    (@ID49, 'Royal Seal', 991002, 1),
    (@ID50, 'Real Arms', 991074, 1),
    (@ID51, 'Real Arms', 991074, 1),
    (@ID52, 'Real Arms', 991074, 1),
    (@ID53, 'Royal Seal', 991002, 1),

    -- Moedas da Alemanha
    (@ID54, 'Águia Federal', 991080, 1),
    (@ID55, 'Águia Federal', 991080, 1),
    (@ID56, 'Águia Federal', 991080, 1),
    (@ID57, 'Águia Federal', 991080, 1),
    (@ID58, 'Águia Federal', 991080, 1),
    (@ID59, 'Águia Federal', 991080, 1),
    (@ID60, 'Águia Federal', 991080, 1),
    (@ID61, 'Águia Federal', 991080, 1),
    (@ID62, 'Águia Federal', 991080, 1),
    (@ID63, 'Águia Federal', 991080, 1),

    -- Moedas da Bélgica
    (@ID64, 'Rei Albert II', 991072, 1),
    (@ID65, 'Rei Albert II', 991072, 1),
    (@ID66, 'Rei Albert II', 991072, 1),
    (@ID67, 'Rei Albert II', 991072, 1),
    (@ID68, 'Rei Philippe', 991072, 1),

    -- Moedas dos Países Baixos
    (@ID69, 'Rainha Beatrix', 991084, 1),
    (@ID70, 'Rainha Beatrix', 991084, 1),
    (@ID71, 'Rei Willem-Alexander', 991085, 1);

INSERT into [Numismatics].NF_Has_Author (Coin_ID, [Description], NIF_Author_NF, ID_Colection) VALUES
    -- Moedas da Itália
    (@ID72, 'Castel del Monte', 991083, 1),     
    (@ID73, 'Mole Antonelliana', 991083, 1),     
    (@ID75, 'Nascimento de Vénus', 991082, 1),
    (@ID76, 'Umberto Boccioni', 991082, 1),
    (@ID77, 'Marco Aurelio', 991082, 1),
    (@ID78, 'Da Vinci', 991083, 1),
    (@ID79, 'Dante Alighieri', 991083, 1),
    (@ID80, 'Nascimento de Vénus', 991082, 1),
    (@ID81, 'Boccioni', 991083, 1),
    (@ID82, 'Marco Aurelio', 991082, 1),
    (@ID83, 'Da Vinci', 991083, 1),
    (@ID84, 'Dante Alighieri', 991083, 1),
    (@ID85, 'Castel del Monte', 991083, 1),

    -- Moedas da Estónia
    (@ID86, 'Mapa da Estónia', 991067, 1);

INSERT into [Numismatics].NF_Has_Author (Coin_ID, [Description], NIF_Author_NF, ID_Colection) VALUES

    -- Non European Countries

    (@ID87, 'Homestead National Monument', 331019, 1),
    (@ID88, 'Silver Dollar - Apollo 11 50th Anniversary', 331019, 1),
    (@ID89, '150th Anniversary of Confederation', 331020, 1),
    (@ID90, 'Tribute to Queen Elizabeth II', 331020, 1),
    (@ID91, '10 Pesos - Bicentenario de la Independencia', 331021, 1),
    (@ID92, 'Vinicius mascot and Rio 2016 logo', 771008, 1),
    (@ID93, 'Heisei', 441039, 1),
    (@ID94, '70th Anniversary of PRC', 441040, 1),
    (@ID95, 'Elizabeth II', 001063, 1),
    (@ID96, 'Nelson Mandela', 221032, 1),
    (@ID97, 'Rabindranath Tagore', 441051, 1),
    (@ID98, 'Shield of Arms', 001064, 1),


    -- Adicionar + 1 Coleçao (Euro)


    (@ID1001, 'Harpa céltica com estrelas europeias', 991090, 2),
    (@ID1002, 'Athena: Símbolo do parlamentarismo austríaco', 991090, 2),
    (@ID1003, 'Torre dos Clérigos', 991001, 2),
    (@ID1004, 'Catedral de Burgos', 991005, 2),
    (@ID1005, 'Cidade Histórica de Toledo', 991076, 2),
    (@ID1006, 'Castelo de Augustusburg', 991080, 2),
    (@ID1007, 'Palácio de Schwerin', 991011, 2),
    (@ID1008, 'Programa Erasmus', 991080, 2),
    (@ID1009, 'Turíngia', 991011, 2),
    (@ID1010, 'Universidade de Coimbra', 991002, 2),
    (@ID1011, 'Banco Nacional da Áustria', 991091, 2),


    -- Adicionar + 1 Colecao (Mundial) - 10 moedas

    (@ID2001, 'War in the Pacific National Park', 331019, 3),
    (@ID2002, '75th Anniversary of D-Day', 331020, 3),
    (@ID2003, 'Aztec Calendar', 331021, 3),
    (@ID2004, '200 Anos da Independência', 771008, 3),
    (@ID2005, 'Reiwa 5 New Year', 441039, 3),
    (@ID2006, 'Elizabeth II', 001063, 3),
    (@ID2007, 'Motto in Bochimans', 221032, 3),
    (@ID2008, 'Ashoka Pillar', 441051, 3),
    (@ID2009, 'Kiwi surrounded by leaves and native ferns', 001064, 3),
    (@ID2010, 'Head of Mustafa Kemal Atatürk facing left', 441061, 3),
    (@ID2011, 'Tutankhamun Mask', 221034, 3),
    (@ID2012, 'Tucuman Building', 771023, 3),
    (@ID2013, 'Stylized vegetable ornament', 441067, 3),

    -- Adicionar + 1 Colecao (Antiga) - 3 moedas

    (@ID3001, 'Boat sailing to the left', 991001, 4),
    (@ID3002, 'Topographical map of Brazil', 771008, 4),  
    (@ID3003, 'Jefferson Wartime Nickel', 331019, 4),

    -- Moedas Comemorativas Portuguesas Não 2 Euros
    (@ID99, 'José Saramago 100 Years', 991001, 1),
    (@ID100, 'UEFA Euro 2024', 991002, 1),
    (@ID101, '900 years of Ponte de Lima charter', 991073, 1),
    (@ID102, '111th Anniversary of ISEG', 991074, 1),

    -- Material and Finishing Variations (Proof)
    (@ID103, '900 years of Ponte de Lima charter', 991075, 1),
    (@ID104, 'Liberdade, Liberdade!', 991010, 1),
    (@ID105, 'Mobiliário Indo-Português — Portugal e o Oriente (Portugal e Índia)', 991001, 1),

    -- FDC
    (@ID106, 'Moeda da Comemoração', 991002, 1),
    (@ID107, 'Moeda da Comemoração', 991073, 1),
    (@ID108, 'Moeda da Comemoração', 991074, 1),

    -- BNC
    (@ID109, 'Centenário da Travessia do Atlântico-Sul', 991075, 1),
    (@ID110, '35.° Aniversário do Programa Erasmus', 991010, 1);



