---------------------
BooleanBuilder
---------------------
	# boolean �����Ĺ�����
		public final class BooleanBuilder implements Predicate, Cloneable

	
	# ���췽��
		public BooleanBuilder()
		public BooleanBuilder(Predicate initial)
	
	# ʵ������
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
	# ���츴�ӵ���������
		QUser qUser = QUser.user;
		
		// ���յ�����
		BooleanBuilder builder = new BooleanBuilder();
		
		// ����1
		BooleanBuilder group1 = new BooleanBuilder(qUser.name.eq("name").or(qUser.id.gt(1)));
		
		// ����2
		BooleanBuilder group2 = new BooleanBuilder(qUser.createdDate.before(LocalDateTime.now()).and(qUser.enabled.eq(true)));
		
		// �ϲ���ϵΪ���յ�����
		builder.and(group1.and(group2));
		
		System.out.println(builder);
		// (user.name = name || user.id > 1) && user.createdDate < 2021-04-09T17:09:16.562 && user.enabled = true