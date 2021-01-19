--------------------
响应压缩配置		|
--------------------
	 # 配置类
		org.springframework.boot.context.embedded.Compression
	
	# 基本的配置
server:
  compression:

    # 开启压缩
    enabled: true

    # 设置支持压缩的响应数据类型,默认:text/html", "text/xml", "text/plain","text/css", "text/javascript", "application/javascript
    mime-types: 
      - application/json
      - application/xml
      - application/javascript
      - text/html
      - text/xml
      - text/plain
      - text/css
      - text/javascript
	 
    # 默认情况下,仅会压缩2048字节以上的内容
    min-response-size: 2048

    # 指定不压缩的user-agent ,默认为 null
    excluded-user-agents:
      - application/json


	# 该配置开启后静态资源都会被压缩	

