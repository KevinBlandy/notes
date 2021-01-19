------------------------
1,Bootstrap-入门		|
------------------------
	# 首先,这东西是一个前段框架
	# 学习Bootstrap基础
		html
		css
		js
	# 响应式布局
		其实就是,这个框架开发的东西可以适用电脑屏幕,可以对付手机,平板等设备
	# 移动设备优先
		框架里面的样式,优先考虑到了移动设备

				
		
------------------------
2,Bootstrap-目录结构	|
------------------------
	css			
		|-bootstrap.css				//未压缩的CSS
		|-bootstrap.min.css			//压缩过的CSS
		|-
	fonts		//字体库文件
	js
		|-bootstrap.js
		|-bootstrap.min.js
		|-npm.js


------------------------
3,Bootstrap-环境安装	|
------------------------
	1,下载
		http://v3.bootcss.com/
		* 用于生产环境的Bootstrap

	2,导入文件
		* bootstrap.min.css
		* bootstrap.min.js

	3,导入Jquery
		* 必须要加载Jquery(这东西就是基于Jquery来做的)

	4,HTML文档定义
		* 框架文档是基于Html5,所有要使用Html5的文档类型定义(DTD)
		* <!DOCTYPE html>

	5,部分头部设置
		<!DOCTYPE html>
		<html lang="en">
			<head>
				<meta charset="UTF-8">			
				<!-- 响应式 -->
				<meta name="viewport" content="width=device-width, initial-scale=1">			
					width			
						* viewport的宽度 
					height			
						* viewport的高度
					initial-scale	
						* 初始的缩放比例
					minimum-scale	
						* 允许用户缩放到的最小比例
					maximum-scale	
						* 允许用户缩放到的最大比例
					user-scalable	
						* 用户是否可以手动缩放
						* 枚举值:no/yes
				<meta http-equiv="X-UA-Compatible" content="IE=edge">
			<head>
		<html/>
	

		
	
