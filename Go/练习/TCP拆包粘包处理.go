/*
+----------------------+--------------------------------------------+
| Length (8 byte)      | Body                                       |
+----------------------+--------------------------------------------+
*/

package main

import (
	"encoding/binary"
	"fmt"
	"io"
	"log"
	"net"
	"os"
	"time"
)
var (
	clog = log.New(os.Stdout, "client - ", log.LstdFlags)
	slog = log.New(os.Stdout, "server - ", log.LstdFlags)
)

func main() {
	go Server()
	go Client()

	select {}
}

func Client(){
	addr, err := net.ResolveTCPAddr("tcp4", "127.0.0.1:1024")
	if err != nil {
		clog.Println(err)
		return
	}
	conn, err := net.DialTCP("tcp4", nil, addr)
	if err != nil {
		clog.Println(err)
		return
	}

	// 异步读取
	go func() {
		for {
			// 读取Header
			length, err := ReadHeader(conn)
			if err != nil && err != io.EOF {
				conn.Close()
				return
			}

			// 读取body
			dataBuffer, err := ReadBody(conn, length)
			if err != nil{
				conn.Close()
				return
			}

			data := string(dataBuffer)
			clog.Printf("客户端收到消息：%s\n", data)
		}
	}()

	// 循环写入
	var count = 1
	for {
		request := fmt.Sprintf("这是客户端的第 %d 条消息", count)

		// 写入Header
		WriteHeader(conn, uint64(len(request)))

		// 写入body
		_, err = io.WriteString(conn, request)
		if err != nil {
			clog.Printf("写入异常：%s\n", err.Error())
			return
		}
		count ++
		time.Sleep(time.Second)
	}
}

// 服务器
func Server (){
	addr, err := net.ResolveTCPAddr("tcp4", "0.0.0.0:1024")
	if err != nil {
		slog.Fatal(err)
	}
	server, err := net.ListenTCP("tcp4", addr)
	if err != nil {
		slog.Fatal(err)
	}
	for {
		conn, err := server.AcceptTCP()
		go ConnHandler(conn, err)
	}
}
func ConnHandler (conn *net.TCPConn, err error){
	if err != nil{
		slog.Printf("链接异常:%s\n", err.Error())
		return
	}
	for {
		// 读取Header
		length, err := ReadHeader(conn)
		if err != nil && err != io.EOF {
			conn.Close()
			return
		}

		// 读取body
		dataBuffer, err := ReadBody(conn, length)
		if err != nil{
			conn.Close()
			return
		}

		data := string(dataBuffer)
		slog.Printf("服务器收到消息：%s\n", data)

		// 给客户的响应数据
		response := fmt.Sprintf("我是服务器，我收到了你的消息。[%s]", time.Now().Format("2006-01-02 15:04:05.0000000"))

		// 写入Header
		WriteHeader(conn, uint64(len(response)))

		// 写入body
		_, err = io.WriteString(conn, response)
		if err != nil {
			slog.Printf("写入异常：%s\n", err.Error())
			conn.Close()
		}
	}
}

// 读取长度
func ReadHeader(reader io.Reader) (uint64, error) {
	lengthBuffer := make([]byte, 8, 8)
	_, err := io.ReadFull(reader, lengthBuffer)
	if err != nil && err != io.EOF {
		slog.Printf("Header读取异常:%s\n", err.Error())
		return 0, err
	}
	length, count := binary.Uvarint(lengthBuffer)
	if count <= 0 {
		return 0, fmt.Errorf("解码Header异常(binary.Uvarint):%d", count)
	}
	return length, nil
}

// 读取Body
func ReadBody(reader io.Reader, length uint64) ([]byte, error){
	dataBuffer := make([]byte, length, length)
	_, err := io.ReadFull(reader, dataBuffer)
	if err != nil{
		slog.Printf("Body读取异常:%s\n", err.Error())
		return nil, err
	}
	return dataBuffer, nil
}

// 写入Header
func WriteHeader(writer io.Writer, length uint64) (int, error) {
	lengthBuffer := make([]byte, 8, 8)
	binary.PutUvarint(lengthBuffer, length)
	count, err := writer.Write(lengthBuffer)
	if err != nil {
		log.Printf("Header写入异常:%s\n", err.Error())
	}
	return count, err
}

