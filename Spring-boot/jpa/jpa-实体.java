----------------
SysMenu
----------------
@Data
@AllArgsConstructor
@NoArgsConstructor
@With
@Builder

@Entity
@Table(name = "sys_menu", indexes = {
	@Index(columnList = "method,uri", name = "method_uri"),
	@Index(columnList = "uri", name = "uri")
})
@org.hibernate.annotations.Table(appliesTo = "sys_menu", comment = "系统菜单") // 过期了
@org.hibernate.annotations.Comment("系统菜单") // 用这个得
public class SysMenu implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3356718399731690634L;
	
	@Id
	@Column(columnDefinition = "INT UNSIGNED COMMENT 'ID'")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(columnDefinition = "VARCHAR(50) COMMENT '标题'", nullable = false)
	private String title;
	
	@Column(columnDefinition = "VARCHAR(10) COMMENT '菜单类型'", nullable = false)
	@Enumerated(EnumType.STRING)
	private Type type;
	
	@Column(columnDefinition = "VARCHAR(50) COMMENT 'icon图标'")
	private String icon;
	
	@Column(columnDefinition = "VARCHAR(50) COMMENT '请求方法'")
	private String method;
	
	@Column(columnDefinition = "VARCHAR(50) COMMENT '资源地址'")
	private String uri;
	
	@Column(columnDefinition = "TINYINT UNSIGNED COMMENT '是否启用。0：禁用，1：启用'", nullable = false)
	private Boolean enabled;
	
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'", nullable = false)
	private LocalDateTime createAt;
	
	@Column(columnDefinition = "TIMESTAMP DEFAULT NULL COMMENT '修改时间'")
	private LocalDateTime updateAt;	

	public static enum Type {
		
		menu,
		
		category,
		
		action
	}
}

----------------
SysMenu
----------------
@Data
@AllArgsConstructor
@NoArgsConstructor
@With
@Builder

@Entity
@Table(name = "sys_role_menu", indexes = {
	@Index(columnList = "role_id", name = "role_id")
})
@org.hibernate.annotations.Table(appliesTo = "sys_role_menu", comment = "系统角色 - 菜单")
@IdClass(SysRoleMenu.Id.class)
public class SysRoleMenu implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3955437842371321249L;

	@javax.persistence.Id
	@Column(name = "role_id", columnDefinition = "INT UNSIGNED COMMENT '角色ID'")
	private Integer roleId;
	
	@javax.persistence.Id
	@Column(name = "menu_id", columnDefinition = "INT UNSIGNED COMMENT '菜单ID'")
	private Integer menuId;
	
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'", nullable = false)
	private LocalDateTime createAt;
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@With
	@Builder
	public static class Id implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -6029333774586119866L;
		private Integer roleId;
		private Integer menuId;
	}
}