------------------ Table Valued Functions -------------------

------------------------------------------------------------
-- 1. Autores por pais e evento
------------------------------------------------------------

GO
ALTER FUNCTION [Numismatics].[GetAuthorsByNationalityAndEvent] (
    @EventID INT,
    @Nationality VARCHAR(30)
)
RETURNS TABLE
AS
RETURN
(
    SELECT 
        a.NIF, 
        a.Fname, 
        a.Lname, 
        a.Nacionality, 
        COUNT(DISTINCT CASE WHEN cfa.NIF_Author_CF IS NOT NULL THEN cfa.Coin_ID END) +
        COUNT(DISTINCT CASE WHEN nfa.NIF_Author_NF IS NOT NULL THEN nfa.Coin_ID END) AS NumMoedas
    FROM [Numismatics].Author a
    LEFT JOIN (
        SELECT cfa.NIF_Author_CF, cfa.Coin_ID 
        FROM [Numismatics].CF_Has_Author cfa
        JOIN [Numismatics].Coin coin_cfa 
            ON cfa.Coin_ID = coin_cfa.ID AND cfa.ID_Colection = coin_cfa.ID_Colection
        JOIN [Numismatics].Colection col_cfa 
            ON coin_cfa.ID_Colection = col_cfa.ID
        JOIN [Numismatics].Participates_Event pe 
            ON col_cfa.ID = pe.ID_Colection
        WHERE pe.ID_Event = @EventID
    ) cfa ON a.NIF = cfa.NIF_Author_CF
    LEFT JOIN (
        SELECT nfa.NIF_Author_NF, nfa.Coin_ID 
        FROM [Numismatics].NF_Has_Author nfa
        JOIN [Numismatics].Coin coin_nfa 
            ON nfa.Coin_ID = coin_nfa.ID AND nfa.ID_Colection = coin_nfa.ID_Colection
        JOIN [Numismatics].Colection col_nfa 
            ON coin_nfa.ID_Colection = col_nfa.ID
        JOIN [Numismatics].Participates_Event pe 
            ON col_nfa.ID = pe.ID_Colection
        WHERE pe.ID_Event = @EventID
    ) nfa ON a.NIF = nfa.NIF_Author_NF
    WHERE a.Nacionality = @Nationality
    GROUP BY a.NIF, a.Fname, a.Lname, a.Nacionality
);

GO

------------------------------------------------------------
-- 2. Participação de users em eventos (futuros ou nao)
------------------------------------------------------------

ALTER FUNCTION [dbo].[fn_GetEventParticipation] (
    @ColectionID INT,
    @OnlyFuture BIT
)
RETURNS @EventTable TABLE (
    EventID INT,
    EventName VARCHAR(25),
    Start_Date VARCHAR(10),
    End_Date VARCHAR(10),
    Localization VARCHAR(40)
)
AS
BEGIN
    IF @OnlyFuture = 1
    BEGIN
        INSERT INTO @EventTable
        SELECT 
            E.ID,
            E.Name,
            E.Start_Date,
            E.End_Date,
            E.Localization
        FROM [Numismatics].Events E
        INNER JOIN [Numismatics].Participates_Event PE 
            ON PE.ID_Event = E.ID
        WHERE 
            PE.ID_Colection = @ColectionID
            AND TRY_CAST(E.End_Date AS DATE) >= CAST(GETDATE() AS DATE)
    END
    ELSE
    BEGIN
        INSERT INTO @EventTable
        SELECT 
            E.ID,
            E.Name,
            E.Start_Date,
            E.End_Date,
            E.Localization
        FROM [Numismatics].Events E
        INNER JOIN [Numismatics].Participates_Event PE 
            ON PE.ID_Event = E.ID
        WHERE PE.ID_Colection = @ColectionID
    END

    RETURN
END

GO

------------------------------------------------------------
-- 3. Autores em colecoes
------------------------------------------------------------

ALTER FUNCTION [dbo].[fn_GetAuthorsOfCollection] (@ColectionID INT)
RETURNS TABLE
AS
RETURN
(
    SELECT DISTINCT 
        A.NIF,
        A.Fname,
        A.Lname,
        CF.Coin_ID AS CoinID,
        'Common Face' AS FaceType
    FROM [Numismatics].Author A
    JOIN [Numismatics].CF_Has_Author CF 
        ON A.NIF = CF.NIF_Author_CF
       AND CF.ID_Colection = @ColectionID

    UNION

    SELECT DISTINCT 
        A.NIF,
        A.Fname,
        A.Lname,
        NF.Coin_ID AS CoinID,
        'National Face' AS FaceType
    FROM [Numismatics].Author A
    JOIN [Numismatics].NF_Has_Author NF 
        ON A.NIF = NF.NIF_Author_NF
       AND NF.ID_Colection = @ColectionID
);

GO

------------------------------------------------------------
-- 4. CC coins por pais e evento
------------------------------------------------------------

ALTER FUNCTION [Numismatics].[GetCommemorativeCoinsByCountryAndEvent](
    @EventID INT,
    @Country VARCHAR(40)
)
RETURNS @Results TABLE (
    EventID INT,
    EventName VARCHAR(25),
    CoinID INT,
    Value FLOAT,
    Country_Name VARCHAR(40),
    Description VARCHAR(150),
    Year INT,
    Comemorative BIT,
    Mint_Mark VARCHAR(20)
)
AS
BEGIN
    INSERT INTO @Results
    SELECT 
        e.ID AS EventID,
        e.Name AS EventName,
        c.ID AS CoinID,
        c.Value,
        c.Country_Name,
        nf.Description,
        nf.Year,
        nf.Comemorative,
        nf.Mint_Mark
    FROM [Numismatics].Events e
    JOIN [Numismatics].Participates_Event pe ON e.ID = pe.ID_Event
    JOIN [Numismatics].Colection col ON pe.ID_Colection = col.ID
    JOIN [Numismatics].Coin c ON col.ID = c.ID_Colection
    JOIN [Numismatics].Nacional_Face nf ON c.ID = nf.Coin_ID AND c.ID_Colection = nf.ID_Colection
    WHERE e.ID = @EventID AND c.Country_Name = @Country AND nf.Comemorative = 1
    ORDER BY nf.Year DESC, c.Value ASC;
    
    RETURN;
END;


GO

------------------------------------------------------------
-- 5. Coins por condições
------------------------------------------------------------

ALTER FUNCTION [Numismatics].[GetCoinStatsByCondition](@EventID INT)
RETURNS @Results TABLE (
    Condition VARCHAR(3),
    Quantidade INT,
    ValorMedio FLOAT,
    ValorMinimo FLOAT,
    ValorMaximo FLOAT
)
AS
BEGIN
    INSERT INTO @Results
    SELECT 
        c.Condition,
        COUNT(*) as Quantidade,
        AVG(c.Market_Price) as ValorMedio,
        MIN(c.Market_Price) as ValorMinimo,
        MAX(c.Market_Price) as ValorMaximo
    FROM [Numismatics].Coin c
    JOIN [Numismatics].Colection col ON c.ID_Colection = col.ID
    JOIN [Numismatics].Participates_Event pe ON col.ID = pe.ID_Colection
    JOIN [Numismatics].Nacional_Face nf ON c.ID = nf.Coin_ID AND c.ID_Colection = nf.ID_Colection
    WHERE pe.ID_Event = @EventID
      AND nf.Comemorative = 1
    GROUP BY c.Condition
    ORDER BY 
        CASE c.Condition
            WHEN 'UNC' THEN 1
            WHEN 'AU' THEN 2
            WHEN 'VF' THEN 3
            WHEN 'F' THEN 4
            WHEN 'VG' THEN 5
            WHEN 'G' THEN 6
            WHEN 'B' THEN 7
            WHEN 'VB' THEN 8
            ELSE 9
        END;
    
    RETURN;
END;

GO

------------------------------------------------------------
-- 6. Nacionalidades que faltam
------------------------------------------------------------

ALTER FUNCTION [Numismatics].[GetAvailableNationalities]()
RETURNS @Result TABLE (Nacionality VARCHAR(30))
AS
BEGIN
    INSERT INTO @Result
    SELECT DISTINCT Nacionality 
    FROM [Numismatics].[Author] 
    WHERE Nacionality IS NOT NULL
    ORDER BY Nacionality;
    
    RETURN;
END;

GO

------------------------------------------------------------
-- 7. Filtro de moedas
------------------------------------------------------------

ALTER   FUNCTION [Numismatics].[GetFilteredCoins] (
    @NIF_User NVARCHAR(20),
    @ID_Colecao INT = NULL,
    @Pais NVARCHAR(100) = NULL,
    @Material NVARCHAR(50) = NULL,
    @Condicao NVARCHAR(50) = NULL,
    @Mintage_Min INT = NULL,
    @Mintage_Max INT = NULL,
    @Preco_Min DECIMAL(10, 2) = NULL,
    @Preco_Max DECIMAL(10, 2) = NULL,
    @Acabamento NVARCHAR(50) = NULL,
    @Comemorativa NVARCHAR(3) = NULL
)
RETURNS TABLE
AS
RETURN
(
    SELECT 
        Coin.ID,
        Coin.[Value],
        Coin.Country_Name,
        Coin.Material,
        Coin.[Condition],
        Coin.Mintage,
        Coin.Market_Price,
        Coin.Finishing,
        Colection.ID AS ColectionID,
        CASE 
            WHEN nf.Coin_ID IS NOT NULL THEN 1 
            ELSE 0 
        END AS Comemorative
    FROM [Numismatics].Coin
    JOIN [Numismatics].Colection 
        ON Coin.ID_Colection = Colection.ID
    LEFT JOIN [Numismatics].Nacional_Face nf
        ON nf.Coin_ID = Coin.ID 
        AND nf.ID_Colection = Coin.ID_Colection 
        AND nf.Comemorative = 1
    WHERE 
        Colection.NIF_User = @NIF_User
        AND (@ID_Colecao IS NULL OR Colection.ID = @ID_Colecao)
        AND (@Pais IS NULL OR Coin.Country_Name LIKE '%' + @Pais + '%')
        AND (@Material IS NULL OR @Material = 'Todos' OR Coin.Material = @Material)
        AND (@Condicao IS NULL OR @Condicao = 'Todas' OR Coin.[Condition] = @Condicao)
        AND (@Mintage_Min IS NULL OR Coin.Mintage >= @Mintage_Min)
        AND (@Mintage_Max IS NULL OR Coin.Mintage <= @Mintage_Max)
        AND (@Preco_Min IS NULL OR Coin.Market_Price >= @Preco_Min)
        AND (@Preco_Max IS NULL OR Coin.Market_Price <= @Preco_Max)
        AND (@Acabamento IS NULL OR @Acabamento = 'Todos' OR Coin.Finishing = @Acabamento)
        AND (
            @Comemorativa IS NULL
            OR (@Comemorativa = 'Sim' AND nf.Coin_ID IS NOT NULL)
            OR (@Comemorativa = 'Nao' AND nf.Coin_ID IS NULL)
        )
);

GO

------------------------------------------------------------
-- 8. Participação em eventos
------------------------------------------------------------

ALTER FUNCTION [Numismatics].[GetEventParticipants](
    @EventID INT
)
RETURNS TABLE
AS
RETURN
    SELECT DISTINCT 
        u.NIF, 
        u.Fname, 
        u.Lname
    FROM [Numismatics].[User] u
    JOIN [Numismatics].Colection c ON u.NIF = c.NIF_User
    JOIN [Numismatics].Participates_Event pe ON c.ID = pe.ID_Colection
    WHERE pe.ID_Event = @EventID;


GO

------------------------------------------------------------
-- 9. Paises com CC coins
------------------------------------------------------------

ALTER FUNCTION [Numismatics].[GetCountriesWithCommemorativeCoins]()
RETURNS @Results TABLE (Country_Name VARCHAR(40))
AS
BEGIN
    INSERT INTO @Results
    SELECT DISTINCT c.Country_Name 
    FROM [Numismatics].Coin c
    JOIN [Numismatics].Nacional_Face nf ON c.ID = nf.Coin_ID AND c.ID_Colection = nf.ID_Colection
    WHERE nf.Comemorative = 1
    ORDER BY c.Country_Name;
    
    RETURN;
END;


------------------ Scalar Valued Functions -------------------

------------------------------------------------------------
-- 1. Nome completo do user
------------------------------------------------------------

GO
ALTER FUNCTION [dbo].[fn_GetUserFullName] (@NIF INT)
RETURNS VARCHAR(50)
AS
BEGIN
    DECLARE @FullName VARCHAR(50)

    SELECT @FullName = Fname + ' ' + ISNULL(Lname, '')
    FROM [Numismatics].[User]
    WHERE NIF = @NIF

    RETURN @FullName
END

GO

------------------------------------------------------------
-- 2. valor de uma coleção
------------------------------------------------------------

ALTER FUNCTION [dbo].[fn_GetCollectionValue] (@ColectionID INT)
RETURNS FLOAT
AS
BEGIN
    DECLARE @TotalValue FLOAT

    SELECT @TotalValue = SUM(Market_Price * Qty)
    FROM [Numismatics].Coin
    WHERE ID_Colection = @ColectionID

    RETURN ISNULL(@TotalValue, 0.0)
END

GO

------------------------------------------------------------
-- 3. Idade do Autor
------------------------------------------------------------

ALTER FUNCTION [dbo].[fn_GetAuthorAge] (@AuthorNIF INT)
RETURNS INT
AS
BEGIN
    DECLARE @Age INT
    DECLARE @BDate DATE

    SELECT @BDate = TRY_CAST(BDate AS DATE)
    FROM [Numismatics].Author
    WHERE NIF = @AuthorNIF

    SET @Age = DATEDIFF(YEAR, @BDate, GETDATE())

    RETURN ISNULL(@Age, 0)
END