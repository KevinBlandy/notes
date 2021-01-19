--------------------------------
ip控制							|
--------------------------------
	# 类库
		IpFilterRuleType
			* 拦截的结果枚举
				ACCEPT
			    REJECT

		IpFilterRule
			*规则描述
			|-IpSubnetFilterRule

		AbstractRemoteAddressFilter
			* 不同的拦截器
			|-UniqueIpFilter
			|-RuleBasedIpFilter

	# AbstractRemoteAddressFilter
		* 抽象类,提供了一个抽象方法,用于过滤ip
			boolean accept(ChannelHandlerContext ctx, T remoteAddress) throws Exception;
		
		* 如果ip处于黑名单,则会触发异常
			 throw new IllegalStateException("cannot determine to accept or reject a channel: " + ctx.channel());

		* 提供可覆写的事件方法
			void channelAccepted(ChannelHandlerContext ctx, T remoteAddress);
				* 客户端验证通过时触发

			ChannelFuture channelRejected(ChannelHandlerContext ctx, T remoteAddress);
				* 客户端验证失败时触发
				* ip拦截触发(可以在里面实现要给客户端响应的数据)
				* 如果不响应任何数据,返回 null

	# IpFilterRule
		* ip规则的描述,是一个接口,实现类:IpSubnetFilterRule
			IpSubnetFilterRule(String ipAddress, int cidrPrefix, IpFilterRuleType ruleType)
			IpSubnetFilterRule(InetAddress ipAddress, int cidrPrefix, IpFilterRuleType ruleType)


--------------------------------
UniqueIpFilter					|
--------------------------------
	# 唯一ip连接的过滤器
	# 一个ip只能保持一个连接
	# accept() 实现
		@Override
		protected boolean accept(ChannelHandlerContext ctx, InetSocketAddress remoteAddress) throws Exception {
			final InetAddress remoteIp = remoteAddress.getAddress();
			if (!connected.add(remoteIp)) {
				return false; // 该ip已经存在于Set集合,阻止连接
			} else {
				ctx.channel().closeFuture().addListener(new ChannelFutureListener() {
					@Override
					public void operationComplete(ChannelFuture future) throws Exception {
						// 断开连接时从Set集合移除
						connected.remove(remoteIp);
					}
				});
				return true;
			}
		}

--------------------------------
RuleBasedIpFilter				|
--------------------------------
	# 根据规则来过滤ip
	# 构造方法
		RuleBasedIpFilter(IpFilterRule... rules)
	# accept() 实现
	    @Override
		protected boolean accept(ChannelHandlerContext ctx, InetSocketAddress remoteAddress) throws Exception {
			for (IpFilterRule rule : rules) {
				if (rule == null) {
					break;
				}
				if (rule.matches(remoteAddress)) {
					return rule.ruleType() == IpFilterRuleType.ACCEPT;
				}
			}
			return true;
		}
