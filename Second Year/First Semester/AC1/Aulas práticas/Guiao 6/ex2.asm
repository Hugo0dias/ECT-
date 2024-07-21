	
	.eqv print_string, 4
	.eqv print_char, 11
	.eqv SIZE, 3
	.data
	
str1:	.asciiz "Array"
str2:	.asciiz "de"
str3:	.asciiz "ponteiros"

array:	.word str1,str2,str3
	.text 
	.globl main
main:	la $t0, array		#p = array
	li $t2, SIZE		#calculo do pultimo: array + size * 4
	sll $t2, $t2, 2		
	addu $t2, $t2, $t0	#pultimo
	
for:	bgeu $t0, $t2, efor
	lw $a0, 0($t2)		#$a0 = p*
	li $v0, print_string
	syscall
	li $a0, '\n'
	li $v0, print_char
	syscall
	addi $t0, $t0, 4	# p++
	j for

efor:	jr $ra