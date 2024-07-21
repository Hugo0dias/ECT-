# mapa de registos
# argc -> $s0
# fla -> $s1
# i -> $t0
# p -> $t1
# argv -> $a1 -> $s2
	.data
	.align 2
fla:	.space	80
str1:	.asciiz "Invalid argc"
	.eqv	SIZE,20
	.eqv	print_float,2
	.eqv	print_string,4
	.text
print:	
	addiu	$sp,$sp,-16
	sw	$ra,0($sp)
	sw	$s0,4($sp)	#	argc
	sw	$s1,8($sp)	#	fla
	sw	$s2,12($sp)	#	argv
	move	$s2,$a1	
	move	$s0,$a0		
	
	la	$s1,fla		#	endereço do array fla
	move	$t1,$s1		#	*p = fla
if:
	ble	$s0,1,else	#	argc > 1
	bgt	$s0,SIZE,else	#	argc <= SIZE
	li	$t0,0		#	i = 0
for:
	bge	$t0,$s0,endfor	#	i < argc
	
	addu	$t3,$s2,$t0	#	&argv[i]
	lb	$t4,0($t3)	#	argv[i]
	move	$a0,$t4
	li	$a1,10
	jal	tof
	s.s	$f0,0($t1)	#	*p = tof(argv[i],10)
	
	addi	$t0,$t0,1	#	i++
	addiu	$t1,$t1,4	#	p++
	j	for
endfor:	
	move	$a0,$s1		
	move	$a1,$s0
	jal	aver
	mov.s 	$f12,$f0
	li	$v0,print_float
	syscall
	j	endif
else:
	la	$a0,str1
	li	$v0,print_string
	syscall
endif:
	move	$v0,$s1	
	lw	$s2,12($sp)	
	lw	$s1,8($sp)
	lw	$s0,4($sp)
	lw	$ra,0($sp)
	addiu	$sp,$sp,16
	jr	$ra
