----------------------------
枚举						|
----------------------------
	# 枚举的声明
		enum class Lang {
			JAVA, C, JAVASCRIPT, PYTHON
		}

		* 按照kotlin的尿性,极简主义,应该只用声明一个 enum 就好了, 为啥还要声明一个 class ?
		* 为了不占用 enum 关键字,只有 enum 后面跟着 class 关键字的时候,enum 才是关键字,其他的时候,可以当做变量名称使用(哦)
	
	# 声明带属性/方法的枚举
		enum class Lang (var desc :String ,var order :Int ){
			// 创建实例的时候初始化
			JAVA("世界上最好的语言",1),
			C("世界上最牛逼的语言",2),
			JAVASCRIPT("全栈语言",3),
			PYTHON("最流行的语言",4);

			fun say(){
				println("name=${this},desc=${this.desc},order=${this.order}");
			}
		}

		* 如果最后一个枚举后还有内容(方法),那么最后一个枚举后面一定要使用分号: ; (这是Kotlin中唯一一个强制要求使用分号结束的地方)
	
	# 可以使用 when 语句操作枚举
		fun getLang(lang :Lang) = when (lang){
			Lang.JAVA -> "java"
			Lang.C -> "c"
			Lang.JAVASCRIPT -> "javascript"
			Lang.PYTHON -> "python"
		}

		* 如果需要匹配多个结果,则使用逗号分隔多个值
			fun getLang(lang :Lang) = when (lang){
				Lang.JAVA, Lang.PYTHON, Lang.JAVASCRIPT -> "最喜欢的"
				Lang.C -> "最不擅长的"
			}
	
	# 可以使用 * 一次性导入所有的枚举实例,从而可以直接访问实例,而不需要通过枚举类导航
		import io.kevinblandy.funcs.Lang.*
		fun main(args:Array<String>){
			println(PYTHON)
		}
	
	
	# 枚举可以继承/实现,并且覆写方法
		interface Foo {
			fun foo()
		}

		enum class Lang : Foo{
			JAVA {
				override fun foo() {
					TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
				}
			}
		}