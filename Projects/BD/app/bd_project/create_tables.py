import sys
sys.path.append('.')  

from session import create_connection

def readScriptsFromFile(filename):
    with open(filename, 'r', encoding='utf-8') as fd:
        sqlFile = fd.read()
    return [cmd.strip() for cmd in sqlFile.split(';') if cmd.strip()]

def create_tables():
    scripts = readScriptsFromFile("DDL-Project.sql")  # tabelas

    with create_connection() as conn:
        cursor = conn.cursor()
        for command in scripts:
            try:
                cursor.execute(command)
                print("Comando executado com sucesso.")
            except Exception as e:
                print("Erro ao criar tabela:", e)
        conn.commit()

if __name__ == '__main__':
    create_tables()
