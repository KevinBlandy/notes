---------------------
discourse			 |
---------------------
	# github
		https://github.com/discourse/discourse

	# 安装
		https://github.com/discourse/discourse_docker
		https://github.com/discourse/discourse/blob/master/docs/INSTALL-cloud.md
	
	# 已知的站
		https://springboot.io/
		https://discuss.kubernetes.io/
		https://discuss.kotlinlang.org/
		https://github.community/
		https://users.rust-lang.org/
		https://twittercommunity.com/
		https://discuss.rubyonrails.org/
		https://forum.realm.io/
		https://forum.vuejs.org/
		https://discuss.python.org/
		https://community.jenkins-zh.cn/
		https://forum.cocos.org/
		https://developer.mongodb.com/community/forums/
		https://community.letsencrypt.org/
		https://discourse.julialang.org/
		https://forum.serverless.com/
		https://forum.dorisdb.com/
		https://discourse.gohugo.io/
		https://discourse.mailinabox.email/
		http://www.react-china.org/
		https://bbs.hankcs.com/
		https://asktug.com/
		
	
	# Docker安装
		* 移除旧版本
yum remove docker \
docker-client \
docker-client-latest \
docker-common \
docker-latest \
docker-latest-logrotate \
docker-logrotate \
docker-selinux \
docker-engine-selinux \
docker-engine
		* 安装系统所需要的工具
			yum install -y yum-utils device-mapper-persistent-data lvm2
		
		* 添加软件源信息
			yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
		
		* 更新 yum 缓存
			yum makecache fast
		
		* 安装 Docker-ce
			yum -y install docker-ce
		
		* 启动 Docker 后台服务
			systemctl start docker
		
		* 测试运行 hello-world
			docker run hello-world
		 
		* 删除docket ce
			yum remove docker-ce
			rm -rf /var/lib/docker
	
	# 直接使用脚本安装
		wget -qO- https://get.docker.com/ | sh
	

---------------------
launcher 维护		 |
---------------------
	语法: launcher COMMAND CONFIG [--skip-prereqs] [--docker-args STRING]
	COMMAND:
		start:      Start/initialize a container
			* 初始化一个container
		stop:       Stop a running container
			* 停止一个container
		restart:    Restart a container
			* 重启一个container
		destroy:    Stop and remove a container
		enter:      Use nsenter to get a shell into a container
			* 进入到容器
		logs:       View the Docker logs for a container
		bootstrap:  Bootstrap a container for the config based on a template
		rebuild:    Rebuild a container (destroy old, bootstrap, start new)
			* 重新构建
		cleanup:    Remove all containers that have stopped for > 24 hours

	Options:
		--skip-prereqs             Don't check launcher prerequisites
		--docker-args              Extra arguments to pass when running docker


---------------------
杂七杂八			 |
---------------------
	# 阿里云部署时配置邮件要注意

		* 添加如下配置

DISCOURSE_SMTP_AUTHENTICATION: login
DISCOURSE_SMTP_OPENSSL_VERIFY_MODE: none
DISCOURSE_SMTP_ENABLE_START_TLS: true

		* 此外在配置文件最后的 run: 那一块中找到
			run:
			  - exec: echo "Beginning of custom commands"

			  ## If you want to set the 'From' email address for your first registration, uncomment and change:
			  #- exec: rails r "SiteSetting.notification_email='info@unconfigured.discourse.org'"
			  ## After getting the first signup email, re-comment the line. It only needs to run once. 

		* 删除掉 #- exec: rails r "SiteSetting.notification_email='info@unconfigured.discourse.org'"* 这一行开头的 # 井号
		* 再把 info@unconfigured.discourse.org 改成你的发件邮箱地址
		* 编辑文件的时候不要删除每一行前的空格符,保持语句块上下是对齐的,不要删除没说明的引号
	
	# Swap空间不足时，需要创建
		* 创建分区文件
			dd if=/dev/zero of=/swapfile bs=1M count=4096
		
		* 制作为Swap文件
			mkswap /swapfile
		
		* 让Swap文件生效
			swapon /swapfile
		
		* 查看当前SWAP
			swapon -s
		
	# 删除Swap
		
		* 停止swap分区
			/sbin/swapoff /swapfile
		
		* 删除swap分区文件
			rm -rf /swapfile

---------------------
杂七杂八			 |
---------------------
	Staff 
		* 系统的模块,里面有固定不能删除的帖子
			常见问题
			服务条款
			隐私

---------------------
zoho 邮箱的配置
---------------------

  DISCOURSE_SMTP_ADDRESS: smtp.zoho.com
  DISCOURSE_SMTP_PORT: 587
  DISCOURSE_SMTP_USER_NAME: no-reply@springcloud.io
  DISCOURSE_SMTP_PASSWORD: "123456"
  #DISCOURSE_SMTP_ENABLE_START_TLS: true           # (optional, default true)
  DISCOURSE_SMTP_DOMAIN: springcloud.io
  DISCOURSE_NOTIFICATION_EMAIL: "no-reply@springcloud.io"
	

---------------------
杂七杂八			 |
---------------------

	# 设置顶部全局提示信息
		设置 -> 未分类 > global notice

	# 开启帖子标签功能
		设置 -> 标签 
			min trust to create tag			创建标签所需的最小信任等级
			min trust level to tag topics	给主题加标签的最小信任等级
	
	# 设置分类页面的样式(/categories)
		设置 -> 基本设置 -> desktop category page style
	
	# 设置主页的菜单(帖子布局)
		设置 -> 基本设置 -> top menu
		categories		分类
		latest			最新
		top				热门
		posted			我的帖子
		new				
		red				已读
		unred			未读
		bookmarks		收藏
	
	# 帖子审核机制
		设置 -> 发帖 -> approve post count (新用户或基础用户需要被审核的帖子数量：一般设置为1，表示0级用户的新帖子，需要被审核一次)
		设置 -> 发帖 -> approve unless trust level (该信任等级之下的用户的帖子必须被审核)
		设置 -> 发帖 -> approve new topics unless trust level (低于该信任等级的用户的新帖子需要被审核：一般设置为0，表示0级用户的帖子需要被审核)
	
	# 询问是否允许通知
		设置 -> 基本设置 -> push notifications prompt

	# 设置通知图标
		设置 -> 基本设置 -> push notifications icon

	# Github登录
		设置 -> 登录 -> github

		* github的回调地址是
			{site}/auth/github/callback
	
	
	# 自动备份
		设置 -> 备份 -> backup frequency
			* 多少天备份一次
		
		设置 -> 备份 -> allow restore
			* 允许导入备份的数据,这会替换全站的数据
	
	# 固定主页板块分类
		设置 -> 基本设置 -> fixed category positions

	# 新用户欢迎私信设置

	# 修改模板引擎代码
		定制 -> 主题 -> 自定义 CSS/HTML
	
	# 删除未分类模块
		设置 -> 发帖 -> allow uncategorized topics
	
	# 不允许使用外部系统的头像
		设置 -> 文件 -> external system avatars enabled
		* 如果使用的话,可能导致用户的头像访问失败
	
	
	# 发送摘要邮件的频率
		设置 -> 用户设置 -> default email digest frequency
	
	# 显示回复到回复的标识
		设置 -> 发帖 -> suppress reply directly above




---------------------
一些链接
---------------------
/sidekiq
	

# rss订阅链接（可以提交给搜索引擎）
/posts.rss
/latest.rss
/c/[分类uri].rss
