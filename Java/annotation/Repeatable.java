--------------------
Repeatable 注解
--------------------
	# @Repeatable 注解表明标记的注解可以多次应用于相同的声明或类型
		* JDK 1.8 的东西
	
	# 创建和使用“可重复注解”
	
		1, 创建“重复注解”
			@Repeatable(Schedules.class)
			public @interface Schedule {
				String dayOfMonth() default "first";
				String dayOfWeek() default "Mon";
				int hour() default 12;
			}

			* 通过 @Repeatable 注解, 指定该注解的“容器类”
		
		2, 创建容器注解
			@Retention(RetentionPolicy.RUNTIME)
			public @interface Schedules {
				Schedule[] value();
			}
			
			* 它的属性, 就是包含一个“重复注解“的集合
		
		3, 在需要的地方, 重复标识注解
			@Schedule(dayOfMonth="last")
			@Schedule(dayOfWeek="Wed", hour=24)
			public class RepetableAnnotation{ 
				....
			}

			* 可以标识多次
		
		4. 反射读取注解
			if (RepetableAnnotation.class.isAnnotationPresent(Schedules.class)){
				schedules = RepetableAnnotation.class.getAnnotation(Schedules.class);
				for (Schedule schedule: schedules.value()){
					System.out.println(schedule);
				}
			}

			* 反射的注解是“容器注解”，通过容器注解的属性，获取到原始注解
		

			