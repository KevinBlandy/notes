package main

import (
	"io"
	"io/fs"
	"log"
	"os"
	"path/filepath"
)

func init() {
	log.Default().SetOutput(os.Stdout)
	log.Default().SetFlags(log.Ldate | log.Ltime | log.Lshortfile)
}

func main() {
	err := CopyFolder("C:\\mvnrepository", "C:\\mvnrepository-copy")
	if err != nil {
		log.Println(err.Error())
	}

}

// CopyFolder 复制文件夹
func CopyFolder(source, target string) error {

	_, err := os.Stat(target)

	if err != nil {
		if os.IsNotExist(err) {
			if err := os.MkdirAll(target, 0775); err != nil {
				return err
			}
		} else {
			return err
		}
	}

	return filepath.WalkDir(source, func(path string, d fs.DirEntry, err error) error {
		// 计算相对路径
		relPath, err := filepath.Rel(source, path)
		if err != nil {
			return err
		}

		absPath := filepath.Join(target, relPath)

		if d.IsDir() {
			_, err := os.Stat(absPath)
			if os.IsNotExist(err) {
				return os.MkdirAll(absPath, d.Type())
			}
			return err
		}

		return func() error {
			writeFile, err := os.OpenFile(absPath, os.O_WRONLY|os.O_CREATE, d.Type())

			if err != nil {
				return err
			}
			defer func() {
				if err := writeFile.Close(); err != nil {
					log.Printf("资源文件关闭异常: %s\n", err)
				}
			}()

			readFile, err := os.Open(path)
			if err != nil {
				return err
			}
			defer func() {
				if err := readFile.Close(); err != nil {
					log.Printf("资源文件关闭异常: %s\n", err)
				}
			}()
			_, err = io.Copy(writeFile, readFile)
			return err
		}()
	})
}
