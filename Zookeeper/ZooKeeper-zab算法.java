----------------------------
ZAB算法						|
----------------------------
	# Zookeeper Atomic Broadcast (Zookeeper原子广播)
		* 所有的写操作都必须通过Leader完成,Leader写入本地日志后再复制到所有的Follower节点
		* 一旦Leader节点无法工作,ZAB协议能够自动从Follower节点中重新选出一个合适的替代者,即新的Leader
		* 该过程即为领导选举,该领导选举过程,是ZAB协议中最为重要和复杂的过程

	# Leader进行写操作,主要分为五步
		* 客户端向Leader发起写请求
		* Leader将写请求以Proposal的形式发给所有Follower并等待ACK
		* Follower收到Leader的Proposal后返回ACK
		* Leader得到过半数的ACK(Leader对自己默认有一个ACK)后向所有的Follower和Observer发送Commmit
		* Leader将处理结果返回给客户端

		* Leader并不需要得到Observer的ACK,即Observer无投票权
		* Leader不需要得到所有Follower的ACK,只要收到过半的ACK即可,同时Leader本身对自己有一个ACK
		* Observer虽然无投票权,但仍须同步Leader的数据从而在处理读请求时可以返回尽可能新的数据
	
	# 未Commit过的消息对客户端不可见

	# 支持的leader选举算法
		* 可通过electionAlg配置项设置ZooKeeper用于领导选举的算法

		0 基于UDP的LeaderElection

		1 基于UDP的FastLeaderElection

		2 基于UDP和认证的FastLeaderElection

		3 基于TCP的FastLeaderElection(默认,另外三种算法已经被弃用,并且有计划在之后的版本中将它们彻底删除而不再支持)


	# FastLeaderElection原理
		* myid
			
			* 每个ZooKeeper服务器,都需要在数据文件夹下创建一个名为myid的文件
			* 该文件包含整个ZooKeeper集群唯一的ID(整数)


		* zxid
			
			* 类似于RDBMS中的事务ID,用于标识一次更新操作的Proposal ID
			* 为了保证顺序性,该zkid必须单调递增
			* 因此ZooKeeper使用一个64位的数来表示,高32位是Leader的epoch,从1开始,每次选出新的Leader,epoch加一
			* 低32位为该epoch内的序号,每次epoch变化,都将低32位的序号重置,这样保证了zkid的全局递增性
		



	# 服务器状态
		LOOKING 不确定Leader状态,该状态下的服务器认为当前集群中没有Leader,会发起Leader选举

		FOLLOWING 跟随者状态,表明当前服务器角色是Follower,并且它知道Leader是谁

		LEADING 领导者状态,表明当前服务器角色是Leader,它会维护与Follower间的心跳

		OBSERVING 观察者状态,表明当前服务器角色是Observer,与Folower唯一的不同在于不参与选举,也不参与集群写操作时的投票