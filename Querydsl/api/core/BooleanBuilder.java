---------------------
BooleanBuilder
---------------------
	# boolean 条件的构造器
		public final class BooleanBuilder implements Predicate, Cloneable

	
	# 构造方法
		public BooleanBuilder()
		public BooleanBuilder(Predicate initial)
	
	# 实例方法
		public <R,C> R accept(Visitor<R,C> v, C context)
		public BooleanBuilder and(@Nullable Predicate right)
		public BooleanBuilder andAnyOf(Predicate... args)
		public BooleanBuilder andNot(Predicate right)
		public BooleanBuilder clone()
		public BooleanBuilder not()
		public BooleanBuilder or(@Nullable Predicate right)
		public BooleanBuilder orAllOf(Predicate... args)
		public BooleanBuilder orNot(Predicate right)
		public Class<? extends Boolean> getType()
		public Predicate getValue()


---------------------
BooleanBuilder
---------------------
	# 构造复杂的条件分组
		QUser qUser = QUser.user;
		
		// 最终的条件
		BooleanBuilder builder = new BooleanBuilder();
		
		// 分组1
		BooleanBuilder group1 = new BooleanBuilder(qUser.name.eq("name").or(qUser.id.gt(1)));
		
		// 分组2
		BooleanBuilder group2 = new BooleanBuilder(qUser.createdDate.before(LocalDateTime.now()).and(qUser.enabled.eq(true)));
		
		// 合并关系为最终的条件
		builder.and(group1.and(group2));
		
		System.out.println(builder);
		// (user.name = name || user.id > 1) && user.createdDate < 2021-04-09T17:09:16.562 && user.enabled = true