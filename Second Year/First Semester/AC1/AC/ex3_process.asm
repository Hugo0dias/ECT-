
#typedef struct
#{			Align	Size	Offset
#	char id;	  1      1        0
#	double av;	  8	 8	  1 -> 8
#	int ns;	 	  4 	 4	  16
#	char smp[18];	  1 	 18	  20
#	int sum;	  4	 4	  38 -> 40
# ----------------------------------------------------
#} t_sample;	          8	 44

# mapa de registos
# ts:	$a0
# nval:	$a1
# sum: $f2
# n: $t1
# k: $t0
# acc: $t2
# pu: $t3
# ns: $t4
	.data
D1:	.double 0.0
	.text

process:
	la	$t9,D1
	l.d	$f2,0($t9)	#	double sum = 0.0
	
	li	$t2,0		#	acc = 0
	mul	$t3,$a1,44	#	nval * 40	
	addu	$t3,$a0,$t3	#	pu = ts + nval
for1:
	bge	$a0,$t3,endfor1	#	ts < pu
	li	$t0,0		#	k = 0
	lw	$t4,16($a0)	#	ns
for2:
	bge	$t0,$t4,endfor2	#	k < ns
	addiu	$t5,$a0,20	#	
	addu	$t5,$t5,$t0	#	endereço do primeiro char
	lb	$t6,0($t5)	#	$t6 -> smp[k]
	add	$t2,$t2,$t6	#	acc += smp[k]
	addi	$t0,$t0,1	#	k++
	j	for2
endfor2:
	sw	$t2,40($a0)	#	ts.sum = acc
	mtc1	$t4,$f4
	cvt.d.w	$f4,$f4		#	(double) ns
	mtc1	$t2,$f6
	cvt.d.w	$f6,$f6		#	(double) acc
	div.d	$f6,$f6,$f4	#	(double) acc / (double) ns
	s.d	$f6,8($a0)	#	av
	add.d	$f2,$f2,$f6	#	sum += ts.av
	
	addi	$a0,$a0,44	#	ts++
	j	for1
endfor1:
	cvt.s.d	$f0,$f2		#	return (float)sum
	jr	$ra
