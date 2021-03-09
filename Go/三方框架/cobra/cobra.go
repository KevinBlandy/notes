----------------------
cobra
----------------------
	# Doc
		https://cobra.dev/
		https://github.com/spf13/cobra
		https://pkg.go.dev/github.com/spf13/cobra
	
	# 核心的概念
		* 命令(Command)：	就是需要执行的操作，命名可以是有子父级关系
		* 参数(Arg)：		命令的参数，即要操作的对象
		* 选项(Flag)：		命令选项可以调整命令的行为
	
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
