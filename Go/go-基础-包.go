------------------------
包
------------------------
	# 所有go源码文件，都必须要要有一个 package 声明

	# 包的定义
		* 包本质上就是一个文件夹，一般文件夹名称和包名称相同，这个文件夹下的所有go文件，开头都要添加 package [包名]
		* go文件的名称，无所谓

		* 同一个包下，所有go文件，不能有相同命名的变量 ，函数，不然异常
		* 同一个包下，所有go文件，必须要有相同的package，不然异常
	
		* package名与文件夹名称不一样的时候，import 要写文件夹名称，而不是package名称

	# main 包的特殊
		package main

		* 这个包名3比较特殊，用来定义一个独立的可执行程序，而不是库。
	
	# import 指令是从gopath开始找包，路径统一使用 / 分割
		$GOPATH/src

	# 多个包名相同的时候，可以通过起别名来引用
		import (
			u "./utils" // 起了个别名:u
		)
		func main(){
			u.Test()	// 通过别名来操作
		}
	
	# 循环依赖
		* Go禁止循环依赖，如果出现，会异常
	
	
	# 匿名导入
		* 只希望导入包，而不使用
			import (
				_ "./utils"
			)
		* 导入包的目的，不是为了调用它的方法或者是属性，也许只是为了它会执行一些我们需要的代码
	
	# 包的初始化执行代码，init
		* 包被导入时，会自动执行
			func init(){
				fmt.Println("init....")
			}
		
		* 不能有参数，不能有返回值，也不能手动调用
		* 这个函数比较特殊，一个go文件可以，定义多个，会挨个执行
		* 不同go文件中的init，会先执行自己import包中的init，再执行自己的init
			mian -> import a -> improt b
			main.init() <- a.init() <- b.init()

		* 不同go文件，也可以定义多个init函数
	
	# 包的初始化顺序
		* 全局声明的变量初始化
		* init函数执行
		
	
	# 没有暴露的对象，可以引用但是不能声明
		// foo 包下有一个 user 结构体
		import (
			"./foo"
			"fmt"
		)
		func main(){
			// 通过暴露的函数，可以获取到user结构体的引用
			u := foo.NewUser()	
			// 这种声明方式，异常
			// var u *foo.user = foo.NewUser()  // Unexported type 'user' usage
			fmt.Printf("%T\n", u) // *foo.user
		}


------------------------
远程包
------------------------
	# 导入
		import (
			"github.com/myteam/exp/crc32"
		)
	
	# 在执行go build或者go install之前执行get
		go get github.com/myteam/exp/crc32

		* 可以直接下载整目录中的包
			go get ...
	
	# go工具会自动获取位于远程的包源码，在随后的编译中，也会在pkg目录中生成对应的.a文件

------------------------
工程的包结构
------------------------
	