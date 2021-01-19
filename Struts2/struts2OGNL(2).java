<%@ taglib prefix="s" uri="/struts-tags" %>
OGNL
	> Object Graph Navigation Language
	> 对象图,导航语言
1,直接访问栈中的Action属性
	<s:property value="userName"/>
	* value,就是属性名称,并且这个属性必须要有getXxx();方法
	!!!!注意啊,大兄弟！OGNL表达式指的是value里面的内容,不是这个标签！也就是说这个笔记都是围绕着这个value里面的名堂来说的
2,访问Action中对象的属性
	<s:property value="user.username"/>
	* 一看就明白,就是这个Action中use对象,这就是输出了user的username属性
	* 同样,Action要提供user的get/set.而且user里面也要提供username的get/set
	* 一法通,万法通!如果你的Action中的对象,又包含了另一个对象,另一个对象又包含了另一个对象,你可以一只xxx.xxx.xxx...这下下去取到值
3,调用值栈中普通对象的普通方法
	<s:property value="userName.length()"/>
	* 这只是一个普通的演示,输出的是Action中这个userName属性的长度
5,访问值栈中Action的普通方法
	<s:property value="execute()"/>
	* 这个你懂的,就是访问了Action中的execute方法,输出的就是它的字符串返回值
6,访问Action中的静态方法
	<s:property value="@带包全路径@静态方法名()"/>
	* 记住xml中的配置<constant name="struts.ognl.allowStaticMethodAccess" value="true"/>
	* 如果该配置不存在,或者值为false,那么是获取不到静态方法的返回值。
7,访问Action中的静态成员
	<s:property value="@带包全路径@静态成员名"/>
	* 静态成员的获取是不用去xml配置常量的,任何时候都支持
8,Math类特殊支持
	 <s:property value="@@max(1,2)"/>
	 * 直接俩艾特符号,加上方法名！
	 * 注意,只能使用Math里面的方法,这个只能使用在Math这个类上
9,访问普通类的构造方法
	<s:property value="new 类名全路径('构造参数')"/>
	* 慎用,因为我用挂了,还没查出来原因.最好别用


	************************************ 访问集合框架 ************************************
---访问List
1,访问List
	<s:property value="Action的获取List的属性名"/>
	* 直接获取的就是list
2,访问List中某个元素
	<s:property value="属性名[下标]"/>
	* 这个跟上面的差不多,只是多了个下标,就是为了确定要取哪一个,后面还可以跟上这个对象的属性.
3,访问List中某个属性的集合
	<s:property value="list属性名.{元素属性名}"/>
	* 这东西就是把List集合里面的所有元素的指定属性的值,都给输出来。例如:这个集合里面所有人的名字
4,访问List中某个属性的集合中的特定值
	<s:property value="list属性名.{元素属性名}[角标]"/>
	* 这个跟上面那个更深一点,他要的是指定集合的中的指定角标的那个。例如:这个集合里面所有人的名字.角标为2的那个
---访问Set
1,访问Set
	<s:property value="Action的获取Set的属性名"/>
	* 获取指定名称的Set]
2,访问Set中的某个元素
	* 别想了,Set是没角标的,别指望拿角标来获取
---访问Map
1,访问Map
	<s:property value="Action的获取Map的属性名"/>
	* 直接就是获取Map
2,访问Map中某个元素
	<s:property value="Map名.指定Key值"/>
	<s:property value="Map名['指定的key值']"/>	这是另一种写法！注意领会就好
	* 根据指定的key值反回其对应的value值
3,访问Map中的所有Key
	<s:property value="获取Map的属性名".keys"/>
	* 得到的是是该Map中的所有Key值
4,访问Map中的所有value
	<s:property value="获取Map的属性名".values">
	* 得到的是Map中的所有value值
5,访问容器的大小
	<s:property value="容器.size()"/>
	* 万能的方法,只要有size();这个方法的对象调用都是可以被执行的!

************************************ 投影(过滤) ************************************
<s:property value="list.{?#this.age==1}.{age}"/>
	> 获取的是这个list集合中,age属性等于1的元素的age属性的值的集合
<s:property value="list.{^#this.age > 1}.{age}"/>
	> age大于1的集合里面,的第一个
<s:property value="list.{$#this.age > 1}.{age}"/>
	> 跟上面一个,结尾的那个
<s:property value="list.{$#this.age > 1}.{age} == null">
	> 判断一下,age大于1的这个集合是不是空的

************************************ 中括号访问 ************************************
<s:property value="[]"/>
	> 。。
<s:property value="[0]"/>
	> 。。