-------------------
interceptor
-------------------
	# 拦截器
		* 文档
			https://grpc.io/docs/guides/interceptors/
		
		* gRPC 一共有 4 种拦截器，按 “端 × 调用类型” 组合
			Unary
				UnaryClientInterceptor
				UnaryServerInterceptor
				
			Stream
				StreamServerInterceptor
				StreamClientInterceptor
		
--------------------------------------
UnaryClientInterceptor
--------------------------------------
	# 一元调用，客户端拦截器
		* 在连接级别添加，拦截发出去的请求，在请求发出前/后进行执行。
		* grpc.WithUnaryInterceptor 添加单个拦截器，多次调用会覆盖前面的
		* grpc.WithChainUnaryInterceptor 可以添加多个，依次执行，也可以多次调用，深度优先。
		* 单个拦截器和拦截器链可以并存，最先添加的最先执行。

		type UnaryClientInterceptor func(ctx context.Context, method string, req, reply any, cc *ClientConn, invoker UnaryInvoker, opts ...CallOption) error
			ctx
				* 用户调用时传入的 ctx 

			method
				* 调用的方法，如 /api.MemberService/Call
			req
				* 即将发出的请求
				* 可以对其进行修改
			reply
				* 返回值的指针（gRPC 会把响应反序列化进去）
				* 可以对其进行修改
			cc
				*  连接对象   
			invoker
				* 继续下一个调用的句柄
					func(ctx context.Context, method string, req, reply any, cc *ClientConn, opts ...CallOption) error
			opts
				* 设置的选项  
			error
				*  错误信息
		
	# Demo
		grpc.NewClient("localhost:7788",
			grpc.WithChainUnaryInterceptor(func(ctx context.Context, method string, req, reply any, cc *grpc.ClientConn, invoker grpc.UnaryInvoker, opts ...grpc.CallOption) error {
				fmt.Println("[interceptor-1] 请求", req)
				err := invoker(ctx, method, req, reply, cc, opts...)
				fmt.Println("[interceptor-1] 响应", reply)
				return err
			}, func(ctx context.Context, method string, req, reply any, cc *grpc.ClientConn, invoker grpc.UnaryInvoker, opts ...grpc.CallOption) error {
				fmt.Println("[interceptor-2] 请求", req)
				err := invoker(ctx, method, req, reply, cc, opts...)
				fmt.Println("[interceptor-2] 响应", reply)
				return err
			}),
		)
		// [interceptor-1] 请求 member:{id:10028} 
		// [interceptor-2] 请求 member:{id:10028}
		// [interceptor-2] 响应 member:{id:10028}
		// [interceptor-1] 响应 member:{id:8888}

--------------------------------------
UnaryServerInterceptor
--------------------------------------
	# 一元调用，服务端拦截器

		* 在创建服务的时候以 ServerOption 形式进行添加，主要作用是在服务端进行拦截，在 Handler/Interceptor 执行之前/后执行。
		* grpc.UnaryInterceptor 添加单个拦截器，多次调用会 panic：panic: The unary server interceptor was already set and may not be reset.
		* grpc.ChainUnaryInterceptor 可以添加多个，按照添加顺序依次执行，可以多次调用。深度优先。
		* 单个拦截器和拦截器链可以并存，按添加顺序执行

		type UnaryServerInterceptor func(ctx context.Context, req any, info *UnaryServerInfo, handler UnaryHandler) (resp any, err error)

			ctx
				* 当前请求的 context，可以读取到 metadata
			
			req
				* 已经解码后的 protobuf 请求对象，需要自己进行转换
			
			info
				* 一元请求信息，目前就俩属性
					Server any
					FullMethod string
				
			handler
				* 继续执行调用链的句柄，并且可以获取到执行结果，其结果就是上个调用返回的结果。
					UnaryHandler func(ctx context.Context, req any) (any, error)
				
			resp
				* 返回给客户端的响应
			
			err
				* 返回给客户端的异常
	
	# Demo	
		grpc.NewServer(
			grpc.UnaryInterceptor(func(ctx context.Context, req any, info *grpc.UnaryServerInfo, handler grpc.UnaryHandler) (resp any, err error) {
				fmt.Println("[filter-3] 执行前", req)
				ret, err := handler(ctx, req)
				fmt.Println("[filter-3] 执行后", ret)
				return ret, err
			}),
			grpc.ChainUnaryInterceptor(
				func(ctx context.Context, req any, info *grpc.UnaryServerInfo, handler grpc.UnaryHandler) (resp any, err error) {
					fmt.Println("[filter-1] 执行前", req)
					ret, err := handler(ctx, req)
					fmt.Println("[filter-1] 执行后", ret)
					return ret, err
				}, func(ctx context.Context, req any, info *grpc.UnaryServerInfo, handler grpc.UnaryHandler) (resp any, err error) {
					fmt.Println("[filter-2] 执行前", req)
					ret, err := handler(ctx, req)
					fmt.Println("[filter-2] 执行后", ret)
					return ret, err
				},
			),
		)

		// [filter-3] 执行前 member:{id:10028}
		// [filter-1] 执行前 member:{id:10028}
		// [filter-2] 执行前 member:{id:10028}
		// [filter-2] 执行后 member:{id:10028}
		// [filter-1] 执行后 member:{id:10028}
		// [filter-3] 执行后 member:{id:10028}

--------------------------------------
StreamClientInterceptor
--------------------------------------
	# 流式客户端拦截器 
		* 在流创建前后进行拦截。
		* 通过 grpc.WithStreamInterceptor 添加单个拦截器，可以多次调用，但是后面的会覆盖前面的。
		* 通过 grpc.WithChainStreamInterceptor 添加多个拦截器，可以多次调用，深度优先遍历。
		* 单个、多个都同时存在，则按照添加顺序依次执行。

		type StreamClientInterceptor func(ctx context.Context, desc *StreamDesc, cc *ClientConn, method string, streamer Streamer, opts ...CallOption) (ClientStream, error)
		
			ctx
				* 客户端调用时传进来的 ctx
			
			desc
				* 流描述
					type StreamDesc struct {
						StreamName string        // the name of the method excluding the service
						Handler    StreamHandler // the handler called for the method
						ServerStreams bool // indicates the server can perform streaming sends
						ClientStreams bool // indicates the client can perform streaming sends
					}

			cc
				* 连接对象
			
			method
				* 方法
			
			streamer
				* 创建流的句柄，用该方法，即创建流
					func(ctx context.Context, desc *StreamDesc, cc *ClientConn, method string, opts ...CallOption) (ClientStream, error)
				* 可以对流进行包装

			opts
				* 设置的选项
			
			ClientStream
			error
				* 返回创建的流和异常信息

	# Demo
		grpc.NewClient("localhost:7788",
			grpc.WithTransportCredentials(insecure.NewCredentials()),
			grpc.WithStreamInterceptor(func(ctx context.Context, desc *grpc.StreamDesc, cc *grpc.ClientConn, method string, streamer grpc.Streamer, opts ...grpc.CallOption) (grpc.ClientStream, error) {
				fmt.Println("拦截器1 前置执行")
				r, err := streamer(ctx, desc, cc, method, opts...)
				fmt.Println("拦截器1 后置执行")
				return r, err
			}),
			grpc.WithChainStreamInterceptor(func(ctx context.Context, desc *grpc.StreamDesc, cc *grpc.ClientConn, method string, streamer grpc.Streamer, opts ...grpc.CallOption) (grpc.ClientStream, error) {
				fmt.Println("拦截器2 前置执行")
				r, err := streamer(ctx, desc, cc, method, opts...)
				fmt.Println("拦截器2 后置执行")
				return r, err
			}, func(ctx context.Context, desc *grpc.StreamDesc, cc *grpc.ClientConn, method string, streamer grpc.Streamer, opts ...grpc.CallOption) (grpc.ClientStream, error) {
				fmt.Println("拦截器3 前置执行")
				r, err := streamer(ctx, desc, cc, method, opts...)
				fmt.Println("拦截器3 后置执行")
				return r, err
			}),
		)
		// 拦截器1 前置执行
		// 拦截器2 前置执行
		// 拦截器3 前置执行
		// 拦截器4 前置执行
		// 拦截器4 后置执行
		// 拦截器3 后置执行
		// 拦截器2 后置执行
		// 拦截器1 后置执行


--------------------------------------
StreamServerInterceptor
--------------------------------------
	# 服务端流式拦截器
		* 在流式 handler 方法执行的钱后执行。
		* 通过 grpc.StreamInterceptor 方法添加单个拦截器，多次执行会崩溃 panic: The stream server interceptor was already set and may not be reset.
		* 通过 grpc.ChainStreamInterceptor 添加多个拦截器，可以多次添加，深度优先执行。
		* 单个、多个拦截器可同时存在。按照添加顺序执行。

		type StreamServerInterceptor func(srv any, ss ServerStream, info *StreamServerInfo, handler StreamHandler) error

			srv
				*  service 实例（*ServiceServer）   
			
			ss
				*  流对象，用于 Recv/Send/读 ctx/操作 metadata
			
			info
				* 流描述
					type StreamServerInfo struct {
						FullMethod string
						IsClientStream bool
						IsServerStream bool
					}
			
			handler
				* 执行 handler 的句柄，执行方法，即开始执行流式服务端方法
					func(srv any, stream ServerStream) error
				* 方法结束，即表示流执行结束
			
			errror
				* 最终返回的异常信息
	
	# Demo
		grpc.NewServer(
			grpc.StreamInterceptor(func(srv any, ss grpc.ServerStream, info *grpc.StreamServerInfo, handler grpc.StreamHandler) error {
				fmt.Println("拦截器3 前置执行")
				err := handler(srv, ss)
				fmt.Println("拦截器3 后执行")
				return err
			}),
			grpc.ChainStreamInterceptor(
				func(srv any, ss grpc.ServerStream, info *grpc.StreamServerInfo, handler grpc.StreamHandler) error {
					fmt.Println("拦截器1 前置执行")
					err := handler(srv, ss)
					fmt.Println("拦截器1 后执行")
					return err
				},
				func(srv any, ss grpc.ServerStream, info *grpc.StreamServerInfo, handler grpc.StreamHandler) error {
					fmt.Println("拦截器2 前置执行")
					err := handler(srv, ss)
					fmt.Println("拦截器2 后执行")
					return err
				},
			),
		)

		// 拦截器3 前置执行
		// 拦截器1 前置执行
		// 拦截器2 前置执行
		// 拦截器2 后执行
		// 拦截器1 后执行
		// 拦截器3 后执行