基本数据类型的包装类。
	所谓万物皆对象，基本数据类型。也是一个对象！也可以拥有方法。
byte	—— Byte
short   —— Short
int     —— Integer		!
long    —— Long
boolean —— Boolean
float   —— Float
double  —— Double
char    —— Character    !
 
 基本数据类型包装类的最常见作用————

	基本数据类型和字符串数据类型之间做转换。
基本数据类型 —— 转换 —— 字符串
	.toString(基本数据类型值);
		|--Integer.toString(34);  把34变成字符串。
	+"";
字符串 —— 转换 —— 基本数据类型
	基本数据类型 x = 基本数据类型包装类.parseXxx(String);
Integer.parseInt(x);
	|--把字符串x转换成 int 类型的数据,返回一个 int。
Long.parseLong(x);
	
——————————————布尔型的转换 
Boolean.parseBoolean(x);
	|--把字符串x转换成布尔型
——————————————char形的转换
	转换你麻痹。。。这个他妈本身就是字符。你拿字符串转换成字符。你要在这里找方法？

十进制转换成其他进制
	toBinaryString();
	toHexString();
	toOctalString();
其他进制转换十进制
	parseInt();




	