	
	.eqv print_string, 4
	.eqv read_int, 5
	.eqv print_char, 11
	.eqv print_int10, 1
	.eqv read_string, 8
	.eqv SIZE, 10
	
	.data
lista:	.word 8, -4, 3, 5, 124, -15, 87, 9, 27, 15
str:	.asciiz "\n Conteudo deo array: \n"
pve:	.asciiz ": "
	.text
	.globl main

main:	la $a0, str
	li $v0, print_string
	syscall 
	la $t0, lista
	li $t2, SIZE		#size = 10
	sll $t2, $t2, 2		#multiplos de 4 por serem inteiros
	addu $t2, $t2, $t0	# lista + size
for:	bgeu $t0, $t2, efor	# nao faz for se p >= lista + size
	lw $a0, 0($t0)		#load no int
	li $v0, print_int10
	syscall 
	la $a0, pve
	li $v0, print_string
	syscall 
	addiu $t0, $t0, 4
	j for
efor:	jr $ra