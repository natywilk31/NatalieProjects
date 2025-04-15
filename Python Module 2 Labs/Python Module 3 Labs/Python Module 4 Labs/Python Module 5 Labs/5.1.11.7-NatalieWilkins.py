##
## Natalie Wilkins 5.1.11.7
##

testWord = input("Please enter a word or phrase to determine if it is a palindrome:")

def is_palindrome(s):
    i = 0
    j = len(testWord) - 1
    while i < j:
        while testWord[i].isalnum is False and i < j:
            i += 1
        while testWord[j].isalnum is False and i < j:
            j -= 1
        if testWord[i].lower() != testWord[j].lower():
            print("It's not a palindrome")
            break
        i += 1
        j -= 1
    print("It's a palindrome")

is_palindrome(testWord)
        
