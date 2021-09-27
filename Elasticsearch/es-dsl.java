------------------------------
DSL							  |
------------------------------
	# �ĵ�
		https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl.html
	
	# HTTPЭ��涨GETû��������,һ��Ҳ������GET�������body,��GET�����ʺ��ڼ�������
		* ���������֧�ֵĳ���,Ҳ����ʹ��POST���� +  _search �˵�
			POST /<index>/_search
	
------------------------------
DSL	query					  |
------------------------------
	match_all
		* ƥ������
			{"query": { "match_all": {} }}
	
	match
		* ȫ�ļ���
			{
			  "query": { "match": { "<field>": <keyworlds> } }
			}

		* �������ƥ�����ֶ�, ��ô keyworlds �����ж��, ʹ�ö��ŷָ�
			{
			  "query":{"match": {"name":"Litch Rocck"}} //name = "Litch" or name = "Rocck"
			}
		* ��Ѽ��������ݽ��зִ�, Ȼ��ȥ���������м���, ֻҪ����������ƥ�䵽�κ�һ����, �Ϳ����������
		* ͨ��������, �ؼ��ֱ���ֺ�, �κ�һ���ĵ�ƥ�䵽���κιؼ���, ��ok, ����ؼ������ĵ��Ĺ�ϵ�� or

	match_phrase
		* ��ȫ�ļ������, Ҳ�ǻ�Թؼ��ֽ��зִ�
		* ���Ǳ���Ҫ��doc�еĹؼ���, ���ϼ��������е�����, �Ż��������, ����ؼ������ĵ��Ĺ�ϵ�� and
			{
			  "query":{"match_phrase": {"name":"Litch Rocck"}}  // ����nameͬʱ������ Litch Rocck �ļ�¼
			}

		* ��ȫƥ�����Ʒǳ����ϸ�, ����Ҫ��doc�г���, �ؼ��ֲ�ʶ�������д�
		* �и��ɵ�������, ����������ƥ��N���ؼ���Ҳ��������, ʹ�õ�:slop
			{
				"query":{
				  "match_phrase": {
					"<field>":{
					  "query": "<keywords>",
					  "slop": 1 // ֻҪ field �ֶ���, ���������1�� keywords��ʺ�Ĵ�, ���������ؽ��
					}
				  }
				},
			}

		

	multi_match
		* �� match ���, ���԰Ѽ����Ĺؼ���, ���ڶ��ָ�����ֶ�
			{
				"query": {
					"multi_match":{
						"query":"<keywords>",				// �����Ĺؼ���
						"fields":["field1", "field2"...]	// ƥ����ֶ�
					}
				}
			}
		
		* ֻҪ���κ��ֶγɹ�ƥ��, ���������ؼ�¼
	
	match_phrase_prefix

	range
		* ����ֵƥ��, ָ�����ֶδ���, ����, С��, ���ڵ���, С�ڵ��� ָ����ֵ
			{
				"query":{
					"range":{
						"<field>":{
							"<operation>":"<value>" 
							"<operation>":"<value>"
						}
					}
				}
			}
		
		* operation ������:
			gt
			lt
			le
			ge
			ne
	
	term
		* ��׼ƥ��, ���Թؼ��ֽ��зִ�������, �ĵ���ָ���ֶα���������������Ĵʻ�
			{
				"query": {
				  "term": {
					"<field>":'<value>'
				  }
				}
			}
		
		* ʹ��termҪȷ����������ֶ��Ƿ�"������(analyzed)", Ĭ�ϵ��ַ����Ǳ�������
		* ͨ��������, �ؼ���û�ִ�, ������docȴ�ִ���, ����doc�͹ؼ�����һ��һ����, Ҳû��ƥ��
		* �ؼ���û�ִ�, docҲû�ִ�, ����һ��, �Ϳ���ƥ��
			
	
	terms
		* ��termһ��, ��������ָ�����ֶ�, ƥ����ֵ
			{
				"query":{
					"terms": {
						"<field>": ["<value1>", "<value2>"]
					}
				}
			}
	
	exists
		* ָ����field�������, ���Ҳ���Ϊ null, �ͷ�������
			{
				"query":{
				  "exists": {
					"field": "<field>"
				  }
				}
			}
	



------------------------------
DSL	bool					  |
------------------------------
	# �Ѷ������ʹ�ò����߼�����С�Ĳ�ѯ��ɸ���Ĳ�ѯ
	# and ��ϵ��:must
		{
		  "query": {
			"bool": {
			  "must": [
				{ "match": { "name": "KevinBlandy" } },
				{ "match": { "age": "23" } }
			  ]
			}
		  }
		}
		* ��bool����һ���ж�����:name = "KevinBlandy" and age = "23"
	
	# or��ϵ��:should
		{
		  "query": {
			"bool": {
			  "should": [
				{ "match": { "name": "Litch" } },
				{ "match": { "name": "Roccl" } }
			  ]
			}
		  }
		}
		* �� bool ����һ���ж�����:name = "Litch" or name = "Roccl"
		
	# ���ȡ����:must_not
		{
		  "query": {
			"bool": {
			  "must_not": [
				{ "match": { "name": "Litch" } },
				{ "match": { "name": "Roccl" } }
			  ]
			}
		  }
		}
		* �� bool ����һ���ж�����:name != "Litch" and name != "Roccl"
	
	# ��������������
		{
		  "query":{
			"bool":{
			  "should": [{
				  "match": {
				  "name": "Litch"
				}}
			  ],
			  "must_not": [{
				  "match": {
				  "age": 24
				}}
			  ]
			}
		  }
		}
		* �� bool ����һ���ж�����:name = "Litch" and 24 != 24
	
	# boolҲ����Ƕ��, ������SQL�е��Ӳ�ѯ

	# minimum_should_match


------------------------------
DSL	filter					  |
------------------------------
	# ����, ʹ�ò�ѯ�����ƽ��������Ӿ�ƥ����ĵ�, ��������ļ�������ķ�ʽ

	# range ��������ֵ
		{
		  "query":{
			"bool":{
			  "must":{
				"match_all":{}
			  },
			  "filter":{
				"range":{
				  "<field>":{
					"<operation>": <value>
				  }
				}
			  }
			}
		  } 
		}

	
	# filter �� query �ĶԱ�����
		* filter, ����ֻ�ᰴ���������˳���Ҫ������
		* query, ��ȥ�����ÿ��doc�����������������ض�, ���Ұ�װ��ضȽ�������

		* ���ֻ�ǽ�������, ��Ҫ��ƥ�����������������ȷ���, ��query
		* ���ֻ��Ҫ��������, ɸѡ��һ���ֵ�����, ����ע����, ��filter

		* filter ����Ҫ������ضȷ���, ����Ҫ������ضȽ�������, ͬʱ����cache�ʹ��
		* query ��Ҫ������ضȷ���, ��Ҫ��������, ����cache���

------------------------------
DSL	��ҳ					  |
------------------------------
	# ʹ�� from & size		
		{"from":0,	"size":10}

------------------------------
DSL	����					  |
------------------------------
	# ʹ�� sort
		"sort": [{ "<field>": { "order": "<desc/asc>" } }]
	
	# Ĭ�ϵ���������Ǹ���Ԫ�����е�:_score �����������(��ض�Խ�ߵ�Խ��ǰ��)

------------------------------
DSL	���ƽ���ֶ�			  |
------------------------------
	# ʹ�� _source
		{
			"_source": ["<field1>", "<field2>"]
		}

		* ͨ�� _source ָ��n���Ҫ�������ֶ�, �ֶ�֧�ֶ��󵼺�

