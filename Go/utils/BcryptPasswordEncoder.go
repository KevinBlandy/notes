package main

import (
	"crypto/rand"
	"crypto/sha256"
	"encoding/base64"
	"errors"
	"fmt"
	"log"
)


// bcryptPasswordEncoder 类似于Spring的密码加密实现
type bcryptPasswordEncoder struct {

	// 密钥长度
	keySize int
}

func NewBCryptPasswordEncoder(keySize int) *bcryptPasswordEncoder {
	if keySize < 1 {
		panic(errors.New("keySize must bigger than 0"))
	}
	return &bcryptPasswordEncoder{keySize: keySize}
}

// Encode 对密码进行hash加密
func (b bcryptPasswordEncoder) Encode (password string) (string, error) {
	if password == "" {
		return "", errors.New("password is empty")
	}

	// 随机密钥，作为盐
	key := make([]byte, b.keySize)
	if _, err := rand.Read(key); err != nil {
		return "", err
	}

	return b.encode(key, password), nil
}

func (b bcryptPasswordEncoder) encode(key []byte, password string) string {

	hash := sha256.New()
	hash.Write(key)
	hash.Write([]byte(password))

	// 计算出 盐 + 密码的 sha256
	result := hash.Sum(make([]byte, 0, 512))

	// 转换为base64
	return base64.StdEncoding.EncodeToString(append(key, result...))
}

// Matches 判断密码是否匹配chipper
func (b bcryptPasswordEncoder) Matches (password string, chipper string) (bool, error){
	if password == "" || chipper == ""{
		return false, errors.New("password or chipper is empty")
	}

	result, err := base64.StdEncoding.DecodeString(chipper)
	if err != nil {
		return false, err
	}

	// TODO result 可能不足16个字节，需要容错处理
	return b.encode(result[:b.keySize], password) == chipper, nil
}


func main(){

	var password = "大青蛙呱呱呱"

	var encoder = NewBCryptPasswordEncoder(50)

	chipper, err := encoder.Encode(password)
	if err != nil {
		log.Fatalf("err=%s\n", err.Error())
	}
	fmt.Printf("password=%s, chipper=%s\n", password, chipper)

	match, err := encoder.Matches(password, chipper)
	if err != nil {
		log.Fatalf("err=%s\n", err.Error())
	}
	fmt.Printf("match=%v\n", match)
}