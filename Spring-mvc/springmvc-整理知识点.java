	classpath：只会到你的class路径中查找找文件;
	classpath*：不仅包含class路径，还包括jar文件中(class路径)进行查找


静态资源解析:
	<mvc:resources mapping="/static/**" location="/static/"/>
    <mvc:resources mapping="/assets/**" location="/assets/"/>
    <mvc:resources mapping="/upload/**" location="/upload/"/>

