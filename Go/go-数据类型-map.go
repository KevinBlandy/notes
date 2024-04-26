--------------------
map
--------------------
	# Hash������ģ�key/value�ṹ�����������ͣ������Զ�����
		map[keyType]valueType
	
	# Map��key�������ǿ�hash�����ݵ�����
		config := map[interface{}]interface{}{
			"":" ",
			// ���԰���Ƭ��Ϊkey���쳣
			[]int {1, 2}: "",  // panic: runtime error: hash of unhashable type []int
		}
		fmt.Println(config)

		* key�����ǣ�������==���������ͣ��������ݵģ�������
	
	# Map �� Value Ҳ����Ҫ��֤�ǲ��ɱ�����
		type Foo struct {
			Name string
		}

		dict := make(map[string]Foo)

		foo := Foo{
			Name: "Hello",
		}

		dict["k1"] = foo

		// ����ֱ���޸� Map �� ��k1�� ��ֵ�ᵼ�� Value �����ı䣬���׳��쳣��
		// cannot assign to struct field dict["k1"].Name in map
		dict["k1"].Name = "Hi"

		* ����취���ȶ���д
			v := dict["k1"]
			v.Name = "Hi"
			dict["k1"] = v
		
		* ����ʹ��ָ��
			dict := make(map[string]*Foo)

	# map��ʼ�����봴��
		* ��������ʽ�ĳ�ʼ��
			config := map[string]string {
				"name": "Vin",
				"lang": "Java",
			}
		
		* ʹ�� make ������ʼ��������ͨ��make�ĵ�����������ָ��hash��ĳ�ʼ������
			config := make(map[string]int)
			var config map[string]int = make(map[string]int, 16)

	# ��������ʽ��ʼ�����﷨��
		* value�����Ա��ƶϳ���������value��ʼ����ʱ����Ҫȥ���������� 

		type User struct {
			name string
		}
		func test(){
			config1 := map[string] [2]string {
				"1": {"1", "2"},	// ֱ�ӳ�ʼ������
				"2": [2]string {"1", "2"},	// ������ʼ��
				"3": [...]string {"1", "2"},	// ������ʼ��
			}
			fmt.Println(config1)

			config2 := map[string] []string {
				"1": {"1", "2"},	// ֱ�ӳ�ʼ����Ƭ
				"2": []string{"1", "2"},	// ������ʼ����Ƭ
			}
			fmt.Println(config2)

			config3 := map[string] User {
				"1": {"hh"},		// ֱ�ӳ�ʼ������
				"2": User{"hh"},	// ������ʼ������
			}
			fmt.Println(config3)

			config4 := map[string] *User {
				// "1": User{"hh"},	// cannot use User literal (type User) as type *User in map value
				"1": {"hh"},		// ֱ�ӳ�ʼ��ָ��
				"2": &User{"hh"},	// ��������ʼ������ȡָ��Ҳ��OK
			}
			fmt.Println(config4)
		}

	
	
	# ��/д
		config := make(map[string]string, 16)
		
		* ʹ��[]����key/value�Ķ�д
			// д��
			config["name"] = "Helllo"
			// ��ȡ
			name := config["name"]
		
		* ��ȡʱ�ж��Ƿ���ڣ�����2����������1��������ֵ����2��������bool��ʾ���Ƿ����
			val, exists := config["foo"]
			fmt.Println(val, exists) // false
	
		* ���value�����ڣ�����map��nil����ô���ص���value��Ĭ����������
			config := map[string]int {"name": 1,}
			fmt.Println(config["foo"]) // 0

			var m map[int]int
			fmt.Println(m == nil)		// true
			fmt.Println(m[0])			// 0
		
		* д���ʱ��map����Ϊnil
			var m map[int]int
			m[0] = 10		// panic: assignment to entry in nil map
			fmt.Println(m)

		
	# ɾ��Ԫ��
		* ʹ�����ú��� delete ���
			delete(map, key)
			
		* ���ɾ����Ԫ�ز����ڣ�����key�� nil�����ᱨ��
		* map������nil�����򱨴�
			delete(nil, "name") // first argument to delete must be map; have nil
		

	# ����
		* ʹ�� range ���� key �� value
			for key, value := range config {
				fmt.Printf("key=%s value=%s \n", key, value)
			}
		
		* ʹ�� range ��������key����value
			for key := range config {
				fmt.Printf("key=%s \n", key)
			}
		
			* ���������Ҫ����value����ôʹ������������ for _, value := range config
				
	
	# Map�ıȽϺ͸�ֵ
		* ֻ�ܸ�nil����==�Ƚ�
			fmt.Println(make(map[int]int) == make(map[int]int)) // invalid operation: (make(map[int]int)) == (make(map[int]int)) (map can only be compared to nil)
		
		* ��ͬKey/valu���͵�map�����໥��ֵ
			m1 := make(map[int]int)
			m2 := make(map[int]int)
			m2 = m1
			fmt.Println(m1, m2)
	
	# map�е�Ԫ�ز��Ǳ��������ܻ�ȡ���ǵ�ָ��
		m := make(map[int]int)
		fmt.Println(&m[1]) // cannot take the address of m[1]

--------------------
map - ����
--------------------
	# �Ƚ�2��map�Ƿ����
		func equals(x, y map[interface{}] interface{}) bool {
			if len(x) != len(y) {
				return false
			}
			for key, xVal := range x {
				if yVal,ok := y[key]; !ok || yVal != xVal {
					return false
				}
			}
			return true
		}