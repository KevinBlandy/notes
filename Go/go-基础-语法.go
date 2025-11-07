
-------------------------
条件
-------------------------
	# if
		if [codifion] {
		}
		
		if [condition] {
		
		} else if [condition] {

		} else{

		}

	# 允许在if语句中存在2个代码块，第一个用于初始化或者计算，第二个代码块结果要是boolean值
		if age := 23; age > 10 {
			fmt.Println("23")
		} else {
			fmt.Println(age)
		}
		fmt.Println(age)  // undefined: age

		v := 5;
		if v ++; v > 5 {
			fmt.Println("大于5") // 大于5
		}
		fmt.Println(v) // 6
				
		* if语句中的变量，只有在它的代码块中有效
	
	# 把if当作case用
		
	
	
-------------------------
运算
-------------------------
	# 基本的运算
		+,-,*,/,%

		x := 2
		x *= 5 // x = x * 5

	# 自增/减语法，仅仅支持后缀，不支持前缀
		i ++ // ok
		i -- // ok
		++ i // error
	
	
	# 关系/逻辑运算
		||
		&&
		!
		==, !=
		>, >=
		<, <=
	
	# 位运算
		x << y		左移
		x >> y		右移
		x & y		and 与
		x | y		or	或
		x ^ y		xor	异或
		^x			not	取反
		&^			位清空
	
	
	
-------------------------
循环
-------------------------
	# 唯一的循环
		for [initialization]; [conditon]; [post] {
			// TODO
		}

		* initialization，初始化在循环开始之前执行，可以是赋值语句，函数调用
		* conditon，布尔表达式，用于确定是否要执行循环
		* post，循环体被执行后，执行

		* 左大括号 '{'， 必须跟在第一行后面
	
	# 只要 conditon，就成了 while 循环
		for [conditon] {
			// TODO
		}

	# 三个部分都是可以省略的
		for {
			// 无限循环
		}
	
	# 继续与终止当前的循环
		continue / break
	
	# 使用goto语法，跳出直接多层循环
		outer: for i := 0; i < 10; i ++ {		// 设置外层循环的 label 为 outer
			for j := 0; j < 10; j++ {
				if (j == 5 && i == 4) {
					break outer					// 跳出 指定的 label
				}
				fmt.Printf("i=%d, j=%d \n", i,  j)
			}
		}
	

	
	
	# for range 循环
		* 可以遍历数组，map，切片，通道，字符串
		* 返回索引/值，key/value，通道只返回值: _,v : range ch
			name := "Hello Go"
			for i, v := range name{
				fmt.Println(i, v)
			}
	
		
	# for range 可以直接遍历数值
		for i := range 5 {
			fmt.Println(i) // 输出 0 - 4
		}

		* 如果 range 的值 <= 0 则不会进行任何迭代
		* 本质上是下面循环的语法糖
			for i := 0; i < 5; i++ {
			    fmt.Println(i)
			}
		
		* 如果不需要循环变量，可以直接省略

			for range 10 {
				// TODO 循环十次
			}

	

-------------------------
switch
-------------------------
	# 基本的语法
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
			case '余':
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

		* param 可以有2个代码块，第一个可以用来初始化变量，第二个是声明用来执行switch的变量，这个变量只在当前switch中生效
		* 不需要写break，自动选择执行然后跳出
		* val 可以有多个，使用逗号分隔，关系是 |，只要匹配其中一个，就会执行
		* case 中 val 的数据不能重复，否则会给异常
		* default 不是必须的
		
	# 不需要操作数
		switch {
			case [val]: {
			}
			case [val]: {
			}
			default: {
				
			}
		}

		* 每条case语句，都是一个布尔表达式，如果不是布尔表达式会异常
		* 这种称为无标签选择，等价于 switch true 
	
	# fallthrough
		* 这是为了兼容C设计的东西，一般不用
		* 该关键字的意思是，强行执行当前满足条件case下的下一个case
			
		switch val := 5;  val {
			case 5:
				fmt.Println("5")		// 执行
				fallthrough
			case 6:
				fmt.Println("6")		// 执行
			case 7:
				fmt.Println("7")
			default: 
				fmt.Println("nil")
		}
	
	# 在switch中对interface使用类型判断，可以通过 .(type) 操作获取到 interface/实例 的类型
		func main() {
			var x interface{} = int(1)
			fmt.Println(x.(type))  // use of .(type) outside type switch

			out(1)			// 是int
			out("h")		// 是字符串
			out(nil)		// 是null
			out(666.33)		// 都不是
		}	
		func out(param interface{}){
			switch param.(type) {
				case int: {
					fmt.Println("是int")
				}
				case string: {
					fmt.Println("是字符串")
				} 
				case nil: {
					fmt.Println("是null")
				}
				default: {
					fmt.Println("都不是")
				}
			}
		}

		* .(type) 只能被 interfacce ，接口操作
		* .(type) 并且只能在 switch 语句中使用
		* .(type) 还会返回强制转换后的对象，可以在switch代码块中使用
			switch v2 := v.(type) {
				case string:
					fmt.Println("is string", v2)
			}

		* 要注意实例和指针，是不同的
			type Foo struct {}
			type Bar interface {}
			func main(){
				var bar Bar = &Foo{}
				switch bar.(type) {
					case *Foo: {
						fmt.Println("我是FOO指针")
					}
					case Foo: {
						fmt.Println("我是FOO实例") // 这里离职，这里不会执行
					}
				}
			}
				

-------------------------
goto
-------------------------
	# 快速跳到某个位置执行，别用这玩意儿
		for i := 0; i < 10; i ++ {
			for j := 0; j < 10; j++ {
				if (j == 5 && i == 4) {
					goto outer  // 跳转到指定的label代码块执行
				}
				fmt.Printf("i=%d, j=%d \n", i,  j)
			}
		}
		// 定义了一个 label 代码块，取名 outer
		outer: {
			fmt.Printf("执行完毕了")
		}
	
