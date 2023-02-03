----------------------
����
----------------------

----------------------
type
----------------------
	# type Formatter interface {
			Format(f State, c rune)
		}
	# type GoStringer interface {
			GoString() string
		}
	# type ScanState interface {
			ReadRune() (r rune, size int, err error)
			UnreadRune() error
			SkipSpace()
			Token(skipSpace bool, f func(rune) bool) (token []byte, err error)
			Width() (wid int, ok bool)
			Read(buf []byte) (n int, err error)
		}
	# type Scanner interface {
			Scan(state ScanState, verb rune) error
		}
	# type State interface {
			Write(b []byte) (n int, err error)
			Width() (wid int, ok bool)
			Precision() (prec int, ok bool)
			Flag(c int) bool
		}
	# type Stringer interface {
			String() string
		}

----------------------
����
----------------------
	func Errorf(format string, a ...interface{}) error
		* ����һ���쳣��Ϣ�����Զ��쳣��Ϣ���з�װ��ʹ�ã� %w
			err1 := errors.New("error1")
			err3 := fmt.Errorf("catch err [%w]", err1)
			

	func Fprint(w io.Writer, a ...interface{}) (n int, err error)
	func Fprintf(w io.Writer, format string, a ...interface{}) (n int, err error)
	func Fprintln(w io.Writer, a ...interface{}) (n int, err error)
		* �����ָ����Writer

	func Fscan(r io.Reader, a ...interface{}) (n int, err error)
	func Fscanf(r io.Reader, format string, a ...interface{}) (n int, err error)
	func Fscanln(r io.Reader, a ...interface{}) (n int, err error)
		* ��ָ����Reader��ȡ

	func Print(a ...interface{}) (n int, err error)
	func Printf(format string, a ...interface{}) (n int, err error)
	func Println(a ...interface{}) (n int, err error)
		* �������׼�����

	func Scan(a ...interface{}) (n int, err error)
	func Scanf(format string, a ...interface{}) (n int, err error)
	func Scanln(a ...interface{}) (n int, err error)
		* �ӱ�׼��������ȡ

	func Sprint(a ...interface{}) string
	func Sprintf(format string, a ...interface{}) string
	func Sprintln(a ...interface{}) string
		* ����������ַ�����ʽ����

	func Sscan(str string, a ...interface{}) (n int, err error)
	func Sscanf(str string, format string, a ...interface{}) (n int, err error)
	func Sscanln(str string, a ...interface{}) (n int, err error) 

	func Append(b []byte, a ...any) []byte
	func Appendf(b []byte, format string, a ...any) []byte
	func Appendln(b []byte, a ...any) []byte
	func FormatString(state State, verb rune) string