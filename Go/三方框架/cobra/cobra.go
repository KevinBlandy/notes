----------------------
cobra
----------------------
	# Doc
		https://cobra.dev/
		https://github.com/spf13/cobra
		https://pkg.go.dev/github.com/spf13/cobra
	
	
	# 最佳实践
		* 项目结构
			app
			  |-cmd
				|-add.go
				|-your.go
				|-commands.go
				|-here.go
				|-root.go
			  |-main.go
			
			* 每个命令实现一个文件，所有命令文件存放在cmd目录下
			* 外层的main.go仅初始化 cobra
			
		* 在main.go 中初始化cobra
			package main
			import (
			  "{pathToYourApp}/cmd"
			)

			func main() {
			  cmd.Execute()
			}
	
	
	# 说明

----------------------
核心
----------------------

	# 命令(Command)：	就是需要执行的操作，命名可以是有子父级关系
		* 命令的生命周期方法，会按照如下顺序执行
			1. PersistentPreRun
			2. PreRun
			3. Run
			4. PostRun
			5. PersistentPostRun

			* PersistentPreRun 方法和 PersistentPostRun 方法会伴随任何子命令的执行

	# 参数(Arg)：		命令的参数，即要操作的对象
		* 默认的验证方法
			NoArgs
				* 如果存在任何位置参数，该命令将报错
			ArbitraryArgs
				* 该命令会接受任何位置参数
			OnlyValidArgs
				* 如果有任何位置参数不在命令的 ValidArgs 字段中，该命令将报错
			MinimumNArgs(int)
				* 至少要有 N 个位置参数，否则报错
			MaximumNArgs(int)
				* 如果位置参数超过 N 个将报错
			ExactArgs(int)
				* 必须有 N 个位置参数，否则报错
			ExactValidArgs(int) 
				* 必须有 N 个位置参数，且都在命令的 ValidArgs 字段中，否则报错
			RangeArgs(min, max)
				* 如果位置参数的个数不在区间 min 和 max 之中，报错
		
		
	# 选项(Flag)：		命令选项可以调整命令的行为
		* persistent 类型的选项，既可以设置给该 Command，又可以设置给该 Command 的子 Command
		* local 类型的选项只能设置给指定的 Command
		* 默认情况下的选项都是可选的，如果必须要求用户提供选项，可以通过 MarkFlagRequired 指定

	