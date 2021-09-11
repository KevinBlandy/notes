
---------------
dao
---------------
	# 实现
		public class AdminDao extends DAOImpl<AdminRecord, io.springcloud.jooq.generated.tables.pojos.Admin, UInteger> 
		public class AdminRoleDao extends DAOImpl<AdminRoleRecord, io.springcloud.jooq.generated.tables.pojos.AdminRole, Record2<UInteger, UInteger>>


---------------
自定义类型映射
---------------
	# 枚举映射
		* 定义枚举转换类
			import org.jooq.impl.EnumConverter;
			import io.springcloud.domain.enums.Gender;
			public class GenderEnumConverter extends EnumConverter<String, Gender>{
				private static final long serialVersionUID = -3880880972715165871L;
				public GenderEnumConverter() {
					// source 类型，转换的枚举类型
					super(String.class, Gender.class); // 如果 source 是字符串，则是用实例名称，如果source是Number子类，则用orind() 值
				}
			}
	
		* 配置
			<forcedType>
				<!-- 指定枚举类 -->
				<userType>io.springcloud.domain.enums.Gender</userType>
				<!-- 指定转换器 -->
				<converter>io.springcloud.jooq.converter.GenderEnumConverter</converter>
				<!-- 要转换列的表达式，可以是正则 -->
				<includeExpression>gender</includeExpression>
				<!-- 要转换的列类型，可以是正则 -->
				<includeTypes>.*</includeTypes>
			</forcedType>
	
	# Boolean 类型映射
		<forcedType>
			<!-- 生成boolean类型 -->
			<name>BOOLEAN</name>
			<!-- 列字段名称，可以是正则表达式 --> 
			<includeExpression>enabled</includeExpression>
			<!-- 列类型，可以是正则表达式 -->
			<includeTypes>.*</includeTypes>
		</forcedType>
	
