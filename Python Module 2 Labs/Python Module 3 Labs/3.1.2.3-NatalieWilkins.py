    ##
## 3.1.2.3 Natalie Wilkins
##
secret_number = 777

print("""
+================================+
| Welcome to my game, muggle!    |
| Enter an integer number        |
| and guess what number I've     |
| picked for you.                |
| So, what is the secret number? |
+================================+
""")
guess = int(input("Please guess a number:"))
while guess != secret_number:
    print("Ha ha! You're stuck in my loop!")
    guess = int(input("Guess again:"))
else:
    print("Well done, muggle! You are free now.")    

