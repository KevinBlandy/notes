----------------------------
Java对象的深浅Clone			|
----------------------------
	* 浅clone，只是简单的吧对象的属性copy到clone对象上,这样两个对象的相同属性都指向了同一个地址
	* 深clone，clone对象的属性，是新创建的属性，由源对象的属性复制过来
	*  先谈谈clone()方法
	* 这是定义在 Object 里面的方法，Java里面任何对象都能调用，但是它是被protected修饰
		protected native Object clone() throws CloneNotSupportedException;
	*  也就是说，我们自己创建的对象没法直接调用该方法，因为自己创建类的没法跟Object在同一个包。
	*  必须要覆写掉该方法，在覆写方法中调用Object的方法来完成clone，clone细节不用关心，因为它是一个native本地方法。
	* 调用clone方法的类，必须要实现Cloneable接口，该接口跟Serializable一样，只是一个标记接口，无任何抽象方法

----------------------------
浅clone						|
----------------------------
	import java.util.Date;
	class Foo implements Cloneable {
		public Date date;
		Foo(Date date){
			this.date = date;
		}
		@Override
		protected Object clone() throws CloneNotSupportedException {
			//调用父类的本地方法，获得浅clone的对象
			return super.clone();
		}
	}
	public class Main {
		public static void main(String[] args) throws CloneNotSupportedException {
			Foo foo = new Foo(new Date());
			Foo cloneFoo = (Foo) foo.clone();
			System.out.println(foo == cloneFoo);                //false
			System.out.println(foo.date == cloneFoo.date);        //true（浅clone,俩对象的date其实都是同一个）
		}
	}
----------------------------
深clone						|
----------------------------
	import java.util.Date;
	class Foo implements Cloneable {
		public Date date;
		Foo(Date date){
			this.date = date;
		}
		@Override
		protected Object clone() throws CloneNotSupportedException {
			//浅clone Foo对象
			Foo cloneFoo = (Foo) super.clone();
			//浅clone Date（属性）对象
			Date cloneDate = (Date) date.clone();
			//深clone完成
			cloneFoo.date = cloneDate;
			return cloneFoo;
		}
	}
	public class Main {
		public static void main(String[] args) throws CloneNotSupportedException {
			Foo foo = new Foo(new Date());
			Foo cloneFoo = (Foo) foo.clone();
			System.out.println(foo == cloneFoo);                //false
			System.out.println(foo.date == cloneFoo.date);        //false
		}
	}

在设计模式原型模型中clone用得多
其实就是把一个对象进行复制，复制出来的对象，就拥有了原对象的所有属性和方法，并且可以基于该对象进行增强，修改，而且不会影响原对象