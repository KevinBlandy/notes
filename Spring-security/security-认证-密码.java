--------------------------
密码编码
--------------------------
	# 密码编码接口
		PasswordEncoder
			String encode(CharSequence rawPassword);
				* 编码密码
			boolean matches(CharSequence rawPassword, String encodedPassword);
				* 匹配校验
			boolean upgradeEncoding(String encodedPassword)
				* 如果编码后的密码应该再次编码以提高安全性，则返回true，否则返回false。
				* 默认 返回false。
	
	# DelegatingPasswordEncoder
		* 这是一个代理密码编码器，它本身实现了 PasswordEncoder 接口
		* 可以在这个代理编码器中注册各种类型的不同的编码器实现

		* 创建默认的，默认包含了10多个预定义的编码器实现
			PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

			* 默认使用： bcrypt
		
		* 自定义，只包含自己指定的实现
			// id 是一个标识符，用于查询应该使用哪个 PasswordEncoder
			String idForEncode = "bcrypt";

			// 编码器
			Map<String, PasswordEncoder> encoders = new HashMap<>();

			// 指定编码器ID和编码器实现
			encoders.put(idForEncode, new BCryptPasswordEncoder());
			encoders.put("pbkdf2@SpringSecurity_v5_8", Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8());
			encoders.put("scrypt@SpringSecurity_v5_8", SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8());
			encoders.put("argon2@SpringSecurity_v5_8", Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8());

			// 构建，并且通过ID指定要使用的编码器
			PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(idForEncode, encoders);

	
		

	# BCryptPasswordEncoder
		* 构造
			public BCryptPasswordEncoder(BCryptVersion version, int strength, SecureRandom random)

			* 指定版本号：BCryptVersion.$2A(默认)/BCryptVersion.$2Y/BCryptVersion.$2B
			* 强度：默认为 10
			* 随机源：默认为 null
		
	# Argon2PasswordEncoder
	# Pbkdf2PasswordEncoder
	# SCryptPasswordEncoder
	# NoOpPasswordEncoder 
		* 不加密的密码，密码原样存储
	
	

--------------------------
密码存储
--------------------------

--------------------------
密码修改
--------------------------
