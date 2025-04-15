##
## 3.1.6.9 Natalie Wilkins
##

myList = [1, 2, 4, 4, 1, 4, 2, 6, 2, 9]
newList = []
for number in myList:
    if number not in newList:
        newList.append(number)
    else:
        del(number)
    
myList = newList[:]
print("The list with unique elements only:")
print(myList)

