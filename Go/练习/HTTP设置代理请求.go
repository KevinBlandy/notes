
-------------------------
socks5 代理
-------------------------

package main

import (
	"fmt"
	"net/http"
	"net/url"
)

func main() {

	// 设置 SOCKS5 代理地址
	proxyURL, err := url.Parse("socks5://127.0.0.1:10808")
	if err != nil {
		panic(err)
	}

	// 设置用户名和密码
	//proxyURL.User = url.UserPassword("admin", "123456")

	// 创建 Transport，使用 SOCKS5 URL 作为代理
	transport := &http.Transport{
		Proxy: http.ProxyURL(proxyURL),
	}

	// 创建使用 SOCKS5 代理的 HTTP Client
	client := &http.Client{
		Transport: transport,
	}

	resp, err := client.Get("https://abc.cn?from=SOCKS")
	if err != nil {
		panic(err)
	}
	defer func() {
		_ = resp.Body.Close()
	}()

	fmt.Println(resp.StatusCode)
}
