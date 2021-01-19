-------------------------------------
zookeeper							 |
-------------------------------------
	# zookeeper自带的客户端是官方提供的,比较底层
		<dependency>
		    <groupId>org.apache.zookeeper</groupId>
		    <artifactId>zookeeper</artifactId>
		    <version>3.4.13</version>
		</dependency>

-------------------------------------
基本的API							 |
-------------------------------------
	Zookeeper
		* 负责建立与服务端的连接,并且提供了方法进行操作

	Watcher
		* 一个标准的事件处理器接口
	

-------------------------------------
连接的创建							 |
-------------------------------------
	# 基本的连接创建
		ZooKeeper(String connectString, int sessionTimeout, Watcher watcher)
		ZooKeeper(String connectString, int sessionTimeout, Watcher watcher,boolean canBeReadOnly)
		ZooKeeper(String connectString, int sessionTimeout, Watcher watcher,long sessionId, byte[] sessionPasswd)
		ZooKeeper(String connectString, int sessionTimeout, Watcher watcher,long sessionId, byte[] sessionPasswd, boolean canBeReadOnly)

		* connectString 连接的地址可以是集群中的多个或者一个
			"127.0.0.1:3000,127.0.0.1:3001,127.0.0.1:3002"

			* 如果是多个连接的话,其中一个无法连接时,会尝试另一个连接
			* 也可以基于某个节点目录进行连接
				"127.0.0.1:3000,127.0.0.1:3001,127.0.0.1:3002/app/a"
		* sessionTimeout 超时时间单位为毫秒
		* Watcher
			* 是一个事件监听,负责监听处理连接事件
		* canBeReadOnly 
			* 用于标识当前会话是否支持"read-only"模式
			* 默认情况下,在ZK集群中,一个机器如果和集群中的半数以及半数以上的机器失去连接,那么这个机器将不再处理客户端请求(读请求+写请求均不处理)
			* 但是有时候我们希望在发生此类故障时不影响读取请求的处理,这个就是zk的 read-only 模式
		* sessionId
		* sessionPasswd
			* 会话id和会话密钥
		
-------------------------------------
节点的操作							 |
-------------------------------------
	# 创建节点
		String create(final String path, byte data[], List<ACL> acl,CreateMode createMode)
		void create(final String path, byte data[], List<ACL> acl,CreateMode createMode,  StringCallback cb, Object ctx)

		path 
			* 节点地址
		data
			*  数据
		acl
			* ACL权限,ZooDefs.Ids 有预定义一大堆的权限,其中:OPEN_ACL_UNSAFE 表示允许所有权限

		createMode
			* 数据类型,CreateMode 枚举

	

	# 获取节点
		byte[] getData(String path, boolean watch, Stat stat)
		void getData(String path, boolean watch, DataCallback cb, Object ctx)
		
		watch
			* 是否要监听事件,如果该值为 true,事件触发,则会调用 Zookeeper 实例的Watcher实例
			* 事件只会触发一次

		byte[] getData(final String path, Watcher watcher, Stat stat);
		void getData(final String path, Watcher watcher, DataCallback cb, Object ctx)

		Watcher
			* 设置path节点事件监听器,如果发生事件,则通知该 Watcher 实例,而不通知Zookeeper的Watcher实例
			* 事件触发的时候,每个监听都会执行
				zooKeeper.getData("/root", new DefaultWatcher("root0"), null);
				zooKeeper.getData("/root", new DefaultWatcher("root1"), null);
				zooKeeper.getData("/root", new DefaultWatcher("root2"), null);
				zooKeeper.getData("/root", new DefaultWatcher("root3"), null);
				zooKeeper.setData("/root", "new".getBytes(), -1);		//修改事件触发,上面三个监听都会执行,但是执行顺序不一定
		

	# 修改节点
		Stat setData(final String path, byte data[], int version)
		void setData(final String path, byte data[], int version,StatCallback cb, Object ctx)
		
		path
			* 路径
		data
			* 数据
		version
			* 乐观锁版本号(如果值为 -1 表示由系统维护,程序不参与)

	# 删除节点
		void delete(final String path, int version)
		void delete(final String path, int version, VoidCallback cb,Object ctx)

		path
			* 路径
		version
			*  乐观锁版本号(如果值为 -1 表示由系统维护,程序不参与)
	
	
	# 获取子节点
		List<String> getChildren(String path, boolean watch);
		void getChildren(String path, boolean watch, Children2Callback cb,Object ctx)
		void getChildren(String path, boolean watch, ChildrenCallback cb,Object ctx)
		List<String> getChildren(String path, boolean watch, Stat stat)

		List<String> getChildren(final String path, Watcher watcher)
		void getChildren(final String path, Watcher watcher,Children2Callback cb, Object ctx)
		void getChildren(final String path, Watcher watcher,ChildrenCallback cb, Object ctx)
		List<String> getChildren(final String path, Watcher watcher,Stat stat)
	
	# 判断节点是否存在
		Stat exists(String path, boolean watch) 
		void exists(String path, boolean watch, StatCallback cb, Object ctx)
		Stat exists(final String path, Watcher watcher)
		void exists(final String path, Watcher watcher,StatCallback cb, Object ctx)



-------------------------------------
权限								 |
-------------------------------------
	# 主要类
		Id{
			private String scheme;
			private String id;
		}
			
		* 授权类型和对象

		ZooDefs.Perms{
			int READ = 1 << 0;
			int WRITE = 1 << 1;
			int CREATE = 1 << 2;
			int DELETE = 1 << 3;
			int ADMIN = 1 << 4;
			int ALL = READ | WRITE | CREATE | DELETE | ADMIN;
		}
			
		* 权限常量

		ACL{
			private int perms;
			private Id id;
		}
		
		* ACL对象
			
	
	# 当schema为digest时ACL权限的创建
		//授权对象
		String digest = DigestAuthenticationProvider.generateDigest("poype:123456");
		//授权的类型
		Id id = new Id("digest", digest);
		//创建其权限等信息
		ACL acl = new ACL(ZooDefs.Perms.READ | ZooDefs.Perms.WRITE, id);

		* 当schema为digest时,授权对象是username:Base64(Sha1(username:password))形式的字符串
		* 为了简化编程,ZooKeeper提供了对应的工具类
		* DigestAuthenticationProvider.generateDigest 这个方法生成对应的授权对象字符串
	
	# 当schema为ip时ACL权限的创建
		ACL acl = new ACL(ZooDefs.Perms.READ | ZooDefs.Perms.WRITE, new Id("ip","192.168.1.110"));
	
	# 客户端添加身份信息
		zooKeeper.addAuthInfo("digest","poype:123456".getBytes());
