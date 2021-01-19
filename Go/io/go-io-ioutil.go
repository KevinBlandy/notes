---------------------
outil
---------------------
	# io的工具包

	# 方法
		func readAll(r io.Reader, capacity int64) (b []byte, err error)
		func ReadAll(r io.Reader) ([]byte, error) 
		func ReadFile(filename string) ([]byte, error)
		func WriteFile(filename string, data []byte, perm os.FileMode) error
		func ReadDir(dirname string) ([]os.FileInfo, error)
		func NopCloser(r io.Reader) io.ReadCloser
	

	# 不需要关闭的 Reader
		type nopCloser struct {
			io.Reader
		}

		func (nopCloser) Close() error 
			* 空实现的close，返回 nil

		func NopCloser(r io.Reader) io.ReadCloser
			* 可以通过这个方法，包装构造一个不需要执行资源释放的Reader

	# 一个丢弃流，类似于把数据写入到 /dev/null 中
		
		type devNull int

		var Discard io.Writer = devNull(0)	// 实现了Writer接口

		var _ io.ReaderFrom = devNull(0)	// 实现了ReaderFrom接口

		func (devNull) Write(p []byte) (int, error)
		func (devNull) WriteString(s string) (int, error) 
		func (devNull) ReadFrom(r io.Reader) (n int64, err error)
	
