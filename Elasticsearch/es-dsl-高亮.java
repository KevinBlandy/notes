------------------------------
DSL	结果高亮				  |
------------------------------
	# 文档
		https://www.elastic.co/guide/en/elasticsearch/reference/current/search-request-highlighting.html
	
	# 使用 highlight
		{
			"query" : {
				"match": { "<filed>": "<keyworlds>" }
			},
			"highlight" : {
				"fields" : {
					"<filed>" : {}
				}
			}
		}

		* filed 指定要高亮检索的字段,可以通过属性控制高亮的很多行为

	# 响应
		{
		  "took" : 78,
		  "timed_out" : false,
		  "_shards" : {
			"total" : 1,
			"successful" : 1,
			"skipped" : 0,
			"failed" : 0
		  },
		  "hits" : {
			"total" : {
			  "value" : 1,
			  "relation" : "eq"
			},
			"max_score" : 1.2693083,
			"hits" : [
			  {
				"_index" : "goods",
				"_type" : "_doc",
				"_id" : "1",
				"_score" : 1.2693083,
				"_source" : {
				  ...
				},
				"highlight" : {
				  "<filed>" : [
					"<em>带</em><em>我</em>去打球当前"
				  ]
				}
			  }
			]
		  }
		}

		* 响应的结果保函了高亮的结果highlight
