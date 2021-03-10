-------------------
viper
-------------------
	# Doc
		https://github.com/spf13/viper
		
		https://www.liwenzhou.com/posts/Go/viper_tutorial/
	

	
	# 读取配置
		v := viper.New()

		// 设置加载目录，可以有多个
		v.AddConfigPath("./config")
		v.AddConfigPath("D:\\config")

		// 设置配置文件名称
		v.SetConfigName("app")
		// 配置文件后缀
		v.SetConfigType("yaml")

		// 读取配置
		if err := v.ReadInConfig(); err != nil {
			log.Fatal(err)
		}