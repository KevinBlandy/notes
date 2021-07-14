
------------------------
心跳
------------------------
	# 在创建链接的时候，设置heart-beat，来开启心跳
		heart-beat:<cx>,<cy>
	
	# Req/Resp
		CONNECT
		accept-version:1.2
		heart-beat:<cx>,<cy>
		host:stomp.github.org

		^@

		CONNECTED
		version:1.2
		heart-beat:<sx>,<sy>

		^@

		* 其中<cx>, <cy>, <sx>, <sy>分别代表一个以毫秒为单位的数字.

		* <cx>:client能保证的发送心跳的最小间隔, 如果是0代表client不发送心跳.
		* <cy>:client希望收到server心跳的间隔, 如果是0代表client不希望收到server的心跳.
		
		* <sx>:server能保证的发送心跳的最小间隔, 如果是0代表server不发送心跳.
		* <sy>:server希望收到client心跳的间隔, 如果是0代表server不希望收到client的心跳.

		* 如果在建立连接时没有心跳header, 默认当作heart-beat:0,0. 也就是不发心跳, 也不希望对方发心跳

	# 加入心跳header进行连接后, 最终协商得出发送心跳的频率的逻辑如下
			
		* 对于client来说, 取 <cx> 与 <sy> 的最大值, 也就是说client会取client最小能发送的间隔与server希望client发送间隔的最大值来发送心跳.
		* 如果<cx>或<sy>中任何一个为0, client都不发送心跳.

		* 对于server来说, 取 <sx> 与 <cy> 的最大值, 也就是说server取server最小能发送的间隔与client希望server发送间隔的最大值来发送心跳.
		* 如果<sx>或<cy>中任何一个为0, server都不发送心跳.

	# client和server根据心跳来判定对方已经挂掉了的逻辑如下
		
		* 以server为例, 假设经过协商, client每10秒发送一个心跳.
		
		* client必须在10秒以内给server至少发送一次数据, 不管是心跳还是正常数据.
		* 如果在10秒内client未发送数据, 那么server认为与client的stomp连接已经挂掉.
	
