---------------------
Specification
---------------------
	# ��̬������ѯ
	# Specification
		static <T> Specification<T> not(@Nullable Specification<T> spec) 
		static <T> Specification<T> where(@Nullable Specification<T> spec)


		default Specification<T> and(@Nullable Specification<T> other)
		default Specification<T> or(@Nullable Specification<T> other)

		Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder)
			* Ψһ�ĳ��󷽷�
			
			root
				* ʵ�����, �������Ϊͨ��������ȡ�����ݿ���У�Ҳ���Ƕ�������ԣ�
			
			query
				* �����ѯ���󣬰�����ѯ�ĸ�������.����:select, form ,where, group
				* ֻ��ʵ�����ͻ�Ƕ��ʽ���͵�Criteria��ѯ�����ã�����⣬���ṩ�˲�ѯROOT�ķ���

			criteriaBuilder
				* ����ƴ������
	
	# Demo
		Page<AccStatusUpdlog> ret = this.accStatusUpdlogService.findAll((root, query, builder) -> {
			List<Predicate> condition = new ArrayList<>();
			// �ͻ��˵Ĳ�ѯ��������
			AccStatusUpdlog model = params.getModel();
			if (model != null) {
				if (StringUtils.hasText(model.getUnitName())) {
					// �������
					condition.add(builder.like(root.get("unitName"), "%" + model.getUnitName() + "%"));
				}
			}
			// ת��Ϊ����
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
		