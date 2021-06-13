------------------
yaml配置
------------------
archetypeDir: "archetypes"
assetDir: "assets"
baseURL: "http://localhost/"
blackfriday: 
build:
  noJSConfigInAssets: false
  useResourceCacheWhen: fallback
  writeStats: false
buildDrafts: false
buildExpired: false
buildFuture: false
caches: 
canonifyURLs: false
contentDir: "content"
dataDir: "data"
defaultContentLanguage: "en"
defaultContentLanguageInSubdir: false
disableAliases: false
disableHugoGeneratorInject: false
	* 是否禁用hugo的生成标记(本页面由Hugo生成)

disableKinds: []
disableLiveReload: false 
	* 是否禁用文件系统监控，在文件改变后重新渲染
disablePathToLower: false
	* 是否禁用转换URL为小写

enableEmoji: false
	* 启用表情

enableGitInfo: false
enableInlineShortcodes: false
	* 启用内联短代码

enableMissingTranslationPlaceholders: false
	* 如果缺少翻译，则显示占位符，而不是空字符串

enableRobotsTXT: false
	* 是否生成robot.txt
frontmatter:
footnoteAnchorPrefix: ""
footnoteReturnLinkContents: ""
googleAnalytics: ""
	* Google Analytics 跟踪 ID。

hasCJKLanguage: ""
	* 如果为 true，则自动检测内容中的中文/日文/韩文。这将使.Summary和.WordCount正确行为的CJK语言。

imaging: 
languages:
languageCode: "en-us"
languageName: ""
disableLanguages:
layoutDir: "layouts"
	* Hugo 从中读取布局（模板）的目录。

log: false
	* 启用日志记录

logFile: ""
	* 开启日志记录情况下，日志输出文件

markup:
menu:
minify:
module:
newContentEditor: ""
	* 创建新内容时使用的编辑器

noChmod: false
	* 不同步文件的权限

noTimes: false
paginate: 10
	* 每页默认大小

paginatePath: "page"
	* 分页路径

permalinks:
	* 永久链接

pluralizeListTitles: true
	* 将列表中的标题复数化

publishDir: "public"
	* 设置构建后的静态资源输出目录
related:
relativeURLs: false
refLinksErrorLevel: "ERROR"
refLinksNotFoundURL:
rssLimit: 
	* RSS 提要中的最大项目数。
	* 默认不限制

sectionPagesMenu: ""
sitemap:
	* 站点地图

staticDir: "static"
	* 静态资源目录

summaryLength: 70
	* 摘要长度

taxonomies: ""
	* 分类法

theme: ""
	* 设置使用的主题

themesDir: "themes"
	* 设置主题文件所在的目录

timeout: 10000
title: "blog"
	* 站点标题

titleCaseStyle: "AP"
uglyURLs: false
verbose: false
verboseLog: false
watch: false
	

------------------
yaml常用
------------------
baseURL: "https://blog.springboot.io/"
defaultContentLanguage: "en"
languageCode: "en-us"
title: "springboot"
theme: "ananke"
enableEmoji: true

------------------
配置目录
------------------
	# 除了使用单个站点配置文件之外，还可以使用configDir目录（默认为config/）来维护更轻松的组织和环境特定设置

	# 每个文件代表一个配置根对象，文件名称，就是配置项名称
		params.tomlfor [Params]
		menu(s).tomlfor [Menu]
		languages.tomlfor[Languages]
	
	# 每个文件的内容必须是顶级的


	# Demo
		* 结构
			├── config
			│   ├── _default
			│   │   ├── config.toml
			│   │   ├── languages.toml
			│   │   ├── menus.en.toml
			│   │   ├── menus.zh.toml
			│   │   └── params.toml
			│   ├── production
			│   │   ├── config.toml
			│   │   └── params.toml
			│   └── staging
			│       ├── config.toml
			│       └── params.toml
		
		* 命令
			hugo --environment staging

			Hugo 将使用来自config/_default 和合并 staging 的所有设置
		
