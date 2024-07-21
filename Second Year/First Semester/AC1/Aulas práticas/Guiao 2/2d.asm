	.eqv bin, 4
	.eqv gray
	
 	.data 
 	
 	.text 
 	.globl main 
 	
main: 	
 	ori $t0, $0, 2
 	sll $t1, $t0, 1
 	and $t2, $t0, $t1
 	jr $ra # fim do programa