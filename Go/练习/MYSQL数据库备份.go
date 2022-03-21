package main

import (
	"bytes"
	"context"
	"fmt"
	"log"
	"os"
	"os/exec"
)

func init() {
	log.Default().SetOutput(os.Stdout)
	log.Default().SetFlags(log.Ldate | log.Ltime | log.Lshortfile)
}

func main() {
	err := MySQLDump(context.Background(), "root", "root", "dd", "C:\\Users\\KevinBlandy\\Desktop\\backup.sql")
	if err != nil {
		log.Println(err)
	}
}

// MySQLDump 本地MYSQL数据库备份
func MySQLDump(ctx context.Context, username, password, database, backupFile string) error {

	file, err := os.OpenFile(backupFile, os.O_CREATE|os.O_TRUNC|os.O_WRONLY, 0775)
	if err != nil {
		return err
	}
	defer func() {
		if err := file.Close(); err != nil {
			log.Printf("备份文件资源关闭异常: %s\n", err.Error())
		}
	}()

	stdErr := &bytes.Buffer{}

	args := []string{fmt.Sprintf("-u%s", username), fmt.Sprintf("-p%s", password), database}
	cmd := exec.CommandContext(ctx, "mysqldump", args...)
	cmd.Stdout = file
	cmd.Stderr = stdErr
	if err := cmd.Run(); err != nil {
		log.Printf("备份异常\n %s \n", stdErr.String())
		return err
	}
	return nil
}
s