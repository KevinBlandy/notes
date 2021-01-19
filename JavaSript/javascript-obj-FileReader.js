-----------------------
FileReader				|
-----------------------
	# H5的新东西


-----------------------
FileReader-属性			|
-----------------------
	result 
		* 结果

-----------------------
FileReader-方法			|
-----------------------
	readAsBinaryString	
		* 参数file	将文件读取为二进制编码

	readAsText	
		* 参数:file,[encoding]	
		* 将文件读取为文本

	readAsDataURL	
		* 参数:file	
		* 将文件读取为DataURL,其实就是 Base64 编码

	abort	
		* 终端读取操作
	
-----------------------
FileReader-事件			|
-----------------------
	onabort	
		* 中断
	onerror	
		* 出错
	onloadstart	
		* 开始
	onprogress	
		* 正在读取
	onload	
		* 成功读取
	onloadend	
		* 读取完成，无论成功失败
	
	# event 属性/方法
		

-----------------------
FileReader-demo			|
-----------------------
	//读取本地文件并显示
	//获取文件
	var file = document.getElementById("file").files[0];
	var reader = new FileReader();	//创建 FileReader 对象
	//添加事件
	reader.onload = function(e){ 
		//获取到读取后的结果:this.result
		result.innerHTML = '<img src="'+this.result+'" alt=""/>' 
	} 
	reader.readAsDataURL(file);		//读取文件