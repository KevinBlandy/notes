-----------------------------
检索的方式					 |
----------------------------
	# JPA父接口定义的检索方法
	# 方法命名规则检索
	# JPQL检索
	# 本地SQL检索
	# Specification 动态查询

-----------------------------
JPA父接口定义的检索方法		 |
----------------------------

    List<UserEntity> findAllById(Iterable<Integer> integers);
    Optional<UserEntity> findById(Integer integer);
	UserEntity getOne(Integer integer);
	
	Optional<UserEntity> findOne(Specification<UserEntity> spec);
    <S extends UserEntity> Optional<S> findOne(Example<S> example);

	List<UserEntity> findAll();
    List<UserEntity> findAll(Sort sort);
    Page<UserEntity> findAll(Pageable pageable);

	<S extends UserEntity> List<S> findAll(Example<S> example);
    <S extends UserEntity> List<S> findAll(Example<S> example, Sort sort);
    <S extends UserEntity> Page<S> findAll(Example<S> example, Pageable pageable);

    List<UserEntity> findAll(Specification<UserEntity> spec);
    Page<UserEntity> findAll(Specification<UserEntity> spec, Pageable pageable);
    List<UserEntity> findAll(Specification<UserEntity> spec, Sort sort);
	
	long count();
	<S extends UserEntity> long count(Example<S> example);
    long count(Specification<UserEntity> spec); 

	<S extends UserEntity> boolean exists(Example<S> example);
	boolean existsById(Integer integer);
    
	
	* 无非就是, 分页, Specification动态条件, Example条件, Sort 各种组合查询
	* findOne 是立即加载, getOne 是延迟加载



----------------
关联检索的坑
----------------
	1. 所有的注解, 要么都配置在属性上, 要么都配置在 getter 方法上, 不要混用
	2. 所有的关联都可以单向, 双向关联, JSON在序列化的时候会产生死循环引用, 需要自己手动处理(添加json忽略字段)
	3. 在所有关联检索中, 表一般是不用创建外键索引的, @MappedBy 要注意
	4. 级联删除比较危险, 建议考虑清楚, 或者完全掌握
	5. 不同的关联关系的配置, @JoinClumn 里面的 namereferencedColumnName 代表的意思是不一样的, 很容易弄混, 可以根据打印出来的SQL做调整
	6. 当配置这些关联关系的时候建议大家直接在表上面, 把外键建好, 然后通过后面我们介绍的开发工具直接生成, 这样可以减少自己调试的时间
	

