------------------------
条件
------------------------
	# IF 条件
		{{ if [condition]}}
            <h1>true</h1>
		{{ else if [condition] }}
			 <h1>true</h1>
        {{ else }}
            <h1>false</h1>
        {{ end  }}

		* 如果变量不存在，会返回false
		* 当 .condition 为bool类型的时候，则为true表示执行
		* 当 .condition为string类型的时候，则非空表示执行
		* 当 .condition为数值类型的时候，则非0表示执行
		* 当 .condition为指针类型的时候，则非nil表示执行
	
	# 关系
		and/or/not
			{{ if and .Admin .Enabled}}
				<h1>{{ .Name }}</h1>
			{{- else }}
				<h1>{{ "非法用户" }}</h1>
			{{- end  }}	
		
		* and函数返回bool值，通过返回第一个空值或者最后一个值。and x y逻辑上相当于if x then y else x
		* or 函数类似 and 函数，但是只要遇到 true就返回。or x y 等价于 if x then x else y。 x 非空的情况下y不会被评估。
		* not函数返回参数的相反值:
	
	# 比较，{{ [function] arg1 arg2 }}
		eq	arg1 == arg2
		ne	arg1 != arg2
		lt	arg1 < arg2
		le	arg1 <= arg2
		gt	arg1 > arg2
		ge	arg1 >= arg2

		{{ if eq .Name "KevinBlandy"}}
            <h1>是本人</h1>
        {{- end  }}

		* eq函数比较特殊，可以拿多个参数和第一个参数进行比较，多个关系是或
			{{ eq arg1 arg2 arg3 arg4 }}逻辑是: arg1==arg2 || arg1==arg3 || arg1==arg4
