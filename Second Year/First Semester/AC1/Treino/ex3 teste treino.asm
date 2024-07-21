	.eqv print_int10, 1
	.eqv N, 35
	.eqv read_int, 5 
	
	.data
	
listA:	.space 20
	.align 2
listB:	.space 20
	.align 2
	
	
	.text
	
#mapa de registos:
# Mapa de registos:
# n_even: t0
# n_odd: t1
#p1 : t2
#p2: t3
#a+N : $t5


	.globl main
	
main:	li $t0, 0	#t0 = 0
	li $t1, 0	#t1 = 0
	
	la $t2, listA
	la $t3, listB
	li $t4, N
	sll $t4, $t4, 2		#transformar N num adress multiplo de 4 para somar a lista A
	
for:	addu $t5, $t2, $t4	# a+N
	bge $t2, $t5, efor	# se p1 >= a + n goto efor
	li $v0, read_int	# 
	syscall 
	sw $v0, 0($t2)		# p1 = readint
	addi $t2, $t2, 1	# p1++
	j for
efor:	
	la $t2, listA
	la $t3, listB
	
for1:	
	bge $t2, $t5, efor1
	lw $t6,0($t2)		# val(p1)
	li $t7, 0		# var --> p1%2
	remu $t7 ,$t6, 2	# $t7 = p1%2
	
if:
	bne $t7,$0,eif
	sw $t6, 0($t3)		# p2 = p1
	addi $t1, $t1, 1	# n_odd ++
	addi $t3, $t3, 4	# p2++
	addi $t2, $t2, 4	# p1++
	j for2
		
eif:	
	addi $t0, $t0, 1	# n_even++
	addi $t2, $t2, 4	# p1++
	j for2
	
efor1:	
	la $t3, listB		# p2 = listB
	sll $t1, $t1, 2		# n_even = 4*i
	addu $t8, $t3, $t1	# p2 + n_even
	
for2:	
	bge $t3, $t8, efor2	
	li $v0, print_int10	
	move $a0, $t3		# a0 = p2
	syscall 
	addi $t3, $t3, 1

efor2:	jr $ra
	
	
	
	
	
	
	
	
	