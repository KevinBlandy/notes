package com.kevin.demo;
import java.util.ArrayList;
import java.util.List;
import com.thoughtworks.xstream.XStream;
/**
 * XStream演示
 * */
public class Demo{
	public static void main(String[] args){
		//alias();
		test3();
	}
	/**
	 * 基础的解析方式
	 * */
	public static  void demo(){
		List<Province> proList = getProvinceList();
		//创建XStream对象,调用toxml把集合转换成xml字符串
		XStream xstream = new XStream();
		//解析文件
		String s = xstream.toXML(proList);
		System.out.println(s);
	}
	/**
	 * 希望
	 *  默认的List类型对应<List>元素,希望让List类型对应China元素
	 *  默认Province类型对应<com.kevin.Province>,希望它对应Province元素
	 *  默认City类型对应<con.kevin.City>,希望它对应City元素
	 * */
	public static void alias(){
		List<Province> proList = getProvinceList();
		//创建XStream对象,调用toxml把集合转换成xml字符串
		XStream xstream = new XStream();
		/**
		 * 给指定的类型,指定别名
		 * */
		xstream.alias("china",List.class);//给List类型指定别名为china
		xstream.alias("province", Province.class);//给province指定别名为province
		xstream.alias("city",City.class);//给City类型指定别名
		String s = xstream.toXML(proList);
		System.out.println(s);
	}
	/**
	 * 默认javaBean的属性,都会生成子元素,而先希望某些javaBean属性不生成子元素
	 * 生成,属性
	 * */
	public static void test(){
		List<Province> proList = getProvinceList();
		//创建XStream对象,调用toxml把集合转换成xml字符串
		XStream xstream = new XStream();
		xstream.alias("china",List.class);//给List类型指定别名为china
		xstream.alias("province", Province.class);//给province指定别名为province
		xstream.alias("city",City.class);//给City类型指定别名
		/**
		 * 把指定类中的指定类成员,不生成子标签,而是成为它的属性
		 * */
		xstream.useAttributeFor(Province.class,"name");//把这个类的这个属性,应用为它的xml属性,而不是默认的子元素
		String s = xstream.toXML(proList);
		System.out.println(s);
	}
	/**
	 * 去除javaBean类里面的List成员在xml中出现的信息
	 * */
	public static void test2(){
		List<Province> proList = getProvinceList();
		//创建XStream对象,调用toxml把集合转换成xml字符串
		XStream xstream = new XStream();
		xstream.alias("china",List.class);//给List类型指定别名为china
		xstream.alias("province", Province.class);//给province指定别名为province
		xstream.alias("city",City.class);//给City类型指定别名
		xstream.useAttributeFor(Province.class,"name");//把这个类的这个属性,应用为它的xml属性,而不是默认的子元素
		/**
		 * 去除某些不需要的标签.例如存在一个类里面的成员变量就是一个集合,那么这个集合类的信息我们是不需要显示在xml文件中的
		 * 但是它里面的数据保留
		 * */
		xstream.addImplicitCollection(Province.class, "citys");//去除指定类中的,名为citys的list属性
		String s = xstream.toXML(proList);
		System.out.println(s);
	}
	/**
	 * 去除某些不想要的javaBean属性
	 * */
	public static void test3()
	{
		List<Province> proList = getProvinceList();
		XStream xstream = new XStream();
		xstream.alias("china",List.class);//给List类型指定别名为china
		xstream.alias("province", Province.class);//给province指定别名为province
		xstream.alias("city",City.class);//给City类型指定别名
		xstream.useAttributeFor(Province.class,"name");//把这个类的这个属性,应用为它的xml属性,而不是默认的子元素
		xstream.addImplicitCollection(Province.class, "citys");//去除指定类中的,名为citys的list属性
		/**
		 * 去除一些javaBean的属性,不让它生成元素
		 * */
		xstream.omitField(City.class,"description");//去除指定类的指定成员
		String s = xstream.toXML(proList);
		System.out.println(s);
	}
	//返回javaBean集合
	public static List<Province> getProvinceList(){
		//北京
		Province p1 = new Province();
		p1.setName("北京");
		p1.addCity(new City("东城区","DongChengQu"));
		p1.addCity(new City("西三旗","XiSanQi"));
		//重庆
		Province p2 = new Province();
		p2.setName("重庆");
		p2.addCity(new City("南岸区","NanAn"));
		p2.addCity(new City("江北区","JiangBei"));
		List<Province> provinceList = new ArrayList<Province>();
		provinceList.add(p1);
		provinceList.add(p2);
		return provinceList;
	}
}

