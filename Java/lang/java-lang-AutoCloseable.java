--------------------
AutoCloseable		|
--------------------
	# 自动关闭资源接口
	# 实现这个接口,那么在 try(){}catch{} 中就可以实现自动关闭资源
	# Demo
		AsynchronousServerSocketChannel 实现了该接口

		try(AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open()){
			
		}catch(Exception e){

		}