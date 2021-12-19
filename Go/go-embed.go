---------------------------
embed
---------------------------
	# 语法
		//go:embed [pattern]
	
	# 支持的数据	
		[]byte		表示数据存储为二进制格式，如果只使用[]byte和string需要以import (_ "embed")的形式引入embed标准库
		string		表示数据被编码成utf8编码的字符串，因此不要用这个格式嵌入二进制文件比如图片，引入embed的规则同[]byte
		embed.FS	表示存储多个文件和目录的结构，[]byte和string只能存储单个文件
			//go:embed texts
			var dir embed.FS

			// 两者没什么区别
			//go:embed texts/*
			var files embed.FS
				
		
		* 如果只使用[]byte和string需要以import _ "embed" 的形式引入embed标准库
		* 声明嵌入内容的变量一定要求使用var声明
		* FS支持一次性嵌入多个资源，包括目录，会自动忽略重复项
			//go:embed hello.txt
			//go:embed hello2.txt
			var f embed.FS

			//go:embed hello.txt hello2.txt
			var f embed.FS

			//go:embed static
			//go:embed templates
			var FS embed.FS
		


	# pattern规则
		* 默认的根目录从module的目录开始，路径开头不可以带/
		* 不管windows还是其他系统路径分割副一律使用/

		* 如果匹配到的是目录，那么目录下的所有文件都会被嵌入，如果其中包含有子目录，则对子目录进行递归嵌入。(可以通过通配符来嵌入/排除指定的文件)

		* golang在对目录进行递归嵌入的时候(只写文件夹名称)会忽略名字以下划线（_）和点（.）开头的文件或目录。
		* 注意: * 不具有递归性，它嵌入.和_开头的文件和文件夹
		* 这些文件名在部分文件系统中为隐藏文件
			//go:embed images
			var images embed.FS // 不包含.b.jpg和_photo_metadata目录

			//go:embed images/*
			var images embed.FS // 注意！！！ 这里包含.b.jpg和_photo_metadata目录

			//go:embed images/.b.jog
			var bJPG []byte // 明确给出文件名也不会被忽略‘’
		
		* 不支持绝对路径、不支持路径中包含.和..,如果想嵌入go源文件所在的路径，使用*:
			//go:embed *
			var f embed.FS

		* pattern 是 path.Matc h所支持的路径通配符
		* 如果在linux系统上，可以用man 7 glob查看更详细的教程
		
			?				代表任意一个字符（不包括半角中括号）
			*				代表0至多个任意字符组成的字符串（不包括半角中括号）
			[...]和[!...]	代表任意一个匹配方括号里字符的字符，!表示任意不匹配方括号中字符的字符
			[a-z]、[0-9]	代表匹配a-z任意一个字符的字符或是0-9中的任意一个数字
			**				部分系统支持，*不能跨目录匹配，**可以，不过目前个golang中和*是同义词
		
		* 如果目录/文件有空格，则可以使用引号
			//go:embed "he llo.txt" `hello-2.txt`
			var f embed.FS
		
		* 通配符的默认目录和源文件所在的目录是同一目录，所以我们只能匹配同目录下的文件或目录，不能匹配到父目录
			.
			├── code
			│   └── main.go
			├── go.mod
			├── imgs
			│   ├── jpg
			│   │   ├── a.jpg
			
			* 这里的main.go可见的资源只有code目录及其子目录里的文件，而imgs和texts里的文件是无法匹配到的
	
	# 访问数据，需要使用全路径
		* 目录结构
			resources
			|- static
			|- templates
			|- resources.go
		
		* resources.go 代码
			//go:embed static
			//go:embed templates
			var FS embed.FS    // 多个目录合并为一个
		
		* 其他地方使用
			file, err := resources.FS.Open("static/favicon.ico")
			// 需要指定嵌入的完整目录


	# Demo
		// 映射目录
		//go:embed static
		var fileSystem embed.FS

		// 映射文件内容为字符串
		//go:embed static/demo.txt
		var txtFile string

		// 映射文件内容为字节数组
		//go:embed static/demo.txt
		var binFile []byte

		func main(){
			// entry, err := fileSystem.ReadDir(".") // entry 只有一个元素，就是 /static 目录
			// 获取文件夹下的所有文件
			entry, err := fileSystem.ReadDir("static")
			if err != nil {
				fmt.Println(err.Error())
			}
			for _, e := range entry {
				fmt.Println(e.Name())
				// demo.txt
				// index.html
			}

			fmt.Println(txtFile)
			// 123
			fmt.Println(string(binFile))
			// 123
		}
	
	
	# 用在HTTP FileServer 时，不显示文件列表

		
---------------------------
静态资源映射
---------------------------
	//go:embed static
	var fsys embed.FS
	http.Handle("/", http.FileServer(http.FS(fsys)))


	http.Handle("/static/", http.StripPrefix("/static/", http.FileServer(http.FS(fsys))))

---------------------------
模板引擎映射
---------------------------
	# 结构
		|-main.exe
		|-resources
			|-templates
				|-default.html
				|-index
					|-index.html
		
	# 实现
		import (
			"embed"
			"fmt"
			"github.com/gin-gonic/gin"
			"html/template"
			"io"
			"io/fs"
			"log"
			"net/http"
			"os"
			"path/filepath"
			"strings"
		)

		// 映射资源目录为一个文件系统
		//go:embed resources
		var resource embed.FS

		func main(){
			// 从文件系统的 “resources/templates"” 加载HTML模板引擎
			templates, err := loadTemplates(resource, "resources/templates")
			if err != nil {
				log.Fatalf(err.Error())
			}
			for _, v := range templates.Templates() {
				fmt.Printf("加载模板引擎：%s\n", v.Name())
			}
			router := gin.New()
			router.SetHTMLTemplate(templates)
			router.GET("/", func(context *gin.Context) {
				context.HTML(http.StatusOK, "default.html", nil)
			})
			router.GET("/index", func(context *gin.Context) {
				context.HTML(http.StatusOK, "index/index.html", nil)
			})
			
			server := &http.Server {
				Addr:    ":1024",
				Handler: router,
			}
			if err := server.ListenAndServe(); err != nil {
				log.Fatalf(err.Error())
			}
		}

		func loadTemplates(fileSystem fs.FS, root string) (*template.Template, error) {

			templates := template.New("templates")

			// 必须先添加方法，模板引擎中如果使用到了方法，不存在会异常
			templates.Funcs(map[string] interface{} {})

			// 遍历模板引擎目录，把所有文件都当做模板，以目录的相对路径作为模板名称
			err := fs.WalkDir(fileSystem, root, func(path string, d fs.DirEntry, err error) error {
				if !d.IsDir() {
					absPath, err := filepath.Rel(root, path)
					if err != nil {
						return err
					}
					// 读取每一个文件到内存，使用相对路径作为“模板名称”
					err = func () error {
						file, err := fileSystem.Open(path)
						if err != nil {
							return err
						}
						defer file.Close()
						context, err := io.ReadAll(file)
						if err != nil {
							return err
						}
						// 把文件分隔符，统一替换为 “/”
						templates.New(strings.ReplaceAll(absPath, string(os.PathSeparator), "/")).Parse(string(context))
						return nil
					}()
					return err
				}
				return nil
			})
			if err != nil {
				return nil, err
			}
			return templates, nil
		}
	

	# 在Gin中使用
		//go:embed resources
		var Resources embed.FS

		func LoadTemplates(fileSystem fs.FS, root string) (*template.Template, error) {

			templates := template.New("templates")

			// 必须先添加方法，模板引擎中如果使用到了方法，不存在会异常
			templates.Funcs(map[string] interface{} {})

			// 遍历模板引擎目录，把所有文件都当做模板，以目录的相对路径作为模板名称
			err := fs.WalkDir(fileSystem, root, func(path string, d fs.DirEntry, err error) error {
				if !d.IsDir() {
					absPath, err := filepath.Rel(root, path)
					if err != nil {
						return err
					}
					// 读取每一个文件到内存，使用相对路径作为“模板名称”
					err = func () error {
						file, err := fileSystem.Open(path)
						if err != nil {
							return err
						}
						defer file.Close()
						context, err := io.ReadAll(file)
						if err != nil {
							return err
						}
						// 把文件分隔符，统一替换为 “/”
						templates.New(strings.ReplaceAll(absPath, string(os.PathSeparator), "/")).Parse(string(context))
						return nil
					}()
					return err
				}
				return nil
			})
			if err != nil {
				return nil, err
			}
			return templates, nil
		}

		// 加载模板引擎
		templates, err := LoadTemplates(Resources, "resources/templates")
		if err != nil {
			log.Fatalf("加载模板引擎异常：%s\n", err.Error())
		}
		router.SetHTMLTemplate(templates)

		// 静态资源路径
		staticFs, err := fs.Sub(Resources, "resources/static")
		if err != nil {
			log.Fatalf("静态资源路径读取异常：%s\n", err.Error())
		}
		router.StaticFS("/static", http.FS(staticFs))

		* 目录结构
			static      静态资源文件，通过 /static 来访问
			templates   模板引擎目录
			main.go