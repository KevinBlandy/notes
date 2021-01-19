--------------------------------------------------
适用于QueryDsl的动态排序工具类
--------------------------------------------------
	private void setSort(String[] sorts, String[] orders, BiConsumer<String, Order> biConsumer) {
		if (sorts == null || sorts.length == 0) {
			return ;
		}
		for (int i = 0; i < sorts.length; i ++) {
			String sort = sorts[i];
			Order order = null;
			if (orders != null && i < orders.length) {
				order = Order.valueOf(orders[i].toUpperCase());
			} else {
				order = Order.ASC; // 使用默认的排序
			}
			biConsumer.accept(sort, order);
		}
	}
	
	@GetMapping("/list")
	public Object list(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "10") int limit,
			
			@RequestParam(value = "sort", defaultValue = "createdDate") String[] sorts,
			@RequestParam(value = "order", defaultValue = "desc") String[] orders) {
		
		QueryResults<Merchant> results = merchantService.executeReadOnly(query -> {
			QMerchant qMerchant = QMerchant.merchant;
			JPAQuery<Merchant> jpaQuery = query.select(Projections.bean(Merchant.class,qMerchant.id, qMerchant.account, qMerchant.lastModifiedDate ,qMerchant.createdDate,qMerchant.enabled))
						.from(qMerchant)
						.offset((page - 1) * limit).limit(limit);
			
			setSort(sorts, orders, (key, order) -> {
				if (key.equals(qMerchant.account.getMetadata().getName())) {
					jpaQuery.orderBy(new OrderSpecifier<>(order, qMerchant.account));
				} else if (key.equals(qMerchant.createdDate.getMetadata().getName())) {
					jpaQuery.orderBy(new OrderSpecifier<>(order, qMerchant.createdDate));
				} else if (key.equals(qMerchant.lastModifiedDate.getMetadata().getName())) {
					jpaQuery.orderBy(new OrderSpecifier<>(order, qMerchant.lastModifiedDate));
				} else {
					// TODO 不存在的排序字段
				}
			});
			
			return jpaQuery.fetchResults();
						
		});
		return Message.success(new PageResult(results));
	}

	// http://localhost:8080/merchant/list?sort=account,createdDate,lastModifiedDate&order=desc


--------------------------------------------------
也是自己写的一个工具类，反射操作太多，影响性能
--------------------------------------------------
	/**
	 * 设置QueryDsl的排序
	 * @param sorts
	 * @param orders
	 * @param biConsumer
	 */
	public static  void setSort(String[] sorts, String[] orders, BiConsumer<String, com.querydsl.core.types.Order> biConsumer) {
		if (sorts == null || sorts.length == 0) {
			return ;
		}
		for (int i = 0; i < sorts.length; i ++) {
			String sort = sorts[i];
			com.querydsl.core.types.Order order = null;
			if (orders != null && i < orders.length) {
				order = com.querydsl.core.types.Order.valueOf(orders[i].toUpperCase());
			} else {
				order = com.querydsl.core.types.Order.ASC; // 使用默认的排序
			}
			biConsumer.accept(sort, order);
		}
	}
	
	/**
	 * queryDsl 排序参数封装
	 * @param entityPathBase			排序字段的对象
	 * @param properties				排序的属性名称
	 * @param orders					排序策略
	 * @return
	 */
	@Deprecated
	public static OrderSpecifier<?>[] queryDslSort(EntityPathBase<?> entityPathBase,String[] properties, String[] orders) {
		List<Expression<?>> expressions = Stream.of(properties).map(i -> {
			Field field;
			try {
				field = entityPathBase.getClass().getField(i);
				if (Modifier.isFinal(field.getModifiers()) && Modifier.isPublic(field.getModifiers())) {
					return (Expression<?>) field.get(entityPathBase);
				}
			} catch (NoSuchFieldException e1) {
				e1.printStackTrace();
			} catch (SecurityException e1) {
				e1.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			return null;
		}).filter(i -> i != null).collect(Collectors.toList());
		
		return queryDslSort(expressions.toArray(new Expression<?>[expressions.size()]), orders);
	}
	
	/**
	 * queryDsl 排序参数封装 
	 * @param expressions
	 * @param order
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static OrderSpecifier<?>[] queryDslSort(Expression<?>[] expressions, String[] orders) {
		if (expressions == null || expressions.length == 0) {
			return new OrderSpecifier[0];
		}
		OrderSpecifier<?>[] orderSpecifier = new OrderSpecifier[expressions.length];
		
		for (int x = 0; x < expressions.length; x++) {

			// 排序策略
			com.querydsl.core.types.Order order = null;
			
			if (orders != null && orders.length > x) {
				try {
					order = com.querydsl.core.types.Order.valueOf(orders[x].toUpperCase().trim());	
				} catch (IllegalArgumentException e) {
					// throw new ServiceException(Message.fail(Message.Code.BAD_REQUEST, "非法的排序策略：" + orders[x]));
					order = com.querydsl.core.types.Order.ASC;
				}
			}else {
				order = com.querydsl.core.types.Order.ASC;
			}
			
			orderSpecifier[x] = new OrderSpecifier(order, expressions[x], OrderSpecifier.NullHandling.NullsLast);
		}
		
		return orderSpecifier;
	}