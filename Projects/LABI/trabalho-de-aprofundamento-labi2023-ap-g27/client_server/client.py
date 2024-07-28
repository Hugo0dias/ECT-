#!/usr/bin/python3

import os
import sys
import socket
import json
import base64
from common_comm import send_dict, recv_dict, sendrecv_dict

from Cryptodome.Cipher import AES
from Cryptodome.Hash import SHA256

# Function to encript values for sending in json format
# return int data encrypted in a 16 bytes binary string coded in base64
def encrypt_intvalue (cipherkey, data):
	return None


# Function to decript values received in json format
# return int data decrypted from a 16 bytes binary strings coded in base64
def decrypt_intvalue (cipherkey, data):
	return None


# verify if response from server is valid or is an error message and act accordingly - já está implementada
def validate_response (client_sock, response):
	if not response["status"]:
		print (response["error"])
		client_sock.close ()
		sys.exit (3)


# process QUIT operation
def quit_action (client_sock, attempts):
	dict = { "op": "QUIT" }
	resposta = sendrecv_dict(client_sock, dict)
	validate_response = (client_sock, resposta)
	exit(1)


# Outcomming message structure:
# { op = "START", client_id, [cipher] }
# { op = "QUIT" }
# { op = "NUMBER", number }
# { op = "STOP", [shasum] }
# { op = "GUESS", choice }
#
# Incomming message structure:
# { op = "START", status }
# { op = "QUIT" , status }
# { op = "NUMBER", status }
# { op = "STOP", status, value }
# { op = "GUESS", status, result }

#
# Suport for executing the client pretended behaviour

def run_client (client_sock, client_id):

	#lista de numeros inseridos pelo cliente
	numeros = []
	#START------------------------------------
	dict = { "op": "START", "client_id": client_id}
	resposta = sendrecv_dict(client_sock, dict)
	validate_response(client_sock, resposta)
	#-----------------------------------------

	while 1:
		print("Menu:")
		print("QUIT [q]")
		print("STOP [s]")
		print(">>> Selecione uma opção ou digite um número")
		opcao = input(">>> ")
		if opcao == "q":
			quit_action(client_sock, 0)
		elif opcao == "s":
			print("Ola")
		elif opcao.isdigit():
			numeros.append(opcao)
			print(">>> Lista de números: ", numeros)
		else:
			print(">>> Resposta Inválida, tente novamente")

def main():
	# validate the number of arguments and eventually print error message and exit with error
	# verify type of of arguments and eventually print error message and exit with error
	#python3 client.py client_id porto [máquina]
	if len(sys.argv) < 3 or len(sys.argv) > 4:
		print("[ERRO] Tem que ser invocado da seguinte forma: python3 client.py client_id porto [máquina].")
		sys.exit(1)

	# obtain the port number
	port = int(sys.argv[2])
	if port < 1024 and port > 65535:
		print("[ERRO] Porta tem que ser um número inteiro entre 1024 e 65535.")
		sys.exit(2)
	
	# obtain client_id
	client_id = sys.argv[1]
	if not isinstance(sys.argv[1], str):
		print("[ERRO] O nome do cliente tem que ser uma string.")
		sys.exit(3)

	# obtain the hostname that can be the localhost or another host
	if len(sys.argv) == 3:
		hostname = "127.0.0.1"
	else:
		hostname = sys.argv[3] 
	
	#verificar se o DNS está correto
	x = hostname.split(".")
	if len(x) != 4:
		print("[ERROR] O DNS tem que ser no formato X.X.X.X, onde X representa um valor inteiro decimal entre 0 e 255.")
		sys.exit(4)
	erros = []
	index = 0
	while index != len(x):
		if int(x[index]) < 0 or int(x[index]) > 255:
			erros.append("1")
		index += 1
	for elements in erros:
		if int(elements) == 1:
			print("[ERROR] O DNS tem que ser no formato X.X.X.X, onde X representa um valor inteiro decimal entre 0 e 255.")
			sys.exit(5)


	print("hostname:", hostname)
	print("porta: ", port)
	print("client_id: ", client_id)

	client_socket = socket.socket (socket.AF_INET, socket.SOCK_STREAM)
	client_socket.bind(("0.0.0.0", 0))
	client_socket.connect ((hostname, port))

	run_client (client_socket, sys.argv[1])  #sys.argv[1] -> client_id

	client_socket.close ()
	sys.exit (0)

if __name__ == "__main__":
    main()
