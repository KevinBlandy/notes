///////////////////
简单小结

['开发与映射']
	1,Controller注解开发,无所谓继承不继承指定接口或者类,也无所谓返回的是 ModelAndView还是 String.视图解析器都会对其进行解析
	2,Controller非注解开发,那么就必须去注册'处理器适配器',并且以适配器的规则去做开发
		* xml, 开发Handler,只能使用两种方式开发.覆写的方法就是处理方法!
		* 注解,开发Hnadler,可以随意.因为方法上的 @RequestMapping 指定了处理方法!
		* spring4不用手动的注册'处理器映射器','处理器适配器'.不论是xml还是注解,都不用手动的注册
		* 注解开发使用:<context:component-scan>,扫描器就已经注册了注解驱动:<context:annotation-config/>
	3,多个URL映射相同会报错,废话呢不是!
	4,xml与注解扫描器都是往同一个容器中注册Bean...不管怎么注册.只要在容器就可以使用和操作!
	5,'Controller',默认是单例的.要手动的设置为非单例!scope="prototype"
	6,所有的'处理器映射器'都实现了 HandlerMapping 接口
	7,所有的'处理器适配器'都实现了 HandlerAdapter 接口
	8,在开发的时候,就算不在xml中配置'视图解析器','处理器映射器','处理器适配器'等组建.Controller还是能正常的运行
		* 那是因为org.springframework.web.servlet.DispatcherServlet.properties这个文件
		* 这个文件中定义上述的几个组建,如果没有手动的指定,那么就会默认的加载这个文件里面指定的数据
		* 但是有些是已经过期的组建,为了避免这个情况.所以,我们有时候有必要去手动的配置
	9,对于注解的Handler,可以单个在xml文件中进行注册配置,不需要配置id.因为url映射是通过注解完成的,注册进IOC容器就好!
	10,xml开发有两个处理器适配器,有两个处理器映射器.注解开发,只有一个处理器适配器和处理器映射器
['请求参数']
	1,springmvc不具备引用类型的数据自动转换-->Date .需要自己处理
	2,springmvc具备基本数据类型的转换-->String -- Integer 但是如果转换异常,就会404
	3,还可以定义数组,如果表单数据一个字段有多个值.可以定义一个数组.只要name相同!框架也能自动的进行封装
		* 如果表单的name字段,有多个值.那么方法形参中可以定义String[] name.可以正常封装数据.如果定义String name.也就是非数组.那么框架就会把表单传递的值,通过','逗号分隔,合成一个字符串,赋值!
	4,如果是注解开发,这个就比较叼了.直接传递POJO,基本数据类型,字符串都是可以的!只要表单中有对应的值,都会进行封装!
		* 就算是参数列表中有多个POJO,只要这些POJO都有表单对应的字段.也是能成功赋值的!
	5,如果是xml开发,那就要靠自己进行手动的封装数据
	6,如果是 Boolean 形的数据,框架只能识别: true / false /0 / 1 这几个字符串!用于表示布尔型变量
	7,注意		-- 这东西有问题,测试几次居然结果不一样.我操!!先别信.
		* 形参中只有基础的变量.唉,也就是特么的:Integer,String,Double,Float 等数据
		  > 随意,有就赋值.没有就算了
		* 形参中没有基本数据类型,只有POJO
		  > 提交数据中,随便写.不会有异常.如果有符合POJO条件的,就自动的进行赋值,如果没就算了,没异常
		  > 只有POJO,如果传递进来的表单字段名称,是POJO具备的,但是.类型转换异常(Integer=qwdqw),那么就404
		* 形参中既有POJO也有基本数据类型
		  > 那么提交的表单中的数据:'POJO中的,跟形参列表相同的属性',必须提交!不然就会抛出异常!
	8,请求参数,你定义啥,框架就给你绑定啥.
	9,如果请求参数,与表单页面的字段不一样,可以使用一个注解,标识在形参上.来完成绑定:@RequestParam(value="表单字段名");
	10,如果涉及到其他的数据格式,有时候我们也需要涉及到自定义参数绑定.

['数据传递']
	1,在处理方法的参数列表中定义一个 Map: Map<String,Object>,这个 Map,是不会接收请求参数的.它是 request,你就可以往里面存入值.
		* 其实这个要从 ModelAndView开始说. ModelAndView构造函数有个map,当处理方法参数列表中有 Map<String,Object>,那么其实默认的,框架就会把它设置为ModelAndView里面的 Map!懂了没 ModelAndView
		* ModelAndView mv = new ModelAndView("key",new HashMap());
	2,在处理方法的参数列表中定义一个 Model
		* 其实这个Model,就是ModelAndView里面的Model,本质上就是一个 Map 集合,你往这里面添加的数据,都会在request域中
	3,直接简单粗暴,处理方法的形参中添加request对象,剩下的不用教了
	4,以上的种种都是因为,Controller的返回值是  String ,不是ModelAndView,而采取的方法!

['重定向与转发']
	1,Controller返回类型为String,可以返回:"redirect:URL";
		* 不同Controller之间重定向,要加上"/",以及Controler的 @RequestMapping
		*  redirect:demo.action			//重定向到本Controller中的demo.action
		*  redirect:/demo/demo.action	//重定向demo下的demo.action
		*  直接写URL,默认为转发
	2,Controller返回类型为ModelAndView
		* 默认是转发
	3,其实无非就是三种返回类型:void String ModelAndView
	4,如果使用response给客户端进行相应,那么不能有返回值.才会生效.也就是说视图解析>response直接操作

['文件上传']
	1,使用文件上传,就不多赘述表单条件了
	//2,处理文件上传的Contoller中,表单数据不会再被封装到方法的POJO,但是POJO还是会创建,也不会封装到基本数据类型的形参中

['数据校验']

['数据转换']
	1,本身框架就有一大堆的数据转换器,其实根本不需要自定义.如果你想,也是可以的!
	2,有很多的接口,可以去实现.也就有了不同的转换器.他们相同的都是,都要注册在一个工厂里面:org.springframework.context.support.ConversionServiceFactoryBean
	3,一般都是实现这个接口: Converter<String,输出类型>
		* 覆写的方法会返回一个'输出类型'的对象,要跟Handler的处理方法的形参类型相匹配.
	4,写完了,就注册进容器.
	5,数据的格式化,就是在指定的字段上,例如:User类,的birthday,标识一个注解.指定一个字符格式.那么只要提交了正确格式的字符串就OK

['拦截器']

['国际化']

['异常处理']

['视图解析']
	

['JSON解析']

['实际开发经验']
	1,框架整合的时候,springmvc+hibernate or mybatis
	2,可以把spring的配置文件分为多个,便于管理
	3,启动的时候,注册框架提供的监听器
		* 给监听器提供初始化配置文件的,时候使用通配符.这样的话,可以就加载多个IOC配置文件.当然,这些文件一定要符合命名规范




	DispatcherServlet 
		* 接收request,相应response
	HandlerMapping
		* 查找Handler,可以是注解,可以是以bean的形式注册在xml中
	HandlerAdapter
		* 根据特定的规则,去执行Handler.其实也就是开发Handler的时候要按照适配器的要求.实现指定的接口！
	Handler
		* 这东西需要开发者去编写.开发方式有三种.xml有两种,注解有一种!
		* Handler 处理器执行后,结果是ModelAndView.不管Handler返回的是 String 还是什么,都会被'处理器适配器',封装为ModelAndView
		* Handler 开发的时候,返回值类型可以是:ModelAndView,String,void
		* void:这种返回值类型,可以通过response来指定相应结果类型.