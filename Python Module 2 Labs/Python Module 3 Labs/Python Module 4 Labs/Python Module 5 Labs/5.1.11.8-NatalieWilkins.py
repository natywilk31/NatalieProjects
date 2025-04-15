##
## Natalie Wilkins 5.1.11.8
##
word1 = input("Please enter the first word to test:")
word2 = input("Please enter the second word to test:")
word1up = word1.upper()
word2up = word2.upper()
word1upsort = sorted(word1up)
word2upsort = sorted(word2up)

if word1upsort == word2upsort:
    print("Your words are anagrams!")
else:
    print("Your words are not anagrams.")
