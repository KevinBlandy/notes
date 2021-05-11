-----------------------------
var
-----------------------------
	const (
		Email        = `` /* 150-byte string literal not displayed */
		UUID3        = "^[0-9a-f]{8}-[0-9a-f]{4}-3[0-9a-f]{3}-[0-9a-f]{4}-[0-9a-f]{12}$"
		UUID4        = "^[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$"
		UUID5        = "^[0-9a-f]{8}-[0-9a-f]{4}-5[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$"
		UUID         = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$"
		Int          = "^(?:[-+]?(?:0|[1-9][0-9]*))$"
		Float        = "^(?:[-+]?(?:[0-9]+))?(?:\\.[0-9]*)?(?:[eE][\\+\\-]?(?:[0-9]+))?$"
		RGBColor     = "" /* 157-byte string literal not displayed */
		FullWidth    = "[^\u0020-\u007E\uFF61-\uFF9F\uFFA0-\uFFDC\uFFE8-\uFFEE0-9a-zA-Z]"
		HalfWidth    = "[\u0020-\u007E\uFF61-\uFF9F\uFFA0-\uFFDC\uFFE8-\uFFEE0-9a-zA-Z]"
		Base64       = "^(?:[A-Za-z0-9+\\/]{4})*(?:[A-Za-z0-9+\\/]{2}==|[A-Za-z0-9+\\/]{3}=|[A-Za-z0-9+\\/]{4})$"
		Latitude     = "^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?)$"
		Longitude    = "^[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$"
		DNSName      = `^([a-zA-Z0-9_]{1}[a-zA-Z0-9_-]{0,62}){1}(\.[a-zA-Z0-9_]{1}[a-zA-Z0-9_-]{0,62})*[\._]?$`
		FullURL      = `^(?:ftp|tcp|udp|wss?|https?):\/\/[\w\.\/#=?&]+$`
		URLSchema    = `((ftp|tcp|udp|wss?|https?):\/\/)`
		URLUsername  = `(\S+(:\S*)?@)`
		URLPath      = `((\/|\?|#)[^\s]*)`
		URLPort      = `(:(\d{1,5}))`
		URLIP        = `([1-9]\d?|1\d\d|2[01]\d|22[0-3])(\.(1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.([0-9]\d?|1\d\d|2[0-4]\d|25[0-4]))`
		URLSubdomain = `((www\.)|([a-zA-Z0-9]+([-_\.]?[a-zA-Z0-9])*[a-zA-Z0-9]\.[a-zA-Z0-9]+))`
		WinPath      = `^[a-zA-Z]:\\(?:[^\\/:*?"<>|\r\n]+\\)*[^\\/:*?"<>|\r\n]*$`
		UnixPath     = `^(/[^/\x00]*)+/?$`
	)
	var (
		Marshal   MarshalFunc   = json.Marshal
		Unmarshal UnmarshalFunc = json.Unmarshal
	)
	var (
		ErrSetValue = errors.New("set value failure")
		// ErrNoData = errors.New("validate: no any data can be collected")
		ErrNoField     = errors.New("field not exist in the source data")
		ErrEmptyData   = errors.New("please input data use for validate")
		ErrInvalidData = errors.New("invalid input data")
	)



-----------------------------
type
-----------------------------
	# type ConfigValidationFace interface {
			ConfigValidation(v *Validation)
		}
	
	# type CustomMessagesFace interface {
			Messages() map[string]string
		}
	
	# type DataFace interface {
			Type() uint8
			Get(key string) (interface{}, bool)
			Set(field string, val interface{}) (interface{}, error)
			// validation instance create func
			Create(err ...error) *Validation
			Validation(err ...error) *Validation
		}
		func FromRequest(r *http.Request, maxMemoryLimit ...int64) (DataFace, error)
	
	# type Errors map[string]MS
		func (es Errors) Add(field, validator, message string)
		func (es Errors) All() map[string]map[string]string
		func (es Errors) Empty() bool
		func (es Errors) Error() string
		func (es Errors) Field(field string) map[string]string
		func (es Errors) FieldOne(field string) string
		func (es Errors) HasField(field string) bool
		func (es Errors) One() string
		func (es Errors) Random() string
		func (es Errors) String() string
	
	# type FieldTranslatorFace interface {
			Translates() map[string]string
		}

	# type FilterRule struct {
		}
		func (r *FilterRule) AddFilters(filters ...string) *FilterRule
		func (r *FilterRule) Apply(v *Validation) (err error)
		func (r *FilterRule) Fields() []string
	
	# type FormData struct {
			Form url.Values
			Files map[string]*multipart.FileHeader
		}
		func FromQuery(values url.Values) *FormData
		func FromURLValues(values url.Values) *FormData
		func (d *FormData) Add(key string, value string)
		func (d *FormData) AddFile(key string, file *multipart.FileHeader)
		func (d *FormData) AddFiles(filesMap map[string][]*multipart.FileHeader)
		func (d *FormData) AddValues(values url.Values)
		func (d FormData) Bool(key string) bool
		func (d *FormData) Create(err ...error) *Validation
		func (d *FormData) Del(key string)
		func (d *FormData) DelFile(key string)
		func (d *FormData) Encode() string
		func (d FormData) FileBytes(field string) ([]byte, error)
		func (d FormData) FileMimeType(field string) (mime string)
		func (d FormData) Float(key string) float64
		func (d FormData) Get(key string) (interface{}, bool)
		func (d FormData) GetFile(key string) *multipart.FileHeader
		func (d FormData) Has(key string) bool
		func (d FormData) HasField(key string) bool
		func (d FormData) HasFile(key string) bool
		func (d FormData) Int(key string) int
		func (d FormData) Int64(key string) int64
		func (d *FormData) Set(field string, val interface{}) (newVal interface{}, err error)
		func (d FormData) String(key string) string
		func (d FormData) Strings(key string) []string
		func (d *FormData) Type() uint8
		func (d *FormData) Validation(err ...error) *Validation
	
	# type GlobalOption struct {
			FilterTag string
			ValidateTag string
			FieldTag string
			MessageTag string
			StopOnError bool
			SkipOnEmpty bool
			UpdateSource bool
			CheckDefault bool
			CheckZero bool
		}
		func Option() GlobalOption
	
	# type M map[string]interface{}

	# type MS map[string]string
		func (ms MS) One() string
		func (ms MS) String() string

	# type MapData struct {
			Map map[string]interface{}
		}
		func FromJSON(s string) (*MapData, error)
		func FromJSONBytes(bs []byte) (*MapData, error)
		func FromMap(m map[string]interface{}) *MapData
		func (d *MapData) BindJSON(ptr interface{}) error
		func (d *MapData) Create(err ...error) *Validation
		func (d *MapData) Get(field string) (interface{}, bool)
		func (d *MapData) Set(field string, val interface{}) (interface{}, error)
		func (d *MapData) Type() uint8
		func (d *MapData) Validation(err ...error) *Validation
	
	# type MarshalFunc func(v interface{}) ([]byte, error)
	# type NilObject struct{}
	# type Rule struct {
			// contains filtered or unexported fields
		}
		func NewRule(fields, validator string, args ...interface{}) *Rule
		func (r *Rule) Apply(v *Validation) (stop bool)
		func (r *Rule) Fields() []string
		func (r *Rule) SetBeforeFunc(fn func(v *Validation) bool)
		func (r *Rule) SetCheckFunc(checkFunc interface{}) *Rule
		func (r *Rule) SetFilterFunc(fn func(val interface{}) (interface{}, error)) *Rule
		func (r *Rule) SetMessage(errMsg string) *Rule
		func (r *Rule) SetMessages(msgMap MS) *Rule
		func (r *Rule) SetOptional(optional bool)
		func (r *Rule) SetScene(scene string) *Rule
		func (r *Rule) SetSkipEmpty(skipEmpty bool)
	# type Rules []*Rule
	# type SValues map[string][]string
	# type StructData struct {
			FieldTag string
			MessageTag string
			FilterTag string
			ValidateTag string
		}
		func FromStruct(s interface{}) (*StructData, error)
		func (d *StructData) Create(err ...error) *Validation
		func (d *StructData) FuncValue(name string) (reflect.Value, bool)
		func (d *StructData) Get(field string) (interface{}, bool)
		func (d *StructData) HasField(field string) bool
		func (d *StructData) Set(field string, val interface{}) (newVal interface{}, err error)
		func (d *StructData) Type() uint8
		func (d *StructData) Validation(err ...error) *Validation
	
	# type Translator struct {
		}
		func NewTranslator() *Translator
		func (t *Translator) AddFieldMap(fieldMap map[string]string)
		func (t *Translator) AddMessage(key, msg string)
		func (t *Translator) AddMessages(data map[string]string)
		func (t *Translator) FieldMap() map[string]string
		func (t *Translator) HasField(field string) bool
		func (t *Translator) HasMessage(key string) bool
		func (t *Translator) Message(validator, field string, args ...interface{}) (msg string)
		func (t *Translator) Reset()
	
	# type UnmarshalFunc func(data []byte, v interface{}) error	
	# type Validation struct {
			Errors Errors
			StopOnError bool
			SkipOnEmpty bool
			UpdateSource bool
			CheckDefault bool
		}
		func JSON(s string, scene ...string) *Validation
		func Map(m map[string]interface{}, scene ...string) *Validation
		func New(data interface{}, scene ...string) *Validation
		func NewEmpty(scene ...string) *Validation
		func NewValidation(data DataFace, scene ...string) *Validation
		func Request(r *http.Request) *Validation
		func Struct(s interface{}, scene ...string) *Validation
			* 创建validate的方法


		func (v *Validation) AddError(field, validator, msg string)
		func (v *Validation) AddErrorf(field, msgFormat string, args ...interface{})
		func (v *Validation) AddFilter(name string, filterFunc interface{})
		func (v *Validation) AddFilters(m map[string]interface{})

		func (v *Validation) AddMessages(m map[string]string)
		func (v *Validation) AddRule(fields, validator string, args ...interface{}) *Rule
			* 添加自定的验证规则，指定字段名称，

		func (v *Validation) AddTranslates(m map[string]string)

		func (v *Validation) AddValidator(name string, checkFunc interface{})
		func (v *Validation) AddValidators(m map[string]interface{})
			* 添加临时验证器，本次验证有效

		func (v *Validation) AppendRule(rule *Rule) *Rule
		func (v *Validation) AtScene(scene string) *Validation
		func (v *Validation) BindSafeData(ptr interface{}) error
		func (v *Validation) BindStruct(ptr interface{}) error
		func (v *Validation) ConfigRules(mp MS) *Validation
		func (v *Validation) EqField(val interface{}, dstField string) bool
		func (v *Validation) FilterFuncValue(name string) reflect.Value
		func (v *Validation) FilterRule(field string, rule string) *FilterRule
		func (v *Validation) FilterRules(rules map[string]string) *Validation
		func (v *Validation) Filtered(key string) interface{}
		func (v *Validation) FilteredData() M
		func (v *Validation) Filtering() bool
		func (v *Validation) Get(key string) (interface{}, bool)
		func (v *Validation) GetDefValue(field string) (interface{}, bool)
		func (v *Validation) GetSafe(key string) interface{}
		func (v *Validation) GetWithDefault(key string) (val interface{}, exist, isDefault bool)
		func (v *Validation) GtField(val interface{}, dstField string) bool
		func (v *Validation) GteField(val interface{}, dstField string) bool
		func (v *Validation) HasValidator(name string) bool
		func (v *Validation) InMimeTypes(fd *FormData, field, mimeType string, moreTypes ...string) bool
		func (v *Validation) InScene(scene string) *Validation
		func (v *Validation) IsFail() bool
		func (v *Validation) IsFormFile(fd *FormData, field string) (ok bool)
		func (v *Validation) IsFormImage(fd *FormData, field string, exts ...string) (ok bool)
		func (v *Validation) IsOK() bool
		func (v *Validation) IsSuccess() bool
		func (v *Validation) LtField(val interface{}, dstField string) bool
		func (v *Validation) LteField(val interface{}, dstField string) bool
		func (v *Validation) NeField(val interface{}, dstField string) bool
		func (v *Validation) Raw(key string) (interface{}, bool)
		func (v *Validation) Required(field string, val interface{}) bool
		func (v *Validation) RequiredIf(field string, val interface{}, kvs ...string) bool
		func (v *Validation) RequiredUnless(_ string, val interface{}, kvs ...string) bool
		func (v *Validation) RequiredWith(_ string, val interface{}, kvs ...string) bool
		func (v *Validation) RequiredWithAll(_ string, val interface{}, kvs ...string) bool
		func (v *Validation) RequiredWithout(_ string, val interface{}, kvs ...string) bool
		func (v *Validation) RequiredWithoutAll(_ string, val interface{}, kvs ...string) bool
		func (v *Validation) Reset()
		func (v *Validation) ResetResult()
		func (v *Validation) Safe(key string) (val interface{}, ok bool)
		func (v *Validation) SafeData() M
		func (v *Validation) SafeVal(key string) interface{}
		func (v *Validation) Sanitize() bool
		func (v *Validation) Scene() string
		func (v *Validation) SceneFields() []string
		func (v *Validation) Set(field string, val interface{}) error
		func (v *Validation) SetDefValue(field string, val interface{})
		func (v *Validation) SetScene(scene ...string) *Validation
		func (v *Validation) StringRule(field, rule string, filterRule ...string) *Validation
		func (v *Validation) StringRules(mp MS) *Validation
			* 为String字段添加Rule

		func (v *Validation) Trans() *Translator
		func (v *Validation) Validate(scene ...string) bool
		func (v *Validation) ValidateData(data DataFace) bool
		func (v *Validation) Validators(withGlobal bool) map[string]int8
		func (v *Validation) WithError(err error) *Validation
		func (v *Validation) WithMessages(m map[string]string) *Validation
		func (v *Validation) WithScenarios(scenes SValues) *Validation
		func (v *Validation) WithScenes(scenes map[string][]string) *Validation
		func (v *Validation) WithTranslates(m map[string]string) *Validation
-----------------------------
func
-----------------------------
	func AddBuiltinMessages(mp map[string]string)
	func AddFilter(name string, filterFunc interface{})
	func AddFilters(m map[string]interface{})
	func AddGlobalMessages(mp map[string]string)

	func AddValidator(name string, checkFunc interface{})
	func AddValidators(m map[string]interface{})
		* 一次性添加一个或者多全局个验证器
			validate.AddValidator("foo", func(val interface{}) bool {
				return true
			})
			validate.AddValidators(validate.M{
				"bar": func(val interface{}) bool {
					// do validate val ...
					return true
				},
			})
					

	func AfterDate(srcDate, dstDate string) bool
	func AfterOrEqualDate(srcDate, dstDate string) bool
	func BeforeDate(srcDate, dstDate string) bool
	func BeforeOrEqualDate(srcDate, dstDate string) bool
	func Between(val interface{}, min, max int64) bool
	func BuiltinMessages() map[string]string
	func ByteLength(str string, minLen int, maxLen ...int) bool
	func CalcLength(val interface{}) int
	func CallByValue(fv reflect.Value, args ...interface{}) []reflect.Value
	func Config(fn func(opt *GlobalOption))
	func Contains(s, sub interface{}) bool
	func DateFormat(s string, layout string) bool
	func EndsWith(s, sub string) bool
	func Enum(val, enum interface{}) bool
	func Gt(val interface{}, dstVal int64) bool
	func HasLowerCase(s string) bool
	func HasURLSchema(s string) bool
	func HasUpperCase(s string) bool
	func HasWhitespace(s string) bool
	func IntEqual(val interface{}, wantVal int64) bool
	func IsASCII(s string) bool
	func IsAlpha(s string) bool
	func IsAlphaDash(s string) bool
	func IsAlphaNum(s string) bool
	func IsArray(val interface{}) (ok bool)
	func IsBase64(s string) bool
	func IsBool(val interface{}) bool
	func IsCIDR(s string) bool
	func IsCIDRv4(s string) bool
	func IsCIDRv6(s string) bool
	func IsCnMobile(s string) bool
	func IsDNSName(s string) bool
	func IsDataURI(s string) bool
	func IsDate(srcDate string) bool
	func IsDirPath(path string) bool
	func IsEmail(s string) bool
	func IsEmpty(val interface{}) bool
	func IsEqual(val, wantVal interface{}) bool
	func IsFilePath(path string) bool
	func IsFloat(val interface{}) bool
	func IsFullURL(s string) bool
	func IsHexColor(s string) bool
	func IsHexadecimal(s string) bool
	func IsIP(s string) bool
	func IsIPv4(s string) bool
	func IsIPv6(s string) bool
	func IsISBN10(s string) bool
	func IsISBN13(s string) bool
	func IsInt(val interface{}, minAndMax ...int64) (ok bool)
	func IsIntString(s string) bool
	func IsInts(val interface{}) bool
	func IsJSON(s string) bool
	func IsLatitude(s string) bool
	func IsLongitude(s string) bool
	func IsMAC(s string) bool
	func IsMap(val interface{}) (ok bool)
	func IsMultiByte(s string) bool
	func IsNumber(v interface{}) bool
	func IsNumeric(v interface{}) bool
	func IsPrintableASCII(s string) bool
	func IsRGBColor(s string) bool
	func IsSlice(val interface{}) (ok bool)
	func IsString(val interface{}, minAndMaxLen ...int) (ok bool)
	func IsStringNumber(s string) bool
	func IsStrings(val interface{}) (ok bool)
	func IsURL(s string) bool
	func IsUUID(s string) bool
	func IsUUID3(s string) bool
	func IsUUID4(s string) bool
	func IsUUID5(s string) bool
	func IsUint(val interface{}) bool
	func IsUnixPath(s string) bool
	func IsWinPath(s string) bool
	func IsZero(v reflect.Value) bool
	func Length(val interface{}, wantLen int) bool
	func Lt(val interface{}, dstVal int64) bool
	func Max(val interface{}, max int64) bool
	func MaxLength(val interface{}, maxLen int) bool
	func Min(val interface{}, min int64) bool
	func MinLength(val interface{}, minLen int) bool
	func NotContains(s, sub interface{}) bool
	func NotEqual(val, wantVal interface{}) bool
	func NotIn(val, enum interface{}) bool
	func PathExists(path string) bool
	func Regexp(str string, pattern string) bool
	func ResetOption()
	func RuneLength(val interface{}, minLen int, maxLen ...int) bool
	func StartsWith(s, sub string) bool
	func StringContains(s, sub string) bool
	func StringLength(val interface{}, minLen int, maxLen ...int) bool
	func ValidatorName(name string) string
	func Validators() map[string]int8
	func ValueIsEmpty(v reflect.Value) bool
	func ValueLen(v reflect.Value) int