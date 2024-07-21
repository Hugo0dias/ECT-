 	
 	.eqv Print_String, 4
 	.eqv Print_Integer, 1
 	.eqv Read_integer, 5
 	
 	.data

str: .asciiz "Número que pretende inserir: "
str1: .asciiz "O resultado é: "	

	.text
	.globl main
	
main:	la $a0, str	
	li $v0, Print_String #imprime no ecra a str 
	syscall 
	li $v0, Read_integer #le o valor digitado no teclado
	syscall 
	add $t0, $v0, $0
	
if:	li $t1, 0 # t1 = 0
	bne $t0, $t1, elseif # 0 != t0 vai para elseif
	addi $t0, $t0, 1 # t0 = 1
	add $v0, $t0, $0
	j fim
	
elseif:	blez $t0, else # se t0 <= 0 vai para else
	li $t0, 7 # t0 = 0
	add $v0, $t0, $0
	j fim
	
else: 	li $t0, 9 # t0 = 9
	add $v0, $t0, $0
	j fim

fim:	la $a0, str1
	li $v0, Print_String
	syscall
	li $v0, Print_Integer
	syscall 
	
	jr $ra
	
	
	
	
	
	
	
	