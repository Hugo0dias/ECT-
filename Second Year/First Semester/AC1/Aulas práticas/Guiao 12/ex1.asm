


################################### read_data ##############################################
read_data:			# recebe em a0 - ponteiro para o inicio do array de estrutura
	move $t0, $a0
	move $t1, $a1
	li $t2, 0		# i = 0
	
f_rd:	bge $t2, $t1, end_f_rd	# i < ns
	la $a0, str1		#le id
	li $v0, print_string	
	syscall
	li $t3, sizest		# $t3 = sizeof(student)
	mulu $t4, $t3, $t2	# $t4 = i * sizeof(student)
	addu $t5, $t0, $t4	# $t5 = endereco inicial da estrutura 
	li $v0, read_integer	# $t5 = $st_arr[i]
	syscall
	sw $v0, stidof($t5)
	
	la $a0, str2		# le fn
	li $v0, print_string
	syscall
	li $t3, sizest		# $t3 = sizeof(student)
	mulu $t4, $t3, $t2	# $t4 = i * sizeof(student)
	addu $t5, $t0, $t4	# $t5 = endereco inicial da estrutura  
	addiu $a0, $t5, stfnot
	li $a1, stfnmx
	li $v0, read_string
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
######################################### max ############################################

max:

	la $t3, max_grade
	l.s $f2, 0($t3)		# float max_grade = -20.0
	mtc1 $0, $f4		# float sum = 0.0
	cvt.s.w $f4, $f4	
	move $t0, $t0		# p=st ponteiro para principio do array de s
	li $t3, sizest		# sizeof(student)
	mul $t3, $t3, $a1	# estamos a trabalhar com ponteiros por isso
	addu $t2, $t0, $t3	# st + ns (na pratica e st + sizest * ns)
f_max	bge $t0, $t2, end_fx	# for (p = st) ...
	l.s $f6, stgrof($t0)	# uso offset a partir de p para carregar p-> grade
	add.s $f4, $f4, $f6	# sum += p -> grade
	c.le.s $f6, $f2		# if ....
	bc1t end_if_max		
	mov.s $f2, $f6		# max_grade = p -> grade
	move $t1, $t0		# pmax = p
end_if_max
	addiu $t0, $t0, sizest # p++
	j f_mx
end_fmx
	mtc1 $a1, $f8		
	cvt.s.w $f8, $f8	# (float) ns
	div.s $f4, $f4, $f8	# sum / (float)ns