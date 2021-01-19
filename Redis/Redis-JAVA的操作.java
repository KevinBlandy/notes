-----------------------
Redis-JAVAAPI的操作		|
-----------------------
	* jedis-2.8.1.jar
	
	<dependency>
		<groupId>redis.clients</groupId>
		<artifactId>jedis</artifactId>
		<version>2.9.0</version>
	</dependency>

一,连接操作
	//根据ip和端口创建连接对象
	Jedis jedis = new Jedis("localhost",6379);
	/*
		密码验证,如果有设置redis密码
		jedis.auth('password');
	*/
二,简单的存储读取
	jedis.set("key","value");
	System.out.println(jedis.get("key"));
	jedis.close();

二,设置key的超时时间
	jedis.expire(key, seconds);、

三,连接池

    public static void main(String[] args) {
        // 构建连接池配置信息
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 设置最大连接数
        jedisPoolConfig.setMaxTotal(50);
        // 构建连接池
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "127.0.0.1", 6379);
        // 从连接池中获取连接
        Jedis jedis = jedisPool.getResource();
        // 读取数据
        System.out.println(jedis.get("mytest"));
        // 将连接还回到连接池中
        jedisPool.returnResource(jedis);
        // 释放连接池
        jedisPool.close();
    }

四,分片式连接池
	 public static void main(String[] args) {
        // 构建连接池配置信息
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        // 设置最大连接数
        poolConfig.setMaxTotal(50);
        // 定义集群信息
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();

		//添加一个集群的节点
        shards.add(new JedisShardInfo("127.0.0.1", 6379));

        // 定义集群连接池
        ShardedJedisPool shardedJedisPool = new ShardedJedisPool(poolConfig, shards);

        ShardedJedis shardedJedis = null;
        try {
            // 从连接池中获取到jedis分片对象
            shardedJedis = shardedJedisPool.getResource();

            // 从redis中获取数据
            String value = shardedJedis.get("mytest");
            System.out.println(value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != shardedJedis) {
                // 关闭，检测连接是否有效，有效则放回到连接池中，无效则重置状态
                shardedJedis.close();
            }
        }
        // 关闭连接池
        shardedJedisPool.close();

    }