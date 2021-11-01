
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
	# 解析公钥
		var PublicKey = []byte(
		`-----BEGIN PUBLIC KEY-----
		MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAlRuRnThUjU8/prwYxbty
		WPT9pURI3lbsKMiB6Fn/VHOKE13p4D8xgOCADpdRagdT6n4etr9atzDKUSvpMtR3
		CP5noNc97WiNCggBjVWhs7szEe8ugyqF23XwpHQ6uV1LKH50m92MbOWfCtjU9p/x
		qhNpQQ1AZhqNy5Gevap5k8XzRmjSldNAFZMY7Yv3Gi+nyCwGwpVtBUwhuLzgNFK/
		yDtw2WcWmUU7NuC8Q6MWvPebxVtCfVp/iQU6q60yyt6aGOBkhAX0LpKAEhKidixY
		nP9PNVBvxgu3XZ4P36gZV6+ummKdBVnc3NqwBLu5+CcdRdusmHPHd5pHf4/38Z3/
		6qU2a/fPvWzceVTEgZ47QjFMTCTmCwNt29cvi7zZeQzjtwQgn4ipN9NibRH/Ax/q
		TbIzHfrJ1xa2RteWSdFjwtxi9C20HUkjXSeI4YlzQMH0fPX6KCE7aVePTOnB69I/
		a9/q96DiXZajwlpq3wFctrs1oXqBp5DVrCIj8hU2wNgB7LtQ1mCtsYz//heai0K9
		PhE4X6hiE0YmeAZjR0uHl8M/5aW9xCoJ72+12kKpWAa0SFRWLy6FejNYCYpkupVJ
		yecLk/4L1W0l6jQQZnWErXZYe0PNFcmwGXy1Rep83kfBRNKRy5tvocalLlwXLdUk
		AIU+2GKjyT3iMuzZxxFxPFMCAwEAAQ==
		-----END PUBLIC KEY-----
		`)

		func main() {

			block, rest := pem.Decode(PublicKey)
			if block == nil || block.Type != "PUBLIC KEY" {
				log.Fatal("公钥解析失败")
			}

			pub, err := x509.ParsePKIXPublicKey(block.Bytes)
			if err != nil {
				log.Fatal(err)
			}

			fmt.Printf("Got a %T, with remaining data: %q", pub, rest)
		}
	
	# 编码
		privateKey, err := rsa.GenerateKey(rand.Reader, 512)
		if err != nil {
			log.Fatalf("rsa.GenerateKey Error: err=%s\n", err.Error())
		}

		publicKey := &privateKey.PublicKey

		// 编码私钥
		pem.Encode(os.Stdout, &pem.Block {
			Type:    "RSA Private Key",
			Bytes:   x509.MarshalPKCS1PrivateKey(privateKey),
		})

		// 编码公钥
		pem.Encode(os.Stdout, &pem.Block {
			Type:    "RSA Public Key",
			Bytes:   x509.MarshalPKCS1PublicKey(publicKey),
		})