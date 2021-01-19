---------------------------------
根据方法名检索					 |
---------------------------------
	# 使用find开头
	# 如果查询结果可能有多个需要使用 List<T> 作为方法返回值
		* 不然当结果出现多个的时候会抛出异常: IncorrectResultSizeDataAccessException, NonUniqueResultException
	
	


---------------------------------
根据方法名检索规则				 |
---------------------------------
	# 规则
		关键字				方法名													JPQL
		And					findByLastnameAndFirstname								… where x.lastname = ?1 and x.firstname = ?2
		Or					findByLastnameOrFirstname								… where x.lastname = ?1 or x.firstname = ?2
		Is, Equals			findByFirstname,findByFirstnameIs,findByFirstnameEquals	… where x.firstname = ?1
		Between				findByStartDateBetween									… where x.startDate between ?1 and ?2
		LessThan			findByAgeLessThan										… where x.age < ?1
		LessThanEqual		findByAgeLessThanEqual									… where x.age <= ?1
		GreaterThan			findByAgeGreaterThan									… where x.age > ?1
		GreaterThanEqual	findByAgeGreaterThanEqual								… where x.age >= ?1
		After				findByStartDateAfter									… where x.startDate > ?1
		Before				findByStartDateBefore									… where x.startDate < ?1
		IsNull, Null		findByAge(Is)Null										… where x.age is null
		IsNotNull, NotNull	findByAge(Is)NotNull									… where x.age not null
		Like				findByFirstnameLike										… where x.firstname like ?1
		NotLike				findByFirstnameNotLike									… where x.firstname not like ?1
		StartingWith		findByFirstnameStartingWith								… where x.firstname like ?1 (parameter bound with appended %)
		EndingWith			findByFirstnameEndingWith								… where x.firstname like ?1 (parameter bound with prepended %)
		Containing			findByFirstnameContaining								… where x.firstname like ?1 (parameter bound wrapped in %)
		OrderBy				findByAgeOrderByLastnameDesc							… where x.age = ?1 order by x.lastname desc
		Not					findByLastnameNot										… where x.lastname <> ?1
		In					findByAgeIn(Collection<Age> ages)						… where x.age in ?1
		NotIn				findByAgeNotIn(Collection<Age> ages)					… where x.age not in ?1
		True				findByActiveTrue()										… where x.active = true
		False				findByActiveFalse()										… where x.active = false
		IgnoreCase			findByFirstnameIgnoreCase								… where UPPER(x.firstame) = UPPER(?1)
	
	# 还支持其他的一些语法(PartTree)
		find			
		read
		get
		query
		stream

		count
		exists
			
		delete
		remove

		* 使用的时候, 要配合不同的返回结果进行配合
	
	# 支持属性导航
		class User {
			Address address;
		}
		class Address {
			String name;
		}
		UserRepositroy extends JpaRepository<User, Integer>{
			findByAddressName(String name); // 从address属性导航到它的name属性
			findByAddress_Name(String name); // 更为科学的写法, 通过下户线标识遍历的节点
		}

		* jpa里面, 下户线是保留标识符
		* 但是下划线又破话了java的驼峰规则
	
	# 通过方法名称限制结果集
		findFirst10ByName(String name);
		findTop10ByName(String name);

		* 通过 First/Top 来限制
	
	# 参考类:
		PartTreeJpaQuery
		PropertyExpression

---------------------------------
分页和排序						 |
---------------------------------
	# 只需要在最后一个参数定义: Sort / Pageable 
	# 返回值需要定义为对应的返回值
		

---------------------------------
Demo							 |
---------------------------------
	findByName(String name);

	findByNameLike(String name);
		* LIKE检索, 关键字需要自己添加通配符
			findByNameLike("%keywords%");

	findByNameAndId(String name, Integer id);

	findByIdBetween(Integer startId, Integer endId);
	findByIdLessThan(Integer id);
		* 检索 id 字段小于 id 的记录

	findByIdIn(List<Integer> ids);

	findByCreateDateAfter(LocalDateTime createDate);
		* 检索 create_date 字段大于 createDate  的记录
	