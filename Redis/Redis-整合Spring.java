---------------------------
Redis-整合spring			|
---------------------------

<!-- 引入外部文件 -->
<context:property-placeholder location="classpath:redis.properties" ignore-unresolvable="true"/>
<!-- 连接池配置 -->
<bean id="jedisPoolConfig" class="redis.clients.jedis.JedisPoolConfig">
	<!-- 最大连接数 -->
	<property name="maxTotal" value="${redis.maxTotal}"/>

	<property name="testWhileIdle" value="${redis.testWhileIdle}"/>
	<property name="minEvictableIdleTimeMillis" value="${redis.minEvictableIdleTimeMillis}"/>
	<property name="numTestsPerEvictionRun" value="${redis.numTestsPerEvictionRun}"/>
	<property name="maxIdle" value="${redis.maxIdle}"/>
	<property name="minIdle" value="${redis.minIdle}"/>
</bean>
<!-- 分片式连接池 -->
<bean class="redis.clients.jedis.ShardedJedisPool" destroy-method="close">
	<constructor-arg index="0" ref="jedisPoolConfig"/>
	<constructor-arg index="1">
		<list>
			<!-- 集群的节点1信息 -->
			<bean class="redis.clients.jedis.JedisShardInfo">
				<constructor-arg index="0" value="${redis.node1.ip}"/>
				<constructor-arg index="1" value="${redis.node1.port}"/>
			</bean>
			<!-- 集群的节点2信息 
			<bean class="redis.clients.jedis.JedisShardInfo">
				<constructor-arg index="0" value="${redis.node2.ip}"/>
				<constructor-arg index="1" value="${redis.node2.port}"/>
			</bean>-->
		</list>
	</constructor-arg>
</bean>


redis.maxTotal=100
redis.node1.ip=127.0.0.1
redis.node1.port=6379

---------------------------
Redis-代码设计思想			|
---------------------------

**
 * Redis Service
 * */
@Service
public class RedisService {
	@Autowired
	private ShardedJedisPool shardedJedisPool;
	/**
	 * 往缓存中添加记录
	 * */
	private <T> T execut(Function<ShardedJedis ,T> fun){
		 ShardedJedis shardedJedis = null;
	        try{
	        	// 获取Redis连接
	        	shardedJedis = shardedJedisPool.getResource();
	        	return fun.callback(shardedJedis);
	        }finally{
	        	 //关闭连接
	            shardedJedis.close();
	        }
	}
	/**
	 * 设置值
	 * */
	public String set(final String key,final String value){
		return this.execut(new Function<ShardedJedis, String>() {
			public String callback(ShardedJedis jedis) {
				return jedis.set(key, value);
			}
		});
	}
	/**
	 * 设置值,同时设置生存时间
	 * 单位为秒
	 * */
	public String set(final String key,final String value,final Integer seconds){
		return this.execut(new Function<ShardedJedis, String>() {
			public String callback(ShardedJedis jedis) {
				String result = jedis.set(key, value);
				jedis.expire(key, seconds);
				return result;
			}
		});
	}
	/**
	 * 获取值
	 * */
	public String get(String key){
		return this.execut(new Function<ShardedJedis, String>() {
			public String callback(ShardedJedis jedis) {
				return jedis.get(key);
			}
		});
	}
	/**
	 * 根据Key删除数据
	 * */
	public Long delete(String key){
		return this.execut(new Function<ShardedJedis, Long>() {
			public Long callback(ShardedJedis jedis) {
				return jedis.del(key);
			}
		});
	}
	/**
	 * 根据Key删除数据
	 * */
	public Long expire(String key,Integer seconnds){
		return this.execut(new Function<ShardedJedis, Long>() {
			public Long callback(ShardedJedis jedis) {
				return jedis.expire(key, seconnds);
			}
		});
	}
}
interface Function <T,E>{
	public E callback(T t);
}

=================================================JK8  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.function.Function;

/**
 * Created by KevinBlandy on 2017/1/22 17:31
 * Redis
 */
@Service
public class RedisService {
    @Autowired
    private ShardedJedisPool shardedJedisPool;

    /**
     * 回调执行
     * @param fun
     * @param <T>
     * @return
     */
    private <T> T execute(Function<ShardedJedis,T> fun){
        ShardedJedis shardedJedis = null;
        try{
            // 获取Redis连接
            shardedJedis = shardedJedisPool.getResource();
            return fun.apply(shardedJedis);
        }finally{
            //关闭连接
            shardedJedis.close();
        }
    }

    /**
     * 添加新的值到redis
     * @param key
     * @param value
     * @return
     */
    public String set(final String key,final String value){
        return this.execute(shardedJedis -> shardedJedis.set(key,value));
    }

    /**
     * 添加新的值到缓存,并且设置生命周期
     * @param key
     * @param value
     * @param seconds
     * @return
     */
    public String set( String key, String value, Integer seconds){
        return this.execute(shardedJedis -> {
            String result = shardedJedis.set(key,value);
            shardedJedis.expire(key,seconds);
            return result;
        });
    }

    /**
     * 根据key尝试读取一个值
     * @param key
     * @return
     */
    public String get(String key){
        return this.execute(shardedJedis -> shardedJedis.get(key));
    }

    /**
     * 根据key尝试删除一个值
     * @param key
     * @return
     */
    public Long delete(String key){
        return this.execute(shardedJedisPool -> shardedJedisPool.del(key));
    }

    /**
     * 根据key,设置值的生命周期
     * @param key
     * @param seconnds
     * @return
     */
    public Long expire(String key,Integer seconnds){
        return this.execute(shardedJedis -> shardedJedis.expire(key,seconnds));
    }
}
