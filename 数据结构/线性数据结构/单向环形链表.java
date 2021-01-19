------------------------
单向环形链表			|
------------------------
	# 实现思路
		* 先创建第一个节点，让first指向该节点，形成环形
	
	# 遍历的思路
		
	# 约瑟夫环问题
		* 设置编号为: 1, 2, 3...n个人围绕作一圈儿
		* 约定编号为k的人从1开始报数，数到m位置的人出列(1 <= k <= n)
		* m的下一位又从1开始报数，数到m的那个人又出列
		* 依次类推，直到所有的人都出列为止，由此产生一个出队编号的序列
	
	

------------------------
Python 实现				|
------------------------
	class Node(object):
		def __init__(self, value, next):
			self.value = value
			self.nex = next


	class Joseph(object):
		def __init__(self):
			self.first = None
			self.current = None
			self.size = 0

		def add(self, value):
			node = Node(value, None)
			if self.first is None:
				self.first = node
				self.first.next = self.first
				self.current = self.first
			else:
				self.current.next = node
				node.next = self.first
				self.current = node

			self.size += 1


		def forEach(self, handler):
			if self.first is None:
				return
			temp = self.first
			while True:
				handler(temp.value)
				if temp.next == self.first:
					break
				temp = temp.next

		def joseph(self, start, count):
			if self.first is None:
				return

			# 让last指向最后一个节点
			last = self.first
			while True:
				if last.next == self.first:
					break
				last = last.next

			for x in range(start - 1):
				self.first = self.first.next
				last = last.next

			while True:
				if last == self.first:
					break
				for x in range(count - 1):
					self.first = self.first.next
					last = last.next

				# 出队
				print(self.first.value)
				self.size -= 1

				self.first = self.first.next
				last.next = self.first

			# 留下的最后一个
			print('last = %s' % self.first.value)


	queue = Joseph()

	for i in range(1, 6):
		queue.add(i)

	queue.joseph(1, 2)

	print(queue.size)






