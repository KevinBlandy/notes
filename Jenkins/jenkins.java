------------------------
Jenkins					|
------------------------
	# 官网
		https://jenkins.io/zh/
		https://www.jenkins-zh.cn/
	
	# 文档 & 学习
		https://jenkins.io/zh/doc/



------------------------
Jenkins - War安装		|
------------------------
	# 下载 Jenkins war包
		https://mirrors.jenkins.io/war-stable/latest/jenkins.war

	# 直接 Java -jar 启动服务
		
		nohup java -jar jenkins.war --httpPort=8888 > jenkins.log 2>&1 &
	
	# 程序的数据目录
		~/.jenkins/

		* 启动OK后, Jenkins会在生成一个密码文件, 里面包含了初始化系统的密码
			/root/.jenkins/secrets/initialAdminPassword
	

------------------------
Jenkins - Docker
------------------------

docker run \
  --restart=always\
  --name jenkins\
  --privileged=true\
  -u root \
  -d \
  -p 8888:8080 \
  -p 50000:50000 \
  -v /root/jenkins_home:/var/jenkins_home \
  -v /var/run/docker.sock:/var/run/docker.sock \
  jenkinsci/blueocean




docker run \
  -d \
  --restart=always \
  --name jenkins \
  -p 8888:8080 \
  -p 50000:50000 \
  --privileged=true \
  -v /home/jenkins_home:/var/jenkins_home \
  jenkins/jenkins:2.375-jdk11

