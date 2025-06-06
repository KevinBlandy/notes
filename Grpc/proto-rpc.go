---------------------
rpc 服务定义
---------------------
	# 通过 service 定义服务

		// SearchService 服务
		service SearchService {
			// 定义一个 rpc 方法
			rpc Search(SearchRequest) returns (SearchResponse);
		}

		
		* 定义无返回值/请求体的服务

			import "google/protobuf/empty.proto";  // 引入空消息类型

			service MyService {
				// 无参数，且无返回值的服务方法
				rpc Ping (google.protobuf.Empty) returns (google.protobuf.Empty);
			}

			// 生成的 go

			type MyServiceServer interface {
				// 无参数，且无返回值的服务方法
				Ping(context.Context, *emptypb.Empty) (*emptypb.Empty, error)
			}
		

		* 通过 stream 管家你定义流式服务

			