{
	"query":{

		"match_all":{
			
		},

		"match":{
			"field":"value"
		},

		"term":{
			"field":"value"		
		},
		"terms":{
			"field":["value1","value2"]	
		}
		"match_phrase":{
			"field":"value"
		}
		"multi_match":{
			"query":"value",
			"fields":["field1,field2"]
		},
		"exists":{
			"field":"field"
	    },
		"bool":{
			"must":{
				"match":{
					"key":"value"
				}
			},
			"filter":{
				"range":{
					"field":{"gt/lt/le/ge/ne":"value"}
				}
			}
		}
	},
	"sort":[
		{"key1":"desc"},
		{"key2":"asc"}
	],
	"from":1,
	"size":2,
	"_source":[],
	"highlight":{
		"fields":{
			"field":{}
		}
	},
	"aggs":{
	},
	"properties":{
	
	}
}

==============================================================================================
query
	query.match_all
		* 检索所有,该值是一个空对象{}

	query.match
		* 检索符合条件的
		* 该值是一个对象,通过kv来组合条件
		* 全文检索,value可以有多个,使用空格隔开,只要key中包含了任意value关键字即满足条件

	query.match_phrase
		* 短语检索,跟全文检索相反,必须是全部符合key=value,才符合条件
	
	query.multi_match
		* 等于同一个值的多个字段
	query.term
		* 跟 match_phrase ,是全文匹配
	query.terms
		* 等一个多个值的一个字段
		* 允许匹配多个值,任意一个值件满足条即可

	query.exists
		* 通过field属性指定的属性不能为null

==============================================================================================
query.bool
	* 它是一个条件,可以由N个子条件构成
	query.bool.must
		* 一个或者多个条件,必须全部满足
	query.bool.should
		* 一个或者多个条件,满足一个即可
	query.bool.must_not
		* 一个或者多个条件,必须全部不满足
	query.bool.filter
		* 一个或者多个过滤条件

==============================================================================================

from
	* 从第几个数据开始检索,limit的第一个参数
size
	* 每页显示的记录数
_source
	* 该值是一个数组,指定要检索的字段,而不是检索所有
	* 通过字符串执行属性,支持.属性导航

==============================================================================================
highlight.fields
	* 高亮设置



--------------------
组件				|
--------------------
match
match_all
match_phrase
multi_match
term
terms
exists

bool
must
should
must_not
filter

range
constant_score

