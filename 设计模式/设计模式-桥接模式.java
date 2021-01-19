--------------------------------
桥接模式						|
--------------------------------
	* 多纬度的关系,有点像笛卡尔积的意思
		品牌	类型

		联想	台式机
		戴尔	笔记本
		苹果	平板
	
	* 扩展品牌或者扩展类型,都会多写两个类,一个实现品牌,一个实现类型

	* 使用类成员的方式代替继承关系
	


/**
 * 品牌
 */
public interface Brand {
	void sale();
}

class Lenovo implements Brand{
	public void sale() {
		System.out.println("销售联想电脑");
	}
}

class Dell implements Brand{
	public void sale() {
		System.out.println("销售戴尔电脑");
	}
}

/**
 * 电脑
 */
public abstract class Computer {
	protected Brand brand;
	public Computer(Brand brand) {
		this.brand = brand;
	}
	
	public void sale() {
		this.brand.sale();
	}
}

//定义台式机
class Desktop extends Computer{
	public Desktop(Brand brand) {
		super(brand);
	}
	@Override
	public void sale() {
		super.sale();
		System.out.println("销售台式机");
	}
}
//定义笔记本
class Laptop extends Computer{

	public Laptop(Brand brand) {
		super(brand);
	}
	
	@Override
	public void sale() {
		super.sale();
		System.out.println("销售笔记本");
	}
}

public static void main(String[] args) {
	Computer desktop = new Desktop(new Lenovo());
	desktop.sale();
}