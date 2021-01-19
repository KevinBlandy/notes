'''
https://leetcode-cn.com/problems/remove-linked-list-elements/
# Definition for singly-linked list.
# class ListNode:
#     def __init__(self, x):
#         self.val = x
#         self.next = None

class Solution:
    def removeElements(self, head, val):
        """
        :type head: ListNode
        :type val: int
        :rtype: ListNode
        """
        
'''


class ListNode:

    def __init__(self, x):
        self.val = x
        self.next = None


class Solution:
    
    # 普通实现
    def removeElements(self, head, val):
        # 删除链表头元素
        while(head != None and head.val == val):
            delNode = head
            head = delNode.next
            delNode.next = None
        
        if head == None:
            return head
        
        # 删除链表中的元素
        prev = head
        while (prev.next != None):
            if prev.next.val == val:
                prev.next = prev.next.next
            else:
                prev = prev.next
        
        return head
    
    # 使用虚拟头节点
    def removeElements1(self, head, val):
        dummyHead = ListNode(-1)
        dummyHead.next = head
        
        prev = dummyHead
        
        while(prev.next != None):
            if(prev.next.val == val):
                prev.next = prev.next.next
            else:
                prev = prev.next
        return dummyHead.next
    
    # 使用递归实现
    def removeElements2(self, head, val):
        if head == None:
            return None
        head.next = self.removeElements2(head.next, val)
        return head.next if head.val == val else head
        
