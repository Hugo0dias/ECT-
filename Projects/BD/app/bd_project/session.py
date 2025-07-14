import configparser
import functools
import os
from pathlib import Path
import pyodbc


@functools.lru_cache
def conn_string() -> str:
    # Caminho absoluto para conf.ini dentro da pasta Flask
    config_file = Path(__file__).resolve().parent.parent / "conf.ini"
    assert config_file.exists(), f"conf.ini file not found at {config_file}"

    config = configparser.ConfigParser(os.environ)
    config.read(config_file)

    server = config["database"]["server"]
    db_name = config["database"]["name"]
    username = config["database"]["username"]
    password = config["database"]["password"]

    return f"DRIVER={{SQL Server}};SERVER={server};DATABASE={db_name};UID={username};PWD={password};"


def create_connection():
    my_conn_string = conn_string()
    return pyodbc.connect(my_conn_string)
