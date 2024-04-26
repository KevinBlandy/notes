------------------------------
聚合检索
------------------------------
	# 管道和步骤
		* 聚合的过程称为管道 pipeline
		* 管道由多个步骤 stage 组成
		* 每个 stage 对文档进行计算, 并且把计算后的结果给下一个stage
	
	
		const pipeline = [$stage1, $stage2 .....]

		db.[collection].aggregate(pipeline, {
			...options
		});
	

	$match
		* 过滤，相当于SQL里面的 where
	
	$filter
		* 处理数组字段
			rounds: $filter: {
				  input: "$funding_rounds",		// 指定文档的数组字段
				  as: "round",					// 为数组取一个别名，也可以理解为变量
				  cond: { $gte: ["$$round.raised_amount", 100000000] }	// 用于过滤作为输入的任何数组的条件，选择一个子集
			} 

			* $$ 用来引用在表达式中定义的变量。
			

	$arrayElemAt 
		* 选择数组中特定位置的元素
			first_round: { $arrayElemAt: [ "$funding_rounds", 0 ] },		// 文档 funding_rounds 数组元素中的第一个
			last_round: { $arrayElemAt: [ "$funding_rounds", -1 ] }			// 文档 funding_rounds 数组元素中的最后一个
		
	$slice
		* 在数组中从一个特定的索引开始按顺序返回多个元素
			early_rounds: { $slice: [ "$funding_rounds", 1, 3 ] }		// 文档 funding_rounds 数组元素从索引 1 开始，获取 3 个元素
	
	$size
		* 计算出数组的长度
			total_rounds: { $size: "$funding_rounds" }

	$project
		* 投影查询，指定要检索的字段
		* 可以投影嵌套字段
			db.companies.aggregate([
			  {$project: {
				_id: 0,
				name: 1,		// 检索 name 字段
				ipo: "$ipo.pub_year",					// 检索 ipo 对象的 pub_year 字段
				valuation: "$ipo.valuation_amount",		// 检索 ipo 对象的 valuation_amount 字段
				funders: "$funding_rounds.investments.financial_org.permalink"	// 检索 funding_rounds 数组下的 investments 数组中的对象的 permalink 属性
			  }}
			]).pretty()

	$sort
		* 排序

	$group
		* 分组，类似于sql里面的 grou by
			$group: {
				_id: { founded_year: "$founded_year" },			// _id 指定，根据哪个字段进行分组
				average_number_of_employees: { $avg: "$number_of_employees" }		// 指定聚合计算结果
			} 

	$skip
		* offset

	$limit
		* 结果限制

	$lookup
		* 左外链接

	$geoNear
		* 输出接近某一地理位置的有序文档。

	$sum
		* 计算总和
			total_funding: { $sum: "$funding_rounds.raised_amount" }
			count: { $sum: 1 }

	$avg
		* 计算平均值

	$min
		* 获取集合中所有文档对应值得最小值

	$max
		* 获取集合中所有文档对应值得最大值

	$push
		* 在结果文档中插入值到一个数组中，即将结果的值添加到其在运行过程中所构建的数组中

	$first
		* 根据资源文档的排序获取第一个文档数据

	$last
		* 根据资源文档的排序获取最后一个文档数据

	$mergeObjects
		* 将多个文档合并为单个文档
	
	$unwind
		* 展开（unwind），指定数组字段中的每个元素都形成一个输出文档。
			
			// 原始对象，有一个 2 个元素的数组
			{
				"key1": 1,
				"key2": 2,
				"key3": ["a", "b"]
			}

			// 指定要展开的数组
			{"$unwind": "$key3"},

			// 展开，把其中所有元素都单独拿出来和原始对象组成一个新的文档
			{
				"key1": 1,
				"key2": 2,
				"key3": "a"
			}
			{
				"key1": 1,
				"key2": 2,
				"key3": "b"
			}

		

	$merge 
		* 将聚合管道生成的文档写入集合中，必须是聚合管道的最后一个阶段。
		