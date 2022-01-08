---------------------------
zset
---------------------------
	# const
		const (
			// SkipListMaxLevel represents the skipList max level number.
			SkipListMaxLevel = 32

			// SkipListP represents the p parameter of the skipList.
			SkipListP = 0.25
		)
	
	# type
		type GetByScoreRangeOptions struct {
			Limit        int
				* 限制返回的node数目
			ExcludeStart bool
				* 排除start
			ExcludeEnd
				* 排除end
		}

		type SCORE float64

		type SortedSet struct {
			Dict map[string]*SortedSetNode
			// contains filtered or unexported fields
		}
		func New() *SortedSet
		func (ss *SortedSet) FindRank(key string) int
		func (ss *SortedSet) FindRevRank(key string) int
		func (ss *SortedSet) GetByKey(key string) *SortedSetNode
		func (ss *SortedSet) GetByRank(rank int, remove bool) *SortedSetNode
		func (ss *SortedSet) GetByRankRange(start, end int, remove bool) []*SortedSetNode
		func (ss *SortedSet) GetByScoreRange(start SCORE, end SCORE, options *GetByScoreRangeOptions) []*SortedSetNode
		func (ss *SortedSet) PeekMax() *SortedSetNode
		func (ss *SortedSet) PeekMin() *SortedSetNode
		func (ss *SortedSet) PopMax() *SortedSetNode
		func (ss *SortedSet) PopMin() *SortedSetNode
		func (ss *SortedSet) Put(key string, score SCORE, value []byte) error
		func (ss *SortedSet) Remove(key string) *SortedSetNode
		func (ss *SortedSet) Size() int
	
		type SortedSetLevel struct {
			// contains filtered or unexported fields
		}
		
		type SortedSetNode struct {
			Value []byte // associated data
		}
		func (ssn *SortedSetNode) Key() string
		func (ssn *SortedSetNode) Score() SCORE
	