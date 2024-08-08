---------------------
rand
---------------------

---------------------
var
---------------------
	var Reader io.Reader
		* ȫ�ֵġ�����ġ�������ѧ�ϵİ�ȫ���������������
			�� Linux �� FreeBSD �ϣ�Reader ʹ�� getrandom(2) ������ã�����ʹ�� /dev/urandom��
			�� OpenBSD �ϣ�Reader ʹ�� getentropy(2) ��
			����������Unix��ϵͳ�У�Readerʹ��/dev/urandom��ȡ��
			��Windowsϵͳ�У�Readerʹ��RtlGenRandom API��
			��Wasmϵͳ�ϣ�Readerʹ��Web Crypto API��

---------------------
type
---------------------


---------------------
func
---------------------
	func Int(rand io.Reader, max *big.Int) (n *big.Int, err error)
	func Prime(rand io.Reader, bits int) (p *big.Int, err error)
	func Read(b []byte) (n int, err error)
		* ��������
			return io.ReadFull(Reader, b)

---------------------
demo
---------------------
	# ���ɰ�ȫ�������Կ

		package main

		import (
			"crypto/rand"
			"encoding/base64"
			"fmt"
		)

		func main() {
			// 32 ���ֽڵ���Կ
			key := make([]byte, 32)
			for range 5 {
				// ����
				if _, err := rand.Read(key); err != nil {
					panic(err)
				}
				fmt.Println(base64.StdEncoding.EncodeToString(key), len(key))
			}
		}
