
# KMP算法
	* 解决的问题就是, 判断字符串中是否存在某个子串儿, 如存在, 返回第一次出现的位置
	* KMP(看毛片)是三个人的姓氏开头, 因为是他们联合发表的





# 暴力匹配的实现
	def violenceMatch(source, target):

		slen = len(source)
		tlen = len(target)
		
		s = 0
		t = 0
		
		while s < slen and t < tlen:
			if source[s] == target[t]:
				s += 1
				t += 1
			else:
				s = s - (t - 1)
				t = 0
		
		if t == tlen:
			return s - t
		return -1
	
# KMP的实现
	# 获取一个字符串的部分匹配值表
	def kmpNext(dest):
		retVal = [0 * i for i in range(len(dest))]
		retVal[0] = 0 # 如果是长度为1，部分匹配值就是0
		i = 1
		j = 0
		while i < len(dest):
			# kmp算法核心
			while j > 0 and not dest[i] == dest[j]:
				j = retVal[j - 1]
			# 部分匹配值就是要 + 1
			if dest[i] == dest[j]:
				j += 1
			retVal[i] = j
			i += 1
		return retVal

	# KMP查找
	"""
		@param source:    源字符串
		@param target:    子串儿     
		@param arr:       子串儿的部分匹配值表
	"""
	def kmpMatch(str1, str2):
		arr = kmpNext(str2)
		j = 0
		for i, char in enumerate(str1):
			# kmp算法核心
			while j > 0 and not char == str2[j]:
				j = arr[j - 1]
			if char == str2[j]:
				j += 1
			if j == len(str2):
				return i - j + 1
		return -1

	s1 = 'BBC ABCDAB ABCDABCDABDE'
	s2 = 'ABCDABD'

	print(kmpMatch(s1, s2))