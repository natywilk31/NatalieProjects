##
##Natalie Wilkins 4.1.3.7
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
    
testYears = [1900, 2000, 2016, 1581, 2020]
testMonths = [2, 2, 1, 11, 13]
testResults = [28, 29, 31, None, None]
for i in range(len(testYears)):
	yr = testYears[i]
	mo = testMonths[i]
	print(yr, mo, "->", end="")
	result = daysInMonth(yr, mo)
	if result == testResults[i]:
		print("OK")
	else:
		print("Failed")


