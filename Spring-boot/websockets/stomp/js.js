------------------------
js �ͻ���
------------------------
	# ��Ҫ�Ŀ�
		sockjs
			https://github.com/sockjs/sockjs-client

		stomp
			http://jmesnil.net/stomp-websocket/doc/

		
		<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js"></script>

------------------------
����Demo
------------------------
	//ʹ��SockJS��stomp.js���򿪡�gs-guide-websocket����ַ�����ӣ���Ҳ������ʹ��Spring������SockJS����
    var socket = new SockJS('/gs-guide-websocket');
    var stompClient = Stomp.over(socket);
    
    //��ʹ��STOMP 1.1 �汾��Ĭ�Ͽ��������������ƣ�Ĭ��ֵ����10000ms��
    stompClient.heartbeat.outgoing = 10000;
 	//�ͻ��˲��ӷ���˽���������
    stompClient.heartbeat.incoming = 0; 
    
 	// �������ӣ�����header�����ӳɹ��Ļص�
    stompClient.connect({  // ����Header
    	username: "KevinBlandy",
    	password: "12345"
    }, function (frame) {
    	
        console.log('Connected: ' + frame); // CONNECTED
        
        //����һ����ַ��������Ϣ������header
        stompClient.subscribe('/topic/greetings', function (response) {
            //�յ���Ϣʱ�Ļص�������չʾ��ӭ��Ϣ��
            console.log("�յ���Ϣ��");
            // ͨ��body��ȡ����Ϣ����
            console.log(JSON.parse(response.body));
        }, {foo: "foo"});  
    });
    
	//�Ͽ����ӵķ���
	function disconnect() {
		stompClient.disconnect(function(){
			// �ر����첽�ģ����������ʾ�����Ѿ��رջص� 
		});
	}

	// ��������
	function send() {
		// �������ݵ�ָ���ĵص㣬��������header��body
	    stompClient.send("/app/hello", {bar: "bar"}, JSON.stringify({'name': $("#name").val()}));
	}

------------------------
�ͻ��˵���Ϣȷ��
------------------------
	var subscription = client.subscribe("/queue/test",
		function(message) {
			// TODO ������Ϣ
			// ����ACK����ʾ�Ѿ�����
			message.ack();
			// ����NACK ��ʾ����ʧ��
			// message.nack();
		},
		{ack: 'client'} // ���ĵ�ʱ�����ÿͻ�����Ϣȷ��ģʽ
	);


------------------------
������Ϣ
------------------------
	  // ��ʼ����
	  var tx = client.begin();
	  // ������Ϣ
	  client.send("/queue/test", {transaction: tx.id}, "message in a transaction");
	  // �ύ����
	  tx.commit();
	  // ȡ������
	  // tx.abort();


	  * {transaction: tx.id} header�Ǳ�ѡ�ģ��������д����ô��Ϣ�ᱻ�������ͳ�ȥ
	  * �ͻᵼ�� abort() ʧ��
