-----------------
检索结果集
-----------------
	# 方法名或者@Query检索, 都需要自己定义返回的结果类型
		* 不同的返回值类型, 可能会影响到检索方式
	
	
	# 分页的结果集
		Page<User> findByName(String name, Pageable pageable);
			* Page 作为返回值, 会默认执行一个 COUNT 检索
				* 也可以通过静态函数, 构造一个不检索总记录数的分页检索: Pageable.unpaged()
			* 一次性检索出所有的结果, 并且包含了分页信息

		Slice<User> findByName(String name, Pageable pageable);
			* 不会检索总记录数量, 没有分页信息
			* 对Slice来说, 只知道是否有下一个Slice可用, 并不知道还有多少条

		List<User> findByName(String name, Pageable pageable);
			*  不会执行统计查询, 仅仅检索分页指定的数据
	

	# 流式结果集, 使用Java8的 Stream 作为结果集
		
		Stream<User> all();

		* 必须在只读事务内执行: @Transactional(readOnly = true), 并且整个过程必须要保持jdbc链接的有效性
			@Transactional(readOnly = true)
			public List<User> all() {
				Stream<User> stream = this.userRepository.all();
				return stream.collect(Collectors.toList());
			} 
		
		* 否则会抛出异常: InvalidDataAccessApiUsageException
	

		
	# 异步结果集, 使用 Future/CompletableFuture/ListenableFuture 作为结果集
		@Query("FROM User")
		Future<List<User>> all();

		@Query("FROM User")
		CompletableFuture<List<User>> all();
		
		@Query("FROM User")
		ListenableFuture<List<User>> all();


		* 异步方法, 在调用的时候就会立即返回
		* 查询任务会提交给 Spring TaskExecutor

	# 通用的结果集
		List<Map<String, Object>>
		Map<String, Object> 

		List<Object[]>
		Object[]
		
		Object

		* 支持这种通用的结果集
	
	# Projections 扩展
		* 仅仅检索实体的部分属性, 可以自定义部分属性的封装接口
			class User{
				id,
				phone,
				version,
				... // 忽略N多属性
			}

			public interface UserNameAndVersion { // 接口仅仅定义需要的属性 getter
				String getName();
				Integer getVersion();
			}
			
			public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor <User> {
				@Query("SELECT u.name, u.version FROM User AS u WHERE id = :id")
				UserNameAndVersion test(@Param("id")Integer id);
			}
			

		* 通过动态代理生成的类: org.springframework.data.jpa.repository.query.AbstractJpaQuery$TupleConverter$TupleBackedMap
		* 该类里面封装了一个map
		* 序列化为JSON
			{
				"target":{null:"Kevin", null:1},
				"targetClass":"org.springframework.data.jpa.repository.query.AbstractJpaQuery$TupleConverter$TupleBackedMap"
			}
		
		* 还可以定义关联的对象, 以及支持使用Spel表达式
	
	# 泛化结果集
		* 支持对结果集使用泛型, 但是要在参数中指定实际类型
		* 泛型只能使用: List,Page,Slice

			public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor <User> {
				@Query("FROM User WHERE id = :id")
				<T> List<T> all(@Param("id")Integer id, Class<T> target, Pageable page);
			}

			List<UserNameAndVersion> users = this.userRepository.all(1, UserNameAndVersion.class, PageRequest.of(0, 10)); // 运行时指定返回类型
			List<User> users = this.userRepository.all(1, User.class, PageRequest.of(0, 10));  // 运行时指定返回类型

