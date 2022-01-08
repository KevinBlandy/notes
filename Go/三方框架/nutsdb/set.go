------------------------
set
------------------------
	# 方法
		func New() *Set
		func (s *Set) SAdd(key string, items ...[]byte) error
		func (s *Set) SAreMembers(key string, items ...[]byte) (bool, error)
		func (s *Set) SCard(key string) int
		func (s *Set) SDiff(key1, key2 string) (list [][]byte, err error)
		func (s *Set) SHasKey(key string) bool
		func (s *Set) SInter(key1, key2 string) (list [][]byte, err error)
		func (s *Set) SIsMember(key string, item []byte) bool
		func (s *Set) SMembers(key string) (list [][]byte, err error)
		func (s *Set) SMove(key1, key2 string, item []byte) (bool, error)
		func (s *Set) SPop(key string) []byte
		func (s *Set) SRem(key string, items ...[]byte) error
		func (s *Set) SUnion(key1, key2 string) (list [][]byte, err error)