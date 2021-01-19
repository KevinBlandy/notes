[ CA_default ]
dir             = /etc/pki/CA             # 定义路径变量
certs           = $dir/certs              # 已颁发证书的保存目录
database        = $dir/index.txt          # 数据库索引文件
new_certs_dir   = $dir/newcerts           # 新签署的证书保存目录
certificate     = $dir/cacert.pem         # CA证书路径名
serial          = $dir/serial             # 当前证书序列号
private_key     = $dir/private/cakey.pem  # CA的私钥路径名