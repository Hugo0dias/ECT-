	.eqv print_int10, 1
	
	.text
atoi:	li $v0,0 #res = 0
while:	lb $t0, 0($a0) # le *s
	blt $t0, '0', ewhile # nao faz se (*s < '0') || (*s > '9')
	bgt $t0, '9', ewhile
	li $t0, '0'
	sub $t1, $t0, $t2 # digit = *s - '0'
	addiu $a0, $a0, 1 # s++
	mul $v0, $v0, 10 # res = 10 * res
	addu $v0,$v0, $t1 # res = res + digit
	j while
ewhile:	jr $ra	#v0 ja tem res


	.data
str:	.asciiz "27843 e 2024 sao anos bissextos"
	.text
	.globl main
main:	addiu $sp, $sp, -4
	sw $ra, 0($sp) 
	