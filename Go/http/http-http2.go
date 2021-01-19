-----------------------
http2
-----------------------

-----------------------
尝试使用push
-----------------------
	# push
		http.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {
			// 如果实现了http.Pusher接口，就可以使用
			if pusher, ok := w.(http.Pusher); ok {
				// Push is supported.
				if err := pusher.Push("/app.js", nil); err != nil {
					log.Printf("Failed to push: %v", err)
				}
			}
			// ...
		})
	
	# type PushOptions struct {
			Method string
			Header Header
		}
		* 指定Push的方法和header
	
	# type Pusher interface {
			Push(target string, opts *PushOptions) error
		}

		* http2的push接口