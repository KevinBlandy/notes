-------------------------------
semaphore
-------------------------------
	
	"golang.org/x/sync/semaphore"

-------------------------------
var
-------------------------------


-------------------------------
type
-------------------------------

	# type Weighted struct {
		}

		func NewWeighted(n int64) *Weighted
		func (s *Weighted) Acquire(ctx context.Context, n int64) error
			* 阻塞直到资源可用或 ctx Done

		func (s *Weighted) Release(n int64)
		func (s *Weighted) TryAcquire(n int64) bool
			* 尝试获取资源，如果失败，则返回 false

-------------------------------
func
-------------------------------



-------------------------------
demo
-------------------------------
	# Demo

