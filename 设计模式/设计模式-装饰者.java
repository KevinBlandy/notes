----------------------------
装饰者设计模式				|
----------------------------
	# 没啥好说的,简单
		abstract class Beverage {		
			// 饮料
			private static final String defaultName = "unknow";
			public String getName() {
				return defaultName;
			}
			abstract int cost();
		}
		abstract class CondimentDecorator extends Beverage {	// 调料
			public abstract String getName();	
		}

		class Coffee extends Beverage {		// 咖啡
			@Override
			public String getName() {
				return "咖啡";
			}
			@Override
			public int cost() {
				return 15;
			}
		}
		class Cola extends Beverage {		// 可乐
			@Override
			public String getName() {
				return "可乐";
			}
			@Override
			public int cost() {
				return 10;
			}
		}
		class Coriander extends CondimentDecorator {	// 香菜
			private Beverage beverage; 
			public Coriander(Beverage beverage) {
				this.beverage = beverage;
			}
			@Override
			public String getName() {
				return "香菜";
			}
			@Override
			public int cost() {
				return 2 + this.beverage.cost();
			}
		}
		class Mustard extends CondimentDecorator {		// 芥末
			private Beverage beverage; 
			public Mustard(Beverage beverage) {
				this.beverage = beverage;
			}
			@Override
			public String getName() {
				return "芥末";
			}
			@Override
			public int cost() {
				return 1 + this.beverage.cost();
			}
		}
		public class Main {
			public static void main(String[] args) throws InterruptedException {
				Beverage beverage = new Coffee();	//	咖啡
				beverage = new Coriander(beverage);	// 	加 香菜
				beverage = new Mustard(beverage);	// 	加 芥末
				System.out.println(beverage.cost()); // 18块钱
			}
		}

