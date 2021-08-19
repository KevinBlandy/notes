------------------------
bufio
------------------------

------------------------
����
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
		
		* ����������Reader
		
		func NewReader(rd io.Reader) *Reader
		func NewReaderSize(rd io.Reader, size int) *Reader
			* Ĭ�ϵ�size��: defaultBufSize = 4096

		func (b *Reader) Buffered() int
		func (b *Reader) Discard(n int) (discarded int, err error)
		func (b *Reader) Peek(n int) ([]byte, error)
		func (b *Reader) Read(p []byte) (n int, err error)
		func (b *Reader) ReadByte() (byte, error)
		func (b *Reader) ReadBytes(delim byte) ([]byte, error)
		func (b *Reader) ReadLine() (line []byte, isPrefix bool, err error)
			* ����һ���Ƚϵͼ���Api�������������Ӧ��ʹ��
				ReadBytes('\n')
				ReadString('\n')
			* �������ʹ��Scanner

		func (b *Reader) ReadRune() (r rune, size int, err error)
		func (b *Reader) ReadSlice(delim byte) (line []byte, err error)
		func (b *Reader) ReadString(delim byte) (string, error)
			* ����ָ���ָ�����ȡ����

		func (b *Reader) Reset(r io.Reader)
		func (b *Reader) Size() int
		func (b *Reader) UnreadByte() error
		func (b *Reader) UnreadRune() error
		func (b *Reader) WriteTo(w io.Writer) (n int64, err error)
	
	# type Scanner struct
		
		* ɨ����
		
		func NewScanner(r io.Reader) *Scanner
		

		func (s *Scanner) Buffer(buf []byte, max int)
		func (s *Scanner) Err() error
			* �����쳣��Ϣ

		func (s *Scanner) Scan() bool
			* ��ȡ��һ���ָ��������ؽ����ʾ�Ƿ�������

		func (s *Scanner) Split(split SplitFunc)
			* ָ���ָ����������ָ������ʹ��newline�ַ���Ϊ�ָ���
			* �����ķָ����������� ScanRunes �� ScanWords,����bufio���С�

		func (s *Scanner) Bytes() []byte
		func (s *Scanner) Text() string
			* ���ֽڻ����ַ�����ʽ��ȡ����
	
	# type SplitFunc func(data []byte, atEOF bool) (advance int, token []byte, err error)
		* ����ָ���ָ���

	# type Writer struct
		
		* ����������Writer

		func NewWriter(w io.Writer) *Writer
		func NewWriterSize(w io.Writer, size int) *Writer

		func (b *Writer) Available() int
			* δʹ�õĻ����С

		func (b *Writer) Buffered() int
		func (b *Writer) Flush() error
		func (b *Writer) ReadFrom(r io.Reader) (n int64, err error)
		func (b *Writer) Reset(w io.Writer)
			* ������û��flush�Ļ�������ݣ�������󲢰�����������������е�writer

		func (b *Writer) Size() int
		func (b *Writer) Write(p []byte) (nn int, err error)
		func (b *Writer) WriteByte(c byte) error
		func (b *Writer) WriteRune(r rune) (size int, err error)
		func (b *Writer) WriteString(s string) (int, error)

------------------------
����
------------------------
	func ScanBytes(data []byte, atEOF bool) (advance int, token []byte, err error)
		* ɨ���ֽ�
	func ScanLines(data []byte, atEOF bool) (advance int, token []byte, err error)
		* ɨ��һ��
	func ScanRunes(data []byte, atEOF bool) (advance int, token []byte, err error)
		* ɨ�赥���ַ�
	func ScanWords(data []byte, atEOF bool) (advance int, token []byte, err error)
		* ɨ�����


------------------------
Demo
------------------------
	# Scanner ɨ��
		import (
			"bufio"
			"fmt"
			"io"
			"os"
		)
		func main() {
			file, _ := os.Open("C:\\Users\\Administrator\\Desktop\\�½��ı��ĵ�.txt")
			defer file.Close()
			scanner := bufio.NewScanner(file)
			scanner.Split(bufio.ScanLines)
			for {
				if scanner.Scan() {
					// ������һ������
					err := scanner.Err()
					if err != nil {
						if err != io.EOF {
							// �쳣
							fmt.Fprintf(os.Stderr, "�ļ�ɨ���쳣:%s\n", err.Error())
							return
						}
					}
					// ���ַ���ʽ��ȡ����
					text := scanner.Text()
					fmt.Println(text)
				} else {
					break
				}
			}
		}
	
	
	# ��ȡ��׼������������
		package main

		import (
			"bufio"
			"fmt"
			"io"
			"os"
			"strings"
		)

		func main() {
			scanner := bufio.NewScanner(os.Stdin)
			scanner.Split(bufio.ScanLines)
			for scanner.Scan() {
				err := scanner.Err()
				if err != nil {
					if err == io.EOF {
						break
					} else {
						panic(err)
					}
				}

				line := scanner.Text()
				if strings.EqualFold(line, "bye") {
					break
				}
				fmt.Println(line)
			}
		}

