	.eqv read_char, 12
	.eqv print_ui, 36
	.eqv print_string, 4
	.eqv print_char, 11
	.eqv print_float, 2
	.eqv read_int, 5
	
	.eqv of_id, 0
	.eqv of_fn, 4
	.eqv of_ln, 22
	.eqv of_gr, 40
	.eqv dim_fn, 17
	
	.data
	.align 2
	
stg:	.word 72343		#unsigned int id_number
	.asciiz "Napoleao"	# char first_name[18]
	.space 9
	.asciiz "Bonaparte"	# char last_name[15]
	.space 4
	.align 2		# .space 3
	.float 5.1		# float grade
	
str1:	.asciiz "\nNMec: "
str2:	.asciiz "\nNome: "
str3:	.asciiz "\nNota: "
str4:	.asciiz "\nInsira: "

	.text
	.globl main
	
main:	la $a0, str1
	li $v0, print_string
	syscall
	li $v0, read_int
	syscall 
	la $t0, stg
	sw $v0, of_id($t0)
	la $a0, str4
	li $v0, print_string
	syscall
	la $t0, stg
	addiu $a0, $t0, of_fn
	li $a1, dim_fn
	li $v0, print_string
	syscall 
	la $a0, stg
	la $a0, str1
	li $v0, print_string
	syscall
	la $a0, stg
	lw $a0, of_id($t0)
	li $v0, print_ui
	syscall
	la $a0, str2
	li $v0, print_string
	syscall
	la $a0, stg
	addiu $a0, $a0, of_ln
	li $v0, print_string
	syscall
	li $a0, ','
	li $v0, print_char
	syscall
	la $a0, stg
	addiu $a0, $a0, of_fn
	li $v0, print_string
	syscall
	
	la $a0, str3
	li $v0, print_string
	syscall
	la $a0, stg
	addiu $a0, $a0, of_gr         # como dar print neste float !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	li $v0, print_float
	syscall
	
	jr $ra
