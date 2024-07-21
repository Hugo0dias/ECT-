	.eqv read_int, 5
	.eqv print_string, 4
	.eqv print_char, 4
	.data
	
str: 	.asciiz "Introduza um número: "
str2: 	.asciiz "O valor em binário é: " 

	.text
	
	.globl main
	
main: 	li $t2, 0

	la $a0, str
	li $v0, print_string 
	syscall 
	li $v0, read_int
	syscall
	move $t0, $v0
	la $a0, str2
	li $v0, print_string 
	syscall


for:	bge $t2, 32, endfor
	li $t3, 0x80000000
	and $t1, $t0, $t3
	beq $t1, $0, else
	li $a0, '1'
	li $v0, print_char
	syscall 
	j eif
else:	
	li $a0, '0'
	li $v0, print_char
	syscall
eif:	sll $t0, $t0, 1

	addi $t2, $t2, 1
	j for
	
endfor: