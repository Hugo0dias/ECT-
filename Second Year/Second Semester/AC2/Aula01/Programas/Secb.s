#cnt -> t0 
    .equ    IN_key, 1
    .equ    PUT_CHAR, 3
    .equ    printInt10, 6

    .data

Ponto:  .asciiz "."
    .text
    .globl    main

main:
    li  $t0, 0          #cnt = 0;
do:
    li    $v0,IN_key
    syscall             #v0 = IN_key
    move    $t1,$v0     #c = IN_key

if: beq $t1,'0', else

    move $a0, $t1
    li    $v0,PUT_CHAR
    syscall             #putChar( c );

else:

    la $a0, Ponto
    li    $v0,PUT_CHAR
    syscall             #putChar( "." );

endif:

    addiu    $t0,$t0,1  #cnt = 0
    bne    $t1,'\n', do

while:

    li    $v0, printInt10
    move   $a0, $t0
    li    $a1, 10
    syscall             #printInt(cnt, 10);
    li    $v0,0
    jr    $ra 