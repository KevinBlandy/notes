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