关于JAVA中的 String
String 被 final 修饰。也就是说不能继承该类。更不可能覆写里面的功能
String s = "abc";
	|--s是一个类类型变量。“abc”是一个对象。字符串最大的特点，一旦被初始化成功。就不可以被改变。
String 类覆写  Object 类中的equals方法。该方法用于判断字符串是否相同。
String 类适用于描述字符串事物。那么它就提供了多个方法对字符串进行操作。重点就是方法的掌握。
-----------------------------------------------------------------------------------------------
String 常用方法
	Sring s = new String();
'获取'
s.charAt(2);
	|--返回s字符串中角标为2的字符(char)！
s.length();	
	|--返回s字符串的长度(int)!
s.indexOf('x');
	|--返回x字符,它在字符串中'第一次出现'的位置(int)!
s.indexOf("x",3);
	|--从s字符串的第三个位置开始查找字符x。并返回它所在的位置(int)!(与上面方法构成函数重载)
s.indexOf("xx");
	|--从s字符串中返回xx字符串的位置(int);
s.lastIndexOf('x');
	|--从字符串s中，反向查找x字符。并返回它的位置(int);如果x是 ""也就是空。那么返回的就是这个字符串的长度，跟length();一样！
s.lastIndexOf("xx");
	|--从字符串s中，反向查找xx字符串！并返回它的位置(int);
'判断'
s.isEmpty();
	|--判断字符串s是不是一个空字符串(boolean);
s.endsWith("x");
	|--判断字符串s是不是以x字符结尾(boolean)-对大小写敏感;
s.startsWith("x");
	|--判断字符串s是不是以x字符开头(boolean)-对大小写敏感;
s.contains("xx");
	|--判断字符串s是不是包含字符"xx"(boolean)-对大小写敏感;
s.equals(s1);
	|--判断字符串s和s1字符串的内容是否相同(boolean)(覆写了 Object 类的equals方法);
s.equalsTgnoreCase(s1);
	|--忽略大小写比较s和s1俩字符串是否相同;
'转换'
构造函数中已经具备部分转换功能
String s = new String(x);
	|--把字'符数组x'转换成s字符串；
String s = new String(x,0,3);
	|--把字符数组x中.0角标位置开始'取3个字符'。转换成字符串s。
String s = String.valueOf(x);
	|--把X转换成字符串
常见方法
s.toCharArray();
	|--把字符串s转换成数组(char[]);
String.valueOf(x);[static]
	|--把x转换成字符串！x可以是多种数据类型(int,boolean,long....)(String);
s.toUpperCase();
	|--把s字符串全部转换成大写并且返回新的字符串(String);
s.toLowerCase();
	|--把s字符串全部转行成小写并且返回新的字符串(String);
'替换'
s.replace(x,y);
	|--把字符串s中所有'x字符',全部换成'y字符'。并返回一个新的字符串(String)如果被替换的字符不存在。还是返回原串;
s.replace(x,y);
	|--把字符串s中的的'x字符串',全部替换成'y字符串'。并返回一个新的字符串(String)如果被替换的字符串不存在。还是返回原串。
'切割'
s.split(x);
	|--把字符串s。进行切割。只要遇到字符串x就切割一次。返回一个字符串类型的数组,不包含x(String[]);
s.split(x,num);
	|--切割字符串。num切割的次数。也可以理解成返回的 String 数组的最大角标。 
'子串'
s.substring(x,y);
	|--从s字符串的x角标开始。y角标结束(不包含y)。如果没有y就是到结尾。生成一个新的字符串并返回(String);
	如果角标不存在会发生角标越界的异常。
'去除'
s.trim();
	|--去除s字符串两端的空格后，并返回(String);
s.conmpareTo(s1)
	|--对两个字符串进行自然顺序的比较.如果s1等于s的话。就返回0.如果 s1>s 返回负数.如果 s1<s 返回一个正数(大于0)(int);
	比较方法参照ASCII码表。
--------------------------------------------------------------
StringBuffer b = new StringBuffer();
StringBuffer
	|--
b.append(x);
	|--在b字符串后面追加一个字符串x。
--------------------------------------------------------StringBuffer--------------------
关于JAVA的 StringBuffer 适合多线程
————线程同步，安全
StringBuffer 是字符串缓冲区，一个容器。而且长度是可变化的。
1,该容器的长度是可变的。
2,可以操作多个数据类型。
3,最终会通过toString方法变成字符串。
————当数据类型不确定，当个数不确定、最后都要变成字符串使用的。就可以考虑使用缓冲区。
【作为数据的存储，一般是为了在最后用toString变成字符串使用】
StringBuffer sb = new StringBuffer();
①存储
sb.append(x);	————含大部分重载函数
	|--将指定的数据,x,添加到已有数据的结尾处。返回的还是本类对象(StringBuffer);
sb.insert(1,x);	————含大部分重载函数
	|--将指定的数据x,插入到sb的1角标位置。其余的往后延伸。还是返回该类对象(StringBuffer);
如果x角标不存在。运行的时候会出现角标越界。
②删除
sb.delete(x,y);
	|--删除缓冲区中x - y位置的数据！包含x不包含y。返回的还是该类对象(StringBuffer);
sb.deleteCharAt(x);
	|--删缓冲区中指定位置。x处的数据。返回的还是该类对象(StringBuffer);
③获取
sb.charAt(x);
	|--通过角标获取字符串。
sb.indexOf(x)
	|--通过字符串获取位置。
sb.substring(x,y);
	|--从x开始y结束。不包含y。返回的是一个String(String)；
sb.getChars(x,y,chs,z);
	|--把sb容器里面x到y的数据。存放到chs容器里面,从z位置开始存放。
将缓冲区中的指定数据(包含头部包含尾)。存储到指定数组中。
④修改
sb.replace(x,y,str);
	|--把容器里面从x开始到y结束(不包含y)的内容替换成str(StringBuffer);
sb.setCharAt(x,str);
	|--把容器里面x位置的数据替换成str。没有返回值。运行完就完事儿(void);
⑤反转
sb.reverse();
	|--反转整个容器。返回的还是该类对象(StringBuffer);
-----------------------------------------------------------------------------------------------
关于JAVA的 StringBuilder  适合单线程，如果非要应用于多线程。需要自己加锁  synchronized 
————线程不同步，线程不一定安全。
在JDK1.5版本(较新)之后才出现了 StringBuilder 。