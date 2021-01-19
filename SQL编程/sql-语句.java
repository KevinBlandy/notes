------------------------
if 语句					|
------------------------
	IF [表达式] THEN
		[操作]
	ELSEIF [表达式] THEN
		[操作]
	ELSE
		[操作]
	END IF
	
	# 可以由 =、<、<=、>、>=、!= 等条件运算符组成，并且可以使用AND、OR、NOT对多个表达式进行组合

------------------------
case 语句				|
------------------------
	case [变量]
		when [值] then [结果]
		when [值] then [结果]
		else [结果]
	end

	case  
		when [条件] then [结果]
		when [条件] then [结果]
		else [结果]
	end
