	.eqv print_int10, 1
	.eqv read_string, 8
	
	.eqv size, 20
	.eqv sizemais1, 21
	
	.data
	
str:	.space sizemais1
	
	.text
	.globl main
	
main:	la $a0, str #read_string(str, SIZE)
	li $a1, size #res = 0
	li $v0, read_string
	syscall
	li $t0, 0 #num = 0
	li $t1, 0 #i = 0
	la $t2, str
	
while: 	addu $t3, $t2, $t1 #str + i = &str[i]
	lb $t4, 0($t3) #str[i]
	beq $t4, $0, ewhile
	blt $t4, '0', eif
	bgt $t4, '9', eif	
	addi $t0, $t0, 1
eif: 	addi $t1, $t1, 1
	j while
ewhile: move $a0, $t0
	li $v0, print_int10
	syscall 
	jr $ra
