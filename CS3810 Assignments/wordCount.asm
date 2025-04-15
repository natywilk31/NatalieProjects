# Given user input, this program will output the word count of that input. 

.data

# variable for our prompt string
prompt: .asciiz "Enter your prompt (max 80 chars): " 
endPrint: .asciiz "Word count: "


# allocate space for the word array	
		.align 0		# byte-align the 80 byte space declared next
wordArray:	.space 80		# allocate 80 bytes as a read buffer for string input


.text

# Print prompt
la $a0, prompt # address of string to print
li $v0, 4 # tell compiler we're printing string
syscall

la $a0, wordArray # address to store string at
li $a1, 80 # maximum number of chars (including '\0')
li $v0, 8 # tell compiler we want to get string input
syscall

move $s0, $a0 # put the wordarray into $s0

# create register for word count, flag register for startedWord
#$t0 will hold wordCount, $t1 will hold flag
addi $t0, $zero, 0
addi $t1, $zero, 0


Loop1:
	lb  $t5, 0($s0) # load the current character
	
	# first check every time should be if we have the null character - 
	# that will be a beq to the null character, then send to exit
	beq $t5, 0, finalWordCheck # if we encounter the null character, 
	beq $t5, 10, finalWordCheck # new line character also counts as null
	
	bgt $t5, 96, lowerCaseCheck # if it was not an uppercase, we'll do the lowercase check on it
	
	# check the uppercase letters, 64 - 90
	bgt $t5, 90, nonAlphabet # go to nonalphabet loop
	
	blt $t5, 65, nonAlphabet # it's 0-64, non alphabet
	
	addi $t1, $zero, 1 # if we avoid all the nonalphabet parts, we are an uppercase letter, set wordStarted to true
	addi $s0, $s0, 1 # iterate to next spot in array
	
	j Loop1 # repeat the loop
		
lowerCaseCheck: # bounds are 97 - 122
	
	# if the current is greater than 122, it's not alphabet
	bgt $t5, 122, nonAlphabet
	
	# otherwise we have a valid letter
	addi $t1, $zero, 1 # startedWord is true
	addi $s0, $s0, 1 # iterate to next char
	j Loop1
		
nonAlphabet: 
	
	addi $s0, $s0, 1
	beq $t1, 0, Loop1 # if we haven't started a word, we don't care about this
	addi $t1,  $zero, 0 # otherwise we will set wordStarted back to false
	addi $t0, $t0, 1 # and increment our word count
	j Loop1
	
finalWordCheck: 
	# check if word had started, if yes, add one more to word count
	beq $t1, 0, Exit # if flag is false, we don't care
	addi $t0, $t0, 1 # increment word count

Exit: 

	la $a0, endPrint # print out Word Count: 
	li $v0, 4 # tell compiler we're printing a string
	syscall
	
	#la $a0, 0($t0) # print out word count
	move $a0, $t0
	li $v0, 1 # tell compiler what we're printing
	syscall
	
	# Exit program
   	li $v0, 10
    	syscall
	
	



