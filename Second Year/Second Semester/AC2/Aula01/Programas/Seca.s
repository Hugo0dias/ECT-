#cnt -> t0 
    .equ    GET_CHAR, 2
    .equ    PUT_CHAR, 3
    .equ    printInt10, 6

    .data
    .text
    .globl    main

main:
    li  $t0, 0          #cnt = 0;
do:
    li    $v0,GET_CHAR
    syscall             #v0 = getChar
    move    $t1,$v0     #c = getchar

    move $a0, $t1
    li    $v0,PUT_CHAR
    syscall             #putChar( c );

    addiu    $t0,$t0,1  #cnt = 0
    bne    $t1,'\n', do
while:
    li    $v0, printInt10
    move   $a0, $t0
    li    $a1, 10
    syscall             #printInt(cnt, 10);
    li    $v0,0
    jr    $ra 