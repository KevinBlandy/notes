--------------------------------
类型转换器						|
--------------------------------
	# 一般而言,系统预定义的类型转换器已经够用
	# 实现接口
		public interface TypeHandler<T> 
		void setParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException;
			 * 用于定义在Mybatis设置参数时该如何把Java类型的参数转换为对应的数据库类型 
			 * @param ps 当前的PreparedStatement对象 
			 * @param i 当前参数的位置 
			 * @param parameter 当前参数的Java对象 
			 * @param jdbcType 当前参数的数据库类型
				* 该参数来于 #{}中的 jdbcType 值,
				* #{status,jdbcType=TINYINT})
				* 如果未指定,则该为为 null
			 * @throws SQLException 
		T getResult(ResultSet rs, String columnName) throws SQLException;
			 * 用于在Mybatis获取数据结果集时如何把数据库类型转换为对应的Java类型 
			 * @param rs 当前的结果集 
			 * @param columnName 当前的字段名称 
			 * @return 转换后的Java对象 
			 * @throws SQLException 
		T getResult(ResultSet rs, int columnIndex) throws SQLException;
			 * 用于在Mybatis通过字段位置获取字段数据时把数据库类型转换为对应的Java类型 
			 * @param rs 当前的结果集 
			 * @param columnIndex 当前字段的位置 
			 * @return 转换后的Java对象 
			 * @throws SQLException 
		T getResult(CallableStatement cs, int columnIndex) throws SQLException;
			 * 用于Mybatis在调用存储过程后把数据库类型的数据转换为对应的Java类型 
			 * @param cs 当前的CallableStatement执行后的CallableStatement 
			 * @param columnIndex 当前输出参数的位置 
			 * @return 
			 * @throws SQLException 
	# 但是一般都是继承抽象类
		BaseTypeHandler
	# 自定义类型转换器
		1,继承抽象类,覆写方法
			org.apache.ibatis.type.BaseTypeHandler<T>
			public abstract void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException;
				* 插入映射
				* parameter :就是JAVABEAN 的对象,在这里可以做一些操作后设置 ps 从而实现自定义的消息转换
				* jdbcType :该参数来于 #{}中的 jdbcType 值,如果未声明,则该值为null
				* ps.setObject(i,parameter.getValue(), jdbcType.TYPE_CODE);

			public abstract T getNullableResult(ResultSet rs, String columnName) throws SQLException;
				* 检索映射,根据列名可以获取到值,
				* 自己对值进行封装后返回,该值至于是
				*  Integer i = rs.getInt(columnIndex);
				
			public abstract T getNullableResult(ResultSet rs, int columnIndex) throws SQLException;
				* 检索映射,根据列所在的位置可以获取到值,
				* 自己对值进行封装后返回
				*  Integer i = rs.getInt(columnIndex);
				
			public abstract T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException;
				* 检索映射,针对于存储过程.根据列所在的位置可以获取到值,
				* 自己对值进行封装后返回
				*  Integer i = cs.getInt(columnIndex);
				
		

		2,配置
			
			* mybatis.config.xml
				* 全局配置
				* JAVABean <-> 数据库
				<typeHandlers>
					<typeHandler handler="com.jd.jos.application.note.dao.CustomTimeStampHandler" javaType="java.util.Date" jdbcType="VARCHAR"/>
					* handler 指定Handler的配置
					* javaType JAVA的对象类型
					* jdbcType	只能是 JdbcType 枚举值其中之一
				</typeHandlers>

			* 检索结果配置
				* 数据库 -> JAVABean
				* typeHandler 指定转换器
				<resultMap type="Note" id="note-base">
					<result property="id" column="id" />
					<result property="updateTime" column="update_time" jdbcType="VARCHAR" javaType="Date" typeHandler="demo.CustomTimeStampHandler"/>
				</resultMap>
			
			* 在CRUD标签中使用转换器
				* JAVABean -> 数据库
				* 通过 typeHandler 属性指定消息转换器
				<update id="updateRow" parameterType="NoteBook">
					update note
						set update_time=#{updateTime,typeHandler=demo.CustomTimeStampHandler}
					where id=#{id}
				</update>
	

