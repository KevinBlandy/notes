----------------------
Repository			  |
----------------------
	# 核心的 Repository
		Repository		// 标识接口，spring会加载
			|-CrudRepository // 基本的CRUD
				|PagingAndSortingRepository // 排序和分页查询
			|-QueryByExampleExecutor // 可以根据Example条件查询
					|-JpaRepository  // 继承分页,条件,crud的接口
						|-JpaRepositoryImplementation (实现了 JpaSpecificationExecutor 接口) // 接口
							|-SimpleJpaRepository // 集大成于一身，它是一个实现类
			
		QuerydslPredicateExecutor			// querydls的接口
			|-QuerydslJpaPredicateExecutor	// querydls的接口实现
			|-QuerydslKeyValueRepository

		JpaSpecificationExecutor // Specification动态查询接口
	
	# SimpleJpaRepository
		* 它其实就是 Repository 接口, 动态代理的类
		* 它是通过操作 EntityManager 来完成对数据库的操作
	
	# JpaSpecificationExecutor
		* 自成一排的Specification检索接口
	
	# QuerydslJpaPredicateExecutor
		* querydls的接口实现
	

----------------------
CrudRepository		  |
----------------------

public interface CrudRepository<T, ID> extends Repository<T, ID> {
	<S extends T> S save(S entity);
		* 保存和修改, 都是使用 sava 方法
		* 区别就是看实体entity是否有主键, 有则是更新, 无则是创建

		* 执行修改之前, 会先根据id执行一次读, 如果不存在, 则插入(???)

	<S extends T> Iterable<S> saveAll(Iterable<S> entities);
		* 持久化, 如果是自增id, id的值会回写到对象中

	Optional<T> findById(ID id);
		* 根据ID检索

	boolean existsById(ID id);
		* 根据ID判断是否存在

	Iterable<T> findAll();
	Iterable<T> findAllById(Iterable<ID> ids);
		* 检索所有或者根据ID检索一批记录

	long count();
		* 获取记录的总数量


	void deleteById(ID id);
	void delete(T entity);
	void deleteAll(Iterable<? extends T> entities);
	void deleteAll();
	
		* 执行这些删除的时候,会先根据ID检索记录 (deleteAll 会先检索所有记录)
		* 如果记录不存在(如果执行的是:deleteById,会直接抛出异常 EmptyResultDataAccessException ),会先插入,再删除
		* 如果记录已经存在,直接删除, 删除操作都是根据ID来删除

	
}
----------------------
JpaRepository		  |
----------------------
	public interface JpaRepository<T, ID> extends PagingAndSortingRepository<T, ID>, QueryByExampleExecutor<T> {
		
		<S extends T> Optional<S> findOne(Example<S> example);
		T getOne(ID id);
		List<T> findAllById(Iterable<ID> ids);
			
		
		<S extends T> List<S> saveAll(Iterable<S> entities);

		void flush();
		<S extends T> S saveAndFlush(S entity);
			* 保存并且立即刷新
		
		
		
		
		List<T> findAll();
		List<T> findAll(Sort sort);
		Iterable<T> findAll(Sort sort);
		Page<T> findAll(Pageable pageable);
		<S extends T> List<S> findAll(Example<S> example);
		<S extends T> List<S> findAll(Example<S> example, Sort sort);
		<S extends T> Page<S> findAll(Example<S> example, Pageable pageable);


		
		
		<S extends T> long count(Example<S> example);
		<S extends T> boolean exists(Example<S> example);

		void deleteAllInBatch();
		void deleteInBatch(Iterable<T> entities);
			* 批量删除, 不会先执行查询,
			* deleteAllInBatch 直接执行: DELETE FROM `table`;
			* deleteInBatch 如果有多个参数, 通过or条件删除: DELETE FRROM `table` WHERE `id` = ? OR `id` = ? OR `id` = ?;
			
	}
