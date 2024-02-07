------------------------
路由
------------------------
	# 基于 http.ServeMux 的路由匹配
	
	# 注册 Handler 
		func Handle(pattern string, handler Handler)
		func HandleFunc(pattern string, handler func(ResponseWriter, *Request))

		* pattern 模式的格式是这样子的: [METHOD ][HOST]/[PATH]，中括号代表这一项可以省略。
		* 如下表达式都是正确的
			POST /items/create
			POST rpcx.io/items/create
			GET /items/123
			/items/123
		
		* 不设置 HTTP Method 如 POST,那么默认是匹配所有的 HTTP Method。注意 HTTP Method 之后有一个空格。
		* 除了 GET 会匹配 GET 和 HEAD 除此之外，其他的 HTTP Method 都是精确匹配。
	
		* 如果未设置 HOST，那么默认是匹配所有的 HOST。否则，HOST 必须完全匹配。
	
	
	# 通配符
		* 请求的路径中可以包含通配符，配符名称必须是有效的Go标识符，且必须是 '完整' 的路径段，例如 /b_{bucket} 就不是一个合法的通配符。
		
			/items/{id}
				* 通配符只匹配一个路径段，匹配/ items/123，但是不匹配 /items/123/456
			
			/items/{apth...}
				* 通配符后面跟着 ...，那么它就会匹配多个路径段，比如 /items/123、/items/123/456 都会匹配到
			
			/items/{$}
				* 默认来说以 / 结尾的模式会匹配所有以它为前缀的路径，比如 /items/、/items/123、/items/123/456 都会匹配这个模式。
				* 如果以 /{$} 为后缀，那么表示严格匹配路径，不会匹配带后缀的路径，比如这个例子只会匹配 /items/，不会匹配 /items/123、/items/123/456
		
		* 在匹配过程中，模式路径和传入请求路径都会逐段解码。因此，例如，路径 /a%2Fb/100%25 被视为具有两个段，a/b 和 100%。模式 /a%2fb/ 与之匹配，但模式 /a/b/ 则不匹配。
		
	# 优先级
		* 如果匹配出现冲突，例如：/items/{id} 和 /items/{path...} 都可以匹配 /items/123
		* 按照一下规则计算优先级

		* 最具体的模式具有优先权。比如 /items/{id} 比 /items/ 更具体。
		* 如果两者都不更具体，则模式冲突。
			* 在冲突的情况下，具有主机的模式具有优先权 rpcx.io/items/{id} 比 /items/{id} 优先权更高。
			* 如果两者都没有 HOST，则模式冲突，panic。 items/{id} 和 /items/{index} 都没有 HOST，所以会 panic。
		
	# 后缀 / 的转发
		* 如果注册了 /images/，则会导致 ServeMux 把 /images 重定向到 /images/ 除非你注册了/images 的handler。
	
	# 请求清理
		* ServeMux 还负责清理 URL 请求路径和 Host Header，去除端口号，并将包含 . 或 .. 段或重复斜杠的任何请求重定向到等效、更清晰的URL。
	

	# 总结
		* 还是鸡肋，实际应用还是用 gin
	