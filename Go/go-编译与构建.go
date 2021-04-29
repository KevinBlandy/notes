
---------------------------
Go app 的目录结构
---------------------------
	<项目名>
	├─README		# 说明
	├─LICENSE		# 分发协议
	├─AUTHORS		# 作者
	├─<bin>
		├─calc
	├─<pkg>
		└─<linux_amd64>
			└─simplemath.a
	├─<src>	
		├─<calc>
			└─calc.go
		├─<simplemath>
			├─add.go
			├─add_test.go
			├─sqrt.go
			├─sqrt_test.go

	
	* pkg和bin则无需手动创建，如果必要Gotool在构建过程中会自动创建这些目录


----------------------
跨平台编译
----------------------
	# 跨平台编译(交叉编译)
		SET CGO_ENABLED=0
			* 禁用CGO
			* 交叉编译不支持 CGO 所以要禁用

		SET GOOS=linux
			* 目标操作系统
			* 如
				windows
				linux
				darwin

		SET GOARCH=amd64
			* 目标处理器架构
			* 如
				amd64
	
	
		* 只有Windows平台下，设置变量需要 SET 指令，其他不需要
