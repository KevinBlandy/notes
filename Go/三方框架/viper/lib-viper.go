-----------------------
var
-----------------------
	var RemoteConfig remoteConfigFactory
	var SupportedExts = []string{"json", "toml", "yaml", "yml", "properties", "props", "prop", "hcl", "dotenv", "env", "ini"}
	var SupportedRemoteProviders = []string{"etcd", "consul", "firestore"}

-----------------------
type
-----------------------
	# type ConfigFileAlreadyExistsError string
		func (faee ConfigFileAlreadyExistsError) Error() string
		
		* 配置文件已存在异常

	# type ConfigFileNotFoundError struct {
		}
		func (fnfe ConfigFileNotFoundError) Error() string
		
		* 配置文件不存在异常
		
	# type ConfigMarshalError struct {
		}
		func (e ConfigMarshalError) Error() string
	# type ConfigParseError struct {
		}
		func (pe ConfigParseError) Error() string
	# type DecoderConfigOption func(*mapstructure.DecoderConfig)
		func DecodeHook(hook mapstructure.DecodeHookFunc) DecoderConfigOption
	# type FlagValue interface {
			HasChanged() bool
			Name() string
			ValueString() string
			ValueType() string
		}
	# type FlagValueSet interface {
			VisitAll(fn func(FlagValue))
		}
	# type Option interface {
		}
		
		* 创建Viper的配置项

		func EnvKeyReplacer(r StringReplacer) Option
		func KeyDelimiter(d string) Option

	# type RemoteConfigError string
		func (rce RemoteConfigError) Error() string
	# type RemoteProvider interface {
			Provider() string
			Endpoint() string
			Path() string
			SecretKeyring() string
		}
	# type RemoteResponse struct {
			Value []byte
			Error error
		}
	# type StringReplacer interface {
			Replace(s string) string
		}
	# type UnsupportedConfigError string
		func (str UnsupportedConfigError) Error() string
	# type UnsupportedRemoteProviderError string
		func (str UnsupportedRemoteProviderError) Error() string
	# type Viper struct {
		}
		func GetViper() *Viper
		func New() *Viper
		func NewWithOptions(opts ...Option) *Viper
		func Sub(key string) *Viper
		func (v *Viper) AddConfigPath(in string)
			* 添加查找配置文件所在的路径
			* 可以指定为: "."，表示在工作目录中查找配置

		func (v *Viper) AddRemoteProvider(provider, endpoint, path string) error
		func (v *Viper) AddSecureRemoteProvider(provider, endpoint, path, secretkeyring string) error
		func (v *Viper) AllKeys() []string
		func (v *Viper) AllSettings() map[string]interface{}
		func (v *Viper) AllowEmptyEnv(allowEmptyEnv bool)
		func (v *Viper) AutomaticEnv()
			* 预加载到环境变量

		func (v *Viper) BindEnv(input ...string) error
			
		func (v *Viper) BindFlagValue(key string, flag FlagValue) error
		func (v *Viper) BindFlagValues(flags FlagValueSet) (err error)
		func (v *Viper) BindPFlag(key string, flag *pflag.Flag) error
		func (v *Viper) BindPFlags(flags *pflag.FlagSet) error
		func (v *Viper) ConfigFileUsed() string
		func (v *Viper) Debug()

		func (v *Viper) Get(key string) interface{}
		func (v *Viper) GetBool(key string) bool
		func (v *Viper) GetDuration(key string) time.Duration
		func (v *Viper) GetFloat64(key string) float64
		func (v *Viper) GetInt(key string) int
		func (v *Viper) GetInt32(key string) int32
		func (v *Viper) GetInt64(key string) int64
		func (v *Viper) GetIntSlice(key string) []int
		func (v *Viper) GetSizeInBytes(key string) uint
		func (v *Viper) GetString(key string) string
		func (v *Viper) GetStringMap(key string) map[string]interface{}
		func (v *Viper) GetStringMapString(key string) map[string]string
		func (v *Viper) GetStringMapStringSlice(key string) map[string][]string
		func (v *Viper) GetStringSlice(key string) []string
		func (v *Viper) GetTime(key string) time.Time
		func (v *Viper) GetUint(key string) uint
		func (v *Viper) GetUint32(key string) uint32
		func (v *Viper) GetUint64(key string) uint64
			* 尝试获取值，如果不存在返回零值
			* 如果配置是[]或者{}，需要用GetStringSlice/GetStringMap
			* 如果配置比较复杂，就用 Get(), 返回 []interface{} 或者 map[string] interface{}
			* 可以通过传入.分隔的路径来访问嵌套字段

		func (v *Viper) InConfig(key string) bool
		func (v *Viper) IsSet(key string) bool
		func (v *Viper) MergeConfig(in io.Reader) error
		func (v *Viper) MergeConfigMap(cfg map[string]interface{}) error
		func (v *Viper) MergeInConfig() error
		func (v *Viper) OnConfigChange(run func(in fsnotify.Event))
			 * 配置文件发生变更之后会调用的回调函数

		func (v *Viper) ReadConfig(in io.Reader) error
			* 从输入流读取配置

		func (v *Viper) ReadInConfig() error
			* 查找并读取配置文件

		func (v *Viper) ReadRemoteConfig() error
		func (v *Viper) RegisterAlias(alias string, key string)
			* 为指定的key添加别名

		func (v *Viper) SafeWriteConfig() error
		func (v *Viper) SafeWriteConfigAs(filename string) error
		func (v *Viper) Set(key string, value interface{})
			* 覆盖/添加配置项

		func (v *Viper) SetConfigFile(in string)
			* 指定配置文件

		func (v *Viper) SetConfigName(in string)
			* 配置文件名称(无扩展名)
		
		func (v *Viper) SetConfigPermissions(perm os.FileMode)
		func (v *Viper) SetConfigType(in string)
			* 如果配置文件的名称中没有扩展名(SetConfigName)，则需要配置此项

		func (v *Viper) SetDefault(key string, value interface{})
			* 设置默认值

		func (v *Viper) SetEnvKeyReplacer(r *strings.Replacer)
		func (v *Viper) SetEnvPrefix(in string)
			* 设置在读取环境变量时使用前缀

		func (v *Viper) SetFs(fs afero.Fs)
		func (v *Viper) SetTypeByDefaultValue(enable bool)
		func (v *Viper) Sub(key string) *Viper
			* 从指定配置中读取子配置树

		func (v *Viper) Unmarshal(rawVal interface{}, opts ...DecoderConfigOption) error
		func (v *Viper) UnmarshalExact(rawVal interface{}, opts ...DecoderConfigOption) error
		func (v *Viper) UnmarshalKey(key string, rawVal interface{}, opts ...DecoderConfigOption) error
			* 解析到结构体/map
			* Exatc，表示准确的解析需要，如果有不匹配的，则返回异常
			* Key，可以指定仅仅解析指定的Key

			* Viper在后台使用github.com/mitchellh/mapstructure来解析值，其默认情况下使用mapstructuretag。

			
		func (v *Viper) WatchConfig()
		func (v *Viper) WatchRemoteConfig() error
		func (v *Viper) WatchRemoteConfigOnChannel() error
			* 监听配置更改
				
		func (v *Viper) WriteConfig() error
			* 将当前配置写入 "viper.AddConfigPath()" 和 "viper.SetConfigName" 设置的预定义路径

		func (v *Viper) WriteConfigAs(filename string) error



-----------------------
func
-----------------------
	func AddConfigPath(in string)
	func AddRemoteProvider(provider, endpoint, path string) error
	func AddSecureRemoteProvider(provider, endpoint, path, secretkeyring string) error
	func AllKeys() []string
	func AllSettings() map[string]interface{}
	func AllowEmptyEnv(allowEmptyEnv bool)
	func AutomaticEnv()
	func BindEnv(input ...string) error
	func BindFlagValue(key string, flag FlagValue) error
	func BindFlagValues(flags FlagValueSet) error
	func BindPFlag(key string, flag *pflag.Flag) error
	func BindPFlags(flags *pflag.FlagSet) error
	func ConfigFileUsed() string
	func Debug()
	func Get(key string) interface{}
	func GetBool(key string) bool
	func GetDuration(key string) time.Duration
	func GetFloat64(key string) float64
	func GetInt(key string) int
	func GetInt32(key string) int32
	func GetInt64(key string) int64
	func GetIntSlice(key string) []int
	func GetSizeInBytes(key string) uint
	func GetString(key string) string
	func GetStringMap(key string) map[string]interface{}
	func GetStringMapString(key string) map[string]string
	func GetStringMapStringSlice(key string) map[string][]string
	func GetStringSlice(key string) []string
	func GetTime(key string) time.Time
	func GetUint(key string) uint
	func GetUint32(key string) uint32
	func GetUint64(key string) uint64
	func InConfig(key string) bool
	func IsSet(key string) bool
	func MergeConfig(in io.Reader) error
	func MergeConfigMap(cfg map[string]interface{}) error
	func MergeInConfig() error
	func OnConfigChange(run func(in fsnotify.Event))
	func ReadConfig(in io.Reader) error
		* ReadConfig从指定的流读取配置文件

	func ReadInConfig() error
	func ReadRemoteConfig() error
	func RegisterAlias(alias string, key string)
	func Reset()
	func SafeWriteConfig() error
	func SafeWriteConfigAs(filename string) error
	func Set(key string, value interface{})
	func SetConfigFile(in string)
	func SetConfigName(in string)
	func SetConfigPermissions(perm os.FileMode)
	func SetConfigType(in string)
	func SetDefault(key string, value interface{})
	func SetEnvKeyReplacer(r *strings.Replacer)
	func SetEnvPrefix(in string)
	func SetFs(fs afero.Fs)
	func SetTypeByDefaultValue(enable bool)
	func Unmarshal(rawVal interface{}, opts ...DecoderConfigOption) error
	func UnmarshalExact(rawVal interface{}, opts ...DecoderConfigOption) error
	func UnmarshalKey(key string, rawVal interface{}, opts ...DecoderConfigOption) error
	func WatchConfig()
	func WatchRemoteConfig() error
	func WriteConfig() error
	func WriteConfigAs(filename string) error