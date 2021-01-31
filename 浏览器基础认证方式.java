-----------------------
Basic
-----------------------
	# 核心
		* 客户端会把用户名和密码，两者中间以冒号（:）连接后，再经过 Base64 编码处理。
		* 添加一个Header
			Authorization	Basic YWRtaW46YWRtaW4=
		
		* BASIC 认证需要配合HTTPS来保证信息传输的安全
		
	# 代码
		// 读取Header，Authorization
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);

		String value = HttpHeaders.encodeBasicAuth("admin", "admin", StandardCharsets.UTF_8);
		value = "Basic " + value;

		if (!value.equals(token)) {
			//设置Header， WWW-Authenticate, realm 可以理解为提示语
			response.setHeader(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Authorization Required\"");
			// 403
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return null;
		}

		// TODO 验证成功

-----------------------
Digest
-----------------------
	# 核心
		* 弥补Basic的缺点
	
