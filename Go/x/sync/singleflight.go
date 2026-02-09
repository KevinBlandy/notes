--------------------------------------
singleflight
--------------------------------------
	# 同步库
		
		golang.org/x/sync/singleflight
		https://pkg.go.dev/golang.org/x/sync/singleflight
	
--------------------------------------
type
--------------------------------------

	# type Group struct {
			// contains filtered or unexported fields
		}
		func (g *Group) Do(key string, fn func() (interface{}, error)) (v interface{}, err error, shared bool)
		func (g *Group) DoChan(key string, fn func() (interface{}, error)) <-chan Result
		func (g *Group) Forget(key string)
	
	
	# type Result struct {
			Val    interface{}
			Err    error
			Shared bool
		}

--------------------------------------
func
--------------------------------------