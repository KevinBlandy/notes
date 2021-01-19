------------------------------
自定义配置属性的转换
------------------------------
	# 在使用 @ConfigurationProperties 进行属性注入的时候，可以通过自定义Converter进行数据的转换

	# 配置
		demo:
		  startTime: "09:30:00"
		  timeout: 2m

  # LocalTimeConverter
	import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
	import org.springframework.core.convert.converter.Converter;
	import org.springframework.stereotype.Component;
	import java.time.LocalTime;
	import java.util.Objects;
	@Component
	@ConfigurationPropertiesBinding
	public class LocalTimeConverter implements Converter<String, LocalTime> {
		@Override
		public LocalTime convert(String source) {
			Objects.requireNonNull(source);
			return LocalTime.parse(source);
		}
	}
	
	*  自定义 Converter 实现类，标识 @ConfigurationPropertiesBinding 注解

  # 配置类
	@Component
	@ConfigurationProperties(prefix = "demo")
	public class ConfigProperties {
		private LocalTime startTime;
		private Duration timeout;
	}
