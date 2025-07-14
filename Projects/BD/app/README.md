# CoinCollector

BD - Projeto Final

---

## Estudantes

- **David Pelicano** (113391)  
- **Hugo Dias** (114142)

## Como testar a Aplicação
   
1. Create and activate a virtual environment:
```
   python3 -m venv venv
   source venv\Scripts\activate
```

   
2. Install the dependencies:
```
   cd APFT_113391_114142\app
   pip install -r requirements.txt
```
   
3. To run the App:
```
   cd APFT_113391_114142\app
   python app.py
```
   

## Configuração do Banco de Dados

O arquivo [`conf.ini`](./conf.ini) vem pré-configurado para acessar o servidor do nosso projeto

Para testar localmente por exemplo, basta alterar o ficheiro que criamos com as configurações do servidor local.

Exemplo:
```
[database]
server = localhost\SQLEXPRESS  
name = NomedaBD
username = Username
password = Password
```


