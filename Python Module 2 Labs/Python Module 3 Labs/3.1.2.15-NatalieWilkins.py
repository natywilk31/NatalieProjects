##
## 3.1.2.15 Natalie Wilkins
##
counter = 0
c0 = int(input("Please enter a positive integer: "))
print(c0)
while c0 != 1 and c0 > 0:
    c0 = int(c0)
    print(c0)
    if c0 % 2 == 0:
        c0 = c0 / 2
        counter += 1
    else: 
        c0 = 3 * c0 + 1
        counter += 1
    
print("Your final number is: " , int(c0))
print("The number of steps taken is: " , counter)
