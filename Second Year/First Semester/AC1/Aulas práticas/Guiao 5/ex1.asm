
	.eqv print_string, 4
	.eqv read_int, 5
	.eqv print_char, 11
	.eqv print_int10, 1
	.eqv read_string, 8
	.eqv SIZE, 5

	.data
	
lista:	.space 20
	.align 2
str:	.asciiz "introduza o numero: "

	.text 
	.globl main
main:	li $t0, 0
for:	bge $t0, SIZE, efor
	la $a0, str
	li $v0, print_string
	syscall 
	li $v0, read_int
	syscall 
	la $t1, lista
	sll $t2, $t0, 2
	addu $t2, $t1, $t2
	sw $v0, 0($t2)
	addi $t0, $t0,1
	j for
efor: 
	jr $ra
	