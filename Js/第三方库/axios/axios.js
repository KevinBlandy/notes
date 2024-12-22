----------------------
axios
----------------------
	# 基于 promise 的 Web 请求库
		* https://axios-http.com/zh/docs/intro

	
	# 使用
		// 导入
		import axios from 'axios';
		
		// 直接使用默认的
		axios.get('/config.json');
		
		// 创建自定义实例，指定配置
		const httpClient = axios.create({
		  baseURL: 'https://api.example.com'
		});
		
		// 创建实例后修改配置
		httpClient.defaults.headers.common['Authorization'] = AUTH_TOKEN;
		
		// 发起请求时候，修改配置（覆盖一切）
		httpClient.get('/foo', {
			 timeout: 5000
		});
	

----------------------
异常处理
----------------------
	axios.get('/user/12345')
	  .catch(function (error) {
		if (error.response) {
		  // 请求成功发出且服务器也响应了状态码，但状态代码超出了 2xx 的范围
		  console.log(error.response.data);
		  console.log(error.response.status);
		  console.log(error.response.headers);
		} else if (error.request) {
		  // 请求已经成功发起，但没有收到响应
		  // `error.request` 在浏览器中是 XMLHttpRequest 的实例，
		  // 而在node.js中是 http.ClientRequest 的实例
		  console.log(error.request);
		} else {
		  // 发送请求时出了点问题
		  console.log('Error', error.message);
		}
		// 可以访问配置信息
		console.log(error.config);
		// 可以获取更多关于HTTP错误的信息。
		console.log(error.toJSON());
	  });


----------------------
取消请求
----------------------

----------------------
拦截器机制
----------------------
	# 请求拦截器 request.use
	
		// 创建自定义的实例
		const httpClient = axios.create();
		
		// 统一的异常处理器
		const errorHandler = err => Promise.reject(err);

		// 拦截器 1
		const requestLogInterceptor = config => {
			config.headers['X-Request-Id'] = 'FFFFFFFFFFFF';
			return config;
		}
		
		// 拦截器 2
		const authTokenInterceptor = config => {
			config.headers['X-Auth-Token'] = '123456';
			return config;
		}
	
		// 多次调用，添加多个。后加的先执行！
		// 返回的是拦截器在队列中的的索引
		const index1 = httpClient.interceptors.request.use(authTokenInterceptor, errorHandler);
		const index2 = httpClient.interceptors.request.use(requestLogInterceptor, errorHandler);
		
		// 可以根据返回的索引值，移除指定的拦截器
		httpClient.interceptors.request.eject(index1);
	
	# 响应拦截器 response.use
		// 2xx 范围内的状态码都会触发拦截方法
		const timestampAppender = response => {
			response.data['timestamp'] = new Date().getTime();
			return response
		}
		const responseTimeAppender = response => {
			response.data['responseTime'] = '15ms';
			return response
		}


		// 添加多个响应拦截器，后加的后执行。
		// 返回的是拦截器在队列中的的索引
		const index1 = httpClient.interceptors.response.use(timestampAppender, errorHandler);
		const index2 = httpClient.interceptors.response.use(responseTimeAppender, errorHandler);

		
		// 同样，也是通过索引移除拦截器
		httpClient.interceptors.response.eject(index1);

	