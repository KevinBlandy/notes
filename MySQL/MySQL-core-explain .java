
----------------
Explain			|
---------------
	# 一条查询语句在经过MySQL查询优化器的各种基于成本和规则的优化会后生成一个所谓的执行计划
		* 这个执行计划展示了接下来具体执行查询的方式, 比如多表连接的顺序是什么, 对于每个表采用什么访问方法来具体执行查询等等
	
	
	# 语法: EXPLAIN [检索语句]
		* DELETE, INSERT, REPLACE, UPDATE语句前边都可以使用它来分析执行计划

		+----+-------------+-------+------------+------+---------------+------+---------+------+------+----------+-------+
		| id | select_type | table | partitions | type | possible_keys | key  | key_len | ref  | rows | filtered | Extra |
		+----+-------------+-------+------------+------+---------------+------+---------+------+------+----------+-------+
		|  1 | SIMPLE      | user  | NULL       | ALL  | NULL          | NULL | NULL    | NULL |    1 |   100.00 | NULL  |
		+----+-------------+-------+------------+------+---------------+------+---------+------+------+----------+-------+


		id
			* 在一个大的查询语句中每个SELECT关键字都对应一个唯一的id
			* 每个表都会对应一条记录,但是这些记录的id值都是相同的

			* 查询优化器可能对涉及子查询的查询语句进行重写, 从而转换为连接查询, 那么俩 SELECT 的id都一样

			* UNION 检索的 id 是 NULL
				* UNION 把多个查询的结果集合并起来并对结果集中的记录进行去重
				* MySQL使用的是内部的临时表,为了把查询的结果集合并起来并去重,所以在内部创建了一个名为<union1, [x]>的临时表
				* id为NULL表明这个临时表是为了合并两个查询的结果集而创建的
			
			* UNION ALL, 不需要为最终的结果集进行去重, 它只是单纯的把多个查询的结果集中的记录合并成一个并返回给用户
			* 所以也就不需要使用临时表, 也就不存在 id 为 NULL 的记录

		
		select_type
			* SELECT关键字对应的那个查询的类型
			* 枚举值
				SIMPLE
					* 不包含UNION或者子查询的查询都算作是SIMPLE
					* 连接查询也算是 SIMPLE

				PRIMARY
					* 对于包含UNION, UNION ALL或者子查询的大查询来说, 它是由几个小查询组成的
					* 其中最左边的那个查询的 select_type 值就是PRIMARY

				UNION
					* 对于包含UNION, UNION ALL的大查询来说, 它是由几个小查询组成的
					* 其中除了最左边的那个小查询以外, 其余的小查询的select_type值就是UNION

				UNION RESULT
					* MySQL选择使用临时表来完成UNION查询的去重工作
					* 针对该临时表的查询的select_type就是UNION RESULT

				SUBQUERY
					* 如果包含子查询的查询语句不能够转为对应的semi-join的形式,
					* 并且该子查询是不相关子查询并且查询优化器决定采用将该子查询物化的方案来执行该子查询时
					* 该子查询的第一个SELECT关键字代表的那个查询的select_type就是SUBQUERY

				DEPENDENT SUBQUERY
					* 如果包含子查询的查询语句不能够转为对应的semi-join的形式,并且该子查询是相关子查询
					* 则该子查询的第一个SELECT关键字代表的那个查询的select_type就是DEPENDENT SUBQUERY

				DEPENDENT UNION
					* 在包含UNION或者UNION ALL的大查询中, 如果各个小查询都依赖于外层查询的话, 那除了最左边的那个小查询之外, 其余的小查询的select_type的值就是DEPENDENT UNION

				DERIVED
					* 对于采用物化的方式执行的包含派生表的查询, 该派生表对应的子查询的select_type就是DERIVED

				MATERIALIZED
					* 当查询优化器在执行包含子查询的语句时, 选择将子查询物化之后与外层查询进行连接查询时, 该子查询对应的select_type属性就是MATERIALIZED

				UNCACHEABLE 
				UNCACHEABLE UNION
			
		table
			* 检索中使用到的表, 如果使用了别名, 则显示别名, 出现在前边的表表示驱动表, 出现在后边的表表示被驱动表
		
		partitions
			* 匹配的分区信息
		
		type
			* 针对单表的访问方法
			* 枚举值
				system
					* 当表中只有一条记录并且该表使用的存储引擎的统计数据是精确的
					* 比如MyISAM, Memory, 那么对该表的访问方法就是system
					
				const
					* 根据主键或者唯一二级索引列与常数进行等值匹配时, 对单表的访问方法就是const

				eq_ref
					* 在连接查询时, 如果被驱动表是通过主键或者唯一二级索引列等值匹配的方式进行访问的(如果该主键或者唯一二级索引是联合索引的话, 所有的索引列都必须进行等值比较)
					* 则对该被驱动表的访问方法就是eq_ref

				ref
					* 当通过普通的二级索引列与常量进行等值匹配时来查询某个表, 那么对该表的访问方法就可能是ref

				fulltext
					* 全文索引

				ref_or_null
					* 当对普通二级索引进行等值匹配查询, 该索引列的值也可以是NULL值时, 那么对该表的访问方法就可能是ref_or_null

				index_merge

				unique_subquery
					* unique_subquery是针对在一些包含IN子查询的查询语句中,
					* 如果查询优化器决定将IN子查询转换为EXISTS子查询,而且子查询可以使用到主键进行等值匹配的话, 那么该子查询执行计划的type列的值就是unique_subquery

				index_subquery
					* index_subquery与unique_subquery类似, 只不过访问子查询中的表时使用的是普通的索引
				range
					* 使用索引获取某些范围区间的记录, 那么就可能使用到range访问方法
				index
					* 以使用索引覆盖, 但需要扫描全部的索引记录时, 该表的访问方法就是index

				ALL
					* 全表扫描

		

		possible_keys
			* 表示在某个查询语句中, 对某个表执行单表查询时可能用到的索引有哪些
			* possible_keys列中的值并不是越多越好, 可能使用的索引越多, 查询优化器计算查询成本时就得花费更长时间, 所以如果可以的话, 尽量删除那些用不到的索引
		
		key
			* 表示实际用到的索引有哪些
		
		key_len
			* 表示当优化器决定使用某个索引执行查询时, 该索引记录的最大长度
		
		ref
			* 当使用索引列等值查询时, 与索引列进行等值匹配的对象信息
		
		rows
			* 预计需要扫描的行数
		
		filtered
			* 某个表经过搜索条件过滤后剩余记录条数的百分比
		
		Extra
			* 一些额外的信息
		

		
	# Json格式的执行计划
		EXPLAIN FORMAT=JSON [select]

		* 以JSON的形式显示结果
	
	# 使用EXPLAIN语句查看了某个查询的执行计划后, 紧接着还可以使用SHOW WARNINGS语句查看与这个查询的执行计划有关的一些扩展信息
		SHOW WARNINGS;