CREATE OR ALTER TRIGGER tr_AfterInsertCoin
ON [Numismatics].Coin
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;
    
    UPDATE c
    SET Total_Coins = Total_Coins + 1
    FROM Colection c
    JOIN inserted i ON c.ID = i.ID_Colection;
    
    PRINT ('TRIGGER: Moeda geral adicionada');
END;
Go

CREATE OR ALTER TRIGGER tr_AfterInsertNacionalFace
ON [Numismatics].Nacional_Face
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;
    
    UPDATE c
    SET Total_Comerative_Coins = Total_Comerative_Coins + 1
    FROM Colection c
    JOIN inserted i ON c.ID = i.ID_Colection
    WHERE i.Comemorative = 1;
    
    PRINT ('TRIGGER: Moeda comemorativa adicionada');
END;
Go






CREATE OR ALTER TRIGGER tr_UpdateTotalCoins_AfterDelete
ON [Numismatics].Coin
AFTER DELETE
AS
BEGIN
    SET NOCOUNT ON;

    UPDATE c
    SET c.Total_Coins = c.Total_Coins - x.Total_Qty
    FROM Colection c
    JOIN (
        SELECT ID_Colection, SUM(Qty) AS Total_Qty
        FROM deleted
        GROUP BY ID_Colection
    ) x ON c.ID = x.ID_Colection;
END;
Go

CREATE OR ALTER TRIGGER tr_AfterDeleteNacionalFace
ON [Numismatics].Nacional_Face
AFTER DELETE
AS
BEGIN
    SET NOCOUNT ON;
    
    UPDATE c
    SET Total_Comerative_Coins = Total_Comerative_Coins - 1
    FROM Colection c
    JOIN deleted i ON c.ID = i.ID_Colection
    WHERE i.Comemorative = 1;
    
    PRINT ('TRIGGER: Moeda comemorativa adicionada');
END;
Go

CREATE OR ALTER TRIGGER tr_DeleteCoinsWhenColectionDeleted
ON [Numismatics].Colection
AFTER DELETE
AS
BEGIN
    DELETE FROM Coin
    WHERE ID_Colection IN (SELECT ID FROM deleted);
END;
Go