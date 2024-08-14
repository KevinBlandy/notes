---------------------
range-over-func
---------------------
	# 参考
		https://go.dev/wiki/RangefuncExperiment
		https://colobu.com/2024/01/18/range-over-func/
	

	# 现在，for-range 除了能迭代 slice、数组、map、字符串、channel、整数外，还可以迭代三种 '函数'
		* 支持 range 表达式的函数类型
			// range 表达式					// 第一个值		// 第二个值
			func(func() bool) bool		
			func(func(V) bool) bool			V	
			func(func(K, V) bool) bool		K				V

		
		* 用法
			for x, y := range f { ... }			// 迭代 key 和 value
			for x, _ := range f { ... }			// 迭代 key
			for _, y := range f { ... }			// 迭代 value
			for x := range f { ... }			// 迭代 value
			for range f { ... }					// 迭代
		

	# Demo

		package main

		import (
			"fmt"
			"time"
		)

		func main() {
			var fn = func(yield func(k, v any) bool) {
				count := 1
				for {
					time.Sleep(time.Millisecond * 500)
					// 进行一轮迭代
					r := yield(count, time.Now().Format(time.DateTime))
					fmt.Printf("%d continue=%v\n", count, r)
					if !r {
						// range 可能执行了 break/return
						// 则不能再次调用 yield 执行函数了，否则 panic
						// 可以退出迭代，或者执行其他代码
						break
					}

					count++
				}
				fmt.Println("迭代结束")
			}
			for k, v := range fn {
				fmt.Printf("{%v: %v}\n", k, v)
				if k == 3 {
					continue
				}
				if k == 5 {
					fmt.Println("break/return")
					return
				}
			}
		}


		
		// 输出
		{1: 2024-08-14 10:46:05}
		1 continue=true
		{2: 2024-08-14 10:46:06}
		2 continue=true
		{3: 2024-08-14 10:46:06}
		3 continue=true
		{4: 2024-08-14 10:46:07}
		4 continue=true
		{5: 2024-08-14 10:46:07}
		break/return
		5 continue=false
		迭代结束
	
		
		* fn 姑且称为 “迭代函数”，它的参数 yield 姑且称为 “执行函数”，yield 这个名称随意，这里只是为了和 JS 中的迭代器一致。
		* 传递给执行函数的参数，就是最终 range 循环的参数，“执行函数” 每执行一次，range 迭代就会执行一次。
		* 迭代函数控制整个迭代周期，它结束，表示迭代结束，但是也有一种可能会提前 return ，那就是在 range 中使用了 break 或 return
		* 如果 range 中断(break/return)，那么迭代函数中的执行函数就会返回 false 
		* 此时，执行函数就不能再次执行了，否则 panic ，这就是为什么要在迭代函数中判断，如果执行函数返回 false 则 return ，整个迭代过程结束
			panic: runtime error: range function continued iteration after function for loop body returned false
			// range 函数在 for 循环体返回 false 后继续迭代
		
