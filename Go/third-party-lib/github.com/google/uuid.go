----------------------
UUID
----------------------
	# UUID°ü
		https://github.com/google/uuid
		https://pkg.go.dev/github.com/google/uuid
	


----------------------
var
----------------------

----------------------
type
----------------------
	# type UUID [16]byte

		func New() UUID
		func NewUUID() (UUID, error) 

		func (uuid UUID) ClockSequence() int
		func (uuid UUID) Domain() Domain
		func (uuid UUID) ID() uint32
		func (uuid UUID) MarshalBinary() ([]byte, error)
		func (uuid UUID) MarshalText() ([]byte, error)
		func (uuid UUID) NodeID() []byte
		func (uuid *UUID) Scan(src interface{}) error
		func (uuid UUID) String() string
		func (uuid UUID) Time() Time
		func (uuid UUID) URN() string
		func (uuid *UUID) UnmarshalBinary(data []byte) error
		func (uuid *UUID) UnmarshalText(data []byte) error
		func (uuid UUID) Value() (driver.Value, error)
		func (uuid UUID) Variant() Variant
		func (uuid UUID) Version() Version


----------------------
method
----------------------
	
----------------------
»ñÈ¡UUID×Ö·û´®
----------------------
package main
import (
	"fmt"
	"github.com/google/uuid"
	"strings"
)
func main() {
	id := uuid.New()
	str := id.String()
	fmt.Println(str) // 4f8ff5f5-443b-11eb-ab81-00fff8633c98

	str = strings.ReplaceAll(str, "-",  "")	// 4f8ff5f5443b11ebab8100fff8633c98
	fmt.Println(str)
}
