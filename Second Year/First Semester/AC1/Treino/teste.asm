
	.data

texto: .asciiz "Eu sou eu"
	
	.text
	.globl main
	
main:		li $v0, 4
		la $a0, texto
		syscall
		