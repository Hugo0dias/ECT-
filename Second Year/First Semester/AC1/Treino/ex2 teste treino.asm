#t0 = i
#$t1 = val[i]
#$t2 = lista
#$t4 = v
	
			
	.eqv print_string, 4
	.eqv print_int10, 1
	.eqv print_char, 11
	.eqv SIZE, 8
	
	.data
	
list:	.word 8,4,15,-1987,327,-9,27,16
str:	.asciiz "Result is: "
	
	.text
	.globl main

main:	li $t0, 0		# i = 0
	la $t2, list
	li $t1, SIZE

do:
	sll $t1, $t0, 2		# multiplos de 4
	addu $t1, $t2, $t1	# val[i]
	
	lw $t4, 0($t1)		# v = val[i]
	lw $t1, 16($t1)
	lw $t1, 0($t4)
	addi $t0, $t0, 1
	addi $t5, $t0, 4
	
	blt $t0, $t5, do
	
out:	la $a0, str
	li $v0, print_string
	syscall 
	li $t0, 0
	
do1:	
	sll $t1, $t0, 2
	addu $t1, $t2, $t1
	lw $a0, 0($t1)
	li $v0, print_int10
	syscall 
	li $a0,':'
	li $v0,print_char
	syscall
	
	addi $t0, $t0, 1
	
	blt $t0, SIZE, do1

out2:	jr $ra
	
	
	
	
	
	
	