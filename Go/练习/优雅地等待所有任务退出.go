-------------------
异步并发执行
-------------------
	type GracefullyShutdown interface {
		Shutdown(waitTimeout time.Duration)
	}

	func Shutdown(timeout time.Duration, tasks ...GracefullyShutdown) error {
		exit := make(chan struct{})

		go func() {

			var group sync.WaitGroup

			for _, t := range tasks {
				group.Add(1)
				go func(t GracefullyShutdown) {
					defer group.Done()
					t.Shutdown(timeout)
				}(t)
			}

			group.Wait()
			exit <- struct{}{}
		}()

		timer := time.NewTimer(timeout)
		defer func() {
			_ = timer.Stop()
		}()

		select {
		case <-exit:
			return nil
		case <-timer.C:
			return errors.New("timeout")
		}
	}

	func main() {
		slog.Debug("测试", slog.String("Hello", "World"))

	}

-------------------
同步串行执行
-------------------
	type GracefullyShutdown interface {
		Shutdown(waitTimeout time.Duration)
	}

	func Shutdown(timeout time.Duration, tasks ...GracefullyShutdown) error {

		// 启动时间
		start := time.Now()
		// 剩余的超时时间
		var left time.Duration
		// 定时器，超时时间为参数指定的时间
		timer := time.NewTimer(timeout)

		for _, v := range tasks {

			// 超时时间等于 = 当前时间 - 已经消耗的时间
			left = timeout - time.Since(start)

			c := make(chan struct{})

			go func(t GracefullyShutdown) {
				v.Shutdown(left)
				c <- struct{}{}
			}(v)

			// 重置超时时间
			timer.Reset(left)

			select {
			case <-timer.C:
				return errors.New("timeout")
			case <-c:
				// 继续执行
			}
		}
		return nil
	}

