-----------------
exec
-----------------

-----------------
var
-----------------
	var ErrNotFound = errors.New("executable file not found in $PATH")
		* ��ִ���ļ�û���ҵ�

-----------------
type
-----------------
	# type Cmd struct {
			Path string
				* ִ�������·��������·���������·��  

			Args []string
				* ����Ĳ���

			Env []string
				* ���̻������������Ϊ�գ���ʹ�õ�ǰ���̵Ļ���  

			Dir string
				* ָ��command�Ĺ���Ŀ¼�����dirΪ�գ���comman�ڵ��ý������ڵ�ǰĿ¼������  

			Stdin io.Reader
				* ��׼���룬���stdin��nil�Ļ������̴�null device�ж�ȡ��os.DevNull��
				* stdinҲ������һ���ļ�������Ļ��������й������ٿ�һ��goroutineȥ��ȡ��׼����  

			Stdout io.Writer
				* ��׼���  

			Stderr io.Writer
				* ��������������������Stdout��Stderr��Ϊ�յĻ�����command����ʱ����Ӧ���ļ����������ӵ�os.DevNull  

			ExtraFiles []*os.File
				* ָ�����½��̼̳е����� open files
				* windows �ϲ�֧��
				
			SysProcAttr *syscall.SysProcAttr
				* ϵͳ������Ϣ
			
			Process *os.Process
				* Process �ײ���̣�ֻ����һ��  

			ProcessState *os.ProcessState
				* ProcessState����һ���˳����̵���Ϣ�������̵���Wait����Runʱ����������Ϣ��  
			
			Err error // LookPath error, if any.
			Cancel func() error
			WaitDelay time.Duration
		}

		func Command(name string, arg ...string) *Cmd
		func CommandContext(ctx context.Context, name string, arg ...string) *Cmd
			* �ṩһ��Context����������ɱ������(���� os.Process.Kill)

		func (c *Cmd) CombinedOutput() ([]byte, error)
			* ִ��������ҷ��ر�׼������쳣����ĺϲ����

		func (c *Cmd) Output() ([]byte, error)
			* ִ��������ر�׼����Ľ��
			* ���c.StderrΪnil��Output�����ExitError.Stderr��
		
		func (c *Cmd) Run() error
			* ����ָ��������ȴ������
			* �������ǵ�����start
				if err := c.Start(); err != nil {
					return err
				}
				return c.Wait()

		func (c *Cmd) Start() error
			* ����ȴ�����ִ����ϣ������������������
			* �����������ɹ���ִ�У���ôProcess���Ծͻᱻ����

		func (c *Cmd) StderrPipe() (io.ReadCloser, error)
			* ��ȡ���쳣������Ĺܵ������Դ����Reader��ȡ

		func (c *Cmd) StdinPipe() (io.WriteCloser, error)
			* ��ȡ����׼�������Ĺܵ������԰����ݴ����Writerд��

		func (c *Cmd) StdoutPipe() (io.ReadCloser, error)
			* ��ȡ����׼������Ĺܵ������Դ����Reader��ȡ

		func (c *Cmd) String() string
		func (c *Cmd) Wait() error
			* ����������� srart ���������ģ���ô���Ե����������������ֱ���������
			* ���c.Stdin��c.Stdout��c.Stderr�е��κ�һ������*os.File��WaitҲ��ȴ����Ե�I/Oѭ�����Ƶ������л�ӽ����и��Ƴ������
			* Wait���ͷ��κ���Cmd.Stdin.File��ص���Դ

		func (c *Cmd) Environ() []string
			* Environ����һ�������ĸ�����������ڸû��������У���Ϊ���ǵ�ǰ���õġ�

	# type Error struct {
			Name string
			Err error
		}
		func (e *Error) Error() string
		func (e *Error) Unwrap() error
	
	# type ExitError struct {
			*os.ProcessState
			Stderr []byte
				* �����׼������Ϣû�б������ط��ռ�����ô���ռ�������
				* ����ռ�����ֻ��洢һ���֣�ʡ�Դ󲿷���Ϣ
		}
		
		* ִ���쳣�󣬻᷵������쳣����
		* �̳���ProcessState�����Ի�ȡ�����̵�״̬��Ϣ

		func (e *ExitError) Error() string

	


-----------------
func
-----------------
	func LookPath(file string) (string, error)
		* ���Դ�path�й�������ִ���ļ��ľ���·��


-----------------
demo
-----------------