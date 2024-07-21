
#typedef struct
#{			Align	Size	Offset
#	char id;	  1      1        0
#	double av;	  8	 8	  1 -> 8
#	int ns;	 	  4 	 4	  16
#	char smp[18];	  1 	 18	  20
#	int sum;	  4	 4	  38 -> 40
# ----------------------------------------------------
#} t_sample;	          8	 44

# mapa de registos
# ts:	$a0
# nval:	$a1
# sum: $f2
# n: $t1
# k: $t0
# acc: $t2
# pu: $t3
# ns: $t4