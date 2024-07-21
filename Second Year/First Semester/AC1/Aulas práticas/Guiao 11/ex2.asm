	
	.text
f1:	la $t0, uvw_s1
	l.d $f2, uvw_g($t0)
	la $t0, uvw_s1
	addiu $t0, $t0, uvw_a2
	li $t1, 1
	sll $t1, $t1, 2
	addu $t0, $t0, $t1
	lw $t1, 0($t0)
	mtc1 $t1, $f4
	cvt.d.w $f4, $f4
	mul.d $f2, $f2, $f4
	la $t0, uvw_s1
	l.s $f4, uvw_k($t0)
	...
	
	
	
	.data
	.align 3
uvw:_s1:	.asciiz "St1"
		.space 6
		.double
		.word 291
		.word 756
		.byte "x"
		.float 1.983
		
		
	.text

f1:	la $t0, uvw_s1
	l.d $f20, uvw_g($t0)
	la $t0, uvw_s1
	addiu $t0, $t0, uvw_a2
	li $t1, 1
	sll $t1, $t1, 2
	addu $t0, $t0, $t1
	lw $t1, 0($t0)
	mtc1 $t1, $f22
	cvt.d.w $f22, $f22
	mul.d $f20, $f20, $f22
	la $t0, uvw_s1
	l.s $f22, uvw_k($t0)
	cvt.d.s $f22, $f22
	...