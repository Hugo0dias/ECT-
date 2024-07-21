# Mapa de registos 
# val	:
# sum	: $f8
# k	: $t1
# fx	: $t0
# array	: $a1
# aux	: f6


	.data	
D1:	.double 0.0
D2:	.double 0.035

	.text

calc:	li $t0, 1	# fx = 1
	li $t1, 0	# k = 0
	la $t9, D1	
	l.d $f8, 0($t9) # sum = 0.0

do:	addi $t3, $t1, 1
	mul $t0, $t0, $t1	# fx = fx * (k+1)
	
	mtc1 $t0, $f2
	cvt.d.w $f2, $f2	# double(fx)
	
	mul $t4, $t1, 8		# k*8
	add $a0, $a0, $t4	# &array[k]
	l.d $f4, 0($a0)		# array[k]
	div.d $f6, $f4, $f2	# divisao
	
	add.d $f8, $f8, $f6	# sum += aux
	s.d $f4, 0($f8)
	addi $t1, $t1, 1

while:	la $t8, D2
	l.d $f10, 0($t8)
	c.le.d $f6, $f10
	bc1f do

out:	cvt.w.d $f8, $f8
	mtc1 $t5, $f8
	move $v0, $t5
	jr $ra
	

	


