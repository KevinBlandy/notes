
--------------------------------
获取客户端的真实IP
--------------------------------
	// RemoteIP 获取客户端真实的地址
	func RemoteIP(ctx *gin.Context) net.IP {
		remoteIp := net.ParseIP(ctx.GetHeader("x-forwarded-for"))
		if remoteIp == nil {
			remoteIp = net.ParseIP(ctx.GetHeader("Proxy-Client-IP"))
			if remoteIp == nil {
				remoteIp = net.ParseIP(ctx.GetHeader("WL-Proxy-Client-IP"))
				if remoteIp == nil {
					remoteIp, _ = ctx.RemoteIP()
				}
			}
		}
		return remoteIp
	}

