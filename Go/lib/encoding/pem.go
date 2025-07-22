
-------------------
pem
-------------------


-------------------
var
-------------------


-------------------
type
-------------------
	# type Block struct {
			Type    string            // 类型，取自序言（也就是顶端----中声明的类型）
			Headers map[string]string // 可选的Headr
			Bytes   []byte            // 内容的解码字节。通常是一个DER编码的ASN.1结构。
		}
		
		func Decode(data []byte) (p *Block, rest []byte)
			* 对一段儿RSAKYE进行解码

-------------------
func
-------------------
	func Encode(out io.Writer, b *Block) error
	func EncodeToMemory(b *Block) []byte
		* 对block进行编码，编码为 pem 类型字符串


-------------------
Demo
-------------------
	# 编码和解码 RSA 密钥对
	
		// 私钥
		privateKey, err := rsa.GenerateKey(rand.Reader, 2048)
		if err != nil {
			panic(err)
		}
		// 公钥
		publicKey := &privateKey.PublicKey

		privateKeyPem := bytes.NewBuffer(nil)
		publicKeyPem := bytes.NewBuffer(nil)

		// 编码私钥为 PEM
		err = pem.Encode(privateKeyPem, &pem.Block{
			Type:  "RSA Private Key",
			Bytes: x509.MarshalPKCS1PrivateKey(privateKey),
		})
		if err != nil {
			panic(err)
		}

		// 编码公钥为 PEM
		err = pem.Encode(publicKeyPem, &pem.Block{
			Type:  "RSA Public Key",
			Bytes: x509.MarshalPKCS1PublicKey(publicKey),
		})
		if err != nil {
			panic(err)
		}

		fmt.Println(privateKeyPem)
		fmt.Println(publicKeyPem)

		// 解码私钥/公钥
		privateBlock, _ := pem.Decode(privateKeyPem.Bytes())
		publicBlock, _ := pem.Decode(publicKeyPem.Bytes())

		// 解析私钥
		privateKey, err = x509.ParsePKCS1PrivateKey(privateBlock.Bytes)
		if err != nil {
			panic(err)
		}

		// 解析公钥
		publicKey, err = x509.ParsePKCS1PublicKey(publicBlock.Bytes)
		if err != nil {
			panic(err)
		}

		content := "朝花夕拾"

		// 公钥加密
		cipher, err := rsa.EncryptPKCS1v15(rand.Reader, publicKey, []byte(content))
		if err != nil {
			panic(err)
		}
		fmt.Printf("encoded %s\n", hex.EncodeToString(cipher))

		// 私钥解密
		raw, err := rsa.DecryptPKCS1v15(rand.Reader, privateKey, cipher)
		if err != nil {
			panic(err)
		}
		fmt.Printf("decoded %s\n", string(raw))