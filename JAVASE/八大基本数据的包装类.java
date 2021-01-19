int Integer	extends Number
Integer i = new Integer(String);//可以把数字字符串传递给构造函数。如果字符串不是数字，或者超出Integer的范围。会报错。

long Long	..


boolean Boolean	extends	Object


char Character	extends Object 


float Float	extends Number


double Double	..


byte Byte	..


short Short	..

除了 Character 跟 Boolean 以外。其它所有的包装类都是 Number 的一个子类。
而这两个，直接就是属于 Object 类的子类。
----------
Integer x = 1;
自动装箱  
Integer x = new Integer(1);
-------------------
Integer x = 1;
自动拆箱
int y = x;
