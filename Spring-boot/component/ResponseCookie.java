----------------------------
ResponseCookie
----------------------------
	# 用于构建响应给客户的Cookie信息

		@GetMapping("/test")
		public Object test (HttpServletRequest request,
						HttpServletResponse response) throws Exception {
			
			ResponseCookie cookie = ResponseCookie.from("myCookie", "myCookieValue") // key & value
					.httpOnly(true)		// 禁止js读取
					.secure(false)		// 在http下也传输
					.domain("localhost")// 域名
					.path("/")			// path
					.maxAge(Duration.ofHours(1))	// 1个小时候过期
					.sameSite("Lax")	// 大多数情况也是不发送第三方 Cookie，但是导航到目标网址的 Get 请求除外
					.build()
					;
			
			// 设置Cookie
			response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
			
			return "ok";
		}
	
	# 主要是 sameSite 属性， servlet还没实现。迫不得已可以使用ResponseCookie