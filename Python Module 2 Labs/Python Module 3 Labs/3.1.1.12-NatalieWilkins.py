income = float(input("Enter the annual income: "))

if income == 0:
    tax = 0
elif income <= 85528:
    tax = (income*.18) - 556.2
elif income > 85528:
    tax = 14839.02 + .32*(income-85528)
else:
    tax = 0
tax = round(tax, 0)
if tax <= 0:
    tax = 0
print("The tax is:", tax, "thalers")
