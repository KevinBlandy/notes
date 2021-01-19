
# 代码配置
	恶心,太长,不看

# 用XML配置线程池
	
	<!-- 配置线程池 -->
	<thread-pools>
		<thread-pool alias="defaultDiskPool" min-size="1" max-size="3" />
		<thread-pool alias="defaultWriteBehindPool" min-size="1" max-size="3" />
		<thread-pool alias="cache2Pool" min-size="2" max-size="2" />
	</thread-pools>

	<!-- 统一指定处理事件的线程池 -->
	<event-dispatch thread-pool="defaultEventPool" />
	<!-- 统一处理写后工作的线程池 -->
	<write-behind thread-pool="defaultWriteBehindPool" />
	<!-- 统一磁盘序列化的线程池 -->
	<disk-store thread-pool="defaultDiskPool" />

	<cache alias="cache2">
		<key-type>java.lang.Long</key-type>
		<value-type>java.lang.String</value-type>
		<loader-writer>
			<class>org.ehcache.docs.plugs.ListenerObject</class>
			<!-- 特别指定当前cache,处理写后工作的线程池  -->
			<write-behind thread-pool="cache2Pool">
				<batching batch-size="5">
					<max-write-delay unit="seconds">10</max-write-delay>
				</batching>
			</write-behind>
		</loader-writer>
		<!-- 特别指定当前cache,事件处理线程池 -->
		<listeners dispatcher-thread-pool="cache2Pool" />
		<resources>
			<heap unit="entries">10</heap>
			<disk unit="MB">10</disk>
		</resources>
		<!-- 特别指定当前cache,磁盘序列化的线程池 -->
		<disk-store-settings thread-pool="cache2Pool" writer-concurrency="2" />
	</cache>