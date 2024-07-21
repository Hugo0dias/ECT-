	.eqv read_double, 7
	.eqv print_str, 4
	.eqv print_double, 3
	
	.data
k1:	.double 5.0
k2:	.double 9.0
k3:	.double 32.0
str1:	.asciiz "\n Introduza temperatura em Farenheit:  \n "
str2:	.asciiz "\n Temperatura em Celcius \n " 
	
	.text
f2c:	la $t0, k1
	l.d $f0, 0($t0)
	#la $t0, k2
	l.d $f2, 8($t0)
	#la $t0, k3
	l.d $f4, 16($t0)
	sub.d $f12, $f12, $f4
	div.d $f0, $f0, $f2
	mul.d $f0, $f0, $f12
	jr $ra
	
	.globl main
main:	addiu $sp, $sp, -4
	sw $ra, 0($sp)
	
	la $a0, str1
	li $v0, print_str
	syscall
	li $v0, read_double
	syscall
	mov.d $f12, $f0
	jal f2c
	la $a0, str1
	li $v0,print_str
    	syscall
    	mov.d $f12,$f0
    	li $v0, print_double
    	syscall
    
    	lw $ra, 0($sp)
    	addiu $sp,$sp,4
    	jr $ra