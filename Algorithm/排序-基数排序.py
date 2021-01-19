# 基数排序
	* 基础排序属性分配式排序，又称为：桶排序
	* 通过键值的各个位的值，把要排序的元素分配到某些桶中，达到排序的作用

	* 基数排序是属于稳定性的排序，基数排序法是效率高的排序法

	* 基数排序是桶排序的扩展，1887年，赫尔曼-何乐礼发明的
	* 把整数位切割成不同的数字，然后按照每个位数分别比较

	* 如果有负数，不建议使用基数排序
		- 如果遇到负数，需要去执行，取绝对值等等操作，比较麻烦



# 代码实现

def radixSort(arr):
    # 得到最大值
    maxVal = arr[0]
    for x, z in enumerate(arr):
        if z > maxVal:
            maxVal = z
    # 最大值的位数
    maxLength = len(str(maxVal))

    bucket = []
    for i in range(10):
        bucket.append([0 for i in range(len(arr))])

    bucketElementCounts = [0 for i in range(10)]

    n = 1
    for l in range(maxLength):
        for i, v in enumerate(arr):
            digitOfElement = v // n % 10
            bucket[digitOfElement][bucketElementCounts[digitOfElement]] = v
            bucketElementCounts[digitOfElement] += 1
        index = 0

        for i, v in enumerate(bucketElementCounts):
            if not v == 0:
                for _i in range(v):
                    arr[index] = bucket[i][_i]
                    index += 1
            bucketElementCounts[i] = 0
        n *= 10
    print(arr)


arr = [53, 3, 542, 748, 14, 214]

radixSort(arr)
