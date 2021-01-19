---------------------
联合主键
---------------------
	@IdClass
		Class value();
		
		* 标识在实体类上. 指定id属性是外部的类的联合主键(复合主键类)
		* 作为复合主键类必须要有几个条件
			1. 必须实现 Serializable
			2. 必须有 public 无参构造器
			3. 必须覆写 hashCode equals 方法(EntityManger通过find方法来查找Entity时,是根据equals的返回值来判断的)
		
		// 使用用户 user_id 和 title 作为联合主键
		public class UserBlogKey implements Serializable {
			private Integer userId; 
			private String title;	
			// 忽略构造方法和getter/setter/equals/hashCode
		}

		@Entity
		@Table(name = "user_blog")
		@IdClass(value = UserBlogKey.class) // 指定联合主键类
		public class UserBlog {
			// 指定联合主键对象中的字段名称
			@Id
			@Column(name = "user_id", nullable = false)
			private Integer userId;
			// 指定联合主键对象中的字段名称
			@Id
			@Column(name = "title", nullable = false)
			private String title;
			
			// 其他的字段
			@Column(name = "name")
			private String name;  

			// 忽略getter/setter
		}

		CREATE TABLE `user_blog` (
		  `title` varchar(255) NOT NULL,
		  `user_id` int(11) NOT NULL,
		  `id` int(11) NOT NULL,
		  `name` varchar(255) DEFAULT NULL,
		  PRIMARY KEY (`title`,`user_id`)
		) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
		
		interface UserBlogRepository extends JpaRepository<UserBlog, UserBlogKey>

---------------------
demo
---------------------
	import java.io.Serializable;
	import javax.persistence.Column;
	import javax.persistence.Entity;
	import javax.persistence.IdClass;
	import javax.persistence.Table;

	@Entity
	@Table(name = "user_role")
	@IdClass(UserRole.Id.class)
	public class UserRole implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 9083244121513095387L;

		@javax.persistence.Id
		@Column(columnDefinition = "INT(11) unsigned COMMENT '用户id'")
		private Integer userId;
		
		@javax.persistence.Id
		@Column(columnDefinition = "INT(11) unsigned COMMENT '用户id'")
		private Integer roleId;

		public Integer getUserId() {
			return userId;
		}
		public Integer getRoleId() {
			return roleId;
		}
		public void setRoleId(Integer roleId) {
			this.roleId = roleId;
		}
		public void setUserId(Integer userId) {
			this.userId = userId;
		}
		
		public static class Id implements Serializable {
			private static final long serialVersionUID = 2424506186663199332L;
			private Integer userId;
			private Integer roleId;
			public Integer getUserId() {
				return userId;
			}
			public void setUserId(Integer userId) {
				this.userId = userId;
			}
			public Integer getRoleId() {
				return roleId;
			}
			public void setRoleId(Integer roleId) {
				this.roleId = roleId;
			}
			@Override
			public int hashCode() {
				final int prime = 31;
				int result = 1;
				result = prime * result + ((roleId == null) ? 0 : roleId.hashCode());
				result = prime * result + ((userId == null) ? 0 : userId.hashCode());
				return result;
			}
			@Override
			public boolean equals(Object obj) {
				if (this == obj)
					return true;
				if (obj == null)
					return false;
				if (getClass() != obj.getClass())
					return false;
				Id other = (Id) obj;
				if (roleId == null) {
					if (other.roleId != null)
						return false;
				} else if (!roleId.equals(other.roleId))
					return false;
				if (userId == null) {
					if (other.userId != null)
						return false;
				} else if (!userId.equals(other.userId))
					return false;
				return true;
			}
		}
	}
	
	
	@NoRepositoryBean
	public interface BaseRepository <T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor <T>, QuerydslPredicateExecutor<T> {
	}
		
	public interface UserRoleRepository extends BaseRepository<UserRole, UserRole.Id> {
	}
	

	// 根据联合id删除方式1
	UserRole userRole = new UserRole();
	userRole.setRoleId(1);
	userRole.setUserId(1);
	this.userRoleService.delete(userRole);
	
	// 根据联合id删除方式2
	UserRole.Id id = new UserRole.Id();
	id.setRoleId(1);
	id.setUserId(1);
	this.userRoleService.deleteById(id);  // 如果删除的记录不存在，会给出异常

	


