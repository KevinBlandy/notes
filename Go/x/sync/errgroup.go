-------------------------------
semaphore
-------------------------------
	
	"golang.org/x/sync/errgroup"

-------------------------------
var
-------------------------------

-------------------------------
type
-------------------------------
	# type Group struct {
			// contains filtered or unexported fields
		}
		func WithContext(ctx context.Context) (*Group, context.Context)
			* 传入一个父 Context，返回一个新的 Group 和一个新的派生 Context。
			* 一旦该组中有任何一个 Goroutine 返回了非 nil 的错误，派生的 Context 就会被取消（cancel()），从而可以通知其他正在执行的 Goroutine 快速退出。

		func (g *Group) Go(f func() error)
			* 执行任务，如果限制了并发执行数量，又达到了限制则会阻塞

		func (g *Group) SetLimit(n int)
			* 设置最大并发执行的 Goroutine 数量。如果 n <= 0，则不限制。这对于控制数据库连接池、API 请求并发等场景非常有用。

		func (g *Group) TryGo(f func() error) bool
			* 仅当组中当前活动协程的数量低于配置的限制时，TryGo才会在新协程中调用给定的函数。
			* 非阻塞的，返回值报告该协程是否已启动。

		func (g *Group) Wait() error
			* 阻塞等待所有通过 g.Go 启动的 Goroutine 执行完毕。
			* 返回第一个出现的非 nil 错误（如果全部成功则返回 nil）。




-------------------------------
Demo
-------------------------------
