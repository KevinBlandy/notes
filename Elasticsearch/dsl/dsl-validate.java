--------------------------------
validate						|
--------------------------------
	# ������SQL�е� explain, ���������ж������Ƿ�Ϸ�
		* ��ʹ�÷ǳ����ӵ�����DSL�����, ������ͨ�����ַ�ʽ����֤�Ƿ�Ϸ�

	# �﷨
		GET /<index>/_validate/query?explain
		{
			"query":....
		}
	
		{
		  "_shards" : {
			"total" : 1,
			"successful" : 1,
			"failed" : 0
		  },
		  "valid" : true,
				* �Ƿ�Ϸ�
		  "explanations" : [
			{
			  "index" : "<index>",
			  "valid" : true,
			  "explanation" : "ConstantScore(NormsFieldExistsQuery [field=modify_date])"
			}
		  ]
		}
		
