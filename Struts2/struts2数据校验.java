————————————————————————————————
一,struts2数据校验的问题		|
————————————————————————————————
	> 在实际开发中请求参数,是需要校验的!客户端的校验需要JavaScript完成!
	  但是服务端也要进行校验!
	> struts2提供校验(服务器校验)
	  * 手动校验
	  * 配置校验(Annotation,xml),这里只讲xml.注解不讲!

————————————————————————————————
二,手动校验						|
————————————————————————————————
	> 也叫编码校验
	> 要求
	1,Actin类必须继承自'ActionSupport' 
		* struts2中实现校验功能!其实是要实现一个叫做'Validateable'的接口!
		* 只是说这个'ActionSupport已经帮我们实现了这个接口'!那你要乐意,你不继承它,直接实现接口也可以(比较麻烦)

	2,重写一个法:public void validate()
		* 该方法,会在请求处理方法之前执行,也就是在里面执行校验!由我们自己编写逻辑代码
		* 如果认为数据错误,那么就:this.addFieldError("错误名称","错误提示");
		* 当存在一个或者多个错误信息之后,会自动跳转到,name为input的result页面!
		['原理']
			又要追溯到struts-default.xml文件中,一个名为:paramsPrepareParamsStack的拦截器桟
			 <interceptor-ref name="params">			['封装请求参数的拦截器']
                 <param name="excludeParams">dojo\..*,^struts\..*,^session\..*,^request\..*,^application\..*,^servlet(Request|Response)\..*,parameters\...*</param>
             </interceptor-ref>
             <interceptor-ref name="conversionError"/>
             <interceptor-ref name="validation">		['这个拦截器会在访问Action之前调用valiDate方法,可以在validate方法中对请求参数进行处理']
                 <param name="excludeMethods">input,back,cancel,browse</param>
             </interceptor-ref>
             <interceptor-ref name="workflow">			['这个个哥们儿就负责判断,如果你往action里面丢了数据异常,那它就会默认转发到input视图']
                 <param name="excludeMethods">input,back,cancel,browse</param>
             </interceptor-ref>
			* params:负责封装请求参数的,到这里的时候,Action中已经有参数了
			* conversionError:这个拦截器会在请求处理方法之前调用valiDate方法!校验数据
				* 数据正确:无视,不管
				* 数据错误:this.addFieldError("错误名称","错误提示");
			* excludeMethods:这个拦截器就是判断,上面的哥们啊!你查出来问题没啊?
			  其实就是去看看FieldError,里面有没有拦截器朝里面丢错误信息!有我就闹腾直接找input的result!没有,我也不管!让Action自己处理
			* 在input视图页面使用标签,可以取出这个错误信息(记得导包)
				 <st:fielderror/><!-- 展示所有错误信息 -->
				 <st:fielderror fieldName="错误名称"/><!-- 展示指明名称的错误信息 -->
			* 这个显示是带有视图的,可以通过常量的配置去掉视图,或者是直接通过'OGNL'表达式来取出值
				* 例如:${filedErrors.age[0]}

	3,在Action如果有其他不需要校验!的请求处理方法存在!
		解决方案:
		定义校验方法的时候,方法命名为:
		validateXxxx(){}
		* 那么这个校验方法就只对xxx这个方法有效果,注意被校验的方法,首字母大写!
		例如:
		public String regist()
		public void validateRegist()
		{
			那么校验就只对regist这个方法有效!
		}
		['注意']
		当这种指定校验某个方法的校验器和校验全部的校验器(validate())同时存在时.
		那么validate()会在最后执行!也就是说先会执行请求方法对应的的校验器!然后再执行一次validate();
		* 说白了就是,validate()就是爹!不管谁来.都要在最后校验一次!而上面这种指定名称的,只会校验指定方法!而且是在validate()之前校验!
		* 所以,我们可以把一些共同的数据,放置在validate()里面进行校验!而每个请求特有的数据,放在自己对应的校验方法里进行校验!
		

————————————————————————————————
三,配置校验	(企业的主流校验)	|
————————————————————————————————	
	> 代码校验不适合大型项目,数据流量复杂时候,开发和维护量都会很大
	> struts2,的校验框架!它已经写好了很多的校验方法,用的时候直接调用方法就OK!
	> 通过配置文件,来调用struts2的校验框架!
	要求:
	1,Action类必须继承ActionSupport
	2,在Action类的目录下创建一个xml文件
	
配置文件详解
	'位置':在需要被校验的Action同一个包下
	'名称':Action类名-validation.xml
	'约束':xwork-core-x.x.x.jar中的xwork-validator-x.x.x.dtd.复制如下,粘到配置文件内
			<!DOCTYPE validators PUBLIC
  				"-//Apache Struts//XWork Validator 1.0.3//EN"
  					"http://struts.apache.org/dtds/xwork-validator-1.0.3.dtd">
	'书写':
			<validators>										//根元素
				<field name="被校验字段名">						//被校验字段
					<field-validator type="指定校验器">			//指定校验器
						<message>用户名不能为空</message>		//如果校验失败,提示的错误信息.可以通过标签取出:<st:fielderror fieldName="字段名.message"/>
					</field-validator> 
				</field>
			</validators>
	['案例']
	<field name="name">//name字段
 		<field-validator type="stringlength">		//字符长度校验器
 			<param name="maxLength">16</param>		//最大长度
 			<param name="minLength">6</param>		//最小长度
			<message key="name"/>					//错误信息的key(JSP页面中可以通过key取出错误信息)
 			<message>6位以上,16位一下</message>		//错误信息
 		</field-validator>
		<field-validator type="requiredstring">		//必填数据校验器
			<param name="trim">false</param>		//不对字符串进行trim()操作
			<message>用户名不能为空</message>		//错误信息	
		</field-validator> 
 	</field>

	<field name="birth">//birth字段
		<field-validator type="date">				//日期校验器
 			<param name="max">2012-12-12</param>	//最大日期
 			<param name="min">2011-11-11</param>	//最小日期
			<message key="date"/>					//错误信息的key(JSP页面中可以通过key取出错误信息)
 			<message>只能在指定日期内</message>		//错误信息
 		</field-validator>
	</field>

	<field name="phone">//phon字段
		<field-validator type="regex">				//正则表达式校验器
 			<param name="regexExpression">
				<![CDATA[正则表达式...]]>			//正则表达式,尽量的使用CDATA,因为正则会有一些特殊符号.不能被xml解析!必须原样输出
			</param>		
			<message key="phone"/>					//错误信息的key(JSP页面中可以通过key取出错误信息)
 			<message>电话号码格式不对</message>		//错误信息
 		</field-validator>
	</field>

只校验指定的方法:
	> 其实就是说,一个Action里面包含了多个请求处理方法!例如:登录跟注册
	> 登录跟注册的校验肯定是不一样的!所以,配置校验,就需要考虑这一点! 
	> 解决办法很简单'修改配置文件的名称'! 
	* Action类名-校验方法路径名-validation.xml
		> 该配置就只对指定的方法进行校验
		> 例: UserAction-xxx_login-validation.xml
		
	* Action类名-validation.xml
		> 该配置文件会对整个类中的请求处理方法都进行校验
		> 例: UserAction-validation.xml
	
	* 如果全类校验和单个方法校验都存在,那么可以参考上面'手动编码校验方式'的同样情况!

短路校验:
	就是当前验证挂了,那么后面的就不再进行验证了
	<validator../>元素和<field-validator.../>可以指定一个可选的
	short-circuit属性,该属性指定'该验证器是否是短路验证器,默认值为false'

	* 对同一个字段内的多个,如果一个短路验证器验证挂了.那么其他的就都不验证了

注意:
	> 其实,系统提交的校验器就已经足够实际的项目开发所用!'而且,很多的校验器都带有参数,可以在校验器类的源码中找到'
	> 多个校验器可以同时存在于一个字段下,也就是说'对一个字段同时可以进行多种约束'
	> 上面的案例只是简单的进行了一下演示,具体的,'用到的时候需要到自己去找'
	> 在进行数据校验的时候,这些数据都已经被赋值给Model!
	> 其实<field>还有一个子元素,也就是'还有一个校验类型',叫做field-validator直接给案例吧
		<field name="repassword">
			<field-validator type="fieldexpression">
				<pram name="expression">
					<!CDATA[[(password==repassword)]]>
				</param>
				<message>两次输入的密码不一致</message>
			</field-validator>
		</field>
	> 在配置文件中也可以使用OGNL表达式
	<field-validator type="stringlength">			//字符长度校验器
 			<param name="maxLength">16</param>		//最大长度
 			<param name="minLength">6</param>		//最小长度
			<message key="name"/>					//错误信息的key(JSP页面中可以通过key取出错误信息)
 			<message>${minLength},${maxLength}位一下</message>		
			//${},能自动读取,上面的属性,没多大意义
 	</field-validator>


多个校验器视图冲突
	* 使用通配符Action可能会出现,多个系统拦截器并发的使用同一个resut--->input视图
	* 例如:登录和上传文件都在同一个Action方法内!而这俩方法都有校验器!xml,出问题默认跳转的都是input视图.
	* 配置文件只允许一个resutl视图存在!不可能让两种不同的业务方式都朝一个地儿蹦吧？
		['解决方案']
		在所有含有input视图的方法上添加不同值的注解
		@InputConfig(resultName="自定义视图名称")
		> 这个注解就会改变方法中那些'自动跳转input视图的拦截器'!它们就会默认跳转到你指定的视图!

————————————————————————————————
四,系统提供的校验器				|
————————————————————————————————
	> type就是指定校验器,目前能指定的校验器都是由系统提供的
	> 在xwork核心包下!
	com.opensymphony.xwork2.validator.validators.default.xml文件中,就可以找到这些系统提供的校验器
<validators>
    <validator name="required" class="com.opensymphony.xwork2.validator.validators.RequiredFieldValidator"/>
		/*必填校验器*/:要求字段值不能为null
    <validator name="requiredstring" class="com.opensymphony.xwork2.validator.validators.RequiredStringValidator"/>
		/*必填校验器*/:要求字段值不能为null,且长度大于0,默认情况下会对字符串做trim()去两端空格操作
    <validator name="int" class="com.opensymphony.xwork2.validator.validators.IntRangeFieldValidator"/>
		/*整数校验器*/:要求字段值必须在指定的范围内,min指定最小值,max指定最大值!
    <validator name="long" class="com.opensymphony.xwork2.validator.validators.LongRangeFieldValidator"/>
    <validator name="short" class="com.opensymphony.xwork2.validator.validators.ShortRangeFieldValidator"/>
    <validator name="double" class="com.opensymphony.xwork2.validator.validators.DoubleRangeFieldValidator"/>
		/*双精度浮点校验器*/:要求字段值必须在指定的范围内,min指定最小值,max指定最大值!
    <validator name="date" class="com.opensymphony.xwork2.validator.validators.DateRangeFieldValidator"/>
		/*日期校验器*/:日期校验要求,要求字段的日期值,必须在指定范围内,min指定最小值,max指定最大值!注意,它不校验格式!它拿到数据的时候已经是一个Date对象了!
    <validator name="expression" class="com.opensymphony.xwork2.validator.validators.ExpressionValidator"/>
    <validator name="fieldexpression" class="com.opensymphony.xwork2.validator.validators.FieldExpressionValidator"/>
		/*OGNL表达式校验器*/要求字段满足一个OGNL表达式,expression参数指定OGNL表达式!
    <validator name="email" class="com.opensymphony.xwork2.validator.validators.EmailValidator"/>
		/*email*/:邮件地址校验器,要求如果字段值非空,则必须是合法的邮件地址
    <validator name="url" class="com.opensymphony.xwork2.validator.validators.URLValidator"/>
		/*url校验器*/:网址校验,如果字段值非空.则必须是合法的url地址
    <validator name="visitor" class="com.opensymphony.xwork2.validator.validators.VisitorFieldValidator"/>
    <validator name="conversion" class="com.opensymphony.xwork2.validator.validators.ConversionErrorFieldValidator"/>
    <validator name="stringlength" class="com.opensymphony.xwork2.validator.validators.StringLengthFieldValidator"/>
		/*字符串长度校验器*/:要求被校验的属性值长度必须在指定的范围内.minLength:指定最小长度,maxLength:指定最大长度
    <validator name="regex" class="com.opensymphony.xwork2.validator.validators.RegexFieldValidator"/>
		/*正则校验器*/:要求该字段,必须符合指定的正则表达式,expression参数,指定正则表达式,caseSentitive参数
    <validator name="conditionalvisitor" class="com.opensymphony.xwork2.validator.validators.ConditionalVisitorFieldValidator"/>
</validators>

————————————————————————————————
五,自定义校验器					|
————————————————————————————————
步骤
	1,自定义校验器必须实现'Validator'接口 
		> 通常自定义校验器继承:'ValidatorSupport'和'FieldValidatorSupport'
		* ValidatorSupport 针对的不是一个输入的字段(两个密码一致)
		* FieldValidatorSupport 针对的是一个输入字段(用户名非空)
	2,注册校验器
		> 在项目的src目录下新建'validators.xml'文件
		> 引入xwork-core-x.x.x.jar中xwork-validator-config-1.0.dtd
	3,使用校验器
		> 在Action所有包创建'Action类名-validation.xml'
['鸡肋']
	> 实际开发中,很少用到这个东西!
