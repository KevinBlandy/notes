-----------------------------
分词器						 |
-----------------------------
	# 分词器包含了几个部分
		* 在文本进行分词之前,先进行预处理
			- 过滤html标签
			- & 转换为单词 and

		* 分词,把语句拆分为单词

		* 对分词进行一些同义词,复数,时态,大小写...之类的转换
		* 这个过程叫做normalization(提升能够搜索到的结果数量)
	

	# 内置的几种分词器
		1,Standard analyzer(默认)
		2,Simple analyzer
		3,Whitespace analyzer
		4,Language analyzer

	
-----------------------------
query String分词器			 |
-----------------------------
	# query tring 默认情况下,es会使用它对应的field建立倒排索引时使用的分词器去进行分词
		* 分词,normalizationm,只有这种才能正确的搜索
		* query string 也会被进行分词,分词策略就是document建立倒排索引时的分词策略
	
	# 不同类型的类型(exact value,full text),不同对待
		/_search?q=name:KevinBlandy
		/_all/_search?q=KevinBlandy

-----------------------------
分词器的测试				 |
-----------------------------
	# 直接测试分词器
		GET /_analyze
		{
			"analyzer": "standard",			//指定分词器
			"text":"Hello KevinBlandy"		//指定词句
		}
	
	# 测试指定index下指定字段的分词器
		GET /index/_analyze
		{
		  "field":"desc",					//指定字段
		  "text":"hhhhh"					//给字段指定值
		}

	# 响应分词信息
		{
		  "tokens": [
			{
			  "token": "hello",
			  "start_offset": 0,
			  "end_offset": 5,
			  "type": "<ALPHANUM>",
			  "position": 0
			},
			{
			  "token": "kevinblandy",
			  "start_offset": 6,
			  "end_offset": 17,
			  "type": "<ALPHANUM>",
			  "position": 1
			}
		  ]
		}