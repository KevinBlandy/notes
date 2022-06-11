package util

import (
	"archive/zip"
	"io"
	"io/fs"
	"log"
	"os"
	"path/filepath"
	"strings"
)

func ZipFolder(folder, targetFile string) error {

	file, err := os.OpenFile(targetFile, os.O_WRONLY|os.O_CREATE|os.O_TRUNC, 0775)
	if err != nil {
		return err
	}

	defer func() {
		if err := file.Close(); err != nil {
			log.Printf("file.Close Err: %s\n", err.Error())
		}
	}()

	zipWriter := zip.NewWriter(file)

	defer func() {
		if err := zipWriter.Close(); err != nil {
			log.Printf("zipWriter.Close Err: %s\n", err.Error())
		}
	}()

	err = filepath.WalkDir(folder, func(path string, d fs.DirEntry, err error) error {

		// 忽略顶级目录
		if strings.EqualFold(folder, path) {
			return nil
		}

		if err != nil {
			return err
		}

		// 根据文件信息构建 header
		fileInfo, err := d.Info()
		if err != nil {
			log.Printf("d.Info Err: %s\n", err.Error())
			return err
		}
		header, err := zip.FileInfoHeader(fileInfo)
		if err != nil {
			log.Printf("zip.FileInfoHeader Err: %s\n", err.Error())
			return err
		}

		// 写入文件的相对路径
		relativePath, err := filepath.Rel(folder, path)
		if err != nil {
			return err
		}

		// 修改Name为相对路径
		header.Name = relativePath

		// 文件还是目录
		if d.IsDir() {
			header.Name += "/"
		} else {
			header.Method = zip.Deflate // 设置压缩算法
		}

		log.Printf("文件: %s\n", header.Name)

		// 在zip中根据header创建文件项
		writer, err := zipWriter.CreateHeader(header)
		if err != nil {
			log.Printf("zipWriter.CreateHeader Err: %s\n", err.Error())
			return err
		}
		defer func() {
			if err := zipWriter.Flush(); err != nil {
				log.Printf("zipWriter.Flush Err: %s\n", err.Error())
			}
		}()

		// 是文件，执行写入
		if !d.IsDir() {
			file, err := os.Open(path)
			if err != nil {
				log.Printf("os.Open Err: %s\n", err.Error())
				return err
			}
			defer func() {
				if err := file.Close(); err != nil {
					log.Printf("file.Close Err: %s\n", err.Error())
				}
			}()
			if _, err = io.Copy(writer, file); err != nil {
				log.Printf("io.Copy Err: %s\n", err.Error())
				return err
			}
		}

		return err
	})

	if err != nil {
		log.Println(err)
	}

	return err
}

// ZipFiles 压缩指定列表的文件
func ZipFiles(zipFile string, files ...string) (err error) {
	fileWriter, err := os.OpenFile(zipFile, os.O_CREATE|os.O_WRONLY|os.O_TRUNC, 0775)
	if err != nil {
		return err
	}
	defer func() {
		err = fileWriter.Close()
	}()

	zipWriter := zip.NewWriter(fileWriter)
	defer func() {
		err = zipWriter.Close()
	}()

	for _, v := range files {
		err := func() (err error) {
			stat, err := os.Stat(v)
			if err != nil {
				return err
			}
			if stat.IsDir() {
				// 忽略目录
				return
			}

			// 创建Header
			header, err := zip.FileInfoHeader(stat)
			if err != nil {
				return err
			}
			header.Method = zip.Deflate

			// 写入header到zip流
			writer, err := zipWriter.CreateHeader(header)
			if err != nil {
				return err
			}

			fileReader, err := os.Open(v)
			if err != nil {
				return err
			}
			defer func() {
				err = fileReader.Close()
			}()

			// 写入数据
			_, err = io.Copy(writer, fileReader)
			return err
		}()

		if err != nil {
			return err
		}
	}

	return
}
