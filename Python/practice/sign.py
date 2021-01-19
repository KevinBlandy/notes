from hashlib import md5
secret_key = '123456'
param = {
    "order":"123456",
    "senderPhone":"18524574185",
    "receiverPhone":"17455451254",
    "receiverName":"熊保宝",
    "remark":"这个是备注",
}
message = ""
for key in sorted(param.keys()):
    value = param.get(key)
    if value :
        message = message +  key + "=" + value + "&"
message += "code=" + secret_key
md5_digest = md5()
print("计算签名:%s" %(message))
md5_digest.update(bytes(message,'UTF_8'))
sign = md5_digest.hexdigest()
print("签名结果:%s" %(sign))