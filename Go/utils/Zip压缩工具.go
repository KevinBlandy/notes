
import (
	"archive/zip"
	"io"
	"io/fs"
	"log"
	"os"
	"path/filepath"
	"strings"
)

// ZipDir 压缩folder中的所有文件到 writer
func ZipDir(dir string, writer io.Writer) (err error) {
	zipWriter := zip.NewWriter(writer)

	defer func() {
		if err := zipWriter.Close(); err != nil {
			log.Printf("zipWriter.Close Err: %s\n", err.Error())
		}
	}()

	err = filepath.WalkDir(dir, func(path string, d fs.DirEntry, err error) error {
		if err != nil {
			return err
		}

		// 忽略顶级目录
		if strings.EqualFold(dir, path) {
			return nil
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
		relativePath, err := filepath.Rel(dir, path)
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

		// 根据header创建文件项
		writer, err := zipWriter.CreateHeader(header)
		if err != nil {
			log.Printf("zipWriter.CreateHeader Err: %s\n", err.Error())
			return err
		}

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

// ZipFile 压缩文件列表，文件列表可以包含目录
func ZipFile(files []string, writer io.Writer) (err error) {
	zipWriter := zip.NewWriter(writer)
	defer func() {
		if err := zipWriter.Close(); err != nil {
			log.Printf("zipWriter.Close Err: %s\n", err.Error())
		}
	}()

	for _, file := range files {
		err = func() (err error) {
			fileStat, err := os.Stat(file)
			if err != nil {
				log.Printf("os.Stat Err: %s\n", err.Error())
				return
			}
			if fileStat.IsDir() {
				err = writeDir(file, zipWriter)
			} else {
				err = writeFile(file, zipWriter)
			}
			return
		}()
		if err != nil {
			return
		}
	}
	return
}

func writeFile(file string, zipWriter *zip.Writer) (err error) {
	fileReader, err := os.Open(file)
	if err != nil {
		log.Printf("os.Open Err: %s\n", err.Error())
		return
	}

	defer func() {
		err = fileReader.Close()
	}()

	stat, err := fileReader.Stat()
	if err != nil {
		log.Printf("fileReader.Stat Err: %s\n", err.Error())
		return
	}

	header, err := zip.FileInfoHeader(stat)
	if err != nil {
		log.Printf("zip.FileInfoHeader Err: %s\n", err.Error())
		return
	}

	header.Method = zip.Deflate

	headerWriter, err := zipWriter.CreateHeader(header)
	if err != nil {
		log.Printf("zipWriter.CreateHeader Err: %s\n", err.Error())
		return
	}

	_, err = io.Copy(headerWriter, fileReader)

	return
}

func writeDir(dir string, zipWriter *zip.Writer) (err error) {

	baseDirName := filepath.Base(dir)

	err = filepath.WalkDir(dir, func(path string, d fs.DirEntry, err error) error {
		if err != nil {
			return err
		}

		info, err := d.Info()
		if err != nil {
			log.Printf("d.Info Err: %s\n", err.Error())
			return err
		}

		header, err := zip.FileInfoHeader(info)
		if err != nil {
			log.Printf("zip.FileInfoHeader Err: %s\n", err.Error())
			return err
		}

		if path == dir {
			// 根目录
			header.Name = baseDirName
		} else {
			// 非根目录，计算出相对路径
			relativePath, err := filepath.Rel(dir, path)
			if err != nil {
				log.Printf("filepath.Rel: %s\n", err.Error())
				return err
			}
			header.Name = filepath.Join(baseDirName, relativePath)
		}

		if d.IsDir() {
			header.Name = header.Name + "/"
		} else {
			header.Method = zip.Deflate
		}

		headerWriter, err := zipWriter.CreateHeader(header)
		if err != nil {
			log.Printf("zipWriter.CreateHeader Err: %s\n", err.Error())
			return err
		}

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
			if _, err = io.Copy(headerWriter, file); err != nil {
				log.Printf("io.Copy Err: %s\n", err.Error())
				return err
			}
		}

		return err
	})
	return
}
