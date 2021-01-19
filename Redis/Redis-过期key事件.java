
1,配置项在 redis.conf 的 906 行左右
	notify-keyspace-events ""

3,这里需要配置 notify-keyspace-events 的参数为 "Ex" x 代表了过期事件
	notify-keyspace-events "Ex"

