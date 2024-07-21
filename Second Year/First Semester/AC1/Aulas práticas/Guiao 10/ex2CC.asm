	.data
xn:	.double 1.0
x1:	.double 0.5
x2:	.double 0.0

	.text

# Mapa de Registos
# $f12 - val
# $f2 - aux
# $f4 - xn
# $f6 = 0.5
# $t0 - i

sqrt:	la $t0, xn
	l.d $f4, 0($t0)	# $f4 = xn = 1.0
	la $t0, x2
	l.d $f8, 0($t0)
	la $t0, x1
	l.d $f6, 0($t0)
	li $t0, 1	# i = 1
	
	
	
if:	c.le.d $f12, $f8		# compara se eles  sao menor ou igual
	bc1t else

do:	mov.d $f2, $f4
	div.d $f4, $f12, $f2
	add.d $f4, $f4, $f2
	mul.d $f4, $f6, $f4

while:	c.eq.d $f2, $f4
	bc1t endif

andd:	addi $t0, $t0, 1
	blt $t0, 25, do
	j endif

else:	l.d $f4, x2

endif:	mov.d $f0, $f4
	jr $ra	











	