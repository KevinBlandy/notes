-----------------------
MyBatis-分页插件		|
-----------------------
	# maven
		<dependency>
			<groupId>com.github.pagehelper</groupId>
			<artifactId>pagehelper</artifactId>
		</dependency>

-------------------------
MyBatis-PageRowBounds分页|
-------------------------
	# mybatis配置
		<plugin interceptor="com.github.pagehelper.PageInterceptor">
			<!-- 方言 -->
			<property name="helperDialect" value="mysql"/>
			<!-- RowBounds对象的offset属性做为分页的page参数 -->
			<property name="offsetAsPageNum" value="true"/>
			<!-- 分页合理化参数，防止因为页码头尾溢出而检索到空记录 -->
			<property name="reasonable" value="true"/>
			<!-- 当 pageSize(limit) = 0 忽略页码，直接检索出所有的记录 -->
			<property name="pageSizeZero" value="true"/>
			<!-- RowBounds 作为分页参数时需要检总记录数 -->
			<property name="rowBoundsWithCount" value="true"/>
		</plugin>
	
	# 代码

		// SQL参数过滤对象
		UserEntity userEntity = new UserEntity();
		
		// 页码和每页显示数量
		PageRowBounds pageRowBounds = new PageRowBounds(2, 1);

		// 是否要检索总记录数
		pageRowBounds.setCount(true);
		
		List<UserEntity> users = this.userMapper.pageTest(userEntity ,pageRowBounds);
		
	
		* pageRowBounds 的属性
			{
				"count":true,			// 是否检索总记录数量
				"limit":1,				// 每页显示数量
				"offset":2,				// 当前页码
				"total":3				// 总记录数量
			}

-------------------------
MyBatis-常用配置	 |
-------------------------
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
  PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-config.dtd">

<configuration>
	<settings>
		<!-- 自动转换实体的属性和列名的规则（驼峰-下划线） -->
		<setting name="mapUnderscoreToCamelCase" value="true" />
		<!-- 默认枚举处理 -->
		<setting name="defaultEnumTypeHandler" value="org.apache.ibatis.type.EnumOrdinalTypeHandler"/>
		<!-- 自动映射，发现未知列（或者未知属性类型）抛出异常 
		<setting name="autoMappingUnknownColumnBehavior" value="FAILING"/>
		-->
		<!-- 自动映射，任何复杂的结果集 -->
		<!-- <setting name="autoMappingBehavior" value="FULL"/>  -->
	</settings>
	<typeAliases>
		<package name="com.quliao.core.domain"/>
	</typeAliases>
	<typeHandlers>
		<typeHandler handler="org.apache.ibatis.type.EnumTypeHandler" javaType="com.quliao.core.domain.enums.SysCode" jdbcType="VARCHAR"/>
	</typeHandlers>
	<plugins>
		<plugin interceptor="com.github.pagehelper.PageInterceptor">
			<!-- 方言 -->
			<property name="helperDialect" value="mysql"/>
			<!-- RowBounds对象的offset属性做为分页的page参数 -->
			<property name="offsetAsPageNum" value="true"/>
			<!-- 分页合理化参数，防止因为页码头尾溢出而检索到空记录 -->
			<property name="reasonable" value="true"/>
			<!-- 当 pageSize(limit) = 0 忽略页码，直接检索出所有的记录 -->
			<property name="pageSizeZero" value="true"/>
			<!-- RowBounds 作为分页参数时需要检总记录数 -->
			<property name="rowBoundsWithCount" value="true"/>
		</plugin>
	</plugins>
</configuration>

-------------------------
MyBatis-PageHelper分页	 |
-------------------------
	# 静态方法
		// 仅仅排序不分页
		Page<E> PageHelper.orderBy(String string);

		// 开始分页，不检索总记录数
		Page<E> PageHelper.startPage(1, 100, false);
	
	# 实例方法
		* 支持链式调用

		Page<E> setOrderBy(String orderBy)
			* 设置排序字段

		void setOrderByOnly(boolean orderByOnly)
			* 设置是否只排序，不分页
				

-------------------------
MyBatis-动态的解析排序字段|
-------------------------
	# 动态的解析排序字段
			/**
		 * 解析排序字段
		 * 
		 * @param sorts
		 * @param orders
		 * @return
		 */
		public static String order(String[] columns, String[] orders) {

			if (columns == null || columns.length == 0 || orders == null || orders.length == 0) {
				return "";
			}

			if (columns.length != orders.length) {
				// 必须保证每个字段都有设置排序策略
				throw new ServiceException(HttpStatus.BAD_REQUEST, "排序字段和排序策略必须一一对应");
			}

			int length = columns.length;

			StringBuilder stringBuilder = new StringBuilder();

			for (int x = 0; x < length; x++) {

				String column = columns[x];
				String order = orders[x];

				// 判断列名称的合法性，防止SQL注入。只能是【字母，数字，下划线】
				if (!column.matches("[A-Za-z0-9_]+")) {
					throw new ServiceException(HttpStatus.BAD_REQUEST, "非法的排序字段名称：" + column);
				}

				// 驼峰转换为下划线
				column = humpConversionUnderscore(column);

				order = order.toUpperCase();

				// 判断排序策略的合法性
				if (!(order.equals("ASC") || order.equals("DESC"))) {
					throw new ServiceException(HttpStatus.BAD_REQUEST, "非法的排序策略：" + column);
				}

				if (x != 0) {
					stringBuilder.append(", ");
				}
				stringBuilder.append("`" + column + "` " + order);
			}
			return stringBuilder.toString();
		}

		/**
		 * 驼峰转换为下划线
		 * 
		 * @param value
		 * @return
		 */
		public static String humpConversionUnderscore(String value) {
			StringBuilder stringBuilder = new StringBuilder();
			char[] chars = value.toCharArray();
			for (char charactor : chars) {
				if (Character.isUpperCase(charactor)) {
					stringBuilder.append("_");
					charactor = Character.toLowerCase(charactor);
				}
				stringBuilder.append(charactor);
			}
			return stringBuilder.toString();
		}