作用
	这东西可以把javaBean转换(序列化)为xml!


XStream 
	是由第三方提供的jar包
XStream工具
核心:xstream-1.4.7.jar
依赖:xpp3_min-1.1.4c.jar(XML Pull Parser --一个速度较快的xml解析器,必须依赖这个)

使用方法

1	XStream xstream = new XStream();
2	String xmlString = xstream.toXML(javaBean);

解析出来的结果.会把类的全路径作为跟元素,然后类成员作为子元素,如果类成员也是一个类(对象/引用型数据),那么就继续有子元素！



	第一个就是创建对象,第二个就是解析javaBean,返回来的字符串就是一个xml格式的字符串！
	但是里面有很多标签,是跟我我们存放类型来建立的.例如,类的全路径！而我们不需要！
	我们需要把这个字符串里面的某些东西进行转换
----一些方法
xstream.alias("china",List.class);		// 替换根元素
	因为解析的对象是个List类型,所以List的全路径会被当作根元素存在,这个方法的目的是
	把跟标签,也就是List.class替换成china,那么解析出来的跟标签就是china了
xstream.alias("province", Province.class);//给province指定别名为province
	同理,这个东西解析出来会有Province这个类的全路径元素的存在,
	我们这Province字符串替代了它
xstream.useAttributeFor(Province.class,"name");		//把指定类成员作为xml标签属性出现.而不是子标签
	因为这个解析是把一个类的类成员变量,也就是属性当作这个类的子标签存在,而某些时候
	我们希望类的成员作为这个标签的属性存在,而不是子标签.可以通过这个方法
	指定某个类的,某个成员,在xml文件中作为属性存在,而不是子标签
xstream.addImplicitCollection(Province.class, "citys");		//对类的,引用型变量进行处理.
	去除指定类中的,名为citys的list属性,因为有些类中,他的成员变量就是一个List,而我们不希望这个List的信息出现在xml中 
	我们只需要里面的元素就好.我们就可以通过这种方式来进行
xstream.omitField(City.class,"description");			//删除指定的类成员
	去除指定类的指定成员,javaBean中,有些类成员我们不想它出现,就可以用这个方法去掉
------------演示
默认
<com.kevin.demo.Province>
    <name>重庆</name>
    <citys>
      <com.kevin.demo.City>
        <name>南岸区</name>
        <description>NanAn</description>
      </com.kevin.demo.City>
      <com.kevin.demo.City>
        <name>江北区</name>
        <description>JiangBei</description>
      </com.kevin.demo.City>
    </citys>
  </com.kevin.demo.Province>

通过替换(指定别名)后
<province>
    <name>重庆</name>
    <citys>
      <city>
        <name>南岸区</name>
        <description>NanAn</description>
      </city>
      <city>
        <name>江北区</name>
        <description>JiangBei</description>
      </city>
    </citys>
  </province>

 通过指定属性后
   <province name="重庆">
    <citys>
      <city>
        <name>南岸区</name>
        <description>NanAn</description>
      </city>
      <city>
        <name>江北区</name>
        <description>JiangBei</description>
      </city>
    </citys>
  </province>

 去除指定类[引用型成员]后的结果
 [只是不让这个引用类型出现在xml中,但是它的数据我们还是要的....]
   </province>
  <province name="重庆">
    <city>
      <name>南岸区</name>
      <description>NanAn</description>
    </city>
    <city>
      <name>江北区</name>
      <description>JiangBei</description>
    </city>
  </province>

 去除了指定类成员后的效果
   <province name="北京">
    <city>
      <name>东城区</name>
    </city>
    <city>
      <name>西三旗</name>
    </city>
  </province>
  <province name="重庆">
    <city>
      <name>南岸区</name>
    </city>
    <city>
      <name>江北区</name>
    </city>
  </province>