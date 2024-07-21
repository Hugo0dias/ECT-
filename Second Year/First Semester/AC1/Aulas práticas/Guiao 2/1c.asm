	.data 
 	.text 
 	.globl main 
main: 	ori $t0,$0,0xE543 # substituir val_1 e val_2 pelos 
 	nor $t1, $t0,$t0
 	jr $ra # fim do programa
 	
 	