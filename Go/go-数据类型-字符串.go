----------------------
�ַ�
----------------------
	# �ַ�ʹ�õ�����
		name  := '��'

		* һ��ʹ�� byte(int8), runne(int32) ����ʾһ���ַ�
		* Ĭ���ǣ� runne(int32)
	
	# Unicode��֧��
		* Unicode����ռ��2���ֽڣ�һ��ʹ�� int16 ���� int32��ʾ
			var ch1 int32 = '\u0041'
			var ch2 int32 = '\u03B2'
			
		* ����д Unicode �ַ�ʱ����Ҫ�� 16 ������֮ǰ����ǰ׺\u����\U
		*  '\u0041'			4���ֽ�
		*  '\U00101234'		8���ֽ�


----------------------
�ַ���
----------------------
	# strin����
		* ����������ַ��������Ƕ��һ��
		* ������������֣�����ͼ������ת��Ϊunicode������ַ���
			fmt.Println(string(65)) // "A", not "65"
			fmt.Println(string(0x4eac)) // "��"

	# �������ַ���ʹ��˫���Ű���
		var val string = "Hello"
	
	# ת���ַ�
		\\
		\r
		\n
		\t
		\'
		\"
	
	# ԭʼ�ַ�����ʹ�÷����Ű���
		var val = `
			Hello \t \r \n 
			\\\
		`
		* �����ַ�����Ҫת��
		* ԭʼ��ô�������������ô��
	

	# �ַ�������ֱ��ʹ�� + ����ƴ��
		var name = "Hello" + " World"
	
	# ��ȡ�ַ������ֽڳ��ȣ�ʹ��ȫ�ֺ��� len
		 var size int = len("Hello")

		 * ע�⣬������ȣ����ֽڳ��ȣ������ַ�����
		
	# ��ȡ�ַ����ȣ�ʹ��"unicode/utf8"���еķ���
		import (
			"fmt"
			"unicode/utf8"
		)
		func main() {
			str := "��ð���Go����"
			fmt.Println(utf8.RuneCountInString(str)) // 8
		}


	# �ַ������ֽ������ַ������ת��
		* �ַ����ͽ�����
			name := "Go����"
			nameBytes := []byte(name)
			fmt.Println(nameBytes) // [71 111 232 175 173 232 168 128]

			nameStr := string(nameBytes)
			fmt.Println(nameStr) // Go����
		
		* �ַ������ַ�����
			name := "Go����"
			nameChars := []rune(name)
			fmt.Println(nameChars) // [71 111 35821 35328]

			nameStr := string(nameChars)
			fmt.Println(nameStr) // Go����
		
		* �ַ�����ָ���ı����ʽת��Ϊ�ֽ�����
			TODO

		* ���ֽ�������ָ���ĸ�ʽת��Ϊ�ַ���
			TODO
		
		* unicode/utf8��Ҳ�ṩ��UTF8��Unicode֮���ת����
		* �ַ����ǲ��ɱ����ݣ�����ֱ���޸ģ�ֻ��ת��Ϊbyte/rune���飬�����޸ĺ���ת��Ϊ�µ��ַ���

	# �ַ����ı���
		* ʹ�� len() �����ֽ�
			name := "Hello Go����"
				for i := 0; i < len(name); i++ {
				fmt.Printf("%c \n", name[i])
			}

			* �������˷�ascii���룬���������

		* ʹ�� range ����ÿһ���ַ�
			name := "Hello Go����"��key��С�꣬value��rune
			for i, c := range name {
				fmt.Printf("index=%d char=%c \n", i, c)
			}


	# �ַ�������Ƭ�󣬷��صĻ����ַ���
		str := "Hello World"
		sub := str[1:]
		fmt.Printf("%T=%s\n", sub, sub)  // string=ello World

		* ע�⣬����ǰ����ֽ���Ƭ�����������ascii�ַ��Ļ��ᵼ������
			v := "��ð�������"
			fmt.Println(v[1:]) // <����><����>�ð�������
		
		* �����Ҫ�����ַ���Ƭ����ת��Ϊ�ַ�����

	
	# �ַ����ıȽϣ�����ʹ�� >,<,==
		* �Ƚϵı������ǰ��űȽ�ÿһ���ֽڣ�ֱ���Ƚϳ����
	
	
	# ���������ڴ棬�ַ����������ת��(ginԴ��)
		// �ַ���ת��Ϊ�ֽ����飬�������µ��ڴ�
		func StringToBytes(s string) (b []byte) {
			sh := *(*reflect.StringHeader)(unsafe.Pointer(&s))
			bh := (*reflect.SliceHeader)(unsafe.Pointer(&b))
			bh.Data, bh.Len, bh.Cap = sh.Data, sh.Len, sh.Len
			return b
		}

		// �ֽ�����ת��Ϊ�ַ������������µ��ڴ�
		func BytesToString(b []byte) string {
			return *(*string)(unsafe.Pointer(&b))
		}

----------------------
�����
----------------------
	# �����ַ�������
	# ���ĸ��������ַ�������Ҫ�������ṩ�˺ܶ��ַ����Ĳ�������
		bytes
		strings
		strconv
		unicode
	
