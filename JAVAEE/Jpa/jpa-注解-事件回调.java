---------------------
生命周期相关的注解
---------------------
	@PrePersist
	@PostPersist
		* 在新增前后调用
	
	@PreUpdate
	@PostUpdate 
		* 在修改前后调用
	
	@PreRemove 
	@PostRemove 
		* 在删除前后调用

	@PostLoad
		* entity被加载(find, load....)之后调用
	
	@EntityListeners
		|-Class[] value();

		* 在实体对象上指定外部周期的实现类, 可以指定多个
		* 该类中使用上述的注解标识的方法, 会在指定的阶段被调用
		* 事件方法的参数, 就可以设置为当前实体对象
	
	# 事件方法都是同步机制
		* 一旦报错将会影响所有底层代码执行,
		* 实际工作中实现这些方法的时候, 方法体里面开启异步线程或者消息队列来异步处理日志
	
	# 父级实体 @MappedSuperclass 上的相关注解, 会被子类继承

	
		