--------------------
��Դ
--------------------
		
	# ע�⣺SphU.entry(xxx) ��Ҫ�� entry.exit() �����ɶԳ��֣�ƥ����ã�����ᵼ�µ�������¼�쳣���׳� ErrorEntryFreeException �쳣��
		* �����Ĵ���
			�Զ������ֻ���� SphU.entry()��û�е��� entry.exit()
			˳����󣬱��磺entry1 -> entry2 -> exit1 -> exit2��Ӧ��Ϊ entry1 -> entry2 -> exit2 -> exit1
		
		* ��ʽ���Ƚ����
			func(func(func()))
		
	# try catch ��ʽ����Դ
		// 1.5.0 �汾��ʼ�������� try-with-resources ���ԣ�ʹ�������ƣ�
		// ��Դ����ʹ��������ҵ��������ַ��������緽�������ӿ�����������Ψһ��ʶ���ַ�����
		try (Entry entry = SphU.entry("resourceName")) {
		  // ��������ҵ���߼�
		  // do something here...
		} catch (BlockException ex) {
		  // ��Դ������ֹ���������򱻽���
		  // �ڴ˴�������Ӧ�Ĵ������
		}
	
	# �ֶ�exit
		Entry entry = null;
		// ��ر�֤ finally �ᱻִ��
		try {
		  // ��Դ����ʹ��������ҵ��������ַ�����ע����Ŀ����̫�ࣨ���� 1K����������ǧ����Ϊ�����������Ҫֱ����Ϊ��Դ��
		  // EntryType �����������ͣ�inbound/outbound��������ϵͳ����ֻ�� IN ���͵������Ч
		  entry = SphU.entry("�Զ�����Դ��");
		  // ��������ҵ���߼�
		  // do something...
		} catch (BlockException ex) {
		  // ��Դ������ֹ���������򱻽���
		  // ������Ӧ�Ĵ������
		} catch (Exception ex) {
		  // ����Ҫ���ý���������Ҫͨ�����ַ�ʽ��¼ҵ���쳣
		  // �쳣���������ҵ���쳣���� Sentinel ��������������쳣��BlockException������Ч��Ϊ��ͳ���쳣�������쳣������Ҫͨ�� Tracer.trace(ex) ��¼ҵ���쳣
		  Tracer.traceEntry(ex, entry);
		} finally {
		  // ��ر�֤ exit����ر�֤ÿ�� entry �� exit ���
		  if (entry != null) {
			entry.exit();
		  }
		}
	
	# �ȵ�������
		* �� entry ��ʱ�������ȵ��������ô exit ��ʱ��Ҳһ��Ҫ���϶�Ӧ�Ĳ�����exit(count, args)����������ܻ���ͳ�ƴ���
		
		Entry entry = null;
		try {
			// ����Ҫ�������������Ĳ���ֻ֧�ֻ������͡�
			// EntryType �����������ͣ�����ϵͳ����ֻ�� IN ���͵������Ч
			// count ������������ 1������ͳ��Ϊһ�ε��á�
			entry = SphU.entry(resourceName, EntryType.IN, 1, paramA, paramB);
			// Your logic here.
		} catch (BlockException ex) {
			// Handle request rejection.
		} finally {
			// ע�⣺exit ��ʱ��Ҳһ��Ҫ���϶�Ӧ�Ĳ�����������ܻ���ͳ�ƴ���
			if (entry != null) {
				entry.exit(1, paramA, paramB);
			}
		}
	
	#  if-else ���� API
		  // ��Դ����ʹ��������ҵ��������ַ���
		  if (SphO.entry("�Զ�����Դ��")) {
			// ��ر�֤finally�ᱻִ��
			try {
			  /**
			  * ��������ҵ���߼�
			  */
			} finally {
			  SphO.exit();
			}
		  } else {
			// ��Դ������ֹ���������򱻽���
			// ������Ӧ�Ĵ������
		  }
	

	# ע�ⷽʽ
		* ͨ�� @SentinelResource ע�ⶨ����Դ������ blockHandler �� fallback ��������������֮��Ĵ���

		// ԭ����ҵ�񷽷�.
		@SentinelResource(blockHandler = "blockHandlerForGetUser")
		public User getUserById(String id) {
			throw new RuntimeException("getUserById command failed");
		}

		// blockHandler ������ԭ�������ñ�����/����/ϵͳ������ʱ�����
		public User blockHandlerForGetUser(String id, BlockException ex) {
			return new User("admin");
		}