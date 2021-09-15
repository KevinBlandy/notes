-----------------
Record
-----------------
	# 专门用于“数据”对象的语法糖“类”
		public class Point {
			private final int x;
			private final int y;
			
			public Point(int x, int y) {
				this.x = x;
				this.y = y;
			}
		}
	
		*一般这种类需要复写 toString()equals()hashCode()枯燥且乏味
	
	# Record 类目的就是为了帮我们提供这些快捷方法
	

	# 生成的类，最终会继承 public abstract class Record
		protected Record() {}
		public abstract boolean equals(Object obj);
		public abstract int hashCode();
		public abstract String toString();
	
	# 可以添加构造函数
		public record State(String name, String capitalCity, List<String> cities) {

			public State(String name, String capitalCity, List<String> cities) {
				this.name = name;
				this.capitalCity = capitalCity;
				this.cities = List.copyOf(cities);
			}

			public State(String name, String capitalCity) {
				this(name, capitalCity, List.of());
			}

			public State(String name, String capitalCity, String... cities) {
				this(name, capitalCity, List.of(cities));
			}
		}