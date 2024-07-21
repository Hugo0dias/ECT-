	.data

Pi:	.double 7.202
	
	.text 
	.globl main

main:	
	li $v0, 3
	ldc1 $f12, Pi($t2)
	syscall 