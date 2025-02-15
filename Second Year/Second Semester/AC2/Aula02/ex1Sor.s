    .equ READ_CORE_TIMER,11
    .equ RESET_CORE_TIMER,12
    .equ PUT_CHAR,3
    .equ PRINT_INT,6
    .data
    .text
    .globl main

main:   addiu $sp, $sp, -8
        sw $t0, -4($sp)
        sw $ra, 0($sp)

        li $s0, 0
        li $v0, RESET_CORE_TIMER
        syscall

lab1:   move $a0, $s0
        li $a1, 10
        li $v0, PRINT_INT
        syscall

        li $t0, '\r'
        move $a0, $t0
        li $v0, PUT_CHAR
        syscall

        li $a0, 100000000000000000

        jal delay

lab2:   li $v0, READ_CORE_TIMER
        syscall
        bleu $v0, 20000, lab2

        li $v0, READ_CORE_TIMER
        syscall
        addiu $s0, $s0, 1
        j lab1

        lw $t0, 4($sp)
        lw $ra, 0($sp)
        addiu $sp, $sp, 8
        li $v0, 0

        jr $ra



        .text

delay:  
        li $v0, RESET_CORE_TIMER
        syscall
while:  li $v0, READ_CORE_TIMER
        syscall
        move $t0, $v0
        mul $a0, $a0, 20000
        blt $t0, $a0, while

        jr $ra