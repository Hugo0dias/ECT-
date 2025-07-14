import sys
import os
sys.path.append('.')

from session import create_connection
BASE_PATH = os.path.dirname(os.path.abspath(__file__))

def readScriptsFromFile(filename):
    full_path = os.path.join(BASE_PATH, filename)
    with open(full_path, 'r', encoding='utf-8') as fd:
        sqlFile = fd.read()
    return [cmd.strip() for cmd in sqlFile.split(';') if cmd.strip()]

def execute_scripts():
    script_files = [
        "../../DDL-Project.sql"
        #"../../StoredProcedures.sql",
        #"../../Triggers.sql",
        #"../../UDF.sql",
        #"../../Indexes.sql"
    ]

    with create_connection() as conn:
        cursor = conn.cursor()

        for script_file in script_files:
            print(f"\n▶ Executando comandos de: {script_file}")
            commands = readScriptsFromFile(script_file)

            for command in commands:
                try:
                    print(f"\n→ Executando:\n{command[:100]}...")
                    cursor.execute(command)
                    cursor.commit()
                except Exception as e:
                    print(f"❌ Erro ao executar:\n{command}")
                    print("Detalhes:", e)

if __name__ == '__main__':
    execute_scripts()
