-----------------------
jpa动态排序工具类
-----------------------
	public static final String DEFAULT_ORDER = "ASC";
	
	/**
	 * JPA排序参数封装
	 * @param properties  排序的实体属性（不是表字段）
	 * @param orders
	 * @return
	 */
	public static Sort jpaSort(String[] properties, String[] orders) {
		if (properties == null || properties.length == 0) {
			return null;
		}
		
		Order[] orderArray = new Order[properties.length];

		for (int x = 0; x < properties.length; x++) {

			// 排序字段
			String property = properties[x];
			
			// 判断列名称的合法性，防止SQL注入。只能是【字母，数字，下划线】
			/**
			 * JPA 严格按照列名称排序。不需要校验
			 */
	//		if (!property.matches("[A-Za-z0-9_]+")) {
	//			throw new ServiceException(Message.fail(Message.Code.BAD_REQUEST, "非法的排序属性名称：" + property));
	//		}
			
			// 排序策略
			Sort.Direction direction = null;
			
			if (orders != null && orders.length > x) {
				try {
					direction = Sort.Direction.valueOf(orders[x].toUpperCase().trim());	
				} catch (IllegalArgumentException e) {
					throw new ServiceException(Message.fail(Message.Code.BAD_REQUEST, "非法的排序策略：" + orders[x]));	
				}
			}else {
				direction = Sort.Direction.ASC;;
			}
			
			orderArray[x] = new Order(direction, property, NullHandling.NULLS_LAST);
		}
		
		return Sort.by(orderArray);
	}