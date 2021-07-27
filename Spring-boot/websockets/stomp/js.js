------------------------
js 客户端
------------------------
	# 需要的库
		sockjs
			https://github.com/sockjs/sockjs-client

		stomp
			http://jmesnil.net/stomp-websocket/doc/

		
		<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js"></script>

------------------------
常用Demo
------------------------
	//使用SockJS和stomp.js来打开“gs-guide-websocket”地址的连接，这也是我们使用Spring构建的SockJS服务。
    var socket = new SockJS('/gs-guide-websocket');
    var stompClient = Stomp.over(socket);
    
    //若使用STOMP 1.1 版本，默认开启了心跳检测机制（默认值都是10000ms）
    stompClient.heartbeat.outgoing = 10000;
 	//客户端不从服务端接收心跳包
    stompClient.heartbeat.incoming = 0; 
    
 	// 创建链接，设置header，连接成功的回调
    stompClient.connect({  // 链接Header
    	username: "KevinBlandy",
    	password: "12345"
    }, function (frame) {
    	
        console.log('Connected: ' + frame); // CONNECTED
        
        //订阅一个地址，设置消息监听，header
        stompClient.subscribe('/topic/greetings', function (response) {
            //收到消息时的回调方法，展示欢迎信息。
            console.log("收到消息：");
            // 通过body获取到消息数据
            console.log(JSON.parse(response.body));
        }, {foo: "foo"});  
    });
    
	//断开连接的方法
	function disconnect() {
		stompClient.disconnect(function(){
			// 关闭是异步的，这个方法表示链接已经关闭回调 
		});
	}

	// 发送数据
	function send() {
		// 发送数据到指定的地点，可以设置header，body
	    stompClient.send("/app/hello", {bar: "bar"}, JSON.stringify({'name': $("#name").val()}));
	}

------------------------
客户端的消息确认
------------------------
	var subscription = client.subscribe("/queue/test",
		function(message) {
			// TODO 处理消息
			// 发送ACK，表示已经消费
			message.ack();
			// 发送NACK 表示消费失败
			// message.nack();
		},
		{ack: 'client'} // 订阅的时候，设置客户端消息确认模式
	);


------------------------
事务消息
------------------------
	  // 开始事务
	  var tx = client.begin();
	  // 发送消息
	  client.send("/queue/test", {transaction: tx.id}, "message in a transaction");
	  // 提交事务
	  tx.commit();
	  // 取消事务
	  // tx.abort();


	  * {transaction: tx.id} header是必选的，如果不填写，那么消息会被立即发送出去
	  * 就会导致 abort() 失败
