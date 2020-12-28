       .data
stringmsg: .asciiz "Please input a string:\n"
success: .asciiz "\nSuccess! Location:"
array:  .space 132
fail: .asciiz "\nFail!\n"
       .text
       la   $a0, stringmsg
       li   $v0, 4
       syscall
       la $a0, array
       move $t1, $a0
       li $a1, 80
       li $v0, 8
       syscall
loop:  li   $v0, 12
       syscall
       beq  $v0, 63, end   # Exit program when input is '?'
       move $a1, $v0
lookup:li $t0, 0
       lb $a0, array($t0)
lplook: beq $a0, $a1, found
       add $t0, $t0, 1
       lb $a0, array($t0)
       bne $a0, 10, lplook
nfound:la  $a0, fail
       li  $v0, 4
       syscall
       j  loop
found: la  $a0, success
       li  $v0, 4
       syscall
       add  $a0, $t0, 1
       li  $v0, 1
       syscall
       li  $a0, 10
       li  $v0, 11
       syscall
       j  loop
end:   li  $v0, 10
       syscall
