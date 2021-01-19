------------------------
random					|
------------------------
	* 随机数模块
	

------------------------
模块函数				|
------------------------
	float random()
		* 返回大于0,于1的随机数
	
	int randint(begin,end)
		* begin,end 都必须参数,且必须是整数,
		* 返回 begin,end之间的随机整数,包含begin和end
	
	int randrange(begin,end)
		* 比较常用
		* 返回 begin - end 之间的任意整数,包含begin,不包含end
		* begin 默认值为0,如果 <= 0,则抛出异常

	T choice(sequence)
		* 返回序列数据中的任意一个数据
		* 关于返回值,返回的数据是啥,类型就是啥
		* demo
			var = random.choice(['1','2','3'])

	None shuffle(list)
		* 参数是一个列表,会修改参数序列
		* 该参数不能是:tuple/frozenset...等不可修改的数据类型
		* 该方法会把参数的的元素进行随机排序(洗牌)
	
	list sample(sequence,count)
		* 从 sequence 中随机选择 count 个数据,组成 list 后返回,不会修改参数序列
		* 一次执行不会重复的选择元素
		* 如果 count 大于seri的长度,抛出异常
		* demo
			arr = (1,2,3,6)
			var = random.sample(arr,2)
			print(arr)  # [2, 1]
		

------------------------
demo					|
------------------------

# 获取指定长度的验证码,随机大小写,允许重复
def get_verify_code(length):
    SOURCE  = tuple(['0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'])
    arr = []
    while not length <= 0:
        arr.append(random.choice(SOURCE))
        length -= 1
    for i,v in enumerate(arr):
        if(random.choice([True,False])):
            arr[i] = v.upper()
    return "".join(arr);