	.eqv print_string, 4
	.eqv print_char, 11
	.eqv print_int, 1
	.eqv SIZE, 3
	.data

str1:	.asciiz "Array"
str2:	.asciiz "de"
str3:	.asciiz "ponteiros"
stra1:	.asciiz "\nString #"
stra2:	.asciiz ": "

array:	.word str1,str2,str3

	.text
	.globl main
	
main:	li $t0,0
for:	bge $t0, SIZE, efor
	la $a0, stra1
	li $v0, print_string 
	syscall
	move $a0, $t0
	li $v0, print_int10
	syscall 
	la $a0, stra2		# imprime string adicional 2
	li $v0, print_string
	syscall 
	li $t1, 0		# j=0
while:	la $t2, array
	move $t3, $t0		# i*4
	sll $t3, $t3, 2
	addu $t3, $t3, $t2
	lw $t4, 0($t3)		#carrega array[i] que é um ponteiro para array de char
	addu $t4, $t4, $t1	#&array[i][j], endereco do elemento j de array i
	lb $t5, 0($t4)		#$t5 = array[i][j]
	beq $t5, 0, ewhile
	move $a0, $t5		#print_char (array[i][j])
	li $v0, print_char
	syscall 		
	addi $t1, $t1, 1
	