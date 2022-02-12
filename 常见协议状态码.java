----------------------------
http
----------------------------

	�ɹ���Ӧ
		200  OK(�ɹ�)
		201  Created(�Ѵ���)
		202  Accepted(�Ѵ���)
		203  Non-Authoritative Information(δ��Ȩ��Ϣ)
		204  No Content(������)
		205  Reset Content(��������)
		206  Partial Content(��������)
	�ض���
		300  Multiple Choice(����ѡ��)
		301  Moved Permanently(�����ƶ�)
		302  Found(��ʱ�ƶ�)
		303  See Other(�鿴����λ��)
		304  Not Modified(δ�޸�)
		305  Use Proxy(ʹ�ô���)
		306  unused (δʹ��)
		307  Temporary Redirect(��ʱ�ض���)
		308  Permanent Redirect(�����ض���)
	�ͻ��˴���
		400  Bad Request(��������)
		401  Unauthorized(δ��Ȩ)
		402  Payment Required(��Ҫ����)
		403  Forbidden(��ֹ����)
		404  Not Found(δ�ҵ�)
		405  Method Not Allowed(������ʹ�ø÷���)
		406  Not Acceptable(�޷�����)
		407  Proxy Authentication Required(Ҫ����������֤)
		408  Request Timeout(����ʱ)
		409  Conflict(��ͻ)
		410  Gone(��ʧЧ)
		411  Length Required(��Ҫ���ݳ���ͷ)
		412  Precondition Failed(Ԥ����ʧ��)
		413  Request Entity Too Large(����ʵ�����)
		414  Request-URI Too Long(������ַ����)
		415  Unsupported Media Type(ý�����Ͳ�֧��)
		416  Requested Range Not Satisfiable(����Χ����Ҫ��)
		417  Expectation Failed(Ԥ�ڽ��ʧ��)
		451  ����ԭ�򲻿��ã�Ӣ�HTTP 451 Unavailable For Legal Reasons����һ��HTTPЭ��Ĵ���״̬���룬���û��������ĳ����������˵Ȳ�˷������϶����Ϸ�����Դʱ���ͻ���ʾ���������롣
	�������˴���
		500  Internal Server Error(�ڲ�����������)
		501  Implemented(δʵ��)
		502  Bad Gateway(���ش���)
		503  Service Unavailable(���񲻿���)
		504  Gateway Timeout (���س�ʱ)
		505  HTTP Version Not Supported(HTTP �汾����֧��)

----------------------------
websocket
----------------------------
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
	1014		-						�� WebSocket ��׼�����Ա�δ��ʹ�á�
	1015		TLS Handshake			������ ��ʾ���������޷���� TLS ���ֶ��ر� (�����޷���֤������֤��)��
	1016�C1999	-						�� WebSocket��׼�����Ա�δ��ʹ�á�
	2000�C2999	-						�� WebSocket��չ����ʹ�á�
	3000�C3999	-						�����ɿ����ʹ�á���Ӧ��Ӧ��ʹ�á� ������ IANA ע��, �ȵ��ȵá�
	4000�C4999	-						������Ӧ��ʹ�á�
