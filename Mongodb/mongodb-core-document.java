-------------------------
document 
-------------------------
	# ��������
		* mongo�е�����, Ĭ���� double ��������, �����Ҫ�洢����, ����ʹ�ú��� NumberInt(val)
			db.user.insert({name: "n3", age: NumberInt(27)});
		
		* ���뵱ǰ����ʹ��: new Date();
		* ����ֶ�Ϊ null,  ��Ӧ������
	
	# ����
		String				�ַ������洢���ݳ��õ��������͡��� MongoDB �У�UTF-8 ������ַ������ǺϷ��ġ�
		Integer				������ֵ�����ڴ洢��ֵ�������������õķ��������ɷ�Ϊ 32 λ�� 64 λ��
		Boolean				����ֵ�����ڴ洢����ֵ����/�٣���
		Double				˫���ȸ���ֵ�����ڴ洢����ֵ��
		Min/Max keys		��һ��ֵ�� BSON�������Ƶ� JSON��Ԫ�ص����ֵ�����ֵ��Աȡ�
		Array				���ڽ�������б����ֵ�洢Ϊһ������
		Timestamp			ʱ�������¼�ĵ��޸Ļ���ӵľ���ʱ�䡣
		Object				������Ƕ�ĵ���
		Null				���ڴ�����ֵ��
		Symbol				���š����������ͻ����ϵ�ͬ���ַ������ͣ�����ͬ���ǣ���һ�����ڲ�������������͵����ԡ�
		Date				����ʱ�䡣�� UNIX ʱ���ʽ���洢��ǰ���ڻ�ʱ�䡣�����ָ���Լ�������ʱ�䣺���� Date ���󣬴�����������Ϣ��
		Object ID			���� ID�����ڴ����ĵ��� ID��
		Binary Data			���������ݡ����ڴ洢���������ݡ�
		Code				�������͡��������ĵ��д洢 JavaScript ���롣
		Regular expression	������ʽ���͡����ڴ洢������ʽ��


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
					* ���Ϊ false, ��������ĵ��쳣, �������᷵��, �������������ĵ�
			}
			
		* ʹ�� insertOne �� insertMany ���Ӻ��ʵ���/�����ĳ�����Ŀǰ insert �Ѿ�������
		* insertOne ����
			{
			  acknowledged: true,
			  insertedId: ObjectId("611ce8adba30db4fe68820ed")
			}
		* insertMany ����
			{
			  acknowledged: true,
			  insertedIds: { '0': ObjectId("611ce8c5ba30db4fe68820ee") }
			}
					
	db.[collection].remove([condition], [config]);
		* ���������Ƴ�����
		* ���û������, ���Ƴ���������
		
		* config
			{
				justOne: false,
					* �����Ϊ true �� 1����ֻɾ��һ���ĵ�
					* ��������øò�������ʹ��Ĭ��ֵ false����ɾ������ƥ���������ĵ���
				writeConcern: <document>
					* ��ѡ���׳��쳣�ļ���
			}
		
-------------------------
document - ����
-------------------------
	db.[collection].update([condition], [update], [config]);
		* ���� condition ִ�� update �޸�һ���ĵ�
			db.users.update({name: "KevinBlandy"}, {$set: {name: "new Name"}}); // UPDATE `users` SET `name` = 'new Name' WHERE `name` = 'KevinBlandy';
		
		* �����ʹ�� $Set, ָ������ָ�����ֶν����޸�, ��ô�ͻ��ɸ����޸�, ʹ�����ĵ����׸��Ǿ��ĵ�
			db.users.update({name: "KevinBlandy"}, {name: "ff"}); // ��name=KevinBlandy���ĵ����޸ĳ�ֻ�� name=ff���ĵ�
		

		* config ѡ��
			{
				upsert: true,
					*  ��ѡ�����������update�ļ�¼���Ƿ����objNew ,trueΪ���룬Ĭ����false�������롣
				multi: true,
					* ��ѡ��Ĭ����false,ֻ�����ҵ��ĵ�һ����¼������������Ϊtrue,�ͰѰ����������������¼ȫ�����¡�
				writeConcern: <document>
					* ��ѡ���׳��쳣�ļ���
				collation: <document>,
				arrayFilters: [<filder-document>],
				hint: <document|string>
			}
			
		* update �Ѿ�����������ʹ�� updateOne, updateMany
			db.user.updateMany({"_id": ObjectId("611ce81eba30db4fe68820ec")}, {"$set": {"foo": "bar"}})
	
	db.[collection].save([document], [config]);
		* ��������ʱ�򣬿��� insert һ����֧�ֵ���������
		* �����޸�, ͨ��������ĵ����滻�����ĵ���ȫ���滻
		* config ѡ��
			{
				writeConcern: <document>
					* ��ѡ���׳��쳣�ļ��� 
			}
			
		* Ŀǰsave�Ѿ��������޸ĵĻ���ִ��updateOne, updateMany, replaceOne
	
	db.[collection].replaceOne([condition], [document], [config])
		* ����conditionȫ���滻Ϊdocument
		
	
	# ������ص�ָ��
		$set
			* ����ֵ
			* ��� $set ���ֶβ�����, ���´���
		
		$inc (ԭ������)
			* �����ֶ�ֵ
				db.user.update({_id: ObjectId("5eba69c51e2bb3537a710e0b")}, {$inc: {age: 1}}); // ��age�ֶ� +1
			
		$unset (ԭ������)
			* �û�ɾ��ָ�����ֶ�
				db.user.update({_id: ObjectId("5eba69c51e2bb3537a710e0b")}, {$unset: {age: 1}}) // ɾ��age�ֶ�

		$push
			* ���һ��Ԫ�ص�����β��
			* ���field�����ڣ����Զ�����һ����������
				db.user.update({_id: ObjectId("5eba69c51e2bb3537a710e0b")}, {$push: {skills: "ruby"}}) // ��� ruby ������� skills ������
			* Ԫ�ؿ�����������������

		$pushAll
			* ��Ӷ����������β��
			* ������һ������
			

		$pop
			* ��������ɾ��Ԫ��
				db.user.update({_id: ObjectId("5eba69c51e2bb3537a710e0b")}, {$pop: {skills: -1}}); // ɾ�� skills �еĵ�һ��ֵ
			* ɾ��λ�õ�ֵ
				-1: ��һ��
				 1: ���һ��

		$pull (ԭ������)
			* ������ɾ����һ��ƥ�䵽��ֵ
				db.user.update({_id: ObjectId("5eba69c51e2bb3537a710e0b")}, {$pull: {skills: "java"}}); // ɾ�� skills �е� java Ԫ��

		$pullAll (ԭ������)
			* ������ɾ�����ƥ�䵽��ֵ
				db.user.update({_id: ObjectId("5eba69c51e2bb3537a710e0b")}, {$pull: {skills: ["java", "python"]}}); // ɾ�� skills �е� java, python Ԫ��

		$addToSet (ԭ������)
			* ��һ��ֵ�������ڣ�����ֻ�е����ֵ�������в�����ʱ������
			* ��������Ƕ���, ��������, ��ô�������Ƚ�
		
		$rename (ԭ������)
			* ���ֶν���������
				db.user.update({_id: ObjectId("5eba69c51e2bb3537a710e0b")}, {$pull: {skills: "_skills"}});  // �� skills �����޸�Ϊ _skills
		
		$bit (ԭ������)
			* λ������integer����
				db.user.update({_id: ObjectId("5eba69c51e2bb3537a710e0b")}, {$bit: {val: {and: NumberInt(5)}}}); // UPDATE `val` SET `val` = (`val` and 5)
			
			* λ�Ʋ���:and,or,not.....
			
		