 .eqv print_string,4 
 .eqv read_int, 5
 .data 
str1: .asciiz "Introduza um numero: " 
str2: .asciiz "Numero" 
str3: .asciiz "A soma dos positivos é " 


 .text
 .globl main
 
main: 
	li $t2, 0 # i = 0
	li $t0, 0 #soma = 0

for: 	bge $t2, 5, endfor # while(i<5) {
	la $a0, str1 # print_string("Introduza um numero: ")
	li $v0, print_string 
	syscall 
	li $v0, read_int # value = read_int();
	syscall
	move $t1, $v0
#falta read int
	ble $t1, 0, else # if (value > 0)
	add $t0, $t0, $t1 # soma += value;
	j eif

else: 	la $a0, str2 #else : print string "valor ignorado"
	li $v0, print_string
	syscall 

eif: 
if:
	j endif

endif:


	addi $t2, $t2, 1
	j for
	
endfor:

# soma: $t0 
# value: $t1 
# i: $t2 
