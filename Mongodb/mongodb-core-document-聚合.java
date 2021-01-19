------------------------------
聚合检索
------------------------------
	# 管道和步骤
		* 聚合的过程称为管道 pipeline
		* 管道由多个步骤 stage 组成
		* 每个stage对文档进行计算, 并且把计算后的结果给下一个stage
	
	
		const pipeline = [$stage1, $stage2 .....]
		db.[collection].aggregate(pipeline, {
			...options
		});
	

	$match
		* 过滤，相当于SQL里面的 where

	$project
		* 投影查询，指定要检索的字段

	$sort
		* 排序

	$group
		* 分组，类似于sql里面的 grou by

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
		
	$avg
		* 计算平均值

	$min
		* 获取集合中所有文档对应值得最小值

	$max
		* 获取集合中所有文档对应值得最大值

	$push
		* 在结果文档中插入值到一个数组中

	$first
		* 根据资源文档的排序获取第一个文档数据

	$last
		* 根据资源文档的排序获取最后一个文档数据
	