---------------------
工厂模式			 |
---------------------
	# 实现了创建者和调用者的分离
	# 分类
		简单工厂
		工厂方法
		抽象工厂

---------------------
工厂模式-简单		 |
---------------------
	/**
	 * 定义接口
	 */
	public interface Car {
		void run();
	}
	/**
	 * 定义实现
	 */
	public class Audi implements Car{
		public void run() {
			System.out.println("audi is run");
		}
	}
	/**
	 * 定义实现
	 */
	public class Baoma implements Car{

		public void run() {
			System.out.println("baoma run");
		}
	}
	/**
	 * 定义工厂
	 */
	public class Factory1 {
		public static Car getCar(String name) {
			if(name.equals("audi")) {
				return new Audi();
			}else {
				return new Baoma();
			}
		}
	}
	* 太简单的东西,没大必要演示
	* 也称为成为静态工厂

---------------------
工厂模式-工厂方法	 |
---------------------
	* 工厂也定义接口,不同的工厂产生不同接口的实现
	


---------------------
工厂模式-抽象工厂	 |
---------------------
	* 把所有的组件都抽象,工厂化