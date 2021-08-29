---------------------------
������ȷ��
---------------------------
	# Ĭ������·�����Ϣ�Ĳ����ǲ��᷵���κ���Ϣ�������ߵ�
		* Ҳ����Ĭ��������������ǲ�֪����Ϣ��û����ȷ�ص��������
	
	# Rabbitmq������������2���������
		1. ����
		2. ȷ��

	# ��2������������ܹ��棬һ����ֻ��ʹ��һ��


---------------------------
�������
---------------------------
	# ���񷽷�
		public abstract SelectOk txSelect()
		public abstract RollbackOk txRollback()
		public abstract CommitOk txCommit()
	
	# ʵ��
		public static void transaction (Channel channel) throws IOException {
			try {
				// ��ʼ����
				SelectOk selectOk = channel.txSelect();
				// ������Ϣ
				channel.basicPublish("my-exchange", "order", null, "Hello".getBytes());
				// �ύ����
				CommitOk commitOk = channel.txCommit();
			} catch (Throwable e) {
				// �ع�����
				RollbackOk rollbackOk = channel.txRollback();
				throw e;
			}
		}
	
	# ���ַ�ʽ���������⣬�����ؽ���MQ����������������ʹ��
	# �ڼ�Ⱥ�����У�ֻ�е�ǰ������ȫ������queue��ִ��֮�󣬿ͻ��˲Ż��յ�Tx.CommitOk����Ϣ

---------------------------	
���ͷ�ȷ��
---------------------------
	# ȷ��ģʽ
		* �����߽��ŵ����ó�ȷ��ģʽ����ô�����ڸ��ŵ����淢������Ϣ���ᱻָ��һ��Ψһ��ID���ӿ�0ʼ
		* ��Ϣ������к󣬻�������߷���ȷ����Ϣ(Basic.Ack)
			* �����Ϣ�ǿɳ־û��ģ���ôȷ����Ϣ����д����̺󷢳�
		* ȷ����Ϣ�а�������Ϣ�����

	# ��Ϣȷ��ģʽ��ͬ��
		public static void config (Channel channel) throws Throwable {
			try {
				// ��ʼȷ��ģʽ
				Confirm.SelectOk selectOk = channel.confirmSelect();
				// ������Ϣ
				channel.basicPublish("my-exchange", "order", null, "Hello".getBytes());
				// ������ֱ����ȡMQ��ȷ����Ϣ
				if (!channel.waitForConfirms()) {
					// TODO ����ʧ�ܣ�ִ�д���
				}
			} catch (Throwable e) {
				// �ع�����
				throw e;
			}
		}
	
	
	# �ڼ�Ⱥ��������publisher���е�ǰmessageȷ�ϵ�ǰ���Ǹ�message��ȫ�������������ˡ�