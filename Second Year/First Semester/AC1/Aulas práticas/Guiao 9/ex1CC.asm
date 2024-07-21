	.eqv read_int, 5
	.eqv print_float, 2

	.data
k1:	.float 2.59375
k2:	.float 0.0

	.text
	.globl main
main:	

do:	li $v0, read_int
	syscall
	mtc1 $v0, $f6		#f6 = val
	cvt.s.w $f6, $f6	# float (val)
	la $t0, k1
	l.s $f4, 0($t0)		#f4 = 2.5...
	mul.s $f12, $f4, $f6	# res
	li $v0, print_float
	syscall

while:	la $t1, k2
	l.s $f8, 0($t1)
	c.eq.s $f8, $f12	# true
	bc1f do			# se for falso da branch	
	jr $ra 