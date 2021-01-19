----------------------------
INI配置						|
----------------------------
	# 从INI资源构建SecurityManager
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);

		* 支持从任意协议来加载配置文件
			file:
			classpath:
			url:

	# INI配置也可以通过编程方式构建
		Ini ini = new Ini();
		ini.setSectionProperty("", "", "");

		Factory<SecurityManager> factory = new IniSecurityManagerFactory(ini);
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);

		* Ini,跟 Properties 差不多使用,支持通过编程的方式来设置kv
		
----------------------------
INI配置详解					|
----------------------------
	[main]
		# 该部分是配置应用程序的SecurityManager实例及其任何依赖关系(如Realm)
		# 定义一个对象
			myRealm = io.springboot.realm.MyRealm		//实例化一个对象

			* 如果对象实现了:org.apache.shiro.util.Nameable 接口
			* 那么在实例化完成后,会执行该接口的:setName(String str) 方法,把配置属性名称(myRealm)传递进来

		# 设置对象的属性,Shiro默认使用Apache Commons BeanUtils来设置这些属性
			myRealm.name = KevinBlandy					//设置该对象的username属性
			myRealm.age = 23							//设置对象的age属性
			
			* 设置的值不是原始的,而是另一个对象,可以使用美元符号 $ 来引用先前定义的实例
				sha256Matcher = org.apache.shiro.authc.credential.Sha256CredentialsMatcher
				myRealm.credentialsMatcher = $sha256Matcher				//设置 myRealm 的 credentialsMatcher 属性值为 sha256Matcher 对象
		
			* 嵌套属性设置(属性导航)
				securityManager.sessionManager.globalSessionTimeout = 1800000	//相当于,securityManager.getSessionManager().setGlobalSessionTimeout(1800000);
			
			* 字节数组值设置
				* 可以指定为Base64编码的字符串(默认值)
				* 也可以指定为十六进制编码的字符串
				securityManager.rememberMeManager.cipherKey = kPH+bIxk5D2deZiIxcaaaA==			//base64编码
				securityManager.rememberMeManager.cipherKey = 0x3707344A4093822299F31D008		//16进制字符串
			
			* 集合与map属性
				* 集合,对于集合和列表,只需指定逗号分隔的一组值或对象引用即可	
					sessionListener1 = com.company.my.SessionListenerImplementation							//定义俩对象
					sessionListener2 = com.company.my.other.SessionListenerImplementation

					securityManager.sessionManager.sessionListeners = $sessionListener1, $sessionListener2	//sessionListeners 是一个集合,设置了上面定义的俩属性
			
				* Map
					object1 = com.company.some.Class					//定义一个o1
					object2 = com.company.another.Class					//定义一个o2
	
					anObject = some.class.with.a.Map.property			//实例化一个对象
	
					anObject.mapProperty = key1:$object1, key2:$object2	//设置该对象的mapProperty为一个map对象,key1 = o1,key2=o2
					
					* 也可以使用其他对象作为键
						anObject.map = $objectKey1:$objectValue1, $objectKey2:$objectValue2

		# 注意点
			* 重名对象会覆盖
				myRealm = com.company.security.MyRealm
				myRealm = com.company.security.DatabaseRealm		//该对象会覆盖上一个对象
			
			* 默认SecurityManager
				* securityManager,实例是一个特殊的实例,已经实例化
				* 如果需要自己实现,那么按照上面定义的方法,覆写即可
			
	[users]
		# 只要定义了非空的[users]或[urls]部分,就会自动创建名为('iniRealm')的org.apache.shiro.realm.text.IniRealm 实例
			* 在[main]配置下可以使用: iniRealm 来引用它

		# 定义一组静态的用户帐户
			* 配置格式:用户名 = 密码,角色1,角色2,....角色n
				admin = secret
				lonestarr = vespa, goodguy, schwartz
				darkhelmet = ludicrousspeed, badguy, schwartz
			
			* 加密密码,可以使用MD5,Sha1,Sha256等对其进行加密
				[main]
				sha256Matcher = org.apache.shiro.authc.credential.Sha256CredentialsMatcher		//实例化一个 Sha256 的Matcher
				iniRealm.credentialsMatcher = $sha256Matcher									//设置iniRealm 的 credentialsMatcher

				[users]
				user1 = 2bb80d537b1da3e38bd30361aa855686bde0eacd7162fef6a25fe97bf527a25b		//创建一个用户,它的密码是经过sha256运算的密文
			
				* 还有 Md5CredentialsMatcher 等Matcher可以参考使用
				* 如果用户的密码字符串是Base64编码,而不是默认的十六进制,需要设置Matcher的storedCredentialsHexEncoded属性
					[main]
					# true = hex, false = base64:
					sha256Matcher.storedCredentialsHexEncoded = false
			

	[roles]
		# 来定义角色到权限的键/值映射
			* 配置格式:角色名称 = 权限1,权限2,权限3,...权限N
				admin = *
				manager = create,update,delete

		# 没有权限的角色
			* 角色不需要权限关联,则不需要在[roles]部分列出
			* 在[users]部分中定义角色名称就会默认创建角色(roles中不存在)
			
	[urls]
