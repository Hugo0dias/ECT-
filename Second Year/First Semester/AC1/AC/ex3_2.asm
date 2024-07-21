# typedef struct
# { 					Alignment Size Offset
# char smp[10]; 				    1	   10	0	
# double av; 				    8	    8   16
# int ns; 				    4	    4   24
# char id; 				    1	    1   28
# int sum; 				    4	    4   32

# } t_cell; 

	.data
zero:	.double 0.0
val:	.double 3.597
	.text
	.globl prcells

prcells: la	$t0,zero
	l.d	$f0,0($t0)	#res = 0.0
	li	$t0,0		#i=0
	la	$t1,val
	l.d	$f6,0($t1)	#$f6 = 3.597

for:	bge	$t0,$a1,endfor
	addu	$t6,$a0,$t0	#$t6 = &tc[i]
	lw	$t7,24($t6)	#$t7 = tc[i].ns
	mtc1	$t7,$f2
	cvt.d.w	$f2,$f2		# $2 = double tc[i].ns
	div.d	$f4,$f2,$f6	#aux = double tc[i].ns / 3.597
	add.d	$f0,$f0,$f4	#res=res+aux
	s.d	$f4,16($6)	#tc[i].av
	cvt.w.d 	$f0,$f0	
	mfc1	$t8,$f0         #int res
	sw	$t8,32($t6)	#tc[i].sum= int res
	
	addi	$t0,$t0,1	#i++
endfor:	
	jr	$ra	
	


