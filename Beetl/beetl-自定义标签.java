-------------------------------
Beetl 自定义标签				|
-------------------------------
	# 标签形式有俩种
	
	# 一种是标签函数

	# 第二种是html tag
		* '实际上在语法解析的时候会转化成第一种'
			* 也就说,tag 其实也会被解析为 fun
		* 其,实现是HTMLTagSupportWrapper，此类将会寻找root/htmltag目录下同名的标签文件作为模板来执行。
		* 类似普通模板一样，在此就不详细说了

	# HTML_TAG_FLAG默认为#用来区别是否是beetl的html tag
	# 你也可以设置成其他符号

-------------------------------
Beetl 标签函数					|
-------------------------------
	# 标签函数类似jsp2.0的实现方式，需要继承Tag抽象类的render方法即可
		org.beetl.core.Tage

	# 在beetl中,非单例模式,如果与spring整合,记得把 tag实现设置为非单例

	# 覆写方法
		public class MyTag extends Tag{
			@Override
			public void render() {
				//标签名称(一般没啥卵用)
				String tagName = (String) this.args[0];
				//获取传递进来的属性map
				Map<String,Object> attrs = (Map<String, Object>) this.args[1];
				//从属性map中获取name属性值
				String value = (String) attrs.get("name");
				try {
					//响应给模版
					this.ctx.byteWriter.writeString("你好," + value);
				}catch (Exception e){
					throw new RuntimeException(e);
				}
			}
		}

	# tag类提供了如下属性和方法供使用
		args	传入标签的参数
		gt		GroupTemplate
		ctx		Context
		bw		当前的输出流
		bs		标签体对应的语法树，不熟悉勿动
	
	# 有如下方法
		protected void doBodyRender()
			* 渲染标签体

		protected BodyContent getBodyContent()
			* 此类将调用父类方法doBodyRender，渲染tag body体
			* 例子
				BodyContent  content = getBodyContent();
				String content = content.getBody();
				String zip = compress(conent);			//调用父类方法getBodyContent ，获得tag body后压缩输出
				ctx.byteWriter.write(zip);


	# 注册标签函数
		  grouptemplate.registerTag("mytag", MyTag.class);	
	
	# 使用标签函数
		 <#mytag name="哎呀呀"></#mytag>


-------------------------------
Beetl 绑定变量的HTML标签		|
-------------------------------
	# Beetl还 支持将标签实现类（java代码）里的对象作为临时变量，被标签体引用。
	# 此时需要实现 GeneralVarTagBinding (此类是Tag的子类） 
	# 该类提供另外几个方法 
		void binds(Object… array) 
			* 子类在render方法里调用此类以实现变量绑定，绑定顺序同在模板中申明的顺序 
		void bind(String name, Object value) 
			* 子类在render方法里调用此类以实现变量绑定，name是模板中申明的变量名,用此方法绑定不如binds更灵活，不推荐
		Object getAttributeValue (String name)
			* 获得标签的属性 
		Map getAttributes 
			* 获得标签的所有属性，
	
	# 写类
		public class TagSample extends GeneralVarTagBinding{
				@Override
				public void render(){
						int limit = Integer.parseInt((String) this.getAttributeValue("limit"));
						for (int i = 0; i < limit; i++){
								this.binds(i)			//绑定 变量 i 到标签的是一个变量上
								this.doBodyRender();	//render方法将循环渲染标签体limit次，且每次都将value赋值为i
						}
				}
		}
		//在某处注册一下标签TagSample
		//gt.registerTag("tag", TagSample.class);
	
	# 写标签
		<#tag limit="3";value>
			${value}
		</#tag>

		* 类似于常规html标签，需要在标签的最后的属性定义后面加上分号 ";" 此分号表示这个是一个需要在标签运行时需要绑定变量的标签。后跟上要绑定的变量列表，
		* 如上例只绑定了一个value变量，'如果需要绑定多个变量，则用逗号分开'，如var1,var2 上。
		* 果后面没有变量列表，只有分号，则'默认绑定到标签名同名的变量上'. 如果标签有 namesapce 则默认绑定订的变量名不包含 namespace

		* 注意，由于标签使用因为太长可能换行或者是文本格式化导致换行，目前beetl只允许在属性之间换行，否则，将报标签解析错误。

		* 默认情况下，如果标签属性出现了var(可以通过配置文件改成其他属性名)，也认为是绑定变量的标签，如上面的例子也可以这么写
			<#tag limit="3" var="value">
				${value}
			</#tag>

		* var属性的值可以是个以逗号分开的变量名列表，如var="total,customer,index"