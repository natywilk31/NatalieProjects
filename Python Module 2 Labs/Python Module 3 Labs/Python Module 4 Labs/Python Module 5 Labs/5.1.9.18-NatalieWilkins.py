##
## Natalie Wilkins 5.1.9.18
##

def mysplit(strng):
    if strng == "" or strng.isspace():
        return []
    lst = []
    word = ""
    inword = not strng[0].isspace()
    for x in strng:
        if inword:
            if not x.isspace():
                word += x
            else:
                lst.append(word)
                inword = False
        else:
            if not x.isspace():
                inword = True
                word = x
            else:
                pass
    if inword:
        lst.append(word)
    return lst

print(mysplit("To be or not to be, that is the question"))
print(mysplit("To be or not to be,that is the question"))
print(mysplit("   "))
print(mysplit(" abc "))
print(mysplit(""))
