---------------------
分词器				 |
---------------------
	# 分词器

	# 切分词语 & normalization(提升recall召回率)
		* 对数据进行分词, 并且对每个单词进行normalization
		* 召回率:搜索的时候, 增加能够搜索到的结果数量
	

	# 分词器包含了几个部分
		character filter
			* 分词之间, 进行预处理, 例如:过滤html标签

		tokenizer
			* 分词

		token filter
			* 会执行normalization的一些操作, 例如:同义词转换, 大小写转换
	

		
	
---------------------
内置的分词器		 |
---------------------
	Standard analyzer(默认)
	Simple analyzer
	Witespace analyzer
	Language analyzer

---------------------
执行分词指令		 |
---------------------
	# 请求
		GET /<index>/_analyze

		{
		  "analyzer": "<analyzer>",
		  "text":"<text>"
		}

		* index 非必须, 如果指定的话, 就是使用指定index的mapping来进行
		* analyzer 指定要使用的分词器, 以及要进行分词的文本(text)

		{
		  "tokens" : [
			{
			  "token" : "hello", //拆分的一个词儿
			  "start_offset" : 0, // 该词在文本中的位置(从哪儿字符串开始到哪个字符串)
			  "end_offset" : 5,
			  "type" : "<ALPHANUM>",
			  "position" : 0  // 该词语在文本中的位置(拆出来的第几个词儿)
			},
			{
			  "token" : "world",
			  "start_offset" : 6,
			  "end_offset" : 11,
			  "type" : "<ALPHANUM>",
			  "position" : 1
			}
		  ]
		}

---------------------
定制分词器			 |
---------------------
	
	