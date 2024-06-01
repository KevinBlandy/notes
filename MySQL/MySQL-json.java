----------------------
json				  |
----------------------
	# 5.7��������

	# ����
		CREATE TABLE `test` (
		  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
		  `value` json NOT NULL,
		  PRIMARY KEY (`id`)
		) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

	# ����
		INSERT INTO `test`(`id`, `value`) VALUES(null, '["Java","Python","Javascript"],"name":"KevinBlandy","age":23}'); 

		* ��json�ֶε����ַ�������
	
		
	# ������صĺ���
		json_array
			* ������ת��Ϊjson����
				SELECT JSON_ARRAY(1, "abc", NULL, TRUE, CURTIME());
				+---------------------------------------------+
				| JSON_ARRAY(1, "abc", NULL, TRUE, CURTIME()) |
				+---------------------------------------------+
				| [1, "abc", null, true, "11:30:24.000000"]   |
				+---------------------------------------------+

		json_object
			* ������ת��Ϊjson����
				SELECT JSON_OBJECT('id', 87, 'name', 'carrot');
				+-----------------------------------------+
				| JSON_OBJECT('id', 87, 'name', 'carrot') |
				+-----------------------------------------+
				| {"id": 87, "name": "carrot"}            |
				+-----------------------------------------+
		
		json_quote
			* ��jsonת��ԭʼjson�ַ�������
				select json_quote('{"name": "KevinBlandy"}');  // "{\"name\": \"KevinBlandy\"}"

		
		json_unquote()
			* ��json�ַ���ת��Ϊjson
				select json_unquote("{\"name\": \"KevinBlandy\"}"); // {"name": "KevinBlandy"}
		
	
	# ��ѯ��صĺ���
		json_contains(json_doc, val[, path]) 
			* ����json�ı����Ƿ�����ض�ֵ, ����: 0/1 
			* ���ܻ᷵��null
				- Ŀ��JSON�ı������ض�ֵΪNULl
				- ָ��·����Ŀ��JSON�ı��µ�·��
			
			* ����
				json_doc
					- json�ı�
				val
					- �ض���ֵ, ������json���߻�����������
				path
					- jsonpath
			
			* demo
				select json_contains('{"a": 1, "b": 2, "c": {"d": 4}}', '1', '$.a');	// 1
				select json_contains('{"a": 1, "b": 2, "c": {"d": 4}}', '1', '$.b');	// 0
				select json_contains('{"a": 1, "b": 2, "c": {"d": 4}}', '{"d": 4}', '$.b');		// 0
				select json_contains('{"a": 1, "b": 2, "c": {"d": 4}}', '{"d": 4}', '$.c');		// 1


		json_contains_path(json_doc, one_or_all, path[, path] ..)
			* �������json�ı��Ƿ����ָ����·��, ����: 0/1
			* ����
				json_doc
					- json�ı�
				
				one_or_all
					- one: �ı��д�������һ��ָ��·���򷵻�1, ���򷵻�0
					- all: �ı��а�������ָ��·���򷵻�1, ���򷵻�0
				path
					- json path, ����ָ����·�������ж��
				
			
			* demo
				select json_contains_path('{"a": 1, "b": 2, "c": {"d": 4}}', 'one', '$.c'); //1
				select json_contains_path('{"a": 1, "b": 2, "c": {"d": 4}}', 'all', '$.c', '$.z'); // 0

				

		json_extract(json_doc, path[, path] ...)
			* ����json_doc����path������ƥ�������
			* demo
				SELECT JSON_EXTRACT('[10, 20, [30, 40]]', '$[1]');	// 20
				SELECT JSON_EXTRACT('[10, 20, [30, 40]]', '$[1]', '$[0]');	// [20, 10] 
				SELECT JSON_EXTRACT('[10, 20, [30, 40]]', '$[2][*]');	// [30, 40]
				SELECT JSON_EXTRACT('[10, 20, [30, 40]]', '$[2][0]');	// [30]

								

		json_keys(json_doc[, path])
			* ����JSON����Ķ���Ŀ¼������keyֵ����pathָ��·���µĶ���Ŀ¼������key����ɵ�JSON����

		json_search(json_doc, one_or_all, search_str[, escape_char[, path] ...])
			* ����json�а�����ָ���ַ�����jsonpath
			* ����
				json_doc
					- json

				one_or_all
					- one �����Ҳ����ҵ���һ��ƥ����󣬲������·�����غ��ֹͣ����
					- all ���������е�ƥ����·��, ����в������ظ�·��, ������ؽ�����а�������ַ���, ���Զ���װΪһ������, Ԫ�ص�����˳��������
				
				search_str
					- ���ص��ַ���, ����һЩ�����ַ�, �����Ҫƥ�������ַ�, ��Ҫת��
					- �������:%	ƥ����
					- �������:_	ƥ��һ��
					- �����ж��
				

	# �޸���صĺ���
		json_append
			* ������MySQL 5.7������, ����MySQL 8.0��ɾ��

		json_array_append
			* ��ָ��������ĩβ��JSON�ı���ʽ׷��ָ����ֵ������

		json_array_insert
		json_insert
		json_merge
		json_remove
		json_replace
		json_set
		json_unquote


	# ����json����
		json_depth(json)
			* ����json�ĵ������
		json_length
			* ����JSON�ĵ��ĳ���(size)
		json_type(json)
			* ����json������, ������json����������
				OBJECT        
				ARRAY
				INTEGER
				BOOLEAN
			
		json_valid(json)
			* �ж�json�Ƿ��Ǳ�׼json
			* ���json���Ǳ�׼��, ���׳��쳣
		

	# json path�﷨
		$ ��ʾ������

	# COLUMN->PATH ����
		* COLUMN ������ʾjson ��, PATH ���� ��ʾjsonpath
		* ������������һ��ʹ��ʱ, ����json_extract�ĺ����ı���, ���ڿ�ݵ���ȡjsonֵ

		* demo, json = {"age": 23, "name": "KevinBlandy", "skill": ["Java", "Python", "Javascript"]}

			select `value`, `value`-> "$.name" from `test`; // {"age": 23, "name": "KevinBlandy", "skill": ["Java", "Python", "Javascript"]}	"KevinBlandy"
			select `id`, `value`-> "$.skill" from `test`;	// 1	["Java", "Python", "Javascript"]
			select `id`, `value`-> "$.skill[0]" from `test`; // 1	"Java"
	
		
	# COLUMN->>PATH 
		* �Ľ���, �����õ���ȡ������, ����MySQL 5.7.13�����߰汾��ʹ��
		* �������µĲ���һ��, ��ʹ��JSON_UNQUOTE�����Ա��ʽ��������ֵ���м���
			JSON_UNQUOTE(JSON_EXTRACT(column, path))
			JSON_UNQUOTE(column -> path)
		
