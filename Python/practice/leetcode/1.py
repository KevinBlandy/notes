'''
https://leetcode-cn.com/problems/two-sum/description/

给定一个整数数组和一个目标值，找出数组中和为目标值的两个数。

你可以假设每个输入只对应一种答案，且同样的元素不能被重复利用。

示例:

给定 nums = [2, 7, 11, 15], target = 9

因为 nums[0] + nums[1] = 2 + 7 = 9
所以返回 [0, 1]
'''
class Solution(object):
    def twoSum(self, nums, target):
        result = []
        length = len(nums)
        index = 0
        current = None
        while(index < length):
            current = nums[index]
            for i in range(index + 1,length):
                if (nums[i] + current) == target:
                    result.append(index)
                    result.append(i)
            index += 1
        return result
print(Solution().twoSum([2, 7, 11, 15], 9))