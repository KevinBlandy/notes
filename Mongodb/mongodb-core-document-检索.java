-------------------------
document - ��ѯ���
-------------------------
	# �����Ĳ�ѯ
		db.[collection].find([condition])
			* �����������������
			* ���û������, ����������е�����
			
			* �÷������صĶ�����һ�� '������', ����ʹ�� while �����е���
				const it = db.foo.find();
				while (it.hasNext()) {
					printjson(it.next());
				}
			
			* ����ʹ�� pretty() ����, ��ʽ��json���
				db.foo.find().pretty;
			

	
	# ��ҳ
		db.[collection].find([condition]).skip([rows])
			* ��skip()����������ָ��������¼����������

		db.[collection].find([condition]).limit([rows])
			* ʹ�� limit ����, �����ƽ��������
	
	
	# ͳ������
		db.[collection].count([condition]);
			* ��������ͳ���ĵ�������
			* ���û������, ��ͳ�����е��ĵ�

	# ����
		db.[collection].find().sort([row])
			* ͨ�� sort ָ��������ֶ��Լ�����
				db.users.find().sort({name: -1}) // ����name�ֶΣ���������
				db.users.find().sort({name: -1, age: 1}) // ����name�ֶΣ���������age�ֶ���������
			
			* ������ԡ�-1: ����1:����
			* ������� skip() һ��ʹ��, ��������, �ٷ�ҳ

	
	# ͶӰ��ѯ, ��������������
		db.[collection].find([condition], [props])
			* ͨ�� props ָ����ѯ����
			* props ��ʹ�� key: val �����Ƿ�Ҫ����, val �����1����ʾ��ѯ��0����ʾ����ѯ
			* Ĭ�ϻ���� _id ����
		
		* ������ѯ�����ĳЩ����
			db.user.find({}, {name: 1, _id: 0}) // ������ѯname���ԣ���id����Ҫ
		
		* ����id���⣬�����ֶβ�������������
	

-------------------------
document - �ۺϼ���
-------------------------

-------------------------
document - �������
-------------------------
	# ������ѯ����
		����				{<key>:<value>}			db.col.find({"by": "����̳�"}).pretty()		where by = '����̳�'
		С��				{<key>:{$lt:<value>}}	db.col.find({"likes":{$lt: 50}}).pretty()		where likes < 50
		С�ڻ����			{<key>:{$lte:<value>}}	db.col.find({"likes":{$lte: 50}}).pretty()		where likes <= 50
		����				{<key>:{$gt:<value>}}	db.col.find({"likes":{$gt: 50}}).pretty()		where likes > 50
		���ڻ����			{<key>:{$gte:<value>}}	db.col.find({"likes":{$gte: 50}}).pretty()		where likes >= 50
		������				{<key>:{$ne:<value>}}	db.col.find({"likes":{$ne: 50}}).pretty()		where likes != 50
		����				{<key>:{$in:[<value>]}}	db.col.find({"likes":{$in: [10]}}).pretty()		where likes IN (10)
		������				{<key>:{$nin:[<value>]}}	db.col.find({"likes":{$nin: [10]}}).pretty()		where likes NOT IN (10)
	
	# �����ѯ
		* ����, ͨ��js�� /reg/ ������ƥ������
			db.user.find({name: /^\d+$/});				// ƥ�� name �����ֵļ�¼
		
		* Ҳ����ͨ�� $regex ָ��
			db.user.find({name: {$regex: "^\\d+$"}});  // ƥ�� name �����ֵļ�¼
	
	# $type ������
		* $type�������ǻ���BSON����������������ƥ����������ͣ������ؽ����
		
			����					����	��ע
			Double					1	 
			String					2	 
			Object					3	 
			Array					4	 
			Binary data				5	 
			Undefined				6		�ѷ�����
			Object id				7	 
			Boolean					8	 
			Date					9	 
			Null					10	 
			Regular	Expression		11	 
			JavaScript				13	 
			Symbol					14	 
			JavaScript	(with scope)15	 
			32-bit integer			16	 
			Timestamp				17	 
			64-bit integer			18	 
			Min key					255		Query with -1.
			Max key					127	 
		
		* ����ָ��key������ȥƥ������
			db.users.find({name: {$type: 'string'}});  // ����ƥ��name�������ַ����ļ�¼
			db.users.find({name: {$type: 2}});		// ͬ��
	
	# $exists ������
		db.users.find({name: {$exists: true}});		// name �ֶδ��ڣ�������
		db.users.find({name: {$exists: false}});	// name �ֶβ����ڣ�������

	
	# AND������ϵ
		* AND ��ϵ, Ĭ�϶����е����Զ��� AND ����
			db.col.find({name: "1", age: 23}); // WHERE name = '1' AND age = 23;
		
		* ����ʹ�� $and
			db.col.find({$and: [{name: 'Kevin'}, {age: 27}]});
		
	# OR ������ϵ
		db.col.find({$or: [{name: "v"}, {name: "z"}]}); // WHERE name = 'v' OR `name` = `z`
		
	# ��Ϲ�ϵ
			db.users.find({
				name: "KevinBlandy",
				$or: [{
					age: {
						$gt: 18
					}
				}, {
					age: {
						$lt: 50
					}
				}]
			});  // WHERE name = 'KevinBlandy' AND (age > 18 OR age < 50)
		

			db.user.find({
				$and: [{
					name: "vin"
				}, {
					$or: [{
						age: {
							$lt: 25
						}
					}]
				}]
			}); //  WHERE name = 'KevinBlandy' AND (age < 50)
			
			db.user.find({$or: [{name: 'vin'}, {age: {$in: [1,2,3]}}]}); // WHERE name = 'vin' OR `age` IN (1,2,3)