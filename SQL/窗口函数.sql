-----------------------
开窗
-----------------------
	# 开窗
		* 最主要的作用是在不减少原表行数的情况下进行分组排序等计算。
		* 简单理解，就是对查询的结果多出一列，这一列可以是聚合值，也可以是排序值。
		* 分组函数每组只返回一行，而开窗函数每组返回多行。

	# 基本语法

		开窗函数() OVER (
			PARTITION BY 分组字段
			ORDER BY 排序字段 [ASC/DESC]
		)
		
		SELECT 
			列名,
			
			--------------------------
			开窗函数() OVER (
				[PARTITION BY 分组列]
				[ORDER BY 排序列 [ASC|DESC]]
				[frame_clause]
			) AS 别名
			--------------------------
		FROM 表名
		
		* PARTITION BY
			* 分区，按照什么字段进行分区
		
		* frame_clause 定义当前行相关的计算范围（窗口大小）
			
			* 窗口总是位于分区的范围之内，是分区的一个子集。
			* 在指定了分析窗口之后，窗口函数不再基于分区进行分析，而是基于窗口内的数据进行分析。
		
			{ROWS | RANGE | GROUPS} BETWEEN frame_start AND frame_end
			
						
			ROWS
				* 表示以数据行为单位计算窗口偏移量，适合大多数需要明确范围的情况
			
			RANGE
				* 表示以数值（10 天、5000 米）为单位计算窗口偏移量，适用于需要按值而非行数确定范围的情况
			
			GROUPS
				* 按分组偏移：SQL:2011 标准新增，适用于有重复值时的分组处理
				* MYSQL 不支持
			
			
			frame_start
				* 用于定义窗口的起始位置，可选值如下：
				
					N PRECEDING
						* 表示窗口从当前行之前的第 N 行开始
						
					UNBOUNDED PRECEDING
						* 表示窗口从分区第一行开始
					
					CURRENT ROW
						* 表示从窗口当前行开始
			
			frame_end
				* 用于定义窗口的结束位置，可选值如下：
				
					CURRENT ROW
						* 表示窗口到当前行结束
					
					M FOLLOWING
						* 表示窗口到当前行后的第 M 行结束
					
					UNBOUNDED FOLLOWING
						* 表示窗口到分区的最后一行结束

			
			* 默认行为
				* 当有 ORDER BY 时：默认 RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW			-- 开始行，到当前行
				* 当无 ORDER BY 时：默认 RANGE BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING	-- 第一行，到最后行
			
			* Demo
		
				ROWS  BETWEEN 1	PRECEDING AND 1 FOLLOWING
				RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
			
		
	
	
	
	# 开窗函数

		* 开窗函数指定了分析函数工作的数据窗口大小，这个数据窗口大小可能会随着行的变化而变化
		* 聚合窗口函数（许多常见的聚合函数也可以作为窗口函数使用）
			SUM()/AVG()/MIN()/MAX()/COUNT()
		
		* 排名窗口函数（用于对数据进行分组排名）
			
			ROW_NUMBER()
				* 为窗口内的每行分配一个唯一的连续整数。
			RANK()
				* 为窗口内的每行分配一个排名，相同值的行拥有相同的排名。
			DENSE_RANK()
				* 与RANK()类似，但不会跳过任何排名。
			PERCENT_RANK()
			CUME_DIST()
			NTILE()
		
		* 取值窗口函数
			LEAD()/LAG()
				* 访问窗口内当前行的前后行的值。
			FIRST_VALUE()/LAST_VALUE()
				* 窗口第一行/最后一行
			NTH_VALUE()
		
		
