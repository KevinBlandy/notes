-----------------
syscall
-----------------

-----------------
变量
-----------------
	
-----------------
type
-----------------
	# type Signal int
		func (s Signal) Signal()
		func (s Signal) String() string

		* 预定义
			const (
				SIGABRT   = Signal(0x6)
				SIGALRM   = Signal(0xe)
				SIGBUS    = Signal(0x7)
				SIGCHLD   = Signal(0x11)
				SIGCLD    = Signal(0x11)
				SIGCONT   = Signal(0x12)
				SIGFPE    = Signal(0x8)
				SIGHUP    = Signal(0x1)
				SIGILL    = Signal(0x4)
				SIGINT    = Signal(0x2)
				SIGIO     = Signal(0x1d)
				SIGIOT    = Signal(0x6)
				SIGKILL   = Signal(0x9)
				SIGPIPE   = Signal(0xd)
				SIGPOLL   = Signal(0x1d)
				SIGPROF   = Signal(0x1b)
				SIGPWR    = Signal(0x1e)
				SIGQUIT   = Signal(0x3)
				SIGSEGV   = Signal(0xb)
				SIGSTKFLT = Signal(0x10)
				SIGSTOP   = Signal(0x13)
				SIGSYS    = Signal(0x1f)
				SIGTERM   = Signal(0xf)
				SIGTRAP   = Signal(0x5)
				SIGTSTP   = Signal(0x14)
				SIGTTIN   = Signal(0x15)
				SIGTTOU   = Signal(0x16)
				SIGUNUSED = Signal(0x1f)
				SIGURG    = Signal(0x17)
				SIGUSR1   = Signal(0xa)
				SIGUSR2   = Signal(0xc)
				SIGVTALRM = Signal(0x1a)
				SIGWINCH  = Signal(0x1c)
				SIGXCPU   = Signal(0x18)
				SIGXFSZ   = Signal(0x19)
			)
	
	# type SysProcAttr struct {
			Chroot     string      // Chroot.
			Credential *Credential // Credential.
			Ptrace bool
			Setsid bool // Create session.
			Setpgid bool
			Setctty bool
			Noctty  bool // Detach fd 0 from controlling terminal
			Ctty    int  // Controlling TTY fd
			Foreground   bool
			Pgid         int            // Child's process group ID if Setpgid.
			Pdeathsig    Signal         // Signal that the process will get when its parent dies (Linux only)
			Cloneflags   uintptr        // Flags for clone calls (Linux only)
			Unshareflags uintptr        // Flags for unshare calls (Linux only)
			UidMappings  []SysProcIDMap // User ID mappings for user namespaces.
			GidMappings  []SysProcIDMap // Group ID mappings for user namespaces.
			GidMappingsEnableSetgroups bool
			AmbientCaps                []uintptr // Ambient capabilities (Linux only)
		}

		* 系统进程信息

-----------------
func
-----------------




--------------------------
监听系统进程消息
--------------------------
	package main

	import (
		"log"
		"os"
		"os/signal"
		"syscall"
	)

	func main(){
		// TODO 启动其他服务，
		signalHandler()
	}

	func signalHandler(){
		var ch = make(chan os.Signal)
		signal.Notify(ch, syscall.SIGHUP, syscall.SIGINT, syscall.SIGQUIT, syscall.SIGILL, syscall.SIGTRAP,
			syscall.SIGABRT, syscall.SIGBUS, syscall.SIGFPE, syscall.SIGKILL,
			syscall.SIGSEGV, syscall.SIGPIPE, syscall.SIGALRM, syscall.SIGTERM)
		for {
			sig := <- ch
			// 处理各种信号
			log.Printf("app get a signal %s\n", sig.String())
			switch sig {
				case syscall.SIGABRT, syscall.SIGINT: {
					return
				}
				default: {
				}
			}
		}
	}