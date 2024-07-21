	.text
	
sqrt:	la $t1, um
	l.d $f4, 0($t1)		#xn = 1.0
	li $t0, 0		#i = 0
	la $t1, zero
	l.d $f0, 0($t1)	
	c.le.d $f12, $f0	# if val > 0.0
	bc1t e1_sq	
	
do_sq:	mov.d $f2, $f4		#aux = xn
	div.d $f14, $f12, $f4	#val/xn
	add.d $f14, $f4, $f14	#xn + val/xn
	la $t1, meio
	l.d $f16, 0($t1)
	mul.d $f4, $f16, $f14	#xn = 0.5 * (xn + val/xn);
	c.eq.d $f2, $f4
	bc1t end_dosq		#acaba do se aux == xn
	addi $t0, $t0, 1	#i++ esta ++i, logo e antes da comparaçao
	li $t1, 25		#podia estar noutro lado mas nao estamos para otimizar
	bge $t0, $t1, end_dosq	# acaba se i>=25
	j do_aq
	
end_dosq:
	j end_ifsq
e1_sq:
	la $t1, zero
	l.d $f4, 0($t1)		#xn = 0.0
end_ifsq:
	mov.d $f0, $f4		#return xn

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	.text
	.globl main
	
main:
	addiu $sp, $sp, -4
	sw $ra