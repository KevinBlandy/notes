--------------------
null 的处理			|
--------------------
	# 我的理解就是 Kotlin 通过一些手段, 把空指针问题, 从运行阶段, 提升到了编译阶段

	# Kotlin 中, 默认的所有数据都不能为 null , 赋值null, 或者未赋值都会给出异常
		var x:String = null; // 异常
	
	# 需要手动的声明, 该变量可以为 Null
		var x:String? = null; 

		* dei, 就是在类型后面添加一个问号, 表示该变量可以为 null
	
	# 如果变量允许 null 值, 那么这会带来一些限制
		* 不能执行调用该变量的方法
			fun foo(value:String?){
				value.length		// 直接调用方法, 异常
			}
		
		* 不能赋值给非空变量
			var x:String? = null;
			var y: String = x // 异常
		
			fun foo(value:String){}
			fun main() {
				var x:String? = ""
				foo(x)			// 异常, 因为方法的形参是非空的
			}
		

		* 突破这些限制需要自己通过手动的去判断 null 
		* 一但进行过判断, 编译器就会记住, 它就会认为是安全的, 就可以执行
			fun foo(value:String?){
				if (value != null){
					value.length
				}
			}

			var x:String? = null;
				if(x != null){
					var y: String = x 
				}
				
			fun foo(value:String){}
			fun main() {
				var x:String? = ""
				if (x != null) {
					foo(x)
				}
			}

	
	# 安全调用运算符: ?.
		* 这个其实就是简化了两个步骤, 1: 判读是否非空, 2: 执行操作
			var value : String? = ""
			var result = value?.trim()
			println(result)
		
		* 如果是null,的话那么 ?. 整个表达式的结果为 null
	
		* 不仅可以安全的调用方法, 还支持安全的访问属性
			class User(val name:String?)
			fun main() {
				var user = User(null)
				var result = user.name?.trim()
				println(result)
			}

		* 必要时,还可以链接多个安全调用
			class Account(val email:String?)
			class User(val account:Account?)
			fun main() {
				var user = User(null)
				// 链式的安全调用
				var result = user.account?.email?.trim()
				println(result)
			}
	
	# Eivis运算符: ?:
		* 这东西, 会帮你判断是否为null, 如果左边表达式的结果为 null, 那么就赋值为 右边的表达式结果
			var value:String? = null
			var result = value ?: "空的"
			println(result) // 空的
		
		* 可以跟安全运算符配合使用
		* 安全运算符可以在变量为null的时候, 安全的返回 null, Eivis 运算符可以把 null 值设置为默认值
			fun length(value:String?):Int{
				return value?.length ?: 0
			}
		
		* 也可以利用它,合理的抛出异常, 类似于js: if xx && throw ...
			fun length(value:String?):Int{
				return value?.length ?: throw Exception("不能为空")
			}
		
	
	# 安全的转换运算符: as ?
		* as 其实就是使用了 cast 强制转换, 如果转换失败, 会抛出异常
		* 可以使用安全的 as? 转换运算符, 在不能进行转换的时候, 安全的返回 null
			class Account
			class User

			fun main() {
				// 转换失败, 返回null
				var foo: Account? = User() as? Account
				println(foo)
			}

			* 因为可能会返回null, 所以接收变量也得是可以为 null 的
		
		* 可以配合Eivis等运算符, 在 equals 时带来方便
			class User (private val id:Int){
				override fun equals(other: Any?): Boolean {
					// 转换失败返回null, null遇到eivis晕算符, 直接返回 false
					var otherUser = other as? User ?: return false
					return otherUser.id == this.id
				}
			}
	

	# 非空断言: !!
		* 作用就是判断变量是否为null, 如果是 null 就抛出异常
		* 这样做的目的是,在你定义断言的时候抛出, 而不是执行的时候抛出, 更好找到异常点
			fun length(value:String?):Int {
				// 非空断言, 如果是 null 就抛此处抛出异常, 而不是到下面访问它的属性时才抛出
				var notNullString :String = value!!
				return notNullString.length
			}
	
	# let 函数
		* 就是对自己进行判断, 如果是 null, 则返回, 不执行
		* 如果非 null, 则执行 lambda 表达式的代码

			fun length(value:String):Int {
				return value.length
			}
			fun main() {
				var value : String? = null
				//?. 安全的访问 value, 
				var result = value?.let { value -> length(value)}
			}
		
	
	# lateinit 延迟初始化
		* lateinit 修饰的变量, 表示可以为null, 需要延迟初始化
		* 如果没初始化访问, 会异常:UninitializedPropertyAccessException 
		* 这个比空指针好理解
		* 应该可以看出必须使用 var,而不是使用 val
			class User {
				public lateinit var name : String
			}
			fun main() {
				println(User().name) // UninitializedPropertyAccessException
			}
	

	# 可空类型的扩展方法
		* 看个方法
			var value:String? = null
			// null 对象调用: isNullOrEmpty 居然没异常?
			var result = value.isNullOrEmpty()
			println(result) // true
		
		* 其实就是定义扩展属性的时候, 设置了允许调用的对象可以为null
		* 但是方法体里面就必须要进行判断了, 因为真的可能为空
			fun String?.foo() : String  {
				return (this ?: "null") + "foo"
			}

			fun main() {
				var value:String? = "131"
				var result = value.foo()
				println(result)
			}

		* 在 java 里面 this 永远不会为空
		* 在 Kotlin 里面,扩展方法中的 this 可以为null

		* 扩展方法被编译为静态方法后, this 就是第一个参数而已, 所以可以为null
	
	# 泛型参数是可空的
		* 方法参数如果不带 ? 表示不能为 null, 如果直接传递 null, 在编译的时候就会给异常
		* 但是泛型方法例外, 泛型参数不带 ?, 调用的时候传递 null, 不会给编译异常
			fun <T> foo( t : T){
				println(t)
			}
			fun bar (value:String){
				println(value)
			}
			fun main() {
				foo(null)
				bar(null) // 编译异常
			}
		
		* 要使泛型可以在编译时期就判断 null 值的话, 需要给它指定泛型的上限

	# 和 Java 打交道中的空值处理
		* Kotlin 可以识别到一些 Java 定义的注解: javax.annotation
		* 并且自动的转换为 Kotlin 对 null 的表达
			@Nullable	+	 type  =  type?
				
			@NotNull	+	 type  = type
		

		* 在Kotlint中访问 Java 的对象要注意 null 判断
		
		* Kotlin 中的可空类型, 不能用 Java 的基本数据类型表示
		* 意味着所有使用了基本数据类型的可空版本,	它就会被编译为对应的包装类型
		
		* 覆写 Java 方法的时候, 可以重新定义方法参数是否允非空
		* 覆写 Kotlin 方法的时候, 必须跟父类定义的一样, 父类方法参数允许空, 子类必须允许空


	
	
	# 集合与空
		* 表示集合可以存储空元素
			ArrayList<Int?>
		
		* 表示集合可以为null, 而且还可以存储 null 元素
			ArrayList<Int?>?
		
