------------------------
hugo
------------------------
	# 相关网站
		https://gohugo.io/
		https://gohugo.io/documentation/
		https://discourse.gohugo.io/
		https://themes.gohugo.io/

		https://www.gohugo.org/
		https://olowolo.com/post/hugo-quick-start/

------------------------
目录结构
------------------------
	├── archetypes
		* 存放文章模板的目录
		* 新建一些一些文章的时候，会从这个目录找模板
	
	├── assets
		* 存储所有需要Hugo Pipes处理的文件。只有使用其.Permalink或的文件.RelPermalink才会发布到public目录中
		* 默认情况下，不创建这个目录

	├── config
		* 配置文件存放目录，在这目录中可以有不同的环境
		* 默认情况下，不创建这个目录
	
	├── content
		* 网站的所有内容都将位于此目录中。
		* Hugo 中的每个顶级文件夹都被视为一个内容部分。

		* 举例来说，如果网站主要有三个sections: blog, articles, tutorials
		* 那么将有三个目录的:content/blog, content/articles, content/tutorials。

	├── data
		* 该目录用于存储 Hugo 在生成网站时可以使用的配置文件
		* 可以使用 YAML、JSON 或 TOML 格式编写这些文件。
		* 除了添加到此文件夹的文件之外，还可以创建从动态内容中提取的数据模板。


	├── layouts
		* 以.html文件形式存储模板，指定如何将内容视图呈现到静态网站中。
		* 模板包括列表页、您的主页、分类模板、部分模板、单页模板等等。

	├── static
		* 存储所有静态内容：图像、CSS、JavaScript 等。
		* 当 Hugo 构建站点时，静态目录中的所有数据都按原样复制。
		* 使用该static文件夹的一个很好的例子是在 Google Search Console 上验证站点所有权，您希望 Hugo 复制完整的 HTML 文件而不修改其内容。

		* 从hugo 0.31 开始，可以拥有多个静态目录。

	├── themes
	└── config.toml