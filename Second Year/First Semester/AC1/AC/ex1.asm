#typedef struct
#{				Align	Size	Offset
#	char id;		1	1	0
#	double av;		8	8	1 -> 8
#	int ns;			4	4	16
#	char smp[18];		1	18	20
#	int sum;		4	4	38 -> 40
#} t_sample;			8	44

# Mapa de registos
# ts	:  
# nval	:
# sum	: $f2
# n	: $t5
# k	: $t4
# acc	: $t0
# pu	: $t3
# av 	: $f8


	.data
D1:	.double 0.0
	.text

process:
	li $t0, 0	#acc = 0
	la $t1, D1
	l.d $f2, 0(D1)

	mul	$t3,$a1,44	#	nval * 40	
	addu	$t3,$a0,$t3	#	pu = ts + nval

for1:	bge $a0, $t3, outfor1	# ts >= pu
	li $t4, 0		#k = 0
	lw $t5, 16($a0)		# ns

for2:	bge $t2, $t5, endfor2	# branch if k >= ns
	addi $t6, $a0, 20	# calcula o endereco de smp na estrutura
	lb $t7, 0($t6)		# vai buscar o char
	add $t6, $t6, $t2	# acc = smp[k]
	add $t2, $t2, 1		# k++
	j for2:

endfor2:
	sw $t6, 40($a0)		# sum = acc
	mtc1 $t6, $f6		# f6 = acc
	cvt.d.w $f6, $f6	# double(acc)
	mtc1 $t5, $f4		# f4 = ns
	cvt.d.w $f4, $f4	# double(ns)
	div.d $f6, $f6, $f4	# av = acc / ns
	s.d $f6, 8($a0)
	add.d $f2, $f2, $f6	# sum += av
	j for1:

outfor1:
	cvt.d.s $f0, $f2
	jr $ra 