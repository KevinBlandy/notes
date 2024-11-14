------------------------
Jenkins					|
------------------------
	# ����
		https://jenkins.io/zh/
		https://www.jenkins-zh.cn/
	
	# �ĵ� & ѧϰ
		https://jenkins.io/zh/doc/



------------------------
Jenkins - War��װ		|
------------------------
	# ���� Jenkins war��
		https://mirrors.jenkins.io/war-stable/latest/jenkins.war

	# ֱ�� Java -jar ��������
		
		nohup java -jar jenkins.war --httpPort=8888 > jenkins.log 2>&1 &
	
	# ���������Ŀ¼
		~/.jenkins/

		* ����OK��, Jenkins��������һ�������ļ�, ��������˳�ʼ��ϵͳ������
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

