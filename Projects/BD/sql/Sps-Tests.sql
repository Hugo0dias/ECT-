Exec [dbo].AutoresAmbasFaces 

Exec [dbo].GetCollectionsByNIF 109012345

EXEC dbo.CheckUserParticipation 109012345, 1;

EXEC dbo.ColecoesMaisAntigas;

EXEC dbo.ColecoesMultiplosEventos;

EXEC dbo.ContagemMoedasPorCondicao;

EXEC dbo.ContagemMoedasPorPais;

go

DECLARE @Result BIT;
DECLARE @Message NVARCHAR(255);
DECLARE @CollectionID INT = 6; 

EXEC [dbo].[DeleteCollection]  
    @CollectionID = @CollectionID,
    @Result = @Result OUTPUT,
    @Message = @Message OUTPUT;

SELECT @Result AS Result, @Message AS Message;

go

EXEC dbo.GetAuthorsByNationalityAndEvent 1, 'Portugal';

EXEC dbo.GetAvailableNationalities;

EXEC dbo.GetCoinStatsByConditionInEvents 1; 

EXEC dbo.GetCollectionsByNIF 109012345;

EXEC dbo.GetCommemorativeCoinsByCountryAndEvent 1, 'Portugal';

EXEC dbo.GetCountriesWithCommemorativeCoins;

EXEC dbo.GetEventDetails 1;

EXEC dbo.GetEventParticipants 1;

EXEC dbo.GetMissingCountriesByColection 1; 

EXEC dbo.ListarColecoesPorUser 109012345;

EXEC dbo.ListarEventosFuturos;

EXEC dbo.ListarMoedasPorAno 2022; 

EXEC dbo.ListarMoedasPorCondicao UNC; 

EXEC dbo.ListarMoedasPorMaterial CuproNiquel; 

EXEC dbo.ListarMoedasPorMaterialECondicao CuproNiquel, UNC; 

EXEC dbo.ListarMoedasPorPaisEColecao 1, Portugal;

EXEC dbo.ListarMoedasPorUser 109012345; 

EXEC dbo.ListarMoedasRaras 2001; 

EXEC dbo.ListarUsersEventos ; 

EXEC dbo.MediaValorPorTipo; 

EXEC dbo.MoedasAcimaMediaGlobal;

EXEC dbo.MoedasAntes2008; 

EXEC dbo.MoedasComemorativasPorPaisEColecao Portugal, 1; 

EXEC dbo.MoedasDataErro ; 

EXEC dbo.MoedasDepois2008; 

EXEC dbo.[PaisesComemorativasMultiplas]; 

EXEC dbo.RemoveCoin; 

EXEC dbo.SetUserPassword 109012345, '11'; 

EXEC dbo.Top5ColecoesMaisMoedas; 

EXEC dbo.TopAutoresCF; 

EXEC dbo.TopAutoresNF;

EXEC dbo.TotalMoedasComemorativasPorUser; 

EXEC dbo.ValidateUserLogin 109012345, '11'; 

Exec dbo.Edit_Coin;

Exec dbo.AdicionarMoeda;

Exec GetCoinStatsByCondition 1;

Exec [Numismatics].AddEventWithParticipation ExpoCoins, '2020/12/12', '2020/12/15', Turkey, 109012345, 7

EXec dbo.sp_DecryptPasswordByNIF 123123

Exec [Numismatics].[sp_RegisterUser] 12345678, Hugo, Dias, 'hugo@gmail.com', '12-02-2004', 964323122, hugodias
