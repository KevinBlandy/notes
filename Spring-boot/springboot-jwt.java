---------------------
jwt
---------------------
	# Json Web Token
		https://jwt.io/
		https://github.com/jwtk/jjwt
	
	# JWT包含了三部分
		Header 头部(标题包含了令牌的元数据, 并且包含签名和/或加密算法的类型)
			{ 
				"alg": "HS256",
				"typ": "JWT"
			} 

			* 头部存在两个信息, token类型和采用的加密算法
			* 加密的算法通常使用: HMAC, SHA256, MD5
			* 还有几个不常用的头
				cty
				kid
			
			* 都定义在了接口:PublicClaims 中

		Payload 负载(请求体, 消息主体)

			{
			  "sub": "1234567890",			// 标准属性
			  "name": "John Doe",			// 私有的属性
			  "admin": true					// 私有属性
			}
			
			* Payload 部分也是一个 JSON 对象, 用来存放实际需要传递的数据
			* JWT 规定了7个官方字段, 都定义在了接口:PublicClaims 中
					iss (issuer)签发人
					exp (expiration time)过期时间
					sub (subject)主题
					aud (audience)受众
					nbf (Not Before)生效时间
					iat (Issued At)签发时间
					jti (JWT ID)编号

			* 还可以在这个部分定义私有字段
			* JWT 默认是不加密的, 任何人都可以读到, 所以不要把秘密信息放在这个部分

			
		Signature 签名/签证
			* 这个部分需要base64加密后的header和base64加密后的payload使用,
			* 连接组成的字符串, 然后通过header中声明的加密方式进行加盐secret组合加密, 然后就构成了jwt的第三部分
			* 密钥secret是保存在服务端的, 服务端会根据这个密钥进行生成token和进行验证, 所以需要保护好
	
	# Header
		Authorization: Bearer <token>

---------------------
jwt - springboot
---------------------
	# Maven
		<!-- https://mvnrepository.com/artifact/com.auth0/java-jwt -->
		<dependency>
			<groupId>com.auth0</groupId>
			<artifactId>java-jwt</artifactId>
			<version>3.10.1</version>
		</dependency>
	
	# 签发Token
		// 距今一个礼拜后的时间戳
		long expiresTimestamp = LocalDateTime.now().plusWeeks(1).toEpochSecond(ZoneOffset.UTC);
		
		// 使用私钥创建加密算法
		Algorithm  algorithm = Algorithm.HMAC256("HellWorld");
		
		String token = JWT.create()
			.withHeader(Map.ofEntries(Map.entry("alg", "HS256"), Map.entry("typ", "JWT")))
			
			.withClaim("name", "KevinBlandy")				// 添加一个或者多个自定义信息
			.withClaim("id", 1000L)
			
			.withIssuer("springboot中文社区")				// 签发人
			.withExpiresAt(new Date(expiresTimestamp))		// 过期时间
			.withSubject("") 								// 主题
			.withAudience("1", "2", "n") 					// 受众
			.withNotBefore(new Date()) 						// 生效时间
			.withIssuedAt(new Date()) 						// 签发时间
			.withJWTId("123456") 							// 编号
			.sign(algorithm);
	
	# Token 校验
		
		// DecodedJWT 包含了 header/body 等一系列的信息
		DecodedJWT decodedJWT = JWT.require(algorithm)
						.build().verify(token);
		
		// 标准信息
		String issuser = decodedJWT.getIssuer();
		Date expiresAt = decodedJWT.getExpiresAt();
		String subject = decodedJWT.getSubject();
		List<String> audiences = decodedJWT.getAudience();
		Date notBefore = decodedJWT.getNotBefore();
		Date issuedAt = decodedJWT.getIssuedAt();
		String id = decodedJWT.getId();
		
		// 私有信息
		String name = decodedJWT.getClaim("name").asString();
		long id = decodedJWT.getClaim("id").asLong();
		
		// 所有的私有信息
		Map<String, Claim> claims = decodedJWT.getClaims();
	

		// 不校验签名的情况下, 解析token
		DecodedJWT decodedJWT = JWT.decode(String token)

	#  如果token已经过期, 或者是校验失败, 会抛出异常
			JWTVerificationException
				|-AlgorithmMismatchException
				|-InvalidClaimException
				|-JWTCreationException
				|-JWTDecodeException
				|-SignatureGenerationException
				|-SignatureVerificationException
				|-TokenExpiredException

