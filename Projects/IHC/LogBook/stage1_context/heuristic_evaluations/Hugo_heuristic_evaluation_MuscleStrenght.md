<!-- This Heuristic Evaluation Workbook replicates the one proposed by the 
Nielsen Norman Group available at: https://media.nngroup.com/media/articles/attachments/Heuristic_Evaluation_Workbook_-_Nielsen_Norman_Group.pdf
-->

**Evaluator**: Hugo Dias
**Date**: [23-02-2025]
**Product**: [SuperMaxiApp]

Severity Scale adopted: [[severity_scale_heuristic_evaluation]]
Summary of each usability heuristic: [here](https://media.nngroup.com/media/articles/attachments/Heuristic_Summary1-compressed.pdf)

# 1 Visibility of System Status
>	The design should always keep users informed about what is going on, through appropriate feedback within a reasonable amount of time. 
>	- Does the design clearly communicate its state?
>	- Is feedback presented quickly after user actions?

| **Issue**                                              | **Severity** | Recommendation                                      |
| ------------------------------------------------------ | ------------ | --------------------------------------------------- |
| Barra de Tarefas disponivel apenas no topo do web site | 1            | Tornar a barra dinamica                             |
| Sugestões na Pesquisa                                  | 1            | Adicionar sugestões com base no input               |

# 2 Match Between System and The Real World
>	The design should speak the users' language. Use words, phrases, and concepts familiar to the user, rather than internal jargon. Follow real-world conventions, making information appear in a natural and logical order. 
>	- Will user be familiar with the terminology used in the design? 
>	- Do the design’s controls follow real-world conventions?

| **Issue**                                   | **Severity** | Recommendation                                                 |
| ------------------------------------------- | ------------ | -------------------------------------------------------------- |
| Titulos nao apropriados (Ex Ab Exercises)   | 1            | Colocar títulos sem abreviações                                |

# 3 User Control and Freedom
>	Users often perform actions by mistake. They need a clearly marked "emergency exit" to leave the unwanted action without having to go through an extended process. 
>	- Does the design allow users to go back a step in the process? 
>	- Are exit links easily discoverable? 
>	- Can users easily cancel an action? 
>	- Is Undo and Redo supported?

| **Issue**                                      | **Severity** | Recommendation                                               |
| ---------------------------------------------- | ------------ | -----------------------------------------------------------  |
| Sistema de gostos de exercicios por modalidade | 3            | Adicionar uma feature de likes para exercícios               |

# 4 Consistency and Standards
>	Users should not have to wonder whether different words, situations, or actions mean the same thing. Follow platform and industry conventions. 
>	- Does the design follow industry conventions? 
>	- Are visual treatments used consistently throughout the design?

| **Issue**                                                                | **Severity** | Recommendation                                 |
| ------------------------------------------------------------------------ | ------------ | ---------------------------------------------- |
| Nao apresenta consistencia na visibilidade do estado atual               | 1            | Adicionar "Estado Atual" a todo o Website      |
| Inconsistencia no tipo de enumeração nas caracteristicas dos exercicios  | 1            | Definir uma estrutura de enumeração específica |

# 5 Error Prevention
>	Good error messages are important, but the best designs carefully prevent problems from occurring in the first place. Either eliminate error-prone conditions, or check for them and present users with a confirmation option before they commit to the action. 
>	- Does the design prevent slips by using helpful constraints? 
>	- Does the design warn users before they perform risky actions?

| **Issue**                                                                                                | **Severity** | Recommendation                                    |
| -------------------------------------------------------------------------------------------------------- | ------------ | ------------------------------------------------- |
| Permite inserir endereços não existentes / cidade / codigo postal e telefone na realizaçao de uma compra | 3            | Usar uma API que liste as cidades ... possíveis   |
| Permite fazer registo com qualquer email que seja inserido                                               | 4            | Inserir uma confirmação via email pelo user       |

# 6 Recognition Rather than Recall
>	Minimize the user's memory load by making elements, actions, and options visible. The user should not have to remember information from one part of the interface to another. Information required to use the design (e.g. field labels or menu items) should be visible or easily retrievable when needed. 
>	- Does the design keep important information visible, so that users do not have to memorize it? 
>	- Does the design offer help in-context?

| **Issue**                                                                                    | **Severity** | Recommendation                    |
| -------------------------------------------------------------------------------------------- | ------------ | --------------------------------- |
| Estado atual                                                                                 | 1            | Adicionar a todo o WebSite        |
| A pessoa gostava de realizar um workout que fez na semana passada e nao se lembra qual foi   | 2            | Adicionar uma feature de likes    |

# 7 Flexibility and Efficiency of Use
>	Shortcuts — hidden from novice users — may speed up the interaction for the expert user such that the design can cater to both inexperienced and experienced users. Allow users to tailor frequent actions. 
>	- Does the design provide accelerators like keyboard shortcuts and touch gestures? 
>	- Is content and funtionality personalized or customized for individual users?

| **Issue**                                                               | **Severity** | Recommendation                                     |
| ----------------------------------------------------------------------- | ------------ | -------------------------------------------------- |
| Barra de Tarefas disponivel apenas no topo do web site                  | 1            | Tornar a barra dinamica                            |
| Separaçao de exercicios por tipo (Maquina / Halteres / Peso Corporal)   | 2            | Fazer um filtro do tipo de exercicio               |

# 8 Aesthetic and Minimalist Design
>	Interfaces should not contain information that is irrelevant or rarely needed. Every extra unit of information in an interface competes with the relevant units of information and diminishes their relative visibility. 
>	- Is the visual design and content focused on the essentials? 
>	- Have all distracting, unnescessary elements been removed?

| **Issue**                                                               | **Severity** | Recommendation                                                            |
| ----------------------------------------------------------------------- | ------------ | ------------------------------------------------------------------------- |
| Design vertical com muito espaço em branco desaproveitado               | 1            | Aproveitar espaço em branco com uma melhor estrutura visual               |
| Inconsistencia no tipo de enumeração nas caracteristicas dos exercicios | 1            | Definir uma estrutura de enumeração específica                            |
| Paginas muito extensas (C. Info pouco relevante)                        | 1            | Eliminar info desnecessária                                               |
| Organização não muito boa na disposição dos elementos visuais           | 1            | Melhorar estrutura visual                                                 |
| Disposisao de Titulos do Exercicio confusos           | 2            | Fazer uma separação de exercícios mais explícita                                            |


# 9 Help Users Recognize, Diagnose, and Recover from Errors
>	Error messages should be expressed in plain language (no error codes), precisely indicate the problem, and constructively suggest a solution. 
>	- Does the design use traditional error message visuals, like bold, red text? 
>	- Does the design offer a solution that solves the error immediately?

| **Issue**                                                                 | **Severity** | Recommendation                                            |
| ------------------------------------------------------------------------- | ------------ | --------------------------------------------------------- |
| Permite fazer registo com qualquer email que seja inserido                | 4            | Inserir uma confirmação via email pelo user               |


# 10 Help and Documentation
>	It’s best if the system doesn’t need any additional explanation. However, it may be necessary to provide documentation to help users understand how to complete their tasks. 
>	- Is help documentation easy to search? 
>	- Is help provided in context right at the moment when the user requires it?

| **Issue**                            | **Severity** | Recommendation                                                   |
| ------------------------------------ | ------------ | ---------------------------------------------------------------- |
| Pouca enfase no About Us (Escondido) | 1            | Adicionar o About Us à barra de tarefas ou no topo da página     |