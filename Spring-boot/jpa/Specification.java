---------------------
Specification
---------------------
	# 动态条件查询
	# Specification
		static <T> Specification<T> not(@Nullable Specification<T> spec) 
		static <T> Specification<T> where(@Nullable Specification<T> spec)


		default Specification<T> and(@Nullable Specification<T> other)
		default Specification<T> or(@Nullable Specification<T> other)

		Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder)
			* 唯一的抽象方法
			
			root
				* 实体对象, 可以通过它获取到属性值
			
			query
				* 用于生成检索语句

			criteriaBuilder
				* 用于拼接条件
