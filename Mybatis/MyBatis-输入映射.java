————————————————————————
1,MyBatis-输入映射		|
————————————————————————
	* MyBatis的输入映射是通过 parameterType 或 parameterMap 属性来进行配置
	* 输入类型,可以是:简单类型,HashMap,基本数据类型,POJO


	1,POJO输入类型映射
		* 当实现复杂的查询功能,参数也就相对的复杂.也许,JavaBean中,并不具备这些属性.
		  那么,我们就有必要:针对查询,建立对应的JavaBean.封装查询条件
		  '直接可以把一些现有的JavaBean直接作为成员属性添加到查询Bean中.'
		  '也可以继承现有的JavaBean,再添加其他的查询属性,这种方式多数用于输出映射'
		  充分的使用面向对象的思想去解决问题.

		总结:
			* 综合查询为例,数据复杂.也许一个用户查询,包含了很多的查询条件.但是User对象确不具备这些条件,
			  那么我们就可以使用'QueryBean',把User作为成员字段,添加其他的查询条件,通过OGNL来获取属性

			* 综合查询的返回结果,也极有可能比较复杂.也有User对象不具备的字段存在.
			  那么我们就可以自定义一Bean,继承User.并且添加对应的返回属性.再做为resultType.进行映射
	
	
	2,HashMap 输入类型映射
		* <select id="dd" parameterType="java.util.HashMap" resultType="com.kevin.vo.User">
			SELECT * FROM User WHERE id=#{id}
		  </select>
		* 传递一个 HashMap 进来,那么 Map 的key,就是sql参数的key