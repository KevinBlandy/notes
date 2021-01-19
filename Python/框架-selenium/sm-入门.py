----------------------------
入门						|
----------------------------
	* 安装
		pip install selenium


----------------------------
创建对象					|
----------------------------
	# 导入webdriver
	from selenium import webdriver

	# 创建driver对象,通过executable_path指定phantomjs执行文件路径
	driver = webdriver.PhantomJS(executable_path='./phantomjs')

----------------------------
基本的操作					|
----------------------------
	# 导入 webdriver
	from selenium import webdriver

	# 想要调用键盘操作,需要引入 keys 包
	from selenium.webdriver.common.keys import Keys

	# 调用环境变量指定的 PhantomJS 浏览器创建浏览器对象
	# driver = webdriver.PhantomJS()

	# 如果没有在环境变量指定 PhantomJS 的位置(其实就是指定 phantomjs执行文件的地址)
	driver = webdriver.PhantomJS(executable_path='./phantomjs')

	# ger方法会一直等到页面完全被加载,然后才会继续程序的执行
	driver.get('https://www.baidu.com')

	# 生成页面快照
	driver.save_screenshot('baidu.png')

	# 找到id为kw的元素(input),在里面写入 "Hello"
	driver.find_element_by_id('kw').send_keys("Hello")

	# 找到id为id的元素,执行点击它
	driver.find_element_by_id('su').click()

	# 生成页面快照
	driver.save_screenshot('hello.png')

	# 打印网页源码
	print(driver.page_source)

	# 获取所有的 cookie
	print(driver.get_cookies())

	# 获取指定名称的cookie
	#driver.get_cookie('JESSIONID')

	# ctrl + a 全选输入框内容
	driver.find_element_by_id('kw').send_keys(Keys.CONTROL,'a')

	# ctrl + x 剪切输入框内容
	driver.find_element_by_id('kw').send_keys(Keys.CONTROL,'x')

	# 输入框重新输入内容
	driver.find_element_by_id('kw').send_keys('KevinBlandy')

	# 模拟enter回车键
	driver.find_element_by_id('su').send_keys(Keys.ENTER)

	# 清除输入框内容
	driver.find_element_by_id('kw').clear()

	# 获取当前Url
	print(driver.current_url)

	# 关闭当前页面,如果只有一个页面会关闭浏览器
	driver.close()

	# 关闭浏览器
	driver.quit()

----------------------------
元素获取					|
----------------------------
	* 基本的获取
		# 根据id检索
		driver.find_element_by_id()
		# 根据名字检索
		driver.find_element_by_name()
		driver.find_element_by_xpath()
		driver.find_element_by_link_text()
		driver.find_element_by_partial_link_text()
		# 根据标签名称检索
		driver.find_element_by_tag_name()
		# 根据class名称检索
		driver.find_element_by_class_name()
		# 根据css选择器检索
		driver.find_element_by_css_selector()
	
	* 也可以优雅的通过api来获取
		from selenium.webdriver.common.by import By
		# 通过 By 枚举来指定要过滤的属性,后面valu指定值
		element = driver.find_element(by=By.ID, value="coolestWidgetEvah")

		* By.CLASS_NAME
		* By.TAG_NAME
		* By.NAME
		* By.LINK_TEXT
		* By.CSS_SELECTOR
		* By.XPATH
	

----------------------------
鼠标动作链					|
----------------------------
	from selenium import webdriver
	from selenium.webdriver import ActionChains
	driver = webdriver.PhantomJS(executable_path='./phantomjs')

	# 获取节点对象
	photo = driver.find_element_by_id('photo')

	# 通过driver创建Action调用链对象
	action = ActionChains(driver)

	# 移动鼠标到指定的节点
	action.move_to_element(photo).perform()

	# 单击指定节点
	action.move_to_element(photo).click(photo).perform()

	# 双击指定节点
	action.move_to_element(photo).double_click(photo).perform()

	# 右击指定节点
	action.move_to_element(photo).context_click(photo).perform()

	# 左键hold住指定节点
	action.move_to_element(photo).click_and_hold(photo).perform()

	# 把photo节点拖拽到next节点
	action.drag_and_drop(photo, driver.find_element_by_id('next')).perform()

----------------------------
表单填充					|
----------------------------
	* 已经知道了怎样向文本框中输入文字
	* 但是有时候我们会碰到<select> </select>标签的下拉框,直接点击下拉框中的选项不一定可行
	* Selenium专门提供了Select类来处理下拉框
		# 导入 Select 类
		from selenium.webdriver.support.ui import Select

		# 找到 name 的选项卡
		select = Select(driver.find_element_by_name('status'))

		# 根据下拉项的位置选择
		select.select_by_index(1)
		# 根据选项的值选择
		select.select_by_value("0")
		# 根据option标签文本的值选择
		select.select_by_visible_text("未审核")

		# 取消选择
		select.deselect_all()

----------------------------
弹窗处理					|
----------------------------
	* 处理页面的alert弹窗
		alert = driver.switch_to_alert()

----------------------------
页面切换					|
----------------------------
	* 切面页面
		driver.switch_to_window('页面名称')

	* 也可以使用 window_handles 方法来获取每个窗口的操作对象
		for handle in driver.window_handles:
			driver.switch_to_window(handle)

----------------------------
页面前进和后退				|
----------------------------
	driver.forward()	# 前进
	driver.back()		# 后退
	
----------------------------
执行js脚本					|
----------------------------
	driver.execute_script('alert("哈哈")')

----------------------------
cookie						|
----------------------------
	* 获取所有的 cookie
		driver.get_cookies()

	* 获取指定名称的cookie
		driver.get_cookie('JESSIONID')
	
	* 删除cookie
		driver.delete_cookie('name')
	
	* 删除所有cookie
		driver.delete_all_cookies()

	
----------------------------
页面等待					|
----------------------------
	* 网站都几乎有才用ajax异步加载,有些元素是通过异步加载才会被载入dom内存
	* 有两种方式处理这个问题
	* .... ...

	* 显示等待,显示指定某个条件,然后设置最长等待时间,如果超时还未找到元素,抛出异常
		from selenium import webdriver
		from selenium.webdriver.common.by import By
		# WebDriverWait 库,负责循环等待
		from selenium.webdriver.support.ui import WebDriverWait
		# expected_conditions 类负责条件触发
		from selenium.webdriver.support import expected_conditions as expected_conditions

		driver = webdriver.Chrome()
		driver.get("http://www.xxxxx.com/loading")
		try:
			# 页面一直循环，直到 id="myDynamicElement" 出现
			element = WebDriverWait(driver, 10).until(
				expected_conditions.presence_of_element_located((By.ID, "myDynamicElement"))
			)
		finally:
			driver.quit()
	
	* 程序默认会 0.5s 调用一次来查看元素是否已经生成，如果本来元素就是存在的，那么会立即返回。
	* 下面是一些内置的等待条件,可以直接调用这些条件,而不用自己写某些等待条件了
		title_is
		title_contains
		presence_of_element_located
		visibility_of_element_located
		visibility_of
		presence_of_all_elements_located
		text_to_be_present_in_element
		text_to_be_present_in_element_value
		frame_to_be_available_and_switch_to_it
		invisibility_of_element_located
		element_to_be_clickable – it is Displayed and Enabled.
		staleness_of
		element_to_be_selected
		element_located_to_be_selected
		element_selection_state_to_be
		element_located_selection_state_to_be
		alert_is_present

	* 隐式等待比较简单,就是简单地设置一个等待时间,单位为秒
		from selenium import webdriver
		driver = webdriver.Chrome()
		driver.implicitly_wait(10) # seconds
		driver.get("http://www.xxxxx.com/loading")
		myDynamicElement = driver.find_element_by_id("myDynamicElement")
		
		* 当然如果不设置,默认等待时间为0