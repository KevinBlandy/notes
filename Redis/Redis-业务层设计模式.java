import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
/**
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
