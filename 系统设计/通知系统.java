----------------------------
通知系统					|
----------------------------
	# 通知类别
		* 公告
		* 提醒
			- 资源订阅提醒「我关注的资源有更新、评论等事件时通知我」
			- 资源发布提醒「我发布的资源有评论、收藏等事件时通知我」
			- 系统提醒「平台会根据一些算法、规则等可能会对你的资源做一些事情，这时你会收到系统通知」
		* 私信
	
----------------------------
公告						|
----------------------------
	# 公告表 notify
		id			//公告编号
		userId		//发送者编号，通常为系统管理员
		titletype	//公告标题
		content		// 内容
		createdAt	//发送时间
	
	# 用户公告表 user_notify
		id					 //用户公告编号；
		notifyId			//公告id
		userId				//用户id
		createdAt			//拉取公告时间；
		state				//状态，已读|未读；
		readDate			//阅读时间
	
	# 读取未读通知
		* 平台发布一则公告之后, 当用户登录的时候去拉取站内公告(notify)并插入 user_notify 表, 这样那些很久都没登陆的用户就没必要插入了
		*「首次拉取，根据用户的注册时间: 否则根据 user_notify.createdAt 即上一次拉取的时间节点获取公告」
	
	# 在用户量过大的时候, 进行Hash分表
		user_notify_{hash(user_id)}

	
----------------------------
提醒						|
----------------------------
	# 提醒的内容公式
		「someone do something in someone’s something」
		「谁对一样属于谁的事物做了什么操作」

		someone			事件触发者(用户)
		do something	事件(行为)
		someone’s		资源所有人(用户)
		something		资源
	

	notify_event 通知事件表
		id: {type: 'integer', primaryKey: true, autoIncrement:true} 
		userID: {type: 'string', required: true} //用户ID
		action: {type: 'string', required: true} //动作，如：捐款/更新/评论/收藏
		objectID: {type: 'string', required: true}, //对象ID，如：文章ID；
		objectType: {type: 'string', required: true} //对象所属类型，如：人、文章、活动、视频等；
		createdAt：{type: 'timestamp', required: true} //创建时间；
	
	notify_setting_config	通知设置配置表
		id: {type: 'integer', primaryKey: true, autoIncrement:true} //通知设置编号；
		objectType: {type: 'string', required: true} //资源对象类型，如：项目、文章、评论、商品、视频、图片、用户；
		action: {type: 'string', required: true} //动作，也即通知类型，如：捐款、更新、评论、收藏
		objectRelationship: {type: 'string', required: true} //用户与资源的关系，如：用户发布的published，用户关注的followed；
		messageTemplate: {type: 'string', required: true} //为某个通知类型设置对应的消息模版
		notifyChannel: {type: 'string', required: true} //为某个通知类型设置一个或多个推送渠道
		description: {type: 'string', required: true}  //设置选项的内容描述
		settingType: {type: 'string', required: true} //remind、privateLetters
	
	notify_setting	通知设置表
		id: {type: 'integer', primaryKey: true, autoIncrement:true} //用户通知设置ID；
		userId: {type: 'string', required: true}，//用户ID，对应 notify_remind 中的 recipientId；
		settingId: {type: 'string', required: true}，//通知设置表ID；
		createdAt：{type: 'timestamp', required: true} //创建时间；
	
	notify_remind 通知提醒表
		id: {type: 'integer', primaryKey: true, autoIncrement:true} //主键；
		remindID: {type: 'string', required: true} //通知提醒编号；
		senderID: {type: 'string', required: true} //操作者的ID，三个0代表是系统发送的；
		senderName: {type: 'string’, required: true} //操作者用户名；
		senderAction: {type: 'string', required: true} //操作者的动作，如：捐款、更新、评论、收藏；
		objectID: {type: 'string', required: true}, //目标对象ID；
		object: {type: 'string', required: false}, //目标对象内容或简介，比如：文章标题；
		objectType: {type: 'string', required: true} //被操作对象类型，如：人、文章、活动、视频等；
		recipientID: {type: 'string’} //消息接收者；可能是对象的所有者或订阅者；
		message: {type: 'text', required: true} //消息内容，由提醒模版生成，需要提前定义；
		createdAt：{type: 'timestamp', required: true} //创建时间；
		status：{type: 'integer', required: false} //是否阅读，默认未读；
		readAt:{type: 'timestamp', required: false} //阅读时间；


	-----------------------------
		Notify
		id          : {type: 'integer', primaryKey: true},      // 主键
		content     : {type: 'text'},   // 消息的内容
		type        : {type: 'integer', required: true, enum: [1, 2, 3]},  // 消息的类型，1: 公告 Announce，2: 提醒 Remind，3：信息 Message
		target      : {type: 'integer'},    // 目标的ID
		targetType  : {type: 'string'},    // 目标的类型
		action      : {type: 'string'},    // 提醒信息的动作类型
		sender      : {type: 'integer'},    // 发送者的ID
		createdAt   : {type: 'datetime', required: true}

		UserNotify
		id          : {type: 'integer', primaryKey: true},      // 主键
		isRead      : {type: 'boolean', required: true},   
		user        : {type: 'integer', required: true},  // 用户消息所属者
		notify      : {type: 'integer', required: true}   // 关联的Notify
		createdAt   : {type: 'datetime', required: true}

		Subscription
		target      : {type: 'integer', required: true},    // 目标的ID
		targetType  : {type: 'string', required: true},    // 目标的类型
		action      : {type: 'string'},						// 订阅动作,如: comment/like/post/update etc.
		user        : {type: 'integer'}，
		createdAt   : {type: 'datetime', required: true}

----------------------------
私信						|
----------------------------
