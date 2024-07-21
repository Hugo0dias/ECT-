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
	move $t0, $v0
	la $t1, k1
	l.s $f2, 0($t1)
	mtc1 $t0, $f0
	cvt.s.w $f0, $f0
	mul.s $f2, $f0, $f2
	mov.s $f12, $f2
	li $v0, print_float
	syscall
	la $t1, k2
	l.s $f4, 0($t1)
	c.eq.s $f4, $f2
 	bc1t fim
 	j do
 	
 	
fim:	li $v0, 0
 	jr $ra