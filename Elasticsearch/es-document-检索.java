-------------------
�����ĸ����������� |
-------------------
	# ����һ���Լ������index
		* ֱ�Ӽ������index
			GET /<index>,<index>/_search
	
		* һ���Լ������е�index
			GET /_all/_search
	
		* ͨ�� * ��ƥ����index
			GET /<prefix>*,*<sufix>/_search

--------------------
document - ����		|
--------------------
	# �����ĸ���id����
		GET /<index>/_doc/<id>

		{
		  "_index" : "customer",
		  "_type" : "_doc",
		  "_id" : "1",
		  "_version" : 4,
		  "_seq_no" : 3,
		  "_primary_term" : 1,
		  "found" : true,		
			  * �Ƿ��ҵ������ݣ�����ĵ������ڣ����� false
		  "_source" : {
			...
		  }
		}
	
	# ����id�ж��Ƿ����, ʹ�� HEAD ����
		HEAD /<index>/_doc/<id>

		����:200 - OK
		����:404 - Not Found

	
-------------------
�����ĸ����������� |
-------------------
	# ����
		GET /<index>/_search?q=<query>
		
		{
		  "took" : 2,
				* ִ��������ʱ�䣨�Ժ���Ϊ��λ��
		  "timed_out" : false,
				* �����Ƿ�ʱ
		  "_shards" : {
			"total" : 1,
				* �����˶��ٸ���Ƭ
			"successful" : 1,
				* �����ɹ��ķ�Ƭ����
			"skipped" : 0,
				* �����ķ�Ƭ����
			"failed" : 0
				* ����ʧ�ܵķ�Ƭ����
		  },
		  "hits" : {
			"total" : {
			  "value" : 13,
				  	* ���е�������
			  "relation" : "eq"
			},
			"max_score" : 1.0,
			"hits" : [
			  {
				"_index" : "customer",
				"_type" : "_doc",
				"_id" : "TsVMQGsBor31qRgUxQmS",
				"_score" : 1.0,
				"_source" : {
				  "name" : "KevinBlandy"
				}
			  }
			]
		  }
		}


-------------------
query����		   |
-------------------
	# ���˲���:q
		q=* 
			* ��������, q=*

		q=<value>
			* �κ��ֶ�, ֻҪ������ֵ����������
			* ��������_all field

		q=<field>:<value>
			* ȫ�ļ���, ֻҪ��ָ���ֶ����йؼ��ֵĶ�OK, :q=name:KevinBlandy
			* �ж��ƥ��valueֵ, ʹ�ö��ŷָ�, ��ϵ��or, ֻҪ��������һ������
		
		q=<field>:<+/-><value>
			* + ��ʾ�������, - ��ʾ���벻����: q=-author.name:Litch 
		
	
	# �������:sort
		sort=<field>:<asc/desc>

		* ����ж��, ʹ�ö��ŷָ�:sort=age:asc,_id:desc
	
	# ��ҳ����:size & from
		* size,ÿҳ��ʾ�ļ�¼����, Ĭ��10
		* from,�ӵڼ������ݿ�ʼ����,Ĭ��0(��ʾ��һ��)
	
		* deep paging����
			* deep paging,����˵,�����������ر���,����1000000������,ÿҳ��ʾ10��,��ʱ��Ҫ�������һҳ������
			* �������������,���ܴ����ڶ��primary shard,replica shard,���Ǿ�Ҫ���������ݻ��ܵ� coordinating node(Э���ڵ�)
			* ��Э���ڵ��������,ȡ�����������������,���շ�ҳ����
			* ������̺ķѴ���,�ڴ�,�������,�������deep paging����,���ǵĿ�������Ҫ����������

	
	# _source ���ݹ��˲���:
		_source
			* ���������Ƿ�ҪЯ�� _source ����, ֵ������ true/false
			* Ҳ����ͨ���ò�����ָ��Ҫ�������ֶ�
				GET /goods/_doc/1?_source=author.name,author.age
			
		_source_includes
		_source_excludes
			* ����/����ָ���� _source ����
		
		* ֧���ж��ֵ, ʹ�÷�ŷָ�
		* ֧��ͨ���:*
				GET /goods/_doc/1?_source=*.name
	
	# stored_fields
		//TODO
	
	# timeout ��ʱ����
		* Ĭ���޳�ʱ��ָ��������ֻ���ռ�����ʱǰ�ռ����Ľ��
		* �������������������ʱ�䳬���˲���ֵ����ô����е� timeout �ͻ��� true 
	
	# df
	# analyzer
	# analyze_wildcard
	# batched_reduce_size
	# default_operator
	# lenient
	# explain
	# track_scores
	# track_total_hits
	# terminate_after
	# search_type
	# allow_partial_search_results
	# scroll
		* ��������(�е���������ĸо�)
		* ÿ�ν�����Ӧһ������, ֱ����Ӧ�������
		* ��һ�μ�����ʱ��, �����ɿ���, ����Ӧ���֮ǰ, doc���޸Ĳ��ɼ�(���ظ����ĸо�)

	
	# filter_path
		* ���Թ�������JSON������ֶ�, ����Ԫ������Ϣ
		* ֧�ֶ��󵼺�
			GET /<index>/_search?filter_path=hits.hits  // ������ʾhits��Ϣ
				

	# error_trace
		* true/false, �Ƿ����쳣��ʱ��, ��Ӧ��ջ��Ϣ
	
