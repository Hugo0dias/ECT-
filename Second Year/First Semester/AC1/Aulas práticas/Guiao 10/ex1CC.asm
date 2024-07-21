#ex 1 : Guiao 10

	.data
x0:	.double 1.0
	.text
	.globl xtoy

# Mapa de Registos
# $s0 - i
# $s1 - y
# $f20 - result
# $f22 - x

xtoy:	
	addiu	$sp, $sp, -28		# 	poem espaco na pilha
	sw	$ra, 0($sp)		#	guarda o $ra
	sw	$s0, 4($sp)		#	guarda o $s0
	sw	$s1, 8($sp)		#	guarda o $s1
	s.d	$f20, 12($sp)		#	guarda o $f20
	s.d	$f22, 20($sp)		#	guarda o $f22

	li $s0, 1			# result = 0.0
	la $t0, x0
	l.d $f20, 0($t0)

#	move $s1, $a1
#	mov.d  	$f22, $f12		#	$f22 = x
#	move	$a0, $s1

	jal abs
	move $t1, $v0

for:	bge $s0, $t1, endfor
if1:	ble $t1, $0, endif1
	mul.d $f20, $f20, $f22
	j outif
endif1:	div.d $f20, $f20, $f22
	
	
outif:	addi $s0, $s0, 1
	j for

endfor:	mov.d $f0, $f20
	lw	$ra, 0($sp)		#	guarda o $ra
	lw	$s0, 4($sp)		#	guarda o $s0
	lw	$s1, 8($sp)		#	guarda o $s1
	l.d	$f20, 12($sp)		#	guarda o $f20
	l.d	$f22, 20($sp)		#	guarda o $f22
	addiu	$sp, $sp, 28		# 	poem espaco na pilha


	jr $ra




