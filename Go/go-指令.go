-----------------------
Go 指令
-----------------------
	go help
		* 查看帮助

	go version
		* 查看版本号
			go version go1.15.3 windows/amd64
	
	go run [file...]
		* 对一个或者多个.go源文件进行编译，链接，然后运行
	
	go build [file...]
		* 跟run一样，它不会执行，而只是生成可执行文件
		* file 可以是文件，也可以是某个目录，如果目录是相对路径，则从$GOPATH去找
		* file 不可以不写，默认是当前目录
		
		-o
			* 指定输出的可执行文件名称
	
	go install
		* 先编译，再把编译后的程序copy到 $GOPATH 下的bin目录

	go env
		* 查看go的环境信息
	
	
	go doc [方法]
		* 查看指定方法文档

	



-----------------------
Go 指令 helpe
-----------------------
Go is a tool for managing Go source code.

Usage:

        go <command> [arguments]

The commands are:

        bug         start a bug report
        build       compile packages and dependencies
        clean       remove object files and cached files
        doc         show documentation for package or symbol
        env         print Go environment information
        fix         update packages to use new APIs
        fmt         gofmt (reformat) package sources
        generate    generate Go files by processing source
        get         download and install packages and dependencies
        install     compile and install packages and dependencies
        list        list packages or modules
        mod         module maintenance
        run         compile and run Go program
        test        test packages
        tool        run specified go tool
        version     print Go version
        vet         report likely mistakes in packages

Use "go help <command>" for more information about a command.

Additional help topics:

        buildconstraint build constraints
        buildmode       build modes
        c               calling between Go and C
        cache           build and test caching
        environment     environment variables
        filetype        file types
        go.mod          the go.mod file
        gopath          GOPATH environment variable
        gopath-get      legacy GOPATH go get
        goproxy         module proxy protocol
        importpath      import path syntax
        modules         modules, module versions, and more
        module-get      module-aware go get
        module-auth     module authentication using go.sum
        module-private  module configuration for non-public modules
        packages        package lists and patterns
        testflag        testing flags
        testfunc        testing functions

Use "go help <topic>" for more information about that topic.
