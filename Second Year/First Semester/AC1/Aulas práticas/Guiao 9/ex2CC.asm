	.eqv read_double, 7 
	.eqv print_double, 3
	.eqv print_string, 4

	.data
x0:	.double	5.0
x1:	.double	9.0
x2:	.double	32.0
str1:	.asciiz	"Temperatura em Fahrenheit: "
str2:	.asciiz "\nTemperatura em Celsius: "
	.text
	.globl main

main:	addiu $sp, $sp, -4
	sw $ra, 0($sp)			# espaço para receber o endereco 

	la $a0, str1
	li $v0, print_string		#ler strinig
	syscall

	li $v0, 7			# pedir numero
	syscall

	mov.d $f12, $f0			# mover para f12 porque e o registo de passagem 
	jal f2c		
	
	la $a0, str2
	li $v0, print_string		#ler strinig
	syscall

	li $v0, 3
	syscall

	lw $ra, 0($sp)
	addiu $sp, $sp, 4
	li $v0, 0
	jr $ra


	.text

f2c:	
	la $t0, x0
	l.d $f2, 0($t0)		# f2 = 5.0
	la $t0, x1
	l.d $f4, 0($t0)		# f4 = 9.0
	la $t0, x2
	l.d $f6, 0($t0)		# f6 = 32.0

	sub.d $f12, $f12, $f6
	mul.d $f12, $f12, $f4
	div.d $f0, $f2, $f12

	jr $ra
 	