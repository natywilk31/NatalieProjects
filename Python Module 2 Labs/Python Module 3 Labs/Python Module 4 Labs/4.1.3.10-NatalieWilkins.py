##
## Natalie Wilkins 4.1.3.10
##
milesIn100Km = 100 / 1.609344
def l100kmtompg(liters):
    gallons = liters / 3.785411784
    mpg = milesIn100Km / gallons
    return mpg

def mpgtol100km(miles):
    km = miles * 1.609344
    liters = 3.785411784
    lkm = liters / km
    l100km = lkm * 100
    return l100km

print(l100kmtompg(3.9))
print(l100kmtompg(7.5))
print(l100kmtompg(10.))
print(mpgtol100km(60.3))
print(mpgtol100km(31.4))
print(mpgtol100km(23.5))

