
	.eqv read_double,7
	.eqv print_double,3	
	.eqv SIZE,3

	.data

a:	.space 80
x:	.double 0.0

	.text
	.globl main

main:	addiu $sp, $sp, -4
	sw $ra, 0($sp)
	la $t1, a		# &a
	li $t0, 0		# i = $t0

forr:	bge $t0, SIZE, endforr
	sll $t2, $t0, 3 
	addu $t3, $t1, $t2
	
	li $v0, read_double
	syscall

	mtc1 $v0, $f2
	cvt.d.w $f4, $f2		# $f4 = valor lido em double	
	s.d $f4, 0($t3)
	
	addi $t0, $t0, 1
	j for

endforr:	la $a0, a
	li $a1, SIZE
	jal average
	mov.d $f12, $f0	
	li $v0, 3
	syscall

	lw $ra, 4($sp)
	addiu	$sp, $sp, 4		
	li	$v0, 0			#	return 0;
	jr	$ra	


	.text

average:		la $t2, x	
		move $t0, $a0		# array 
		move $t1, $a1		# n $t1 = i
		l.d $f0, 0($t2)		# transforma o 0.0 em double

for:		blt $t1, $0, endfor
		addi $t3, $t1, -1	# t3 = n-1
		sll $t3, $t3, 3		# shift left, porque e um aarray de doubles
		addu $t4, $t3, $t0
		l.d $f2, 0($t4)		
		add.d $f0, $f0, $f2
		addi $t1, $t1, -1
		j for

endfor:		mtc1 $a1, $f2
		cvt.d.w $f2, $f2
		div.d $f0, $f0, $f2
		jr $ra





