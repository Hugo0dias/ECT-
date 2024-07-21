	
	.eqv read_int, 5
	.eqv print_int10, 1
	.eqv print_char, 11
	.eqv print_string, 4
	
	.data
	
val:	.word 
str:	.asciiz "maximo/minimo sao: \n"
char:	.asciiz ":\n"
str1:	.asciiz "Digite ate 20 inteiros (zero para terminar): \n"  

	.text
	.globl main
	
main:	
	li $t0, 0		# n = 0
	li $t1, 5		# min = 0x7FFFFFFF
	li $t2, 10		# max = 0x80000000
	la $a0, str1 
	li $v0, print_string
	syscall 
	
do:	li $v0, read_int
	syscall 
	move $t3, $v0		# val = read_int()
	
if:	bne $t3, 0, if1		# se val != 0 goto if1

eif:	addi $t0, $t0, 1	# n = n+1
	j do			# goto do

if1:	ble $t3, $t2, if2	# se val <= max go to if2
	move $t2, $t3		# max = val  --------------------------------------
	j eif			# incrementa

if2:	bge $t3, $t1, eif	# se val >= min go to if
	move $t1, $t3		# min = val
	j eif			# incrementa
	
	li $t4, 20		# $t4 = 20
	blt $t0, $t4, while	# n<20
	j out			# sai fora se n>=20
	
while:	bne $t3, 0, do		# val != 0
	j out			# sai fora se val = 0
	
out:	la $a0, str
	li $v0, print_string
	syscall 
	move $a0, $t2
	li $v0, print_int10
	syscall 
	la $a0, char
	li $v0, print_char
	syscall 
	move $a0, $t1
	li $v0, print_int10
	syscall 