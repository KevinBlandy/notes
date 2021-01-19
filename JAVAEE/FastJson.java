--------------------
FastJson			|
--------------------
	# 阿里巴巴的神器



--------------------
FastJson			|
--------------------
	主要类(静态)
		com.alibaba.fastjson.JSON
	

1,把对象序列化为json字符串
	String json = JSON.toJSONString(Object obj);
	String json = JSON.toJSONString(Object obj, boolean prettyFormat); // 将JavaBean序列化为带格式的JSON文本

2,把json,序列化为javaBean
	T t = parseObject(String text, Class<T> clazz); 

3,把json,序列化为javaBean集合
	List<T>  ts = parseArray(String text, Class<T> clazz); 

--------------------
FastJson-注解		|
--------------------
	@JSONField
		format
			* 标注在Bean的字段属性上,以字符串的形式指定格式化形式
		
		deserialize
			* true/false,是否不序列化该字段
	

--------------------
FastJson-整合springmvc|
--------------------

 <mvc:annotation-driven>
        <mvc:message-converters register-defaults="true">
            <bean class="com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter">
                <property name="supportedMediaTypes">
                    <list>
                        <value>text/html;charset=UTF-8</value>
                        <value>application/json</value>
                    </list>
                </property>
            </bean>
        </mvc:message-converters>
    </mvc:annotation-driven>

    <bean class="com.alibaba.fastjson.support.spring.FastJsonpResponseBodyAdvice">
        <constructor-arg>
            <list>
                <value>callback</value>
                <value>jsonp</value>
            </list>
        </constructor-arg>
    </bean>
		
