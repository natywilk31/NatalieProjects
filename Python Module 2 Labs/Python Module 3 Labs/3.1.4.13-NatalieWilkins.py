##
## 3.1.4.13 Natalie Wilkins
##

beatles = []
print("Step 1:", beatles)
beatles.append("John Lennon")
beatles.append("Paul McCartney")
beatles.append("George Harrison")
print("Step 2:", beatles)
for names in range(2):
    name = input("Please enter the new name: ")
    beatles.append(name)
print("Step 3:", beatles)
del beatles[-1]
del beatles[-1]
print("Step 4:", beatles)
beatles.insert(0,"Ringo Starr")
print("Step 5:", beatles)
print("The Fab", len(beatles))
