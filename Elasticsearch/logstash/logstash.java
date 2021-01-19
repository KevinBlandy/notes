----------------
logstash		|
----------------
	# 下载
		https://www.elastic.co/cn/downloads/logstash
	



----------------
导入测试数据	|
----------------
	# 下载测试数据
		https://grouplens.org/datasets/movielens/
		http://files.grouplens.org/datasets/movielens/ml-latest.zip
	
	# 解压测试数据, 然后在解压目录添加配置文件: logstash.conf
		input {
		  file {
			path => "D:\elasticsearch\ml-latest\ml-latest\movies.csv"
			start_position => "beginning"
			sincedb_path => "/dev/null"
		  }
		}

		* 通过 path, 指定 movies.csv 数据文件
	
	# 启动logstash
		logstash -f logstash.conf

		* 通过 -f 指定 logstash.conf 配置文件的目录
	


		