----------------------------
realm						|
----------------------------
	# 顶层接口
		public interface Realm {
			//返回唯一的 realm 名称
			String getName();
			//当前realm是否支持此token
			boolean supports(AuthenticationToken token);
			//根据token,获取认证信息
			AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException;
		}

	
	# ini 配置一个或者多个realm
		[main]
		myRealm1=practice.shiro.realm.MyRealm1
		myRealm2=practice.shiro.realm.MyRealm2
		securityManager.realms=$myRealm1,$myRealm2

		* securityManager 会按照 realms 指定的顺序进行身份认证
		* 如果删除"securityManager.realms=$myRealm1,$myRealm2",那么 securityManager 会按照 realm 声明的顺序进行使用(自动发现)
		* 但是一旦手动的设置了:securityManager.realms 属性,那么自动发现会失效,将严格按照 securityManager.realms 声明的顺序来进行身份验证
	
	
	# Shiro提供的默认Realm
													Realm
														|
													CachingRealm
														|
													CachingRealm
														|
													AuthenticatingRealm
														|
													AuthorizingRealm
														|
								|---------------|--------------|---------------|
						SimpleAccountRealm	JDBCRealm	JndiLdapRealm	AbstractLdapRealm
							|													|
				TextConfigurationRealm									ActiveDirectoryRealm
					|-------------|
			PropertiesRealm		IniRealm

			* 一般继承 AuthorizingRealm(授权)即可

			* IniRealm
				* [users]部分指定用户名/密码及其角色
				* [roles]部分指定角色即权限信息
			
			* PropertiesRealm
				* user.username=password,role1,role2 指定用户名/密码及其角色
				* role.role1=permission1,permission2 指定角色及权限信息
			
			* JdbcRealm
				* 通过jdbc来进行验证和授权
			

----------------------------
JdbcRealm 的使用			|
----------------------------
	