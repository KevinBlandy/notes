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
				* 实体对象, 可以理解为通过它来获取到数据库的列（也就是对象的属性）
			
			query
				* 顶层查询对象，包含查询的各个部分.例如:select, form ,where, group
				* 只对实体类型或嵌入式类型的Criteria查询起作用，简单理解，它提供了查询ROOT的方法

			criteriaBuilder
				* 用于拼接条件
	
	# Demo
		Page<AccStatusUpdlog> ret = this.accStatusUpdlogService.findAll((root, query, builder) -> {
			List<Predicate> condition = new ArrayList<>();
			// 客户端的查询参数对象
			AccStatusUpdlog model = params.getModel();
			if (model != null) {
				if (StringUtils.hasText(model.getUnitName())) {
					// 添加条件
					condition.add(builder.like(root.get("unitName"), "%" + model.getUnitName() + "%"));
				}
			}
			// 转换为条件
			return query.where(condition.toArray(new Predicate[condition.size()])).getRestriction();
		},  PageRequest.of((int) params.offset(), (int)params.getRows(), Sort.by(Sort.Order.desc("createTime"))));

---------------------
Predicate
---------------------
---------------------
root
---------------------
---------------------
criteriaBuilder
---------------------
---------------------
CriteriaQuery
---------------------
		