	.eqv read_char, 12
	.eqv print_ui, 36
	.eqv print_string, 4
	.eqv print_char, 11
	.eqv print_float, 2
	.eqv read_int, 5
	
	.eqv id_number, 0
	.eqv first_name, 4
	.eqv last_name, 22
	.eqv grade, 40

	.data

stg:	.word 72343
	.asciiz "Napoleao"
	.space 9
	.asciiz "Bonaparte"
	.space 5
	.float 5.1

str1:	.asciiz "\nN. Mec: "
str2:	.asciiz "\nNome: "
str3:	.asciiz "\nNota: "

	.text
	.globl main

main:	
	la $t0, stg
	la $a0, str1
	li $v0, print_string
	syscall
	
	lw $a0, id_number($t0)
	li $v0, print_ui
	syscall				# ---------------------------------
	

	la $a0, str2
	li $v0, print_string
	syscall

	addiu $a0, $t0, first_name
	li $v0, print_string
	syscall
	
	li $a0, ','
	li $v0, print_char
	syscall 

	addiu $a0, $t0, last_name
	li $v0, print_string
	syscall

	
	la $a0, str3
	li $v0, print_string
	syscall

	l.s $f12, grade($t0)
	li $v0, print_float
	syscall
	

	jr $ra






	