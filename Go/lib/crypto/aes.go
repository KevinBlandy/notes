-------------------
aes
-------------------
	# AES算法的实现

-------------------
var
-------------------
	# 秘钥大小
		const BlockSize = 16


-------------------
type
-------------------
	# type KeySizeError int
		func (k KeySizeError) Error() string

-------------------
func
-------------------
	func NewCipher(key []byte) (cipher.Block, error)
		* 根据秘钥(16、24或32个字节)返回一个 cipher
		* 会根据秘钥的长度返回AES-128, AES-192, or AES-256 的chipher
	

	
-------------------
aes的实现
-------------------
	# AES下各个模式(CBC/ECB/CFB)的加密解密实现
		import (
			"bytes"
			"crypto/aes"
			"crypto/cipher"
			"crypto/rand"
			"encoding/base64"
			"encoding/hex"
			"io"
			"log"
		)

		func main(){
			origData := []byte("Go语言") // 待加密的数据
			key := []byte("1234567899986596") // 加密的密钥

			log.Println("------------------ CBC模式 --------------------")
			encrypted := AesEncryptCBC(origData, key)
			log.Println("密文(hex)：", hex.EncodeToString(encrypted))
			log.Println("密文(base64)：", base64.StdEncoding.EncodeToString(encrypted))
			decrypted := AesDecryptCBC(encrypted, key)
			log.Println("解密结果：", string(decrypted))

			log.Println("------------------ ECB模式 --------------------")
			encrypted = AesEncryptECB(origData, key)
			log.Println("密文(hex)：", hex.EncodeToString(encrypted))
			log.Println("密文(base64)：", base64.StdEncoding.EncodeToString(encrypted))
			decrypted = AesDecryptECB(encrypted, key)
			log.Println("解密结果：", string(decrypted))

			log.Println("------------------ CFB模式 --------------------")
			encrypted = AesEncryptCFB(origData, key)
			log.Println("密文(hex)：", hex.EncodeToString(encrypted))
			log.Println("密文(base64)：", base64.StdEncoding.EncodeToString(encrypted))
			decrypted = AesDecryptCFB(encrypted, key)
			log.Println("解密结果：", string(decrypted))
		}
		// CBC 模式加密
		func AesEncryptCBC(origData []byte, key []byte) (encrypted []byte) {
			block, _ := aes.NewCipher(key)
			blockSize := block.BlockSize()                              // 获取秘钥块的长度
			origData = pkcs5Padding(origData, blockSize)                // 补全码
			blockMode := cipher.NewCBCEncrypter(block, key[:blockSize]) // 加密模式
			encrypted = make([]byte, len(origData))                     // 创建数组
			blockMode.CryptBlocks(encrypted, origData)                  // 加密
			return encrypted
		}

		// CBC 模式解密
		func AesDecryptCBC(encrypted []byte, key []byte) (decrypted []byte) {
			block, _ := aes.NewCipher(key)                              // 分组秘钥
			blockSize := block.BlockSize()                              // 获取秘钥块的长度
			blockMode := cipher.NewCBCDecrypter(block, key[:blockSize]) // 加密模式
			decrypted = make([]byte, len(encrypted))                    // 创建数组
			blockMode.CryptBlocks(decrypted, encrypted)                 // 解密
			decrypted = pkcs5UnPadding(decrypted)                       // 去除补全码
			return decrypted
		}
		func pkcs5Padding(cipherText []byte, blockSize int) []byte {
			padding := blockSize - len(cipherText)%blockSize
			padText := bytes.Repeat([]byte{byte(padding)}, padding)
			return append(cipherText, padText...)
		}
		func pkcs5UnPadding(origData []byte) []byte {
			length := len(origData)
			unpadding := int(origData[length-1])
			return origData[:(length - unpadding)]
		}

		// ECS模式加密
		func AesEncryptECB(origData []byte, key []byte) (encrypted []byte) {
			cipher, _ := aes.NewCipher(generateKey(key))
			length := (len(origData) + aes.BlockSize) / aes.BlockSize
			plain := make([]byte, length*aes.BlockSize)
			copy(plain, origData)
			pad := byte(len(plain) - len(origData))
			for i := len(origData); i < len(plain); i++ {
				plain[i] = pad
			}
			encrypted = make([]byte, len(plain))
			// 分组分块加密
			for bs, be := 0, cipher.BlockSize(); bs <= len(origData); bs, be = bs+cipher.BlockSize(), be+cipher.BlockSize() {
				cipher.Encrypt(encrypted[bs:be], plain[bs:be])
			}

			return encrypted
		}
		// ECB模式解密
		func AesDecryptECB(encrypted []byte, key []byte) (decrypted []byte) {
			cipher, _ := aes.NewCipher(generateKey(key))
			decrypted = make([]byte, len(encrypted))
			for bs, be := 0, cipher.BlockSize(); bs < len(encrypted); bs, be = bs+cipher.BlockSize(), be+cipher.BlockSize() {
				cipher.Decrypt(decrypted[bs:be], encrypted[bs:be])
			}
			trim := 0
			if len(decrypted) > 0 {
				trim = len(decrypted) - int(decrypted[len(decrypted)-1])
			}

			return decrypted[:trim]
		}

		func generateKey(key []byte) (genKey []byte) {
			genKey = make([]byte, 16)
			copy(genKey, key)
			for i := 16; i < len(key); {
				for j := 0; j < 16 && i < len(key); j, i = j+1, i+1 {
					genKey[j] ^= key[i]
				}
			}
			return genKey
		}

		// CFB模式加密
		func AesEncryptCFB(origData []byte, key []byte) (encrypted []byte) {
			block, err := aes.NewCipher(key)
			if err != nil {
				panic(err)
			}
			encrypted = make([]byte, aes.BlockSize+len(origData))
			iv := encrypted[:aes.BlockSize]
			if _, err := io.ReadFull(rand.Reader, iv); err != nil {
				panic(err)
			}
			stream := cipher.NewCFBEncrypter(block, iv)
			stream.XORKeyStream(encrypted[aes.BlockSize:], origData)
			return encrypted
		}
		// CFB模式解密
		func AesDecryptCFB(encrypted []byte, key []byte) (decrypted []byte) {
			block, _ := aes.NewCipher(key)
			if len(encrypted) < aes.BlockSize {
				panic("cipher text too short")
			}
			iv := encrypted[:aes.BlockSize]
			encrypted = encrypted[aes.BlockSize:]

			stream := cipher.NewCFBDecrypter(block, iv)
			stream.XORKeyStream(encrypted, encrypted)
			return encrypted
		}

	
	# 工具类
		package aes

		import (
			"bytes"
			"crypto/aes"
			"crypto/cipher"
			"crypto/rand"
			"io"
		)


		// EncryptCBC CBC 模式加密
		func EncryptCBC(origData []byte, key []byte) (encrypted []byte) {
			block, _ := aes.NewCipher(key)
			blockSize := block.BlockSize()                              // 获取秘钥块的长度
			origData = pkcs5Padding(origData, blockSize)                // 补全码
			blockMode := cipher.NewCBCEncrypter(block, key[:blockSize]) // 加密模式
			encrypted = make([]byte, len(origData))                     // 创建数组
			blockMode.CryptBlocks(encrypted, origData)                  // 加密
			return encrypted
		}

		// DecryptCBC CBC 模式解密
		func DecryptCBC(encrypted []byte, key []byte) (decrypted []byte) {
			block, _ := aes.NewCipher(key)                              // 分组秘钥
			blockSize := block.BlockSize()                              // 获取秘钥块的长度
			blockMode := cipher.NewCBCDecrypter(block, key[:blockSize]) // 加密模式
			decrypted = make([]byte, len(encrypted))                    // 创建数组
			blockMode.CryptBlocks(decrypted, encrypted)                 // 解密
			decrypted = pkcs5UnPadding(decrypted)                       // 去除补全码
			return decrypted
		}
		func pkcs5Padding(cipherText []byte, blockSize int) []byte {
			padding := blockSize - len(cipherText)%blockSize
			padText := bytes.Repeat([]byte{byte(padding)}, padding)
			return append(cipherText, padText...)
		}
		func pkcs5UnPadding(origData []byte) []byte {
			length := len(origData)
			unPadding := int(origData[length-1])
			return origData[:(length - unPadding)]
		}

		// EncryptECB ECS模式加密
		func EncryptECB(origData []byte, key []byte) (encrypted []byte) {
			cipher, _ := aes.NewCipher(generateKey(key))
			length := (len(origData) + aes.BlockSize) / aes.BlockSize
			plain := make([]byte, length*aes.BlockSize)
			copy(plain, origData)
			pad := byte(len(plain) - len(origData))
			for i := len(origData); i < len(plain); i++ {
				plain[i] = pad
			}
			encrypted = make([]byte, len(plain))
			// 分组分块加密
			for bs, be := 0, cipher.BlockSize(); bs <= len(origData); bs, be = bs+cipher.BlockSize(), be+cipher.BlockSize() {
				cipher.Encrypt(encrypted[bs:be], plain[bs:be])
			}

			return encrypted
		}
		// DecryptECB ECB模式解密
		func DecryptECB(encrypted []byte, key []byte) (decrypted []byte) {
			cipher, _ := aes.NewCipher(generateKey(key))
			decrypted = make([]byte, len(encrypted))
			for bs, be := 0, cipher.BlockSize(); bs < len(encrypted); bs, be = bs+cipher.BlockSize(), be+cipher.BlockSize() {
				cipher.Decrypt(decrypted[bs:be], encrypted[bs:be])
			}
			trim := 0
			if len(decrypted) > 0 {
				trim = len(decrypted) - int(decrypted[len(decrypted)-1])
			}

			return decrypted[:trim]
		}

		func generateKey(key []byte) (genKey []byte) {
			genKey = make([]byte, 16)
			copy(genKey, key)
			for i := 16; i < len(key); {
				for j := 0; j < 16 && i < len(key); j, i = j+1, i+1 {
					genKey[j] ^= key[i]
				}
			}
			return genKey
		}

		// EncryptCFB CFB模式加密
		func EncryptCFB(origData []byte, key []byte) (encrypted []byte) {
			block, err := aes.NewCipher(key)
			if err != nil {
				panic(err)
			}
			encrypted = make([]byte, aes.BlockSize+len(origData))
			iv := encrypted[:aes.BlockSize]
			if _, err := io.ReadFull(rand.Reader, iv); err != nil {
				panic(err)
			}
			stream := cipher.NewCFBEncrypter(block, iv)
			stream.XORKeyStream(encrypted[aes.BlockSize:], origData)
			return encrypted
		}
		// DecryptCFB CFB模式解密
		func DecryptCFB(encrypted []byte, key []byte) (decrypted []byte) {
			block, _ := aes.NewCipher(key)
			if len(encrypted) < aes.BlockSize {
				panic("cipher text too short")
			}
			iv := encrypted[:aes.BlockSize]
			encrypted = encrypted[aes.BlockSize:]

			stream := cipher.NewCFBDecrypter(block, iv)
			stream.XORKeyStream(encrypted, encrypted)
			return encrypted
		}
