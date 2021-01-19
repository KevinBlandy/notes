重定向是利用服务器返回http消息的状态码实现的！客户端请求服务器的时候。服务器会返回一个状态码。
服务端通过HttpServletResponse的setStatus(int类型状态码);方法设置状态码.
301//永久性定向
302//临时性定向
response.setStatus(302);
	|--设置返回的状态码为302，告诉浏览器你要重新定位资源。
response.setHeader("Location","http://www.baidu.com");
	|--设置让浏览器重新定位的资源。
/**重定向，设置状态码302，设置Location头。其中变化的只有Location所以JAVA提供了更为简洁的方法来完成重定向**/
sendRedirect(String location);//设置重定向连接。内部其实封装了-设置302状态码,把参数String设置为location头.一步到位。
