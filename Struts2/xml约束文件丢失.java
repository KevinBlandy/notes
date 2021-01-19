
	在struts2.xml中,或许你的IDE不会给你提示！因为你没网!不会自动的下载DID！
这时候就需要我们手动的来进行配置,已经提供好的DTD约束文件!

根据下列步骤进行操作
1,把xml头部的dtd文件地址copy下来
	http://struts.apache.org/dtds/struts-2.3.dtd
	* 这个根据版本不同,后面会有不同
2,在struts2的核心jar包中找到这个文件
	struts-2.3.15.1-all\struts-2.3.15.1\src\core\src\main\resources\struts-3.3.dtd

3,在MyEclipse上进行如下操作
	* window -- >   preferences
	* 在顶部搜索xml
	* 选择 -- > XML Catalog
	* 点击Add
	* location:填写第2步找到的dtd文件的路径!注意是带盘符的本地绝对路径
	* key type:选择,URI
	* URI就写上第1步copy的路径
	* 大功告成... ...


struts2还好,写多了!这些都记得了!Hibernate,Spring才要命.所以掌握这个技能非常的重要!
