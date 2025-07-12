[Back to main Logbook Page](../hci_logbook.md)

---
# C. Requirement Definition
>	Based on all the gathered context, including an understanding of current practices, competitors, and user feedback and expectations: 
>	- summarize the user characteristics, context, and motivations using Personas
>	- explain your vision for a novel solution that will target user motivations using Scenarios
>	- identify requirements

# Personas

## Persona: [Pessoa habituada à execução de exercícios] 
### Summary 
| Attribute        | Details                                       |
| ---------------- | --------------------------------------------- |
| **Photo**        | ![Persona Name\|100](personas/persona.jpg)  |
| **Name**         | Pedro Martins                               |
| **Age**          | 32                                          |
| **Occupation**   | Gestor de Marketing                        |
| **Location**     | Porto, Portugal                             |
| **Goals**        | Gostaria de marcar o progresso de seu treino e poder personalizá-lo com novos exercícios           |
| **Pain Points**  | Tem pouco tempo para aprender novos exercícios, não tem uma ferramenta que facilite ver sua evolução e permita editar seu plano de treino              |
| **Motivation**   | *"Quero cuidar do meu corpo, bem-estar e sentir me mais confiante. Preciso de algo prático, que se encaixe nos meus treinos habituais e possa me informar sobre novos exercícios. Quero acompanhar meu progresso de modo eficiente e verificar meus resultados para sentir-me motivado. Quero também encontrar uma namorada pois sinto-me um pouco sozinho e sem algo que me faça sair da rotina."*                |
| **Full Profile** | [📄 Read More](personas/persona1_template.md) |

---


# Scenarios


## Scenario 1:
Pedro gostaria de registar o seu progresso de um plano de treino em especifico de forma a acompanhar o desenvolvimento muscular e a sua resistencia ao esforço. Para isso vai entrar na APP "Motion Lab" e selecionar o plano de treino que vai executar no dia. Durante o treino, regista o numero de repeticoes que faz por cada execucao de um exercicio, a carga que usa, o tempo de descanso e regista umas notas sobre a forma com que executou o exercicio. No fim do treino guarda todos estes dados e terá a liberdade de rever como foi o seu treino naquele dia na próxima vez que for fazer o treino.

## Scenario 2:
O Pedro, cansado de repetir os mesmos exercícios de costas há 6 meses, decide renovar seu plano de treino através da app "Motion Lab" para pesquisar exercícios que nunca fez, Remada curvada com barra T ou Pullover na polia. Após explorar as opções disponíveis na app, ele escolhe o Pullover na polia por ser diferente e trabalhar múltiplos grupos musculares. No ginásio, ele substitui um exercício antigo (como a remada com barra) pelo novo e, caso sinta que foi útil e eficaz, atualiza seu plano de treino na app, mantendo a variedade e a motivação nos treinos.

## Scenario 3:
O Pedro ao não ter a certeza sobre a execução correta de um exercício, decide pesquisá-lo na app Motion Lab, onde encontra demonstrações em vídeo e dicas detalhadas sobre a execução adequada. Ele aprende a forma correta, prestando atenção aos pontos-chave como postura, amplitude de movimento e contração muscular. No ginásio, ele replica a execução da melhor forma possível, focando na precisão do movimento. Com isto, o Pedro evita lesões, maximiza a eficiência do exercício e aumenta os seus ganhos de massa muscular de maneira segura e consistente.

## Scenario 4:
Pedro gostaria de criar e personalizar um plano de treino para fazer em casa porque ao fim de semana o ginásio está fechado. Para isso vai à aba de Plano de treinos e nomeia seu plano como "Treino de Hipertrofia para Peito e Costas em casa". Em seguida, ele filtra os tipos de treino que deseja focar, optando por peito, costas e sem máquinas, visto que não as tem em casa.
Dentro da categoria escolhida, Lucas encontra uma lista de exercícios disponíveis. Para aquecimento, escolhe polichinelos e alongamento dinâmico, preparando seu corpo para o esforço principal. No treino, ele seleciona todos os exercícios que pretende fazer. Cada exercício pode ser ajustado conforme sua necessidade, numero de series, numero de repeticoes e tempo de descanso.
Com o treino configurado, Lucas finaliza o processo clicando em "Criar". O treino é salvo automaticamente em sua biblioteca, permitindo que ele acesse e modifique conforme necessário.


## Scenario 5: Pedro guarda um exercício em sua lista de favoritos
Pedro gostaria de guardar um exercício que lhe foi recomendado, mas ainda não quer alterar seu plano de treino. Para fazer isso, ele vai vai à aba de exercícios, pesquisa pelo exercício específico, e entra na sua página. Em seguida, Pedro clica no ícone de "like". No futuro, quando quiser checar informações, ele vai à aba de "favoritos", onde encontra todos os exercícios adicionados previamente. Ao clicar no exercício ele é redirecionado à página correspondente, podendo verificar sua correta execução ou adicioná-lo a um plano de treino.


# Requirements





## C.1. Functional requirements

- A app precisa ter uma secção de browsing de exercícios
- A app precisa de uma funcionalidade de busca
- A app precisa de páginas informativas relativas a cada exercício
- A app deve apresentar sorting na lista de exercícios favoritos (default: tempo desde que foi adicionado)  
- Os dados de um utilizador (i.e. planos de treino, exercícios favoritos) devem ser guardados entre diferentes acessos  
- Os Exercícios devem conter vídeos, imagens e descrições detalhadas  
- O catálogo deve conter filtros, de forma a facilitar a busca  
- O user deve poder alterar e personalizar os seus planos de treino  
- O user deve poder guardar exercícios  
- O user deve poder criar uma conta pessoal  
- O user deve poder criar planos de treino

## C.2. Non-functional requirements

-   Os exercícios devem ter incorporados, um Vídeo, Imagens e Descrições detalhadas  
-   O sistema deve ser responsivo  
-   O sistema deve ser seguro  
-   O sistema deve ser suportado por diferentes formatos de tecnologia 
       (Telemóvel ou PC)  
-   O sistema deve ter suporte em pelo menos duas línguas (Inglês e Português)  
-   O sistema deve ser concistente e agradável ao utilizador visualmente  
-   A app deve apresentar os exercícios de forma clara, com títulos representativos

---
[Back to main Logbook Page](hci_logbook.md)
