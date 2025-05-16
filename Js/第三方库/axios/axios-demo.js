------------------
Demo
------------------
	# 总结
		1. 查询参数，必须要通过 config 对象的 params 设置
		2. JSON、表单、文件请求可以直接使用对象、 URLSearchParams、 FormData 对象作为第二个参数
		3. 如果需要自定义 Header、查询参数，则可以提供第三个参数 config 对象为配置属性

		
	# 前置
		import axios from 'axios';
		const httpClient = axios.create();
	
	
	# 查询参数
				
		// 查询参数
		const queryParams = new URLSearchParams();
		queryParams.append('keywords', '重庆');

		// 使用 get 方法调用，设置 params 属性
		httpClient.get('/member.json', { params: queryParams }).then(resp => console.log(resp.data));
		// 直接调用，设置 params 属性
		httpClient('/member.json', { params: queryParams }).then(resp => console.log(resp.data));

	# JSON
		// 使用 post 方法调用，第二个参数直接就是 JSON 对象
		// 会自动添加 application/json ContentType 请求头
		httpClient.post('/member.json', {"account": "admin", "password": "*****"}).then(resp => console.log(resp.data));


	# 表单
		// 使用 URLSearchParams 构建表单
		const form = new URLSearchParams();
		form.append('account', 'admin');
		form.append('password', '******');

		// 使用 URLSearchParams （表单）作为 post 方法第二个参数
		// 会自动添加 application/x-www-form-urlencoded;charset=UTF-8 Header
		httpClient.post('/member.json', form).then(resp => console.log(resp.data));
	
	
	# 文件上传
	
		// FormData 表单
		const form = new FormData();
		form.append("config",  new Blob([JSON.stringify({'with': 200, 'height': 200})], {type: 'application/json'}));

		// 使用表单作为第二个参数
		// 会自动添加 Multipart 请求头
		httpClient.post('/member.json', form).then(resp => console.log(resp.data));
	
	