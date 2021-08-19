package main

import (
	"crypto/rand"
	"crypto/sha256"
	"encoding/base64"
	"errors"
	"fmt"
	"log"
)


// bcryptPasswordEncoder ������Spring���������ʵ��
type bcryptPasswordEncoder struct {

	// ��Կ����
	keySize int
}

func NewBCryptPasswordEncoder(keySize int) *bcryptPasswordEncoder {
	if keySize < 1 {
		panic(errors.New("keySize must bigger than 0"))
	}
	return &bcryptPasswordEncoder{keySize: keySize}
}

// Encode ���������hash����
func (b bcryptPasswordEncoder) Encode (password string) (string, error) {
	if password == "" {
		return "", errors.New("password is empty")
	}

	// �����Կ����Ϊ��
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

	// ����� �� + ����� sha256
	result := hash.Sum(make([]byte, 0, 512))

	// ת��Ϊbase64
	return base64.StdEncoding.EncodeToString(append(key, result...))
}

// Matches �ж������Ƿ�ƥ��chipper
func (b bcryptPasswordEncoder) Matches (password string, chipper string) (bool, error){
	if password == "" || chipper == ""{
		return false, errors.New("password or chipper is empty")
	}

	result, err := base64.StdEncoding.DecodeString(chipper)
	if err != nil {
		return false, err
	}

	// TODO result ���ܲ���16���ֽڣ���Ҫ�ݴ���
	return b.encode(result[:b.keySize], password) == chipper, nil
}


func main(){

	var password = "������������"

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