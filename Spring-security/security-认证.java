------------------
认证			  |
------------------
	# 各个组件
		UsernamePasswordAuthenticationFilter


		UsernamePasswordAuthenticationToken

		AuthenticationManager

		AuthenticationProvider

		GrantedAuthority
			* 表示角色信息接口
			* 实现
				JaasGrantedAuthority
				SimpleGrantedAuthority
				SwitchUserGrantedAuthority

		UserDetailsService
			* 根据用户名, 检索用户的接口
			* 只有一个方法
				UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
				
				* 该方法只要返回null, 就表示认证失败
			
			
		UserDetails
			* 表示用户的详情接口
			* 抽象方法
				Collection<? extends GrantedAuthority> getAuthorities();
				String getPassword();
				String getUsername();
				boolean isAccountNonExpired();
				boolean isAccountNonLocked();
				boolean isCredentialsNonExpired();
				boolean isEnabled();
			
			* 子实现类
				User
					private String password;							// 密码
					private final String username;						// 用户名
					private final Set<GrantedAuthority> authorities;	// 授权信息
					private final boolean accountNonExpired;			// 账户未过期
					private final boolean accountNonLocked;				// 账户未锁定
					private final boolean credentialsNonExpired;		// 密码未过期
					private final boolean enabled;						// 账户未禁用
		
		PasswordEncoder
			* 密码的编码接口
			* 实现类
				BCryptPasswordEncoder
				DelegatingPasswordEncoder
		

		RememberMeServices
			* 记住我的接口
				Authentication autoLogin(HttpServletRequest request, HttpServletResponse response);
				void loginFail(HttpServletRequest request, HttpServletResponse response);
				void loginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication);
			
			* 实现类
				NullRememberMeServices
				PersistentTokenBasedRememberMeServices
				TokenBasedRememberMeServices
			
