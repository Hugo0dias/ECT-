<!-- This Heuristic Evaluation Workbook replicates the one proposed by the 
Nielsen Norman Group available at: https://media.nngroup.com/media/articles/attachments/Heuristic_Evaluation_Workbook_-_Nielsen_Norman_Group.pdf
-->

**Evaluator**: Pedro Sugiyama
**Date**: [DD-MM-AAAA]
**Product**: [SuperMaxiApp]

---

Severity Scale adopted: [[severity_scale_heuristic_evaluation]]
Summary of each usability heuristic: [here](https://media.nngroup.com/media/articles/attachments/Heuristic_Summary1-compressed.pdf)

# 1 Visibility of System Status
>	The design should always keep users informed about what is going on, through appropriate feedback within a reasonable amount of time. 
>	- Does the design clearly communicate its state?
>	- Is feedback presented quickly after user actions?


| **Issue**       | **Severity** | Recommendation |
| --------------- |-------------|----------------|
|Em algumas secções, como o carregamento de páginas ou durante buscas, o feedback não é claro.| 1          | Implementar indicadores visuais claros, como spinners ou barras de progresso, durante o carregamento de páginas e buscas.               | ---

# 2 Match Between System and The Real World
>	The design should speak the users' language. Use words, phrases, and concepts familiar to the user, rather than internal jargon. Follow real-world conventions, making information appear in a natural and logical order. 
>	- Will user be familiar with the terminology used in the design? 
>	- Do the design’s controls follow real-world conventions?

| **Issue**       | **Severity** | Recommendation |
| --------------- |-------------|----------------|
|Alguns ícones ou rótulos podem ser mais intuitivos. Por exemplo, ícones de redes sociais ou menus secundários podem não ser imediatamente claros para todos os utilizadores.| 2          | Adicionar rótulos textuais ao lado de ícones para melhorar a clareza e a compreensão.               | ---


# 3 User Control and Freedom
>	Users often perform actions by mistake. They need a clearly marked "emergency exit" to leave the unwanted action without having to go through an extended process. 
>	- Does the design allow users to go back a step in the process? 
>	- Are exit links easily discoverable? 
>	- Can users easily cancel an action? 
>	- Is Undo and Redo supported?

| **Issue**                                                                                                                   | **Severity** | Recommendation |
|-----------------------------------------------------------------------------------------------------------------------------| ------------ | -------------- |
| Em algumas páginas, como formulários ou processos de compra, não há uma opção clara para "cancelar" ou "desfazer" uma ação. | 3            |             Adicionar opções claras de "Cancelar" ou "Desfazer" em formulários e processos críticos.   | ---


# 4 Consistency and Standards
>	Users should not have to wonder whether different words, situations, or actions mean the same thing. Follow platform and industry conventions. 
>	- Does the design follow industry conventions? 
>	- Are visual treatments used consistently throughout the design?

| **Issue**                                                                                                                        | **Severity** | Recommendation |
|----------------------------------------------------------------------------------------------------------------------------------| ------------ | -------------- |
| Algumas páginas, como as de artigos ou planos de treino, têm layouts ligeiramente diferentes, o que pode confundir o utilizador. | 1            |    Padronizar os layouts das páginas para garantir consistência visual e funcional.            | ---

# 5 Error Prevention
>	Good error messages are important, but the best designs carefully prevent problems from occurring in the first place. Either eliminate error-prone conditions, or check for them and present users with a confirmation option before they commit to the action. 
>	- Does the design prevent slips by using helpful constraints? 
>	- Does the design warn users before they perform risky actions?

| **Issue**                                                                                                                                        | **Severity** | Recommendation |
|--------------------------------------------------------------------------------------------------------------------------------------------------| ------------ | -------------- |
| Durante o processo de compra não há orientação para evitar erros, como selecionar o endereço errado ou esquecer de aplicar um cupão de desconto. | 1            |    Adicionar verificações e alertas contextuais durante o processo de compra.            |

# 6 Recognition Rather than Recall
>	Minimize the user's memory load by making elements, actions, and options visible. The user should not have to remember information from one part of the interface to another. Information required to use the design (e.g. field labels or menu items) should be visible or easily retrievable when needed. 
>	- Does the design keep important information visible, so that users do not have to memorize it? 
>	- Does the design offer help in-context?

| **Issue**                                                                                     | **Severity** | Recommendation |
|-----------------------------------------------------------------------------------------------| ------------ | -------------- |
| Algumas funcionalidades, como filtros avançados na loja, não são muito visíveis e intuitivas. | 2            |      Melhorar a visibilidade e a usabilidade dos filtros  avançados.          | ---

# 7 Flexibility and Efficiency of Use
>	Shortcuts — hidden from novice users — may speed up the interaction for the expert user such that the design can cater to both inexperienced and experienced users. Allow users to tailor frequent actions. 
>	- Does the design provide accelerators like keyboard shortcuts and touch gestures? 
>	- Is content and funtionality personalized or customized for individual users?

| **Issue**                                                                                                         | **Severity** | Recommendation |
|-------------------------------------------------------------------------------------------------------------------| ------------ | -------------- |
| Não existem opções de personalização, como favoritos ou listas de desejos, para agilizar a experiência de compra. | 2            | Adicionar funcionalidades de personalização, como listas de desejos e favoritos.               |

# 8 Aesthetic and Minimalist Design
>	Interfaces should not contain information that is irrelevant or rarely needed. Every extra unit of information in an interface competes with the relevant units of information and diminishes their relative visibility. 
>	- Is the visual design and content focused on the essentials? 
>	- Have all distracting, unnescessary elements been removed?

| **Issue**                                                                                    | **Severity** | Recommendation |
|----------------------------------------------------------------------------------------------| ------------ | -------------- |
| Em algumas páginas, há excesso de informações ou anúncios, o que pode distrair o utilizador. | 2            |  Simplificar o design, removendo elementos desnecessários e focando no conteúdo principal.              |

# 9 Help Users Recognize, Diagnose, and Recover from Errors
>	Error messages should be expressed in plain language (no error codes), precisely indicate the problem, and constructively suggest a solution. 
>	- Does the design use traditional error message visuals, like bold, red text? 
>	- Does the design offer a solution that solves the error immediately?

| **Issue**                                                                                                                                                  | **Severity** | Recommendation |
|------------------------------------------------------------------------------------------------------------------------------------------------------------| ------------ | -------------- |
| As mensagens de erro não são específicas relativamente ao tipo de erro, e não sugere soluções (por exemplo, "Tente outro código ou verifique a validade"). | 1            |  Melhorar as mensagens de erro, tornando-as mais descritivas e sugerindo soluções práticas.              |


# 10 Help and Documentation
>	It’s best if the system doesn’t need any additional explanation. However, it may be necessary to provide documentation to help users understand how to complete their tasks. 
>	- Is help documentation easy to search? 
>	- Is help provided in context right at the moment when the user requires it?

| **Issue**                                                                                    | **Severity** | Recommendation |
|----------------------------------------------------------------------------------------------| ------------ | -------------- |
| Durante o processo de compra, o utilizador pode ter dúvidas sobre políticas de entrega, devoluções, métodos de pagamento ou uso de cupões. No entanto, o acesso à documentação ou à secção de ajuda não é imediatamente visível ou acessível durante esse processo. O utilizador precisa de sair da página de checkout e procurar manualmente a secção de "Ajuda" ou "FAQs", o que pode gerar frustração e até mesmo abandonar o carrinho. | 2            |  Adicionar links contextuais de ajuda durante o processo de compra, como "Política de devoluções" ou "Como usar cupões".              |
