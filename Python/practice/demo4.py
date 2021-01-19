
def hex_to_bytes(hex_str):
    assert len(hex_str) %  2 == 0,"滚"
    bins = bytearray()
    for i,v in enumerate(hex_str):
        if (i + 1) % 2 == 0:
            bins.append(int(hex_str[i - 1] + hex_str[i],16))
    return bins


result = hex_to_bytes("FFFE")

print(result)

print(bytes.fromhex("FFFE"))