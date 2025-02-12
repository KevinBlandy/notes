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
	
	func Text() string
		* ʹ�ñ�׼ RFC 4648 base32 ��ĸ����һ����������ַ���������Ҫ�����ַ��������ơ�����������ı�ʱʹ�á�
		* ������ٰ��� 128 ���ص�����ԣ����Է�ֹ�����²⹥������ʹ��ײ�Ŀ�����΢����΢��δ���汾���ܻ������Ҫ���ظ������ı����Ա�����Щ���ԡ�
		* ���磺3BVGQSPSODDDH7JZMKFWR5N5XE


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
