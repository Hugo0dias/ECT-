#!/usr/bin/python3

import sys
import socket
import select
import json
import base64
import csv
import random
from common_comm import send_dict, recv_dict, sendrecv_dict

from Cryptodome.Cipher import AES
from Cryptodome.Hash import SHA256

# Dicionário com a informação relativa aos clientes
users = {}

# return the client_id of a socket or None
def find_client_id (client_sock):
	for client_id in users:
		if users[client_id]["client_sock"] == client_sock:
			return client_id
	return None


# Função para encriptar valores a enviar em formato json com codificação base64
# return int data encrypted in a 16 bytes binary string and coded base64
def encrypt_intvalue (client_id, data):

	cipherkey= users[client_id]["cipher"] #check if the user has a cipher 
	if cipherkey is None or not cipherkey: 
		return data
	cipher = AES.new(cipherkey, AES.MODE_ECB) #data encryptation
	dataencrypted= cipher.encrypt(bytes("%16d"%(data), "utf8"))
	dataencryptedtosend= base64.b64encode(dataencrypted)
	return str(dataencryptedtosend, "utf8")

# Função para desencriptar valores recebidos em formato json com codificação base64
# return int data decrypted from a 16 bytes binary string and coded base64
def decrypt_intvalue (client_id, data):

	cipherkey= users[client_id]["cipher"] #check if the user has a cipher
	if cipherkey is None or not cipherkey: 
		return data
	
	cipher = AES.new(cipherkey, AES.MODE_ECB) # data decoding
	datadecoded= base64.b64decode(data)
	datadecrypted= cipher.decrypt(datadecoded)
	return int(str(datadecrypted, "utf8"))


# Função auxiliar para gerar o resultado - já está implementada
# return int value and list of description strings identifying the characteristic of the value
def generate_result(list_values):
	if len(list_values) % 2 == 1:
		test = 4
	else:
		test = 3

	minimal = min(list_values)
	maximal = max(list_values)
	first = list_values[0]
	last = list_values[-1]

	choice = random.randint(0, test)
	if choice == 0:
		if minimal == first:
			return first, ["min", "first"]
		elif maximal == first:
			return first, ["max", "first"]
		else:
			return first, ["first"]
	elif choice == 1:
		if minimal == last:
			return last, ["min", "last"]
		elif maximal == last:
			return last, ["max", "last"]
		else:
			return last, ["last"]
	elif choice == 2:
		if minimal == first:
			return first, ["min", "first"]
		elif minimal == last:
			return last, ["min", "last"]
		else:
			return minimal, ["min"]
	elif choice == 3:
		if maximal == first:
			return first, ["max", "first"]
		elif maximal == last:
			return last, ["max", "last"]
		else:
			return maximal, ["max"]
	elif choice == 4:
		list_values.sort()
		median = list_values[len(list_values) // 2]
		if median == first:
			return first, ["median", "first"]
		elif median == last:
			return last, ["median", "last"]
		else:
			return median, ["median"]
	else:
		return None


# Incomming message structure:
# { op = "START", client_id, [cipher] }
# { op = "QUIT" }
# { op = "NUMBER", number }
# { op = "STOP", [shasum] }
# { op = "GUESS", choice }
#
# Outcomming message structure:
# { op = "START", status }
# { op = "QUIT" , status }
# { op = "NUMBER", status }
# { op = "STOP", status, value }
# { op = "GUESS", status, result }


#
# Suporte de descodificação da operação pretendida pelo cliente - já está implementada
#
def new_msg (client_sock):
	request = recv_dict (client_sock)
	# print( "Command: %s" % (str(request)) )

	op = request["op"]
	if op == "START":
		response = new_client (client_sock, request)
	elif op == "QUIT": # 
		response = quit_client (client_sock, request)
	elif op == "NUMBER": # 
		response = number_client (client_sock, request)
	elif op == "STOP": # 
		response = stop_client (client_sock, request)
	elif op == "GUESS": # 
		response = guess_client (client_sock, request)
	else:
		response = { "op": op, "status" : False, "error": "Operação inexistente" }

	# print (response)
	send_dict (client_sock, response )

#
# Suporte da criação de um novo cliente - operação START
#
# detect the client in the request - feito
# verify the appropriate conditions for executing this operation
# process the client in the dictionary
# return response message with or without error message
def new_client (client_sock, request): #request -> {'op': 'START', 'client_id': 'marinheiro'}
	print("ola")
	print(request)
	#buscar o cliente que se conectou
	client_id = request["client_id"] #marinheiro

	#procurar pelo client_id
	if find_client_id(client_sock):
		return { "op": "START", "status": False, "error": "Cliente existente" }
	else:
		#adicionar ao dicionário users informações sobre o cliente registado ex: marinheiro, num numeros,...
		users[client_id] = {"client_sock": client_sock, "numeros": []}
		
		return { "op": "START", "status": True}
#
# Suporte da eliminação de um cliente - já está implementada
#
# obtain the client_id from his socket and delete from the dictionary
def clean_client (client_sock):
	client_id = find_client_id (client_sock)
	if client_id != None:
		print ("Client %s removed\n" % client_id)
		del users[client_id]


#
# Suporte do pedido de desistência de um cliente - operação QUIT
#
# obtain the client_id from his socket -feito
# verify the appropriate conditions for executing this operation
# process the report file with the QUIT result
# eliminate client from dictionary using the function clean_client
# return response message with or without error message
def quit_client (client_sock, request):
	client_id = find_client_id(client_sock)

	if client_id in users:
		update_file(client_id, len(users[client_id]["numeros"]), "QUIT")
		clean_client(client_sock)
		return { "op": "QUIT", "status": True }
	else:
		return { "op": "QUIT", "status": False, "error": "Cliente inexistente" }



#
# Suporte da criação de um ficheiro csv com o respectivo cabeçalho - já está implementada
#
def create_file ():
	with open("result.csv", "w", newline="") as csvfile:
		columns = ["client_id", "number_of_numbers", "guess"]

		fw = csv.DictWriter (csvfile, delimiter=",", fieldnames=columns)
		fw.writeheader()


#
# Suporte da actualização de um ficheiro csv com a informação do cliente
#
# update report csv file with the simulation of the client

def update_file(client_id, size, guess):
    data = [[client_id, size, guess]]

    with open("result.csv", "a", newline="") as csvfile:
        writer = csv.writer(csvfile)
        writer.writerows(data)



#
# Suporte do processamento do número de um cliente - operação NUMBER
#
# obtain the client_id from his socket
# verify the appropriate conditions for executing this operation
# return response message with or without error message
def number_client (client_sock, request):
	return None


#
# Suporte do pedido de terminação de um cliente - operação STOP
#
# obtain the client_id from his socket
# verify the appropriate conditions for executing this operation
# randomly generate a value to return using the function generate_result
# process the report file with the result
# return response message with result or error message
def stop_client (client_sock, request):
	# ...
	# value, solution = generate_result (users[client_id]["numbers"])
	# ...
	return None


#
# Suporte da adivinha de um cliente - operação GUESS
#
# obtain the client_id from his socket
# verify the appropriate conditions for executing this operation
# eliminate client from dictionary
# return response message with result or error message
def guess_client (client_sock, request):
	return None


def main():
	# validate the number of arguments and eventually print error message and exit with error
	# verify type of of arguments and eventually print error message and exit with error

	if len(sys.argv) != 2:
		print("[ERRO] Tem que ser invocado da seguinte forma: python3 server.py porto.")
		sys.exit(1)

	# obtain the port number
	port = int(sys.argv[1])
	if port < 1024 and port > 65535:
		print("[ERRO] Porta tem que ser um número inteiro entre 1024 e 65535.")
		sys.exit(2)

	print(">>> [ESTADO] Servidor iniciando com sucesso")
	server_socket = socket.socket (socket.AF_INET, socket.SOCK_STREAM)
	server_socket.bind (("127.0.0.1", port)) 
	server_socket.listen ()

	clients = []
	create_file ()

	while True:
		try:
			available = select.select ([server_socket] + clients, [], [])[0]
		except ValueError:
			# Sockets may have been closed, check for that
			for client_sock in clients:
				if client_sock.fileno () == -1: client_sock.remove(clients) # closed
			continue # Reiterate select

		for client_sock in available:
			# New client?
			if client_sock is server_socket:
				newclient, addr = server_socket.accept ()
				clients.append (newclient)
			# Or an existing client
			else:
				# See if client sent a message
				if len (client_sock.recv (1, socket.MSG_PEEK)) != 0:
					# client socket has a message
					##print ("server" + str (client_sock))
					new_msg (client_sock)
				else: # Or just disconnected
					clients.remove (client_sock)
					clean_client (client_sock)
					client_sock.close ()
					break # Reiterate select

if __name__ == "__main__":
	main()
