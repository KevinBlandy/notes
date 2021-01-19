

def getBinary(number):
    bin = None
    bin = number % 2
    if number >= 2:
        getBinary(number / 2)
    print("%s" % ('1' if bin > 0 else '0'), end='')


getBinary(6)
