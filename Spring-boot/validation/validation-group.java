----------------------------------
Group
----------------------------------
	# 分组的概念
		* 可以用groups来实现:同一个模型在不同场景下，(动态区分)校验模型中的不同字段。
	
	# 所有的校验注解都有一个属性
		Class<?>[] groups() default { };

		* 用于指定一个或者多个Group
	
	# 默认的 Group 接口
		package javax.validation.groups
		public interface Default {
		}
		
		* 这是一个标记接口，默认的情况下，校验注解没有声明 group 属性，那么该注解就是属于这个 default group 的
		* 一旦指定了一个或者多个Group，那么只有在执行 validate 方法指定指定了 Group 才会执行校验，跟继承没关系
	
	# 自定义Group
		* 随便定义类, 无所谓实现不实现 Group 接口

	# 在声明注解的时候，指定Group
		@NotNull(message = "ID不能为空", groups = { UpdateGroup.class })
		public Integer id;
	
	# 校验的时候，指定Group
		validation.validate(user, UpdateGroup.class);

	# 对于 @Valid 注解来说， group 具有传递性
		* 校验otter对象，指定了group的时候，outter对象中标识了 @Valid 注解的属性，也会仅仅只校验指定group的属性

	

----------------------------------
GroupSequence
----------------------------------
	# GroupSequence 注解的作用是，一次性把N个Group聚集在一起，形成一个Group
		* 并会严格按照声明顺序执行校验

		@Target({ TYPE })
		@Retention(RUNTIME)
		@Documented
		public @interface GroupSequence {

			Class<?>[] value();
		}
	
	# 定义多个Group
		public interface UserNameCheck {
		}
		public interface PasswordCheck {
		}
	
	# 定义 GroupSequence
		@GroupSequence({PasswordCheck.class, UserNameCheck.class})
		public interface UserCheckSequence {
		}

		* 在value属性，声明N个 Group
	
	# 校验的时候，指定 Group 组

		// 原生
		validation.validate(user, UserCheckSequence.class);

		// Spring
		@RequestBody @Validated({UserCheckSequence.class}) User user