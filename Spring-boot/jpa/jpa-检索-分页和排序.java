-----------------------------
排序						 |
-----------------------------
	# 类库
		Sort
			|-JpaSort
		Sort.Order
		Sort.Direction

		* 一个Sort对象包含N多个 Order 对象
	
	# Sort.Order
		Order(@Nullable Direction direction, String property)
		Order(@Nullable Direction direction, String property, NullHandling nullHandlingHint)
		
		direction
			* 排序策略,枚举
				ASC
				DESC
		property
			* 排序字段名称
		nullHandlingHint	
			* 对于空值的排序处理,枚举
				NATIVE				让DB决定
				NULLS_FIRST			排在最前面
				NULLS_LAST			排在最后面
	
	# demo
		// 根据name升序排序
		Sort nameAes = Sort.by(new Sort.Order(Sort.Direction.ASC,"name"));

		// 根据age降序,createDate升序 排序
        Sort ageDesccreateDateAes = Sort.by(Sort.Order.desc("age"),Sort.Order.asc("createDate"));

		// 根据gender排序,如果gender字段是空的,就排在记录的最前面
        Sort genderAes = Sort.by(new Sort.Order(Sort.Direction.ASC,"gender",Sort.NullHandling.NULLS_FIRST));

		// 不排序
		Sort unsorted = Sort.unsorted();

-----------------------------
分页						 |
-----------------------------
	# 类库(接口)
		Pageable
			|-PageRequest
		Page
	
	# Pageable
		static Pageable unpaged()
			* 不分页

	
	# PageRequest
		* 通过静态方法来创建
			static PageRequest of(int page, int size) 
			static PageRequest of(int page, int size, Sort sort)
			static PageRequest of(int page, int size, Direction direction, String... properties)
				page 页码, 第一页从0开始
				size 每页显示数量
					

		* demo
			// 创建一个分页,不排序
			PageRequest.of(1,10);

			// 创建一个分页,根据name升序排序
			PageRequest.of(1,10,Sort.by(Sort.Order.asc("name")));

			// 创建一个分页,根据name,age,gender升序排序
			PageRequest.of(1,10, Sort.Direction.ASC,"name","age","gender");

		
	
	# 分页结果 Page(接口)
		* 静态方法
			static <T> Page<T> empty()
			static Page<T> empty(Pageable pageable)
				* 创建一个空的结果集
		
		* 实例方法

			int getNumber();
			int getNumberOfElements();
			int getSize();			 // 每页显示的记录数
			int getTotalPages();	 // 总页数
			long getTotalElements(); // 总记录数
			<U> Page<U> map(Function<? super T, ? extends U> converter);	// 数据转换接口
			List<T> getContent();	// 获取到数据
			boolean hasContent();	// 是否有数据
			Sort getSort();			// 获取排序策略
			boolean isFirst();		// 是否是第一个
			boolean isLast();		// 是否是最后一个
			boolean hasNext();		// 是否还有下一个
			boolean hasPrevious();	// 是否还有上一个
			
			Pageable getPageable()
				* 获取该结果集的分页信息

			Pageable previousPageable();
			Pageable nextPageable();
				* 获取上一个或者下一个分页, 可能会溢出

			Pageable previousOrFirstPageable()
			Pageable nextOrLastPageable()
				* 获取下一个或者上一个分页, 可以防止溢出
				* 如果不存在下一个或者上一个, 那么返回最后一个或者最前一个
		
		* json结构
			{
				"content": [],					//分页的数据
				"pageable": {
					"sort": {
						"sorted": true,
						"unsorted": false,
						"empty": false
					},
					"offset": 10,
					"pageSize": 10,
					"pageNumber": 1,
					"paged": true,
					"unpaged": false
				},
				"totalPages": 1,
				"totalElements": 6,
				"last": true,
				"number": 1,
				"size": 10,
				"sort": {
					"sorted": true,
					"unsorted": false,
					"empty": false
				},
				"numberOfElements": 0,
				"first": false,
				"empty": true
			}

