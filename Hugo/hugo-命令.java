---------------------
命令
---------------------
Usage:
  hugo [flags]
  hugo [command]

Available Commands:
  config      Print the site configuration
  convert     Convert your content to different formats
  deploy      Deploy your site to a Cloud provider.
  env         Print Hugo version and environment info
  gen         A collection of several useful generators.
  help        Help about any command
  	* 输出帮助命令

  import      Import your site from others.
  list        Listing out various types of content
  mod         Various Hugo Modules helpers.
  new         Create new content for your site
  	* 创建新的站点，指定名称
		
  server      A high performance webserver
  	* 开启一个本地的服务

  version     Print the version number of Hugo
  	* 输出版本号

Flags:
  -b, --baseURL string             hostname (and path) to the root, e.g. http://spf13.com/
  -D, --buildDrafts                include content marked as draft
  		* 构建的时候，包括草稿文件

  -E, --buildExpired               include expired content
  		* 构建的时候，包括已经过期的文件

  -F, --buildFuture                include content with publishdate in the future
  		* 构建的时候，包括未来的文件

      --cacheDir string            filesystem path to cache directory. Defaults: $TMPDIR/hugo_cache/
      --cleanDestinationDir        remove files from destination not found in static directories
      --config string              config file (default is path/config.yaml|json|toml)
	  	* 指定配置文件，可以是json,toml格式，可以有多个，使用逗号:, 分隔

      --configDir string           config dir (default "config")
  -c, --contentDir string          filesystem path to content directory
      --debug                      debug output
  -d, --destination string         filesystem path to write files to
  		* 指定构建后的静态HTML文件输出路径，默认为目录下的public文件夹

      --disableKinds strings       disable different kind of pages (home, RSS etc.)
      --enableGitInfo              add Git revision, date and author info to the pages
  -e, --environment string         build environment
  		* 构建的时候，指定配置环境，就是指定配置文件夹中的文件夹名称
			hugo --environment staging

      --forceSyncStatic            copy all files when static is changed.
      --gc                         enable to run some cleanup tasks (remove unused cache files) after the build
  -h, --help                       help for hugo
      --i18n-warnings              print missing translations
      --ignoreCache                ignores the cache directory
      --ignoreVendor               ignores any _vendor directory
      --ignoreVendorPaths string   ignores any _vendor for module paths matching the given Glob pattern
  -l, --layoutDir string           filesystem path to layout directory
      --log                        enable Logging
      --logFile string             log File path (if set, logging enabled automatically)
      --minify                     minify any supported output format (HTML, XML etc.)
      --noChmod                    don't sync permission mode of files
      --noTimes                    don't sync modification time of files
      --path-warnings              print warnings on duplicate target paths etc.
      --print-mem                  print memory usage to screen at intervals
      --quiet                      build in quiet mode
      --renderToMemory             render to memory (only useful for benchmark testing)
  -s, --source string              filesystem path to read files relative from
      --templateMetrics            display metrics about template executions
      --templateMetricsHints       calculate some improvement hints when combined with --templateMetrics
  -t, --theme strings              themes to use (located in /themes/THEMENAME/)
      --themesDir string           filesystem path to themes directory
      --trace file                 write trace to file (not useful in general)
  -v, --verbose                    verbose output
      --verboseLog                 verbose logging
  -w, --watch                      watch filesystem for changes and recreate as needed
		* 是否实时监控文件系统修改，如果修改了会立即更新页面
		* 默认开启，禁用
			hugo server --watch=false
		* 禁用也可以用
			hugo server --disableLiveReload
 
---------------------
命令
---------------------
	hugo
		* 执行构建，输出静态资源到public目录
		* 构建的时候，不会删除目录中的资源，自己应该先删除，或者重新指定一个新的目录

	hugo new site [folder-path]
		* 在folder-path中初始化一个新的博客项目
	

	hugo new [file]
		* 新建一个文章，file指定文章名称，例如:  HelloWorld.md
		* 也可以指定路径： post/HelloWorld.md
	
	hugo server
		* 在本地提供HTTP预览服务，默认端口: 1313