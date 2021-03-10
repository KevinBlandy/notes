-------------------
viper - 解析到对象
-------------------
	# 配置
		server:
		  port: 80
		  host: "0.0.0.0"
		  read-time-out: "5s"
		  compression:
			enabled: true
			mime-types:
			  - application/json
			  - application/xml
			  - application/javascript
			  - text/html
			  - text/xml
			  - text/plain
			  - text/css
			  - text/javascript
			min-response-size: 2048

		users:
		  - {account: "admin", password: "123456"}
		  - {account: "manager", password: "654321"}
		  - {account: "guest", password: "11111"}

		static-locations:
		  - "/static"
 
	 # 对象
		 type Config struct {
			// 服务器配置
			Server struct {
				Port int `mapstructure:"port"`
				Host string `mapstructure:"host"`
				ReadTimeOut time.Duration `mapstructure:"read-time-out"`			// 默认单位是纳秒：5000000000
				Compression struct {
					Enabled bool `mapstructure:"enabled"`
					MinResponseSize int `mapstructure:"min-response-size"`
					MineTypes []string `mapstructure:"mime-types"`
				} `mapstructure:"compression"`
			} `mapstructure:"server"`

			// 用户账户配置
			Users []*struct{
				Account string `mapstructure:"account"`
				Password string `mapstructure:"password"`
			} `mapstructure:"users"`

			// 静态资源访问路径配置
			StaticLocations []string `mapstructure:"static-locations"`
		}
	
	# 解析
		log.Default().SetOutput(os.Stdout)
		v := viper.New()
		v.AddConfigPath("./config")
		v.AddConfigPath("D:\\config")
		v.SetConfigName("app")

		if err := v.ReadInConfig(); err != nil {
			log.Fatal(err)
		}

		var config = &Config{}
		if err := v.UnmarshalExact(config); err != nil {
			log.Fatal(err)
		}
		
		j, _ := json.MarshalIndent(config, "", "	")
		log.Println(string(j))