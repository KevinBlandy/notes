---------------------------
Server
---------------------------


---------------------------
Demo
---------------------------
	package main

	import (
		"context"
		"errors"
		"net"
		"os"
		"os/signal"
		"test/gen/api"

		"google.golang.org/grpc"
	)

	// ServiceServer 自定义服务
	type ServiceServer struct {
		// 继承要实现的服务
		api.UnimplementedMemberServiceServer
	}

	func (ServiceServer) Call(ctx context.Context, req *api.Request) (*api.Response, error) {
		return &api.Response{Member: req.Member}, nil
	}
	func (ServiceServer) Subscribe(req *api.Request, serverStream grpc.ServerStreamingServer[api.Response]) error {
		return nil
	}
	func (ServiceServer) Publisher(clientStream grpc.ClientStreamingServer[api.Request, api.Response]) error {
		return nil
	}
	func (ServiceServer) Stream(stream grpc.BidiStreamingServer[api.Request, api.Response]) error {
		return nil
	}

	func main() {

		// 创建 API 服务
		var serviceServer = &ServiceServer{}

		// 创建 GRPC 服务器
		grpcServer := grpc.NewServer()

		// 自动注册服
		api.RegisterMemberServiceServer(grpcServer, serviceServer)

		//// 手动注册服务
		//grpcServer.RegisterService(&api.MemberService_ServiceDesc, serviceServer)

		// 创建监听器
		listener, err := net.Listen("tcp", ":7788")
		if err != nil {
			panic(err)
		}

		// 监听 Kill 信号
		go func() {
			sigCtx, sigCancel := signal.NotifyContext(context.Background(), os.Interrupt, os.Kill)
			defer sigCancel()
			<-sigCtx.Done()

			// 优雅停机
			grpcServer.GracefulStop()
		}()

		// 监听服务
		if err := grpcServer.Serve(listener); err != nil && !errors.Is(err, grpc.ErrServerStopped) {
			panic(err.Error())
		}
	}
