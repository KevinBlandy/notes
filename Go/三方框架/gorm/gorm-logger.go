----------------
logger
----------------
	# logger日志接口
		type Interface interface {
			LogMode(LogLevel) Interface
				* 日志级别
					Silent
					Error
					Warn
					Info

			Info(context.Context, string, ...interface{})
			Warn(context.Context, string, ...interface{})
			Error(context.Context, string, ...interface{})
			Trace(ctx context.Context, begin time.Time, fc func() (string, int64), err error)
		}



	# 创建logger
		newLogger := logger.New(log.New(os.Stdout, "", log.LstdFlags), logger.Config{
			SlowThreshold: time.Second,		// 打印慢SQL的执行时间
			Colorful: false,				// 是否彩色打印
			LogLevel:logger.Silent,			// 日志级别
		})

	
	# 默认的logger
		Default = New(log.New(os.Stdout, "\r\n", log.LstdFlags), Config{
			SlowThreshold: 200 * time.Millisecond,
			LogLevel:      Warn,
			Colorful:      true,
		})
	
	# 全局配置
		db, err := gorm.Open(sqlite.Open("test.db"), &gorm.Config{
		  Logger: newLogger,
		})
	
	# 会话配置
		tx := db.Session(&Session{Logger: newLogger})
	
	# gorm开启debug日志
		func (db *DB) Debug() (tx *DB)

		* 本质上就是把当前db的logger的level设置为了info，并且开启一个Session
			return db.Session(&Session{
				Logger: db.Logger.LogMode(logger.Info),
			})