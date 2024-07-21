	.eqv print_string, 1

	.data
str:	.asciiz "201632412 e 2020 sao anos bissextos"
	
	.text
	.globl main

main:	addiu $sp, $sp , -4
	sw $ra, 0($sp)
	la $a0, str
	jal atoi
	move $a0, $v0
	li $v0, print_string
	syscall
	li $v0, 0
	lw $ra, 0($sp)
	addiu $sp, $sp , 4
	jr $ra

atoi:	li $v0, 0
while:	lb $t0, 0($a0)
	blt $t0, '0', end
	bgt $t0, '9', end
	addi $t1, $t0, -0x30
	addiu $a0, $a0, 1
	mul $v0, $v0, 10
	add $v0, $v0, $t1
	j while


end: jr $ra	