import os
import sys
import socket
import json
import base64

from Cryptodome.Cipher import AES
from Cryptodome.Hash import SHA256

def main():

    if len(sys.argv) < 3 or len(sys.argv) > 4:
        print("[ERRO] Tem que ser invocado da seguinte forma: python3 client.py client_id porto [máquina]")
        sys.exit(1)
