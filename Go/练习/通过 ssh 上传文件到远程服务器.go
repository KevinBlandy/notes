package main

import (
	"errors"
	"fmt"
	"github.com/pkg/sftp"
	"golang.org/x/crypto/ssh"
	"io"
	"log"
	"os"
	"path"
)

func main() {

	// 打开本地文件
	localFile, err := os.Open("C:\\Users\\Laptop\\Desktop\\foo.pdf")
	if err != nil {
		log.Fatalf("Failed to open local file: %s", err)
	}
	defer func() {
		_ = localFile.Close()
	}()

	c, err := Write2RemoteServer("192.168.0.2:22", "root", "root.", "/root/doo/d.pdf", localFile)
	if err != nil {
		fmt.Println(err)
	}

	fmt.Printf("Ok，传输大小：%d\n", c)

}

// Write2RemoteServer 写入数据到远程服务
// host 地址，e.g：192.168.0.2:22
// username 用户名
// password 密码
// file 要写入的路径，如果文件（夹）不存在会创建，如果文件已存在则会覆盖
// read 文件数据
func Write2RemoteServer(host, username, password, file string, read io.Reader) (int64, error) {
	// SSH 客户端配置
	sshConfig := &ssh.ClientConfig{
		User: username,
		Auth: []ssh.AuthMethod{
			ssh.Password(password),
		},
		HostKeyCallback: ssh.InsecureIgnoreHostKey(),
	}

	// 连接到远程服务器
	sshClient, err := ssh.Dial("tcp", host, sshConfig)
	if err != nil {
		return 0, err
	}
	defer func() {
		_ = sshClient.Close()
	}()

	// 创建 SFTP 客户端
	sftpClient, err := sftp.NewClient(sshClient)
	if err != nil {
		return 0, err
	}
	defer func() {
		_ = sftpClient.Close()
	}()

	// 尝试创建文件夹
	fileDir := path.Dir(file)
	if err := sftpClient.MkdirAll(fileDir); err != nil && !errors.Is(err, os.ErrExist) {
		return 0, err
	}

	// 创建远程文件
	remoteFile, err := sftpClient.OpenFile(file, os.O_RDWR|os.O_CREATE|os.O_TRUNC)
	if err != nil {
		return 0, err
	}
	defer func() {
		_ = remoteFile.Close()
	}()

	// 复制文件内容
	return io.Copy(remoteFile, read)
}
