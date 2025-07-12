<!-- This Heuristic Evaluation Workbook replicates the one proposed by the 
Nielsen Norman Group available at: https://media.nngroup.com/media/articles/attachments/Heuristic_Evaluation_Workbook_-_Nielsen_Norman_Group.pdf
-->

**Evaluator**: Pedro Sugiyama
**Date**: [24-02-2025]
**Product**: [Muscle & Strength]


Severity Scale adopted: [[severity_scale_heuristic_evaluation]]
Summary of each usability heuristic: [here](https://media.nngroup.com/media/articles/attachments/Heuristic_Summary1-compressed.pdf)

# 1 Visibility of System Status
>	The design should always keep users informed about what is going on, through appropriate feedback within a reasonable amount of time. 
>	- Does the design clearly communicate its state?
>	- Is feedback presented quickly after user actions?

| **Issue**                                                                  | **Severity**   | Recommendation |
| -------------------------------------------------------------------------- | -------------- | -------------- |
| Shopping cart vazio é pouco detalhado (o user pode não perceber onde está) | 1              | Uso de layout / ícones padrões    | ---

# 2 Match Between System and The Real World
>	The design should speak the users' language. Use words, phrases, and concepts familiar to the user, rather than internal jargon. Follow real-world conventions, making information appear in a natural and logical order. 
>	- Will user be familiar with the terminology used in the design? 
>	- Do the design’s controls follow real-world conventions?

# 3 User Control and Freedom
>	Users often perform actions by mistake. They need a clearly marked "emergency exit" to leave the unwanted action without having to go through an extended process. 
>	- Does the design allow users to go back a step in the process? 
>	- Are exit links easily discoverable? 
>	- Can users easily cancel an action? 
>	- Is Undo and Redo supported?

| **Issue**       | **Severity** | Recommendation |
| --------------- | ------------ | -------------- |
| Ao clicar em suporte, o user é redirecionado para outro site sem opção para voltar ao site original | 3            | A página de suporte deve estar na plataforma principal ou a página de suporte deve ter um link para voltar facilmente à home               |
| Não é possível obter o e-mail de contacto. A única opção é submeter um pedido de contacto no website de suporte   | 3            | Colocar o endereço de e-mail na página principal               |
| Downbar do website muda em diferentes secções; algumas opções tornam-se indisponíveis | 2 | Decidir em um único layout|
| Não há barra de pesquisa para várias das categorias do site (workouts, exercícios etc) | 3 | Incrementar esta funcionalidade na barra principal ou criar novas opções de pesquisa por secção|
| Ao pesquisar um ítem, não é possível fazer sorting | 2 | Reutilizar sorting usado para browsing no store | ***
| Shopping cart não é salvo após logout | 2 | Armazenamento em local storage |

# 4 Consistency and Standards
>	Users should not have to wonder whether different words, situations, or actions mean the same thing. Follow platform and industry conventions. 
>	- Does the design follow industry conventions? 
>	- Are visual treatments used consistently throughout the design?

| **Issue**                                                        | **Severity** | Recommendation                                              |
| ---------------------------------------------------------------- | ------------ | ----------------------------------------------------------- |
| Muita informação solicitada na criação de conta                  | 1            | Colocar estas informações na edição do perfil               |
| Barra de pesquisa serve para produtos e artigos                  | 2            | Separar os diferentes tipos de pesquisa                     |
| Em lugar do e-mail link para o site de suporte                   | 2            | Colocar o endereço de e-mail em conjunto com o link         | ***
| Opções de sorting são pouco claras                               | 2            | Remover ou renomear algumas opções                          | ***

# 5 Error Prevention
>	Good error messages are important, but the best designs carefully prevent problems from occurring in the first place. Either eliminate error-prone conditions, or check for them and present users with a confirmation option before they commit to the action. 
>	- Does the design prevent slips by using helpful constraints? 
>	- Does the design warn users before they perform risky actions?

# 6 Recognition Rather than Recall
>	Minimize the user's memory load by making elements, actions, and options visible. The user should not have to remember information from one part of the interface to another. Information required to use the design (e.g. field labels or menu items) should be visible or easily retrievable when needed. 
>	- Does the design keep important information visible, so that users do not have to memorize it? 
>	- Does the design offer help in-context?

# 7 Flexibility and Efficiency of Use
>	Shortcuts — hidden from novice users — may speed up the interaction for the expert user such that the design can cater to both inexperienced and experienced users. Allow users to tailor frequent actions. 
>	- Does the design provide accelerators like keyboard shortcuts and touch gestures? 
>	- Is content and funtionality personalized or customized for individual users?

# 8 Aesthetic and Minimalist Design
>	Interfaces should not contain information that is irrelevant or rarely needed. Every extra unit of information in an interface competes with the relevant units of information and diminishes their relative visibility. 
>	- Is the visual design and content focused on the essentials? 
>	- Have all distracting, unnescessary elements been removed?

| **Issue**                                  | **Severity** | Recommendation             |
| ------------------------------------------ | ------------ | -------------------------- |
| Página de perfil muito poluída visualmente | 1            |                            |
| Texto difícil de enxergar na downbar       | 1            | Alterar a cor do texto     |
| Produtos com imagens muito grandes na loja | 2            |                            | ***

# 9 Help Users Recognize, Diagnose, and Recover from Errors
>	Error messages should be expressed in plain language (no error codes), precisely indicate the problem, and constructively suggest a solution. 
>	- Does the design use traditional error message visuals, like bold, red text? 
>	- Does the design offer a solution that solves the error immediately?

# 10 Help and Documentation
>	It’s best if the system doesn’t need any additional explanation. However, it may be necessary to provide documentation to help users understand how to complete their tasks. 
>	- Is help documentation easy to search? 
>	- Is help provided in context right at the moment when the user requires it?
