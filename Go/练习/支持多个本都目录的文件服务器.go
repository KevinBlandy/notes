// onlyFileFs 如果请求资源是目录，不显示列表，返回 404

type onlyFileFs struct {
	fileSystems []http.FileSystem
}

func (o onlyFileFs) Open(name string) (http.File, error) {
	for _, fileSystem := range o.fileSystems {
		file, err := fileSystem.Open(name)
		if err != nil {
			if os.IsNotExist(err) { // 当前fs没找到文件，继续下一个fs
				continue
			}
			return nil, err
		}
		stat, err := file.Stat()
		if err != nil {
			return nil, err
		}
		if stat.IsDir() { // 不显示目录
			//return nil, os.ErrNotExist
			continue // 其他fs可能有同名文件
		}
		return file, err
	}
	return nil, os.ErrNotExist
}



// 使用
router.StaticFS("/static", onlyFileFs{fileSystems: []http.FileSystem{
	http.FS(staticFs),
	http.FS(os.DirFS("./static")),
}})