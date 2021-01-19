-----------------------------
枚举						 |
-----------------------------
	# 枚举类型语法定义格式为
		enum DAY
		{
			  MON=1, TUE, WED, THU, FRI, SAT, SUN
		};

		* 第一个枚举成员的'默认值为整型的 0',后续枚举成员的值在前一个成员上加 1
		* 在这个实例中把第一个枚举成员的值定义为 1,第二个就为 2,以此类推

	# 可以在定义枚举类型时改变枚举元素的值
		enum season {spring, summer=3, autumn, winter};

		* 有指定值的枚举元素,其值为前一元素加 1,也就说 spring 的值为 0,summer 的值为 3,autumn 的值为 4,winter 的值为 5
	
	# 枚举变量的定义
		* 前面只是声明了枚举类型,通过以下三种方式来定义枚举变量
		
		1,先定义枚举类型，再定义枚举变量
			enum DAY
			{
				  MON=1, TUE, WED, THU, FRI, SAT, SUN
			};
			enum DAY day;
		
		2,定义枚举类型的同时定义枚举变量
			enum DAY
			{
				  MON=1, TUE, WED, THU, FRI, SAT, SUN
			} day;
		
		3,省略枚举名称,直接定义枚举变量
			enum
			{
				  MON=1, TUE, WED, THU, FRI, SAT, SUN
			} day;
	
	# 枚举的遍历
		* 在C 语言中,枚举类型是被当做 int 或者 unsigned int 类型来处理的,所以按照 C 语言规范是没有办法遍历枚举类型的
		* 不过在一些特殊的情况下,枚举类型必须连续是可以实现有条件的遍历
			#include<stdio.h>
			enum DAY
			{
				  MON=1, TUE, WED, THU, FRI, SAT, SUN
			} day;
			int main()
			{
				// 遍历枚举元素
				for (day = MON; day <= SUN; day++) {
					printf("枚举元素：%d \n", day);
				}
			}
		*  枚举类型不连续,这种枚举无法遍历
	
	# 枚举在 switch 中的使用
		enum color { red=1, green, blue };

		enum  color favorite_color;
	 
		printf("请输入你喜欢的颜色: (1. red, 2. green, 3. blue): ");
		scanf("%d", &favorite_color);
	 
		switch (favorite_color){
			case red:
				printf("你喜欢的颜色是红色");
				break;
			case green:
				printf("你喜欢的颜色是绿色");
				break;
			case blue:
				printf("你喜欢的颜色是蓝色");
				break;
			default:
				printf("你没有选择你喜欢的颜色");
		}
	
	# 将整数转换为枚举
		enum Color {
			RED,
			BLUE,
			YELLOW,
			BLACK
		};
		int a = 0;
		enum Color color = (enum Color)a;