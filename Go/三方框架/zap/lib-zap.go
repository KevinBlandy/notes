----------------------------
var
----------------------------
	const (
		// DebugLevel logs are typically voluminous, and are usually disabled in
		// production.
		DebugLevel = zapcore.DebugLevel
		// InfoLevel is the default logging priority.
		InfoLevel = zapcore.InfoLevel
		// WarnLevel logs are more important than Info, but don't need individual
		// human review.
		WarnLevel = zapcore.WarnLevel
		// ErrorLevel logs are high-priority. If an application is running smoothly,
		// it shouldn't generate any error-level logs.
		ErrorLevel = zapcore.ErrorLevel
		// DPanicLevel logs are particularly important errors. In development the
		// logger panics after writing the message.
		DPanicLevel = zapcore.DPanicLevel
		// PanicLevel logs a message, then panics.
		PanicLevel = zapcore.PanicLevel
		// FatalLevel logs a message, then calls os.Exit(1).
		FatalLevel = zapcore.FatalLevel
	)

	* ��־����


----------------------------
struct
----------------------------
	# type AtomicLevel struct {
			// contains filtered or unexported fields
		}
		func NewAtomicLevel() AtomicLevel	
			* �����µĶ�̬��־����Ĭ�ϼ����� InfoLevel

		func NewAtomicLevelAt(l zapcore.Level) AtomicLevel
			* �����µĶ�̬��־���𣬲������ü���

		func (lvl AtomicLevel) Enabled(l zapcore.Level) bool
		func (lvl AtomicLevel) Level() zapcore.Level
			* ��ȡ��־����

		func (lvl AtomicLevel) MarshalText() (text []byte, err error)
		func (lvl AtomicLevel) ServeHTTP(w http.ResponseWriter, r *http.Request)
			* �ṩ�˲�ѯ/�޸���־�����API

		func (lvl AtomicLevel) SetLevel(l zapcore.Level)
			* ������־����

		func (lvl AtomicLevel) String() string
		func (lvl *AtomicLevel) UnmarshalText(text []byte) error
		
		* ʵ���� LevelEnabler �ӿ�
		* ԭ�ӿɸ��ĵĶ�̬��־��¼���𣬿���������ʱ��ȫ�ظ��ļ�¼����������¼����ͨ����������Ĵ������κ��Ӵ�������־����
		* l������һ��http.Handler�����ṩJSON�˵��������伶��
		* ����ʹ�� NewAtomicLevel ���캯������AtomicLevels���ܷ������ڲ�ԭ��ָ��

	
	# type Config struct {
			Level AtomicLevel `json:"level" yaml:"level"`
				* ������־����
			
			Development bool `json:"development" yaml:"development"`
				* ����Ϊ����ģʽ�����ı�DPanicLevel����Ϊ�����Ҹ����ɵ�ʹ��stacktraces

			DisableCaller bool `json:"disableCaller" yaml:"disableCaller"`
				* ��ֹ����־��������ú������ļ����ƺ��кš�
				* Ĭ��true

			DisableStacktrace bool `json:"disableStacktrace" yaml:"disableStacktrace"`
				* ���ö�ջ���٣�
				* Ĭ������£��Ჶ�񿪷�ģʽ�е�WarnLevel�����ϼ������־���Լ����������е�ErrorLevel�����ϼ������־

			Sampling *SamplingConfig `json:"sampling" yaml:"samencoderConfigpling"`	
				* SamplingConfig

			Encoding string `json:"encoding" yaml:"encoding"`
				* ���ñ��������ͣ�ö��ֵ��json, console

			EncoderConfig zapcore.EncoderConfig `json:"encoderConfig" yaml:""`
				* ����������
			
			OutputPaths []string `json:"outputPaths" yaml:"outputPaths"`	
				* �������ö�����·����·���������ļ�·����stdout����׼�������
			
			ErrorOutputPaths []string `json:"errorOutputPaths" yaml:"errorOutputPaths"`
				* �������·����Ҳ�����Ƕ����

			InitialFields map[string]interface{} `json:"initialFields" yaml:"initialFields"`
				* ÿ����־�ж��������Щֵ
		}
		
		* ��־��������Ϣ
			
		func NewDevelopmentConfig() Config
		func NewProductionConfig() Config
		func (cfg Config) Build(opts ...Option) (*Logger, error)
	
	# type Field = zapcore.Field

		* ����������ֶ�
		* zapΪ��������ܡ������ڴ���������û��ʹ�÷��䣬����Ĭ�ϵ�Loggerֻ֧��ǿ���͵ġ��ṹ������־��
		* ����ʹ��zap�ṩ�ķ�����¼�ֶ�
		
		func Any(key string, value interface{}) Field
			* ����κ�����

		func Array(key string, val zapcore.ArrayMarshaler) Field
		func Binary(key string, val []byte) Field
			* ����������ֶ�

		func Bool(key string, val bool) Field
			* �����ͨbool�ֶ�
		func Boolp(key string, val *bool) Field
			* ���boolָ���ֶ�
		func Bools(key string, bs []bool) Field
			* ���bool��Ƭ
		
		func ByteString(key string, val []byte) Field
		func ByteStrings(key string, bss [][]byte) Field
		func Complex128(key string, val complex128) Field
		func Complex128p(key string, val *complex128) Field
		func Complex128s(key string, nums []complex128) Field
		func Complex64(key string, val complex64) Field
		func Complex64p(key string, val *complex64) Field
		func Complex64s(key string, nums []complex64) Field
		func Duration(key string, val time.Duration) Field
		func Durationp(key string, val *time.Duration) Field
		func Durations(key string, ds []time.Duration) Field
		func Error(err error) Field
		func Errors(key string, errs []error) Field
		func Float32(key string, val float32) Field
		func Float32p(key string, val *float32) Field
		func Float32s(key string, nums []float32) Field
		func Float64(key string, val float64) Field
		func Float64p(key string, val *float64) Field
		func Float64s(key string, nums []float64) Field
		func Inline(val zapcore.ObjectMarshaler) Field
		func Int(key string, val int) Field
		func Int16(key string, val int16) Field
		func Int16p(key string, val *int16) Field
		func Int16s(key string, nums []int16) Field
		func Int32(key string, val int32) Field
		func Int32p(key string, val *int32) Field
		func Int32s(key string, nums []int32) Field
		func Int64(key string, val int64) Field
		func Int64p(key string, val *int64) Field
		func Int64s(key string, nums []int64) Field
		func Int8(key string, val int8) Field
		func Int8p(key string, val *int8) Field
		func Int8s(key string, nums []int8) Field
		func Intp(key string, val *int) Field
		func Ints(key string, nums []int) Field
		func NamedError(key string, err error) Field
		func Namespace(key string) Field
		func Object(key string, val zapcore.ObjectMarshaler) Field
		func Reflect(key string, val interface{}) Field
		func Skip() Field
		func Stack(key string) Field
		func StackSkip(key string, skip int) Field
		func String(key string, val string) Field
		func Stringer(key string, val fmt.Stringer) Field
		func Stringp(key string, val *string) Field
		func Strings(key string, ss []string) Field
		func Time(key string, val time.Time) Field
		func Timep(key string, val *time.Time) Field
		func Times(key string, ts []time.Time) Field
		func Uint(key string, val uint) Field
		func Uint16(key string, val uint16) Field
		func Uint16p(key string, val *uint16) Field
		func Uint16s(key string, nums []uint16) Field
		func Uint32(key string, val uint32) Field
		func Uint32p(key string, val *uint32) Field
		func Uint32s(key string, nums []uint32) Field
		func Uint64(key string, val uint64) Field
		func Uint64p(key string, val *uint64) Field
		func Uint64s(key string, nums []uint64) Field
		func Uint8(key string, val uint8) Field
		func Uint8p(key string, val *uint8) Field
		func Uint8s(key string, nums []uint8) Field
		func Uintp(key string, val *uint) Field
		func Uintptr(key string, val uintptr) Field
		func Uintptrp(key string, val *uintptr) Field
		func Uintptrs(key string, us []uintptr) Field
		func Uints(key string, nums []uint) Field
	
	# type LevelEnablerFunc func(zapcore.Level) bool
		func (f LevelEnablerFunc) Enabled(lvl zapcore.Level) bool

		* LevelEnabler��ʵ�ַ���
	
	# type Logger struct {
			// contains filtered or unexported fields
		}
		func L() *Logger
			* ��ȡȫ�ֱ�׼��logger
			* ȫ�ֵ�LoggerĬ�ϲ������¼��־������һ����ʵ��Ч����Logger��

		func New(core zapcore.Core, options ...Option) *Logger
			* �����µ� Logger����Ҫ�Լ����ƻ�

		func NewDevelopment(options ...Option) (*Logger, error)
			* �ʺ��ڿ���������ʹ��

		func NewExample(options ...Option) *Logger
			* �ʺ����ڲ��Դ�����

		func NewNop() *Logger
			* ����һ����ʵ�ֵ�logger
			
		func NewProduction(options ...Option) (*Logger, error)
			* ʹ��option�����µ�logger, �ʺ��������ɻ���
			* Ĭ�ϼ�¼���ú�����Ϣ�����ں�ʱ���

		func (log *Logger) Check(lvl zapcore.Level, msg string) *zapcore.CheckedEntry
		func (log *Logger) Core() zapcore.Core
		func (log *Logger) DPanic(msg string, fields ...Field)
		func (log *Logger) Debug(msg string, fields ...Field)
		func (log *Logger) Error(msg string, fields ...Field)
		func (log *Logger) Fatal(msg string, fields ...Field)
		func (log *Logger) Info(msg string, fields ...Field)
		func (log *Logger) Named(s string) *Logger
			* copy��ǰ��־��¼����������Ϣ�����µģ����Ҹ�����һ������
			* ����Ѿ������ƵĻ��������.�ָ�

		func (log *Logger) Panic(msg string, fields ...Field)
		func (log *Logger) Sugar() *SugaredLogger
			* �ӵ�ǰLogger����һ��SugaredLogger

		func (log *Logger) Sync() error
		func (log *Logger) Warn(msg string, fields ...Field)
		func (log *Logger) With(fields ...Field) *Logger
			* 

		func (log *Logger) WithOptions(opts ...Option) *Logger
	
	# type Option interface {
			// contains filtered or unexported methods
		}
	
		* ��־��������Ϣ

		func AddCaller() Option
			* �����־������ļ���Ϣ
			* ��������ִ����:  WithCaller(true)

		func AddCallerSkip(skip int) Option
		func AddStacktrace(lvl zapcore.LevelEnabler) Option
			* ��ӵ���ջ��Ϣ
			* �ú���ָ��lvl��֮�ϵļ�����Ҫ������ö�ջ��

		func Development() Option
		func ErrorOutput(w zapcore.WriteSyncer) Option
			* �쳣��������ֻ���¼�ڲ����쳣��������ERROR�������־

		func Fields(fs ...Field) Option
			* Ԥ��һ������ֶΣ����logger�������������

		func Hooks(hooks ...func(zapcore.Entry) error) Option
			* ��ӻص�

		func IncreaseLevel(lvl zapcore.LevelEnabler) Option
		func OnFatal(action zapcore.CheckWriteAction) Option
			* ������ Fatal loggerʱ�Ĵ�����

		func WithCaller(enabled bool) Option	
			* �����־������ļ���Ϣ

		func WrapCore(f func(zapcore.Core) zapcore.Core) Option
	
	# type SamplingConfig struct {
			Initial    int                                           `json:"initial" yaml:"initial"`
			Thereafter int                                           `json:"thereafter" yaml:"thereafter"`
			Hook       func(zapcore.Entry, zapcore.SamplingDecision) `json:"-" yaml:"-"`
		}

		* ��������

	
	# type Sink interface {
			zapcore.WriteSyncer
			io.Closer
		}
	
	# type SugaredLogger struct {
			// contains filtered or unexported fields
		}
		
		* "����"����־��¼��������֧�� fmt ��ʽ������ĳ����¼��

		func S() *SugaredLogger
			* ��ȡȫ�ֵ�SugaredLogger
			* ȫ�ֵ�LoggerĬ�ϲ������¼��־������һ����ʵ��Ч����Logger��

		func (s *SugaredLogger) DPanic(args ...interface{})
		func (s *SugaredLogger) DPanicf(template string, args ...interface{})
		func (s *SugaredLogger) DPanicw(msg string, keysAndValues ...interface{})
		func (s *SugaredLogger) Debug(args ...interface{})
		func (s *SugaredLogger) Debugf(template string, args ...interface{})
		func (s *SugaredLogger) Debugw(msg string, keysAndValues ...interface{})
		func (s *SugaredLogger) Desugar() *Logger
		func (s *SugaredLogger) Error(args ...interface{})
		func (s *SugaredLogger) Errorf(template string, args ...interface{})
		func (s *SugaredLogger) Errorw(msg string, keysAndValues ...interface{})
		func (s *SugaredLogger) Fatal(args ...interface{})
		func (s *SugaredLogger) Fatalf(template string, args ...interface{})
		func (s *SugaredLogger) Fatalw(msg string, keysAndValues ...interface{})
		func (s *SugaredLogger) Info(args ...interface{})
		func (s *SugaredLogger) Infof(template string, args ...interface{})
		func (s *SugaredLogger) Infow(msg string, keysAndValues ...interface{})
		func (s *SugaredLogger) Named(name string) *SugaredLogger
		func (s *SugaredLogger) Panic(args ...interface{})
		func (s *SugaredLogger) Panicf(template string, args ...interface{})
		func (s *SugaredLogger) Panicw(msg string, keysAndValues ...interface{})
		func (s *SugaredLogger) Sync() error
		func (s *SugaredLogger) Warn(args ...interface{})
		func (s *SugaredLogger) Warnf(template string, args ...interface{})
		func (s *SugaredLogger) Warnw(msg string, keysAndValues ...interface{})
		func (s *SugaredLogger) With(args ...interface{}) *SugaredLogger

----------------------------
func
----------------------------
	func CombineWriteSyncers(writers ...zapcore.WriteSyncer) zapcore.WriteSyncer
	func LevelFlag(name string, defaultLevel zapcore.Level, usage string) *zapcore.Level

	func NewDevelopmentEncoderConfig() zapcore.EncoderConfig
		* �����µı��������ã���������

	func NewProductionEncoderConfig() zapcore.EncoderConfig
		* �����µı��������ã���������
		
	func NewStdLog(l *Logger) *log.Logger
		* ��װָ����logger��Ϊ��׼���logger

	func NewStdLogAt(l *Logger, level zapcore.Level) (*log.Logger, error)
		* �ñ�׼�ӿ���level����д���ڲ���*zap.Logger��

	func Open(paths ...string) (zapcore.WriteSyncer, func(), error)
	func RedirectStdLog(l *Logger) func()
		* ��ָ��Logger������ض��򵽱�׼��logger(ȫ�ֵ�)
		* ���� fanc() ����ȡ��

	func RedirectStdLogAt(l *Logger, level zapcore.Level) (func(), error)
	func RegisterEncoder(name string, constructor func(zapcore.EncoderConfig) (zapcore.Encoder, error)) error
	func RegisterSink(scheme string, factory func(*url.URL) (Sink, error)) error

	func ReplaceGlobals(logger *Logger) func()
		* ReplaceGlobals �滻ȫ��Logger��SugredLogger��������һ���������ָ�ԭ����ֵ�����Բ���ʹ���ǰ�ȫ�ġ�
		* ��logger����Ϊȫ�ֵ�Logger���ú�������һ���޲κ��������ڻָ�ȫ��Logger����