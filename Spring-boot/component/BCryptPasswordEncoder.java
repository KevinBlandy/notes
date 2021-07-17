----------------------------
BCryptPasswordEncoder
----------------------------
	# 密码加密/校验工具类
		public class BCryptPasswordEncoder implements PasswordEncoder

	# 函数
		public BCryptPasswordEncoder()
		public BCryptPasswordEncoder(int strength)
		public BCryptPasswordEncoder(BCryptVersion version)
		public BCryptPasswordEncoder(BCryptVersion version, SecureRandom random)
		public BCryptPasswordEncoder(int strength, SecureRandom random)
		public BCryptPasswordEncoder(BCryptVersion version, int strength)
		public BCryptPasswordEncoder(BCryptVersion version, int strength, SecureRandom random) 

		public String encode(CharSequence rawPassword)
		public boolean matches(CharSequence rawPassword, String encodedPassword)
		public boolean upgradeEncoding(String encodedPassword)
			* 如果编码后的密码应该再次编码以提高安全性，则返回true，否则返回false。默认实现总是返回false。
			* 如果编码后的密码应该重新编码以提高安全性，则返回true，否则返回false。
			encodedPassword 
				* 要检查的编码密码
			
			
		* BCryptVersion
			$2A("$2a"), 枚举
			$2Y("$2y"),
			$2B("$2b");

		
	# 使用
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        // 对原始密码加密，获取到hash结果
        String chipper = bCryptPasswordEncoder.encode("123456");
        System.out.println(chipper);  // $2a$10$NSwn3QgNPTHk/33Wl6kye.4LMGdbDnc1YJI48O/a6D5NmLBzewLzO

        // 使用原始密码和hash结果进行对比，判断是否OK
        boolean match = bCryptPasswordEncoder.matches("123456", chipper);
        System.out.println(match); // true
	
	# 原理
		* 这东西有个特点就是，对同一个密码进行N此 encode 返回的hash都不一样
		* 但是对不同的hash进行校验，都能校验成功

		* 原理就是，hash结果中已经包含了salt信息
	
