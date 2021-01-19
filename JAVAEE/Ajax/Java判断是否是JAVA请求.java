
	String header = request.getHeader("x-requested-with");
	//如果 header == XMLHttpRequest,就是AJAX请求
	//如果不是  header 为空,则不是 ajax
	
	