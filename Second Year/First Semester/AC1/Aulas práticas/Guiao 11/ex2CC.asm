	
	.eqv print_float, 2
	.eqv a1, 0
	.eqv g, 16
	.eqv a2, 24
	.eqv v, 36
	.eqv k, 40 

	.data
stg:	.asciiz  "St1"
	.space 6
	.align 3
	.double 3.141592653589
	.word 291, 756
	.asciiz  "X"
	.align 2
	.float 1.983

	.text
	.globl main

main:
	addiu $sp, $sp, -4
	sw $ra, 0($sp)
	jal f1
	mov.d $f12, $f0
	li $v0, print_float
	syscall 
	lw $ra, 0($sp)
	addiu $sp, $sp, 4
	jr $ra

	.text

f1:	la $t0, stg	# s1 = $t0
	l.d $f2, g($t0)	# double g = $f2
	
	lw $t1, a2($t0)
	addi $t1, $t1, 4	# int &a2[1] = $t1
	
	mtc1 $t1, $f2
	cvt.d.w $f2, $f2
	
	mul.d $f2, $f2, $f4	# s1.g * (double)s1.a2[1]
	
	l.s $f6, k($t0)		# k = $f6
	cvt.d.s $f6, $f6	# k = double($f6)

	div.d $f2, $f2, $f6	# resultado do return

	cvt.s.d $f0, $f2	# float do resultado
	
	jr $ra


	