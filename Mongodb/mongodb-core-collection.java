------------------------------
collection
------------------------------


------------------------------
collection - ����
------------------------------
	db.createCollection([collection], [config]) 
		* �������ô���ָ�����Ƶļ���
		* conifg
			{
				capped: true ����ѡ��
						* ���Ϊ true���򴴽��̶����ϡ��̶�������ָ���Ź̶���С�ļ��ϣ����ﵽ���ֵʱ�������Զ�����������ĵ���
						* ����ֵΪ true ʱ������ָ�� size/max ������

				autoIndexId: true ����ѡ��
						* ��Ϊ true���Զ��� _id �ֶδ���������Ĭ��Ϊ false��

				size: 1024 ����ѡ��
						* Ϊ�̶�����ָ��һ�����ֵ����ǧ�ֽڼƣ�KB����
						* �ֽڵ�λһ����2���������ݣ�������õĲ��ǣ������Զ�ƫ��
						* ��� capped Ϊ true��Ҳ��Ҫָ�����ֶΡ�

				max: 10
						* ����ѡ��ָ���̶������а����ĵ������������
				
			}

			* ��size/max�������˵�ʱ�����ж�size�����ж�max���κ�һ�������㣬�����׳��쳣
		
		* ֻ�е�����������д����һ��document, collection�Ż������Ĵ���, ���̴洢
	
	db.getCollection([collection])
		* ��ȡ����, ������������
		* ���᲻�ᴴ��, ֻ���ڲ����˼�¼��Żᱻ����

	show tables
		* �鿴��ǰdb�µ�����collection
		* Ҳ����ʹ��
			show collections
		
	db.[collection].drop()
		* ɾ��ָ���ļ���
	
	
	db.[collection].stats()
		* �鿴����״̬
			{
			  ns: 'demo.user',		// ��������
			  size: 0,				// �ĵ���С
			  count: 0,				// �ĵ�����
			  storageSize: 4096,	// �洢�ռ�
			  freeStorageSize: 0,
			  capped: false,
			  nindexes: 1,
			  indexBuilds: [],
			  totalIndexSize: 4096,
			  totalSize: 8192,
			  indexSizes: { _id_: 4096 },
			  scaleFactor: 1,
			  ok: 1,
			  '$clusterTime': {
				clusterTime: Timestamp({ t: 1629122112, i: 1 }),
				signature: {
				  hash: Binary(Buffer.from("883238bce8eea92ce3c406e304cc245e33309d50", "hex"), 0),
				  keyId: Long("6944398072461918210")
				}
			  },
			  operationTime: Timestamp({ t: 1629122112, i: 1 })
			}