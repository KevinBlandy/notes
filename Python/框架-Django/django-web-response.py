--------------------------------
HTML模版渲染					|
--------------------------------
	* 使用 django.shortcuts.render 进行模版渲染
		from django.shortcuts import render
		return render(request,'root/tempalte.html',{'param':''})
		* request 对象是必须的
	
	* 使用 django.shortcuts.render_to_response 进行模版渲染
		from django.shortcuts import render_to_response
		return render_to_response('root/tempalte.html',{'param':''})
		* 没啥特别的地方,只是它允许少传一个 request 对象而已
		* 目前有个小BUG,无法渲染模版中的:{%csrf_token%},如果添加额外的参数来修复
			return render_to_response('root/tempalte.html',{'param':''},context_instance=RequestContext(request))
	
	* 可以使用 locals() 全局函数,快捷的传递本地变量(当前函数中的变量)
		from django.shortcuts import render
		return render(request,'root/tempalte.html',locals())
		* locals(),返回当前上下文中的所有变量名 & 值(dict)

--------------------------------
JSON响应						|
--------------------------------
	* 使用 django.shortcuts.HttpResponse 函数进行JSON字符串响应
		from django.shortcuts import HttpResponse
		return HttpResponse(json.dumps({'name':'KevinBlandy','age':23}))
	
	* HttpResponse 还可以响应二进制的图片信息
		def get_image(request):
			with open('static/imgs/16.jpg','rb') as fp:
				data = fp.read()
				return HttpResponse(data)

--------------------------------
响应头							|
--------------------------------

--------------------------------
Cookie							|
--------------------------------

--------------------------------
文件下载						|
--------------------------------

--------------------------------
重定向							|
--------------------------------
	* 使用 django.shortcuts.redirect 进行重定向
		from django.shortcuts import HttpResponse,render,render_to_response,redirect
		def index(request):
			return redirect("http://www.baidu.com")