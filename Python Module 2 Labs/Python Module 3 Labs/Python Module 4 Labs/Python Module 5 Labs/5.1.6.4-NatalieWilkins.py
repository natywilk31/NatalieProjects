##
## Natalie Wilkins 5.1.6.4
##

def readint(prompt, min, max):

    while True:
        try:
            value = int(input(prompt))
        except ValueError:
            print("Error: wrong input")
        
        if value < min or value > max:
            print("Error: the value is not within permitted range (min..max)")
        else:
            return value
            
v = readint("Enter a number from -10 to 10: ", -10, 10)

print("The number is:", v)
