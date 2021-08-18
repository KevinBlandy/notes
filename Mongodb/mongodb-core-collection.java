------------------------------
collection
------------------------------
	# ���ϵĲ���Api
		https://docs.mongodb.com/manual/reference/method/js-collection/

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
			  ns: 'demo.user',			// ��������
			  size: 141,				// �ĵ�ռ�ÿռ��С
			  count: 3,					// �ĵ�����
			  avgObjSize: 47,			// ƽ���ĵ���С
			  storageSize: 36864,		// �洢�ռ䣬Ԥ�ȴ������ļ���С����д���˾�����
			  freeStorageSize: 16384,
			  capped: false,
			  nindexes: 1,
			  indexBuilds: [],
			  totalIndexSize: 36864,
			  totalSize: 73728,
			  indexSizes: { _id_: 36864 },
			  scaleFactor: 1,
			  ok: 1,
			  '$clusterTime': {
				clusterTime: Timestamp({ t: 1629284670, i: 12 }),
				signature: {
				  hash: Binary(Buffer.from("bb3a5c1a16e2e2fa0353bcdcd743854473257c4f", "hex"), 0),
				  keyId: Long("6944398072461918210")
				}
			  },
			  operationTime: Timestamp({ t: 1629284670, i: 12 })
			}