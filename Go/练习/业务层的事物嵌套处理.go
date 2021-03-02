-------------------
说明
-------------------
	# 类似于Spring中的声明式事物处理
	# 方法运行在事务中，事务方法直接的相互调用，会保证所有方法都使用的同一个事务
	# 事务方法支持递归调用
	# 事务方法的固定调用模板
		1. 获取连接
		2. 在defer中回滚事务
		3. 执行业务方法/调用其他的业务方法
		4. 提交事务


-------------------
实现
-------------------
	# Dao层设计
		package dao
		import (
			"context"
			"database/sql"
			"fmt"
			"gorm.io/driver/mysql"
			"gorm.io/gorm"
			"gorm.io/gorm/schema"
			"log"
		)

		var (
			ctxKeySession = "_db_session"	// 在ctx中存储数据库链接Session
			ctxKeyDepth = "_db_depth"		// 在ctx中存储调用深度
		)


		// 全局数据库
		var DB *gorm.DB

		// 初始化数据库
		func init (){
			const (
				host string = "localhost"		// 主机
				port int = 3306					// 端口
				db string = "demo"				// 数据库
				username string = "root"		// 用户名
				password string = "root"		// 密码
			)
			gormDB, err := gorm.Open(mysql.Open(fmt.Sprintf("%s:%s@tcp(%s:%d)/%s?charset=utf8mb4&parseTime=True&loc=Local", username, password, host, port, db)),
				&gorm.Config{
					NamingStrategy: schema.NamingStrategy{
						SingularTable: true,		// 表名称单数
					},
					QueryFields: false,				// 检索时列出所有列
					PrepareStmt: true,				// 缓存预编译SQL
				})
			if err != nil {
				log.Fatal(err)
			}

			DB = gormDB.Debug()
		}

		// 从ctx获取数据库连接，如果不存在，则创建新的
		func GetSession(ctx *context.Context, txOptions *sql.TxOptions) *gorm.DB {
			var session, ok = (*ctx).Value(ctxKeySession).(*gorm.DB)
			if ok {
				*ctx = context.WithValue(*ctx, ctxKeyDepth, (*ctx).Value(ctxKeyDepth).(int) + 1)
				log.Printf("获取已经存在的Session: %d\n", (*ctx).Value(ctxKeyDepth).(int))
			} else {
				log.Println("创建了新的Session")
				session = DB.Begin(txOptions)
				*ctx = context.WithValue(*ctx, ctxKeySession, session)
				*ctx = context.WithValue(*ctx, ctxKeyDepth, 0)
			}
			return session
		}

		// 尝试回滚，如果不是顶层调用，则忽略
		func TryRollback(ctx context.Context)  {
			if depth, ok := ctx.Value(ctxKeyDepth).(int); ok {
				if depth == 0 {
					if session, ok := ctx.Value(ctxKeySession).(*gorm.DB); ok {
						log.Printf("事务回滚：%d\n", depth)
						session.Rollback()
					} else {
						//TODO 没有读取到Session
					}
				}
			} else {
				// TODO 没读取到Depth
			}
		}

		// 尝试提交，如果不是顶层调用则忽略
		func TryCommit(ctx context.Context){
			if depth, ok := ctx.Value(ctxKeyDepth).(int); ok {
				if depth == 0 {
					if session, ok := ctx.Value(ctxKeySession).(*gorm.DB); ok {
						log.Printf("事务提交：%d\n", depth)
						session.Commit()
					} else {
						//TODO 没有读取到Session
					}
				}
			} else {
				// TODO 没读取到Depth
			}
		}


	# 业务层的调用
		func main() {
			// 入口方法调用使用，context.Background()
			if err := CreateUser(context.Background(), 3); err != nil {
				log.Printf("执行异常了:%s\n", err.Error())
			}
		}

		func Server(ctx context.Context) (interface{}, error){
			// 1, 获取连接
			db := dao.GetSession(&ctx, nil)
			// 2, 始终回滚事务，再已经提交了情况下再次执行回滚，数据不会有影响
			defer func() {
				dao.TryRollback(ctx)
			}()

			// 执行业务逻辑，或者调用其他的业务方法
			var now time.Time
			if err := db.Raw("SELECT NOW()").Scan(&now).Error; err != nil {
				return nil, err
			}

			// 3, 没有异常，提交事务
			dao.TryCommit(ctx)

			// 返回业务结果
			return "result", nil
		}