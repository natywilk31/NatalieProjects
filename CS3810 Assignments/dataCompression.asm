# This program will perform a form of data compression. 
# Example:
# Input: AAAAAAAAABBCCC
# Output: A9B2C3
# Ratio: 2.333


.data
prompt: .asciiz "Provide an input string with less than 50 characters and only containing a-z or A-Z: " 
compressedPrint: .asciiz "The compressed string is: "
		.align 0
outputArray: 	.space 50
inputArray:	.space 80 # leave extra space because we have a different check for inputs that are too big
# variable for invalid input string
invalidPrint: .asciiz "Invalid input"
compressionRatio: .asciiz "The compression ratio is "
newLine: .asciiz "\n"


.text

# Print prompt
la $a0, prompt # address of string to print
li $v0, 4 # tell compiler what we're doing
syscall

la $a0, inputArray # address to store string at
li $a1, 80 # maximum number of chars 
li $v0, 8 # tell compiler what we're doing
syscall

move $s0, $a0 # put the inputarray into $s0
la $s5, outputArray # output array address into two spots: one for the address,
la $s4, outputArray # one for data manipulation

#currentCount
addi $s1, $zero, 0
#currentChar
addi $s2, $zero, 0

lb  $s2, 0($s0) # load the current character
beq $s2, 32, End
#previousChar will be in $s3
addi $s0, $s0, 1

#startCount will be in $s6
addi $s6, $zero, 0

j currentCheck # make sure alphabet characters each time

charLoop:
	
	
	beq $s6, 52, invalidEnd # if array is too big
	move $s3, $s2 # put current into previous
	lb  $s2, 0($s0) # get new current
	
	blt $s2, 65, notMatch # non alphabet
	bgt $s2, 122, notMatch # non alphabet
	
	
	addi $s6, $s6, 1 # increment count of the string we start with
	bne $s2, $s3, notMatch # if they're not equal, we have a special procedure
	beq $s1, 9, nineCounter # if we're at 9, we need to do something special
	addi $s0, $s0, 1 # otherwise, just add to the current count
	j currentCheck # do the check again
	
currentCheck:

	beq $s2, 10, End # if new line, skip to end
	blt $s2, 65, invalidEnd # invalid character
	bgt $s2, 122, invalidEnd
	
	addi $s1, $s1, 1
	j charLoop		
	
notMatch:
	
	
	beq $s1, 1, oneCounter
	beq $s1, 0, notMatch0
	
	sb $s3, 0($s5)      # Store the compressed character
    	addi $s5, $s5, 1    # Increment the output buffer pointer
    	addi $s1, $s1, 48
    	sb $s1, 0($s5)      # Store the count
    	addi $s5, $s5, 1    # Increment the output buffer pointer
    	addi $s0, $s0, 1
	addi $s1, $zero, 0
	
	
	j currentCheck
	

notMatch0:
	
	addi $s1, $s1, 1
	addi $s0, $s0, 1
	j currentCheck

nineCounter:
	
	blt $s2, 65, notMatch
	bgt $s2, 122, notMatch
	sb $s3, 0($s5)
	addi $s5, $s5, 1
	addi $s1, $s1, 48
	sb  $s1, 0($s5)
	addi $s1, $zero, 0
	addi $s5, $s5, 1
	addi $s0, $s0, 1
	
	j currentCheck
	

oneCounter:

	blt $s2, 65, invalidEnd
	bgt $s2, 122, invalidEnd
	sb $s3, 0($s5)
	addi $s5, $s5, 1
	addi $s0, $s0, 1
	addi $s1, $zero, 0
	
	j currentCheck

End:
	beq $s1, 1, finalOne
	addi $s6, $s6, 1
	# Print prompt
	la $a0, compressedPrint # address of string to print
	li $v0, 4 # what is this??
	syscall
	
	beq $s1, 0, printLoop
	sb $s3, 0($s5)
	addi $s5, $s5, 1
	addi $s1, $s1, 48
	sb $s1, 0($s5)
	
	
	
	j printLoop
	
finalOne:
	sb $s3, 0($s5)	
	
printLoop:
	
	
	
	lb $t1, 0($s4)
	beq $t1, 0, Exit
	beq $t1, 12, End
	beq $t1, 32, End
	
	addi $s7, $s7, 1 # store end count
	lb $a0, 0($s4)
	li $v0, 11
	syscall
	
	addi $s4, $s4, 1
	j printLoop
	
	
	
	
	j Exit
	
	# convert starting size to float
	# convert ending size to float
	# print out compression rate string
	
	
	#figure out float of starting / ending size
	#print print string and the ratio compression number
	
invalidEnd:

	# Print prompt
	la $a0, invalidPrint # address of string to print
	li $v0, 4 
	syscall
	j Done

Exit:
	la $a0, newLine
	li $v0, 4
	syscall
	
	la $a0, compressionRatio
	li $v0, 4
	syscall
	
	mtc1 $s6, $f0
	cvt.s.w $f0, $f0
	mtc1 $s7, $f1
	cvt.s.w $f1, $f1
	
	div.s $f2, $f0, $f1
	
	mov.s $f12, $f2
	li $v0, 2
	syscall

Done:	
 # Exit program
    li $v0, 10
    syscall
	
	
