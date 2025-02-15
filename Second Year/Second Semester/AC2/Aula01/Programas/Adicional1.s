    .equ printInt, 6
    .equ putChar, 3
    .equ Inkey, 1
    .equ UP, 1
    .equ DOWN, 0

    .data

str1:   .asciiz "/r"
str2:   .asciiz "/t"

    .text
    .globl main
    
main:   addiu $sp, $sp, -4
        sw $ra, 0($sp)
        li $t0, 0   #state = 0
        li $t1, 0   #cnt = 0

do:     la $a0, str1
        li $v0, putChar
        syscall             #putChar(/r)

        move $a0, $t1
        li $t2, 3
        sll $t2, $t2, 16
        li $t3, 10
        or $t3, $t3, $t2
        move $a1, $t3
        li $v0, printInt
        syscall             #printInt(cnt, 10 | 3 << 16)

        la $a0, str2
        li $v0, putChar
        syscall             #putChar(/t)

        move $t1, $a0
        li $t2, 8
        sll $t2, $t2, 16
        li $t3, 2
        or $t3, $t3, $t2
        move $a1, $t3
        li $v0, printInt
        syscall             #printInt(cnt, 2 | 8 << 16)


        li $t2, 5
        move $a0, $t2
        jal wait            #wait(5)

        li $v0, Inkey
        syscall
        move $t2, $v0       # c = $t2

if1:    bne $t2, '+', else1 # if(c == +)
        li $t0, UP 

else:
if2:    bne $t2, '-', else2 # if(c == -)
        li $t0, DOWN

else2:
if3:    bne $t0, UP, else3  # if(state == UP)
        addi $t1, $t1, 1
        andi $t1, $t1, 0xFF # cnt = (cnt + 1) & 0xFF


else3:  li $t3, 1
        sub $t1, $t1, $t3
        andi $t1, $t1, 0xFF # cnt = (cnt - 1) & 0xFF

while:  bne $t2, 'q', do    # while(c != 'q')

        li $v0, 0

        lw $ra, 0($sp)
        addiu $sp, $sp, 4
        
        jr $ra

    .text

wait:   
        move $t0, $a0          #$t0 = ts
        li $t1, 1               #i = 0
        mul $t2, $t0, 515000    #ts*515000

for:    bge $t0, $t2, out_for   # for()
        addi $t1, $t1, 1       # i++
    
out_for:    jr $ra