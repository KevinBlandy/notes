


SpringMVC默认对POJO数据进行回显
	1,框架会自动把POJO的数据,放到request,key.就是POJO首字母小写


	@ModelAttribute 可以指定POJO回显到页面,在request中的 key.
	
	@ModelAttribute(value="items")
	它还可以把方法的返回值,也传递到页面.
	
	