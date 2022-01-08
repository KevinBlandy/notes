---------------------
nutsdb
---------------------
	# 嵌入式KEY/VALUE数据库
		https://github.com/xujiajun/nutsdb
		https://github.com/xujiajun/nutsdb/blob/master/README-CN.md

		github.com/xujiajun/nutsdb

---------------------
var
---------------------
	const (
		// DefaultInvalidAddress returns default invalid node address.
		DefaultInvalidAddress = -1
		// RangeScan returns range scanMode flag.
		RangeScan = "RangeScan"
		// PrefixScan returns prefix scanMode flag.
		PrefixScan = "PrefixScan"
		// PrefixSearchScan returns prefix and search scanMode flag.
		PrefixSearchScan = "PrefixSearchScan"
		// CountFlagEnabled returns enabled CountFlag.
		CountFlagEnabled = true
		// CountFlagDisabled returns disabled CountFlag.
		CountFlagDisabled = false
		// BPTIndexSuffix returns b+ tree index suffix.
		BPTIndexSuffix = ".bptidx"
		// BPTRootIndexSuffix returns b+ tree root index suffix.
		BPTRootIndexSuffix = ".bptridx"
		// BPTTxIDIndexSuffix returns b+ tree tx ID index suffix.
		BPTTxIDIndexSuffix = ".bpttxid"
		// BPTRootTxIDIndexSuffix returns b+ tree root tx ID index suffix.
		BPTRootTxIDIndexSuffix = ".bptrtxid"
	)
	const (
		// BucketMetaHeaderSize returns the header size of the BucketMeta.
		BucketMetaHeaderSize = 12
		// BucketMetaSuffix returns b+ tree index suffix.
		BucketMetaSuffix = ".meta"
	)
	const (
		// DataSuffix returns the data suffix
		DataSuffix = ".dat"
		// DataEntryHeaderSize returns the entry header size
		DataEntryHeaderSize = 42
	)
	const (
		// DataDeleteFlag represents the data delete flag
		DataDeleteFlag uint16 = iota
		// DataSetFlag represents the data set flag
		DataSetFlag
		// DataLPushFlag represents the data LPush flag
		DataLPushFlag
		// DataRPushFlag represents the data RPush flag
		DataRPushFlag
		// DataLRemFlag represents the data LRem flag
		DataLRemFlag
		// DataLPopFlag represents the data LPop flag
		DataLPopFlag
		// DataRPopFlag represents the data RPop flag
		DataRPopFlag
		// DataLSetFlag represents the data LSet flag
		DataLSetFlag
		// DataLTrimFlag represents the data LTrim flag
		DataLTrimFlag
		// DataZAddFlag represents the data ZAdd flag
		DataZAddFlag
		// DataZRemFlag represents the data ZRem flag
		DataZRemFlag
		// DataZRemRangeByRankFlag represents the data ZRemRangeByRank flag
		DataZRemRangeByRankFlag
		// DataZPopMaxFlag represents the data ZPopMax flag
		DataZPopMaxFlag
		// DataZPopMinFlag represents the data aZPopMin flag
		DataZPopMinFlag
	)
	const (
		// UnCommitted represents the tx unCommitted status
		UnCommitted uint16 = 0
		// Committed represents the tx committed status
		Committed uint16 = 1
		Persistent uint32 = 0
			* KEY用不过期的Flag

		// ScanNoLimit represents the data scan no limit flag
		ScanNoLimit int = -1
	)
	const (
		// DataStructureSet represents the data structure set flag
		DataStructureSet uint16 = iota
		// DataStructureSortedSet represents the data structure sorted set flag
		DataStructureSortedSet
		// DataStructureBPTree represents the data structure b+ tree flag
		DataStructureBPTree
		// DataStructureList represents the data structure list flag
		DataStructureList
	)
	const BPTreeRootIdxHeaderSize = 28
	const SeparatorForListKey = "|"
	const SeparatorForZSetKey = "|"
	var (
		// ErrStartKey is returned when Range is called by a error start key.
		ErrStartKey = errors.New("err start key")
		// ErrScansNoResult is returned when Range or prefixScan or prefixSearchScan are called no result to found.
		ErrScansNoResult = errors.New("range scans or prefix or prefix and search scans no result")
		// ErrPrefixScansNoResult is returned when prefixScan is called no result to found.
		ErrPrefixScansNoResult = errors.New("prefix scans no result")
		// ErrPrefixSearchScansNoResult is returned when prefixSearchScan is called no result to found.
		ErrPrefixSearchScansNoResult = errors.New("prefix and search scans no result")
		// ErrKeyNotFound is returned when the key is not in the b+ tree.
		ErrKeyNotFound = errors.New("key not found")
		// ErrBadRegexp is returned when bad regular expression given.
		ErrBadRegexp = errors.New("bad regular expression")
	)
	var (
		// ErrCrcZero is returned when crc is 0
		ErrCrcZero = errors.New("error crc is 0")

		// ErrCrc is returned when crc is error
		ErrCrc = errors.New(" crc error")

		// ErrCapacity is returned when capacity is error.
		ErrCapacity = errors.New("capacity error")
	)
	var (
		// ErrDBClosed is returned when db is closed.
		ErrDBClosed = errors.New("db is closed")

		// ErrBucket is returned when bucket is not in the HintIdx.
		ErrBucket = errors.New("err bucket")

		// ErrEntryIdxModeOpt is returned when set db EntryIdxMode option is wrong.
		ErrEntryIdxModeOpt = errors.New("err EntryIdxMode option set")

		// ErrFn is returned when fn is nil.
		ErrFn = errors.New("err fn")

		// ErrBucketNotFound is returned when looking for bucket that does not exist
		ErrBucketNotFound = errors.New("bucket not found")
	)
	var (
		// ErrUnmappedMemory is returned when a function is called on unmapped memory
		ErrUnmappedMemory = errors.New("unmapped memory")

		// ErrIndexOutOfBound is returned when given offset out of mapped region
		ErrIndexOutOfBound = errors.New("offset out of mapped region")
	)
	var (
		// ErrKeyAndValSize is returned when given key and value size is too big.
		ErrKeyAndValSize = errors.New("key and value size too big")

		// ErrTxClosed is returned when committing or rolling back a transaction
		// that has already been committed or rolled back.
		ErrTxClosed = errors.New("tx is closed")

		// ErrTxNotWritable is returned when performing a write operation on
		// a read-only transaction.
		ErrTxNotWritable = errors.New("tx not writable")

		// ErrKeyEmpty is returned if an empty key is passed on an update function.
		ErrKeyEmpty = errors.New("key cannot be empty")

		// ErrBucketEmpty is returned if bucket is empty.
		ErrBucketEmpty = errors.New("bucket is empty")

		// ErrRangeScan is returned when range scanning not found the result
		ErrRangeScan = errors.New("range scans not found")

		// ErrPrefixScan is returned when prefix scanning not found the result
		ErrPrefixScan = errors.New("prefix scans not found")

		// ErrPrefixSearchScan is returned when prefix and search scanning not found the result
		ErrPrefixSearchScan = errors.New("prefix and search scans not found")

		// ErrNotFoundKey is returned when key not found int the bucket on an view function.
		ErrNotFoundKey = errors.New("key not found in the bucket")
	)

	var DefaultOptions = Options{
		EntryIdxMode:         HintKeyValAndRAMIdxMode,
		SegmentSize:          defaultSegmentSize,
		NodeNum:              1,
		RWMode:               FileIO,
		SyncEnable:           true,
		StartFileLoadingMode: MMap,
	}

	* 默认的配置

---------------------
type
---------------------
	# type BucketMeta struct {
		}
	
		* bucket的元属性

		func ReadBucketMeta(name string) (bucketMeta *BucketMeta, err error)
			* 获取指定bucket的元属性

		func (bm *BucketMeta) Encode() []byte
		func (bm *BucketMeta) GetCrc(buf []byte) uint32
		func (bm *BucketMeta) Size() int64

	# type DB struct {
			BPTreeIdx            BPTreeIdx // Hint Index
			BPTreeRootIdxes      []*BPTreeRootIdx
			BPTreeKeyEntryPosMap map[string]int64 // key = bucket+key  val = EntryPos

			SetIdx                  SetIdx
			SortedSetIdx            SortedSetIdx
			ListIdx                 ListIdx
			ActiveFile              *DataFile
			ActiveBPTreeIdx         *BPTree
			ActiveCommittedTxIdsIdx *BPTree

			MaxFileID int64

			KeyCount int // total key number ,include expired, deleted, repeated.
		}
		func Open(opt Options) (*DB, error)
			* 根据配置创建DB对线，数据目录不存在会执行创建

		func (db *DB) Backup(dir string) error
			* 备份到指定的目录
			* 这个方法执行的是一个热备份，不会阻塞到数据库其他的只读事务操作，对写（读写）事务会有影响。

		func (db *DB) Begin(writable bool) (tx *Tx, err error)
			* 开启事务，通过参数指定事务是否为可写事务

		func (db *DB) Close() error
			* 关闭

		func (db *DB) Merge() error
			* 整理存储空间
			* 一些删除或者过期的数据占据着磁盘，通过这个方法完成清理，但是有性能缺陷，尽量在访问低的时候使用

		func (db *DB) Update(fn func(tx *Tx) error) error
			* 更新事务

		func (db *DB) View(fn func(tx *Tx) error) error
			* 只读事务
	
	# type Entries []*Entry

	# type Entry struct {
			Key   []byte
			Value []byte
			Meta  *MetaData
		}
		
		* 返回的Value项

		func (e *Entry) Encode() []byte
		func (e *Entry) GetCrc(buf []byte) uint32
		func (e *Entry) IsZero() bool
		func (e *Entry) Size() int64
	
	# type MetaData struct {
			TTL  uint32
			Flag uint16 // delete / set
		}
	
	# type Options struct {
			Dir string
				* 存储目录

			EntryIdxMode EntryIdxMode
				* 索引entry的模式
				* 枚举
					HintKeyValAndRAMIdxMode (默认)
						* 代表纯内存索引模式（key和value都会被cache）
					HintKeyAndRAMIdxMode
						* 内存+磁盘的索引模式（只有key被cache）。
					HintBPTSparseIdxMode
						* 专门节约内存的设计方案，单机10亿条数据，只要80几M内存。但是读性能不高，需要自己加缓存来加速。

			RWMode      RWMode
				* IO模式
				* 枚举
					FileIO (默认)
						* 标准IO
					MMap
						* MMAP


			SegmentSize int64
				* 分段文件大小，配置后不能修改

			NodeNum int64
				* 节点编号，取值范围:1 - 10223, 默认: 1

			SyncEnable bool
				* 是否开启同步刷盘，同步刷盘不会丢失数据，但是可能会损失性能
				* 默认 true

			StartFileLoadingMode RWMode
				* 代表启动数据库的载入文件的方式。参数选项同 RWMode
				* 枚举
					FileIO 
					MMap(默认)
			
		}
		
		* DB 配置
	
	# type RWMode int
		* 读写方式，枚举
			const (
				//FileIO represents the read and write mode using standard I/O.
				FileIO RWMode = iota
				//MMap represents the read and write mode using mmap.
				MMap
			)
	
	# type Tx struct {
			ReservedStoreTxIDIdxes map[int64]*BPTree
		}

		* 事务操作

		func (tx *Tx) Commit() error
			* 提交事务

		func (tx *Tx) Delete(bucket string, key []byte) error
			* 根据key删除value

		func (tx *Tx) FindLeafOnDisk(fID int64, rootOff int64, key, newKey []byte) (bn *BinaryNode, err error)
		func (tx *Tx) FindOnDisk(fID uint64, rootOff uint64, key, newKey []byte) (entry *Entry, err error)
		func (tx *Tx) FindTxIDOnDisk(fID, txID uint64) (ok bool, err error)

		func (tx *Tx) Get(bucket string, key []byte) (e *Entry, err error)
			* 获取value
				
		func (tx *Tx) GetAll(bucket string) (entries Entries, err error)
			* 获取所有value

		func (tx *Tx) PrefixScan(bucket string, prefix []byte, offsetNum int, limitNum int) (es Entries, off int, err error)
			* 前缀扫描，使用参数 offSet 和 limitNum 来限制返回的结果的数量
			* 从offset开始 ，限制 limitNum 个 entries 返回 

		func (tx *Tx) PrefixSearchScan(bucket string, prefix []byte, reg string, offsetNum int, limitNum int) (es Entries, off int, err error)
			* 前缀正则扫描

		func (tx *Tx) Put(bucket string, key, value []byte, ttl uint32) error
			* 存入数据，可以指定ttl过期时间。单位是秒，如果设置为0则表示永不过期

		func (tx *Tx) PutWithTimestamp(bucket string, key, value []byte, ttl uint32, timestamp uint64) error

		func (tx *Tx) RangeScan(bucket string, start, end []byte) (es Entries, err error)
			* 范围的扫描

		func (tx *Tx) Rollback() error
			* 回滚事务


		// List 的操作 -----------------
		func (tx *Tx) RPush(bucket string, key []byte, values ...[]byte) error
			* 从右边入队列
		func (tx *Tx) LPush(bucket string, key []byte, values ...[]byte) error
			* 从左边入队列
		func (tx *Tx) LPop(bucket string, key []byte) (item []byte, err error)
			* 弹出最左边的元素，删除
		func (tx *Tx) LPeek(bucket string, key []byte) (item []byte, err error)
			* 获取最左边的元素，不删除
		func (tx *Tx) RPop(bucket string, key []byte) (item []byte, err error)
			* 弹出最右边元素，删除
		func (tx *Tx) RPeek(bucket string, key []byte) (item []byte, err error)
			* 获取最右边元素，不删除
		func (tx *Tx) LRange(bucket string, key []byte, start, end int) (list [][]byte, err error)
			* 返回指定bucket里面的指定队列key列表里指定范围内的元素。 
			* start 和 end 偏移量都是基于0的下标，即list的第一个元素下标是0（list的表头），第二个元素下标是1，以此类推。
			* 偏移量也可以是负数，表示偏移量是从list尾部开始计数。 例如：-1 表示列表的最后一个元素，-2 是倒数第二个，以此类推。
		func (tx *Tx) LRem(bucket string, key []byte, count int, value []byte) (removedNum int, err error)
			* 从指定bucket里面的指定的key的列表里移除前 count 次出现的值为 value 的元素。
			* 这个 count 参数通过下面几种方式影响这个操作：
				count > 0: 从头往尾移除值为 value 的元素
				count < 0: 从尾往头移除值为 value 的元素
				count = 0: 移除所有值为 value 的元素
		func (tx *Tx) LSet(bucket string, key []byte, index int, value []byte) error
			* 设置指定位置的值
		func (tx *Tx) LSize(bucket string, key []byte) (int, error)
			* 返回大小
		func (tx *Tx) LTrim(bucket string, key []byte, start, end int) error
			* 修剪一个已存在的 list，这样 list 就会只包含指定范围的指定元素。
			* start 和 stop 都是由0开始计数的， 这里的 0 是列表里的第一个元素（表头），1 是第二个元素，以此类推。

		// Set 的操作 -----------------
		func (tx *Tx) SAdd(bucket string, key []byte, items ...[]byte) error
			* 添加元素

		func (tx *Tx) SAreMembers(bucket string, key []byte, items ...[]byte) (bool, error)
			* 返回 items 是否都存在于 key 中

		func (tx *Tx) SCard(bucket string, key []byte) (int, error)
			* 返回集合中元素的数量
			
		func (tx *Tx) SDiffByOneBucket(bucket string, key1, key2 []byte) (list [][]byte, err error)
			* 返回一个集合与给定集合的差集的元素。这俩key在同一个bucket中

		func (tx *Tx) SDiffByTwoBuckets(bucket1 string, key1 []byte, bucket2 string, key2 []byte) (list [][]byte, err error)
			* 返回一个集合与给定集合的差集的元素。这俩key不在同一个bucket中

		func (tx *Tx) SHasKey(bucket string, key []byte) (bool, error)
			* 判断是否指定的集合在指定的bucket中。

		func (tx *Tx) SIsMember(bucket string, key, item []byte) (bool, error)
			* 返回 item 是否 key 的成员
		
		func (tx *Tx) SMembers(bucket string, key []byte) (list [][]byte, err error)
			* 返回Set所有元素

		func (tx *Tx) SMoveByOneBucket(bucket string, key1, key2, item []byte) (bool, error)
			* 将member从source集合移动到destination集合中，其中source集合和destination集合均在一个bucket中。

		func (tx *Tx) SMoveByTwoBuckets(bucket1 string, key1 []byte, bucket2 string, key2, item []byte) (bool, error)
			* 将member从source集合移动到destination集合中。其中source集合和destination集合在两个不同的bucket中。
		
		func (tx *Tx) SPop(bucket string, key []byte) ([]byte, error)
			* 从指定bucket里的指定key的集合中移除并返回一个或多个随机元素。
		
		func (tx *Tx) SRem(bucket string, key []byte, items ...[]byte) error
			* 在指定bucket里面移除指定的key集合中移除指定的一个或者多个元素。
		
		func (tx *Tx) SUnionByOneBucket(bucket string, key1, key2 []byte) (list [][]byte, err error)
			* 返回指定一个bucket里面的给定的两个集合的并集中的所有成员。

		func (tx *Tx) SUnionByTwoBuckets(bucket1 string, key1 []byte, bucket2 string, key2 []byte) (list [][]byte, err error)
			* 返回指定两个bucket里面的给定的两个集合的并集中的所有成员。

		// SortedSet 的操作 -----------------
		func (tx *Tx) ZAdd(bucket string, key []byte, score float64, val []byte) error
		func (tx *Tx) ZCard(bucket string) (int, error)
		func (tx *Tx) ZCount(bucket string, start, end float64, opts *zset.GetByScoreRangeOptions) (int, error)
		func (tx *Tx) ZGetByKey(bucket string, key []byte) (*zset.SortedSetNode, error)
		func (tx *Tx) ZMembers(bucket string) (map[string]*zset.SortedSetNode, error)
		func (tx *Tx) ZPeekMax(bucket string) (*zset.SortedSetNode, error)
		func (tx *Tx) ZPeekMin(bucket string) (*zset.SortedSetNode, error)
		func (tx *Tx) ZPopMax(bucket string) (*zset.SortedSetNode, error)
		func (tx *Tx) ZPopMin(bucket string) (*zset.SortedSetNode, error)
		func (tx *Tx) ZRangeByRank(bucket string, start, end int) ([]*zset.SortedSetNode, error)
		func (tx *Tx) ZRangeByScore(bucket string, start, end float64, opts *zset.GetByScoreRangeOptions) ([]*zset.SortedSetNode, error)
		func (tx *Tx) ZRank(bucket string, key []byte) (int, error)
		func (tx *Tx) ZRem(bucket, key string) error
		func (tx *Tx) ZRemRangeByRank(bucket string, start, end int) error
		func (tx *Tx) ZRevRank(bucket string, key []byte) (int, error)
		func (tx *Tx) ZScore(bucket string, key []byte) (float64, error)
		
---------------------
func
---------------------
	func ErrBucketAndKey(bucket string, key []byte) error
	func ErrNotFoundKeyInBucket(bucket string, key []byte) error
	func ErrSeparatorForListKey() error
	func ErrSeparatorForZSetKey() error
	func ErrWhenBuildListIdx(err error) error
	func IsExpired(ttl uint32, timestamp uint64) bool
	func SortFID(BPTreeRootIdxGroup []*BPTreeRootIdx, by sortBy)
	func SortedEntryKeys(m map[string]*Entry) (keys []string, es map[string]*Entry)
	func Truncate(path string, capacity int64, f *os.File) error