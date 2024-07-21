	
	.eqv SIZE,40
	.eqv read_int, 5
	.eqv print_int10, 1
	.eqv print_string, 4
	
	.data
str1:	.asciiz ":::::::"
array:	.space 40
string:	.asciiz "Numero: "
	
	.text
	
# MAPA DE REGISTOS 
# $t0: i
# $t1: valor
# $t2: &array(0)
# $t3; 5

	.globl main
	
main:	li $t0, 1		# i  = 0
	
do:	la $a0, string
	li $v0, print_string
	syscall			# print da string
	  	
	li $v0, read_int
	syscall 		# valor = a
	move $t1, $v0		# a --> $t1
	
	la $t2, array		# &array
	lw $t1, 0($t2)		# array[0] = a
	
	addi $t0, $t0, 1	# I++
	sll $t0, $t0, 2
	li $t8, SIZE
	
while:	blt $t0, $t8, do
	
	li $t0, 1
	j for
	
for:	blt $t0, SIZE, outfor

	li $t3, 5
	la $t2, array
	
if:	ble $t0, $0, else
	bge $t0, $t3, else
	lw $t4, 0($t2)
	sll $t4, $t4, 2
	
	la $a0, str1
	li $v0, print_string
	syscall 
	
	li $v0, print_int10
	syscall 
	
	addi $t0, $t0, 1
	sll $t0, $t0, 2
	
	j for
	
else:	
	
	li $v0, print_int10
	lw $t5, 0($t0)
	syscall 
	
	addi $t0, $t0, 1
	sll $t0, $t0, 2
	
	j for
	
outfor:	jr $ra
	
	
	
	
	
	
	
	
	
	
	
	
	
	