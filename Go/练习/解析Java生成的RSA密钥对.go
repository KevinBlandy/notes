package main

import (
	"crypto/rand"
	"crypto/rsa"
	"crypto/x509"
	"encoding/base64"
	"fmt"
)

func main() {

	// Java  KeyPairGenerator 生成的密钥对
	publicKeyStr := "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvyKd9UmWRG1/maRgYhdaetwWcRZ/TQMVeI/dL4/dqmVqWgv5VQ8r97thDxyrVyqQBWxfut1yOwc9HA10T62tuxD1x/MD9uU4pGgk5CbiGMEYylJ3OJWVzFGpcSlCuc52ZB95o+Ao2UPxn5t5Fd4ulOC4uLPBrWI2pnZo80iU13YfWPVZNTnqAMQuJhsF9G8wrpTPP5a/92+AJLj6/sQC5AdLZRr8sHp9LQ7hU6Nk8nPM9S/4ciXrJFRcx6+NHAzo6c0geDMzjb/enzDaICaWujXkrljCPRCOaXdgjU7PTMNQjvhEWX7jK9hvcBztgPwsmysJOoDUtebzsEpEJJwrFwIDAQAB"
	privateKeyStr := "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC/Ip31SZZEbX+ZpGBiF1p63BZxFn9NAxV4j90vj92qZWpaC/lVDyv3u2EPHKtXKpAFbF+63XI7Bz0cDXRPra27EPXH8wP25TikaCTkJuIYwRjKUnc4lZXMUalxKUK5znZkH3mj4CjZQ/Gfm3kV3i6U4Li4s8GtYjamdmjzSJTXdh9Y9Vk1OeoAxC4mGwX0bzCulM8/lr/3b4AkuPr+xALkB0tlGvywen0tDuFTo2Tyc8z1L/hyJeskVFzHr40cDOjpzSB4MzONv96fMNogJpa6NeSuWMI9EI5pd2CNTs9Mw1CO+ERZfuMr2G9wHO2A/CybKwk6gNS15vOwSkQknCsXAgMBAAECggEAGVjK38NxdQOcX1CoI3n7Wr2jED4uhZT6HPHfIDaQ687lKqnZ18jaSNEFjw57PmT7kmyLyWkig+dy2CyElyhuGJeoMGruxKgjOdJByivJ4gSBeBs1ikB0DBNsSABzw9Y4ZxCxfeT1JdLX0RW6GgdL4BxpAr172rVij/K1+kVOz7+0kWQ+07i8fOKvgPm6onby0AtKwkJ2XCLxPE/UZTKuEK8BCB0/HvZ4WicsfWFO33TM04fNsqCewSSZVXbs4gh28SM/wdJ9JajZRZqhFKj2cJ5Zyc3/3T58B950B/7cIo+KxTVHztHppOSC35n8qb/f0tik2DrAwXm2EeaB5azI8QKBgQDvKtqMy4pWGv8zY4dBCdU0+p130fv7xjdB0kBs1dWghJtj8+fNkiRjPTYh/JjH42uCn/lpfy5HBH4xDVM4gytYFO//zjhmeFKOjE2e2U0mEm/OREspBDEtjoRFv4FDO+uiv2BtuVRYJE+Q63dcP8DIe//CK72yPd/6CTdTAsfxLwKBgQDMllqm8jJa6Wq3MtOF4BkbKXZNOF8Zp72jOueGFpP6DycLfhfmrj1g77aeZGQQLcOt+NJBO2tNePWWCCzWaPJElQ6WP9BbNA+lPxqHf+7wmBR8nwVUygy7a9uSjszdpVanKUjO4DYTeWfxzvAzVAJJ+ShIPVf5m2PNvtt0BzXamQKBgQDB6H2/b3seas8ETCqp+Z3qUVMAhbiGnmPIsv3W/9wvQ7R06pjDHDzyVPuhvJnmXqWZB7kA5sOPgr4JaSNTC05GINuhDALAWcedmXWJV+dr+cC6fLZvb92rcQQfRLA54cgfcIhvDbarq+zys2ZX3byhD6ACPtKTRAVnRLedTwDqswKBgHqWGOlxtDR/Kl9tSAsutTxuQqGQhoNT3DCDahkRTEeIbsfXHTEu9D4yRIVx9/ctxNNohWtq1CfOs7DbYypyJcBsZBopUIpljn5gamAIiz6Ekbn+eu5MRwbCYQXXJPAmUt7EwQiclipjE6Jzcb//tYNnlPh8MUuyDUJJ+yrx6xX5AoGAUgozGJsnDtE8qWMIDhbY7J7di0CsSMsFiWR3roJCgH3KkyftXGNtOwOjlyn6POeXbemFKE9ESGqVjTHZ7wuGO6tDB7gqJ3+cBFaRq2B827OvJ7W1DUo2gMfa1w7kIPwz4AfLaQ/wnpqx7O1kcNfeYAn/5Lf5bX+abjutRTYGdCA="

	// Base64 解码
	publicKeyBytes, err := base64.StdEncoding.DecodeString(publicKeyStr)
	if err != nil {
		fmt.Printf("解码公钥失败 err=%s\n", err.Error())
		return
	}

	privateKeyBytes, err := base64.StdEncoding.DecodeString(privateKeyStr)
	if err != nil {
		fmt.Printf("解码私钥失败 err=%s\n", err.Error())
		return
	}

	// 解析为 RSA 公钥 & 私钥
	publicKey, err := x509.ParsePKIXPublicKey(publicKeyBytes)
	if err != nil {
		fmt.Printf("解析公钥失败 err=%s\n", err.Error())
		return
	}

	rsaPublicKey := publicKey.(*rsa.PublicKey)

	privateKey, err := x509.ParsePKCS8PrivateKey(privateKeyBytes)
	if err != nil {
		fmt.Printf("解析公钥失败 err=%s\n", err.Error())
		return
	}
	rsaPrivateKey := privateKey.(*rsa.PrivateKey)

	// 公钥加密 & 私钥解密

	cypher, err := rsa.EncryptPKCS1v15(rand.Reader, rsaPublicKey, []byte("Hello World"))
	if err != nil {
		fmt.Printf("公钥加密失败 err=%s\n", err.Error())
		return
	}
	fmt.Printf("密文：%s\n", base64.StdEncoding.EncodeToString(cypher))

	raw, err := rsa.DecryptPKCS1v15(rand.Reader, rsaPrivateKey, cypher)
	if err != nil {
		fmt.Printf("私钥解密失败 err=%s\n", err.Error())
		return
	}

	fmt.Printf("原文：%s\n", string(raw))
}
