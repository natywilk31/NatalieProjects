##
## Natalie Wilkins 5.1.11.9
##
dob = input("Please enter your date of birth in YYYYMMDD format:")
totalDOB = 0
for x in dob:
    if x.isdigit:
        totalDOB += int(x)
    else:
        print("Please enter only numbers.")
total2 = 0
for x in str(totalDOB):
    total2 += int(x)
    if len(str(totalDOB)) == 1:
        break
    else:
        totalDOB = total2
print(total2)    




##input a string of numbers
##for each number in the string, add it to the total
