##
##Natalie Wilkins 4.1.3.8
##
def isYearLeap(year):# add if year < 1582 return false	
    if year < 1582:
        return False
    elif year % 400 == 0:
        return True
    elif year % 100 == 0:
        return False
    elif year % 4 == 0:
        return True
    else:
        return False
def daysInMonth(year, month):
    numberDaysInMonth = [31,28,31,30,31,30,31,31,30,31,30,31]
    if month > 12 or month < 1:
        return None
    elif year < 1582:
        return None
    elif isYearLeap(year) is True and month == 2:
        return 29
    else:
        return numberDaysInMonth[month - 1]

def dayOfYear(year, month, day):
    if year < 1582 or month < 1 or month > 12:
        print("Either the year or the month is invalid!")
        return None
    if day < 1 or day > daysInMonth(year,month):
        print("The day is invalid!")
        return None
    totalNumberDays = day
    month -= 1
    while month > 0:
        totalNumberDays += daysInMonth(year,month)
        month -=1
    return totalNumberDays    

print(dayOfYear(2001, 1, 31))
