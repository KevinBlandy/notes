---------------------
继承
---------------------
	@MappedSuperclass
		* 标识在实体类的父类, 用于JPA注解的继承
		* 一般用于抽象类, 抽取出所有实体类公用的字段, 子实体类继承它
		* 使用该注解标识的类, 不能再用  @Entity 注解标识

---------------------
继承策略
---------------------
	@Inheritance
		* 指定实体的继承策略
			InheritanceType strategy() default SINGLE_TABLE;
				SINGLE_TABLE			所有继承的实体都保存在同一张数据库表中
				TABLE_PER_CLASS			有继承关系的所有实体类都将保存在单独的表中
				JOINED					每个实体子类都将保存在一个单独的表中
		
	
	* 一般用于指定 entity 与 entity 之间的继承关系

---------------------
@Embedded 引用继承
---------------------
	@Embedded
		* 标识在实体的属性上
		* 一般这个属性是一个公共抽取出来的对象, 表示要把这个对象的属性拆分开来合并到当前的对象
			class Common{
				Integer id;
				String name;
			}
			class User {
				@Embedded
				Common common;
			}

			user table:
				id int
				name varchar

---------------------
继承覆写
---------------------
	@AttributeOverride
		String name();
			* 父类的属性名称

		Column column();
			* 重新定义
		
		* 用于重定义字段名或长度等属性
		* 可以通过 @AttributeOverrides 一次性定义多个 @AttributeOverride 注解
	
	@AssociationOverride
		String name();
		JoinColumn[] joinColumns() default {};
		ForeignKey foreignKey() default @ForeignKey(PROVIDER_DEFAULT);
		JoinTable joinTable() default @JoinTable;


		* 可以通过 @AssociationOverrides 一次性定义多个 @AssociationOverride 注解
	
