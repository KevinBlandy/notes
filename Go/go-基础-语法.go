
-------------------------
����
-------------------------
	# if
		if [codifion] {
		}
		
		if [condition] {
		
		} else if [condition] {

		} else{

		}

	# ������if����д���2������飬��һ�����ڳ�ʼ�����߼��㣬�ڶ����������Ҫ��booleanֵ
		if age := 23; age > 10 {
			fmt.Println("23")
		} else {
			fmt.Println(age)
		}
		fmt.Println(age)  // undefined: age

		v := 5;
		if v ++; v > 5 {
			fmt.Println("����5") // ����5
		}
		fmt.Println(v) // 6
				
		* if����еı�����ֻ�������Ĵ��������Ч
	
	# ��if����case��
		
	
	
-------------------------
����
-------------------------
	# ����������
		+,-,*,/,%

		x := 2
		x *= 5 // x = x * 5

	# ����/���﷨������֧�ֺ�׺����֧��ǰ׺
		i ++ // ok
		i -- // ok
		++ i // error
	
	
	# ��ϵ/�߼�����
		||
		&&
		!
		==, !=
		>, >=
		<, <=
	
	# λ����
		x << y		����
		x >> y		����
		x & y		and ��
		x | y		or	��
		x ^ y		xor	���
		^x			not	ȡ��
		&^			λ���
	
	
	
-------------------------
ѭ��
-------------------------
	# Ψһ��ѭ��
		for [initialization]; [conditon]; [post] {
			// TODO
		}

		* initialization����ʼ����ѭ����ʼ֮ǰִ�У������Ǹ�ֵ��䣬��������
		* conditon���������ʽ������ȷ���Ƿ�Ҫִ��ѭ��
		* post��ѭ���屻ִ�к�ִ��

		* ������� '{'�� ������ڵ�һ�к���
	
	# ֻҪ conditon���ͳ��� while ѭ��
		for [conditon] {
			// TODO
		}

	# �������ֶ��ǿ���ʡ�Ե�
		for {
			// ����ѭ��
		}
	
	# ��������ֹ��ǰ��ѭ��
		continue / break
	
	# ʹ��goto�﷨������ֱ�Ӷ��ѭ��
		outer: for i := 0; i < 10; i ++ {		// �������ѭ���� label Ϊ outer
			for j := 0; j < 10; j++ {
				if (j == 5 && i == 4) {
					break outer					// ���� ָ���� label
				}
				fmt.Printf("i=%d, j=%d \n", i,  j)
			}
		}
	

	
	
	# for range ѭ��
		* ���Ա������飬map����Ƭ��ͨ�����ַ���
		* ��������/ֵ��key/value��ͨ��ֻ����ֵ: _,v : range ch
			name := "Hello Go"
			for i, v := range name{
				fmt.Println(i, v)
			}
	
		
	# for range ����ֱ�ӱ�����ֵ
		for i := range 5 {
			fmt.Println(i) // ��� 0 - 4
		}

		* ��� range ��ֵ <= 0 �򲻻�����κε���
		* ������������ѭ�����﷨��
			for i := 0; i < 5; i++ {
			    fmt.Println(i)
			}
		
		* �������Ҫѭ������������ֱ��ʡ��

			for range 10 {
				// TODO ѭ��ʮ��
			}

	

-------------------------
switch
-------------------------
	# �������﷨
		switch [param] {
			case [val]: {
			}
			case [val]: {
			}
			default: {
				
			}
		}
		
		name  := '-'
		switch name {
			case '��':
				fmt.Println("1")
			case ' ' :
				fmt.Println("2")
			default: 
				fmt.Println("3")
		} 

		val := 1
		switch val {
			case 1, 2, 3:
				fmt.Println("123")
			case 4, 5, 6:
				fmt.Println("456")
		}

		switch val := 5;  val {
			case 1, 2, 3:
				fmt.Println("123")
			case 4, 5, 6:
				fmt.Println("456")
		}

		* param ������2������飬��һ������������ʼ���������ڶ�������������ִ��switch�ı������������ֻ�ڵ�ǰswitch����Ч
		* ����Ҫдbreak���Զ�ѡ��ִ��Ȼ������
		* val �����ж����ʹ�ö��ŷָ�����ϵ�� |��ֻҪƥ������һ�����ͻ�ִ��
		* case �� val �����ݲ����ظ����������쳣
		* default ���Ǳ����
		
	# ����Ҫ������
		switch {
			case [val]: {
			}
			case [val]: {
			}
			default: {
				
			}
		}

		* ÿ��case��䣬����һ���������ʽ��������ǲ������ʽ���쳣
		* ���ֳ�Ϊ�ޱ�ǩѡ�񣬵ȼ��� switch true 
	
	# fallthrough
		* ����Ϊ�˼���C��ƵĶ�����һ�㲻��
		* �ùؼ��ֵ���˼�ǣ�ǿ��ִ�е�ǰ��������case�µ���һ��case
			
		switch val := 5;  val {
			case 5:
				fmt.Println("5")		// ִ��
				fallthrough
			case 6:
				fmt.Println("6")		// ִ��
			case 7:
				fmt.Println("7")
			default: 
				fmt.Println("nil")
		}
	
	# ��switch�ж�interfaceʹ�������жϣ�����ͨ�� .(type) ������ȡ�� interface/ʵ�� ������
		func main() {
			var x interface{} = int(1)
			fmt.Println(x.(type))  // use of .(type) outside type switch

			out(1)			// ��int
			out("h")		// ���ַ���
			out(nil)		// ��null
			out(666.33)		// ������
		}	
		func out(param interface{}){
			switch param.(type) {
				case int: {
					fmt.Println("��int")
				}
				case string: {
					fmt.Println("���ַ���")
				} 
				case nil: {
					fmt.Println("��null")
				}
				default: {
					fmt.Println("������")
				}
			}
		}

		* .(type) ֻ�ܱ� interfacce ���ӿڲ���
		* .(type) ����ֻ���� switch �����ʹ��
		* .(type) ���᷵��ǿ��ת����Ķ��󣬿�����switch�������ʹ��
			switch v2 := v.(type) {
				case string:
					fmt.Println("is string", v2)
			}

		* Ҫע��ʵ����ָ�룬�ǲ�ͬ��
			type Foo struct {}
			type Bar interface {}
			func main(){
				var bar Bar = &Foo{}
				switch bar.(type) {
					case *Foo: {
						fmt.Println("����FOOָ��")
					}
					case Foo: {
						fmt.Println("����FOOʵ��") // ������ְ�����ﲻ��ִ��
					}
				}
			}
				

-------------------------
goto
-------------------------
	# ��������ĳ��λ��ִ�У������������
		for i := 0; i < 10; i ++ {
			for j := 0; j < 10; j++ {
				if (j == 5 && i == 4) {
					goto outer  // ��ת��ָ����label�����ִ��
				}
				fmt.Printf("i=%d, j=%d \n", i,  j)
			}
		}
		// ������һ�� label ����飬ȡ�� outer
		outer: {
			fmt.Printf("ִ�������")
		}
	
