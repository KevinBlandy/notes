--------------------
document - ����		|
--------------------
	# ����ʱ, Elasticsearch����ɾ�����ĵ�, Ȼ��һ���Զ�Ӧ���˸��µ����ĵ���������

--------------------
document - ����		|
--------------------
	# ȫ���滻����(PUT)
		PUT /<index>/_doc/<id>
		{...}

		{
		  "_index" : "customer",
		  "_type" : "_doc",
		  "_id" : "1",
		  "_version" : 2,
		  "result" : "updated",
		  "_shards" : {
			"total" : 2,
			"successful" : 1,
			"failed" : 0
		  },
		  "_seq_no" : 1,
		  "_primary_term" : 1
		}
		
		* �൱��ִ����һ�θ���
		* ���id�Ѿ�����,��ôԭ����document���ᱻ����ɾ��,���ǻᱻ���Ϊ: delete
		* ��es������Խ��Խ���ʱ��,es���ں�̨�Լ����İѱ��Ϊ:delete ��document����ɾ����
		* _version ʼ�ջ� +1
	
	# ǿ�Ƹ���(ȫ������)
		POST /<index>/_doc/<id>
		{...}

		* ��������Ҫ�ύ�����ֶ�,�����ڵ��ֶλᱻɾ��
		* ���ܱ����ύ,�Ƿ��гɹ��޸��ֶ�,resultֵ��ԶΪ:'updated'
		* ���������޸�,_version�ֶαػ��1
		* �������Ϊǿ�Ƹ���
		* ���ָ��id�����ݲ�����(����δָ��id), ��ᴴ��, �� "result" = "created"
	
	# ��ǿ�Ƹ���(���ָ���)
		POST /<index>/_update/<id>
		{"doc":{...}}

		{
		  "_index" : "customer",
		  "_type" : "_doc",
		  "_id" : "1",
		  "_version" : 5,
		  "result" : "noop",
		  "_shards" : {
			"total" : 0,
			"successful" : 0,
			"failed" : 0
		  }
		}


		* ���ַ�ʽ,�ύ��JSON�������仯
			{
				"doc":{
					//��Ҫ�޸ĵ��ֶ�
				}
			}

		* ���Խ����ύ������Ҫ���µ��ֶΣ��ĵ������ڷ����쳣���ֶβ����ڻᴴ��
		* ��������ύδ�޸����ݵĻ�,��ôresult�ֶ�ֵΪ:'noop',����û��:'_seq_no'��'_primary_term'�ֶ�,
			- Ҳ��Ϊnoop����
		
		* ֻ�����������޸ĵ�ʱ��,version +1
		* �������Ϊ��ǿ�Ƹ���
		* partial update(���ָ���)
	
	# upsert����
		* ������¼�¼�����ڣ���ִ�в���
		
		POST /users/_update/5
		{
		  "doc": {
			"name": "foo"			// �����ֶ�
		  },
		  "upsert": {				// �����ֶ�
			"name": "foo",
			"remark": "Upsert"
		  }
		}

--------------------
document - �ű����Ը���
--------------------
	# ֧��ʹ��Groovy�ű����Ը���
		ctx._source
			* ����Դ�ĵ�
	
	# ����
		POST /<index>/_update/<id>
		{
			"script" : "ctx._source.age += price_diff",  // ��ȡ���� price_diff 
			"params": {					// �������
				"price_diff": 10
			}
		}

	# ����
		POST /<index>/_update/<id>
		{
		  "script" : "ctx._source.age += 5"
		}
	


--------------------
ʹ�ò�ѯAPI����		|
--------------------
	# ʹ�� _update_by_query ��ƥ�䵽���ĵ����и���
		POST /<index>/_update_by_query
		{
		  "query": { 
			"term": {
			  "<filed>": "<value>"
			}
		  }
		}
		* ��index�е�����field��ֵ���޸�Ϊvalue

--------------------
��������֧�ֵĲ���
--------------------
	# ���²���, ֧�ֵĲ�ѯ���ܲ���
		retry_on_conflict
			* �ڸ��µ�get��indexing�׶�֮��, ��һ�����̿����Ѿ�������ͬһ�ĵ�
			* Ĭ�������, ���½���汾��ͻ�쳣��ʧ��, ��retry_on_conflict ���������������׳��쳣֮ǰ���Ը��µĴ���

		routing
		timeout
		wait_for_active_shards
		refresh
		_source
			* �Ƿ��Լ��������Ӧ�з��ظ��µ�����
			* Ĭ�������, ���᷵�ظ��µ�����

		version
			* ����API��֧���ڲ��汾����İ汾����

--------------------
ʹ��Task API		|
--------------------
	# Task API���¹���, Ŀǰ����Ϊ���԰湦��
