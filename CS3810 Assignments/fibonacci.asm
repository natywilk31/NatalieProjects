# This code has a bug somewhere and I can't figure it out, but I left very descriptive comments
# so hopefully you can see where I'm trying to go with it (and maybe give me partial credit).

#procedure Fib(N)
#if (N == 0) return 0
#if (N == 1) return 1
#return Fib(N-1) + Fib(N-2)

#An example run of the program:
#Enter an integer greater than zero:
#7
#Element 7 of the Fibonacci sequence is: 13

.data
prompt: .asciiz "Enter an integer greater than zero: "
	.align 2
firstOutputPrompt: .asciiz "Element "
secondOutputPrompt: .asciiz " of the Fibonacci sequence is: "


.text

# Print prompt
    la $a0, prompt
    li $v0, 4 # tell it what we're doing
    syscall
    
    # Read user input
    li $v0, 5 # tell it what we're doing
    syscall
    move $s0, $v0  # Store user input in $s0
    move $s5, $v0 # store the user input in a long term register to print at end
	move $a0, $s0
	jal Fibonacci # call fibonacci

Fibonacci:

 	# Save currently used registers on the stack
    	addi $sp, $sp, -8
	sw $ra, 0($sp)
    	sw $a0, 4($sp)
    	
    # Check base cases (N == 0 or N == 1)
    li $t0, 0     
    bne $a0, $t0, notZero
    li $v0, 0  # if it is 0, then we return 0
    j end_fibonacci # we hit our base case, ready to go to end
    
notZero:
    li $t0, 1 # test other base case: N is 1
    bne $a0, $t0, notOne
    li $v0, 1  # if it is 1, then we return 1
    j end_fibonacci # now that we hit the 1, we can go to the end
    
notOne:
    # Recursive call to Fib(N-1)
    addi $a0, $a0, -1 # decrease the input
    jal Fibonacci # jump to fibonacci with the current
    move $s1, $v0  # Store result in $s1
    
    # Recursive call to Fib(N-2)
    addi $a0, $a0, -1 # decrease the input 
    jal Fibonacci # call fibonacci with the current
    move $s2, $v0  # Store result in $s2
    
    add $v0, $s1, $s2 # store it in return value $v0
    
end_fibonacci:
    # Restore registers and go to endPrint
    lw $a0, 4($sp)
    lw $ra, 0($sp)
    addi $sp, $sp, 8
    j endPrint
    
endPrint:

   # Print result
   la $a0, firstOutputPrompt
   li $v0, 4 # tell it what we're doing
   syscall
    
   move $a0, $s5 # Move initial to $a0 for printing
   li $v0, 1
   syscall
   
   la $a0, secondOutputPrompt
   li $v0, 4
   syscall
   
   move $a0, $v0
   li $v0, 1
   syscall
   
    
    # Exit program
    li $v0, 10
    syscall








