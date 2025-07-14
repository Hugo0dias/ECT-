Create Schema [Numismatics];
Go

use [Numismatics];
go

-- 1. Country
BEGIN
    CREATE TABLE [Numismatics].Country (
    [Name] VARCHAR(40) CHECK ([Name] NOT LIKE '%[^a-zA-Z ]%'),
    Number_Unique_CC_Coins INT NOT NULL CHECK (Number_Unique_CC_Coins >= 0),
    PRIMARY KEY ([Name])
    );

    PRINT 'Tabela Country criada com sucesso';
END;

-- 2. Type
BEGIN
    CREATE TABLE [Numismatics].[Type] (
        Code_Type INT,
        Designation VARCHAR(70),
        PRIMARY KEY (Code_Type),
        CHECK (Code_Type >= 0 AND Code_Type <= 3)
    );
    PRINT 'Tabela Type criada com sucesso';
END;

-- 3. Events
BEGIN
    CREATE TABLE [Numismatics].[Events] (
        ID INT,
        [Name] VARCHAR(25) NOT NULL,
        [Start_Date] VARCHAR(10) NOT NULL,
        End_Date VARCHAR(10) NOT NULL,
        Localization VARCHAR(40) NOT NULL,
        PRIMARY KEY (ID)
    );
    PRINT 'Tabela Events criada com sucesso';
END;

-- 4. User
BEGIN
    CREATE TABLE [Numismatics].[User] (
        NIF INT,
        Fname VARCHAR(20) NOT NULL,
        Lname VARCHAR(20),
        Email VARCHAR(30) NOT NULL,
        BDate DATE,
        Phone VARCHAR(15),
        [Password] Varbinary(256) NOT NULL,
        PRIMARY KEY (NIF),
        UNIQUE (Email),
        CHECK (LEN(Phone) >= 9 AND LEN(Phone) <= 15),
        CHECK (Email LIKE '%@%.%')
    );
    PRINT 'Tabela User criada com sucesso';
END;

-- 5. Author
BEGIN
    CREATE TABLE [Numismatics].Author (
        NIF INT,
        Fname VARCHAR(30) NOT NULL,
        Lname VARCHAR(30),
        Nacionality VARCHAR(30),
        BDate VARCHAR(10),
        PRIMARY KEY (NIF)
    );
    PRINT 'Tabela Author criada com sucesso';
END;

-- 6. Colection
BEGIN
    CREATE TABLE [Numismatics].Colection (
        ID INT Identity(1,1),
        NIF_User INT,
        Creation_Date DATE NOT NULL,
        Code_Type INT,
        Total_Coins INT NOT NULL DEFAULT 0,
        Total_Comerative_Coins INT NOT NULL DEFAULT 0,
        PRIMARY KEY (ID),
        FOREIGN KEY (NIF_User) REFERENCES [Numismatics].[User](NIF),
        FOREIGN KEY (Code_Type) REFERENCES [Numismatics].[Type](Code_Type)
    );
    PRINT 'Tabela Colection criada com sucesso';
END;

-- 7. Coin
BEGIN
    CREATE TABLE [Numismatics].Coin (
        ID INT Identity(1,1),
        [Value] FLOAT NOT NULL,
        Country_Name VARCHAR(40),
        Mintage INT,
        Condition VARCHAR(3) NOT NULL,
        Material VARCHAR(20) DEFAULT 'CuproNiquel',
        Finishing VARCHAR(30) DEFAULT 'Normal',
        Market_Price FLOAT DEFAULT 0.0,
        ID_Colection INT,
        Qty INT NOT NULL DEFAULT 1,
        PRIMARY KEY (ID, ID_Colection),
        FOREIGN KEY (ID_Colection) REFERENCES [Numismatics].Colection(ID),
        FOREIGN KEY (Country_Name) REFERENCES [Numismatics].Country([Name]),
        CHECK (Mintage >= 0),
        CHECK (Market_Price >= 0),
        CHECK (Condition IN ('VB', 'B', 'G', 'VG', 'F', 'VF', 'AU', 'UNC')),
        CHECK (Finishing IN ('Proof', 'Normal', 'FDC', 'BU', 'BNC')),
        CHECK (Material IN ('Aluminum Bronze', 'Steel', 'CuproNiquel', 'Silver', 'Gold', 'Platinum', 'Bullion', 'Palladium', 'Nickel-brass', 'Bicolor Clad', 'Bimetallic', 'Nickel'))
    );
    PRINT 'Tabela Coin criada com sucesso';
END;


-- 8. Currency

BEGIN
    CREATE TABLE [Numismatics].Currency (
        Currency_Name VARCHAR(40),
        Country_Name VARCHAR(40),
        ISO_Code VARCHAR(3),
        Date_First_Edition INT NOT NULL,
        Date_Last_Edition INT,
        PRIMARY KEY (Currency_Name, Country_Name),
        FOREIGN KEY (Country_Name) REFERENCES [Numismatics].Country([Name]),
        CHECK (Currency_Name NOT LIKE '%[^a-zA-Z ]'),
        CHECK (Date_First_Edition <= 2100),
        CHECK (Date_Last_Edition <= 2100),
        CHECK (LEN(ISO_Code) = 3)
    );
    PRINT 'Tabela Currency criada com sucesso';
END;

-- 9. Common_Face
BEGIN
    CREATE TABLE [Numismatics].Common_Face (
        Coin_ID INT,
        [Description] VARCHAR(150),
        [Version] VARCHAR(10),
        ID_Colection INT,
        PRIMARY KEY ([Description], Coin_ID, ID_Colection),
        FOREIGN KEY (Coin_ID, ID_Colection) REFERENCES [Numismatics].Coin(ID, ID_Colection),
        CHECK ([Version] IN ('A2008', 'B2008', NULL))
    );
    PRINT 'Tabela Common_Face criada com sucesso';
END;

-- 10. Nacional_Face
BEGIN
    CREATE TABLE [Numismatics].Nacional_Face (
        Coin_ID INT,
        [Year] INT NOT NULL,
        [Description] VARCHAR(150),
        Comemorative BIT NOT NULL DEFAULT 0,
        Mint_Mark VARCHAR(20) DEFAULT NULL,
        ID_Colection INT,
        PRIMARY KEY ([Description], Coin_ID, ID_Colection),
        FOREIGN KEY (Coin_ID, ID_Colection) REFERENCES [Numismatics].Coin(ID, ID_Colection),
        CHECK (Comemorative IN (0, 1)),
        CHECK ([Year] <= 2100)
    );
    PRINT 'Tabela Nacional_Face criada com sucesso';
END;

-- 11. CF_Has_Author
BEGIN
    CREATE TABLE [Numismatics].CF_Has_Author (
        Coin_ID INT,
        [Description] VARCHAR(150),
        NIF_Author_CF INT,
        ID_Colection INT,
        PRIMARY KEY (Coin_ID, [Description], ID_Colection, NIF_Author_CF),
        FOREIGN KEY ([Description], Coin_ID, ID_Colection) REFERENCES [Numismatics].Common_Face([Description], Coin_ID, ID_Colection),
        FOREIGN KEY (NIF_Author_CF) REFERENCES [Numismatics].Author(NIF)
    );
    PRINT 'Tabela CF_Has_Author criada com sucesso';
END;

-- 12. NF_Has_Author
BEGIN
    CREATE TABLE [Numismatics].NF_Has_Author (
        Coin_ID INT,
        [Description] VARCHAR(150),
        NIF_Author_NF INT,
        ID_Colection INT,
        PRIMARY KEY (Coin_ID, [Description], NIF_Author_NF, ID_Colection),
        FOREIGN KEY ([Description], Coin_ID, ID_Colection) REFERENCES [Numismatics].Nacional_Face([Description], Coin_ID, ID_Colection),
        FOREIGN KEY (NIF_Author_NF) REFERENCES [Numismatics].Author(NIF)
    );
    PRINT 'Tabela NF_Has_Author criada com sucesso';
END;

-- 13. Participates_Event
BEGIN
    CREATE TABLE [Numismatics].Participates_Event (
        ID_Colection INT,
        ID_Event INT,
        PRIMARY KEY (ID_Colection, ID_Event),
        FOREIGN KEY (ID_Colection) REFERENCES [Numismatics].Colection(ID),
        FOREIGN KEY (ID_Event) REFERENCES [Numismatics].[Events](ID)
    );
    PRINT 'Tabela Participates_Event criada com sucesso';
END;