
--------------------------------
获取客户端的真实IP
--------------------------------
	// proxyHeaders 反向代理情况下的源 IP Header
	var proxyHeaders = []string{"X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP"}

	// RemoteAddr 获取客户端 IP 地址
	func RemoteAddr(request *http.Request) string {
		for _, header := range proxyHeaders {
			ip, err := netip.ParseAddr(request.Header.Get(header))
			if err != nil || !ip.IsValid() {
				continue
			}
			return ip.String()
		}
		host, _, err := net.SplitHostPort(request.RemoteAddr)
		if err != nil {
			host = request.RemoteAddr
		}
		return host
	}
