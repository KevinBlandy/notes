---------------------------------
IO模式							 |
---------------------------------
	NIO			
		* io.netty.channel.socket.nio	
		* 基于java.nio.channels的工具包,使用选择器作为基础的方法。
	OIO			
		* io.netty.channel.socket.oio	
		* 基于java.net的工具包,使用阻塞流

	Local		
		* io.netty.channel.local
		* 用来在虚拟机之间本地通信

	Embedded	
		* io.netty.channel.embedded	嵌入传输,它允许在没有真正网络的传输中使用 ChannelHandler
		* 可以非常有用的来测试ChannelHandler的实现
	
