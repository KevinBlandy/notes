------------------
var
------------------
	const (
		BashCompFilenameExt     = "cobra_annotation_bash_completion_filename_extensions"
		BashCompCustom          = "cobra_annotation_bash_completion_custom"
		BashCompOneRequiredFlag = "cobra_annotation_bash_completion_one_required_flag"
		BashCompSubdirsInDir    = "cobra_annotation_bash_completion_subdirs_in_dir"
	)

	const (
		ShellCompRequestCmd = "__complete"
		ShellCompNoDescRequestCmd = "__completeNoDesc"
	)
	
	var EnableCommandSorting = true
	var EnablePrefixMatching = false
	var MousetrapDisplayDuration = 5 * time.Second
	var MousetrapHelpText = `This is a command line tool.
	You need to open cmd.exe and run it from there.
	`
------------------
type
------------------
	# type Command struct {
			Use string
				* 命令
			Aliases []string
				* 别名
			SuggestFor []string
			Short string
				* 短的说明

			Long string
				* 长的说明

			Example string
				* 使用例子

			ValidArgs []string
			ValidArgsFunction func(cmd *Command, args []string, toComplete string) ([]string, ShellCompDirective)
			Args PositionalArgs
			ArgAliases []string
			BashCompletionFunction string
			Deprecated string
			Annotations map[string]string
			Version string
			PersistentPreRun func(cmd *Command, args []string)
			PersistentPreRunE func(cmd *Command, args []string) error
			PreRun func(cmd *Command, args []string)
			PreRunE func(cmd *Command, args []string) error
			Run func(cmd *Command, args []string)
				* 执行命令的方法
				* 参数就是命令(cmd)，以及除了命令选项以外的其他所有参数(args)

			RunE func(cmd *Command, args []string) error
			PostRun func(cmd *Command, args []string)
			PostRunE func(cmd *Command, args []string) error
			PersistentPostRun func(cmd *Command, args []string)
			PersistentPostRunE func(cmd *Command, args []string) error
			FParseErrWhitelist FParseErrWhitelist
			TraverseChildren bool
			Hidden bool
			SilenceErrors bool
			SilenceUsage bool
			DisableFlagParsing bool
			DisableAutoGenTag bool
			DisableFlagsInUseLine bool
			DisableSuggestions bool
			SuggestionsMinimumDistance int
		}
		func (c *Command) AddCommand(cmds ...*Command)
		func (c *Command) ArgsLenAtDash() int
		func (c *Command) CalledAs() string
		func (c *Command) CommandPath() string
		func (c *Command) CommandPathPadding() int
		func (c *Command) Commands() []*Command
		func (c *Command) Context() context.Context
		func (c *Command) DebugFlags()
		func (c *Command) ErrOrStderr() io.Writer
		func (c *Command) Execute() error
		func (c *Command) ExecuteC() (cmd *Command, err error)
		func (c *Command) ExecuteContext(ctx context.Context) error
		func (c *Command) Find(args []string) (*Command, []string, error)
		func (c *Command) Flag(name string) (flag *flag.Flag)
		func (c *Command) FlagErrorFunc() (f func(*Command, error) error)
		func (c *Command) Flags() *flag.FlagSet
			* 返回 flag设置，这个flag是github.com/spf13/pflag中的flag
			* 用于添加/读取选项值
			* 本地选项，只能在这个命令中使用

		func (c *Command) GenBashCompletion(w io.Writer) error
		func (c *Command) GenBashCompletionFile(filename string) error
		func (c *Command) GenFishCompletion(w io.Writer, includeDesc bool) error
		func (c *Command) GenFishCompletionFile(filename string, includeDesc bool) error
		func (c *Command) GenPowerShellCompletion(w io.Writer) error
		func (c *Command) GenPowerShellCompletionFile(filename string) error
		func (c *Command) GenPowerShellCompletionFileWithDesc(filename string) error
		func (c *Command) GenPowerShellCompletionWithDesc(w io.Writer) error
		func (c *Command) GenZshCompletion(w io.Writer) error
		func (c *Command) GenZshCompletionFile(filename string) error
		func (c *Command) GenZshCompletionFileNoDesc(filename string) error
		func (c *Command) GenZshCompletionNoDesc(w io.Writer) error
		func (c *Command) GlobalNormalizationFunc() func(f *flag.FlagSet, name string) flag.NormalizedName
		func (c *Command) HasAlias(s string) bool
		func (c *Command) HasAvailableFlags() bool
		func (c *Command) HasAvailableInheritedFlags() bool
		func (c *Command) HasAvailableLocalFlags() bool
		func (c *Command) HasAvailablePersistentFlags() bool
		func (c *Command) HasAvailableSubCommands() bool
		func (c *Command) HasExample() bool
		func (c *Command) HasFlags() bool
		func (c *Command) HasHelpSubCommands() bool
		func (c *Command) HasInheritedFlags() bool
		func (c *Command) HasLocalFlags() bool
		func (c *Command) HasParent() bool
		func (c *Command) HasPersistentFlags() bool
		func (c *Command) HasSubCommands() bool
		func (c *Command) Help() error
		func (c *Command) HelpFunc() func(*Command, []string)
		func (c *Command) HelpTemplate() string
		func (c *Command) InOrStdin() io.Reader
		func (c *Command) InheritedFlags() *flag.FlagSet
		func (c *Command) InitDefaultHelpCmd()
		func (c *Command) InitDefaultHelpFlag()
		func (c *Command) InitDefaultVersionFlag()
		func (c *Command) IsAdditionalHelpTopicCommand() bool
		func (c *Command) IsAvailableCommand() bool
		func (c *Command) LocalFlags() *flag.FlagSet
		func (c *Command) LocalNonPersistentFlags() *flag.FlagSet
		func (c *Command) MarkFlagCustom(name string, f string) error
		func (c *Command) MarkFlagDirname(name string) error
		func (c *Command) MarkFlagFilename(name string, extensions ...string) error
		func (c *Command) MarkFlagRequired(name string) error
		func (c *Command) MarkPersistentFlagDirname(name string) error
		func (c *Command) MarkPersistentFlagFilename(name string, extensions ...string) error
		func (c *Command) MarkPersistentFlagRequired(name string) error
		func (c *Command) MarkZshCompPositionalArgumentFile(argPosition int, patterns ...string) error
		func (c *Command) MarkZshCompPositionalArgumentWords(argPosition int, words ...string) error
		func (c *Command) Name() string
			* 返回命令的名称

		func (c *Command) NameAndAliases() string
		func (c *Command) NamePadding() int
		func (c *Command) NonInheritedFlags() *flag.FlagSet
		func (c *Command) OutOrStderr() io.Writer
		func (c *Command) OutOrStdout() io.Writer
		func (c *Command) Parent() *Command
		func (c *Command) ParseFlags(args []string) error
		func (c *Command) PersistentFlags() *flag.FlagSet
			* 返回永久flag设置，定义它的命令和其子命令都可以使用

		func (c *Command) Print(i ...interface{})
		func (c *Command) PrintErr(i ...interface{})
		func (c *Command) PrintErrf(format string, i ...interface{})
		func (c *Command) PrintErrln(i ...interface{})
		func (c *Command) Printf(format string, i ...interface{})
		func (c *Command) Println(i ...interface{})
		func (c *Command) RegisterFlagCompletionFunc(flagName string, ...) error
		func (c *Command) RemoveCommand(cmds ...*Command)
		func (c *Command) ResetCommands()
		func (c *Command) ResetFlags()
		func (c *Command) Root() *Command
		func (c *Command) Runnable() bool
		func (c *Command) SetArgs(a []string)
		func (c *Command) SetErr(newErr io.Writer)
		func (c *Command) SetFlagErrorFunc(f func(*Command, error) error)
		func (c *Command) SetGlobalNormalizationFunc(n func(f *flag.FlagSet, name string) flag.NormalizedName)
		func (c *Command) SetHelpCommand(cmd *Command)
		func (c *Command) SetHelpFunc(f func(*Command, []string))
			* 设置用于输出帮助信息的方法

		func (c *Command) SetHelpTemplate(s string)
		func (c *Command) SetIn(newIn io.Reader)
		func (c *Command) SetOut(newOut io.Writer)
		func (c *Command) SetOutput(output io.Writer)
		func (c *Command) SetUsageFunc(f func(*Command) error)
		func (c *Command) SetUsageTemplate(s string)
		func (c *Command) SetVersionTemplate(s string)
		func (c *Command) SuggestionsFor(typedName string) []string
		func (c *Command) Traverse(args []string) (*Command, []string, error)
		func (c *Command) Usage() error
		func (c *Command) UsageFunc() (f func(*Command) error)
		func (c *Command) UsagePadding() int
		func (c *Command) UsageString() string
		func (c *Command) UsageTemplate() string
		func (c *Command) UseLine() string
		func (c *Command) ValidateArgs(args []string) error
		func (c *Command) VersionTemplate() string
		func (c *Command) VisitParents(fn func(*Command))
	
	# type FParseErrWhitelist flag.ParseErrorsWhitelist

	# type PositionalArgs func(cmd *Command, args []string) error
		func ExactArgs(n int) PositionalArgs
		func ExactValidArgs(n int) PositionalArgs
		func MaximumNArgs(n int) PositionalArgs
		func MinimumNArgs(n int) PositionalArgs
		func RangeArgs(min int, max int) PositionalArgs

	# type ShellCompDirective int

------------------
func
------------------
	func AddTemplateFunc(name string, tmplFunc interface{})
	func AddTemplateFuncs(tmplFuncs template.FuncMap)
	func ArbitraryArgs(cmd *Command, args []string) error
	func CheckErr(msg interface{})
	func CompDebug(msg string, printToStdErr bool)
	func CompDebugln(msg string, printToStdErr bool)
	func CompError(msg string)
	func CompErrorln(msg string)
	func Eq(a interface{}, b interface{}) bool
	func Gt(a interface{}, b interface{}) bool
	func MarkFlagCustom(flags *pflag.FlagSet, name string, f string) error
	func MarkFlagDirname(flags *pflag.FlagSet, name string) error
	func MarkFlagFilename(flags *pflag.FlagSet, name string, extensions ...string) error
	func MarkFlagRequired(flags *pflag.FlagSet, name string) error
	func NoArgs(cmd *Command, args []string) error
	func OnInitialize(y ...func())
	func OnlyValidArgs(cmd *Command, args []string) error
	func WriteStringAndCheck(b io.StringWriter, s string)