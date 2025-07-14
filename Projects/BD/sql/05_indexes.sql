
------------------ Coin Table Indexes ------------------

CREATE NONCLUSTERED index IX_Coin_ID_Colection 
ON [Numismatics].Coin (ID_Colection);

CREATE NONCLUSTERED INDEX IDX_Coin_Country_Name
ON [Numismatics].Coin (Country_Name);

CREATE NONCLUSTERED INDEX IDX_Coin_Material_Condition
ON [Numismatics].Coin (Material, [Condition]);

CREATE NONCLUSTERED INDEX IDX_Coin_Mintage
ON [Numismatics].Coin (Mintage);

CREATE NONCLUSTERED INDEX IDX_Coin_MarketPrice
ON [Numismatics].Coin (Market_Price);

CREATE NONCLUSTERED INDEX IDX_Coin_Country_Condition
ON [Numismatics].Coin (Country_Name, [Condition]);

CREATE NONCLUSTERED INDEX IDX_Coin_ID_IDColection
ON [Numismatics].Coin (ID, ID_Colection);

CREATE NONCLUSTERED INDEX IDX_Coin_Value_Country
ON [Numismatics].Coin ([Value], Country_Name);

------------------ Colection Table Indexes ------------------

CREATE NONCLUSTERED INDEX IDX_Colection_NIF_User
ON [Numismatics].Colection (NIF_User);

CREATE NONCLUSTERED INDEX IDX_Colection_Creation_Date
ON [Numismatics].Colection (Creation_Date);

CREATE NONCLUSTERED INDEX IDX_Colection_Code_Type
ON [Numismatics].Colection (Code_Type);

------------------ Events Table Indexes ------------------
------------------ User Table Indexes ------------------

CREATE NONCLUSTERED INDEX IDX_User_Fname_Lname
ON [Numismatics].[User] (Fname, Lname);

------------------ Country Table Indexes ------------------
------------------ Nacional_Face Table Indexes ------------------

CREATE NONCLUSTERED INDEX IDX_NacionalFace_Year
ON [Numismatics].Nacional_Face (Year);

CREATE NONCLUSTERED INDEX IDX_NacionalFace_IDColection
ON [Numismatics].Nacional_Face (ID_Colection);

CREATE NONCLUSTERED INDEX IDX_NacionalFace_Comemorative
ON [Numismatics].Nacional_Face (Comemorative);

------------------ Common_Face Table Indexes ------------------
------------------ Author Table Indexes ------------------
------------------ CF_Has_Author Table Indexes ------------------
------------------ NF_Has_Author Table Indexes -----------------
------------------ Participates_Event Table Indexes ------------------
------------------ Currency Table Indexes ------------------

Create NONCLUSTERED INDEX IDX_Currency_Date_First_Edition
ON [Numismatics].Currency (Date_First_Edition);


SET STATISTICS IO OFF;
SET STATISTICS TIME OFF;
GO