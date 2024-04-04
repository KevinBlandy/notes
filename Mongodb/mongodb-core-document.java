-------------------------
document 
-------------------------
	# �ĵ�
		* BSON��MongoDB ���ݿ��е����ݴ洢�����紫���ʽ��һ�ֶ����Ʊ�ʾ��ʽ
		* Key ���ִ�Сд����ͬһ���ĵ�����ͬ��ε� Key �����ظ���
		* ׼��Ҫ������ĵ������ĵ�����key�����ܰ��� '.'����Ϊ '.' �������ڼ���������ʱ�������Ե����ġ�

	# ��������
		* mongo�е�����, Ĭ���� double ��������, �����Ҫ�洢����, ����ʹ�ú��� NumberInt(val)
			db.user.insert({name: "n3", age: NumberInt(27)});
		
		* ���뵱ǰ����ʹ��: new Date();
		* ����ֶ�Ϊ null,  ��Ӧ������
	
	# ����
		String				�ַ������洢���ݳ��õ��������͡��� MongoDB �У�UTF-8 ������ַ������ǺϷ��ġ�
		Integer				������ֵ�����ڴ洢��ֵ�������������õķ��������ɷ�Ϊ 32 λ NumberInt() �� 64 λ��NumberLong()
		Boolean				����ֵ�����ڴ洢����ֵ����/�٣���
		Double				˫���ȸ���ֵ�����ڴ洢����ֵ��shell Ĭ��ʹ�� 64 λ����������ʾ��ֵ��
		Min/Max keys		��һ��ֵ�� BSON�������Ƶ� JSON��Ԫ�ص����ֵ�����ֵ��Աȡ�
		Array				���ڽ�������б����ֵ�洢Ϊһ������
			* ������԰�����ͬ�������͵�Ԫ�ء�

		Timestamp			ʱ�������¼�ĵ��޸Ļ���ӵľ���ʱ�䡣
		Object				������Ƕ�ĵ���
			* Ƕ��ʽ����

		Null				���ڴ�����ֵ��
		Symbol				���š����������ͻ����ϵ�ͬ���ַ������ͣ�����ͬ���ǣ���һ�����ڲ�������������͵����ԡ�
		Date				����ʱ�䡣�� UNIX ʱ���ʽ���洢��ǰ���ڻ�ʱ�䡣�����ָ���Լ�������ʱ�䣺���� Date ���󣬴�����������Ϣ��
			* �� MongoDB �У��������ڶ���Ӧ����: new Date()
			* ���ݿ��д洢�����ڽ�Ϊ Unix ��Ԫ�����ĺ���������û����֮������ʱ����Ϣ��

		Object ID			���� ID�����ڴ����ĵ��� ID��
			* MongoDB �д洢��ÿ���ĵ���������һ�� "_id" key��
			* "_id" ��ֵ�������κ����ͣ�����Ĭ��Ϊ ObjectId��
			* ÿ�������е� id ֵӦ����Ψһ�ģ���������ĵ�ʱ������ "_id" key ���Զ����ɡ�


		Binary Data			���������ݡ����ڴ洢���������ݡ�ͨ�� shell ����ֱ�Ӳ�����
		Code				�������͡��������ĵ��д洢 JavaScript ���룬{"x": function(){}}
		Regular expression	������ʽ���͡����ڴ洢������ʽ��/foobar/i


-------------------------
document - id
-------------------------
	# �ڼ����в����ĵ�ʱ�����û�����ֶ���������Ӵ���_id���ֶ����ƣ���MongoDB���Զ����һ��Object id�ֶ�
		{"_id": ObjectId("xxxxxxxxx")}

-------------------------
document - ��������
-------------------------
	db.[collection].insert([document], [config]);
		* ��ָ����collection����һ�����߶��(����������)document
		* ��� collection ������, �ᴴ��һ�����޳��ȵ�collection

		* ִ�гɹ��󷵻ز���ɹ����ĵ�����
			WriteResult({"nInserted": 1});
		
		* config
			{
				writeConcern: <document>
					* ��ѡ���׳��쳣�ļ���
				ordered: true
					* Ĭ��Ϊ true, ����˳������ĵ�, ��������κ�һ���쳣, ����������, ʣ����ĵ����ᴦ��
					* ���Ϊ false, Mongo���������У�������ٶȣ���������ĵ��쳣, �������᷵��, �������������ĵ�
			}
			
		* ʹ�� insertOne �� insertMany ���Ӻ��ʵ���/�����ĳ�����Ŀǰ insert �Ѿ�������
		* insertMany һ���Բ������������Լ�� 50Mb����������������з�������
		* �����ĵ���С���ܳ��� 16Mb������ͨ�� bsonsize(doc) ������ĵ���С
			bsonsize({"title": ''})
			17

		* insertOne ����
			{
			  acknowledged: true,
			  insertedId: ObjectId("611ce8adba30db4fe68820ed")
			}
		* insertMany ����
			{
			  acknowledged: true,
			  insertedIds: {
				'0': ObjectId('660ced8f6a4199e694670fb2'),
				'1': ObjectId('660ced8f6a4199e694670fb3')
			  }
			}	
	
	db..[collection].deleteOne([condition], [config]) / db..[collection].deleteMany([condition], [config])

		* ���������Ƴ���һ��/�������ݡ�
		* �ĸ��ĵ��� '��һ��'��ȡ���ڶ�����棬�����ĵ��������˳�򡢶��ĵ���������Щ���£�����ĳЩ�洢������˵���Լ�ָ������Щ������
		* ���û�����������ն��� {}) ���Ƴ��������ݡ�

		* config
			{
				justOne: false,
					* �����Ϊ true �� 1����ֻɾ��һ���ĵ�
					* ��������øò�������ʹ��Ĭ��ֵ false����ɾ������ƥ���������ĵ���
				writeConcern: <document>
					* ��ѡ���׳��쳣�ļ���
			}
		
		* ɾ���������ݵĻ���ʹ�� db.[collection].deleteMany({}) ����ʹ�� db.[collection].drop() ֱ��ɾ����������
		* �ĵ�������ɾ������Ҳ������ˣ������б��ݡ�

-------------------------
document - ����
-------------------------
	db.[collection].updateOne([condition], [update], [config]); / updateMany
		* ���� condition ִ���޸�һ��/�����ĵ�
			db.users.updateOne({name: "KevinBlandy"}, {$set: {name: "new Name"}}); // UPDATE `users` SET `name` = 'new Name' WHERE `name` = 'KevinBlandy';
		
		* �����ʹ�� $Set, ָ������ָ�����ֶν����޸�, ��ô�ͻ��ɸ����޸�, ʹ�����ĵ����׸��Ǿ��ĵ�
			db.users.updateOne({name: "KevinBlandy"}, {name: "ff"}); // ��name=KevinBlandy���ĵ����޸ĳ�ֻ�� name=ff���ĵ�
		
		* ���¶��
			db.user.updateMany({"_id": ObjectId("611ce81eba30db4fe68820ec")}, {$set: {"foo": "bar"}})
		
		* ���� 
				{
				  acknowledged: true,
				  insertedId: null,
				  matchedCount: 1,
				  modifiedCount: 1,
				  upsertedCount: 0
				}
	

		* config ѡ��
			{
				upsert: true,
					*  ��ѡ����������� update �ļ�¼���Ƿ����objNew ,trueΪ���룬Ĭ����false�������롣
					* ���ĵ����������ĵ���Ϊ������������Ӧ�����η��ĵ����д����ģ�����ԭ���Եġ�
						
						// name = foo �ļ�¼�����ڣ�����롣������ڣ��� views �ֶ����� 1
						db.user.updateOne({"name": "foo"}, {$inc: {"views": 1}}, {upsert: true})
						{
						  "name": "foo",
						  "views": 1
						}
					
					* �������key���͸��� key ��ͬһ�� key�����ҶԸ� key ��ֵ���е����� upsert �����������������Ӧ������ƥ����ĵ�
						db.user.updateOne({"count": 1}, {$inc: {"count": 1}}, {"upsert": true})
						// "count": 1 �ļ�¼�����ڣ�����󣬻�ִ�� $inc ������
						{
						  "count": 2
						}
					
				multi: true,
					* ��ѡ��Ĭ���� false,ֻ�����ҵ��ĵ�һ����¼������������Ϊtrue,�ͰѰ����������������¼ȫ�����¡�
				
				writeConcern: <document>
					* ��ѡ���׳��쳣�ļ���
				
				collation: <document>,
				arrayFilters: [<filder-document>],
					* ���ڸ��µ�������Ԫ�ص�ѡ��޸����ض�����ƥ�������Ԫ��
						{
						  "id": 1,
						  "hobby": [
							{
							  "name": "��",
							  "rate": 1
							},
							{
							  "name": "��",
							  "rate": 2
							},
							{
							  "name": "rap",
							  "rate": 3
							}
						  ]
						}
						
						// ���� id = 1 �ĵ�
						// �� hobby ������ rate >= 2��Ԫ�ص� rate ֵ������ 1

						// �����佫 elem ����Ϊ�� "hobby" ������ÿ��ƥ��Ԫ�صı�ʶ��
						db.user.updateOne({"id": 1}, {$inc: {"hobby.$[elem].rate": 1}}, {arrayFilters: [{"elem.rate": {"$gte": 2}}]});
					

				hint: <document|string>
			}
			
		
	db.[collection].replaceOne([condition], [document], [config])
		* ���� condition ȫ���滻Ϊ document
		* һ���������ĵ��Ľṹ���£��ȶ�ȡ�ĵ����޸ĺ���д��
			// ����
			var u = db.users.findOne({...});
			// �޸Ľ��
			u.Address = {...}
			// �޸Ľ��
			delete u.Foo
			// ����
			db.users.replaceOne({...}, u);
		
		* ע�⣬�����ĵ��滻ʱ�ǿ��Ըı� "_id" �ġ�
	
	save() ��������
		* save ��һ�� shell ���������������ĵ�������ʱ�����ĵ������ĵ�����ʱ�����ĵ���
		* ��ֻ��Ψһ���ĵ�����������ĵ��а��� "_id" key��save �ͻ�ִ��һ�� upsert������ִ�в��������

	
	findOneAndDelete(filter, options)
		* ɾ�������ر�ɾ�����ĵ����������ܸ����ĵ���Ϊ����

	findOneAndReplace(filter, replacement, options )
	findOneAndUpdate(filter, update, options)
		* findOneAndUpdate �Խ���һ���������µľۺϹܵ���
		* �ܵ����԰������½׶Σ�$addFields ������� $set��$project ������� $unset���Լ� $replaceRoot ������� $replaceWith��
		* ��һ�������з���ƥ��Ľ�������и��£�Ĭ�Ϸ����ĵ��޸�/�滻֮ǰ��״̬��
	
	* �� updateOne ֮�����Ҫ�������ڣ����ǿ���ԭ�ӵػ�ȡ���޸��ĵ���ֵ��
	* ��ѡ���ĵ��е� "returnNewDocument" �ֶ�����Ϊ true����ô���������޸�/�滻����ĵ���
			db.users.findOneAndUpdate({...}, {...}, {"returnNewDocument": true})

-------------------------
document - ������ص�ָ��
-------------------------
	$set
		* ����/�޸�ֵ�������Ͷ������޸�
		* ��� $set ���ֶβ�����, ���´���
	
	$unset ��ԭ�����
		* �û�ɾ��ָ�����ֶ�
			db.user.update({_id: ObjectId("5eba69c51e2bb3537a710e0b")}, {$unset: {age: 1}}) // ɾ��age�ֶ�
	
	$inc (ԭ������)
		* �����ֶ�ֵ
			db.user.update({_id: ObjectId("5eba69c51e2bb3537a710e0b")}, {$inc: {age: 1}}); // ��age�ֶ� +1
		
		* ����ֶβ����ڣ�����Ϊ $set������ֶη���ֵ��������������׳��쳣

	$push
		* ���һ��Ԫ�ص�����β��
		* ���field�����ڣ����Զ�����һ����������
			db.user.update({_id: ObjectId("5eba69c51e2bb3537a710e0b")}, {$push: {skills: "ruby"}}) // ��� ruby ������� skills ������
		* Ԫ�ؿ�����������������
		* ��� $each ָ�����һ������Ӷ��Ԫ��
			// �޸� hobby �ֶΣ���� $each ָ��������Ԫ��
			db.user.updateOne({"name": "V"}, {"$push": {"hobby": {"$each": ["��", "��", "rap"]}}})
			// �޸ĺ�Ľ������
			{
			  "hobby": [
				"��",
				"��",
				"rap"
			  ]
			}
			 
		* ��� $slice �������Ƴ���
			// �޸� hobby �ֶΣ����  $each ָ���� 2 ��Ԫ�أ�Ȼ���޼� hobby �ֶΣ�����ֶ�Ԫ�س��ȴ����� 2 ������ֻ������ 2 ��
			db.user.updateOne({"name": "V"}, {"$push": {"hobby": {"$each": ["д����", "������"], "$slice": -2}}})
			// �޸ĺ�������
			{
			  "hobby": [
				"д����",
				"������"
			  ]
			}
		
		*�� "$slice" �ض�֮ǰ���Խ� "$sort" ���η�Ӧ���� "$push"���Ȱ���ĳ�ַ�ʽ�����ڽض�
			db.user.updateOne({"name": "V"}, {"$push": {"hobby": {"$each": ["д����", "������"], "$slice": -2, "$sort": 1}}})
		
			* ע�⣬����ֻ�� "$slice" �� "$sort" �� "$push" ���ʹ�ã�������� "$each"��
		
	$addToSet (ԭ������)
		* ��һ��ֵ�������ڣ�����ֻ�е����ֵ�������в�����ʱ������
			// ���� name = V ���ĵ����� emails Set ����� a.qq.com
			db.user.updateOne({"name": "V"}, {$addToSet: {"emails": "a.qq.com"}})

		* ��������Ƕ���, ��������, ��ô�������Ƚϡ�
		* Ҳ���Ժ� $each ���ʹ��
	
	$pop
		* ��������ɾ��Ԫ��
			db.user.update({_id: ObjectId("5eba69c51e2bb3537a710e0b")}, {$pop: {skills: -1}}); // ɾ�� skills �еĵ�һ��ֵ
		* ɾ��λ�õ�ֵ
			-1: ��һ������ͷ
			 1: ���һ������β

	$pull (ԭ������)
		* ������ɾ������ƥ�䵽��ֵ
			db.user.update({_id: ObjectId("5eba69c51e2bb3537a710e0b")}, {$pull: {skills: "java"}}); // ɾ�� skills �е� java Ԫ��

	$setOnInsert
		* �ڴ����ĵ�ʱ���ֶν������ã����ں�������ʱ��������и��ġ�


	$rename (ԭ������)
		* ���ֶν���������
			db.user.update({_id: ObjectId("5eba69c51e2bb3537a710e0b")}, {$pull: {skills: "_skills"}});  // �� skills �����޸�Ϊ _skills
	
	$bit (ԭ������)
		* λ������integer����
			db.user.update({_id: ObjectId("5eba69c51e2bb3537a710e0b")}, {$bit: {val: {and: NumberInt(5)}}}); // UPDATE `val` SET `val` = (`val` and 5)
		
		* λ�Ʋ���:and,or,not.....
		
	