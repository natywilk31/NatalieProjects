##
## 3.1.2.11 Natalie Wilkins
##
wordWithoutVowels = ""
userWord = input("Please enter a word: ")
userWord = userWord.upper()

for letter in userWord:
    if letter == "A" or letter == "E" or letter == "I" or letter == "O" or letter == "U":
        continue
    else:
        wordWithoutVowels = wordWithoutVowels + letter
        
if wordWithoutVowels == "":
    print("Your word consisted of only vowels.")
else:
    print("This is your world without vowels: ", wordWithoutVowels )
