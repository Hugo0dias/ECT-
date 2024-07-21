	.text
	.globl main
	
toascii:
	addi $v0,$a0,'0' # v += '0'
	ble $v0, '9', end # if (v>0) ...
	addi $v0, $v0, 7
end:	jr $ra