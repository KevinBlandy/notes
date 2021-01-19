----------------------
配置文件主体		  |
----------------------
	{
	  "log": {},
		# 日志的配置
			*  "log" 对象
				{
					"loglevel": "warning",
					"access": "/var/log/v2ray/access.log", 
					"error": "/var/log/v2ray/error.log"
				}

			* 日志级别:loglevel
				debug	最详细的日志信息，专用于软件调试
				info	比较详细的日志信息，可以看到 V2Ray 详细的连接信息
				warning	警告信息。轻微的问题信息，经我观察 warning 级别的信息大多是网络错误。推荐使用 warning
				error	错误信息。比较严重的错误信息。当出现 error 时该问题足以影响 V2Ray 的正常运行
				none	空。不记录任何信息
	
	  "api": {},
		# 可以开放一些 API 以便远程调用
			* 基于 gRPC

	  "dns": {},
		# 内置的 DNS 服务器，若此项不存在，则默认使用本机的 DNS 设置

	  "stats": {},
		# 当此项存在时, 开启统计信息
			* 该配置对象目前没有任何属性, 配置一个空的即可 
			* 同时还需要在 Policy 中开启对应的项, 才可以统计对应的数据
		 
	  "routing": {},
		# 路由配置
			* 下文详解


	  "policy": {},
			# 本地策略可进行一些权限相关的配置
				{
				  "levels": {
					"0": {								// 用户等级
					  "handshake": 4,					// 连接建立时的握手时间限制。单位为秒。默认值为4
					  "connIdle": 300,					// 连接空闲的时间限制。单位为秒。默认值为300。
					  "uplinkOnly": 2,					// 当连接下行线路关闭后的时间限制。单位为秒。默认值为2。当服务器（如远端网站）关闭下行连接时，出站代理会在等待 uplinkOnly 时间后中断连接。
					  "downlinkOnly": 5,				// 当连接上行线路关闭后的时间限制。单位为秒。默认值为5。当客户端（如浏览器）关闭上行连接时，入站代理会在等待 downlinkOnly 时间后中断连接。
					  "statsUserUplink": false,			// 当值为true时，开启当前等级的所有用户的上行流量统计。	
					  "statsUserDownlink": false,		// 当值为true时，开启当前等级的所有用户的下行流量统计。
					  "bufferSize": 10240				// 每个连接的内部缓存大小。单位为 kB。当值为0时，内部缓存被禁用。
						* 默认值
							在 ARM、MIPS、MIPSLE 平台上，默认值为0。
							在 ARM64、MIPS64、MIPS64LE 平台上，默认值为4。
							在其它平台上，默认值为512。
						* 它会覆盖环境变量中v2ray.ray.buffer.size的设定。
					}
				  },
				  "system": {
					"statsInboundUplink": false,		// 当值为true时，开启所有入站代理的上行流量统计。
					"statsInboundDownlink": false		// 当值为true时，开启所有入站代理的下行流量统计。
				  }
				}

	  "reverse": {},
			# 反向代理配置

	  "inbounds": [],
			# 入站链接的配置
			  * 下文详解

	  "outbounds": [],
			# 出站链接的配置
				* 下文详解

	  "transport": {}
			# 用于配置 V2Ray 如何与其它服务器建立和使用网络连接
			# 全局
				

	}


	# alterId 
		* 这个参数主要是为了加强防探测能力
		* 理论上 alterId 越大越好,但越大就约占内存(只针对服务器，客户端不占内存),所以折中之下设一个中间值才是最好的
		* 那么设多大才是最好的？其实这个是分场景的，我没有严格测试过这个，不过根据经验，alterId 的值设为 30 到 100 之间应该是比较合适的。
		* alterId 的大小要保证客户端的小于等于服务器的。

----------------------
routing				  |
----------------------
	# v2ray内建了一个简单的路由功能, 可以将入站数据按需求由不同的出站连接发出，以达到按需代理的目的
	# 这一功能的常见用法是分流国内外流量，V2Ray 可以通过内部机制判断不同地区的流量，然后将它们发送到不同的出站代理。
	# 配置对象
		{	
		  "domainStrategy": "AsIs",	
				* "AsIs": 只使用域名进行路由选择。默认值。
				* "IPIfNonMatch": 当域名没有匹配任何规则时，将域名解析成 IP（A 记录或 AAAA 记录）再次进行匹配；
					- 当一个域名有多个 A 记录时，会尝试匹配所有的 A 记录，直到其中一个与某个规则匹配为止；
					- 解析后的 IP 仅在路由选择时起作用，转发的数据包中依然使用原始域名；
				* "IPOnDemand": 当匹配时碰到任何基于 IP 的规则，将域名立即解析为 IP 进行匹配；

		  "rules": [],

		  "balancers": []
		}

----------------------
inbounds			  |
----------------------
	# 入站连接用于接收从客户端（浏览器或上一级代理服务器）发来的数据
	# 配置对象
		{
		  "port": 1080,
				* 端口
		  "listen": "127.0.0.1",
				* 绑定的网卡, 默认: 0.0.0.0
		  "protocol": "协议名称",
				* 支持的协议：
					Blackhole
					Dokodemo-door
					Freedom
					HTTP
					MTProto
					Shadowsocks
					Socks
					VMess
		  "settings": {},
				* 具体的配置内容，视协议不同而不同。
		  "streamSettings": {},
				* 底层传输配置

		  "tag": "标识",
				* 此入站连接的标识，用于在其它的配置中定位此连接
				* 当其不为空时，其值必须在所有tag中唯一

		  "sniffing": {
			"enabled": false,
			"destOverride": ["http", "tls"]
		  },
				* 尝试探测流量的类型
		  "allocate": {
			"strategy": "always",
			"refresh": 5,
			"concurrency": 3
		  }
				* 端口分配设置
		}
	
	# settings
		{
		  "tcpSettings": {},
		  "kcpSettings": {},
		  "wsSettings": {},
		  "httpSettings": {},
		  "dsSettings": {},
		  "quicSettings": {}
		}
----------------------
outbounds			  |
----------------------

----------------------
一般配置			  |
----------------------
# 服务器配置
{
  "inbounds": [
    {
      "port": 16823, // 服务器监听端口
      "protocol": "vmess",    // 主传入协议
      "settings": {
        "clients": [
          {
            "id": "b831381d-6324-4d53-ad4f-8cda48b30811",  // 用户 ID，客户端与服务器必须相同
            "alterId": 64
          }
        ]
      }
    }
  ],
  "outbounds": [
    {
      "protocol": "freedom",  // 主传出协议
      "settings": {}
    }
  ]
}

 
# 客户端配置
{
  "inbounds": [
    {
      "port": 1080, // 监听端口
      "protocol": "socks", // 入口协议为 SOCKS 5
      "sniffing": {
        "enabled": true,
        "destOverride": ["http", "tls"]
      },
      "settings": {
        "auth": "noauth"  //socks的认证设置，noauth 代表不认证，由于 socks 通常在客户端使用，所以这里不认证
      }
    }
  ],
  "outbounds": [
    {
      "protocol": "vmess", // 出口协议
      "settings": {
        "vnext": [
          {
            "address": "serveraddr.com", // 服务器地址，请修改为你自己的服务器 IP 或域名
            "port": 16823,  // 服务器端口
            "users": [
              {
                "id": "b831381d-6324-4d53-ad4f-8cda48b30811",  // 用户 ID，必须与服务器端配置相同
                "alterId": 64 // 此处的值也应当与服务器相同
              }
            ]
          }
        ]
      },
	  "mux": {"enabled": true}
    }
  ]
}

