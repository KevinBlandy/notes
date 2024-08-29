package main

import (
	"encoding/binary"
	"encoding/hex"
	"fmt"
	"net"
)

func main() {
	// 解析 IP
	ip := net.ParseIP("112.123.8.246")

	// 转换为 IPv4
	ip = ip.To4()

	// 编码为Hex
	fmt.Println(ip)
	fmt.Println(hex.EncodeToString(ip)) // 707b08f6

	// 编码 IP 为无符号 int32
	ipNumber := binary.BigEndian.Uint32(ip)
	fmt.Println(ipNumber) // 1887111414

	var parsedIp = make(net.IP, 4)

	// 解码无符号 int32 为 IP
	binary.BigEndian.PutUint32(parsedIp, ipNumber)

	// 编码为Hex
	fmt.Println(parsedIp)
	fmt.Println(hex.EncodeToString(parsedIp)) // 707b08f6
}

