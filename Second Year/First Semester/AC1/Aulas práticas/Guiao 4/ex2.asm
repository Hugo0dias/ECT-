	.eqv print_int10, 1
	.eqv read_string, 8
	
	.eqv size, 20
	.eqv sizemais1, 21
	
	.data
	
str:	.space sizemais1
	
	.text
	.globl main
	
main:	li $t0, 0
	la $a0, str #read_string(str, SIZE)
	li $a1, size #res = 0
	li $v0, read_string
	syscall
	
	la $t1, str #i = 0
	
	
while: 	
	lb $t2, 0($t1) #str[i]
	beq $t2, $0, ewhile
	blt $t2, '0', eif
	bgt $t2, '9', eif	
	addi $t0, $t0, 1
eif: 	addi $t1, $t1, 1
	j while
ewhile: move $a0, $t0
	li $v0, print_int10
	syscall 
	jr $ra