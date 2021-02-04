-------------------------------
命令模式
-------------------------------
	# 命令模式
		// 命令接口
		interface Command {
			void execute();
		}
		class Car implements Command{		// 车
			public void run() {
				System.out.println("car is runing...");
			}
			@Override
			public void execute() {
				this.run();
			}
		}

		class Control {		// 控制器
			private Command command;
			public Control(Command command) {
				this.command = command;
			}
			public void control() {
				this.command.execute();
			}
		}

		public class Main {
			public static void main(String[] args) throws InterruptedException {
				Control control = new Control(new Car());
				control.control();  // car is runing...
			}
		}