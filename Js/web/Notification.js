------------------------------------------
Notification extends EventTarget
------------------------------------------
	# 接口用于向用户配置和显示桌面通知
		https://developer.mozilla.org/zh-CN/docs/Web/API/Notification
	
	# 构造函数
		new Notification(title, options)

		title
			* 通知的标题，它将显示在通知窗口的顶部。

		options
			* 可选，应用于通知的任何自定义设置的选项对象。可选的选项有：

			dir
				* 显示通知的方向。它默认为 auto，即只采用浏览器的语言设置行为，但你可以通过设置 ltr 和 rtl 的值来覆盖该行为（尽管大多数浏览器似乎忽略这些设置）。

			lang
				* 指定通知的语言，根据 RFC 5646: 识别语言的标签（也被称为 BCP47）使用表示语言标签的字符串指定。

			badge
				* 一个包含图像 URL 的字符串，用于在没有足够空间显示通知本身时表示通知。

			body
				* 一个表示通知正文的字符串，显示在标题下方，默认值是一个空字符串。

			tag
				* 一个表示通知的识别标签的字符串，默认值是一个空字符串。

			icon
				* 一个包含要在通知中显示的图标的 URL 的字符串。

			image
				* 一个包含要在通知中显示的图像的 URL 的字符串。

			data
				* 任意你想要与通知关联的数据。它可以是任何数据类型。默认值为 null。

			vibrate
				* 设备振动硬件随通知一起发出的振动模式。当该值被指定时，silent 参数不得设置为 true。

			timestamp
				* 一个表示通知创建或适用的时间（过去、现在或将来）的数字。

			renotify
				* 一个布尔值，指定在新通知替换旧通知后是否应通知用户。
				* 默认值为 false，这意味着他们不会收到通知。如果该值被指定为 true，那么必须同时设置 tag 参数。

			requireInteraction
				* 指示通知应保持活动状态，直到用户单击或关闭它，而不是自动关闭。默认值为 false。
		
			actions
				* 要在通知中显示的一系列操作的数组，默认为一个空数组。数组中的每个元素都是一个具有以下成员的对象：
					action
						* 一个标识要在通知上显示的用户操作的字符串。

					title
						* 一个包含要向用户显示的操作文本的字符串。

					icon
						* 一个包含与操作一起显示的图标 URL 的字符串。
				
				* 使用 notificationclick 事件中的 event.action 构建适当的响应。

			silent
				* 布尔值，指定通知是否静音（不发出声音或振动），无论设备设置如何。
				* 默认值为 null。如果被设置为 true，那么不能同时存在 vibrate 参数。
				
------------------------------------------
this
------------------------------------------
	actions
	badge
	body
	data
	dir
	icon
	image
	lang
	renotify
	requireInteraction
	silent
	tag
	timestamp
	title
	vibrate
	close()

------------------------------------------
event
------------------------------------------
	click
	close
	error
	show

------------------------------------------
static
------------------------------------------
	maxActions
	permission
	requestPermission()
