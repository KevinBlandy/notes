---------------------
BooleanBuilder
---------------------
	# boolean 条件的构造器
		Expression
			|-BooleanBuilder

	
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
