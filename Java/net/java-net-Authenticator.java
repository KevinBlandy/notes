--------------------------
Authenticator
--------------------------
	# 身份验证器
	
	# 静态方法
		static Authenticator getDefault()

		static PasswordAuthentication requestPasswordAuthentication(
                                            InetAddress addr,
                                            int port,
                                            String protocol,
                                            String prompt,
                                            String scheme)
		
		static PasswordAuthentication requestPasswordAuthentication(
                                            String host,
                                            InetAddress addr,
                                            int port,
                                            String protocol,
                                            String prompt,
                                            String scheme)
		
		static PasswordAuthentication requestPasswordAuthentication(
                                    String host,
                                    InetAddress addr,
                                    int port,
                                    String protocol,
                                    String prompt,
                                    String scheme,
                                    URL url,
                                    RequestorType reqType)
		


		static PasswordAuthentication requestPasswordAuthentication(
                                    Authenticator authenticator,
                                    String host,
                                    InetAddress addr,
                                    int port,
                                    String protocol,
                                    String prompt,
                                    String scheme,
                                    URL url,
                                    RequestorType reqType)
			
	
	# 实例方法
		public PasswordAuthentication requestPasswordAuthenticationInstance(String host,
                                          InetAddress addr,
                                          int port,
                                          String protocol,
                                          String prompt,
                                          String scheme,
                                          URL url,
                                          RequestorType reqType)