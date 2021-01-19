def encode_hex(bins):
    return ''.join([hex(i).replace('0x','').zfill(2).upper() for i in bins])
print(encode_hex(bytes.fromhex('F1581856')))