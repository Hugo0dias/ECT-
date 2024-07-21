
	.data
	
str:	.asciiz "I serodatupmoC ed arutetiuqrA"

	
	.text
	.globl main

main:	


strcpy:	li $t0, 0
	addu $t1, $a1, $t0
	lb $t3, 0($t1)
	addu $t2, $a0, $t0
	sb $t3, 0($t2)
	addi $t0, $t0, 1
	beq $t3, '\0', edo
	j do
	
edo: 	move $v0, $a0
	jr $ra
	
#	
#
	
	.text
strlen:	li $v0, 0
while:	lb $t0, 0($a0)
	beq $t0, 0, ewhile
	addiu $a0, $a0, 1
	addi $v0, $v0, 1
	j while

ewhile: