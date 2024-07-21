# mapa de registos
# val: $f12
# array: $t2
# fx: $t0
# k: $t1
# sum: $f2
# aux: $f4
# array[k]: $f6
	.data
D1:	.double 0.0
D2:	.double 0.035
	.text
calc:	
	move	$t2,$a0
	li	$t0,1		#	fx = 1
	li	$t1,0		#	k = 0
	la	$t9,D1
	l.d	$f2,0($t9)	#	sum = 0.0
do:
	addi	$t3,$t1,1	#	k + 1
	mul	$t0,$t0,$t3	#	fx * (k + 1)
	
	mul	$t4,$t1,8	#	k * 8
	addu	$t4,$t4,$t2	#	&array[k]
	l.d	$f6,0($t4)	#	array[k]
	
	mtc1	$t0,$f8
	cvt.d.w	$f8,$f8		#	(double) fx
	
	div.d	$f4,$f6,$f8	#	aux = array[k] / (double) fx
	add.d	$f2,$f2,$f4	#	sum  = sum + aux
	
	s.d	$f2,0($t4)	#	array[k] = sum
	addi	$t1,$t1,1	#	k++
while:	
	la	$t9,D2	
	l.d	$f10,0($t9)	#	0.035
	c.le.d	$f4,$f10	
	bc1f	do		#	aux > 0.035
endw:
	cvt.w.d	$f2,$f2
	mfc1	$t8,$f2		# (int)sum
	move	$v0,$t8		#	return (int)sum
	jr	$ra