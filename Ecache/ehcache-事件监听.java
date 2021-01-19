
-----------------------------
事件监听					 |
-----------------------------
	# 实现 CacheEventListener 接口
		import org.ehcache.event.CacheEvent;
		import org.ehcache.event.CacheEventListener;
		import org.ehcache.event.EventType;
		import org.slf4j.Logger;
		import org.slf4j.LoggerFactory;

		public class Li implements CacheEventListener<Long,String>{
			
			private static final Logger LOGGER = LoggerFactory.getLogger(Li.class);

			@Override
			public void onEvent(CacheEvent<? extends Long, ? extends String> event) {
				Long key = event.getKey();
				String newValue = event.getNewValue();
				String oldValue = event.getOldValue();
				EventType eventType = event.getType();
				LOGGER.info("key={},newValue={},oldValue={},eventType={}",key,newValue,oldValue,eventType);
			}
		}

	
	# xml配置

		<listeners>
			<!-- 指定一个事件监听 -->
			<listener>
				<!-- CacheEventListener 实现 -->
				<class>com.tedi.door.utils.Li</class>
				<!-- 处理方式,同步/异步 -->
				<event-firing-mode>ASYNCHRONOUS</event-firing-mode>
				<!-- 事件排序策略 -->
				<event-ordering-mode>UNORDERED</event-ordering-mode>
				<!-- 在什么事件发生的时候执行触发监听,可以有多个 -->
				<events-to-fire-on>CREATED</events-to-fire-on>
			</listener>
		</listeners>
