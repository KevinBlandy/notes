------------------------------
collection
------------------------------
	# ���ϵĲ���Api
		https://www.mongodb.com/zh-cn/docs/manual/reference/method/js-collection/
	
	# ����
		* ���Ʋ����� 'system.' ��ͷ����Ҫ���������ַ����磺'$'.
		* ʹ�� '.' �ַ��ָ���ͬ�����ռ���Ӽ�����һ����֯���ϵĹ��������磺blog.posts �� blog.authors��


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


db.collection.analyzeShardKey()
	* ����������ֵ��Ƭ����ָ�ꡣ
db.collection.aggregate()
	* �ṩ�ԾۺϹܵ�
db.collection.bulkWrite()
	* �ṩ����д��������ܡ�
db.collection.configureQueryAnalyzer()
	* Ϊ�������ò�ѯ������
db.collection.count()
	* ��װ count�����ؼ����л���ͼ���ĵ��ļ�����
db.collection.countDocuments()
	* ʹ�� $sum ���ʽ��װ $group �ۺϽ׶Σ����ؼ��ϻ���ͼ���ĵ��ļ�����
db.collection.createIndex()
	* �ڼ����Ϲ���������
db.collection.createIndexes()
	* Ϊ���Ϲ���һ������������
db.collection.dataSize()
	* ���ؼ��ϵĴ�С����װ collStats ������е� size
db.collection.deleteOne()
	* ɾ�������еĵ����ĵ���
db.collection.deleteMany()
	* ɾ�������еĶ���ĵ���
db.collection.distinct()
	* ���ؾ���ָ���ֶεĲ�ֵͬ���ĵ����顣
db.collection.drop()
	* �����ݿ���ɾ��ָ���ļ��ϡ�
db.collection.dropIndex()
ɾ�����ϵ�ָ��������
db.collection.dropIndexes()
ɾ�������ϵ�����������
db.collection.ensureIndex()
��ɾ����ʹ�� db.collection.createIndex()
db.collection.estimatedDocumentCount()
��װ count�����ؼ��ϻ���ͼ���ĵ��Ĵ��¼�����
db.collection.explain()
���ظ��ַ����Ĳ�ѯִ����Ϣ��
db.collection.find()
�Լ��ϻ���ͼִ�в�ѯ���������α����
db.collection.findAndModify()
��ԭ�ӷ�ʽ�޸Ĳ����ص����ĵ���
db.collection.findOne()
ִ�в�ѯ�����ص����ĵ���
db.collection.findOneAndDelete()
���Ҳ����µ����ĵ���
db.collection.findOneAndReplace()
���Ҳ����µ����ĵ���
db.collection.findOneAndUpdate()
���Ҳ����µ����ĵ���
db.collection.getIndexes()
����˵������������������һ���ĵ���
db.collection.getShardDistribution()
���ڷ�Ƭ��Ⱥ�еļ��ϣ�db.collection.getShardDistribution() �������ݶηֲ������ݡ�
db.collection.getShardVersion()
��Ƭ��Ⱥ���ڲ���Ϸ�����
db.collection.hideIndex()
�ڲ�ѯ�滮��������������
db.collection.insertOne()
�ڼ����в������ĵ���
db.collection.insertMany()
�ڼ����в��������ĵ���
db.collection.isCapped()
���漯���Ƿ�Ϊ�̶���С����
db.collection.latencyStats()
���ؼ��ϵ��ӳ�ͳ����Ϣ��
db.collection.mapReduce()
ִ�� map-reduce ��ʽ�����ݾۺϡ�
db.collection.reIndex()
���¹��������ϵ���������������
db.collection.remove()
�Ӽ���ɾ���ĵ���
db.collection.renameCollection()
���ļ��ϵ����ơ�
db.collection.replaceOne()
�滻�����еĵ����ĵ���
db.collection.stats()
���漯�ϵ�״̬���ṩ collStats �ķ�װ��
db.collection.storageSize()
���漯��ʹ�õ��ܴ�С�����ֽ�Ϊ��λ�����ṩ collStats ����� storageSize �ֶεķ�װ����
db.collection.totalIndexSize()
���漯��������ʹ�õ��ܴ�С���ṩ collStats ����� totalIndexSize �ֶεķ�װ����
db.collection.totalSize()
���漯�ϵ��ܴ�С�����а��������������ĵ������������Ĵ�С��
db.collection.unhideIndex()
�Ӳ�ѯ�滮����ȡ������������
db.collection.updateOne()
�޸ļ����еĵ����ĵ���
db.collection.updateMany()
�޸ļ����еĶ���ĵ���
db.collection.watch()
�ڼ����Ͻ����������
db.collection.validate()
�Լ���ִ����ϲ�����