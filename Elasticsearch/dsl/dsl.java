------------------------------
DSL							  |
------------------------------
	# 文档
		https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl.html
	
	# HTTP协议规定GET没有请求体,一般也不允许GET请求带有body,但GET更加适合于检索场景
		* 如果遇到不支持的场景,也可以使用POST方法 +  _search 端点
			POST /<index>/_search
	
------------------------------
DSL	query					  |
------------------------------
	match_all
		* 匹配所有
			{"query": { "match_all": {} }}
	
	match
		* 根据字段检索
			{
			  "query": { "match": { "<field>": <keyworlds> } }
			}

		* 如果允许匹配多个字段, 那么 keywords 可以有多个, 使用空格分隔
			{
			  "query":{"match": {"name":"Litch Rocck"}} //name = "Litch" or name = "Rocck"
			}
		* 会把检索的内容进行分词, 然后去倒排索引中检索, 只要是索引可以匹配到任何一个词, 就可以算作结果
		* 通俗理解就是, 关键字被拆分后, 任何一个文档匹配到了任何关键字, 都ok, 多个关键字与文档的关系是 or
		
		* 修改多个关键字的关系规则为 and
		  "query": {
			"match": {
			  "name": {
				"query": "KevinBlandy dd",  // name 字段必须要包含 KevinBlandy dd 2个词条
				"operator": "and"
			  }
			}
		  }
	
	phrase
		* 可以指定关键字之间的间隔
	
	match_phrase
		* 跟match差不多, 也是会对关键字进行分词
		* 但是必须要求doc中的关键字, 符合检索条件中的所有, 才会算作结果, 多个关键字与文档的关系是 and
			{
			  "query":{"match_phrase": {"name":"Litch Rocck"}}  // 检索name同时包含了 Litch Rocck 的记录
			}

		* 完全匹配限制非常的严格, 必须要求doc中出现, 关键字拆词儿后的所有词
		* 有个可调节因子, 可以允许少匹配N个关键字也满足条件, 使用到:slop
			{
				"query":{
				  "match_phrase": {
					"<field>":{
					  "query": "<keywords>",
					  "slop": 1 // 只要 field 字段中, 起码出现了1个 keywords拆词后的词, 就算作返回结果
					}
				  }
				},
			}

		

	multi_match
		* 跟 match 差不多, 可以把检索的关键字, 用于多个指定的字段
			{
				"query": {
					"multi_match":{
						"query":"<keywords>",				// 检索的关键字
						"fields":["field1", "field2"...]	// 匹配的字段
					}
				}
			}
		
		* 只要是任何字段成功匹配, 都算作返回记录
	
	match_phrase_prefix
		* 搜索一组关键字，并且以最后一个关键字开头的前缀进行匹配


	range
		* 区间值匹配, 指定的字段大于, 等于, 小于, 大于等于, 小于等于 指定的值
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
		
		* operation 可以有:
			gt
			lt
			le
			ge
			ne
		* 多个逻辑关系之间的关系是 and
		* range 一般在过滤器中使用比较好
	
	term
		* 精准匹配, 不对关键字进行分词器分析, 文档中指定字段必须包含整个搜索的词汇
			{
				"query": {
				  "term": {
					"<field>":'<value>'
				  }
				}
			}
		
		* 使用term要确定的是这个字段是否"被分析(analyzed)", 默认的字符串是被分析的
		* 通俗理解就是, 因为关键字没分词, 但是你doc却分词了, 就算doc和关键字是一摸一样的, 也没法匹配
		* 关键字没分词, doc也没分词, 内容一样, 就可以匹配
			
	
	terms
		* 跟term一样, 但是允许指定的字段, 匹配多个值，任意出现一个就好
			{
				"query":{
					"terms": {
						"<field>": ["<value1>", "<value2>"]
					}
				}
			}
	
	exists
		* 指定的field必须存在, 并且不能为 null, 就符合条件
			{
				"query":{
				  "exists": {
					"field": "<field>"
				  }
				}
			}
	
	query_string
		* 通过在body中设置查询参数来完成检索
		  "query": {
			"query_string": {
			  "default_field": "name",   // 仅仅检索指定字段，不指定则检索 _all
			  "query": "KevinBlandy"  
			}
		  }
		 
		* 支持强大的表达式
			"query": "name:KevinBlandy AND -hobby:哭"		// WHERE name = "KevinBlandy" AND hobby != "哭"
			"query": "tags:search OR lucence AND create_at:[1993-10-01 2001-01-01]" // WHERE tags IN ("seratc", "lucence") AND create_at BETWEEN 1993-10-01 2001-01-01
 	
	simpl_query_string

	wildcard

	missing
		* 查询null值, 缺省字段


------------------------------
DSL	bool					  |
------------------------------
	# 把多个条件使用布尔逻辑将较小的查询组成更大的查询
	# and 关系用:must
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
		* 该bool返回一个判断条件:name = "KevinBlandy" and age = "23"
	
	# or关系用:should
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
		* 该 bool 返回一个判断条件:name = "Litch" or name = "Roccl"
		
	# 结果取反用:must_not
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
		* 该 bool 返回一个判断条件:name != "Litch" and name != "Roccl"
	
	# 多个条件可以组合
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
		* 该 bool 返回一个判断条件:name = "Litch" and 24 != 24
	
	# minimum_should_match
		* 最低匹配数量
		* 如果指定了 must 语句，该值默认为: 0, 如果没有指定则默认值为 1

	# bool也可以嵌套, 类似于SQL中的子查询

------------------------------
DSL	filter					  |
------------------------------
	# 过滤, 使用查询来限制将与其他子句匹配的文档, 而不会更改计算分数的方式
	# filter 和 query 的对比区别
		* filter, 仅仅只会按照条件过滤出需要的数据
		* query, 会去计算出每个doc相对于搜索条件的相关度, 并且安装相关度进行排序

		* 如果只是进行搜索, 需要把匹配搜索条件的数据先返回, 用query
		* 如果只是要根据条件, 筛选出一部分的数据, 不关注排序, 用filter

		* filter 不需要计算相关度分数, 不需要按照相关度进行排序, 同时还会cache最常使用
		* query 需要计算相关度分数, 还要进行排序, 不能cache结果
	
	# filter 使用位集合(BitSet)来过滤数据，性能很高
		* 一个关键字，对于所有文档，会有一个 BitSet 如果这个文档存在这个关键字，那么值就是1，反之为0
			doc1 doc2 doc3
			1    0    0

	# filter/query 混合使用
		* 先通过 filter 过滤掉大量数据，那么对于 query来说，需要计算得分的文档少了，性能更高

	# range 计算区间值
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

	
	

------------------------------
DSL	分页
------------------------------
	# 使用 from & size		
		{"from":0,	"size":10}

------------------------------
DSL	排序
------------------------------
	# 使用 sort
		"sort": [{ "<field>": { "order": "<desc/asc>" } }]
		"sort": [{ "<field>": "<desc/asc>"}]
	
	# 默认的排序规则是根据元数据中的:_score 来进行排序的(相关度越高的越在前面)

------------------------------
DSL	限制结果字段
------------------------------
	# 使用 _source
		"_source": ["<field1>", "<field2>"]
		"_source": {
			"includes": "<field>",
			"excludes": "<field>"
		}

		* 通过 _source 指定n多个要检索的字段, 字段支持对象导航

