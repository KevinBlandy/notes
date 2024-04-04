------------------------------
mongodb						  |
------------------------------
	# ��ַ
		https://mongodb.com/
		https://www.mongodb.com/zh
		https://docs.mongodb.com/
		https://github.com/mongodb/mongo

		https://docs.mongodb.com/manual/reference/method/
		https://www.mongodb.com/zh-cn/docs/manual/ // �����ĵ�

		https://mongoing.com/
		https://www.mongodb.org.cn/

	
	# ��صĽ̳�
		https://mongoing.com/docs4.2
		https://www.runoob.com/mongodb/mongodb-tutorial.html
	
	# �汾�ŵĺ���: x.y.z
		y ������ʱ��, ��ʾ�ǿ�����, ż����ʱ��, ��ʾΪ�ȶ���
		z �������汾��, Խ��Խ��


------------------------------
mongodb	- Ŀ¼�ṹ			  |
------------------------------
	bin
		|-mongo.exe
			* һ��js��Shell�Ŀͻ���,����ʹ��js���﷨ȥִ�пͻ��˵Ĳ���
			* �����߱���js�ı�׼��,������ dom �� bom

			* ����ʹ�ö�������,���������js�﷨�Ƿ���������,ûд��������,�����ͨ���س�,��������һ��д
			* ���������λس�,����ȡ��û��������ɵ�����

		|-mongod.exe
			* mongo����˽ű�

		|-mongos.exe.exe
			* ����Windowsƽ̨�� MongoDB Shard�������Ĺ�����
	log
		|-mongod.log

------------------------------
mongodb	- ��������			  |
------------------------------
	# ִ�� mongod �ű�

	# û�в���,Ĭ�ϻ�ʹ�� /data/db Ϊ����Ŀ¼ 
		* Windows ʹ�õ�ǰ���̸�Ŀ¼�� \data\db
		* ��Ҫ�ȴ���,���������ʧ��
	
	# Mongo�����ɹ����� 27017 �˿��ṩ����

	# ��������
		--noscripting
			* ��ֹ�����ִ��js����
		
		--config
			* ָ�������ļ��ĵ�ַ
				mongod.exe --config=D:\mongodb\config\mongod.conf

		--dbpath
			* ָ�����ݴ洢Ŀ¼
				mongod.exe --dbpath=D:\mongodb-win32-x86_64-windows-7.0.7\data
			
			* ���û��ָ���������� mongod ��ʹ��Ĭ�ϵ�����Ŀ¼ /data/db/����Windows ϵͳ��Ϊ��ǰ��� \data\db\����
			* '�������Ŀ¼�����ڻ򲻿�д����ô�������˽��޷�������'
			* ��������� MongoDB ֮ǰ����������Ŀ¼���� mkdir -p /data/db/����ȷ���Ը�Ŀ¼��дȨ�޷ǳ���Ҫ��


------------------------------
mongodb	- �ͻ��˵�ʹ��		  |
------------------------------
	# ����ʱ���ӵ����� mongo ����
		mongo host:port/db
	
	# ���������ӵ����� Mongo ����
		// ������������
		conn = new Mongo('host:port')
		// ��ȡ��ָ�������ݿ�
		db = conn.getBD('dbname')
	
	# ������ʱ��,�������ӵ�Ĭ�ϵ� test ���ݿ⣬���Ұ����ݿ�����Ӹ�ֵ��ȫ�ֱ���: db
	# Shell �еĳ���ָ��
	
		db
			* �鿴��ǰ���ڵ����ݿ�
		
		user db
			* �л���ָ����db,�����ڻᴴ��
		
		help
			* �鿴��������
			* ���Բ鿴ָ��������ǩ����˵��
			// [����].help ���ط���ԭ�ͺ�˵�����Լ��ٷ��ĵ��ĵ�ַ
			db.[collection].[function].help
			db.collection.updateOne(filter, update, options)		// ԭ��
				Updates a single document within the collection based on the filter.
			For more information on usage: https://docs.mongodb.com/manual/reference/method/db.collection.updateOne
		
		cls
			* ����
	
	# ��������
		--quiet
			* ִ��js�ű��ļ�������ָ�����������ִ��
				mongo --quiet a.js b.js
		--nodb
			* ���� mongo shell ʱ�������κ� mongod��
	
	# �û���Ŀ¼�µ� .mongorc.js �����ű�
		* ���ļ��������� shell ʱ�Զ����С�
		* ���� shell ʱָ�� --norc ����������Խ��ö� .mongorc.js �ļ��ļ��ء�
		


	# ����ʹ�õ�����ʱ����
		load
		print

------------------------------
mongodb	- ���ĸ���
------------------------------
	# ���ݿ� db
	# ����	collection
	# �ĵ�	document
	# ID	id



------------------------------
mongodb	- ��;
------------------------------
	# ����Ƕ�׽ṹ�ļ���������
	# �ۺϼ���
	# ����
	# ����
	# Java API
	# ���ݺͻָ�


