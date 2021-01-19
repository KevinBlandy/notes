JAVA里面其他的对象
System 类 ——系统类
	没有构造函数，不能被实例化。里面的方法全是静态的。
--------------------------------------------------------
Runtime 类
	没有构造函数，不能被实例化。
	它其实是一个单例。有一个静态方法，可以获取它的对象！
	该方法是getRuntime();
	Runtime r = Runtime.getRuntime();
	r.exec("CMD命名");
	|--可以执行一段当前操作系统的命令。
	Process prop = r.exec(可执行程序路径);
	|--运行指定路径的程序
	Thread.sleep(4000);
	|--线程停止4秒 
	prop.destroy();
	|--杀死进程。(只能弄死它启动的程序)
	prop.getInputStream();
	|--可以获取到cmd的一个输入流,执行了cmd命名后可以读取到cmd显示出来的内容

--------------------------------------------------------
Date 类
	|--sql.Date   -数据库
	|--Calendar 日历类
		|--是一个抽象基类，主要用于日期字段之间相互操作的功能。
		无法之间创建对象。抽象类，里面其实没有抽象方法。
		Calendar.getInstance();//获得子类对象。
		GregorianCalendar 通过它的这个子类来获取对象也可以。
	|--DateFormat 类//用语解析Date变成字符串，或者把字符串变成Date的类。。抽象的
		|--SimpleDateFormat //子类。。。。
		SimpleDateFormat sdf = new SimpleDateFormat("显示的时间格式");
			|--sdf.format(Date);//对指定的Date对象进行。指定的时间格式规范化。
			|--在把一个字符串解析成时间的时候。字符串的格式必须和给定的格式，相匹配
			|--String str = "2015-09-24 14:58:32";
			|--sdf.parse(str);//把字符串解析成时间对象。
Date d = new Date();
	|--获取当前时间。
Date d = new Date(long);
	|--根据指定的毫秒值创建日期对象。
d.getTime();
	|--把时间转换成毫秒数。返回 long 类型。
Date d = new Date(464646464l);
	|--把该 long 形值，转换成 Date 类型。表示成时间。
d.before(Date d1);
	|--如果d在d1之前。那么返回 true
d.after(Date d1);
	|--如果d在d1之后。那么返回 false
d.setTime(long);
	|--根据毫秒值设置日期对象。返回的就是一个日期对象。
d.toLocaleString();
	|--格式化时间(跟环境语言相关),返回 String 字符串
-------
SimpleDateFormat 易于国际化的一个类。
SimpleDateFormat sdf = new SimpleDateFormat();
|--DateFormat 类//用语解析Date变成字符串，或者把字符串变成Date的类。。抽象的
		|--SimpleDateFormat //子类。。。。
		SimpleDateFormat sdf = new SimpleDateFormat("显示的时间格式");
			|--sdf.format(Date);//对指定的Date对象进行。指定的时间格式规范化。
			|--在把一个字符串解析成时间的时候。字符串的格式必须和给定的格式，相匹配
			|--String str = "2015-09-24 14:58:32";
			|--sdf.parse(str);//把字符串解析成时间对象。
sdf.format(Date);
	|--格式化一个时间对象。返回的是一个字符串。默认的格式是:年后两位-月-日 上/下午小时:分钟(15-9-15 上午10:11)
SimpleDateFormat sdf = new SimpleDateFormat("yyy年MM月dd日");
sdf.format(Date);
	|--格式化时间的对象。按照设定格式来显示时间对 Date 进行初始化。返回指定的字符串格式。
sdf.parse(String);
	|--把字符串格式转换为 Date 类型。注意，字符串格式的默认格式：:年后两位-月-日 上/下午小时:分钟(15-9-15 上午10:11)
	如果 sdf 对象在创建的时候，指定了日期格式。那么这个方法中的形参字符串。就要按照自己指定的格式来传递参数
	"yyy年MM月dd日" 2015年9月15日
	并且此类需要处理/抛出异常： ParseException 
-------
Calendar c = Calendar.getInstance();
	-----------------------------并不是单利设计模式，获取的是子类的对象。每时每刻获取的对象。都有不一样的数据！
								 根据计算机的时区，时间。来生成特有的对象。
常见字段 YEAR MONTH DAT WEEK
c.get(Calendar.WEEK_OF_YEAR);
	|--获取指定字段的值，返回 int 类型数据。
c.add(Calendar.DAY_OF_MONTH,1);
	|--据日历的规则，为给定的日历字段添加或减去指定的时间量。
c.get(Calendar.DAY_OF_MONTH);
	|--获取，月份中的第几天。返回 int 类型。
c.set(Calendar.DAY_OF_MONTH,23);
	|--设置月份中的天数为23。
c.setTime(Date);
	|--把 Date 转换成 Calendar 的日历格式。无返回值
c.getTime();
	|--把c日历类转换成 Date 类。返回的是 Date 对象。
--------------------------------------------------------
Math 类   
	用于执行基本数学运算的方法。
Math.ceil(指定值);
	|--返回大于指定数据的最小整数。
Math.floor(指定值);
	|--返回小余指定数据的最大整数。
Math.round(指定值)
	|--对指定值进行四舍五入。
Math.pow(x,y);
	|--返回x值的。y次幂。
Math.random();
	|--返回一个包含0，不包含1的随机数。
Math.max(x,y);
	|--返回俩值中比较大的一个。返回值就是该数值类型。
------------------------------------------------------
Pattern  Matcher   用于正则表达式
	|--匹配器
------------------------------------------------------- Random
Random r = new Random();//未给种子。那么是根据当前时间的毫秒值来获取的随机数。
Random r = new Random(long);//给定种子。
r.nextInt();//返回 0 - 1 之间的任何数。
r.netxInt(int num);//返回0 - num之间的随机数。不包含 num.
	|--给定种子之后。返回的随机数。是相同的。



AtomicInteger
	基本工作原理是使用了同步synchronized的方法实现了对一个long, integer, 对象的增、减、赋值（更新）操作. 
	比如对于++运算符AtomicInteger可以将它持有的integer 能够atomic 地递增。
	在需要访问两个或两个以上 atomic变量的程序代码（或者是对单一的atomic变量执行两个或两个以上的操作）通常都需要被synchronize以便两者的操作能够被当作是一个atomic的单元。
	说白了,就是线程安全的自增,自减



