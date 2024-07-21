---------------------
discourse			 |
---------------------
	# github
		https://github.com/discourse/discourse

	# ��װ
		https://github.com/discourse/discourse_docker
		https://github.com/discourse/discourse/blob/master/docs/INSTALL-cloud.md
	
	# ��֪��վ
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
		
	
	# Docker��װ
		* �Ƴ��ɰ汾
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
		* ��װϵͳ����Ҫ�Ĺ���
			yum install -y yum-utils device-mapper-persistent-data lvm2
		
		* ������Դ��Ϣ
			yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
		
		* ���� yum ����
			yum makecache fast
		
		* ��װ Docker-ce
			yum -y install docker-ce
		
		* ���� Docker ��̨����
			systemctl start docker
		
		* �������� hello-world
			docker run hello-world
		 
		* ɾ��docket ce
			yum remove docker-ce
			rm -rf /var/lib/docker
	
	# ֱ��ʹ�ýű���װ
		wget -qO- https://get.docker.com/ | sh
	

---------------------
launcher ά��		 |
---------------------
	�﷨: launcher COMMAND CONFIG [--skip-prereqs] [--docker-args STRING]
	COMMAND:
		start:      Start/initialize a container
			* ��ʼ��һ��container
		stop:       Stop a running container
			* ֹͣһ��container
		restart:    Restart a container
			* ����һ��container
		destroy:    Stop and remove a container
		enter:      Use nsenter to get a shell into a container
			* ���뵽����
		logs:       View the Docker logs for a container
		bootstrap:  Bootstrap a container for the config based on a template
		rebuild:    Rebuild a container (destroy old, bootstrap, start new)
			* ���¹���
		cleanup:    Remove all containers that have stopped for > 24 hours

	Options:
		--skip-prereqs             Don't check launcher prerequisites
		--docker-args              Extra arguments to pass when running docker


---------------------
�����Ӱ�			 |
---------------------
	# �����Ʋ���ʱ�����ʼ�Ҫע��

		* �����������

DISCOURSE_SMTP_AUTHENTICATION: login
DISCOURSE_SMTP_OPENSSL_VERIFY_MODE: none
DISCOURSE_SMTP_ENABLE_START_TLS: true

		* �����������ļ����� run: ��һ�����ҵ�
			run:
			  - exec: echo "Beginning of custom commands"

			  ## If you want to set the 'From' email address for your first registration, uncomment and change:
			  #- exec: rails r "SiteSetting.notification_email='info@unconfigured.discourse.org'"
			  ## After getting the first signup email, re-comment the line. It only needs to run once. 

		* ɾ���� #- exec: rails r "SiteSetting.notification_email='info@unconfigured.discourse.org'"* ��һ�п�ͷ�� # ����
		* �ٰ� info@unconfigured.discourse.org �ĳ���ķ��������ַ
		* �༭�ļ���ʱ��Ҫɾ��ÿһ��ǰ�Ŀո��,�������������Ƕ����,��Ҫɾ��û˵��������
	
	# Swap�ռ䲻��ʱ����Ҫ����
		* ���������ļ�
			dd if=/dev/zero of=/swapfile bs=1M count=4096
		
		* ����ΪSwap�ļ�
			mkswap /swapfile
		
		* ��Swap�ļ���Ч
			swapon /swapfile
		
		* �鿴��ǰSWAP
			swapon -s
		
	# ɾ��Swap
		
		* ֹͣswap����
			swapoff /swapfile
		
		* ɾ��swap�����ļ�
			rm -rf /swapfile

---------------------
�����Ӱ�			 |
---------------------
	Staff 
		* ϵͳ��ģ��,�����й̶�����ɾ��������
			��������
			��������
			��˽

---------------------
zoho ���������
---------------------

  DISCOURSE_SMTP_ADDRESS: smtp.zoho.com
  DISCOURSE_SMTP_PORT: 587
  DISCOURSE_SMTP_USER_NAME: no-reply@springcloud.io
  DISCOURSE_SMTP_PASSWORD: "123456"
  #DISCOURSE_SMTP_ENABLE_START_TLS: true           # (optional, default true)
  DISCOURSE_SMTP_DOMAIN: springcloud.io
  DISCOURSE_NOTIFICATION_EMAIL: "no-reply@springcloud.io"
	

---------------------
�����Ӱ�			 |
---------------------

	# ���ö���ȫ����ʾ��Ϣ
		���� -> δ���� > global notice

	# �������ӱ�ǩ����
		���� -> ��ǩ 
			min trust to create tag			������ǩ�������С���εȼ�
			min trust level to tag topics	������ӱ�ǩ����С���εȼ�
	
	# ���÷���ҳ�����ʽ(/categories)
		���� -> �������� -> desktop category page style
	
	# ������ҳ�Ĳ˵�(���Ӳ���)
		���� -> �������� -> top menu
		categories		����
		latest			����
		top				����
		posted			�ҵ�����
		new				
		red				�Ѷ�
		unred			δ��
		bookmarks		�ղ�
	
	# ������˻���
		���� -> ���� -> approve post count (���û�������û���Ҫ����˵�����������һ������Ϊ1����ʾ0���û��������ӣ���Ҫ�����һ��)
		���� -> ���� -> approve unless trust level (�����εȼ�֮�µ��û������ӱ��뱻���)
		���� -> ���� -> approve new topics unless trust level (���ڸ����εȼ����û�����������Ҫ����ˣ�һ������Ϊ0����ʾ0���û���������Ҫ�����)
	
	# ѯ���Ƿ�����֪ͨ
		���� -> �������� -> push notifications prompt

	# ����֪ͨͼ��
		���� -> �������� -> push notifications icon

	# Github��¼
		���� -> ��¼ -> github

		* github�Ļص���ַ��
			{site}/auth/github/callback
	
	
	# �Զ�����
		���� -> ���� -> backup frequency
			* �����챸��һ��
		
		���� -> ���� -> allow restore
			* �����뱸�ݵ�����,����滻ȫվ������
	
	# �̶���ҳ������
		���� -> �������� -> fixed category positions

	# ���û���ӭ˽������

	# �޸�ģ���������
		���� -> ���� -> �Զ��� CSS/HTML
	
	# ɾ��δ����ģ��
		���� -> ���� -> allow uncategorized topics
	
	# ������ʹ���ⲿϵͳ��ͷ��
		���� -> �ļ� -> external system avatars enabled
		* ���ʹ�õĻ�,���ܵ����û���ͷ�����ʧ��
	
	
	# ����ժҪ�ʼ���Ƶ��
		���� -> �û����� -> default email digest frequency
	
	# ��ʾ�ظ����ظ��ı�ʶ
		���� -> ���� -> suppress reply directly above




---------------------
һЩ����
---------------------
# ��Ϣ����
	/sidekiq

# rss�������ӣ������ύ���������棩
	/posts.rss
	/latest.rss
	/c/[����uri].rss


---------------------
���ڹ���
---------------------
	# ��� ruby �������ص�����
		* �� app.yml �� templates �ڵ��������������ã�
		* ���ڵ�һ��

			- "templates/web.china.template.yml"

------------------------------------------
ʹ���Լ������� - ������� socket
------------------------------------------
	# �༭ app.yml �� templates �ڵ�
		* ע�͵� ssl ����
		* ��� socketed ����

		  - "templates/postgres.template.yml"
		  - "templates/redis.template.yml"
		  - "templates/web.template.yml"
		  # - "templates/web.ssl.template.yml" # ע��
		  # - "templates/web.letsencrypt.ssl.template.yml" # ע��
		  - "templates/web.ratelimited.template.yml"
		  - "templates/web.socketed.template.yml"  # ���
		
	# ע�͵� expose �µĶ˿�ӳ��
		# - "80:80"   # http
		# - "443:443" # https
	
	
	# �༭ nginx ����
		server_name discourse.example.com;  # �޸�Ϊ�Լ�������

		location / {
			proxy_pass http://unix:/var/discourse/shared/standalone/nginx.http.sock:;
			proxy_set_header Host $http_host;
			proxy_http_version 1.1;
			proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
			proxy_set_header X-Forwarded-Proto $scheme;
			proxy_set_header X-Real-IP $remote_addr;
		}
	
	# �ο���https://meta.discourse.org/t/run-other-websites-on-the-same-machine-as-discourse/17247/269
	# ���ַ�ʽ�Ƚϸ���

------------------------------------------
ʹ���Լ������� - ˫ Nginx
------------------------------------------
	# �༭ app.yml �� templates �ڵ�
		* ע�͵� ssl ����

		  - "templates/postgres.template.yml"
		  - "templates/redis.template.yml"
		  - "templates/web.template.yml"
		  - "templates/web.ratelimited.template.yml"
		  # - "templates/web.ssl.template.yml" # ע��
		  # - "templates/web.letsencrypt.ssl.template.yml" # ע��
	
	# ע�͵� expose �ڵ��µ� https �˿�ӳ������
		- "80:80"   # http
		# - "443:443" # https

	# ���¹���
		
		

------------------------------------------
���ڵĴ���Դ
------------------------------------------
	# ɾ��Ĭ�ϵĹٷ�Դ
	gem sources --remove https://rubygems.org/  


	# ���Դ�����⣩
		gem sources -a https://ruby.taobao.org/
		gem sources -a https://gems.ruby-china.com/

	# �鿴��ǰԴ
		gem sources -l                                # https://ruby.taobao.org
	
	
	# �� app.yaml �����ļ�ĩβ�������
		
		hooks:
		  after_code:
			# ͨ������������ yarn �ľ����ַ
			- exec:
				cd: /var/www/discourse
				cmd:
				  - "yarn config set registry https://registry.npm.taobao.org/"
			# ���� yarn �ĳ�ʱʱ��
			- exec:
				cd: $home
				cmd:
				  - "su discourse -c 'yarn config set network-timeout 6000000 -g'"