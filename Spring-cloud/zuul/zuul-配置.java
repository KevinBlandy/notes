# 配置类:ZuulProperties,ZuulConstants

zuul:
  # 禁止以默认的方式访问服务
  ignored-services: "*"
  # 路由表
  routes:
    # 自定义的路由名称
    api-user:
	  # 访问的路径
      path: /user-api/***/
	  # 转发到的服务
      service-id: USER-SERVICE
	  # 转发的URL
	  url: https://springboot.io 
	  # 该路由是否允许失败重试
	  retryable: false
  
  # 统一当前网关的前缀
  prefix: /api
  # 配置网关忽略的URI
  ignored-patterns:
    - /**/foo/****/
  # 过滤敏感的http头
  sensitive-headers:
    - Cookie
    - Set-Cookie
    - Authorization
  # 添加客户端(消费者)的HOST到http头
  add-host-header: true
  # 添加代理头到HTTP头
  add-proxy-headers: true
  # 是否允许失败重试
  retryable: false