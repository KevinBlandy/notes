---------------------------
Field
---------------------------
	# 字段接口
	# public interface Field<T> extends
				SelectField<T>,
				GroupField,
				OrderField<T>,
				FieldOrRow,
				FieldOrConstraint 
			
	
	
	# 方法
		<Z> Field<Z> cast(Class<Z> type);
			* 强制转换为指定的类型
		
		String getName();
			* 获取字段名称，可能是别名
		
				
		
