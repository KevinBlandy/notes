--------------------------
客户端
--------------------------

--------------------------
Demo
--------------------------
	package main

	import (
		"context"
		"fmt"
		"test/gen/api"
		"test/gen/model"

		"google.golang.org/grpc"
		"google.golang.org/grpc/credentials/insecure"
	)

	func main() {

		// 创建 GRPC 连接
		conn, err := grpc.NewClient("localhost:7788", grpc.WithTransportCredentials(insecure.NewCredentials()))
		if err != nil {
			panic(err)
		}
		defer func() {
			_ = conn.Close()
		}()

		// 根据连接创建 GRpc 服务
		serviceClient := api.NewMemberServiceClient(conn)

		// 调用远程服务
		resp, err := serviceClient.Call(context.Background(), &api.Request{Member: &model.Member{
			Id:    new(int64(1)),
			Title: new(""),
		}})
		if err != nil {
			panic(err)
		}
		fmt.Println(resp.String())
	}
