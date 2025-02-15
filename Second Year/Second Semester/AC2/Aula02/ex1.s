    .equ READ_CORE_TIMER,11
    .equ RESET_CORE_TIMER,12
    .equ PUT_CHAR,3
    .equ PRINT_INT,6
    .data
char:   .asciiz '/r'
    .text
    .globl main

main:   addiu $sp, $sp, -4
        sw $ra, 0($sp)
        li $t0,0                # counter=0
while:                          #while (1) {
        la $a0, char
        li $v0, PUT_CHAR
        syscall
        li $t1, 10
        li $t2, 4
        sll $t2, 16
        or $t3, $t1, $t2
        la $a0, $t0
        la $a1, $t3
        li $v0, PRINT_INT
        syscall

        li $v0,RESET_CORE_TIMER         #
        syscall                         # resetCoreTimer()

whileRead:  li $v0, READ_CORE_TIMER
            syscall
            move $a0, $t4
            li $t5, 10
            blt $t4, $t5, whileRead
            addiu $t0, $t0, 1


        j while                         # }

        lw $ra, 0($sp)
        addiu $sp, $sp, 4

        jr $ra                          #