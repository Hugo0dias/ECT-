# Mapa de registos 
# p: $t0 
# pultimo:$t1 
# *p $t2 
# soma: $t3 
	.eqv read_string, 8
	
	.eqv print_int10, 1
 	.eqv SIZE,4 
	.eqv sizemais1, 21
	
 	.data 
array:	.word 7692,23,5,234
p:	.word 
 	
 	.text 
 	.globl main 
 	
main: 	li $t3,0 	# soma = 0; 
 	li $t4,SIZE 	# 
 	li $t6, 1
 	sub $t4,$t4,$t6 	# $t4 = 3 
 	sll $t4,$t4,2 	# ou "mul $t4,$t4,4" 
 	la $t0,p 	# p = array; 
 	addi $t5, $0, 1
 	sub $t5, $t4, $t5
 	addu $t1,$t0,$t5 # pultimo = array + SIZE - 1; 
while: 			# while(p <= pultimo) 
 	bgtu $t0,$t1,endw # { 
 	lb $t2,0($t0) 	# $t2 = *p; 
 	add $t3,$t3, $t2	# soma = soma + (*p); 	
 	addiu $t0,$t0,1 # p++; 
 	j while
 			# } 
endw:
	move $a0, $t0
	li $v0, print_int10
	syscall		# print_int10(soma); 
 	jr $ra 		# termina o programa 
 	
 	
 	