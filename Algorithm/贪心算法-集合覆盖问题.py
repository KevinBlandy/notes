# 贪心算法
	* 也叫做贪婪算法,
	* 在对问题进行求解的时候, 每一步都采取最好的选择
	* 从而"希望"最终计算结果都是最好的

	* 贪心算法, 得到的结果不一定是最好的结果
	* 但是都是相对近似最好的结果

	* 通俗的理解就是, 贪心算法可以解决问题
	* 但是计算出的解决方案, 不一定是所有解决方案中最好的


# 解决集合覆盖问题
	* 如果存在下列的电台, 以及电台信号可以覆盖的地区

		K1		北京, 上海, 天津
		K2		广州, 北京, 深圳
		K3		成都, 上海, 杭州
		K4		上海, 天津
		K5		杭州, 大连
	
	* 如何选择最少的广播电台, 让所有地区都可以接收(覆盖所有)



# 代码实现

# 广播电台
broadCasts = dict()

# 存入数据
broadCasts['K1'] = {"北京", "上海", "天津"}
broadCasts['K2'] = {"广州", "北京", "深圳"}
broadCasts['K3'] = {"成都", "上海", "杭州"}
broadCasts['K4'] = {"上海", "天津"}
broadCasts['K5'] = {"杭州", "大连"}


# 需要覆盖的所有地区
allArea = {"北京", "上海", "天津", "广州", "深圳", "成都", "杭州", "大连"}

# 选择的电台集合
selects = []

# 临时集合
tempSet = set()

maxKey = None

while len(allArea) != 0:
    
    # 还未覆盖到所有的地区
    for key, value in broadCasts.items():
        
        tempSet.clear()
        
        #  并集
        tempSet = tempSet.union(value)
        # 交集
        tempSet = tempSet.intersection(allArea)
        
        # 贪心算法的核心
        if len(tempSet) > 0 and (maxKey is None or (len(tempSet) > len(broadCasts[maxKey]))):
            maxKey = key
        
    if maxKey is not None:
        selects.append(maxKey)
        allArea = allArea.difference(broadCasts[maxKey])
    
    maxKey = None

# 覆盖所有的结果
print(selects)
    
        

