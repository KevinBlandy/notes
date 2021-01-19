------------------
entity
------------------
	# 实体有两个关键注解
		@Entity		被EntityManage管理, 可以在很多地方使用缩写
		@Table		需要映射表
	
------------------
Persistable
------------------
	# 一个用于标识实体类是不是新的实体类的接口
	# 最佳的实践, 就是公共的父类实现该接口, 并且设置回调方法, 来修改对象的状态
		@MappedSuperclass
		public abstract class AbstractBaseEntity<ID> implements Persistable<ID> {

			@Transient
			private boolean isNew = true; 

			@Override
			public boolean isNew() {
				return isNew; 
			}

			@PrePersist			// 在存储之前执行
			@PostLoad			// 在加载之后执行
			void markNotNew() {
				this.isNew = false;
			}
			// More code…
		}

------------------
EntityInformation
------------------