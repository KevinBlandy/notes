
# 太简单了，不说

def bubble(arr):
    length = len(arr)
    for i in range(length):
        for x in range(i, length):
            if arr[i] > arr[x]:
                temp = arr[i]
                arr[i] = arr[x]
                arr[x] = temp


arr = [5, 6, 8, 7, 6, 3, 2, 4, 0, 1, 9]
bubble(arr)
print(arr)



# 冒泡排序的优化
	* 如果发现某一次的排序过程中没有发生交换, 可以提前结束整个排序

	def bubble(arr):

		# 设置标识，是否发生过交换
		flag = False

		length = len(arr)
		for i in range(length):
			for x in range(i, length):
				if arr[i] > arr[x]:
					# 发生过交换
					flag = True
					temp = arr[i]
					arr[i] = arr[x]
					arr[x] = temp

			if not flag:
				# 没有发生过交换，已经是有序的数组了
				break
			else:
				# 重置标识
				flag = False


	arr = [5, 6, 8, 7, 6, 3, 2, 4, 0, 1, 9]
	bubble(arr)
	print(arr)