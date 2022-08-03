--------------------------
var
--------------------------
	const DefaultLineEnding = "\n"
	const OmitKey = ""

--------------------------
type
--------------------------
	# type ArrayEncoder interface {
			// Built-in types.
			PrimitiveArrayEncoder

			// Time-related types.
			AppendDuration(time.Duration)
			AppendTime(time.Time)

			// Logging-specific marshalers.
			AppendArray(ArrayMarshaler) error
			AppendObject(ObjectMarshaler) error

			// AppendReflected uses reflection to serialize arbitrary objects, so it's
			// slow and allocation-heavy.
			AppendReflected(value interface{}) error
		}
	
	# type ArrayMarshaler interface {
			MarshalLogArray(ArrayEncoder) error
		}
	
	# type ArrayMarshalerFunc func(ArrayEncoder) error
		func (f ArrayMarshalerFunc) MarshalLogArray(enc ArrayEncoder) error

		* �������к������������л��������͵Ĳ���
	
	# type CallerEncoder func(EntryCaller, PrimitiveArrayEncoder)
		func (e *CallerEncoder) UnmarshalText(text []byte) error
	
	# type CheckWriteAction uint8
	
		* һЩ��־д���¼������󴥷�����Ϊ����
		
		const (
			WriteThenNoop CheckWriteAction = iota
				* ʲôҲ������Ĭ�ϵ�

			WriteThenGoexit
				* WriteThenGoexit��Write֮������runtime.Goexit
			
			WriteThenPanic
				* WriteThenPanic����Write֮���Panic

			WriteThenFatal
				* WriteThenFatal����Write֮������������os.Exit��
		)
	
	# type CheckedEntry struct {
			Entry
			ErrorOutput WriteSyncer
		}
		func (ce *CheckedEntry) AddCore(ent Entry, core Core) *CheckedEntry
		func (ce *CheckedEntry) Should(ent Entry, should CheckWriteAction) *CheckedEntry
		func (ce *CheckedEntry) Write(fields ...Field)
	
	# type Core interface {
			LevelEnabler
			// With adds structured context to the Core.
			With([]Field) Core
			// Check determines whether the supplied Entry should be logged (using the
			// embedded LevelEnabler and possibly some extra logic). If the entry
			// should be logged, the Core adds itself to the CheckedEntry and returns
			// the result.
			//
			// Callers must use Check before calling Write.
			Check(Entry, *CheckedEntry) *CheckedEntry
			// Write serializes the Entry and any Fields supplied at the log site and
			// writes them to their destination.
			//
			// If called, Write should always log the Entry and Fields; it should not
			// replicate the logic of Check.
			Write(Entry, []Field) error
			// Sync flushes buffered logs (if any).
			Sync() error
		}
		
		* ���Ľӿ�

		func NewCore(enc Encoder, ws WriteSyncer, enab LevelEnabler) Core
			* ����ʵ�֣�������, WriteSyncer, ��־����

		func NewIncreaseLevelCore(core Core, level LevelEnabler) (Core, error)
		func NewNopCore() Core
			* ����һ����ʵ��

		func NewSampler(core Core, tick time.Duration, first, thereafter int) Core
		func NewSamplerWithOptions(core Core, tick time.Duration, first, thereafter int, opts ...SamplerOption) Core
		func NewTee(cores ...Core) Core
		func RegisterHooks(core Core, hooks ...func(Entry) error) Core
			* ��ӻص�
	
	# type DurationEncoder func(time.Duration, PrimitiveArrayEncoder)
		func (e *DurationEncoder) UnmarshalText(text []byte) error
	
	# type Encoder interface {
			ObjectEncoder

			// Clone copies the encoder, ensuring that adding fields to the copy doesn't
			// affect the original.
			Clone() Encoder

			// EncodeEntry encodes an entry and fields, along with any accumulated
			// context, into a byte buffer and returns it. Any fields that are empty,
			// including fields on the `Entry` type, should be omitted.
			EncodeEntry(Entry, []Field) (*buffer.Buffer, error)
		}
		
		* ��־�ı����������ڸ�ʽ����־

		func NewConsoleEncoder(cfg EncoderConfig) Encoder
			* ��ʽ����־Ϊ����̨ģʽ(һ��һ�����)

		func NewJSONEncoder(cfg EncoderConfig) Encoder
			* ��ʽ����־ΪJSON
	
	# type EncoderConfig struct {
			MessageKey    string `json:"messageKey" yaml:"messageKey"`
				* ��־����Ϣ�ļ�����Ĭ��Ϊmsg
			LevelKey      string `json:"levelKey" yaml:"levelKey"`
				* ��־�м���ļ�����Ĭ��Ϊlevel��
			TimeKey       string `json:"timeKey" yaml:"timeKey"`
				* ʱ���KEY
			NameKey       string `json:"nameKey" yaml:"nameKey"`
				* logger name��key
			CallerKey     string `json:"callerKey" yaml:"callerKey"`
				* caller��key
			FunctionKey   string `json:"functionKey" yaml:"functionKey"`
				* ������key
			StacktraceKey string `json:"stacktraceKey" yaml:"stacktraceKey"`
				* ջ���ٵ�key
			LineEnding    string `json:"lineEnding" yaml:"lineEnding"`
				* ���з�?
			// Configure the primitive representations of common complex types. For
			// example, some users may want all time.Times serialized as floating-point
			// seconds since epoch, while others may prefer ISO8601 strings.
			EncodeLevel    LevelEncoder    `json:"levelEncoder" yaml:"levelEncoder"`
				* ��־�м���ĸ�ʽ��Ĭ��ΪСд����debug/info��

			EncodeTime     TimeEncoder     `json:"timeEncoder" yaml:"timeEncoder"`
				* ʱ���ʽ������
			
			EncodeDuration DurationEncoder `json:"durationEncoder" yaml:"durationEncoder"`
				* ʱ�䵥λ�ĸ�ʽ��

			EncodeCaller   CallerEncoder   `json:"callerEncoder" yaml:"callerEncoder"`
			// Unlike the other primitive type encoders, EncodeName is optional. The
			// zero value falls back to FullNameEncoder.
			EncodeName NameEncoder `json:"nameEncoder" yaml:"nameEncoder"`
			// Configures the field separator used by the console encoder. Defaults
			// to tab.
			ConsoleSeparator string `json:"consoleSeparator" yaml:"consoleSeparator"`
		}

		* �����������ã�����Ҫ���趨��־�и�����λ�ĸ�ʽ����ʽ

	# type Entry struct {
			Level      Level
			Time       time.Time
			LoggerName string
			Message    string
			Caller     EntryCaller
			Stack      string
		}

		* һ����־��Ϣ
	
	# type EntryCaller struct {
			Defined  bool
			PC       uintptr
			File     string
			Line     int
			Function string
		}
		
		* ��־�ĵ�����Ϣ

		func NewEntryCaller(pc uintptr, file string, line int, ok bool) EntryCaller
		func (ec EntryCaller) FullPath() string
		func (ec EntryCaller) String() string
		func (ec EntryCaller) TrimmedPath() string
	
	# type Field struct {
			Key       string
			Type      FieldType
			Integer   int64
			String    string
			Interface interface{}
		}
		func (f Field) AddTo(enc ObjectEncoder)
		func (f Field) Equals(other Field) bool
	
	# type FieldType uint8
		const (
			// UnknownType is the default field type. Attempting to add it to an encoder will panic.
			UnknownType FieldType = iota
			// ArrayMarshalerType indicates that the field carries an ArrayMarshaler.
			ArrayMarshalerType
			// ObjectMarshalerType indicates that the field carries an ObjectMarshaler.
			ObjectMarshalerType
			// BinaryType indicates that the field carries an opaque binary blob.
			BinaryType
			// BoolType indicates that the field carries a bool.
			BoolType
			// ByteStringType indicates that the field carries UTF-8 encoded bytes.
			ByteStringType
			// Complex128Type indicates that the field carries a complex128.
			Complex128Type
			// Complex64Type indicates that the field carries a complex128.
			Complex64Type
			// DurationType indicates that the field carries a time.Duration.
			DurationType
			// Float64Type indicates that the field carries a float64.
			Float64Type
			// Float32Type indicates that the field carries a float32.
			Float32Type
			// Int64Type indicates that the field carries an int64.
			Int64Type
			// Int32Type indicates that the field carries an int32.
			Int32Type
			// Int16Type indicates that the field carries an int16.
			Int16Type
			// Int8Type indicates that the field carries an int8.
			Int8Type
			// StringType indicates that the field carries a string.
			StringType
			// TimeType indicates that the field carries a time.Time that is
			// representable by a UnixNano() stored as an int64.
			TimeType
			// TimeFullType indicates that the field carries a time.Time stored as-is.
			TimeFullType
			// Uint64Type indicates that the field carries a uint64.
			Uint64Type
			// Uint32Type indicates that the field carries a uint32.
			Uint32Type
			// Uint16Type indicates that the field carries a uint16.
			Uint16Type
			// Uint8Type indicates that the field carries a uint8.
			Uint8Type
			// UintptrType indicates that the field carries a uintptr.
			UintptrType
			// ReflectType indicates that the field carries an interface{}, which should
			// be serialized using reflection.
			ReflectType
			// NamespaceType signals the beginning of an isolated namespace. All
			// subsequent fields should be added to the new namespace.
			NamespaceType
			// StringerType indicates that the field carries a fmt.Stringer.
			StringerType
			// ErrorType indicates that the field carries an error.
			ErrorType
			// SkipType indicates that the field is a no-op.
			SkipType

			// InlineMarshalerType indicates that the field carries an ObjectMarshaler
			// that should be inlined.
			InlineMarshalerType
		)
	
	# type Level int8
		const (
			// DebugLevel logs are typically voluminous, and are usually disabled in
			// production.
			DebugLevel Level = iota - 1
			// InfoLevel is the default logging priority.
			InfoLevel
			// WarnLevel logs are more important than Info, but don't need individual
			// human review.
			WarnLevel
			// ErrorLevel logs are high-priority. If an application is running smoothly,
			// it shouldn't generate any error-level logs.
			ErrorLevel
			// DPanicLevel logs are particularly important errors. In development the
			// logger panics after writing the message.
			DPanicLevel
			// PanicLevel logs a message, then panics.
			PanicLevel
			// FatalLevel logs a message, then calls os.Exit(1).
			FatalLevel
		)
		
		* ��־����

		func (l Level) CapitalString() string
		func (l Level) Enabled(lvl Level) bool
		func (l *Level) Get() interface{}
		func (l Level) MarshalText() ([]byte, error)
		func (l *Level) Set(s string) error
		func (l Level) String() string
		func (l *Level) UnmarshalText(text []byte) error
	
	# type LevelEnabler interface {
			Enabled(Level) bool
		}

		* ��־����ӿ�
		* Enabled��������true����ʾҪ�����־
	
	# type LevelEncoder func(Level, PrimitiveArrayEncoder)
		func (e *LevelEncoder) UnmarshalText(text []byte) error
	
	# type MapObjectEncoder struct {
			// Fields contains the entire encoded log context.
			Fields map[string]interface{}
			// contains filtered or unexported fields
		}
		func NewMapObjectEncoder() *MapObjectEncoder
		func (m *MapObjectEncoder) AddArray(key string, v ArrayMarshaler) error
		func (m *MapObjectEncoder) AddBinary(k string, v []byte)
		func (m *MapObjectEncoder) AddBool(k string, v bool)
		func (m *MapObjectEncoder) AddByteString(k string, v []byte)
		func (m *MapObjectEncoder) AddComplex128(k string, v complex128)
		func (m *MapObjectEncoder) AddComplex64(k string, v complex64)
		func (m MapObjectEncoder) AddDuration(k string, v time.Duration)
		func (m *MapObjectEncoder) AddFloat32(k string, v float32)
		func (m *MapObjectEncoder) AddFloat64(k string, v float64)
		func (m *MapObjectEncoder) AddInt(k string, v int)
		func (m *MapObjectEncoder) AddInt16(k string, v int16)
		func (m *MapObjectEncoder) AddInt32(k string, v int32)
		func (m *MapObjectEncoder) AddInt64(k string, v int64)
		func (m *MapObjectEncoder) AddInt8(k string, v int8)
		func (m *MapObjectEncoder) AddObject(k string, v ObjectMarshaler) error
		func (m *MapObjectEncoder) AddReflected(k string, v interface{}) error
		func (m *MapObjectEncoder) AddString(k string, v string)
		func (m MapObjectEncoder) AddTime(k string, v time.Time)
		func (m *MapObjectEncoder) AddUint(k string, v uint)
		func (m *MapObjectEncoder) AddUint16(k string, v uint16)
		func (m *MapObjectEncoder) AddUint32(k string, v uint32)
		func (m *MapObjectEncoder) AddUint64(k string, v uint64)
		func (m *MapObjectEncoder) AddUint8(k string, v uint8)
		func (m *MapObjectEncoder) AddUintptr(k string, v uintptr)
		func (m *MapObjectEncoder) OpenNamespace(k string)
			* ��nameSpace����������ӵ����ԣ����������NameSpace��
	
	# type NameEncoder func(string, PrimitiveArrayEncoder)
		func (e *NameEncoder) UnmarshalText(text []byte) error
	
	# type ObjectEncoder interface {
			// Logging-specific marshalers.
			AddArray(key string, marshaler ArrayMarshaler) error
			AddObject(key string, marshaler ObjectMarshaler) error

			// Built-in types.
			AddBinary(key string, value []byte)     // for arbitrary bytes
			AddByteString(key string, value []byte) // for UTF-8 encoded bytes
			AddBool(key string, value bool)
			AddComplex128(key string, value complex128)
			AddComplex64(key string, value complex64)
			AddDuration(key string, value time.Duration)
			AddFloat64(key string, value float64)
			AddFloat32(key string, value float32)
			AddInt(key string, value int)
			AddInt64(key string, value int64)
			AddInt32(key string, value int32)
			AddInt16(key string, value int16)
			AddInt8(key string, value int8)
			AddString(key, value string)
			AddTime(key string, value time.Time)
			AddUint(key string, value uint)
			AddUint64(key string, value uint64)
			AddUint32(key string, value uint32)
			AddUint16(key string, value uint16)
			AddUint8(key string, value uint8)
			AddUintptr(key string, value uintptr)

			// AddReflected uses reflection to serialize arbitrary objects, so it can be
			// slow and allocation-heavy.
			AddReflected(key string, value interface{}) error
			// OpenNamespace opens an isolated namespace where all subsequent fields will
			// be added. Applications can use namespaces to prevent key collisions when
			// injecting loggers into sub-components or third-party libraries.
			OpenNamespace(key string)
		}
	
	# type ObjectMarshaler interface {
			MarshalLogObject(ObjectEncoder) error
		}

	# type ObjectMarshalerFunc func(ObjectEncoder) error
		func (f ObjectMarshalerFunc) MarshalLogObject(enc ObjectEncoder) error

		* �������л���
	
	# type PrimitiveArrayEncoder interface {
			// Built-in types.
			AppendBool(bool)
			AppendByteString([]byte) // for UTF-8 encoded bytes
			AppendComplex128(complex128)
			AppendComplex64(complex64)
			AppendFloat64(float64)
			AppendFloat32(float32)
			AppendInt(int)
			AppendInt64(int64)
			AppendInt32(int32)
			AppendInt16(int16)
			AppendInt8(int8)
			AppendString(string)
			AppendUint(uint)
			AppendUint64(uint64)
			AppendUint32(uint32)
			AppendUint16(uint16)
			AppendUint8(uint8)
			AppendUintptr(uintptr)
		}
	
	# type SamplerOption interface {
			// contains filtered or unexported methods
		}
		func SamplerHook(hook func(entry Entry, dec SamplingDecision)) SamplerOption
	
	# type SamplingDecision uint32
		const (
			// LogDropped indicates that the Sampler dropped a log entry.
			LogDropped SamplingDecision = 1 << iota
			// LogSampled indicates that the Sampler sampled a log entry.
			LogSampled
		)
	
	# type TimeEncoder func(time.Time, PrimitiveArrayEncoder)
		func TimeEncoderOfLayout(layout string) TimeEncoder	
			* �����µ�ʱ���ʽ������ָ��Layout

		func (e *TimeEncoder) UnmarshalJSON(data []byte) error
		func (e *TimeEncoder) UnmarshalText(text []byte) error
		func (e *TimeEncoder) UnmarshalYAML(unmarshal func(interface{}) error) error
	
	# type WriteSyncer interface {
			io.Writer
			Sync() error
		}
		
		* ��־�����Ŀ�ĵ�

		func AddSync(w io.Writer) WriteSyncer
			* ���ioWriter

		func Lock(ws WriteSyncer) WriteSyncer
			* ��װһ��Wrtier����������ô���Ϳ��԰�ȫ�Ĳ���ʹ����
			* os.File��������ļ���ѡҪ����

		func NewMultiWriteSyncer(ws ...WriteSyncer) WriteSyncer
			* �ϲ����Writer

--------------------------
func
--------------------------
	func CapitalColorLevelEncoder(l Level, enc PrimitiveArrayEncoder)
	func CapitalLevelEncoder(l Level, enc PrimitiveArrayEncoder)
		* ʹ�ô�д��ĸ����¼��־����

	func EpochMillisTimeEncoder(t time.Time, enc PrimitiveArrayEncoder)
	func EpochNanosTimeEncoder(t time.Time, enc PrimitiveArrayEncoder)
	func EpochTimeEncoder(t time.Time, enc PrimitiveArrayEncoder)
	func FullCallerEncoder(caller EntryCaller, enc PrimitiveArrayEncoder)
	func FullNameEncoder(loggerName string, enc PrimitiveArrayEncoder)
	func ISO8601TimeEncoder(t time.Time, enc PrimitiveArrayEncoder)
		* ����ISO8601ʱ���ʽ����Encode

	func LowercaseColorLevelEncoder(l Level, enc PrimitiveArrayEncoder)
	func LowercaseLevelEncoder(l Level, enc PrimitiveArrayEncoder)
	func MillisDurationEncoder(d time.Duration, enc PrimitiveArrayEncoder)
	func NanosDurationEncoder(d time.Duration, enc PrimitiveArrayEncoder)
	func RFC3339NanoTimeEncoder(t time.Time, enc PrimitiveArrayEncoder)
	func RFC3339TimeEncoder(t time.Time, enc PrimitiveArrayEncoder)
	func SecondsDurationEncoder(d time.Duration, enc PrimitiveArrayEncoder)
	func ShortCallerEncoder(caller EntryCaller, enc PrimitiveArrayEncoder)
	func StringDurationEncoder(d time.Duration, enc PrimitiveArrayEncoder)