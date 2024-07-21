	
	.eqv SIZE,13
	.eqv read_int, 5
	.eqv print_int10, 1
	.eqv print_string, 4
	
	.data
array:	.word 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16
string:	.asciiz "valor: \n"
	
	.text
	.globl main

main:	li $t0, 0	# i = 0
	la $a0, array
	move $t1, $a0	# t1 = &array(0)
	
for:	bge $t0, SIZE, efor
	lw $t2, 0($t1)	# valor = a[i]
	addi $t1, $t1, 4
	
	li $v0, print_int10
	move $a0, $t2
	syscall 
	
	addi $t0, $t0, 1
	
	j for
	
efor:	jr $ra
	
	
	