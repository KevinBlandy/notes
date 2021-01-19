# 注解所在包: org.hibernate.annotations

@Any
@AnyMetaDef
@AnyMetaDefs
@AttributeAccessor
@BatchSize
@UpdateTimestamp
@Table
	appliesTo
		* 表名称
	comment
		* 表注释

@DynamicUpdate
	* 标识在实体类上
	* 比较更新要使用的实体类中的字段值与从数据库中查询出来的字段值, 判断其是否有修改
	* 通俗理解就是, 把数据库中的记录查出来, 跟这个对象的值进行对比, 仅仅更新有修改字段

@DynamicInsert
	* 标识在实体类上
	* 在执行插入的时候, 仅仅根据对实体的非null字段生成SQL语句

