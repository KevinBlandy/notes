--------------------------------
�쳣����
--------------------------------
	# �첽��Ϣ������
		* �ڴ�����Ϣ��ʱ���п��ܻ�����׳��쳣����ΪSTOMP��Ϣ�첽���ص㣬�����߿�����ԶҲ����֪�������˴���
		* @MessageExceptionHandler ��ע�ķ����ܹ�������Ϣ���������׳����쳣��
		* ���ǿ��԰Ѵ����͸��û��ض���Ŀ�ĵ��ϣ�Ȼ���û��Ӹ�Ŀ�ĵ��϶�����Ϣ���Ӷ��û�����֪���Լ�������ʲô����

	
	# Demo
		@MessageExceptionHandler(Exception.class)
		@SendToUser("/queue/errors")
		public Exception handleExceptions(Exception t){
			t.printStackTrace();
			return t;
		}
	