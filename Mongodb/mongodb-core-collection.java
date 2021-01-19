------------------------------
collection
------------------------------


------------------------------
collection - 命令
------------------------------
	db.createCollection([collection], [config]) 
		* 根据配置创建指定名称的集合
		* conifg
			{
				capped: true （可选）
						* 如果为 true，则创建固定集合。固定集合是指有着固定大小的集合，当达到最大值时，它会自动覆盖最早的文档。
						* 当该值为 true 时，必须指定 size 参数。

				autoIndexId: true （可选）
						* 如为 true，自动在 _id 字段创建索引。默认为 false。

				size: 1024 （可选）
						* 为固定集合指定一个最大值，以千字节计（KB）。
						* 如果 capped 为 true，也需要指定该字段。

				max: 10
						* （可选）指定固定集合中包含文档的最大数量。
				
			}
		
		* 只有当集合中起码写入了一个document, collection才会真正的创建, 落盘存储
	
	db.getCollection([collection])
		* 获取集合, 返回它的引用
		* 但会不会创建, 只有在插入了记录后才会被创建

	show tables
		* 查看当前db下的所有collection
		
	db.[collection].drop()
		* 删除指定的集合
	
	