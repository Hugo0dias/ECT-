# Projeto AS 2024 - Grupo 305

## Repositório para o projeto final de Análise de Sistemas, feito por:

* António Caetano, antonio.caetano@ua.pt
* Carlos Verenzuela, carlos.verenzuela@ua.pt
* Hugo Dias, hugomdias@ua.pt
* Rúben Gomes, rlcg@ua.pt

## Link do repositório: https://github.com/RubenCGomes/as-proj-24

## Instruções de uso:

### Regras gerais:

* Um utilizador sem conta deve poder ter livre acesso ao site, mas não pode reservar um cacifo nem editar uma conta
* A seleção dos dias deve ser linear, não podendo haver dias intercalados
* A seleção dos dias em vermelho (ocupados) não deve ser possível
* Após fechar o navegador, é terminada a sessão automaticamente (_behaviour_ normal do `sessionStorage`)
* As contas criadas permanecem no `localStorage` até este ser limpo
* Existem apenas 3 zonas de cacifos no mapa, estando estas logo visíveis ao abrir a página onde está o mesmo
* Para reservar um cacifo, é necessário selecionar a zona e o tamanho do mesmo, sendo assim redirecionado para a página desse mesmo cacifo.
* Clicar no logo da página redireciona o utilizador para a página inicial (index.html)

### Contas existentes:

Inicialmente, as contas disponíveis são as seguintes (incluídas no [seguinte ficheiro](website/db/logins.json)) (email-password):

* admin
	* admin@onebox.com
	* admin
* user
	* user@domain.com
	* user 
* distributor
	* distributor@dist-comp.com
	* distributor

Dado que a interface é bastante intuitiva e a existência de um manual simplificado na aplicação, não é necessário a inclusão de um _step-by-step guide_ de como usar a aplicação neste ficheiros.

### Descontos no checkout

Tal como demonstrado na apresentação/testes, existem 2 tipos de descontos possíveis, sendo estes o **cartão cliente** e **códigos promocionais**. O cartão cliente desconta 5% do preço final e o código promocional desconta 10% do preço final. Estes códigos encontram-se a seguir:

* Cartões cliente:
	* 12345
	* 23456
	* 34567
* Códigos promocionais:
	* DISCOUNT
	* PROMO
	* SALE
	
