import hashlib
md5 = hashlib.md5()
md5.update(b'javaweb.io')
print(md5.hexdigest())   #0a473d1c44fbccb8f58c7401c5f04a3d