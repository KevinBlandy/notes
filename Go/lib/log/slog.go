--------------
slog
--------------
	# 标准库的日志增强

--------------
var
--------------
	const (
		// TimeKey is the key used by the built-in handlers for the time
		// when the log method is called. The associated Value is a [time.Time].
		TimeKey = "time"
		// LevelKey is the key used by the built-in handlers for the level
		// of the log call. The associated value is a [Level].
		LevelKey = "level"
		// MessageKey is the key used by the built-in handlers for the
		// message of the log call. The associated value is a string.
		MessageKey = "msg"
		// SourceKey is the key used by the built-in handlers for the source file
		// and line of the log call. The associated value is a string.
		SourceKey = "source"
	)

--------------
type
--------------
	# type Attr struct {
			Key   string
			Value Value
		}
		func Any(key string, value any) Attr
		func Bool(key string, v bool) Attr
		func Duration(key string, v time.Duration) Attr
		func Float64(key string, v float64) Attr
		func Group(key string, args ...any) Attr
		func Int(key string, value int) Attr
		func Int64(key string, value int64) Attr
		func String(key, value string) Attr
		func Time(key string, v time.Time) Attr
		func Uint64(key string, v uint64) Attr
		func (a Attr) Equal(b Attr) bool
		func (a Attr) String() string
	
	# type Handler interface {
			Enabled(context.Context, Level) bool
			Handle(context.Context, Record) error
			WithAttrs(attrs []Attr) Handler
			WithGroup(name string) Handler
		}
	
	# type HandlerOptions struct {
			AddSource bool
			Level Leveler
			ReplaceAttr func(groups []string, a Attr) Attr
		}
	
	# type JSONHandler struct {
		}
		func NewJSONHandler(w io.Writer, opts *HandlerOptions) *JSONHandler
		func (h *JSONHandler) Enabled(_ context.Context, level Level) bool
		func (h *JSONHandler) Handle(_ context.Context, r Record) error
		func (h *JSONHandler) WithAttrs(attrs []Attr) Handler
		func (h *JSONHandler) WithGroup(name string) Handler
	
	# type Kind int
		const (
			KindAny Kind = iota
			KindBool
			KindDuration
			KindFloat64
			KindInt64
			KindString
			KindTime
			KindUint64
			KindGroup
			KindLogValuer
		)
		func (k Kind) String() string
	
	# type Level int

		const (
			LevelDebug Level = -4
			LevelInfo  Level = 0
			LevelWarn  Level = 4
			LevelError Level = 8
		)

		func (l Level) Level() Level
		func (l Level) MarshalJSON() ([]byte, error)
		func (l Level) MarshalText() ([]byte, error)
		func (l Level) String() string
		func (l *Level) UnmarshalJSON(data []byte) error
		func (l *Level) UnmarshalText(data []byte) error
	
	# type LevelVar struct {
		}
		func (v *LevelVar) Level() Level
		func (v *LevelVar) MarshalText() ([]byte, error)
		func (v *LevelVar) Set(l Level)
		func (v *LevelVar) String() string
		func (v *LevelVar) UnmarshalText(data []byte) error
	
	# type Leveler interface {
			Level() Level
		}
	
	# type LogValuer interface {
			LogValue() Value
		}
	
	# type Logger struct {
		}
		func Default() *Logger
		func New(h Handler) *Logger
		func With(args ...any) *Logger
		func (l *Logger) Debug(msg string, args ...any)
		func (l *Logger) DebugContext(ctx context.Context, msg string, args ...any)
		func (l *Logger) Enabled(ctx context.Context, level Level) bool
		func (l *Logger) Error(msg string, args ...any)
		func (l *Logger) ErrorContext(ctx context.Context, msg string, args ...any)
		func (l *Logger) Handler() Handler
		func (l *Logger) Info(msg string, args ...any)
		func (l *Logger) InfoContext(ctx context.Context, msg string, args ...any)
		func (l *Logger) Log(ctx context.Context, level Level, msg string, args ...any)
		func (l *Logger) LogAttrs(ctx context.Context, level Level, msg string, attrs ...Attr)
		func (l *Logger) Warn(msg string, args ...any)
		func (l *Logger) WarnContext(ctx context.Context, msg string, args ...any)
		func (l *Logger) With(args ...any) *Logger
		func (l *Logger) WithGroup(name string) *Logger
	
	# type Record struct {
			Time time.Time
			Message string
			Level Level
			PC uintptr
		}
		func NewRecord(t time.Time, level Level, msg string, pc uintptr) Record
		func (r *Record) Add(args ...any)
		func (r *Record) AddAttrs(attrs ...Attr)
		func (r Record) Attrs(f func(Attr) bool)
		func (r Record) Clone() Record
		func (r Record) NumAttrs() int
	
	# type Source struct {
			Function string `json:"function"`
			File string `json:"file"`
			Line int    `json:"line"`
		}
	
	# type TextHandler struct {
		}
		func NewTextHandler(w io.Writer, opts *HandlerOptions) *TextHandler
		func (h *TextHandler) Enabled(_ context.Context, level Level) bool
		func (h *TextHandler) Handle(_ context.Context, r Record) error
		func (h *TextHandler) WithAttrs(attrs []Attr) Handler
		func (h *TextHandler) WithGroup(name string) Handler
	
	# type Value struct {
		}
		func AnyValue(v any) Value
		func BoolValue(v bool) Value
		func DurationValue(v time.Duration) Value
		func Float64Value(v float64) Value
		func GroupValue(as ...Attr) Value
		func Int64Value(v int64) Value
		func IntValue(v int) Value
		func StringValue(value string) Value
		func TimeValue(v time.Time) Value
		func Uint64Value(v uint64) Value
		func (v Value) Any() any
		func (v Value) Bool() bool
		func (a Value) Duration() time.Duration
		func (v Value) Equal(w Value) bool
		func (v Value) Float64() float64
		func (v Value) Group() []Attr
		func (v Value) Int64() int64
		func (v Value) Kind() Kind
		func (v Value) LogValuer() LogValuer
		func (v Value) Resolve() (rv Value)
		func (v Value) String() string
		func (v Value) Time() time.Time
		func (v Value) Uint64() uint64

--------------
funcs
--------------

	func Debug(msg string, args ...any)
	func DebugContext(ctx context.Context, msg string, args ...any)
	func Error(msg string, args ...any)
	func ErrorContext(ctx context.Context, msg string, args ...any)
	func Info(msg string, args ...any)
	func InfoContext(ctx context.Context, msg string, args ...any)
	func Log(ctx context.Context, level Level, msg string, args ...any)
	func LogAttrs(ctx context.Context, level Level, msg string, attrs ...Attr)
	func NewLogLogger(h Handler, level Level) *log.Logger
	func SetDefault(l *Logger)
	func Warn(msg string, args ...any)
	func WarnContext(ctx context.Context, msg string, args ...any)

