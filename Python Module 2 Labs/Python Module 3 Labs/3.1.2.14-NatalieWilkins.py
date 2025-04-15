##
## 3.1.2.14 Natalie Wilkins
##
height = 0
blocks = int(input("Enter the number of blocks: "))
needed=1
while True:
   #needed = needed + height
    if blocks >= needed:
        height += 1
        blocks = blocks-needed
        needed = height + 1
    else:
        break


print("The height of the pyramid:", height)
