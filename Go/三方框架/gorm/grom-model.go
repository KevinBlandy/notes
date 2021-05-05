---------------
model
---------------
	# 模型以及字段
		* 模型是标准的 struct
		* 总段由 Go 的基本数据类型、实现了 Scanner 和 Valuer 接口的自定义类型及其指针或别名组成
	
	# 默认约定
		*  使用 ID 作为主键
		* 使用结构体名的 蛇形复数(下户线+复数) 作为表名
		* 字段名的 蛇形 作为列名
		* 并使用 CreatedAt、UpdatedAt 字段追踪创建、更新时间
	
	# 预定义的一个结构体
		// gorm.Model 的定义
		type Model struct {
		  ID        uint           `gorm:"primaryKey"`
		  CreatedAt time.Time
		  UpdatedAt time.Time
		  DeletedAt gorm.DeletedAt `gorm:"index"`
		}
	
	# 字段权限控制
		* 可导出的字段在使用 GORM 进行 CRUD 时拥有全部的权限
		* GORM 允许用标签控制字段级别的权限。这样就可以让一个字段的权限是只读、只写、只创建、只更新或者被忽略
		type User struct {
		  Name string `gorm:"<-:create"` // 只能读，创建
		  Name string `gorm:"<-:update"` // 只能读，更新
		  Name string `gorm:"<-"`        // 只能读，更新，创建
		  Name string `gorm:"<-:false"`  // 只能，禁止写
		  Name string `gorm:"->"`        // readonly (disable write permission unless it configured )
		  Name string `gorm:"->;<-:create"` // allow read and create
		  Name string `gorm:"->:false;<-:create"` // createonly (disabled read from db)
		  Name string `gorm:"-"`  // ignore this field when write and read with struct
		}
	
	# 创建/更新时间追踪（纳秒、毫秒、秒、Time）
		* 约定使用 CreatedAt、UpdatedAt 追踪创建/更新时间
		* 如果定义了这种字段，GORM 在创建、更新时会自动填充 当前时间
		* 如果要使用不同名称的字段，可以配置 autoCreateTime、autoUpdateTime 标签
		* 如果想要保存 UNIX（毫/纳）秒时间戳，而不是 time，只需简单地将 time.Time 修改为 int 即可
			type User struct {
			  CreatedAt time.Time // 在创建时，如果该字段值为零值，则使用当前时间填充
			  UpdatedAt int       // 在创建时该字段值为零值或者在更新时，使用当前时间戳秒数填充

			  Updated   int64 `gorm:"autoUpdateTime:nano"` // 使用时间戳填纳秒数充更新时间
			  Updated   int64 `gorm:"autoUpdateTime:milli"` // 使用时间戳毫秒数填充更新时间
			  Created   int64 `gorm:"autoCreateTime"`      // 使用时间戳秒数填充创建时间
			}
		
	# 嵌入结构体
		* 匿名字段，GORM 会将其字段包含在父结构体中
		* 对于正常的结构体字段，可以通过标签 embedded 将其嵌入
			type Author struct {
				Name  string
				Email string
			}

			type Blog struct {
			  ID      int
			  Author  Author `gorm:"embedded"`		// embedded嵌入
			  Upvotes int32
			}

			// 等效于
			type Blog struct {
			  ID    int64
				Name  string
				Email string
			  Upvotes  int32
			}
		
		* 可以使用标签 embeddedPrefix 来为 db 中的字段名添加前缀
			type Blog struct {
			  ID      int
			  Author  Author `gorm:"embedded;embeddedPrefix:author_"`
			  Upvotes int32
			}
			// 等效于
			type Blog struct {
			  ID          int64
				AuthorName  string
				AuthorEmail string
			  Upvotes     int32
			}
	
	# 索引
		* 普通索引/唯一索引
			type User struct {
				ID uint
				NickName string `gorm:"type:varchar(10); index:name"`			// `nick_name` varchar(10) DEFAULT NULL / KEY `name` (`nick_name`)
				Email string `gorm:"type:varchar(50); uniqueIndex:mail"`		// `email` varchar(50) DEFAULT NULL	/ UNIQUE KEY `mail` (`email`)
				Number uint `gorm:"type:int(50) unsigned; index:,unique"`		//  `number` int(50) unsigned DEFAULT NULL / UNIQUE KEY `idx_users_number` (`number`)
			}

		* 联合索引，2个index名称相同就是联合索引
			type UserEmail struct {
				ID uint
				UserId uint		`gorm:"uniqueIndex:user_id_email; type:INT(11) UNSIGNED NOT NULL COMMENT '用户ID'"`
				Email string	`gorm:"uniqueIndex:user_id_email; type:VARCHAR(50) NOT NULL COMMENT '邮箱账户'"`
			}

			* 只要有任何一个索引为 uniqueIndex，则最终的联合索引都是unique的
			* 可以通过 priority 指定索引列顺序（值越小，越靠前），默认优先级值是 10，如果优先级值相同，则顺序取决于模型结构体字段的顺序
				Email string	`gorm:"index:user_id_email,priority:5; type:VARCHAR(50) NOT NULL COMMENT '邮箱账户'; "`
				
			
		* 同一个字段，多个索引，重复的声明 index 标签就行
			type UserEmail struct {
				ID uint
				UserId uint		`gorm:"index:user_id_email; type:INT(11) UNSIGNED NOT NULL COMMENT '用户ID'"`
				Email string	`gorm:"index:user_id_email; index:email; type:VARCHAR(50) NOT NULL COMMENT '邮箱账户'; "`
			}
			KEY `user_id_email` (`user_id`,`email`),
			KEY `email` (`email`)
	
	# 联合ID，设置多个primaryKey即可
		type UserEmail struct {
			UserId uint		`gorm:"primaryKey; type:INT(11) UNSIGNED NOT NULL COMMENT '用户ID'"`
			EmailId uint	`gorm:"primaryKey; type:VARCHAR(50) NOT NULL COMMENT '邮箱ID'; "`
		}
		
	
	# 自定义表名，可以通过实现 Tabler 接口来更改默认表名
		type Tabler interface {
			TableName() string
		}
	
		* TableName 不支持动态变化，它会被缓存下来以便后续使用
	
	

---------------
所有标签
---------------	
	column					指定 db 列名
	type					列数据类型，推荐使用兼容性好的通用类型，
		* 例如：所有数据库都支持 bool、int、uint、float、string、time、bytes 
		* 并且可以和其他标签一起使用，例如：not null、size, autoIncrement… 像 varbinary(8) 这样指定数据库数据类型也是支持的。
		* 在使用指定数据库数据类型时，它需要是完整的数据库数据类型，如：MEDIUMINT UNSIGNED not NULL AUTO_INSTREMENT
	size					指定列大小，例如：size:256
	primaryKey				指定列为主键
	unique					指定列为唯一
	default					指定列的默认值，插入记录到数据库时，默认值 会被用于 填充值为 零值 的字段
	precision				指定列的精度
	scale					指定列大小
	not null				指定列为 NOT NULL
	autoIncrement			指定列为自动增长
	autoIncrementIncrement	自动步长，控制连续记录之间的间隔
	embedded				嵌套字段
	embeddedPrefix			嵌入字段的列名前缀
	autoCreateTime			创建时追踪当前时间，对于 int 字段，它会追踪秒级时间戳，您可以使用 nano/milli 来追踪纳秒、毫秒时间戳，例如：autoCreateTime:nano
	autoUpdateTime			创建/更新时追踪当前时间，对于 int 字段，它会追踪秒级时间戳，您可以使用 nano/milli 来追踪纳秒、毫秒时间戳，例如：autoUpdateTime:milli
	index					根据参数创建索引，多个字段使用相同的名称则创建复合索引，查看 索引 获取详情
	uniqueIndex				与 index 相同，但创建的是唯一索引
	check					创建检查约束，例如 check:age > 13，查看 约束 获取详情
	<-						设置字段写入的权限， <-:create 只创建、<-:update 只更新、<-:false 无写入权限、<- 创建和更新权限
	->						设置字段读的权限，->:false 无读权限
	-						忽略该字段，- 无读写权限
	comment					迁移时为字段添加注释

------------------------
几个模型Demo
---------------	---------
	// 用户
	type User struct {
		Id 				uint		`gorm:"type:int(11) unsigned not null auto_increment comment 'ID';"`
		NickName 		string		`gorm:"type:varchar(20) default null comment '昵称'"`
		Email 			string		`gorm:"type:varchar(50) not null comment '邮箱'; index:email,unique"`
		CreateAt 		*time.Time 	`gorm:"type:timestamp not null default current_timestamp comment '创建时间'"`
		UpdateAt 		*time.Time	`gorm:"type:timestamp not null default current_timestamp comment '修改时间'"`
		Version 		uint		`gorm:"type:int(11) unsigned not null comment '版本号'"`
		Introduction	string		`gorm:"type:varchar(200) default null comment '个人说明'"`
	}

	// 用户配置 （一对一）
	type UserConfig struct {
		UserId 		uint	`gorm:"primaryKey; type:int(11) unsigned not null comment '用户ID'"`
		AllowFollow bool	`gorm:"type:tinyint(1) unsigned not null comment '是否允许别人关注我'"`
	}

	// 角色
	type Role struct {
		Id		uint	`gorm:"type:int(11) unsigned not null auto_increment comment 'ID';"`
		Name	string	`gorm:"type:varchar(20) not null comment '角色名称'"`
	}

	// 用户角色关联 （多对多）
	type UserRole struct {
		UserId	uint	`gorm:"primaryKey; type:int(11) unsigned not null comment '用户ID'"`
		RoleId	uint	`gorm:"primaryKey; type:int(11) unsigned not null comment '角色ID'; index:role_id"`
	}

	# 通用的设计
		type Menu struct {
			Id int				`gorm:"type:int(32) unsigned auto_increment; primaryKey; comment:ID" json:"id"`
			ParentId int		`gorm:"type:int(32) unsigned; not null; size:32; comment:父级菜单ID"'"`
			Type MenuType		`gorm:"not null; size:10; comment:菜单类型"`
			Icon string			`gorm:"size:50; comment:小图标"`
			Title	string		`gorm:"not null; size:20; uniqueIndex:title; comment:标题"`
			Mapping	string		`gorm:"not null; size:30; uniqueIndex:mapping_method; comment:请求路径"`
			Method string		`gorm:"not null; size:10; uniqueIndex:mapping_method; comment:请求方法"`
			Enabled bool		`gorm:"type:tinyint(1) unsigned; not null; comment:是否开启"`
			Comment string		`gorm:"size:200; comment:备注"`
			CreateAt *time.Time	`gorm:"type:timestamp not null default current_timestamp; comment:创建时间"`
			UpdateAt *time.Time	`gorm:"type:timestamp null default null; comment:修改时间"`
		}
