##
## Natalie Wilkins 4.1.3.9
##
upperLimit = int(input("Please choose your upper limit:"))
def isPrime(num):
    for i in range(2,num):
        if num % i == 0:
            return False
    return True    
        
for i in range(1, upperLimit):
	if isPrime(i+1):
			print(i+1, end=" ")
print()
print("These are the prime numbers for the given range.")

