------------------------
WebSocket-js			|
------------------------
	# ����Ȼ����JS�Ķ���
	# ���������Ƿ�֧��WebSocket
		function loadDemo() {  
			if (window.WebSocket) {  
				//֧��  
			} else {  
				//��֧��
			}  
		}  
	

------------------------
WebSocket-WebSocket		|
------------------------
	# ���� WebSocket ����
		var Socket = new WebSocket(url,[protocol]);
			* url:��ʾ���ӵĵ�ַ,ͨ����Э��:"ws://"����"wss://"(��ȫ��websocket)Э�鿪ͷ
			* protocol:��һ������,���Խ���һ�����߶����Э��,�ò��������Ǳ����
	
	# ����
		readyState	
			* ֻ������ readyState ��ʾ����״̬������������ֵ
			CONNECTING	:ֵΪ0,��ʾ��������
			OPEN		:ֵΪ1,��ʾ���ӳɹ�������ͨ����
			CLOSING		:ֵΪ2,��ʾ�������ڹر�
			CLOSED		:ֵΪ3,��ʾ�����Ѿ��ر�,���ߴ�����ʧ��
			
		bufferedAmount	
			* ֻ������ bufferedAmount �ѱ� send() �������ڶ����еȴ�����,���ǻ�û�з����� UTF-8 �ı��ֽ���.
			* �����ж���Ϣ�Ƿ������
				if (socket.bufferedAmount === 0) {
				  // �������
				} else {
				  // ���ͻ�û����
				}
		
		binaryType 
			* ָ����������������
			* ö��ֵ(�ַ���)
				binaryType = "blob";
				binaryType = "arraybuffer";
			
	# �¼�
		* �¼�������,������һ�� event ����
			Socket.onopen	
				* ���ӽ���ʱ����
				*  event����
					var protocol = event.target.protocol

			Socket.onmessage	
				* �ͻ��˽��շ��������ʱ����
				* event����
					var data = event.data;

				* �ж���Ϣ����
					if(typeof event.data === String) {
						console.log("Received data string");
					}
					if(event.data instanceof ArrayBuffer){
						console.log("Received arraybuffer");
					}

			Socket.onerror	
				* ͨ�ŷ�������ʱ����

			Socket.onclose	
				* ���ӹر�ʱ����
				* event����
					var code = event.code;
					var reason = event.reason;
					var wasClean = event.wasClean;

		* eventͨ������
			
	
	# ����
		Socket.send()	
			* ʹ�����ӷ�������
			* ���Ͷ���������
				var file = document.querySelector('input[type="file"]').files[0];
				ws.send(file);

		Socket.close()	
			* �ر�����
			* �ڴ��ڹر�֮ǰ,�͹رյ�socket����,�п��ܻᵼ�·��������쳣
				window.onbeforeunload = function () {  
					ws.close();  
				}  
		

------------------------
WebSocket-WebSocket		|
------------------------
״̬��		����					����
0�C999		-						������, δʹ�á�
1000		CLOSE_NORMAL			�����ر�; ����Ϊ��Ŀ�Ķ�����, �����Ӷ��ѳɹ��������
1001		CLOSE_GOING_AWAY		�ն��뿪, ������Ϊ����˴���, Ҳ������Ϊ��������Ӵ����ӵ�ҳ����ת�뿪��
1002		CLOSE_PROTOCOL_ERROR	����Э�������ж����ӡ�
1003		CLOSE_UNSUPPORTED		���ڽ��յ���������������Ͷ��Ͽ����� (��������ı����ݵ��ն˽��յ��˶���������)��
1004		-						������ ��������ܻ���δ�����塣
1005		CLOSE_NO_STATUS			������ ��ʾû���յ�Ԥ�ڵ�״̬�롣
1006		CLOSE_ABNORMAL			������ ���������յ�״̬��ʱ���ӷ������ر� (Ҳ����˵, û�з��͹ر�֡)��
1007		Unsupported Data		�����յ��˸�ʽ���������ݶ��Ͽ����� (���ı���Ϣ�а����˷� UTF-8 ����)��
1008		Policy Violation		�����յ�������Լ�������ݶ��Ͽ����ӡ� ����һ��ͨ��״̬��, ���ڲ��ʺ�ʹ�� 1003 �� 1009 ״̬��ĳ�����
1009		CLOSE_TOO_LARGE			�����յ����������֡���Ͽ����ӡ�
1010		Missing Extension		�ͻ��������������̶�һ��������չ, ��������û�д���, ��˿ͻ��˶Ͽ����ӡ�
1011		Internal Error			�ͻ�����������û��Ԥ�ϵ������ֹ���������, ��˷���˶Ͽ����ӡ�
1012		Service Restart			�����������������Ͽ����ӡ� [Ref]
1013		Try Again Later			������������ʱԭ��Ͽ�����, �������������˶Ͽ�һ���ֿͻ������ӡ� [Ref]
1014		-	�� WebSocket		��׼�����Ա�δ��ʹ�á�
1015		TLS Handshake			������ ��ʾ���������޷���� TLS ���ֶ��ر� (�����޷���֤������֤��)��
1016�C1999	-						�� WebSocket ��׼�����Ա�δ��ʹ�á�
2000�C2999	-						�� WebSocket ��չ����ʹ�á�
3000�C3999	-						�����ɿ����ʹ�á� ��Ӧ��Ӧ��ʹ�á� ������ IANA ע��, �ȵ��ȵá�
4000�C4999	-						������Ӧ��ʹ�á�



------------------------
WebSocket-WebSocket
------------------------
let websocket = new WebSocket("ws://localhost:8000/channel/demo");
websocket.onclose = e => {
   console.log(`���ӹر�: code=${e.code}, reason=${e.reason}, wasClean=${e.wasClean}`)
}
websocket.onmessage = e => {
    console.log(`�յ���Ϣ:${e.data}`);
}
websocket.onerror = e => {
    console.log(`�����쳣`)
    console.error(e)
}
websocket.onopen = e => {
    console.log(`���Ӵ�: ${e}`);
}