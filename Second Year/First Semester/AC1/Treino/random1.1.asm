	
	.eqv SIZE,10
	.eqv read_int, 5
	.eqv print_int10, 1
	.eqv print_string, 4
	
	.data
str1:	.asciiz ":::::::"
array:	.space 40
	.align 2
string:	.asciiz "Numero: "
	
	.text
	
# tabela de registos
# t0 : i
# t1: valor
# t2: &array
	
	.globl main
	
main:	li $t0, 1		# i = 0
	la $t2, array		# &array
	
do:	la $a0, string
	li $v0, print_string	# printf("Numero : ")
	syscall
	
	li $v0, read_int
	syscall
	
	move $t1, $v0		# valor = numero escolhido
	
	sw $t1, 0($t2)		# array[i] = valor
	
	addi $t2, $t2, 4	# add &array + 4
	
	addi $t0, $t0, 1	# i++
	
while:	bge $t0, SIZE, out
	
	j do
	
out:	li $t0, 1
	la $t2, array
	
for:	bge $t0, SIZE, outfor
	
if:	ble $t0, $0, else
	addi $t3, $t3, 5	# t3 = 5
eif:	ble $t0, $t3, else
	lw $t4, 0($t2)		# t4 = array(0)
	sll $t4, $t4, 2		# t4 << 2
	
	move $a0, $t4
	li $v0, print_int10
	syscall 
	
	addi $t2, $t2, 4
	addi $t0, $t0, 1
	
	j for
				
else:	
	lw $t4, 0($t2)
	move $a0, $t4
	li $v0, print_int10
	syscall 
	addi $t2, $t2, 4
	addi $t0, $t0, 1
	
	j for
	
outfor:	jr $ra






	
	
	
	