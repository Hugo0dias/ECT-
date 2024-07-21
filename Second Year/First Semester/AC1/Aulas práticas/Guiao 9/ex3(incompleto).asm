	.text
	
average:	move $t0, $a1
		addi $t0, $t0, -1
		mtc1 $0, $f2
		cvt.d.w $f2, $f2
		
foravg:		blt $t0, $0, endforavg
		move $t1, $t0
		sll $t1, $t1, 3
		move $t2, $a0
		addu $t2, $t2, $t1
		l.d $f4, 0($t2)
		add.d $f2, $f2, $f4
		addi $t0, $t0, -1
		j foravg
		
endforavg:	mtc1 $a1, $f4
		cvt.d.w $f4, $f4
		div.d $f0, $f2, $f4
		jr $ra