-----------------
debug
-----------------


-----------------
var
-----------------

-----------------
type
-----------------
	# type BuildInfo struct {
			Path string    // The main package path
			Main Module    // The module containing the main package
			Deps []*Module // Module dependencies
		}
		func ReadBuildInfo() (info *BuildInfo, ok bool)
	
	# type GCStats struct {
			LastGC         time.Time       // time of last collection
			NumGC          int64           // number of garbage collections
			PauseTotal     time.Duration   // total pause for all collections
			Pause          []time.Duration // pause history, most recent first
			PauseEnd       []time.Time     // pause end times history, most recent first
			PauseQuantiles []time.Duration
		}
	
	# type Module struct {
			Path    string  // module path
			Version string  // module version
			Sum     string  // checksum
			Replace *Module // replaced by this module
		}



-----------------
func
-----------------

	func FreeOSMemory()
	func PrintStack()
	func ReadGCStats(stats *GCStats)
	func SetGCPercent(percent int) int
	func SetMaxStack(bytes int) int
	func SetMaxThreads(threads int) int
	func SetPanicOnFault(enabled bool) bool
	func SetTraceback(level string)
	func Stack() []byte
	func WriteHeapDump(fd uintptr)