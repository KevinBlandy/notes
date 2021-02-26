---------------------------
模块
---------------------------
	# 模块
		https://blog.golang.org/using-go-modules
	
	# 查看命令帮助
		go help mod
		go help modules
		go help module-ge

	# 开启
		* 设置环境变量
			GO111MODULE=on

	# 仓库代理
		* 设置环境变量
			export GOPROXY=https://goproxy.io
		
		* 通过命令设置
			go env -w GOPROXY=https://goproxy.io,direct
	
	# 初始化一个go mod
		go mod init [module]
			module
					* 指定模块名称，可以是带路径的
	
	# 查看模块的依赖
		go list -m all
			-m 
				* 可以指定只查看指定的依赖信息，
			
			all
				* 表示查看所有
			main
				* 可执行程序的顶层包
			std
				* 标准库的包
			cmd
				* go仓库里的命令行工具和它们的内部库

			-versions
				* 列出依赖的版本号列表
			
			-json
				* 指定格式化输出为JSON
			
			-deps
				* 列出传递依赖

			
		
	# 通过命令修改依赖版本
		go get [module]@[version]
			module
				* 指定模块
			version
				* 指定版本

			-u
				* 下载的库使用最新的minor 或 patch最新版
	
	# 通过修改go.mod文件修改依赖版本
		go mod edit -require="[module]@[version]"
			module
				* 指定模块
			version
				* 指定版本
	# 更新依赖信息
		go mod tidy
			* 下载更新依赖
			* 会自动清理掉不需要的依赖项，同时可以将依赖项更新到当前版本

---------------------------
命令
---------------------------
	#  go mod <command> [arguments]
	
	# command
		download    
			* 下载依赖到本地缓存
			* 默认缓存在 $GOPATH/pkg/mod
			
        edit        edit go.mod from tools or scripts
			* 编辑go.mod文件
        graph       
			* 输出依赖
        init        
			* 在当前目录初始化一个mod
        tidy        
			* 更依赖信息，并且会删除没使用到的依赖
        vendor      
			* 创建依赖关系的拷贝
        verify     
			* 验证依赖关系的内容
        why         
			* 解释为什么需要go mod
	
---------------------------
go.mod
---------------------------
module go-project

go 1.15

replace (
	golang.org/x/crypto v0.0.0-20180820150726-614d502a4dac => github.com/golang/crypto v0.0.0-20180820150726-614d502a4dac
	golang.org/x/net v0.0.0-20180821023952-922f4815f713 => github.com/golang/net v0.0.0-20180826012351-8a410e7b638d
	golang.org/x/text v0.3.0 => github.com/golang/text v0.3.0
)

require (
    github.com/gin-gonic/gin v1.6.3 // indirect
)


replace
	* 用于替换依赖的包，相当于重写
	* 在一些没法下载的情况下，可以替换成可以访问到的网络
		golang.org/x/text v0.3.0 => github.com/golang/text v0.3.0

require
	* 依赖和版本设置
	* 后面的 indirect 表示间接引用
