##
## 5.1.11.10 Natalie Wilkins
##
wordOne = input("Please enter your first word:")
wordTwo = input("Please enter your second word:")
counter = 0
lengthOne = len(wordOne)
for x in wordOne:
    if x in wordTwo:
        counter += 1
    else:
        print("No, your first word is not in your second word.")
        break
    if counter == lengthOne:
        print("Yes, your first word is in your second word.")
    else:
        continue
   
    
    

