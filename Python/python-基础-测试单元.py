--------------------------------
测试单元						|
--------------------------------
	import unittest
	class MyTest(unittest.TestCase):
		# 执行测试之前执行
		def setUp(self):
			pass

		# 执行测试的方法,必须是由 test 开头
		def testDemo(self):
			pass

		# 测试执行完毕后的清理工作
		def tearDown(self):
			pass

	if __name__ == '__main__':
		# 执行测试
		unittest.main()
