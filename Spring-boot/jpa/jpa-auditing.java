-------------------------
Auditing
-------------------------
	# 一般一条记录包含了: 创建用户, 创建时间, 修改用户, 修改时间等等信息,
		* spring-data-jpa 提供一系列方便的注解

	# 在实体属性上标识相关注解
		@CreatedBy
		@CreatedDate
		@LastModifiedBy
		@LastModifiedDate

		@CreatedBy
		@Column(name = "created_by", columnDefinition = "INT(11) UNSIGNED NOT NULL COMMENT '创建用户'")
		private Integer createdBy;
		
		@CreatedDate
		@Column(name = "created_date", columnDefinition = "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'")
		private LocalDateTime createdDate;
		
		@LastModifiedBy
		@Column(name = "last_modified_by", columnDefinition = "INT(11) UNSIGNED COMMENT '最后修改用户'")
		private Integer lastModifiedBy;
		
		@LastModifiedDate
		@Column(name = "last_modified_date", columnDefinition = "timestamp NULL DEFAULT NULL COMMENT '最后修改时间'")
		private LocalDateTime lastModifiedDate;

	# 在实体类上标识注解注解
		@EntityListeners
			|- Class[] value();
				* 指定监听器
		
		@EntityListeners(value = { AuditingEntityListener.class })
	
	# 实现: AuditorAware 接口, 用于系统获取当前用户id
		public interface AuditorAware<T> {
			Optional<T> getCurrentAuditor();
		}

		import java.util.Optional;
		import javax.servlet.http.HttpSession;
		import org.springframework.data.domain.AuditorAware;
		import org.springframework.stereotype.Component;
		import org.springframework.web.context.request.RequestContextHolder;
		import org.springframework.web.context.request.ServletRequestAttributes;

		@Component
		public class SessionUserIdAuditorAware implements AuditorAware<Integer>{

			@Override
			public Optional<Integer> getCurrentAuditor() {
				ServletRequestAttributes servletRequestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
				HttpSession session = servletRequestAttributes.getRequest().getSession(false);
				return session == null ? Optional.empty() : Optional.of((Integer)session.getAttribute("session_user_id"));
			}
		}

		* 该接口返回对象的关联id
	
	# 通过注解开始 jpa-auditing
		@EnableJpaAuditing
			|-String auditorAwareRef() default "";
			|-boolean setDates() default true;
			|-boolean modifyOnCreate() default true;
			|-tring dateTimeProviderRef() default "";
		