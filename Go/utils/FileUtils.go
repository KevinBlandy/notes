

// 复制文件
func copy(resource, target *os.File) error {
	var buffer = make([]byte, 1024 * 4)
	for {
		count, err := resource.Read(buffer)
		if err == io.EOF {
			break
		}
		if err != nil {
			return err
		}
		target.Write(buffer[0:count])
	}
	return nil
}


// TODO 文件遍历

// TODO 插入内容

// Copy整个目录

// 删除整个目录


