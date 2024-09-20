------------------------
bufio
------------------------

------------------------
变量
------------------------
	
	const (
		MaxScanTokenSize = 64 * 1024
	)
	
	var (
		ErrInvalidUnreadByte = errors.New("bufio: invalid use of UnreadByte")
		ErrInvalidUnreadRune = errors.New("bufio: invalid use of UnreadRune")
		ErrBufferFull        = errors.New("bufio: buffer full")
		ErrNegativeCount     = errors.New("bufio: negative count")
	)
	var (
		ErrTooLong         = errors.New("bufio.Scanner: token too long")
		ErrNegativeAdvance = errors.New("bufio.Scanner: SplitFunc returns negative advance count")
		ErrAdvanceTooFar   = errors.New("bufio.Scanner: SplitFunc returns advance count beyond input")
		ErrBadReadCount    = errors.New("bufio.Scanner: Read returned impossible count")
	)
	var ErrFinalToken = errors.New("final token")

------------------------
type
------------------------
	# type ReadWriter struct {
			*Reader
			*Writer
		}

		func NewReadWriter(r *Reader, w *Writer) *ReadWriter
	
	# type Reader struct
		
		* 带缓冲区的Reader
		
		func NewReader(rd io.Reader) *Reader
		func NewReaderSize(rd io.Reader, size int) *Reader
			* 默认的size是: defaultBufSize = 4096

		func (b *Reader) Buffered() int
		func (b *Reader) Discard(n int) (discarded int, err error)
		func (b *Reader) Peek(n int) ([]byte, error)
		func (b *Reader) Read(p []byte) (n int, err error)
		func (b *Reader) ReadByte() (byte, error)
		func (b *Reader) ReadBytes(delim byte) ([]byte, error)
		func (b *Reader) ReadLine() (line []byte, isPrefix bool, err error)
			* 这是一个比较低级的Api，大多数调用者应该使用
				ReadBytes('\n')
				ReadString('\n')
			* 来代替或使用Scanner

		func (b *Reader) ReadRune() (r rune, size int, err error)
		func (b *Reader) ReadSlice(delim byte) (line []byte, err error)
		func (b *Reader) ReadString(delim byte) (string, error)
			* 根据指定分隔符读取数据

		func (b *Reader) Reset(r io.Reader)
		func (b *Reader) Size() int
		func (b *Reader) UnreadByte() error
		func (b *Reader) UnreadRune() error
		func (b *Reader) WriteTo(w io.Writer) (n int64, err error)
	
	# type Scanner struct
		
		* 扫描器
		
		func NewScanner(r io.Reader) *Scanner
		

		func (s *Scanner) Buffer(buf []byte, max int)
		func (s *Scanner) Err() error
			* 返回异常信息，如果是 EOF 异常，则返回 nil
				// 源码如下
				if s.err == io.EOF {
					return nil
				}
				return s.err

		func (s *Scanner) Scan() bool
			* 读取下一个分隔符，返回结果表示是否还有数据
			* 如果读取到末尾，或者是异常则返回 false
			* 此时可以通过 Err() 方法获取到异常

		func (s *Scanner) Split(split SplitFunc)
			* 指定分隔符，如果不指定，会使用newline字符作为分隔符
			* 其它的分隔函数还包括 ScanRunes 和 ScanWords,皆在bufio包中。

		func (s *Scanner) Bytes() []byte
		func (s *Scanner) Text() string
			* 以字节还是字符的形式读取数据
	
	# type SplitFunc func(data []byte, atEOF bool) (advance int, token []byte, err error)
		* 用于指定分隔符

	# type Writer struct
		
		* 带缓冲区的Writer

		func NewWriter(w io.Writer) *Writer
		func NewWriterSize(w io.Writer, size int) *Writer

		func (b *Writer) Available() int
			* 未使用的缓存大小

		func (b *Writer) AvailableBuffer() []byte
			* AvailableBuffer返回一个具有b.Available()容量的空缓冲区。这个缓冲区的目的是被追加到紧接着的Write调用中并传递给它。
			* 该缓冲区只在对b进行下一次写操作之前有效。
				return b.buf[b.n:][:0]

		func (b *Writer) Buffered() int
		func (b *Writer) Flush() error
		func (b *Writer) ReadFrom(r io.Reader) (n int64, err error)
		func (b *Writer) Reset(w io.Writer)
			* 丢弃还没有flush的缓存的内容，清除错误并把它的输出传给参数中的writer

		func (b *Writer) Size() int
		func (b *Writer) Write(p []byte) (nn int, err error)
		func (b *Writer) WriteByte(c byte) error
		func (b *Writer) WriteRune(r rune) (size int, err error)
		func (b *Writer) WriteString(s string) (int, error)

------------------------
方法
------------------------
	func ScanBytes(data []byte, atEOF bool) (advance int, token []byte, err error)
		* 扫描字节
	func ScanLines(data []byte, atEOF bool) (advance int, token []byte, err error)
		* 扫描一行
	func ScanRunes(data []byte, atEOF bool) (advance int, token []byte, err error)
		* 扫描单个字符
	func ScanWords(data []byte, atEOF bool) (advance int, token []byte, err error)
		* 扫描词语


------------------------
Demo
------------------------
	# Scanner 扫描
		package main

		import (
			"bufio"
			"fmt"
			"os"
		)

		func main() {
			file, err := os.Open("C:\\Users\\Administrator\\Desktop\\notes\\Google Authenticator.java")

			if err != nil {
				panic(err.Error())
			}

			defer func() {
				_ = file.Close()
			}()

			scanner := bufio.NewScanner(file)
			scanner.Split(bufio.ScanLines)
			for scanner.Scan() {
				// 以字符形式获取内容
				text := scanner.Text()
				fmt.Println(text)
			}

			// 异常处理
			if scanner.Err() != nil {
				panic(scanner.Err())
			}
		}

	
	# 读取标准输入流的输入
		package main

		import (
			"bufio"
			"fmt"
			"os"
			"strings"
		)

		func main() {

			scanner := bufio.NewScanner(os.Stdin)
			scanner.Split(bufio.ScanLines)
			for scanner.Scan() {
				text := scanner.Text()
				fmt.Println(text)

				// 如果是 bye 就退出
				if strings.EqualFold(text, "bye") {
					break
				}
			}

			// 异常处理
			if scanner.Err() != nil {
				panic(scanner.Err())
			}
		}

