------------------------------
DurationStyle
------------------------------
	# 用于Duration的格式化，一般配合 @DurationFormat 使用
		package org.springframework.boot.convert;

		import java.time.Duration;
		import java.time.temporal.ChronoUnit;
		import java.util.function.Function;
		import java.util.regex.Matcher;
		import java.util.regex.Pattern;

		import org.springframework.util.Assert;
		import org.springframework.util.StringUtils;

		public enum DurationStyle {
			SIMPLE("^([+-]?\\d+)([a-zA-Z]{0,2})$") {
				@Override
				public Duration parse(String value, ChronoUnit unit) {
					try {
						Matcher matcher = matcher(value);
						Assert.state(matcher.matches(), "Does not match simple duration pattern");
						String suffix = matcher.group(2);
						return (StringUtils.hasLength(suffix) ? Unit.fromSuffix(suffix) : Unit.fromChronoUnit(unit))
								.parse(matcher.group(1));
					}
					catch (Exception ex) {
						throw new IllegalArgumentException("'" + value + "' is not a valid simple duration", ex);
					}
				}

				@Override
				public String print(Duration value, ChronoUnit unit) {
					return Unit.fromChronoUnit(unit).print(value);
				}

			},

			ISO8601("^[+-]?P.*$") {

				@Override
				public Duration parse(String value, ChronoUnit unit) {
					try {
						return Duration.parse(value);
					}
					catch (Exception ex) {
						throw new IllegalArgumentException("'" + value + "' is not a valid ISO-8601 duration", ex);
					}
				}

				@Override
				public String print(Duration value, ChronoUnit unit) {
					return value.toString();
				}

			};

			private final Pattern pattern;

			DurationStyle(String pattern) {
				this.pattern = Pattern.compile(pattern);
			}

			protected final boolean matches(String value) {
				return this.pattern.matcher(value).matches();
			}

			protected final Matcher matcher(String value) {
				return this.pattern.matcher(value);
			}

			public Duration parse(String value) {
				return parse(value, null);
			}

			public abstract Duration parse(String value, ChronoUnit unit);

			public String print(Duration value) {
				return print(value, null);
			}
			public abstract String print(Duration value, ChronoUnit unit);

			public static Duration detectAndParse(String value) {
				return detectAndParse(value, null);
			}

			public static Duration detectAndParse(String value, ChronoUnit unit) {
				return detect(value).parse(value, unit);
			}

			public static DurationStyle detect(String value) {
				Assert.notNull(value, "Value must not be null");
				for (DurationStyle candidate : values()) {
					if (candidate.matches(value)) {
						return candidate;
					}
				}
				throw new IllegalArgumentException("'" + value + "' is not a valid duration");
			}

			enum Unit {

				/**
				 * Nanoseconds.
				 */
				NANOS(ChronoUnit.NANOS, "ns", Duration::toNanos),

				/**
				 * Microseconds.
				 */
				MICROS(ChronoUnit.MICROS, "us", (duration) -> duration.toMillis() * 1000L),

				/**
				 * Milliseconds.
				 */
				MILLIS(ChronoUnit.MILLIS, "ms", Duration::toMillis),

				/**
				 * Seconds.
				 */
				SECONDS(ChronoUnit.SECONDS, "s", Duration::getSeconds),

				/**
				 * Minutes.
				 */
				MINUTES(ChronoUnit.MINUTES, "m", Duration::toMinutes),

				/**
				 * Hours.
				 */
				HOURS(ChronoUnit.HOURS, "h", Duration::toHours),

				/**
				 * Days.
				 */
				DAYS(ChronoUnit.DAYS, "d", Duration::toDays);

				private final ChronoUnit chronoUnit;

				private final String suffix;

				private Function<Duration, Long> longValue;

				Unit(ChronoUnit chronoUnit, String suffix, Function<Duration, Long> toUnit) {
					this.chronoUnit = chronoUnit;
					this.suffix = suffix;
					this.longValue = toUnit;
				}

				public Duration parse(String value) {
					return Duration.of(Long.parseLong(value), this.chronoUnit);
				}

				public String print(Duration value) {
					return longValue(value) + this.suffix;
				}

				public long longValue(Duration value) {
					return this.longValue.apply(value);
				}

				public static Unit fromChronoUnit(ChronoUnit chronoUnit) {
					if (chronoUnit == null) {
						return Unit.MILLIS;
					}
					for (Unit candidate : values()) {
						if (candidate.chronoUnit == chronoUnit) {
							return candidate;
						}
					}
					throw new IllegalArgumentException("Unknown unit " + chronoUnit);
				}

				public static Unit fromSuffix(String suffix) {
					for (Unit candidate : values()) {
						if (candidate.suffix.equalsIgnoreCase(suffix)) {
							return candidate;
						}
					}
					throw new IllegalArgumentException("Unknown unit '" + suffix + "'");
				}

			}

		}
	
	# 解析和格式化
		// 5分钟
		Duration duration = DurationStyle.SIMPLE.parse("5m");
		System.out.println(duration); // PT5M

		// 300 秒
		String result = DurationStyle.SIMPLE.print(duration, ChronoUnit.SECONDS);
		System.out.println(result); // 300s
	
	# 用于配置的绑定，可以在 Duration 字段表示 @DurationFormat 注解来自动完成
	
	# 用于请求参数，需要自己添加 Converter
		import org.springframework.boot.convert.DurationStyle;
		import org.springframework.core.convert.converter.Converter;

		import java.time.Duration;

		public class DurationConverter implements Converter<String, Duration> {

			@Override
			public Duration convert(String source) {
				return DurationStyle.SIMPLE.parse(source);
			}
		}
			
		
		// 在WebMvcConfigurer中添加实现
		@Override
		public void addFormatters(FormatterRegistry registry) {
			registry.addConverter(new DurationConverter());
		}