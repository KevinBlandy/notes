-------------------------------
ActiveMQ-单机多启动				|
-------------------------------
	1,复制整个conf文件夹,可以命名为:conf2
	2,修改里面的activemq.xml文件
		①,brokerName 不能重复
		②,数据存放分文件名称不能重复,不如
			<kahaDB directoty="${activemq.data}/kahadb_2"/>
		③所有涉及到 TransportConnectors 的端口,都不能重复
	3,修改jetty.xml,主要就是修改端口
		<property name="port" value="8181"/>
	4,进入bin目录,复制一份activemq启动脚本,重命名为 acticemq2
		①,修改程序ID,不能重复
			* ACTIVEMQ_PIDFILE ="$ACTIVEMQ_DATA/activemq2-`hostname`.pid"
		②,修改配置文件路径
			* ACTIVEMQ_CONF="$ACTIVEMQ_BASE/conf2"
		③,修改端口,里面有个TCP的61616的端口,不能相同,改为跟activemq.xml相同即可
		④,然后就可以执行了
