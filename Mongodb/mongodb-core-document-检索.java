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
		
		* ��ȷ�ҳ���ܻ������������
		* ������������󣬽��鰴�������ֶ������з�ҳ�������� skip ָ��
			// ÿҳ���ݵĵ�һ��������һҳ�����һ�� ID ��Ϊ��ʼ
			// ObjectId('660e6c7ac5ef631d778afc3a')������һҳ�����һ�� ID
			db.foo.find({"_id": {"$gt": ObjectId('660e6c7ac5ef631d778afc3a')}}).limit(10);
	
	
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
			
			* �������͵ıȽ���һ����νṹ����ʱһ�� KEY ��ֵ�����ж������͡�
			* ����Ի�����͵� KEY ����������ô����һ��Ԥ���������˳�򣬴���Сֵ�����ֵ��˳�����¡�
				/��Сֵ/null/���֣����͡������͡�˫���ȸ����͡�С���ͣ�/�ַ���������ĵ�/����/����������/���� ID/������/����/ʱ���/������ʽ/���ֵ/

	
	# ͶӰ��ѯ, ��������������
		db.[collection].find([condition], [props])
			* ͨ�� props ָ����ѯ����
			* props ��ʹ�� key: val �����Ƿ�Ҫ����, val �����1����ʾ��ѯ��0����ʾ����ѯ
			* Ĭ�ϻ���� _id ����
		
		* ������ѯ�����ĳЩ����
			db.user.find({}, {name: 1, _id: 0}) // ������ѯname���ԣ���id����Ҫ
		
		* ����id���⣬�����ֶβ�������������
	

-------------------------
document - �α�
-------------------------
	# find() ���صľ���һ���α꣬������������ѯ

		* ���û�а� find() ���ص��α�洢�������У�Mongo ���Զ���������ǰ���һЩ�ĵ��������
		
		let cursor = db.foo.find();

		// �����α�
		while (cursor.hasNext()){
			// ��ȡ��Ŀ
			let doc = cursor.next();
			print(doc);
		}
		
		// Ҳ֧��ʹ�� forEach �ص����е���
		cursor.forEach(doc => {
			print(doc);
		});
		
	
	# ���� find ʱ��shell ������������ѯ���ݿ⣬���ǵȵ�������ʼ������ʱ�ŷ��Ͳ�ѯ
		
		* ����������ִ��֮ǰ����ѯ���Ӷ����ѡ��α������������Ƿ��� this �������Ϳ���ʵ����ʽ����
		
		// ��� N ��ѡ��ڵ��ǲ������������ѯ
		let cursor = db.foo.find().skip(1).limit(100).sort({"i": -1});

		while (cursor.hasNext()){
			let doc = cursor.next();
			print(doc);
		}

		* ������ 'cursor.hasNext()' ��ʱ�򣬲�ѯ�Żᱻ�����������ˡ�
		* shell �����̻�ȡǰ 100 ���������ǰ 4MB �����ݣ�����֮�н�С�ߣ��������´ε��� next ���� hasNext ʱ�Ͳ����ٴ����ӷ�������ȥ��ȡ����ˡ�
		* �ڿͻ��˱������һ������shell ���ٴ��������ݿ⣬ʹ�� getMore �������Ľ����
		* getMore �������һ���α�ı�ʶ�������������ݿ�ѯ���Ƿ��и���Ľ����������򷵻���һ�������
		* ������̻�һֱ������ֱ���α�ľ����߽����ȫ�����ء�
	
	# �α����������
		* �α�����������֣�����ͻ��˵��α꣨���潲�ģ����ɿͻ����α�����ʾ�����ݿ��αꡣ

		* �ڷ������ˣ��α��ռ���ڴ����Դ��һ���α��������֮�󣬻��߿ͻ��˷���һ����ϢҪ����ֹ�����ݿ�Ϳ����ͷ�������ʹ�õ���Դ��

		* ���α������ƥ��Ľ��ʱ�������������
		* ���α곬���ͻ��˵�������ʱ����������������ݿⷢ��һ���������Ϣ�������ݿ�֪�������ԡ�ɱ�������αꡣ
		* �û�û�б��������н�������α������������ڣ���� 10 ����û�б�ʹ�õĻ������ݿ��α�Ҳ���Զ� �����١���

		* �����������ʵ����һ����Ϊ immortal �ĺ������������ƵĻ��ƣ����������ݿⲻҪ���α곬ʱ��
		* ����ر����α곬ʱ����ô�ͻ��˱��뾡��ı������������ݣ��������������αꡣ�������ݿ��һֱ�����α�ֱ���´�������



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
		* ����, ͨ��js�� /reg/i ������ƥ�����ݣ������ i ��ʾ�����ִ�Сд
			db.user.find({name: /^\d+$/});				// ƥ�� name �����ֵļ�¼
		
		* Ҳ����ͨ�� $regex ָ��
			db.user.find({name: {$regex: "^\\d+$"}});  // ƥ�� name �����ֵļ�¼

		
		* Mongo ʹ�� Perl ���ݵ�������ʽ��PCRE��������������ʽ����ƥ�䣬�κ� PCRE ֧�ֵ�������ʽ�﷨���ܱ� MongoDB ���ܡ�


	
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

		* null ��ƥ�� �������ڡ� �������
			// ��� foo �ֶβ����ڣ�������Ϊ null �����������
			db.users.find({"foo": {$eq: null}})
		
		* ��� $exists���Ϳ���ʵ�� �����������Ϊ null��
			db.users.find({"foo": {$eq: null, $exists: true}})
	
	# $where ������
		* ������ JS ������ĵ����й���
			
			// ����������� true���ĵ�����Ϊ�������һ���ַ��أ������������ false���ĵ��Ͳ����ء�
			db.foo.find({"$where" : function () {
				// this ָ��ǰ�ĵ�
				for (var current in this) {
					for (var other in this) {
						if (current != other && this[current] == this[other]) {
							return true;
						}
					}
				}
				return false;
			}});
		
		* ���Ǿ��Ա�Ҫ������Ӧ��ʹ�� "$where" ��ѯ�����Ǳȳ����ѯ���öࡣÿ���ĵ�������� BSON ת��Ϊ JavaScript ����Ȼ��ͨ�� "$where" ���ʽ���С�
		* "$where" Ҳ�޷�ʹ��������

		* ������ʹ��������ѯ���й��ˣ�Ȼ����ʹ�� "$where" �Ӿ䣬�������ʹ�ÿ��Խ���������ʧ��
	
	# $expr ������
		* ������ MongoDB ��ѯ�����ʹ�þۺϱ��ʽ��������Ҫִ�� JavaScript�������ٶȱ� $where �죬���龡����ʹ�ô��������� $where��


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
	
