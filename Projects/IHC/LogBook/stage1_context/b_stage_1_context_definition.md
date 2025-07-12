[Back to main Logbook Page](../hci_logbook.md)

---
# B. Stage 1 - Context Definition


# B.1. Competitor Identification
>	The competitor analysis will entail an identification of all competitors, with brief descriptions and a collection of the look and feel of their solutions, e.g., with screenshots, etc. It will also include a detailed analysis of the competitor deemed the best or more representative.



## B.1a. Competitors


| **Competitor**    | **Description**                             		   | Information repository                 |
| ----------------- | ---------------------------------------------------- | -------------------------------------- |
| Muscle & Strenght | Plataforma Online de Fitness e vanda de Suplementos  | [Competitor Analysis Muscle Strenght]  |



## B.1b. Detailed Competitor Analysis
>	Choose the most notable competitor and do a more thorough analysis of their interactive solution


### - Heuristic Evaluation

#### Method

- Utilizamos as heurísticas de Nielson e participaram 3 Experts nessa avaliação.  
- Como severity scale usamos a seguinte :  
0 - I don't agree that this is a usability problem at all;  
1 - Cosmetic problem;  
2 - Minor usability problem;  
3 - Major usability problem;  
4 - Usability catastrophe.  
- Calculamos a média e esse foi o consenso


#### Individual Evaluations


- [expert1_heuristic_evaluation_workbook](./heuristic_evaluations/Hugo_heuristic_evaluation_MuscleStrenght.md)

- [expert2_heuristic_evaluation_workbook](./heuristic_evaluations/Pedro_heuristic_evaluation_MuscleStrenght.md)

- [expert3_heuristic_evaluation_workbook](./heuristic_evaluations/Francisco_heuristic_evaluation_MuscleStrenght.md)


#### Consensus

>	After the individual analysis by each expert, all results should be gathered in a consensus table. If an expert has not found any of the problems found by other experts, they should analyse it, at this point, and give it a severity.

|   **Issue**                                                                 |   **Heuristic**                           						                     | **Francisco** |    **Hugo**    |   **Pedro**   | **Median** | **Recomendations**                                                                                                           |
| --------------------------------------------------------------------------- |  ----------------------------------------------------------------------------------- |---------------|   ------   |   -----   |------------|------------------------------------------------------------------------------------------------------------------------------|
| Feedback não é claro                                                        |  Visibility of System Status  			  						 					 | 1             |  1  |  1  | 1          | Implementar indicadores visuais claros, como spinners ou barras de progresso, durante o carregamento de páginas e buscas     |
| Barra de tarefas apenas no topo                                             |  Visibility of System Status; Flexibility and Efficiency of Use  					 | 1             |  1  |  1  | 1          | Tornar a barra dinâmica                                                                                                      |
| Não há sugestões na pesquisa                                                |  Visibility of System Status; Flexibility and Efficiency of Use  					 | 1             |  1  |  2  | 1          | Adicionar sugestões com base no input                                                                                        |
| Shopping cart vazio é pouco claro                                           |  Visibility of System Status; Aesthetic and Minimalist Design    					 | 1             |  0  |  1  | 1          | Uso de layout / ícones padrões                                                                                               |
| Alguns ícones são difíceis de reconhecer                                    |  Match Between System and The Real World  						 					 | 2             |  1  |  2  | 2          | Adicionar rótulos textuais ao lado de ícones para melhorar a clareza e a compreensão.                                        |
| Disposição confusa de títulos de exercício                                  |  Aesthetic and Minimalist Design  						 					 | 2             |  2  |  2  | 2          | Fazer uma separação de exercícios mais explícita                                                                             |
| Títulos não apropriados (i.e. abreviados)                                   |  Match Between System and The Real World  						 					 | 1             |  1  |  1  | 1          | Colocar títulos sem abreviações                                                                                              |
| Faltam opções de cancelar/desfazer ações                                    |  User Control and Freedom; Help Users Recognize, Diagnose, and Recover from Errors   | 3             |  1  |  3  | 3          | Adicionar opções claras de "Cancelar" ou "Desfazer" em formulários e processos                                               |
| Não há sistema de "gostar" para exercícios                                  |  User Control and Freedom; Flexibility and Efficiency of Use     					 | 3             |  3  |  2  | 3          | Adicionar uma feature de likes                                                                                               |
| Suporte redireciona usar para outro site sem opção de retorno               |  User Control and Freedom 										 					 | 3             |  3  |  3  | 3          | A página de suporte deve estar na plataforma principal ou a página de suporte deve ter um link para voltar facilmente à home |
| Não é possível obter e-mail de contacto                                     |  User Control and Freedom; Help and Documentation 				 					 | 3             |  2  |  3  | 3          | Colocar o endereço de e-mail na página principal                                                                             |
| Downbar do website muda em algumas secções, com certas opções indisponíveis |  User Control and Freedom; Consistency and Standards 			 					 | 3             |  2  |  3  | 3          | Decidir em um único layout                                                                                                   |
| Não há barra de pesquisa por categoria                                      |  User Control and Freedom; Flexibility and Efficiency of Use 	 					 | 3             |  3  |  3  | 3          | Incrementar esta funcionalidade na barra principal ou criar novas opções de pesquisa por secção                              |
| Não há sorting ao fazer pesquisa 											  |  User Control and Freedom; Flexibility and Efficiency of Use 	 					 | 2             |  2  |  2  | 2          | Reutilizar sorting usado para browsing no store                                                                              |
| Shopping cart não é salvo após logout 									  |  User Control and Freedom 										 					 | 2             |  2  |  2  | 2          | Armazenamento em local storage                                                                                               |
| Algumas páginas têm layout ligeiramente diferente 						  |  Consistency and Standards 										 					 | 1             |  0  |  1  | 1          | Padronizar os layouts das páginas para garantir consistência visual e funcional.                                             |
| Não há consistência na visibilidade do estado atual 						  |  Consistency and Standards; Recognition Rather than Recall 		 					 | 1             |  1  |  1  | 1          | Adicionar "Estado Atual" a todo o Website                                                                                    |
| Inconsistência no tipo de enumeração nas características dos exercícios 	  |  Consistency and Standards; Aesthetic and Minimalist Design 	 					 | 2             |  1  |  1  | 1          | Definir uma estrutura de enumeração específica                                                                               |
| Muita informação solicitada na criação de conta  							  |  Consistency and Standards 										 					 | 1             |  0  |  1  | 1          | Colocar estas informações na edição do perfil                                                                                |
| Barra de pesquisa serve para produtos e artigos   						  |  Consistency and Standards 										 					 | 0             |  1  |  2  | 2          | Separar os diferentes tipos de pesquisa                                                                                      |
| Link para o site de suporte na informação de e-mail 						  |  Consistency and Standards 										 					 | 1             |  1  |  2  | 2          | Colocar o endereço de e-mail em conjunto com o link                                                                          |
| Opções de sorting são pouco claras   										  |  Consistency and Standards; Recognition Rather than Recall 		 					 | 2             |  1  |  2  | 2          | Remover ou renomear algumas opções                                                                                           |
| Não há orientação para evitar endereço errado ou esquecimento de usar cupão |  Error Prevention; Help Users Recognize, Diagnose, and Recover from Errors  		 | 1             |  3  |  2  | 1          | Adicionar verificações e alertas contextuais durante o processo de compra                                                    |
| É possível inserir endereço/telefone inexistentes na realização de compra   |  Error Prevention; 														    		 | 4             |  3  |  3  | 3          | Usar uma API que liste as cidades ... possíveis                                                                              |
| Permite fazer registo com qualquer e-mail que seja inserido   			  |  Error Prevention; Help Users Recognize, Diagnose, and Recover from Errors  		 | 4             |  4  |  3  | 4          | Inserir uma confirmação via email pelo user                                                                                  |
| Não há histórico de exercícios/workouts 									  |  Recognition Rather than Recall 										    		 | 2             |  2  |  2  | 2          | Adicionar um histórico com os ultimos exercícios/workouts feitos                                                             |
| Não há opções de personalização para compras (favoritos/lista de desejos)   |  Flexibility and Efficiency of Use  									    		 | 2             |  2  |  2  | 2          | Adicionar funcionalidades de personalização, como listas de desejos e favoritos.                                             |
| Não há separação de exercícios por tipo (Máquina/Halteres/Peso Corporal)    |  Flexibility and Efficiency of Use  									    		 | 2             |  2  |  2  | 2          | Fazer uma separação de exercícios mais explícita                                                                             |
| Excesso de informação e anúncios em algumas páginas 						  |  Aesthetic and Minimalist Design 										    		 | 2             |  1  |  1  | 1          | Simplificar o design, removendo elementos desnecessários e focando no conteúdo principal.                                    |
| Design vertical com muito espaço em branco desaproveitado 				  |  Aesthetic and Minimalist Design 										    		 | 1             |  1  |  1  | 1          | Aproveitar espaço em branco com uma melhor estrutura visual                                                                  |
| Desorganização na disposição de elementos visuais 						  |  Aesthetic and Minimalist Design 										    		 | 1             |  1  |  1  | 1          | Melhorar a estrutura visual                                                                                                   |
| Texto difícil de enxergar na downbar										  |  Aesthetic and Minimalist Design 										    		 | 1             |  1  |  1  | 1          | Alterar a cor do texto                                                                                                       |
| Produtos com imagens muito grandes na loja								  |  Aesthetic and Minimalist Design 										    		 | 1             |  0  |  2  | 2          | Diminuir tamanho da imagem                                                                                                   |
| Mensagens de erro são genéricas e não sugerem soluções 					  |  Help Users Recognize, Diagnose, and Recover from Errors 				    		 | 2             |  1  |  2  | 2          | Melhorar as mensagens de erro, tornando-as mais descritivas e sugerindo soluções práticas.                                   |
| Informações de ajuda não são visíveis no processo de compra				  |  Help and Documentation 												    		 | 2             |  2  |  2  | 2          | Adicionar links contextuais de ajuda durante o processo de compra, como "Política de devoluções" ou "Como usar cupões".      |
| Pouca ênfase no About Us													  |  Help and Documentation 												    		 | 1             |  1  |  2  | 1          | Adicionar o About Us à barra de tarefas ou no topo da página                                                                 |







---
### - Cognitive Walkthrough

#### Method

Dividimos um determinado cenário em tasks mais pequenas e respondemos às seguintes questões com Sim ou Não:  

- Will User Know What to do at this step?  
- If the user does the right thing, will they know it is progressing towards goal?  
- Is action successfull?  

Com isto conseguimos compreender problemas do WebSite que não se podem repetir no nosso sistema.
#### Task Selection and Task Analysis

Escolhemos estas tasks pois são aquelas mais executadas, que envolvem uma maior interação por parte do utilizador e as tarefas onde o nosso sistema irá incidir.


| Task                                                                                              | Subtasks                               |
| ------------------------------------------------------------------------------------------------- | -------------------------------------- |
| **1. Comprar uma creatina da marca nutrex. Enviá-la para Portugal e efetuar pagamento em euros.** | Clicar em store     								|
|                                                                                                   | Shop by category 									|
|                             																		| Scroll e selecionar creatine     					|
|                             																		| Clicar na creatina (opcional: filtrar por marca)  |
|                             																		| Selecionar quantidade e adicionar ao carrinho 	|
|                             																		| Ir ao carrinho 									|
|                             																		| Ir ao checkout 									|
|                             																		| Fazerlogin 										|
| 																									| Voltar para o site original 
| 																									| Escolher a opção de Login							|
| 																									| Escolher opção de criar conta						|
| 																									| Inserir dados e criar conta						|
| 																									| Selecionar o carrinho								|
| 																									| Inserir dados e efetuar pagamento					|



| Task                          | Subtasks                                |
| ----------------------------- | --------------------------------------- |
| **2. Procurar o site por uma receita de Apple Muffin** | Ir a receitas |
|                               | Selecionar snacks ou breakfast - não é claro qual categoria é a correta; o user pode se confundir e ir a Vegetarian ou a Low Carb             |
|                               | Filtrar por nome - user pode se perder nesta etapa. Pode estar a procurar por uma forma de pesquisar exclusiva para as receitas             |
|                               | Ir à página do apple muffin        |



| Task                                                                                              | Subtasks                               |
| ------------------------------------------------------------------------------------------------- | -------------------------------------- |
| **3. Encontrar um exercicio de Abdominal e ver a sua execução (Turkish Get Up to Knee).** 		| Selecionar a opção "Exercises" na barra de tarefas    	|
|                                                                                                   | Procurar pela secção relativa aos abdominais e Selecionar 		|
|                             																		| Dar Scroll na página até ao fim    	|
|                             																		| Mudar de página, clicar no círculo "2"      |
|                             																		| Dar Scroll na página até encontrar o exercício  		|
|                             																		|  		|





#### Results

Task: 1.

| Step # | Task/Action to Perform                        | Will User Know What to do at this step? (Yes/No) | Notes | If the user does the right thing, will they know it is progressing towards goal? (Yes/No) | Notes | Is Action Successful? (Yes/No) | Suggestions for Improvement |     |
|--------|-----------------------------------------------|--------------------------------------------------| ----- |-------------------------------------------------------------------------------------------| ----- |--------------------------------| --------------------------- | --- |
| 1      | Clicar em Store                               | Yes                                              |       | Yes                                                                                       |       | Yes                            |               |     |
| 2      | Shop by category                              | Yes                                         |       | Yes                                                                                       |       | Yes                       |               |     |
| 3      | Scroll e selecionar creatine                  | Yes                                        |       | Yes                                                                                       |       | Yes                       |              |     |
| 4      | Clicar na creatina                            | Yes                                         |       | Yes                                                                                       |       | Yes                       |                |     |
| 5      | Selecionar quantidade e adicionar ao carrinho | Yes                                         |       | Yes                                                                                       |       | Yes                       |               |     |
| 6      | Ir ao carrinho                                | Yes                                         |       | Yes                                                                                  |       | Yes                       |                |     |
| 7      | Ir ao checkout                                | Yes                                         |       | Yes                                                                                  |       | Yes                       |                |     |
| 8      | Fazer login                                  | No                                         | O user pode não apresentar uma conta pessoal, o que gera problemas na compra pois só permite dar login      | Yes                                                                                  |       | Yes                       | Apresentar uma opção de criar conta naquela página               |     |
| 9      | Voltar para o site          | No                                         |  User é obrigado a voltar ao WebSite principal, porém esta opção não é intuitiva pois estamos perante uma página fora da interface inicial     | No                                                                                  |       | Yes                       | Manter a interface principal consistente durante o processo todo               |     |
| 10      | Escolher a opção de Login         | Yes                                         |       | Yes                                                                                  |       | Yes                       |                |     |
| 11      | Escolher opção de criar conta         | Yes                                         |       | Yes                                                                                  |       | Yes                       |                |     |
| 12      | Inserir dados e criar conta         | Yes                                         |       | Yes                                                                                  |       | Yes                       |              |     |
| 13      | Selecionar o carrinho         | Yes                                         |       | Yes                                                                                  |       | Yes                       |        |     |
| 14      | Inserir dados e efetuar pagamento        | Yes                                         |       | Yes                                                                                  |       | Yes                       |           |     |




Task: 2

| Step # | Task/Action to Perform | Will User Know What to do at this step? (Yes/No) | Notes                                                        | If the user does the right thing, will they know it is progressing towards goal? (Yes/No)                    | Notes | Is Action Successful? (Yes/No) | Suggestions for Improvement                                                                                 |     |
|--------| -------------------- |--------------------------------------------------|--------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------| ----- |--------------------------------|-------------------------------------------------------------------------------------------------------------| --- |
| 1      | Ir a receitas   | Yes                                              |                                                              | Yes                                                                                                          |       | Yes                            |                                                                                               |     |
| 2      | Selecionar snacks ou breakfast  | Yes                                              | User pode se confundir e ir a Vegetarian ou a Low Carb | Yes                                                                                                          |       | Yes                            | Existência de uma barra de pesquisa para as receitas, assim o utilizador pode ir diretamente à receita que pretende |     |
| 3      | Filtrar por nome  | No                                               |                                                              | Yes | User pode se perder nesta etapa. Pode estar a procurar por uma forma de pesquisar exclusiva para as receitas       | Yes                            |                                                                                               |     |
| 4      | Ir à página do apple muffin      | Yes                                              |                                                              | Yes                                                                                                          |       | Yes                            |                                                                                              |     |



Task: 3

| Step # | Task/Action to Perform | Will User Know What to do at this step? (Yes/No) | Notes | If the user does the right thing, will they know it is progressing towards goal? (Yes/No) | Notes | Is Action Successful? (Yes/No) | Suggestions for Improvement  |     |
|--------| -------------------- |--------------------------------------------------|--------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------| ----- |--------------------------------|-------------------------------------------------------------------------------------------------------------| --- |
| 1      | Selecionar a opção "Exercises" na barra de tarefas   | Yes                                              |                                                              | Yes                                                                                                          |       | Yes                            |                                                                                           |     |
| 2      | Procurar pela secção relativa aos abdominais e Selecionar  | Yes                                              |  | Yes                                                                                                          |       | Yes                            |  |     |
| 3      | Dar Scroll na página até ao fim  | Yes                                               |                                                              | Yes |       | Yes                            | Existência de uma barra de pesquisa para os exercícios ou existência de um filtro                                                                                             |     |
| 4      | Mudar de página, clicar no círculo "2"      | Yes                                              |                                                              | Yes                                                                                                          |       | Yes                            |
| 5      | Dar Scroll na página até encontrar o exercício      | Yes                                              |                                                              | Yes                                                                                                          |       | Yes                            |





# B.2. Users
>	For the users, there are two goals: 1) understand the current status of users in the domain you are addressing. How do they manage, what are the main tasks they do, if they use some tool for the purpose, what are current challenges, what might be improved, what might be new features, ...


## B.2a. Method

Para obter resultados das entrevistas, usamos o Google Forms e partilhamos. [[URL](https://forms.gle/qY28sfFrLN2arpRr5)]
Com a entrevista pretendemos perceber as necessidades e problemas que sistemas similares apresentam
Foram consideradas perguntas que avaliam as APPs em uso, quais os pontos negativos e positivos e as features que apresentam ...

## B.2b. Results

>	This section tracks all informal user interviews, summarizing key insights and linking to detailed notes for each session. 

### Interview List 
| Date       | Participant / Role | Key Insights                                                    | Link to Notes                  |     |
| ---------- | ------------------ | --------------------------------------------------------------- | ------------------------------ | --- |
| 2025-02-25 | Duarte             | Alimentação - Controlar calorias, proteína e outros macros \ Gym - Sugestão de plano de treino  | [📄 Notes](./interviews/interview-Hugo1.md) |     |
| 2025-02-25 | Beatriz            | Alimentação - Receitas especializadas, ajuda na escolha de ingredientes, informações sobre a comida e como comer bem \ Gym - Ajuda especializada no treino                                                             | [📄 Notes](./interviews/interview-Hugo2.md)  |     |
| 2025-02-25 | Simão              | Alimentação - Código QR para ler os alimentos \ Gym - Criar os meus treinos e controlar o peso utilizado                                                             | [📄 Notes](./interviews/interview-Hugo2.md) |     |
| 2025-02-25 | Tomás              | Alimentação - Fornecer um catálogo explicativo de cada alimento \ Gym - Planos de treino, para iniciantes e Cronômetro dentro da APP para evitar estar sempre a mudar de interface                                                             | [📄 Notes](./interviews/interview-Hugo3.md) |     |

### Common Themes & Patterns 

- **Recurring Problems:** 
	- Apesar de ter muitos exercícios e máquinas para criar os treinos ainda falta alguns
	- Podia ser um bocado mais intuitivo
- **Frequently Used Tools:** 
	- Liftoff
	- Strava
	- Mi Fitness
	- Viva Gym
- **Desired Features / Solutions:** 
	- Controlar calorias, proteína e outros macros
	- Receitas especializadas, ajuda na escolha de ingredientes, informações sobre a comida e como comer bem
	- Código QR para ler os alimentos
	- Uma feature para quem quer perder peso, indicar os melhores alimentos e um plano de dieta
	- Fornecer um catálogo explicativo de cada alimento
	----------------------------------------
	- Sugestão de plano de treino
	- Ajuda especializada no treino
	- Criar os meus treinos e controlar o peso utilizado
	- Videos de exemplo de execução
	- Planos de treino, para iniciantes
	- Cronômetro dentro da APP para evitar estar sempre a mudar de interface
	- Tracking de progresso (C. Graficos)
- --- 



---
[Back to main Logbook Page](../hci_logbook.md)

---

