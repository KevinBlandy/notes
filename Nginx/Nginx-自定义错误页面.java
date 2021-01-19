------------------------
Nginx-自定义错误页面	|
------------------------
	error_page 500 502 503 504 /50x.html
	location = /50x.html{
		root html;
	}