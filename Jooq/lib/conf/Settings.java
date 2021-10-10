-------------------
Settings
-------------------
	# 运行时配置，可选的
		public class Settings extends SettingsBase implements Serializable, Cloneable, XMLAppendable
	
	# 该org.jooq.conf.Settings类是JAXB注释类型，可以通过多种方式提供给配置：
		* 在 DSLContext 构造函数 ( DSL.using()) 中。这将覆盖下面的默认设置
		* 在org.jooq.impl.DefaultConfiguration构造函数中。这将覆盖下面的默认设置
		* 从 JVM 参数指定的位置：-Dorg.jooq.settings
		* 从 /jooq-settings.xml 的类路径
		* 从设置默认值中指定
			https://www.jooq.org/xsd/jooq-runtime-3.15.0.xsd

		Settings settings = JAXB.unmarshal(new File("/path/to/settings.xml"), Settings.class);

		Settings settings = new Settings();
		settings.setStatementType(StatementType.STATIC_STATEMENT);
		DSLContext create = DSL.using(connection, dialect, settings);
	
	# 方法
		public Settings withRenderKeywordCase(RenderKeywordCase value)
			* 设置渲染的SQL中关键字大小写
				AS_IS			// （默认）生成在代码库中定义的关键字（主要是小写）
				LOWER			// 小写
				UPPER			// 大写
				PASCAL			// 生成 pascal 大小写的关键字。
		
		public Settings withExecuteLogging(Boolean value) 
			* 是否记录执行日志
		
		public Settings withMapJPAAnnotations(Boolean value)
			* org.jooq.impl.DefaultRecordMapper支持基本JPA映射（大多@Table和@Column注释）。
			* 查找这些注释会产生一些额外的开销（主要通过反射缓存来处理）。可以使用以下mapJPAAnnotations设置关闭它：
			* 默认为 true

		public Settings withParamType(ParamType value) 
			* 设置占位符参数类型
				INDEXED
				FORCE_INDEXED
				NAMED
				NAMED_OR_INLINED
				INLINED
		
		public Settings withRenderNamedParamPrefix(String value)
			* 占位符参数如果是 named，这个方法可以设置前缀

		public Settings withRenderFormatted(Boolean value) 
			* 是否格式化输出SQL
		
		public Settings withRenderQuotedNames(RenderQuotedNames value)
			* 设置字段单引号输出策略
				ALWAYS
				EXPLICIT_DEFAULT_QUOTED
				EXPLICIT_DEFAULT_UNQUOTED
				NEVER

		public Settings withRenderNameCase(RenderNameCase value)
			* 字段名称大小写策略
				AS_IS,
				LOWER,
				LOWER_IF_UNQUOTED,
				UPPER,
				UPPER_IF_UNQUOTED;
		

			
				