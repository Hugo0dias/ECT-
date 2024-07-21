# Mapa de registos
# argc	: $s0
# argv	: $s2
# i	: $t1
# p	: $t0

	.eqv	print_float,2
	.eqv	print_string,4
	.eqv SIZE, 20
	.data
str1:	.asciiz "Invalid argc"
fla:	.space 80

	.text

print:	addiu	$sp,$sp,-16
	sw	$ra,0($sp)
	sw	$s0,4($sp)	#	argc
	sw	$s1,8($sp)	#	fla
	sw	$s2,12($sp)	#	argv

	move $s0, $a0		# s0 = argc
	move $s2, $a1		# s2 = argv
	
	la $s1, fla
	move $t0, $s1		# *p = fla

if:	ble $s0, 1, else	# if condition
	bgt $s2, SIZE, else	# if condition
	li $t1, 1		# i = 0
	
for:	bge $t1, $s0, end_for 	# for condition
	
	la $t2, $a1		# &argv
	add $t3, $t2, $t1	# &argv[i]
	lb $t4, 0($t3)		# $t4 = argv[i]
	move $a0, $t4
	li $a1, 10
	jal tof

	s.s $f0, 0($t0) 
	addi $t1, $t1, 1
	addi $t0, $t0, #4

	j for

end_for:
	move $a0, $s1
	move $a1, $s0
	jal aver
	mov.s $f12,$f0
	li $v0, print_float
	syscall
	j out
else:	
	la $a0, str1
	li $v0, print_string
	syscall
	

out:	move $v0, $s1
	lw	$ra,0($sp)
	lw	$s0,4($sp)	#	argc
	lw	$s1,8($sp)	#	fla
	lw	$s2,12($sp)	#	argv
	addiu	$sp,$sp,16
	jr $ra



