	.data
	
	.text
	.globl main
	
itoa:
	sw $s0, 0($sp)
	sw $s1, 4($sp)
	sw $s2, 8($sp)
	sw $s3, 12($sp)
	sw $ra, 16($sp)
	
	move $s0, $a0
	move $s1, $a1
	move $s2, $a2
	move $s3, $a3


do:	rem $t0, $s0, $s1
	div $s0, $s0, $s1
	move $a0, $t0
	jal toascii
	sb $v0, 0($s3)
	addiu $s3, $s3, 1
	bgt $s0, 0, do
	sb $0, 0($s3)
	move $a0, $s2
	jal strrev
	move $v0, $s2
	
	lw $s0, 0($sp)
	lw $s1, 4($sp)
	lw $s2, 8($sp)
	lw $s3, 12($sp)
	lw $ra, 16($sp)
	
	
	