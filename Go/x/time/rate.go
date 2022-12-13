
------------------------
rate
------------------------
	# 限速器

------------------------
var
------------------------
	const Inf = Limit(math.MaxFloat64)
		* Inf是无限的速率限制；它允许所有的事件（即使burst是零）。

	const InfDuration = time.Duration(math.MaxInt64)
		* InfDuration是当一个 Reservation 不确定时，由Delay返回的持续时间。


------------------------
type
------------------------
	# type Limit float64
		func Every(interval time.Duration) Limit
			* 指定向 Token 桶中放置 Token 的间隔，默认为 秒
	
	# type Limiter struct {
		}
		func NewLimiter(r Limit, b int) *Limiter
			* r 代表每秒可以向 Token 桶中产生多少 token。Limit 实际上是 float64 的别名。
			* b 代表 Token 桶的容量大小，也就是总共存储多少个令牌

		func (lim *Limiter) Allow() bool
		func (lim *Limiter) AllowN(t time.Time, n int) bool
			* 截止到目前/某一刻，目前桶中数目是否至少为 1/n 个，满足则返回 true，同时从桶中消费 n 个 token。
			* 反之返回不消费 Token，false。
			* Allow 实际上就是 AllowN(time.Now(),1)

		func (lim *Limiter) Burst() int
		func (lim *Limiter) Limit() Limit
			* 返回限制和同大小

		func (lim *Limiter) Reserve() *Reservation
		func (lim *Limiter) ReserveN(t time.Time, n int) *Reservation
			* 当调用完成后，无论 Token 是否充足，都会返回一个 *Reservation (预约)对象。
			* Reserve 相当于 ReserveN(time.Now(), 1)。

		func (lim *Limiter) SetBurst(newBurst int)
		func (lim *Limiter) SetBurstAt(t time.Time, newBurst int)	
			* 改变放入 Token 的速率

		func (lim *Limiter) SetLimit(newLimit Limit)
		func (lim *Limiter) SetLimitAt(t time.Time, newLimit Limit)
			* 改变放入 Token 的速率

		func (lim *Limiter) Tokens() float64
		func (lim *Limiter) TokensAt(t time.Time) float64
			* 返回Token数量

		func (lim *Limiter) Wait(ctx context.Context) (err error)
		func (lim *Limiter) WaitN(ctx context.Context, n int) (err error)
			* 一次消费1/n个令牌
			* 如果令牌不足，会阻塞直到Token数量满足需求或者ctx超时
			* WaitN(ctx,1)
	
	# type Reservation struct {
		}
		
		* 预约

		func (r *Reservation) Cancel()
		func (r *Reservation) CancelAt(t time.Time)
			* 如果不想等待，可以调用 Cancel() 方法，该方法会将 Token 归还。
			* Cancel() 相当于调用了: CancelAt(time.Now())

		func (r *Reservation) Delay() time.Duration
		func (r *Reservation) DelayFrom(t time.Time) time.Duration
			* 需要等待的时间。如果等待时间为 0，则说明不用等待。
			* Delay 相当于 DelayFrom(time.Now()).
			
		func (r *Reservation) OK() bool
			* 返回Limiter是否能在最大等待时间内提供所要求的Token数量。
			* 如果OK为 false，Delay 返回 InfDuration，Cancel不做任何事情。

	# type Sometimes struct {
			First    int           // 如果非零，对Do的前N次调用将运行f。
			Every    int           // 如果不为零，则对Do的每N次调用都将运行f。
			Interval time.Duration // 如果不为零，且间隔时间与f的最后一次运行时间相同，则Do将运行f。
		}
		func (s *Sometimes) Do(f func())

------------------------
func
------------------------
