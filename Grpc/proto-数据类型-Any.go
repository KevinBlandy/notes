---------------------
Any
---------------------
	# Any 类型在 protobuf 中的定义如下

		import "google/protobuf/any.proto";

		message Any {
		  string type_url = 1;
		  bytes value = 2;
		}

		* type_url
			* 这个字段包含一个 URL，它指向存储在 value 字段中的数据的类型。
			* 它通常是一个指向类型的全名，类似于 type.googleapis.com/some.package.MessageType，它标识了 value 字段中存储的具体消息类型。
			* 默认值: type.googleapis.com/_packagename_._messagename_

		* value
			* 是一个 字节序列，用于存储具体的数据。它可以是任何类型的消息的序列化表示。
		
		* 它通过封装 type_url 和 value 字段实现消息的动态类型存储。


	# 任意类型，需要先导入  "google/protobuf/any.proto"
	
		// 导入
		import "google/protobuf/any.proto";

		message ErrorStatus {
		  string message = 1;
		  // 定义
		  repeated google.protobuf.Any details = 2;
		}

	# Demo
		package main

		import (
			"fmt"
			"google.golang.org/protobuf/types/known/anypb"
			"grpc/proto"
		)

		func main() {
			// 把一个 Bar 对象序列化为 Any
			anyData, err := anypb.New(&proto.Bar{
				BarName: &[]string{"Bar Name..."}[0],
			})
			if err != nil {
				panic(err)
			}

			// 把 Any 反序列化到指定的对象
			bar := new(proto.Bar)
			if err = anyData.UnmarshalTo(bar); err != nil {
				panic(err)
			}
			// Bar Name...
			fmt.Println(*bar.BarName)
		}

