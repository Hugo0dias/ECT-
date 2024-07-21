
	.data
age: .word 23

	.text 
	.globl main
	
main:	
	la $a0, age
	move $t1, $a0
	lw $a0, 0($t1)
	li $v0, 1
	syscall 
