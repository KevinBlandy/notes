
// 使用is的自动转换类型,配合 when 计算和

import java.lang.Exception

interface Expires

class Num(val number:Int):Expires
class Sum(val left:Num, val right:Num):Expires

fun eval(expires: Expires): Int {
    return  when (expires) {
        is Num -> expires.number
        is Sum -> eval(expires.left) + eval(expires.right)
        else -> throw Exception("");
    }
}

fun main(args:Array<String>){
    var value = eval(Sum(Num(1), Num(eval(Sum(Num(1),Num(5))))));
    println(value)
}


// 编译异常的表达式
fun foo(expires: Expires) = when (expires) {
        is Num -> expires.number
		// foo(expires.left) 异常
		/*
			为啥? 是因为递归的时候, 无法确定上层栈返回的数据类型么???
		*/
        is Sum -> foo(expires.left) +  foo(expires.right)
        else -> throw Exception("")
    }
}

