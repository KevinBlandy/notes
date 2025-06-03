-----------------------------
GRPC
-----------------------------
	# 站
		https://grpc.io/
		https://doc.oschina.net/grpc

	


-----------------------------
开始
-----------------------------
	# 项目结构
		grpc
		├─client
			├─main.go
		├─demo
			├─demo.proto
		└─serve
			├─main.go


	# 下载对应（语言）的 proto 和 proto-grpc 编译插件

		* Golang

			// protoc-gen-go
			go install google.golang.org/protobuf/cmd/protoc-gen-go@latest

			// protoc-gen-go-grpc
			go install google.golang.org/grpc/cmd/protoc-gen-go-grpc@latest

			* https://github.com/protocolbuffers/protobuf-go/tree/master/cmd/protoc-gen-go
			* https://github.com/grpc/grpc-go/tree/master/cmd/protoc-gen-go-grpc
		
	
	# 定义 demo.proto protobuf

		syntax = "proto3";

		// Go 模块名称
		option go_package = "grpc/demo";

		package demo;

		// 服务定义
		service Greeter {
		  rpc SayHello (HelloRequest) returns (HelloReply) {}
		}

		// 请求消息定义
		message HelloRequest {
		  string name = 1;
		}

		// 响应消息定义
		message HelloReply {
		  string message = 1;
		}

		

	# 生成对应的代码 

		protoc --go_out=. --go_opt=paths=source_relative --go-grpc_out=. --go-grpc_opt=paths=source_relative demo.proto

		--go_out=.
			* 让 protoc 调用 protoc-gen-go 插件，生成 demo.pb.go
		
		 --go-grpc_out=.
		 	*  让 protoc 调用 protoc-gen-go-grpc 插件，生成 demo_grpc.pb.go
		
		paths=source_relative
			* 生成的 .pb.go 文件将直接生成到和 .proto 文件相同的目录下，而不是根据 go_package 或 proto 目录层级创建子目录。


	# 实现服务端 server/main.go
		package main

		import (
			"context"
			"flag"
			"fmt"
			"google.golang.org/grpc"
			pb "grpc/demo"
			"log"
			"net"
		)

		var (
			port = flag.Int("port", 50051, "The server port")
		)

		// server is used to implement helloworld.GreeterServer.
		type server struct {
			pb.UnimplementedGreeterServer
		}

		// SayHello 实现接口定义的方法
		func (s *server) SayHello(_ context.Context, in *pb.HelloRequest) (*pb.HelloReply, error) {
			log.Printf("Received: %v", in.GetName())
			return &pb.HelloReply{Message: "Hello " + in.GetName()}, nil
		}

		func main() {
			flag.Parse()
			lis, err := net.Listen("tcp", fmt.Sprintf(":%d", *port))
			if err != nil {
				log.Fatalf("failed to listen: %v", err)
			}

			// 创建 GRPC 服务器
			s := grpc.NewServer()

			// 注册服务
			pb.RegisterGreeterServer(s, &server{})

			log.Printf("server listening at %v", lis.Addr())

			// 开始监听服务
			if err := s.Serve(lis); err != nil {
				log.Fatalf("failed to serve: %v", err)
			}
		}


	# 实现客户端 client/main.go

		package main

		import (
			"context"
			"flag"
			"google.golang.org/grpc"
			"google.golang.org/grpc/credentials/insecure"
			pb "grpc/demo"
			"log"
			"time"
		)

		const (
			defaultName = "world"
		)

		var (
			addr = flag.String("addr", "localhost:50051", "the address to connect to")
			name = flag.String("name", defaultName, "Name to greet")
		)

		func main() {
			flag.Parse()
			// 创建 GRPC 连接
			conn, err := grpc.NewClient(*addr, grpc.WithTransportCredentials(insecure.NewCredentials()))
			if err != nil {
				log.Fatalf("did not connect: %v", err)
			}
			defer func() {
				_ = conn.Close()
			}()

			// 根据连接创建 GRPC 服务
			c := pb.NewGreeterClient(conn)

			// Ctx
			ctx, cancel := context.WithTimeout(context.Background(), time.Second)
			defer cancel()

			// 调用服务，设置超时时间
			r, err := c.SayHello(ctx, &pb.HelloRequest{Name: *name})
			if err != nil {
				log.Fatalf("could not greet: %v", err)
			}
			log.Printf("Greeting: %s", r.GetMessage())
		}
