------------------------
Selector				|
------------------------
	* ֻҪ ServerSocketChannel ���� SocketChannel �� Selector ע�����ض����¼�
	  Selector �ͻ�����Щ�¼��Ƿ���,SelectableChannel �� register() ��������ע���¼�
	  �÷�������һ�� SelectionKey ����,�ö��������ڸ�����Щ��ע���¼��ľ��
	
	* һ�� Selector �����3�����͵� SelectionKey ����
		all-keys
			* ��ǰ������ Selector ע�� SelectionKey ����
			* Selector �� keys() �������ظü���
		
		slected-keys
			* ����¼��Ѿ��� Selector ����,�� SelectionKey ����
			* Selector �� selectedKeys() �������ظü���
		
		cancelled-keys
			* �Ѿ���ȡ���� SelectionKey �ļ���
			* Selector û���ṩ�������ּ��ϵķ���
	
	* �� SelectableChannel ִ�� register() ����ʱ,�÷������½�һ�� SelectionKey ,���������뵽 Selector �� all-keys ������
	
	* ����ر��� SelectionKey ��������� Channel ����,���ߵ����� SelectionKey ����� cancel() ����
	  ��� SelectionKey ����ͻᱻ���뵽 cancelled-key ������,��ʾ��� SelectionKey �Ѿ���ȡ��
	  ����ִ����һ�� Selector �� select() ����ʱ,��ȡ���� SelectionKey ���󽫴����м�����ɾ��
	
	* �� Selector ִ�� select() ������ʱ��,����� SelectionKey ��ص��¼�������,��� SelectionKey �ͻᱻ���뵽:selected-keys ������
	  ����ֱ�ӵ��� selected-keys ���ϵ� remove() ����,���ߵ������� Iterator ��remove() ���������Դ� selected-keys ������ɾ��һ�� SelectionKey ����
	  ��������,ֱ��ͨ�����ϼ��ϵ� remove() ����,ɾ�� all-keys �����е� SelectionKey,���������ͼ��ô��,���׳��쳣:UnsupportedOperationException

------------------------
Selector-api			|
------------------------
	public static Selector open()

	void close()
	boolean isOpen()
		* �ж� Selector �Ƿ��ڴ�״̬
		* ������ Selector ʵ����,���Ǵ�״̬,������ close() ����,�ͽ����˹ر�״̬

	Set<SelectionKey> keys()��
		* ���� Selector �� all-keys ����,������ Selector ������ SelectionKey ����

	SelectorProvider provider()
	int select()
	int select(Consumer<SelectionKey> action, long timeout)
	int select(Consumer<SelectionKey> action) throws IOException
	int select(long timeout)
		* select() �������������Ĺ�����ʽ,��������¼��Ѿ������� SelectionKey ������Ŀ
		* ���һ����û��,����������,ֱ�������������֮һ,���� select() �����з���
			1,������һ�� SelectionKey ������¼��Ѿ�����
			2,�����̵߳����� Selector �� wakeup() ����,���� select() �������߳�,������ select() ��������
			3,��ǰִ�� select()�������߳�,�������߳��ж�
			4,�����˵ȴ�ʱ��,��λ�Ǻ���,����ȴ���ʱ,����������,�����׳��쳣
			  �����������û�г�ʱ������select(),�÷������߳̾ͻ��������״̬,��Զ������Ϊ��ʱ���ж�

	Set<SelectionKey> selectedKeys()
	int selectNow(Consumer<SelectionKey> action) throws IOException
	int selectNow()
		* ��������¼��Ѿ����͵�  SelectionKey ������Ŀ
		* �÷������÷������Ĺ�����ʽ,���ص�ǰ����¼��Ѿ������� SelectionKey ������Ŀ
		* ���û��,��������0
	

	Selector wakeup()
		* ����ִ�� Selector �� select() ����(ͬ�������� select(long timeOut))���߳�
		* �߳�Aִ���� Selector ����� wakeup() ����,����߳�B����ִ��ͬһ�� Selector ����� select() ����ʱ
		  ����B�̹߳�һ���ִ����� Selector ����� select() ����,��ôB�߳���ִ��select() ����ʱ,�������� select() �����з���
		* wakeup() ֻ�ܻ���ִ��select()�������߳�Bһ��,����߳�B��ִ��select()����ʱ������,�Ժ���ִ��select()���ᱻ����
		  �����߳�A�ٴ�ִ�� Selector ����� wakeup() ����
		 



	