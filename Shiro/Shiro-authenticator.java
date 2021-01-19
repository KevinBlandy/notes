--------------------------------
authenticator					|
--------------------------------
	# Authenticator 的职责是验证用户帐号,是 Shiro API 中身份验证核心的入口点
		public AuthenticationInfo authenticate(AuthenticationToken authenticationToken)throws AuthenticationException;

		* 验证成功,将返回 AuthenticationInfo 验证信息,信息中包含了身份及凭证
		* 如果验证失败将抛出相应的 AuthenticationException 实现
	
	# SecurityManager 接口继承了 Authenticator

	# Authenticator 还有一个实现:ModularRealmAuthenticator 
		* 它会委托多个 Realm 进行验证,验证规则通过 AuthenticationStrategy 接口指定
		* 默认提供 AuthenticationStrategy 的实现:
			FirstSuccessfulStrategy
				* 只要有一个 Realm 验证成功即可
				* 只返回第一个 Realm 身份验证成功的认证信息,其他的忽略

			AtLeastOneSuccessfulStrategy
				* 只要有一个 Realm 验证成功即可('默认')
				* 返回所有 Realm 身份验证成功的认证信息

			AllSuccessfulStrategy
				* 所有 Realm 验证成功才算成功
				* 只要有一个验证失败,抛出异常
				* 返回所有 Realm 身份验证成功的
		
			

	# ini配置 Authenticator
		authenticator=org.apache.shiro.authc.pam.ModularRealmAuthenticator					//实例化 ModularRealmAuthenticator
		securityManager.authenticator=$authenticator
			* 指定 securityManager 的 authenticator 实现

		allSuccessfulStrategy=org.apache.shiro.authc.pam.AllSuccessfulStrategy				//实例化 AllSuccessfulStrategy
		securityManager.authenticator.authenticationStrategy=$allSuccessfulStrategy
			* 设置 securityManager.authenticator 的 authenticationStrategy
		
		myRealm1=com.github.zhangkaitao.shiro.chapter2.realm.MyRealm1
		myRealm2=com.github.zhangkaitao.shiro.chapter2.realm.MyRealm2
		myRealm3=com.github.zhangkaitao.shiro.chapter2.realm.MyRealm3
		securityManager.realms=$myRealm1,$myRealm3
			* 设置n个realm
	
	# 代码
		//1、获取 SecurityManager 工厂，此处使用 Ini 配置文件初始化 SecurityManager
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
		
		//2、得到 SecurityManager 实例 并绑定给 SecurityUtils
		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		
		//3、得到 Subject 及创建用户名/密码身份验证 Token（即用户身份/凭证）
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("Kevin", "123");
		subject.login(token);
		
		//4，得到一个身份集合，其包含了 Realm 验证成功的身份信息。校验模式根据	securityManager.authenticator 的 authenticationStrategy 来决定
		//校验失败,抛出异常
		PrincipalCollection principalCollection = subject.getPrincipals();
		
		System.out.println(principalCollection);
	
	# 自定义:AuthenticationStrategy
		public interface AuthenticationStrategy {
			//在所有 Realm 验证之前调用
			AuthenticationInfo beforeAllAttempts(Collection<? extends Realm> realms, AuthenticationToken token) throws AuthenticationException;
			//在每个 Realm 之前调用
			AuthenticationInfo beforeAttempt(Realm realm, AuthenticationToken token, AuthenticationInfo aggregate) throws AuthenticationException;
			//在每个 Realm 之后调用
			AuthenticationInfo afterAttempt(Realm realm, AuthenticationToken token, AuthenticationInfo singleRealmInfo, AuthenticationInfo aggregateInfo, Throwable t)throws AuthenticationException;
			//在所有 Realm 之后调用
			AuthenticationInfo afterAllAttempts(AuthenticationToken token, AuthenticationInfo aggregate) throws AuthenticationException;
		}
		
		* 每个 AuthenticationStrategy 实例都是无状态的,所有每次都通过接口将相应的认证信
		* 息传入下一次流程,通过如上接口可以进行如合并/返回第一个验证成功的认证信息
	
