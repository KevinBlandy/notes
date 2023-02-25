---------------------
认证
---------------------
	# 组件
		SecurityContextHolder - SecurityContextHolder 是 Spring Security 存储 认证 用户细节的地方。
		SecurityContext - 是从 SecurityContextHolder 获得的，包含了当前认证用户的 Authentication （认证）。
		Authentication - 可以是 AuthenticationManager 的输入，以提供用户提供的认证凭证或来自 SecurityContext 的当前用户。
		GrantedAuthority - 在 Authentication （认证）中授予委托人的一种权限（即role、scope等）。
		AuthenticationManager - 定义 Spring Security 的 Filter 如何执行 认证 的API。
		ProviderManager - 最常见的 AuthenticationManager 的实现。
		AuthenticationProvider - 由 ProviderManager 用于执行特定类型的认证。