	.eqv print_int10, 1
	.text

strlen: li $v0, 0
while:	lb $t0,0($a0)
	addi $a0, $a0, 1
	beq $t0, 0, ewhile
	addi $v0, $v0, 1
	j while
	
ewhile:	jr $ra

	.data
	
str:	.asciiz "Arquitetura de computadores 1"
	.text
	.globl main

main:	addiu $sp, $sp, -4
	sw $ra,0($sp)
	
	la $a0, str
	jal strlen
	move $a0, $v0
	move $a0, $v0
	li $v0, print_int10
	syscall 
	li $v0, 0
	
	lw $ra,0($sp)
	
	
# Mapa de registos:
# str: $a0 -> $s0 (argumento é passado em $a0)
# p1: $s1 (registo callee-saved)
# p2: $s2 (registo callee-saved)
#
strrev:	addiu $sp,$sp,-16 # reserva espaço na stack
 	sw $ra,0($sp) # guarda endereço de retorno
 	sw $s0,4($sp) # guarda valor dos registos
 	sw $s1,8($sp) # $s0, $s1 e $s2
 	sw $s2,12($sp) #
 	move $s0,$a0 # registo "callee-saved"
 	move $s1,$a0 # p1 = str
 	move $s2,$a0 # p2 = str
 	
while1: # while( *p2 != '\0' ) {
	lb $t0,0($a2)
	beq $t0,0,ewhil
	addiu $s2, $s2, 1
 	j while1 # }
 		
ewhil: addiu $s2, $s2, -1
while2: bge $s1, $s2, ewhil2
	 	
 	move $a0,$s1 #
 	move $a1,$s2 #
 	jal exchange # exchange(p1,p2)
 	addiu $s1, $s1, 1
 	addiu $s2, $s2, -1
 	j while2 # }
 	
ewhil2:	move $v0,$s0 # return str
 	
 	lw $ra,0($sp) # repõe endereço de retorno
 	lw $s0,4($sp) # repõe o valor dos registos
 	lw $s1,8($sp)# $s0, $s1 e $s2
 	lw $s2,12($sp) #
 	addiu $sp,$sp,16 # liberta espaço da stack
 	jr $ra # termina a sub-rotina 
 	
 	.text
 	
exchange:
 	lb $t0, 0($a0)
 	lb $t1,0($a1)
 	sb $t1, 0($a1)
 	sb $t0, 0($a1)
 	jr $ra
 