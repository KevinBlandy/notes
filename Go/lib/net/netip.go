-----------------------
netip
-----------------------
	# 一个新的 IP 地址类型Addr. 
		* 与现有 net.IP类型相比，该netip.Addr类型占用的内存更少，不可变
		* 并且具有可比性，因此它支持 == 并可以用作Map的key


-----------------------
var
-----------------------

-----------------------
type
-----------------------
	# type Addr struct {
			// contains filtered or unexported fields
		}
		
		func AddrFrom16(addr [16]byte) Addr
		func AddrFrom4(addr [4]byte) Addr
		func AddrFromSlice(slice []byte) (ip Addr, ok bool)
		func IPv4Unspecified() Addr
		func IPv6LinkLocalAllNodes() Addr
		func IPv6Unspecified() Addr
		func MustParseAddr(s string) Addr
		func ParseAddr(s string) (Addr, error)
		func (ip Addr) AppendTo(b []byte) []byte
		func (ip Addr) As16() (a16 [16]byte)
		func (ip Addr) As4() (a4 [4]byte)
		func (ip Addr) AsSlice() []byte
		func (ip Addr) BitLen() int
		func (ip Addr) Compare(ip2 Addr) int
		func (ip Addr) Is4() bool
		func (ip Addr) Is4In6() bool
		func (ip Addr) Is6() bool
		func (ip Addr) IsGlobalUnicast() bool
		func (ip Addr) IsInterfaceLocalMulticast() bool
		func (ip Addr) IsLinkLocalMulticast() bool
		func (ip Addr) IsLinkLocalUnicast() bool
		func (ip Addr) IsLoopback() bool
		func (ip Addr) IsMulticast() bool
		func (ip Addr) IsPrivate() bool
		func (ip Addr) IsUnspecified() bool
		func (ip Addr) IsValid() bool
		func (ip Addr) Less(ip2 Addr) bool
		func (ip Addr) MarshalBinary() ([]byte, error)
		func (ip Addr) MarshalText() ([]byte, error)
		func (ip Addr) Next() Addr
		func (ip Addr) Prefix(b int) (Prefix, error)
		func (ip Addr) Prev() Addr
		func (ip Addr) String() string
		func (ip Addr) StringExpanded() string
		func (ip Addr) Unmap() Addr
		func (ip *Addr) UnmarshalBinary(b []byte) error
		func (ip *Addr) UnmarshalText(text []byte) error
		func (ip Addr) WithZone(zone string) Addr
		func (ip Addr) Zone() string

		* 地址
	
	# type AddrPort struct {
			// contains filtered or unexported fields
		}
		func AddrPortFrom(ip Addr, port uint16) AddrPort
		func MustParseAddrPort(s string) AddrPort
		func ParseAddrPort(s string) (AddrPort, error)
		func (p AddrPort) Addr() Addr
		func (p AddrPort) AppendTo(b []byte) []byte
		func (p AddrPort) IsValid() bool
		func (p AddrPort) MarshalBinary() ([]byte, error)
		func (p AddrPort) MarshalText() ([]byte, error)
		func (p AddrPort) Port() uint16
		func (p AddrPort) String() string
		func (p *AddrPort) UnmarshalBinary(b []byte) error
		func (p *AddrPort) UnmarshalText(text []byte) error

		* 地址和端口
	
	# type Prefix struct {
		}
		func MustParsePrefix(s string) Prefix
		func ParsePrefix(s string) (Prefix, error)
		func PrefixFrom(ip Addr, bits int) Prefix
		func (p Prefix) Addr() Addr
		func (p Prefix) AppendTo(b []byte) []byte
		func (p Prefix) Bits() int
		func (p Prefix) Contains(ip Addr) bool
		func (p Prefix) IsSingleIP() bool
		func (p Prefix) IsValid() bool
		func (p Prefix) MarshalBinary() ([]byte, error)
		func (p Prefix) MarshalText() ([]byte, error)
		func (p Prefix) Masked() Prefix
		func (p Prefix) Overlaps(o Prefix) bool
		func (p Prefix) String() string
		func (p *Prefix) UnmarshalBinary(b []byte) error
		func (p *Prefix) UnmarshalText(text []byte) error

-----------------------
func
-----------------------
