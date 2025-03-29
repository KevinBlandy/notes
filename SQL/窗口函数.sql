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
		
		
		* ORDER BY
			* 当指定了 ORDER BY 时，窗口框架默认是		RANGE BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING	（整个分区）
			* 当未指定 ORDER BY 时，窗口框架默认变为	RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW			（从分区开始到当前行）
		
		* frame_clause 定义当前行相关的计算范围（窗口大小）：
		
			ROWS  BETWEEN {frame_start}
			RANGE BETWEEN {frame_start} AND {frame_end}
			
						
			ROWS
				* 表示以数据行为单位计算窗口偏移量
			
			RANGE
				* 表示以数值（10 天、5000 米）为单位计算窗口偏移量
			
			
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

			
			* Demo
		
				ROWS  BETWEEN 1	PRECEDING AND 1 FOLLOWING
				RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
			
		
	
	
	
	# 开窗函数

		* 开窗函数指定了分析函数工作的数据窗口大小，这个数据窗口大小可能会随着行的变化而变化

		ROW_NUMBER()
			* 为窗口内的每行分配一个唯一的连续整数。
		RANK()
			* 为窗口内的每行分配一个排名，相同值的行拥有相同的排名。
		DENSE_RANK()
			* 与RANK()类似，但不会跳过任何排名。
		LEAD()/LAG()
			* 访问窗口内当前行的前后行的值。
		SUM()/AVG()/MIN()/MAX()/COUNT()
			* 窗口内的聚合值。
		FIRST_VALUE()/LAST_VALUE()
			* 窗口第一行/最后一行
