-------------------------
document - ����
-------------------------
	# ֧�����Ե���, ������key ��Ҫ���˫����
		db.user.find({"account.email": "10000@qq.com"});
	
	# ������, �����˵����������, �Ϳ���Ӧ��һ��
	
		db.user.insert({name: "vi", account: {email: '10000@qq.com', phone: '10086'}, skill:['java', 'python', 'javascript']});
		db.user.insert({name: "kk", account: {email: '99999@qq.com', phone: '10000'}, skill:['Ruby', 'C++', {lang: 'javascirpt', proficiency: 80}]});
		db.user.insert({name: "kk", account: {email: '99999@qq.com', phone: '10000'}, skill:[{lang: 'javascirpt', proficiency: 80},{lang: 'ruby', proficiency: 72},{lang: 'c', proficiency: 55}]});
		
		// �޸�
		db.user.updateOne({"account.email": "10000@qq.com"}, {$set: {"account.phone": 10010}});
		
		// ��ѯ
		db.user.find({"account.phone": /\d+/});
	
	# ������Ƕ�ĵ����Ե�ʱ�򣬱����������Զ�ƥ�䣬˳�����Ҳ��������
		{
			"name": {
				"first": "Kevin",
				"last": "Blandy"
			}
		}
		
		// ���� first��Ҳ��ѯ����
		db.user.find({"name": { "last": "Blandy"}})
		// first �� last ����λ�÷��ˣ���ѯ����
		db.user.find({"name": { "last": "Blandy", "first": "Kevin",}})
	
		* ����ʹ�ö��󵼺��������������
			
			// ֻ����һ�������Լ�����
			db.user.find({"name.last": "Blandy"});
			// ˳��ߵ������Լ���������ϵ�� OR
			db.user.find({"name.last": "Blandy", "name.first": "Kevin"});
	
-------------------------
document - ����
-------------------------
	# �ڲ�ѯ��
		* ��׼��ѯ, �����е�Ԫ�غ�λ�ñ���һ��һ��, ���ܱ�ƥ��
			db.user.find({"skill": ['java', 'python', 'javascript']});
		
		* Ҳ����ʹ�� $all ����׼�飬��ʱ˳���޹ؽ�Ҫ
			db.user.find({"skill": {"$all": ['java', 'python', 'javascript']}});
		
		* ƥ�䵥��Ԫ��, ֻҪ������ָ��Ԫ��, �Ϳ���ƥ��
			db.user.find({"skill": 'java'}); // ����Ԫ�ز���д []
		
		* ƥ����Ԫ�أ�or��ϵ
			db.user.find({$or: [{"skill": "java"}, {"skill": "python"}]}); // and ��ϵҲͬ��
		
		* ƥ��������ĳ��ָ����Ԫ�أ�ĳ��ָ��λ�õ�Ԫ�أ�
			db.user.find({"skill.2": 'javascript'}); // ͨ�� arrayKey.[index] ָ��Ԫ�ص��±ꡣ�±��Ǵ�0��ʼ
		
		* ʹ�� $ ���Ի�ȡ��Ԫ�ص��±�
			// {"emails": "c.qq.com"} ��ʾ emails �����У�ֵΪ "c.qq.com" �����Ԫ��
			// �����±����ͨ�� emails.$ ����ȡ
			db.user.updateOne({"emails": "c.qq.com"}, {"$set": {"emails.$": "cc.qq.com"}})

			* ֻ����������е�һ��ƥ�䵽��Ԫ��
			* �����ڷ��������з��������ĵ�һ��Ԫ��
		
		* $size ���������
			// hobby ����Ϊ 3 �ļ�¼
			db.user.find({"hobby": {"$size": 3}})

			* $size ���ܺ� $gt һ���ã����Կ������ĵ���ר��ά��һ�� "size" �ֶ�
			* ÿ��������ɾ������Ԫ�ص�ʱ��ͬʱά�� "size" �ֶ�
		
		* $slice ֻ����Ԫ���е�ĳһ�ζ����ݣ���Ƭ
			// ����ǰ 10 ����������ʾ�� 10 ��
			db.user.find({}, {"hobby": {"$slice": 10}});

			// ƫ����������ǰ 1 ��Ԫ�أ��� 2 ����ʼ����ȡ 3 �� Ԫ��
			// Ԫ�ز��㣬�᷵�ؾ����ܶ��Ԫ��
			db.user.find({}, {"hobby": {"$slice": [1, 3]}});

			* �����ر�ָ���� "$slice" �᷵������Ԫ�أ�Ԫ����һ��Ƕ�׵��ĵ��������� Key
		
		* KEY ����ĳһ��Ԫ�����ѯ����������һ�������ƥ�䣬�ĵ�Ҳ�ᱻ����
			{"key": 1}
			{"key": 6}
			{"key": [8, 15]} 

			// 
			db.user.find({"key": {$gt: 5, $lt: 10}})
			// {"key": 6}, {"key": [8, 15]}  // 8 �����˲�ѯ������5 < 8 > 10������Ȼ 15 �����㣬���ǻ��������ء�
		
		* ʹ�� $elemMatch ������Ԫ�ؽ��бȽϣ�����Ҫ���� $elemMatch �е���������
			db.user.find({"key": {$elemMatch: {$gt: 5, $lt: 10}}})
			// ���ؿ�
		* ע�⣬��� KEY �������飬�ᱻ����
		* "$elemMatch" �����޶��������� �����顱��������Ҫ��һ����Ƕ�ĵ��Ķ�������в���ʱ�Ż��õ�����

	
	# ����Ԫ���Ƕ���
		* ����ƥ�䵥������
			db.user.find({"skill.lang": "java"}); // {skill: [{lang: "java"}]}
		
		* ƥ��������, ʹ�� $elemMatch
			db.user.find({"skill.lang": {$elemMatch: {lang: "Java", proficiency: 80}}});
	

