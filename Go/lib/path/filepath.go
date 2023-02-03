-------------------------
filepath
-------------------------

-------------------------
����
-------------------------
	const (
		Separator     = os.PathSeparator			// ·���ָ������ָ�·��Ԫ�أ�
		ListSeparator = os.PathListSeparator		// ·���б�ָ������ָ����·����
	)
		
		* ��ƽ̨��·���ָ�����·�����Ϸָ���

	var ErrBadPattern = errors.New("syntax error in pattern")
	
	var SkipDir = errors.New("skip this directory")
		* ����Ŀ¼��ʱ�򣬿��Է�������쳣����������ǰĿ¼
	var SkipAll error = fs.SkipAll
		* ����Ŀ¼��ʱ�򣬿��Է�������쳣������������Ŀ¼

-------------------------
type
-------------------------
	# type WalkFunc func(path string, info os.FileInfo, err error) error

-------------------------
����
-------------------------
	func Abs(path string) (string, error)
		* ���� path ����ľ���·������� path ���Ǿ���·��������뵱ǰ����Ŀ¼��ʹ֮��Ϊ����·����


	func Base(path string) string
		* ����·�������һ��Ԫ�ء�����ȡԪ��ǰ��ȥ��ĩβ��б�ܡ�
		* ���·���� ""���᷵�� "."�����·����ֻ��һ��б�˹��ɵģ��᷵�� "/"��
			"C:\go-project"			-> go-project
			"C:\go-project\"		-> go-project
			"C:\go-project\a.jpg"	-> a.jpg
			""						-> .

	func Clean(path string) string
		* �������ڰ�����·���ĳɱ��ص��ļ�·��
			filepath.Clean("/dd/dd.jpg") // \dd\dd.jpg
		* ���ַ��������� "."
		* ����·���еĶ����ַ������� /// �� ../ �� ./

	func Dir(path string) string
		* ����·���г�ȥ���һ��·��Ԫ�صĲ��֣�����·�����һ��Ԫ�����ڵ�Ŀ¼


	func EvalSymlinks(path string) (string, error)
		* �������ӣ���ݷ�ʽ����ָ���ʵ���ļ�

	func Ext(path string) string
		* ��ȡ��׺����
	
	func FromSlash(path string) string
		* �� path �е� '/' ת��Ϊϵͳ��ص�·���ָ���
				
	func Glob(pattern string) (matches []string, err error)
		* �г���ָ����ģʽ pattern ��ȫƥ����ļ���Ŀ¼��ƥ��ԭ��ͬ�ϣ�

	func HasPrefix(p, prefix string) bool
	func IsAbs(path string) bool
		* ����·���Ƿ���һ������·��
	
	func Join(elem ...string) string
		* ƴ��·�������·�����Ѿ�������һЩ����ķָ������ᱻȥ��
		* ���Կ�Ԫ�أ���������ַ���

	func Match(pattern, name string) (matched bool, err error)
		* name �Ƿ��ָ����ģʽ pattern ��ȫƥ��
	
	func Rel(basepath, targpath string) (string, error)
		* ��ȡ targpath ����� basepath ��·����
		* Ҫ�� targpath �� basepath ���롰�������·�����򡰶��Ǿ���·������

	func Split(path string) (dir, file string)
		* ��path�ָ�ΪĿ¼���ļ���������
		* ���û��Ŀ¼���� dir �ǿ��ַ���

	func SplitList(path string) []string	
		* ·������ path �ָ�Ϊ����������·��

	func ToSlash(path string) string
		* �� path ��ƽ̨��ص�·���ָ���ת��Ϊ '/'

	func VolumeName(path string) string
		* ����·���ַ����еľ���
			indows �е� `C:\Windows` �᷵�� "C:"
			Linux �е� `//host/share/name` �᷵�� `//host/share`

	func Walk(root string, walkFn WalkFunc) error
		* ����Ŀ¼�������walkFn���� SkipDir ����������ǰĿ¼����������
		* ��Ŀ¼Walk��Ч�ʻ�ܵͣ����������ݷ�ʽ
	
	func WalkDir(root string, fn fs.WalkDirFunc) error
		* ������ Walk����ͨ��Ч�ʸ��ߡ�
		* ���ݸ��ĺ���WalkDir���� fs.DirEntry ������ fs.FileInfo
	
	func IsLocal(path string) bool