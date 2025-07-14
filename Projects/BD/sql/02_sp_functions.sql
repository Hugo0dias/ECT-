------------------------------------------------------------
-- 1. Autores que contribu�ram para CF e NF
------------------------------------------------------------
ALTER PROCEDURE [dbo].[AutoresAmbasFaces]
AS
BEGIN
    SELECT DISTINCT 
        A.NIF,
        A.Fname,
        A.Lname
    FROM [Numismatics].Author A
    WHERE EXISTS (
        SELECT 1 FROM [Numismatics].CF_Has_Author CF WHERE CF.NIF_Author_CF = A.NIF
    )
    AND EXISTS (
        SELECT 1 FROM [Numismatics].NF_Has_Author NF WHERE NF.NIF_Author_NF = A.NIF
    );
END;

GO

------------------------------------------------------------
-- 2. Adicionar Coin
------------------------------------------------------------

ALTER PROCEDURE [dbo].[AdicionarMoeda]
    @Value FLOAT,
    @Country_Name VARCHAR(40),
    @Mintage INT = NULL,
    @Condition VARCHAR(3),
    @Material VARCHAR(20) = 'CuproNiquel',
    @Finishing VARCHAR(30) = 'Normal',
    @Market_Price FLOAT = 0.0,
    @ID_Colection INT,
    @Year INT = NULL,
    @NF_Description VARCHAR(255) = NULL,
    @Comemorative BIT = 0,
    @Mint_Mark VARCHAR(50) = NULL,
    @CF_Description VARCHAR(255) = NULL,
    @CF_Version VARCHAR(50) = NULL,
    @Resultado BIT OUTPUT,
    @Mensagem VARCHAR(255) OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    
    BEGIN TRY

        Begin TRANSACTION;

        -- Validar condição
        IF @Condition NOT IN ('VB', 'B', 'G', 'VG', 'F', 'VF', 'AU', 'UNC')
        BEGIN
            SET @Resultado = 0;
            SET @Mensagem = 'Condição inválida. Valores permitidos: VB, B, G, VG, F, VF, AU, UNC';
            RETURN;
        END;

        -- Validar acabamento
        IF @Finishing NOT IN ('Proof', 'Normal', 'FDC', 'BU', 'BNC')
        BEGIN
            SET @Resultado = 0;
            SET @Mensagem = 'Acabamento inválido. Valores permitidos: Proof, Normal, FDC, BU, BNC';
            RETURN;
        END;

        -- Validar material
        IF @Material NOT IN ('Aluminum Bronze', 'Steel', 'CuproNiquel', 'Silver', 'Gold', 
                           'Platinum', 'Bullion', 'Palladium', 'Nickel-brass', 
                           'Bicolor Clad', 'Bimetallic', 'Nickel')
        BEGIN
            SET @Resultado = 0;
            SET @Mensagem = 'Material inválido';
            RETURN;
        END;

        -- Verificar e inserir país se não existir (com valor padrão para Number_Unique_CC_Coins)
        IF NOT EXISTS (SELECT 1 FROM [Numismatics].Country WHERE [Name] = @Country_Name)
        BEGIN
            INSERT INTO [Numismatics].Country ([Name], Number_Unique_CC_Coins) 
            VALUES (@Country_Name, 0);
            
            PRINT 'País ' + @Country_Name + ' criado com sucesso';
        END;

        -- Verificar se coleção existe
        IF NOT EXISTS (SELECT 1 FROM [Numismatics].Colection WHERE ID = @ID_Colection)
        BEGIN
            SET @Resultado = 0;
            SET @Mensagem = 'Coleção não existe';
            RETURN;
        END;

        -- Verificar se moeda já existe com os mesmos dados
		DECLARE @ExistingCoinID INT;

		SELECT TOP 1 @ExistingCoinID = c.ID
		FROM [Numismatics].Coin c
		LEFT JOIN [Numismatics].Common_Face cf ON c.ID = cf.Coin_ID AND c.ID_Colection = cf.ID_Colection
		LEFT JOIN [Numismatics].Nacional_Face nf ON c.ID = nf.Coin_ID AND c.ID_Colection = nf.ID_Colection
		WHERE 
			c.[Value] = @Value AND
			c.Country_Name = @Country_Name AND
			ISNULL(c.Mintage, -1) = ISNULL(@Mintage, -1) AND
			c.[Condition] = @Condition AND
			c.Material = @Material AND
			c.Finishing = @Finishing AND
			c.Market_Price = @Market_Price AND
			c.ID_Colection = @ID_Colection AND
			ISNULL(cf.[Description], '') = ISNULL(@CF_Description, '') AND
			ISNULL(cf.[Version], '') = ISNULL(@CF_Version, '') AND
			ISNULL(nf.[Year], -1) = ISNULL(@Year, -1) AND
			ISNULL(nf.[Description], '') = ISNULL(@NF_Description, '') AND
			ISNULL(nf.Comemorative, -1) = ISNULL(@Comemorative, -1) AND
			ISNULL(nf.Mint_Mark, '') = ISNULL(@Mint_Mark, '');

		IF @ExistingCoinID IS NOT NULL
		BEGIN
			-- Já existe, incrementar Qty
			UPDATE [Numismatics].Coin
			SET Qty = Qty + 1
			WHERE ID = @ExistingCoinID AND ID_Colection = @ID_Colection;

		    COMMIT TRANSACTION;
			SET @Resultado = 1;
			SET @Mensagem = 'Moeda já existia. Quantidade incrementada com sucesso.';
			RETURN;
		END;

		-- Se não existir, inserir nova moeda
		INSERT INTO [Numismatics].Coin (
		    [Value], Country_Name, Mintage, [Condition], 
			Material, Finishing, Market_Price, ID_Colection
		) VALUES (
			@Value, @Country_Name, @Mintage, @Condition, 
			@Material, @Finishing, @Market_Price, @ID_Colection
		);

		DECLARE @NewCoinID INT = SCOPE_IDENTITY();


        -- Incrementar contador de moedas únicas do país
        --UPDATE Country 
        --SET Number_Unique_CC_Coins = Number_Unique_CC_Coins + 1 
        --WHERE [Name] = @Country_Name;

        -- Inserir face nacional se dados foram fornecidos
        IF @Year IS NOT NULL AND @NF_Description IS NOT NULL
        BEGIN
            INSERT INTO [Numismatics].Nacional_Face (
                Coin_ID, [Year], [Description], Comemorative, Mint_Mark, ID_Colection
            ) VALUES (
                @NewCoinID, @Year, @NF_Description, @Comemorative, @Mint_Mark, @ID_Colection
            );
        END;

        -- Inserir face comum se dados foram fornecidos
        IF @CF_Description IS NOT NULL AND @CF_Version IS NOT NULL
        BEGIN
            -- Validar versão da face comum
            IF @CF_Version IS NOT NULL AND @CF_Version NOT IN ('A2008', 'B2008')
            BEGIN
                SET @Resultado = 0;
                SET @Mensagem = 'Versão da face comum inválida. Valores permitidos: A2008, B2008 ou NULL';
                RETURN;
            END;
            
            INSERT INTO [Numismatics].Common_Face (
                Coin_ID, [Description], [Version], ID_Colection
            ) VALUES (
                @NewCoinID, @CF_Description, @CF_Version, @ID_Colection
            );
        END;

        Commit TRANSACTION;

        SET @Resultado = 1;
        SET @Mensagem = 'Moeda adicionada com sucesso';
    END TRY
    BEGIN CATCH

        IF @@TRANCOUNT > 0
            ROLLBACK TRANSACTION;

        SET @Resultado = 0;
        SET @Mensagem = 'Erro ao adicionar moeda: ' + ERROR_MESSAGE();
        
        -- Log detalhado do erro
        PRINT 'ERRO: ' + ERROR_MESSAGE();
        PRINT 'Linha: ' + CAST(ERROR_LINE() AS VARCHAR);
        PRINT 'Procedimento: ' + ERROR_PROCEDURE();
    END CATCH;

    -- Retorna os valores de output
    SELECT @Resultado AS Resultado, @Mensagem AS Mensagem;
END;
GO

------------------------------------------------------------
-- 3. Verificar a participa��o de users em eventos
------------------------------------------------------------

ALTER PROCEDURE [dbo].[CheckUserParticipation]
    @UserNIF INT,
    @EventID INT
AS
BEGIN
    SELECT 1
    FROM [Numismatics].Participates_Event pe
    JOIN [Numismatics].Colection c ON pe.ID_Colection = c.ID
    WHERE c.NIF_User = @UserNIF AND pe.ID_Event = @EventID;
END;

GO

------------------------------------------------------------
-- 4. Listar cole��es mais antigas
------------------------------------------------------------
ALTER PROCEDURE [dbo].[ColecoesMaisAntigas]
AS
BEGIN
    SELECT 
        ID,
        Creation_Date
    FROM [Numismatics].Colection
    ORDER BY Creation_Date ASC;
END;

GO

------------------------------------------------------------
-- 5. Cole��es que participaram em mais de um evento
------------------------------------------------------------
ALTER PROCEDURE [dbo].[ColecoesMultiplosEventos]
AS
BEGIN
    SELECT 
        ID_Colection,
        COUNT(*) AS Num_Events
    FROM [Numismatics].Participates_Event
    GROUP BY ID_Colection
    HAVING COUNT(*) > 1;
END;

GO

------------------------------------------------------------
-- 6. Eliminar uma moeda
------------------------------------------------------------

ALTER PROCEDURE [dbo].[DeleteCollection]
    @CollectionID INT,
    @Result BIT OUTPUT,
    @Message NVARCHAR(255) OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    SET @Result = 0;
    SET @Message = '';

    BEGIN TRY
        -- Check if collection exists
        IF NOT EXISTS (SELECT 1 FROM [Numismatics].Colection WHERE ID = @CollectionID)
        BEGIN
            SET @Message = 'Collection does not exist';
            RETURN;
        END

        BEGIN TRANSACTION;
    
        -- Delete Common Face Authors
        DELETE CFA FROM [Numismatics].CF_Has_Author CFA
        INNER JOIN [Numismatics].Common_Face CF ON CFA.Coin_ID = CF.Coin_ID AND CFA.ID_Colection = CF.ID_Colection
        INNER JOIN [Numismatics].Coin C ON CF.Coin_ID = C.ID AND CF.ID_Colection = C.ID_Colection
        WHERE C.ID_Colection = @CollectionID;
    
        -- Delete National Face Authors
        DELETE NFA FROM [Numismatics].NF_Has_Author NFA
        INNER JOIN [Numismatics].Nacional_Face NF ON NFA.Coin_ID = NF.Coin_ID AND NFA.ID_Colection = NF.ID_Colection
        INNER JOIN [Numismatics].Coin C ON NF.Coin_ID = C.ID AND NF.ID_Colection = C.ID_Colection
        WHERE C.ID_Colection = @CollectionID;
    
        -- Delete Common Faces
        DELETE FROM [Numismatics].Common_Face 
        WHERE ID_Colection = @CollectionID;
    
        -- Delete National Faces
        DELETE FROM [Numismatics].Nacional_Face 
        WHERE ID_Colection = @CollectionID;
    
        -- Delete Coins
        DELETE FROM [Numismatics].Coin 
        WHERE ID_Colection = @CollectionID;
    
        -- Delete from Participates_Event
        DELETE FROM [Numismatics].Participates_Event 
        WHERE ID_Colection = @CollectionID;
    
        -- Finally delete the collection
        DELETE FROM [Numismatics].Colection 
        WHERE ID = @CollectionID;
    
        SET @Result = 1;
        SET @Message = 'Collection deleted successfully';
    
        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        IF @@TRANCOUNT > 0
            ROLLBACK TRANSACTION;
            
        SET @Result = 0;
        SET @Message = ERROR_MESSAGE();
    END CATCH;
END;

GO

------------------------------------------------------------
-- 7. Contagem de moedas por pa�s
------------------------------------------------------------
ALTER PROCEDURE [dbo].[ContagemMoedasPorPais]
AS
BEGIN
    SELECT 
        Country_Name,
        COUNT(*) AS Total_Coins
    FROM [Numismatics].Coin
    GROUP BY Country_Name
    ORDER BY Total_Coins DESC;
END;

GO

------------------------------------------------------------
-- 8. Contagem de moedas por condi��o
------------------------------------------------------------
ALTER PROCEDURE [dbo].[ContagemMoedasPorCondicao]
AS
BEGIN
    SELECT 
        [Condition],
        COUNT(*) AS Total
    FROM [Numismatics].Coin
    GROUP BY [Condition];
END;

GO
------------------------------------------------------------
-- 9. Calcula valor de todas as cole��es e ordena por pre�o
------------------------------------------------------------

ALTER PROCEDURE [dbo].[GetCollectionsByValue]
    @NIF_User VARCHAR(20)
AS
BEGIN
    SELECT c.ID AS Colecao_ID, t.Designation, c.Creation_Date, 
           SUM(co.Market_Price) AS Valor_Total
    FROM [Numismatics].Colection c
    JOIN [Numismatics].[Type] t ON c.Code_Type = t.Code_Type
    JOIN [Numismatics].Coin co ON co.ID_Colection = c.ID
    WHERE c.NIF_User = @NIF_User
    GROUP BY c.ID, t.Designation, c.Creation_Date
    ORDER BY Valor_Total DESC
END

GO

------------------------------------------------------------
-- 10. Moedas por condi��o em eventos
------------------------------------------------------------

ALTER PROCEDURE [dbo].[GetCoinStatsByConditionInEvents]
    @EventID INT
AS
BEGIN
    SELECT 
        c.Condition,
        COUNT(*) as Quantidade,
        AVG(c.Market_Price) as ValorMedio,
        MIN(c.Market_Price) as ValorMinimo,
        MAX(c.Market_Price) as ValorMaximo
    FROM [Numismatics].Coin c
    JOIN [Numismatics].Colection col ON c.ID_Colection = col.ID
    JOIN [Numismatics].Participates_Event pe ON col.ID = pe.ID_Colection
    WHERE pe.ID_Event = @EventID
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
END;

GO

------------------------------------------------------------
-- 11. Editar Moeda
------------------------------------------------------------

ALTER PROCEDURE [dbo].[Edit_Coin]
    @ID INT,
    @New_Value FLOAT,
    @New_Mintage INT,
    @New_Condition VARCHAR(3),
    @New_Material VARCHAR(20),
    @New_Finishing VARCHAR(30),
    @New_Market_Price FLOAT,
    @New_Country_Name VARCHAR(40),
    @Resultado BIT OUTPUT,
    @Mensagem VARCHAR(255) OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRY
        BEGIN TRANSACTION;

        -- Verifica se a moeda existe
        IF NOT EXISTS (SELECT 1 FROM [Numismatics].Coin WHERE ID = @ID)
        BEGIN 
            SET @Resultado = 0;
            SET @Mensagem = 'Moeda com o ID fornecido n�o existe.';
            ROLLBACK;
            RETURN;
        END

        -- Verifica se o pa�s existe
        IF NOT EXISTS (SELECT 1 FROM [Numismatics].Country WHERE [Name] = @New_Country_Name)
        BEGIN
            SET @Resultado = 0;
            SET @Mensagem = 'Pa�s fornecido n�o existe.';
            ROLLBACK;
            RETURN;
        END

        -- Valida a condi��o
        IF @New_Condition NOT IN ('VB', 'B', 'G', 'VG', 'F', 'VF', 'AU', 'UNC')
        BEGIN
            SET @Resultado = 0;
            SET @Mensagem = 'Condi��o inv�lida.';
            ROLLBACK;
            RETURN;
        END

        -- Valida o acabamento
        IF @New_Finishing NOT IN ('Proof', 'Normal', 'FDC', 'BU', 'BNC')
        BEGIN
            SET @Resultado = 0;
            SET @Mensagem = 'Acabamento inv�lido.';
            ROLLBACK;
            RETURN;
        END

        -- Valida o material
        IF @New_Material NOT IN ('Aluminum Bronze', 'Steel', 'CuproNiquel', 'Silver', 'Gold', 'Platinum',
                                 'Bullion', 'Palladium', 'Nickel-brass', 'Bicolor Clad', 'Bimetallic', 'Nickel')
        BEGIN
            SET @Resultado = 0;
            SET @Mensagem = 'Material inv�lido.';
            ROLLBACK;
            RETURN;
        END

        -- Atualiza a moeda
        UPDATE [Numismatics].Coin
        SET 
            [Value] = @New_Value,
            Mintage = @New_Mintage,
            [Condition] = @New_Condition,
            Material = @New_Material,
            Finishing = @New_Finishing,
            Market_Price = @New_Market_Price,
            Country_Name = @New_Country_Name
        WHERE ID = @ID;

        COMMIT;
        SET @Resultado = 1;
        SET @Mensagem = 'Moeda atualizada com sucesso.';
    END TRY
    BEGIN CATCH
        IF @@TRANCOUNT > 0
            ROLLBACK;

        SET @Resultado = 0;
        SET @Mensagem = ERROR_MESSAGE();
    END CATCH
END;

GO

------------------------------------------------------------
-- 12. Paises que faltam na cole��o
------------------------------------------------------------

ALTER PROCEDURE [dbo].[GetMissingCountriesByColection]
    @ColectionID INT
AS
BEGIN
    SET NOCOUNT ON;

    -- Parte 1: Estat�sticas de pa�ses
    SELECT 
        (SELECT COUNT(*) FROM [Numismatics].Country) AS TotalCountries,
        COUNT(DISTINCT c.Country_Name) AS TotalCountriesInColection
    FROM [Numismatics].Coin c
    WHERE c.ID_Colection = @ColectionID;

    -- Parte 2: Pa�ses em falta
    SELECT 
        co.[Name] AS MissingCountry
    FROM [Numismatics].Country co
    WHERE co.[Name] NOT IN (
        SELECT DISTINCT c.Country_Name
        FROM [Numismatics].Coin c
        WHERE c.ID_Colection = @ColectionID
    );
END;

GO

------------------------------------------------------------
-- 13. Material no catalogo de moedas do user
------------------------------------------------------------

ALTER PROCEDURE [dbo].[GetMaterialsUsed]
    @NIF_User VARCHAR(20)
AS
BEGIN
    SELECT DISTINCT Material
    FROM [Numismatics].Coin c
    JOIN [Numismatics].Colection col ON c.ID_Colection = col.ID
    WHERE col.NIF_User = @NIF_User
    ORDER BY Material
END

GO

------------------------------------------------------------
-- 14. Detalhes do Evento
------------------------------------------------------------

ALTER PROCEDURE [dbo].[GetEventDetails]
    @EventID INT
AS
BEGIN
    SELECT * FROM [Numismatics].Events WHERE ID = @EventID;
END;

GO

------------------------------------------------------------
-- 15. Currencies que estao no user
------------------------------------------------------------

ALTER PROCEDURE [dbo].[GetUserCurrencies]
    @NIF_User VARCHAR(20)
AS
BEGIN
    SELECT DISTINCT curr.Currency_Name, curr.Country_Name, curr.ISO_Code,
                    curr.Date_First_Edition, curr.Date_Last_Edition
    FROM [Numismatics].[User] u
    JOIN [Numismatics].Colection col ON u.NIF = col.NIF_User
    JOIN [Numismatics].Coin c ON col.ID = c.ID_Colection
    JOIN [Numismatics].Nacional_Face nf ON c.ID = nf.Coin_ID AND c.ID_Colection = nf.ID_Colection
    LEFT JOIN [Numismatics].Currency curr ON c.Country_Name = curr.Country_Name
                            AND nf.[Year] >= curr.Date_First_Edition
                            AND (curr.Date_Last_Edition IS NULL OR nf.[Year] <= curr.Date_Last_Edition)
    WHERE u.NIF = @NIF_User
    ORDER BY curr.Country_Name
END

GO

------------------------------------------------------------
-- 16. Cole��es de um user
------------------------------------------------------------

ALTER PROCEDURE [dbo].[GetUserCollections]
    @NIF_User VARCHAR(20)
AS
BEGIN
    SELECT 
        c.ID,
        t.Designation,
        c.Creation_Date,
        dbo.fn_GetCollectionValue(c.ID) AS TotalValue
    FROM [Numismatics].Colection c
    JOIN [Numismatics].[Type] t ON c.Code_Type = t.Code_Type
    WHERE c.NIF_User = @NIF_User
    ORDER BY c.Creation_Date DESC
END

GO

------------------------------------------------------------
-- 17. PROCEDURE PARA OBTER UTILIZADOR POR NIF
------------------------------------------------------------
ALTER PROCEDURE [dbo].[GetUserByNIF]
    @NIF INT
AS
BEGIN
    SET NOCOUNT ON;

    SELECT NIF
    FROM [Numismatics].[User]
    WHERE NIF = @NIF;
END;

GO
------------------------------------------------------------
-- 18. Lista eventos futuros
------------------------------------------------------------

ALTER PROCEDURE [dbo].[ListarEventosFuturos]
AS
BEGIN
    SELECT 
        ID,
        Name,
        Start_Date,
        End_Date,
		Localization
    FROM [Numismatics].Events
    WHERE CAST(End_Date AS DATE) > GETDATE();
END;

GO

------------------------------------------------------------
-- 19. Listar cole��es por utilizador
------------------------------------------------------------
ALTER PROCEDURE [dbo].[ListarColecoesPorUser]
    @NIF_User INT
AS
BEGIN
    SELECT 
        ID,
        Creation_Date,
        Code_Type
    FROM [Numismatics].Colection
    WHERE NIF_User = @NIF_User;
END;

GO

------------------------------------------------------------
-- 20. Status dos users
------------------------------------------------------------

ALTER PROCEDURE [dbo].[GetUserStats]
    @NIF_User VARCHAR(20)
AS
BEGIN
    SELECT 
        (SELECT COUNT(*) 
         FROM [Numismatics].Coin c
         JOIN [Numismatics].Colection col ON c.ID_Colection = col.ID
         WHERE col.NIF_User = @NIF_User) AS TotalCoins,

        (SELECT COUNT(*) 
         FROM [Numismatics].Coin c
         JOIN [Numismatics].Colection col ON c.ID_Colection = col.ID
         JOIN [Numismatics].Nacional_Face nf ON c.ID = nf.Coin_ID AND c.ID_Colection = nf.ID_Colection
         WHERE col.NIF_User = @NIF_User AND nf.Comemorative = 1) AS TotalComemorativas
END

GO

------------------------------------------------------------
-- 21. Listar moedas por material
------------------------------------------------------------
ALTER PROCEDURE [dbo].[ListarMoedasPorMaterial]
    @Material NVARCHAR(50)
AS
BEGIN
    SELECT 
        ID,
        Material,
        Country_Name
    FROM [Numismatics].Coin
    WHERE Material = @Material;
END;

GO

------------------------------------------------------------
-- 22. Listar moedas por condi��o
------------------------------------------------------------
ALTER PROCEDURE [dbo].[ListarMoedasPorCondicao]
    @Condicao NVARCHAR(50)
AS
BEGIN
    SELECT 
        ID,
        Country_Name,
        [Condition]
    FROM [Numismatics].Coin
    WHERE [Condition] = @Condicao;
END;

GO
------------------------------------------------------------
-- 23. Lista de moedas por ano
------------------------------------------------------------

ALTER PROCEDURE [dbo].[ListarMoedasPorAno]
    @Ano INT
AS
BEGIN
    SELECT 
        Coin.ID,
        Coin.Country_Name,
        Nacional_Face.Year
    FROM [Numismatics].Coin
    JOIN [Numismatics].Nacional_Face ON Coin.ID = Nacional_Face.Coin_ID
    WHERE Nacional_Face.Year = @Ano;
END;

GO

------------------------------------------------------------
-- 24. Lista de moedas por user
------------------------------------------------------------

ALTER PROCEDURE [dbo].[ListarMoedasPorUser]
    @NIF_User INT
AS
BEGIN
    SELECT 
        Coin.ID,
        Coin.ID_Colection,
        Coin.Country_Name,
        Coin.Material,
        Coin.[Condition]
    FROM [Numismatics].Coin
    JOIN [Numismatics].Colection ON Coin.ID_Colection = Colection.ID
    WHERE Colection.NIF_User = @NIF_User;
END;

GO

------------------------------------------------------------
-- 25. Listar moedas por pa�s e cole��o
------------------------------------------------------------

ALTER PROCEDURE [dbo].[ListarMoedasPorPaisEColecao]
    @ID_Colection INT,
    @Country NVARCHAR(100)
AS
BEGIN
    SELECT 
        ID,
        Country_Name,
        Material,
        [Condition]
    FROM [Numismatics].Coin
    WHERE ID_Colection = @ID_Colection AND Country_Name = @Country;
END;


GO

------------------------------------------------------------
-- 26. Listar moedas por material e condi��o
------------------------------------------------------------
ALTER PROCEDURE [dbo].[ListarMoedasPorMaterialECondicao]
    @Material NVARCHAR(50),
    @Condicao NVARCHAR(50)
AS
BEGIN
    SELECT 
        ID,
        Material,
        [Condition]
    FROM [Numismatics].Coin
    WHERE Material = @Material AND [Condition] = @Condicao;
END;

GO
------------------------------------------------------------
-- 27. Media de valor da colecao por tipo
------------------------------------------------------------

ALTER PROCEDURE [dbo].[MediaValorPorTipo]
AS
BEGIN
    SELECT 
        [Type].Designation,
        AVG(Coin.Market_Price) AS Avg_Market_Price
    FROM [Numismatics].Coin
    JOIN [Numismatics].Colection ON Coin.ID_Colection = Colection.ID
    JOIN [Numismatics].[Type] ON Colection.Code_Type = [Type].Code_Type
    GROUP BY [Type].Designation;
END;

GO

------------------------------------------------------------
-- 28. Lista de users por eventos
------------------------------------------------------------

ALTER PROCEDURE [dbo].[ListarUsersEventos]
AS
BEGIN
    SELECT DISTINCT 
        [User].NIF,
        [User].Fname,
        [User].Lname,
        [User].Email
    FROM [Numismatics].[User]
    JOIN [Numismatics].Colection ON [User].NIF = Colection.NIF_User
    JOIN [Numismatics].Participates_Event ON Colection.ID = Participates_Event.ID_Colection;
END;

GO

------------------------------------------------------------
-- 29. Listar moedas raras (threshold)
------------------------------------------------------------
ALTER PROCEDURE [dbo].[ListarMoedasRaras]
    @Threshold INT
AS
BEGIN
    SELECT 
        ID,
        Country_Name,
        Mintage
    FROM [Numismatics].Coin
    WHERE Mintage < @Threshold;
END;

GO

------------------------------------------------------------
-- 30. Lista de moedas Comemorativas por pais e cole��o
------------------------------------------------------------

ALTER PROCEDURE [dbo].[MoedasComemorativasPorPaisEColecao]
    @Country NVARCHAR(100),
    @ID_Colection INT
AS
BEGIN
    SELECT 
        Coin.ID,
        Coin.Country_Name,
        Nacional_Face.Comemorative
    FROM [Numismatics].Coin
    JOIN [Numismatics].Nacional_Face ON Coin.ID = Nacional_Face.Coin_ID
    WHERE Coin.Country_Name = @Country AND Coin.ID_Colection = @ID_Colection AND Nacional_Face.Comemorative = 1;
END;

GO

------------------------------------------------------------
-- 31. Lista de moedas cunhadas antes de 2008
------------------------------------------------------------

ALTER PROCEDURE [dbo].[MoedasAntes2008]
AS
BEGIN
    SELECT 
        Coin.ID,
        Coin.Country_Name,
        Nacional_Face.Year
    FROM [Numismatics].Coin
    JOIN [Numismatics].Nacional_Face ON Coin.ID = Nacional_Face.Coin_ID
    WHERE Nacional_Face.Year < 2008;
END;

GO

------------------------------------------------------------
-- 32. Moedas acima da m�dia global de valor
------------------------------------------------------------
ALTER PROCEDURE [dbo].[MoedasAcimaMediaGlobal]
AS
BEGIN
    SELECT 
        Coin.ID,
        Coin.Country_Name,
        Coin.Market_Price
    FROM [Numismatics].Coin
    WHERE Coin.Market_Price > (
        SELECT AVG(Market_Price) FROM [Numismatics].Coin
    );
END;


GO

------------------------------------------------------------
-- 33. Lista de moedas comemorativas por pais
------------------------------------------------------------

ALTER PROCEDURE [dbo].[PaisesComemorativasMultiplas]
AS
BEGIN
    SELECT 
        Coin.Country_Name,
        COUNT(*) AS Total_Commemorative
    FROM [Numismatics].Coin
    JOIN [Numismatics].Nacional_Face ON Coin.ID = Nacional_Face.Coin_ID
    WHERE Nacional_Face.Comemorative = 1
    GROUP BY Coin.Country_Name
    HAVING COUNT(*) > 1;
END;

GO

------------------------------------------------------------
-- 34. Lista de moedas cunhadas depois de 2008
------------------------------------------------------------

ALTER PROCEDURE [dbo].[MoedasDepois2008]
AS
BEGIN
    SELECT 
        Coin.ID,
        Coin.Country_Name,
        Nacional_Face.Year
    FROM [Numismatics].Coin
    JOIN [Numismatics].Nacional_Face ON Coin.ID = Nacional_Face.Coin_ID
    WHERE Nacional_Face.Year >= 2008;
END;

GO

------------------------------------------------------------
-- 35. Decripta��o da pass do user
------------------------------------------------------------

ALTER PROCEDURE [dbo].[sp_DecryptPasswordByNIF]
    @NIF INT
AS
BEGIN
    SET NOCOUNT ON;

    SELECT 
        CONVERT(VARCHAR(50), DecryptByPassPhrase('Numismatics', Password)) AS DecryptedPassword
    FROM [Numismatics].[User]
    WHERE NIF = @NIF;
END

GO

------------------------------------------------------------
-- 36. PROCEDURE PARA REMOVER coin
------------------------------------------------------------

ALTER PROCEDURE [dbo].[RemoveCoin]
    @CoinID INT,
    @CollectionID INT
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRY
        BEGIN TRANSACTION;

        IF NOT EXISTS (
            SELECT 1 FROM [Numismatics].Coin
            WHERE ID = @CoinID AND ID_Colection = @CollectionID
        )
        BEGIN
            RAISERROR('Coin n�o encontrada.', 16, 1);
            ROLLBACK TRANSACTION;
            RETURN;
        END;

        -- Apagar autores das faces
        DELETE FROM [Numismatics].CF_Has_Author WHERE Coin_ID = @CoinID AND ID_Colection = @CollectionID;
        DELETE FROM [Numismatics].NF_Has_Author WHERE Coin_ID = @CoinID AND ID_Colection = @CollectionID;

        -- Apagar faces
        DELETE FROM [Numismatics].Common_Face WHERE Coin_ID = @CoinID AND ID_Colection = @CollectionID;
        DELETE FROM [Numismatics].Nacional_Face WHERE Coin_ID = @CoinID AND ID_Colection = @CollectionID;

        -- Apagar a moeda
        DELETE FROM [Numismatics].Coin WHERE ID = @CoinID AND ID_Colection = @CollectionID;

        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        IF @@TRANCOUNT > 0
            ROLLBACK TRANSACTION;

        -- Relatar erro para o client
        DECLARE @ErrMsg NVARCHAR(4000) = ERROR_MESSAGE();
        RAISERROR(@ErrMsg, 16, 1);
    END CATCH
END;

GO

------------------------------------------------------------
-- 37. Autores com mais common faces
------------------------------------------------------------

ALTER PROCEDURE [dbo].[TopAutoresCF]
AS
BEGIN
    SELECT 
        Author.NIF,
        Author.Fname,
        Author.Lname,
        COUNT(*) AS Total_Designs
    FROM [Numismatics].CF_Has_Author
    JOIN [Numismatics].Author ON CF_Has_Author.NIF_Author_CF = Author.NIF
    GROUP BY Author.NIF, Author.Fname, Author.Lname
    ORDER BY Total_Designs DESC;
END;

GO

------------------------------------------------------------
-- 38. Top 5 cole��es com mais moedas
------------------------------------------------------------
ALTER PROCEDURE [dbo].[Top5ColecoesMaisMoedas]
AS
BEGIN
    SELECT TOP 5 
        ID_Colection,
        COUNT(*) AS Total_Coins
    FROM [Numismatics].Coin
    GROUP BY ID_Colection
    ORDER BY Total_Coins DESC;
END;


GO
------------------------------------------------------------
-- 39. Currencies por user
------------------------------------------------------------

ALTER PROCEDURE [dbo].[sp_GetUserCurrencies]
    @NIF_User VARCHAR(20)
AS
BEGIN
    SELECT DISTINCT curr.Currency_Name, curr.Country_Name, curr.ISO_Code,
                    curr.Date_First_Edition, curr.Date_Last_Edition
    FROM [Numismatics].[User] u
    JOIN [Numismatics].Colection col ON u.NIF = col.NIF_User
    JOIN [Numismatics].Coin c ON col.ID = c.ID_Colection
    JOIN [Numismatics].Nacional_Face nf ON c.ID = nf.Coin_ID AND c.ID_Colection = nf.ID_Colection
    LEFT JOIN [Numismatics].Currency curr ON c.Country_Name = curr.Country_Name
                            AND nf.[Year] >= curr.Date_First_Edition
                            AND (curr.Date_Last_Edition IS NULL OR nf.[Year] <= curr.Date_Last_Edition)
    WHERE u.NIF = @NIF_User
    ORDER BY curr.Country_Name
END

GO

------------------------------------------------------------
-- 40. Moedas comemorativas por user
------------------------------------------------------------

ALTER PROCEDURE [dbo].[TotalMoedasComemorativasPorUser]
AS
BEGIN
    SELECT 
        [User].NIF,
        [User].Fname,
        [User].Lname,
        COUNT(*) AS Total_Comemorativas
    FROM [Numismatics].[User]
    JOIN [Numismatics].Colection ON [User].NIF = Colection.NIF_User
    JOIN [Numismatics].Coin ON Coin.ID_Colection = Colection.ID
    JOIN [Numismatics].Nacional_Face ON Coin.ID = Nacional_Face.Coin_ID
    WHERE Nacional_Face.Comemorative = 1
    GROUP BY [User].NIF, [User].Fname, [User].Lname;
END;

GO

------------------------------------------------------------
-- 41. Autores com mais nacional faces
------------------------------------------------------------

ALTER PROCEDURE [dbo].[TopAutoresNF]
AS
BEGIN
    SELECT 
        Author.NIF,
        Author.Fname,
        Author.Lname,
        COUNT(*) AS Total_Designs
    FROM [Numismatics].NF_Has_Author
    JOIN [Numismatics].Author ON NF_Has_Author.NIF_Author_NF = Author.NIF
    GROUP BY Author.NIF, Author.Fname, Author.Lname
    ORDER BY Total_Designs DESC;
END;

GO

------------------------------------------------------------
-- 42. Regista User
------------------------------------------------------------

ALTER PROCEDURE [Numismatics].[sp_RegisterUser]
    @NIF INT,
    @Fname VARCHAR(20),
    @Lname VARCHAR(20),
    @Email VARCHAR(30),
    @BDate DATE,
    @Phone VARCHAR(15),
    @PlainPassword VARCHAR(100)
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRY
        BEGIN TRANSACTION;

		DECLARE @Passphrase NVARCHAR(128) = 'Numismatics';

    -- Verifica se o utilizador j� existe
		IF EXISTS (SELECT 1 FROM [Numismatics].[User] WHERE Email = @Email OR NIF = @NIF)
		BEGIN
			ROLLBACK TRANSACTION;
			RAISERROR('J� existe um utilizador com este Email ou NIF.', 16, 1);
			RETURN;
		END

		-- Inser��o do novo utilizador com password encriptada
		INSERT INTO [Numismatics].[User] (
			NIF, Fname, Lname, Email, BDate, Phone, [Password]
		)
		VALUES (
			@NIF, @Fname, @Lname, @Email, @BDate, @Phone,
			EncryptByPassPhrase(@Passphrase, @PlainPassword)
		);

		Commit transaction;
		PRINT 'Utilizador registado com sucesso.';

	END TRY
    BEGIN CATCH
        ROLLBACK TRANSACTION;

        -- Return the error message
        DECLARE @ErrMsg NVARCHAR(4000) = ERROR_MESSAGE();
        RAISERROR(@ErrMsg, 16, 1);
    END CATCH
END;

GO

------------------------------------------------------------
-- 43. Add event
------------------------------------------------------------

ALTER PROCEDURE [Numismatics].[AddEventWithParticipation]
    @EventName VARCHAR(25),
    @StartDate VARCHAR(10),
    @EndDate VARCHAR(10),
    @Localization VARCHAR(40),
    @UserNIF INT,
    @NewEventID INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    
    BEGIN TRY
        BEGIN TRANSACTION;
        
        -- Get the next available Event ID
        SELECT @NewEventID = ISNULL(MAX(ID), 0) + 1 FROM [Numismatics].[Events];
        
        -- Insert the new event
        INSERT INTO [Numismatics].[Events] (ID, [Name], [Start_Date], End_Date, Localization)
        VALUES (@NewEventID, @EventName, @StartDate, @EndDate, @Localization);
        
        -- Get all collections of the user
        INSERT INTO [Numismatics].Participates_Event (ID_Colection, ID_Event)
        SELECT ID, @NewEventID
        FROM [Numismatics].Colection
        WHERE NIF_User = @UserNIF;
        
        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        IF @@TRANCOUNT > 0
            ROLLBACK TRANSACTION;
            
        THROW;
    END CATCH
END