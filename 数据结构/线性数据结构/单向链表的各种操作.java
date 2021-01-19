--------------------------
单向链表的反转			  |
--------------------------
	# 实现思路

	# 代码实现
		class Node (object):
			def __init__(self, value, next):
				self.next = next
				self.value = value


		def console(node):
			while node is not None:
				if node.value :
					# 不是虚拟头节点
					print(node.value)
				node = node.next


		def reverse(node):
			if node is None or node.next is None:
				return

			current = node.next
			next = None
			reverseNode = Node(None, None)

			while current is not None:
				next = current.next
				current.next = reverseNode.next
				reverseNode.next = current
				current = next

			node.next = reverseNode.next


		# 虚拟头节点
		dummyNode = Node(None, None)
		node1 = Node(1, None)
		node2 = Node(2, None)
		node3 = Node(3, None)
		node4 = Node(4, None)

		# 组织链表关系
		dummyNode.next = node1
		node1.next = node2
		node2.next = node3
		node3.next = node4

		# 输出原始的链表
		console(dummyNode)

		# 反转链表
		reverse(dummyNode)

		print("------------反转后------------")

		# 输出
		console(dummyNode)




--------------------------
逆序打印链表			  |
--------------------------
	# 使用栈数据结构来进行遍历
		* 先把各个节点入栈
		* 再遍历栈就完事儿
	

	