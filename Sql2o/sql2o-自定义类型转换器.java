----------------------------
自定义类型转换器			|
----------------------------
	# 实现转换器接口 Converter<T>
		T convert(Object val) throws ConverterException;
			* 把 sql 类型转换为 实体类型

		Object toDatabaseParam(T val);
			* 把实体类型转换为 sql 类型
	
	# 实现 ConvertersProvider 接口 
		void fill(Map<Class<?>,Converter<?>> mapToFill);
			* 覆写该方法,通过参数,添加多个: Converter 的实现
	
	# 在 src/main/resources 目录下创建文件
		META-INF/services/org.sql2o.converters.ConvertersProvider
	
	# 在该文件中写入 ConvertersProvider 接口的实现全路径,使用回车结尾
	
		

----------------------------
demo						|
----------------------------
	# 实现 Converter
		import org.sql2o.converters.Converter;

		import java.sql.Timestamp;
		import java.time.Instant;
		import java.time.LocalDateTime;
		import java.time.ZoneOffset;

		public class LocalDateTimeConverter implements Converter<LocalDateTime> {
			@Override
			public LocalDateTime convert(final Object val)  {
				if(val != null ){
					if(val instanceof Timestamp){
						return LocalDateTime.ofInstant(Instant.ofEpochMilli(((Timestamp)val).getTime()), ZoneOffset.UTC);
					}
				}
				return null;
			}

			@Override
			public Object toDatabaseParam(final LocalDateTime val) {
				if(val != null){
					return new Timestamp(val.toInstant(ZoneOffset.UTC).toEpochMilli());
				}
				return null;
			}
		}

	# 实现 ConvertersProvider
		import org.sql2o.converters.Converter;
		import org.sql2o.converters.ConvertersProvider;

		import java.time.LocalDateTime;
		import java.util.Map;

		public class Sql2oConvertersProvider implements ConvertersProvider {
			@Override
			public void fill(Map<Class<?>, Converter<?>> mapToFill) {
				mapToFill.put(LocalDateTime.class, new LocalDateTimeConverter());
			}
		}
	
	# 在 META-INF/services/org.sql2o.converters.ConvertersProvider 文件中添加实现类全路径(注意最后有空格)
		io.javaweb.example.sql2o.converter.Sql2oConvertersProvider


----------------------------
在程序中设置类型转换器		|
----------------------------

	// 创建类型映射Map
	Map<Class, Converter> converters = new HashMap<>();

	// 添加类型和转换器的映射
	converters.put(LocalDateTime.class,new LocalDateTimeConverter());

	// 实例化 NoQuirks
	Quirks quirks = new NoQuirks(converters);

	// 实例化sql2o
	Sql2o sql2o = new Sql2o(dataSource,quirks);