-----------------------
Enum					|
-----------------------
	# 枚举,JAVA里面的特殊对象
	# enum  --通过该关键字来创建枚举类。，替换掉 class 
	|--JDK1.5版本后新增的关键字。用于定义枚举通过公共方法来调用属性
	# 方法
		 int ordinal();
			* 返回当前对象在枚举类中的位置,从 0 开始
		 String name() ;
			* 当前对象在枚举中的名称
	# 静态
		<T extends Enum<T>> T  valueOf(Class<T> enumType, String name) ;
			* 获取指定 Class 枚举类型的,指定名称的实例对象
		values();
			* 返回的是一个该类的数组集合，里面包含了该类的所有枚举对象。

	此关键字用于表示一个类。跟 class 一样！被它标识的类，就是一个枚举类。
	1,必须在此类的第一项就建立对象，格式：对象1(参数1，参数2),对象2(参数1，参数2),对象3(参数1，参数2);

	类名.values();
		|--返回的是一个该类的数组集合，里面包含了该类的所有枚举对象。

	类名.valueOf(String);
		|--返回的是指定名称的对象.String 用来指定对象的名字。只能写已经存在对象名，乱写，会报错。

	---注意事项
	定义枚举类用关键字 enum
	所有的枚举类都是 Enum 的子类
	枚举类的第一行必须是枚举项，最后一个枚举项后的分好可以省略，但是如果枚举类有其他的东西，这个分号就不能省略，建议不要省略
	枚举类可以有构造器。但必须是 private 的。它默认的也是 private 的。枚举项的用法比较特殊:
	枚举("");
	枚举也可以有构造方法。但是枚举项必须重写该方法。
	-------
	当枚举类中出现抽象方法的时候。需要在每个实例上都实现抽象方法
	例：
	enum Test{
		BLUE{
			public void show(){
				System.out.println("blue");
			}
		},
		RED{
			public void show(){
				System.out.println("red");
			}
		},
		YELLOW{
			public void show(){
				System.out.println("yellow");
			}
		},
		PINK{
			public void show(){
				System.out.println("pink");
			}
		};
		abstract void show();
	}