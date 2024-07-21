xtoy:	addiu $sp, $sp, -20
	sw $ra, 0($sp)
	sw $s0, 4($sp)
	sw $s1, 8($sp)
	s.s $f20, 12($sp)
	s.s $f22, 16($sp)
	mov.s $f20, $f12
	li $s0, 0
	la $t3, um
	l.s $f22, 0($t3)
	move $s1, $a0
	jal abs
	move $t1, $v0
	
forxtoy:	bge $s0, $t1, endfxt
		blez $s1, elsext
		mul.s $f22, $f22, $f20
		j xtl
		
elsext:	div.s $f22, $f22, $f20
fim_if:	addi $s0, $s0, 1
	j forxtoy
	
endfxt:	mov.s $f0, $f22
	l.s $f22, 16($sp)
	l.s $f20, 12($sp)
	lw $s1, 8($sp)
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
main:
	
	