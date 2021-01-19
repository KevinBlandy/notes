————————————————————————————————
1,再谈struts2	OGNL入门		|
————————————————————————————————
	struts2提供了一个标签,可以清楚的看到值桟区的快照
		<%@ taglib prefix="s" uri="/struts-tags"%>
		<s:debug/>
	* 通过Html页面的形式,把值桟中的数据,显示出来!
	Value Stack Contents
	* 灰常牛逼的手绘值桟结构图
		-----------------------------------------------------------------------------------------------
		Object								Property Name	Property Value								
		-----------------------------------------------------------------------------------------------
											container		There is no read method for container
											errorMessages	[]
											actionErrors	[]
		com.kevin.action.ValueStackAction	actionMessages	[]
											fieldErrors		{}
											texts			null
											valueStack		null
											locale			zh_CN
											errors			{}
		-----------------------------------------------------------------------------------------------
		com.opensymphony.xwork2.DefaultTextProvider	texts	null										
		-----------------------------------------------------------------------------------------------
		* 可以看出,对象桟的属性.来自于Action中的每一个get方法.首字母小写!
	