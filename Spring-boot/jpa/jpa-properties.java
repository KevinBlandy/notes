
# 相关的配置类
	JpaProperties (spring.jpa)
	SpringDataWebProperties (spring.data.web)
	HibernateProperties	(spring.jpa.hibernate)

# SpringData根JPA的相关配置
	spring.data.jpa.repositories.bootstrap-mode=default
		* 枚举
			DEFAULT(默认)
			DEFERRED
			LAZY
	spring.data.jpa.repositories.enabled=true
	

# JPA的配置
	spring.jpa.database=
		* 指定ioc中的数据库, 默认可以检测到

	spring.jpa.database-platform=
		* 指定数据库的类型, Dialect 的实现类, 例如: org.hibernate.dialect.MySQL57Dialect

	spring.jpa.generate-ddl=false
		* 是否在启动时初始化schema, 默认为false

	spring.jpa.open-in-view=true
		* 是否注册 OpenEntityManagerInViewInterceptor, 绑定JPA EntityManager 到请求线程中, 默认为: true

	spring.jpa.show-sql=false
		* 是否打印SQL

	spring.jpa.properties.*
		* JPA实现, 定义的配置
		* Hibernate的可配置项, 都在: org.hibernate.cfg.AvailableSettings

	spring.jpa.mapping-resources=

# Hibernate的配置
	spring.jpa.hibernate.ddl-auto=
		* 枚举
			create				不管表是否存在, 每次启动都会重新建表(会导致数据丢失)
			create-drop			启动的时候创建表, 程序退出(SessionFactory关闭)的时候删除表
			none				不进行任何操作
			update				如果数据表不存在则创建, 在实体对象被修改后,下次启动重新修改表结构(不会删除已经存在的数据)
			validate			启动的时候验证数据表的结构, 

	spring.jpa.hibernate.naming.implicit-strategy=
	spring.jpa.hibernate.naming.physical-strategy=
	spring.jpa.hibernate.use-new-id-generator-mappings=

