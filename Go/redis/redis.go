------------------------
redis
------------------------
	# addr
		https://github.com/go-redis/redis
		https://pkg.go.dev/github.com/go-redis/redis/v8
		github.com/go-redis/redis/v8
	


------------------------
var
------------------------
	const KeepTTL = -1
	const Nil = proto.Nil
	const TxFailedErr = proto.RedisError("redis: transaction failed")
	var ErrClosed = pool.ErrClosed

------------------------
type
------------------------
	# type BitCount struct {
			Start, End int64
		}

	# type BoolCmd struct {
		}
		func NewBoolCmd(ctx context.Context, args ...interface{}) *BoolCmd
		func NewBoolResult(val bool, err error) *BoolCmd
		func (cmd *BoolCmd) Args() []interface{}
		func (cmd *BoolCmd) Err() error
		func (cmd *BoolCmd) FullName() string
		func (cmd *BoolCmd) Name() string
		func (cmd *BoolCmd) Result() (bool, error)
		func (cmd *BoolCmd) SetErr(e error)
		func (cmd *BoolCmd) String() string
		func (cmd *BoolCmd) Val() bool
	
	# func NewBoolSliceCmd(ctx context.Context, args ...interface{}) *BoolSliceCmd
		func NewBoolSliceResult(val []bool, err error) *BoolSliceCmd
		func (cmd *BoolSliceCmd) Args() []interface{}
		func (cmd *BoolSliceCmd) Err() error
		func (cmd *BoolSliceCmd) FullName() string
		func (cmd *BoolSliceCmd) Name() string
		func (cmd *BoolSliceCmd) Result() ([]bool, error)
		func (cmd *BoolSliceCmd) SetErr(e error)
		func (cmd *BoolSliceCmd) String() string
		func (cmd *BoolSliceCmd) Val() []bool
	
	# type Client struct {
		}
		func NewClient(opt *Options) *Client
		func NewFailoverClient(failoverOpt *FailoverOptions) *Client
		func (hs *Client) AddHook(hook Hook)
		func (c Client) Append(ctx context.Context, key, value string) *IntCmd
		func (c Client) BLPop(ctx context.Context, timeout time.Duration, keys ...string) *StringSliceCmd
		func (c Client) BRPop(ctx context.Context, timeout time.Duration, keys ...string) *StringSliceCmd
		func (c Client) BRPopLPush(ctx context.Context, source, destination string, timeout time.Duration) *StringCmd
		func (c Client) BZPopMax(ctx context.Context, timeout time.Duration, keys ...string) *ZWithKeyCmd
		func (c Client) BZPopMin(ctx context.Context, timeout time.Duration, keys ...string) *ZWithKeyCmd
		func (c Client) BgRewriteAOF(ctx context.Context) *StatusCmd
		func (c Client) BgSave(ctx context.Context) *StatusCmd
		func (c Client) BitCount(ctx context.Context, key string, bitCount *BitCount) *IntCmd
		func (c Client) BitField(ctx context.Context, key string, args ...interface{}) *IntSliceCmd
		func (c Client) BitOpAnd(ctx context.Context, destKey string, keys ...string) *IntCmd
		func (c Client) BitOpNot(ctx context.Context, destKey string, key string) *IntCmd
		func (c Client) BitOpOr(ctx context.Context, destKey string, keys ...string) *IntCmd
		func (c Client) BitOpXor(ctx context.Context, destKey string, keys ...string) *IntCmd
		func (c Client) BitPos(ctx context.Context, key string, bit int64, pos ...int64) *IntCmd
		func (c Client) ClientGetName(ctx context.Context) *StringCmd
		func (c Client) ClientID(ctx context.Context) *IntCmd
		func (c Client) ClientKill(ctx context.Context, ipPort string) *StatusCmd
		func (c Client) ClientKillByFilter(ctx context.Context, keys ...string) *IntCmd
		func (c Client) ClientList(ctx context.Context) *StringCmd
		func (c Client) ClientPause(ctx context.Context, dur time.Duration) *BoolCmd
		func (c Client) ClientUnblock(ctx context.Context, id int64) *IntCmd
		func (c Client) ClientUnblockWithError(ctx context.Context, id int64) *IntCmd
		func (c Client) Close() error
		func (c Client) ClusterAddSlots(ctx context.Context, slots ...int) *StatusCmd
		func (c Client) ClusterAddSlotsRange(ctx context.Context, min, max int) *StatusCmd
		func (c Client) ClusterCountFailureReports(ctx context.Context, nodeID string) *IntCmd
		func (c Client) ClusterCountKeysInSlot(ctx context.Context, slot int) *IntCmd
		func (c Client) ClusterDelSlots(ctx context.Context, slots ...int) *StatusCmd
		func (c Client) ClusterDelSlotsRange(ctx context.Context, min, max int) *StatusCmd
		func (c Client) ClusterFailover(ctx context.Context) *StatusCmd
		func (c Client) ClusterForget(ctx context.Context, nodeID string) *StatusCmd
		func (c Client) ClusterGetKeysInSlot(ctx context.Context, slot int, count int) *StringSliceCmd
		func (c Client) ClusterInfo(ctx context.Context) *StringCmd
		func (c Client) ClusterKeySlot(ctx context.Context, key string) *IntCmd
		func (c Client) ClusterMeet(ctx context.Context, host, port string) *StatusCmd
		func (c Client) ClusterNodes(ctx context.Context) *StringCmd
		func (c Client) ClusterReplicate(ctx context.Context, nodeID string) *StatusCmd
		func (c Client) ClusterResetHard(ctx context.Context) *StatusCmd
		func (c Client) ClusterResetSoft(ctx context.Context) *StatusCmd
		func (c Client) ClusterSaveConfig(ctx context.Context) *StatusCmd
		func (c Client) ClusterSlaves(ctx context.Context, nodeID string) *StringSliceCmd
		func (c Client) ClusterSlots(ctx context.Context) *ClusterSlotsCmd
		func (c Client) Command(ctx context.Context) *CommandsInfoCmd
		func (c Client) ConfigGet(ctx context.Context, parameter string) *SliceCmd
		func (c Client) ConfigResetStat(ctx context.Context) *StatusCmd
		func (c Client) ConfigRewrite(ctx context.Context) *StatusCmd
		func (c Client) ConfigSet(ctx context.Context, parameter, value string) *StatusCmd
		func (c *Client) Conn(ctx context.Context) *Conn
		func (c *Client) Context() context.Context
		func (c Client) DBSize(ctx context.Context) *IntCmd
		func (c Client) DebugObject(ctx context.Context, key string) *StringCmd
		func (c Client) Decr(ctx context.Context, key string) *IntCmd
		func (c Client) DecrBy(ctx context.Context, key string, decrement int64) *IntCmd
		func (c Client) Del(ctx context.Context, keys ...string) *IntCmd
		func (c *Client) Do(ctx context.Context, args ...interface{}) *Cmd
		func (c Client) Dump(ctx context.Context, key string) *StringCmd
		func (c Client) Echo(ctx context.Context, message interface{}) *StringCmd
		func (c Client) Eval(ctx context.Context, script string, keys []string, args ...interface{}) *Cmd
		func (c Client) EvalSha(ctx context.Context, sha1 string, keys []string, args ...interface{}) *Cmd
		func (c Client) Exists(ctx context.Context, keys ...string) *IntCmd
		func (c Client) Expire(ctx context.Context, key string, expiration time.Duration) *BoolCmd
		func (c Client) ExpireAt(ctx context.Context, key string, tm time.Time) *BoolCmd
		func (c Client) FlushAll(ctx context.Context) *StatusCmd
		func (c Client) FlushAllAsync(ctx context.Context) *StatusCmd
		func (c Client) FlushDB(ctx context.Context) *StatusCmd
		func (c Client) FlushDBAsync(ctx context.Context) *StatusCmd
		func (c Client) GeoAdd(ctx context.Context, key string, geoLocation ...*GeoLocation) *IntCmd
		func (c Client) GeoDist(ctx context.Context, key string, member1, member2, unit string) *FloatCmd
		func (c Client) GeoHash(ctx context.Context, key string, members ...string) *StringSliceCmd
		func (c Client) GeoPos(ctx context.Context, key string, members ...string) *GeoPosCmd
		func (c Client) GeoRadius(ctx context.Context, key string, longitude, latitude float64, ...) *GeoLocationCmd
		func (c Client) GeoRadiusByMember(ctx context.Context, key, member string, query *GeoRadiusQuery) *GeoLocationCmd
		func (c Client) GeoRadiusByMemberStore(ctx context.Context, key, member string, query *GeoRadiusQuery) *IntCmd
		func (c Client) GeoRadiusStore(ctx context.Context, key string, longitude, latitude float64, ...) *IntCmd
		func (c Client) Get(ctx context.Context, key string) *StringCmd
		func (c Client) GetBit(ctx context.Context, key string, offset int64) *IntCmd
		func (c Client) GetRange(ctx context.Context, key string, start, end int64) *StringCmd
		func (c Client) GetSet(ctx context.Context, key string, value interface{}) *StringCmd
		func (c Client) HDel(ctx context.Context, key string, fields ...string) *IntCmd
		func (c Client) HExists(ctx context.Context, key, field string) *BoolCmd
		func (c Client) HGet(ctx context.Context, key, field string) *StringCmd
		func (c Client) HGetAll(ctx context.Context, key string) *StringStringMapCmd
		func (c Client) HIncrBy(ctx context.Context, key, field string, incr int64) *IntCmd
		func (c Client) HIncrByFloat(ctx context.Context, key, field string, incr float64) *FloatCmd
		func (c Client) HKeys(ctx context.Context, key string) *StringSliceCmd
		func (c Client) HLen(ctx context.Context, key string) *IntCmd
		func (c Client) HMGet(ctx context.Context, key string, fields ...string) *SliceCmd
		func (c Client) HMSet(ctx context.Context, key string, values ...interface{}) *BoolCmd
		func (c Client) HScan(ctx context.Context, key string, cursor uint64, match string, count int64) *ScanCmd
		func (c Client) HSet(ctx context.Context, key string, values ...interface{}) *IntCmd
		func (c Client) HSetNX(ctx context.Context, key, field string, value interface{}) *BoolCmd
		func (c Client) HVals(ctx context.Context, key string) *StringSliceCmd
		func (c Client) Incr(ctx context.Context, key string) *IntCmd
		func (c Client) IncrBy(ctx context.Context, key string, value int64) *IntCmd
		func (c Client) IncrByFloat(ctx context.Context, key string, value float64) *FloatCmd
		func (c Client) Info(ctx context.Context, section ...string) *StringCmd
		func (c Client) Keys(ctx context.Context, pattern string) *StringSliceCmd
		func (c Client) LIndex(ctx context.Context, key string, index int64) *StringCmd
		func (c Client) LInsert(ctx context.Context, key, op string, pivot, value interface{}) *IntCmd
		func (c Client) LInsertAfter(ctx context.Context, key string, pivot, value interface{}) *IntCmd
		func (c Client) LInsertBefore(ctx context.Context, key string, pivot, value interface{}) *IntCmd
		func (c Client) LLen(ctx context.Context, key string) *IntCmd
		func (c Client) LPop(ctx context.Context, key string) *StringCmd
		func (c Client) LPos(ctx context.Context, key string, value string, a LPosArgs) *IntCmd
		func (c Client) LPosCount(ctx context.Context, key string, value string, count int64, a LPosArgs) *IntSliceCmd
		func (c Client) LPush(ctx context.Context, key string, values ...interface{}) *IntCmd
		func (c Client) LPushX(ctx context.Context, key string, values ...interface{}) *IntCmd
		func (c Client) LRange(ctx context.Context, key string, start, stop int64) *StringSliceCmd
		func (c Client) LRem(ctx context.Context, key string, count int64, value interface{}) *IntCmd
		func (c Client) LSet(ctx context.Context, key string, index int64, value interface{}) *StatusCmd
		func (c Client) LTrim(ctx context.Context, key string, start, stop int64) *StatusCmd
		func (c Client) LastSave(ctx context.Context) *IntCmd
		func (c Client) MGet(ctx context.Context, keys ...string) *SliceCmd
		func (c Client) MSet(ctx context.Context, values ...interface{}) *StatusCmd
		func (c Client) MSetNX(ctx context.Context, values ...interface{}) *BoolCmd
		func (c Client) MemoryUsage(ctx context.Context, key string, samples ...int) *IntCmd
		func (c Client) Migrate(ctx context.Context, host, port, key string, db int, timeout time.Duration) *StatusCmd
		func (c Client) Move(ctx context.Context, key string, db int) *BoolCmd
		func (c Client) ObjectEncoding(ctx context.Context, key string) *StringCmd
		func (c Client) ObjectIdleTime(ctx context.Context, key string) *DurationCmd
		func (c Client) ObjectRefCount(ctx context.Context, key string) *IntCmd
		func (c *Client) Options() *Options
		func (c Client) PExpire(ctx context.Context, key string, expiration time.Duration) *BoolCmd
		func (c Client) PExpireAt(ctx context.Context, key string, tm time.Time) *BoolCmd
		func (c Client) PFAdd(ctx context.Context, key string, els ...interface{}) *IntCmd
		func (c Client) PFCount(ctx context.Context, keys ...string) *IntCmd
		func (c Client) PFMerge(ctx context.Context, dest string, keys ...string) *StatusCmd
		func (c *Client) PSubscribe(ctx context.Context, channels ...string) *PubSub
		func (c Client) PTTL(ctx context.Context, key string) *DurationCmd
		func (c Client) Persist(ctx context.Context, key string) *BoolCmd
		func (c Client) Ping(ctx context.Context) *StatusCmd
		func (c *Client) Pipeline() Pipeliner
		func (c *Client) Pipelined(ctx context.Context, fn func(Pipeliner) error) ([]Cmder, error)
		func (c *Client) PoolStats() *PoolStats
		func (c *Client) Process(ctx context.Context, cmd Cmder) error
		func (c Client) PubSubChannels(ctx context.Context, pattern string) *StringSliceCmd
		func (c Client) PubSubNumPat(ctx context.Context) *IntCmd
		func (c Client) PubSubNumSub(ctx context.Context, channels ...string) *StringIntMapCmd
		func (c Client) Publish(ctx context.Context, channel string, message interface{}) *IntCmd
		func (c Client) Quit(ctx context.Context) *StatusCmd
		func (c Client) RPop(ctx context.Context, key string) *StringCmd
		func (c Client) RPopLPush(ctx context.Context, source, destination string) *StringCmd
		func (c Client) RPush(ctx context.Context, key string, values ...interface{}) *IntCmd
		func (c Client) RPushX(ctx context.Context, key string, values ...interface{}) *IntCmd
		func (c Client) RandomKey(ctx context.Context) *StringCmd
		func (c Client) ReadOnly(ctx context.Context) *StatusCmd
		func (c Client) ReadWrite(ctx context.Context) *StatusCmd
		func (c Client) Rename(ctx context.Context, key, newkey string) *StatusCmd
		func (c Client) RenameNX(ctx context.Context, key, newkey string) *BoolCmd
		func (c Client) Restore(ctx context.Context, key string, ttl time.Duration, value string) *StatusCmd
		func (c Client) RestoreReplace(ctx context.Context, key string, ttl time.Duration, value string) *StatusCmd
		func (c Client) SAdd(ctx context.Context, key string, members ...interface{}) *IntCmd
		func (c Client) SCard(ctx context.Context, key string) *IntCmd
		func (c Client) SDiff(ctx context.Context, keys ...string) *StringSliceCmd
		func (c Client) SDiffStore(ctx context.Context, destination string, keys ...string) *IntCmd
		func (c Client) SInter(ctx context.Context, keys ...string) *StringSliceCmd
		func (c Client) SInterStore(ctx context.Context, destination string, keys ...string) *IntCmd
		func (c Client) SIsMember(ctx context.Context, key string, member interface{}) *BoolCmd
		func (c Client) SMembers(ctx context.Context, key string) *StringSliceCmd
		func (c Client) SMembersMap(ctx context.Context, key string) *StringStructMapCmd
		func (c Client) SMove(ctx context.Context, source, destination string, member interface{}) *BoolCmd
		func (c Client) SPop(ctx context.Context, key string) *StringCmd
		func (c Client) SPopN(ctx context.Context, key string, count int64) *StringSliceCmd
		func (c Client) SRandMember(ctx context.Context, key string) *StringCmd
		func (c Client) SRandMemberN(ctx context.Context, key string, count int64) *StringSliceCmd
		func (c Client) SRem(ctx context.Context, key string, members ...interface{}) *IntCmd
		func (c Client) SScan(ctx context.Context, key string, cursor uint64, match string, count int64) *ScanCmd
		func (c Client) SUnion(ctx context.Context, keys ...string) *StringSliceCmd
		func (c Client) SUnionStore(ctx context.Context, destination string, keys ...string) *IntCmd
		func (c Client) Save(ctx context.Context) *StatusCmd
		func (c Client) Scan(ctx context.Context, cursor uint64, match string, count int64) *ScanCmd
		func (c Client) ScanType(ctx context.Context, cursor uint64, match string, count int64, keyType string) *ScanCmd
		func (c Client) ScriptExists(ctx context.Context, hashes ...string) *BoolSliceCmd
		func (c Client) ScriptFlush(ctx context.Context) *StatusCmd
		func (c Client) ScriptKill(ctx context.Context) *StatusCmd
		func (c Client) ScriptLoad(ctx context.Context, script string) *StringCmd
		func (c Client) Set(ctx context.Context, key string, value interface{}, expiration time.Duration) *StatusCmd
		func (c Client) SetBit(ctx context.Context, key string, offset int64, value int) *IntCmd
		func (c Client) SetEX(ctx context.Context, key string, value interface{}, expiration time.Duration) *StatusCmd
		func (c Client) SetNX(ctx context.Context, key string, value interface{}, expiration time.Duration) *BoolCmd
		func (c Client) SetRange(ctx context.Context, key string, offset int64, value string) *IntCmd
		func (c Client) SetXX(ctx context.Context, key string, value interface{}, expiration time.Duration) *BoolCmd
		func (c Client) Shutdown(ctx context.Context) *StatusCmd
		func (c Client) ShutdownNoSave(ctx context.Context) *StatusCmd
		func (c Client) ShutdownSave(ctx context.Context) *StatusCmd
		func (c Client) SlaveOf(ctx context.Context, host, port string) *StatusCmd
		func (c Client) SlowLogGet(ctx context.Context, num int64) *SlowLogCmd
		func (c Client) Sort(ctx context.Context, key string, sort *Sort) *StringSliceCmd
		func (c Client) SortInterfaces(ctx context.Context, key string, sort *Sort) *SliceCmd
		func (c Client) SortStore(ctx context.Context, key, store string, sort *Sort) *IntCmd
		func (c Client) StrLen(ctx context.Context, key string) *IntCmd
		func (c Client) String() string
		func (c *Client) Subscribe(ctx context.Context, channels ...string) *PubSub
		func (c Client) Sync(ctx context.Context)
		func (c Client) TTL(ctx context.Context, key string) *DurationCmd
		func (c Client) Time(ctx context.Context) *TimeCmd
		func (c Client) Touch(ctx context.Context, keys ...string) *IntCmd
		func (c *Client) TxPipeline() Pipeliner
		func (c *Client) TxPipelined(ctx context.Context, fn func(Pipeliner) error) ([]Cmder, error)
		func (c Client) Type(ctx context.Context, key string) *StatusCmd
		func (c Client) Unlink(ctx context.Context, keys ...string) *IntCmd
		func (c Client) Wait(ctx context.Context, numSlaves int, timeout time.Duration) *IntCmd
		func (c *Client) Watch(ctx context.Context, fn func(*Tx) error, keys ...string) error
		func (c *Client) WithContext(ctx context.Context) *Client
		func (c *Client) WithTimeout(timeout time.Duration) *Client
		func (c Client) XAck(ctx context.Context, stream, group string, ids ...string) *IntCmd
		func (c Client) XAdd(ctx context.Context, a *XAddArgs) *StringCmd
		func (c Client) XClaim(ctx context.Context, a *XClaimArgs) *XMessageSliceCmd
		func (c Client) XClaimJustID(ctx context.Context, a *XClaimArgs) *StringSliceCmd
		func (c Client) XDel(ctx context.Context, stream string, ids ...string) *IntCmd
		func (c Client) XGroupCreate(ctx context.Context, stream, group, start string) *StatusCmd
		func (c Client) XGroupCreateMkStream(ctx context.Context, stream, group, start string) *StatusCmd
		func (c Client) XGroupDelConsumer(ctx context.Context, stream, group, consumer string) *IntCmd
		func (c Client) XGroupDestroy(ctx context.Context, stream, group string) *IntCmd
		func (c Client) XGroupSetID(ctx context.Context, stream, group, start string) *StatusCmd
		func (c Client) XInfoGroups(ctx context.Context, key string) *XInfoGroupsCmd
		func (c Client) XInfoStream(ctx context.Context, key string) *XInfoStreamCmd
		func (c Client) XLen(ctx context.Context, stream string) *IntCmd
		func (c Client) XPending(ctx context.Context, stream, group string) *XPendingCmd
		func (c Client) XPendingExt(ctx context.Context, a *XPendingExtArgs) *XPendingExtCmd
		func (c Client) XRange(ctx context.Context, stream, start, stop string) *XMessageSliceCmd
		func (c Client) XRangeN(ctx context.Context, stream, start, stop string, count int64) *XMessageSliceCmd
		func (c Client) XRead(ctx context.Context, a *XReadArgs) *XStreamSliceCmd
		func (c Client) XReadGroup(ctx context.Context, a *XReadGroupArgs) *XStreamSliceCmd
		func (c Client) XReadStreams(ctx context.Context, streams ...string) *XStreamSliceCmd
		func (c Client) XRevRange(ctx context.Context, stream, start, stop string) *XMessageSliceCmd
		func (c Client) XRevRangeN(ctx context.Context, stream, start, stop string, count int64) *XMessageSliceCmd
		func (c Client) XTrim(ctx context.Context, key string, maxLen int64) *IntCmd
		func (c Client) XTrimApprox(ctx context.Context, key string, maxLen int64) *IntCmd
		func (c Client) ZAdd(ctx context.Context, key string, members ...*Z) *IntCmd
		func (c Client) ZAddCh(ctx context.Context, key string, members ...*Z) *IntCmd
		func (c Client) ZAddNX(ctx context.Context, key string, members ...*Z) *IntCmd
		func (c Client) ZAddNXCh(ctx context.Context, key string, members ...*Z) *IntCmd
		func (c Client) ZAddXX(ctx context.Context, key string, members ...*Z) *IntCmd
		func (c Client) ZAddXXCh(ctx context.Context, key string, members ...*Z) *IntCmd
		func (c Client) ZCard(ctx context.Context, key string) *IntCmd
		func (c Client) ZCount(ctx context.Context, key, min, max string) *IntCmd
		func (c Client) ZIncr(ctx context.Context, key string, member *Z) *FloatCmd
		func (c Client) ZIncrBy(ctx context.Context, key string, increment float64, member string) *FloatCmd
		func (c Client) ZIncrNX(ctx context.Context, key string, member *Z) *FloatCmd
		func (c Client) ZIncrXX(ctx context.Context, key string, member *Z) *FloatCmd
		func (c Client) ZInterStore(ctx context.Context, destination string, store *ZStore) *IntCmd
		func (c Client) ZLexCount(ctx context.Context, key, min, max string) *IntCmd
		func (c Client) ZPopMax(ctx context.Context, key string, count ...int64) *ZSliceCmd
		func (c Client) ZPopMin(ctx context.Context, key string, count ...int64) *ZSliceCmd
		func (c Client) ZRange(ctx context.Context, key string, start, stop int64) *StringSliceCmd
		func (c Client) ZRangeByLex(ctx context.Context, key string, opt *ZRangeBy) *StringSliceCmd
		func (c Client) ZRangeByScore(ctx context.Context, key string, opt *ZRangeBy) *StringSliceCmd
		func (c Client) ZRangeByScoreWithScores(ctx context.Context, key string, opt *ZRangeBy) *ZSliceCmd
		func (c Client) ZRangeWithScores(ctx context.Context, key string, start, stop int64) *ZSliceCmd
		func (c Client) ZRank(ctx context.Context, key, member string) *IntCmd
		func (c Client) ZRem(ctx context.Context, key string, members ...interface{}) *IntCmd
		func (c Client) ZRemRangeByLex(ctx context.Context, key, min, max string) *IntCmd
		func (c Client) ZRemRangeByRank(ctx context.Context, key string, start, stop int64) *IntCmd
		func (c Client) ZRemRangeByScore(ctx context.Context, key, min, max string) *IntCmd
		func (c Client) ZRevRange(ctx context.Context, key string, start, stop int64) *StringSliceCmd
		func (c Client) ZRevRangeByLex(ctx context.Context, key string, opt *ZRangeBy) *StringSliceCmd
		func (c Client) ZRevRangeByScore(ctx context.Context, key string, opt *ZRangeBy) *StringSliceCmd
		func (c Client) ZRevRangeByScoreWithScores(ctx context.Context, key string, opt *ZRangeBy) *ZSliceCmd
		func (c Client) ZRevRangeWithScores(ctx context.Context, key string, start, stop int64) *ZSliceCmd
		func (c Client) ZRevRank(ctx context.Context, key, member string) *IntCmd
		func (c Client) ZScan(ctx context.Context, key string, cursor uint64, match string, count int64) *ScanCmd
		func (c Client) ZScore(ctx context.Context, key, member string) *FloatCmd
		func (c Client) ZUnionStore(ctx context.Context, dest string, store *ZStore) *IntCmd
	
	# type ClusterClient struct {
		}
		func NewClusterClient(opt *ClusterOptions) *ClusterClient
		func NewFailoverClusterClient(failoverOpt *FailoverOptions) *ClusterClient
		func (hs *ClusterClient) AddHook(hook Hook)
		func (c ClusterClient) Append(ctx context.Context, key, value string) *IntCmd
		func (c ClusterClient) BLPop(ctx context.Context, timeout time.Duration, keys ...string) *StringSliceCmd
		func (c ClusterClient) BRPop(ctx context.Context, timeout time.Duration, keys ...string) *StringSliceCmd
		func (c ClusterClient) BRPopLPush(ctx context.Context, source, destination string, timeout time.Duration) *StringCmd
		func (c ClusterClient) BZPopMax(ctx context.Context, timeout time.Duration, keys ...string) *ZWithKeyCmd
		func (c ClusterClient) BZPopMin(ctx context.Context, timeout time.Duration, keys ...string) *ZWithKeyCmd
		func (c ClusterClient) BgRewriteAOF(ctx context.Context) *StatusCmd
		func (c ClusterClient) BgSave(ctx context.Context) *StatusCmd
		func (c ClusterClient) BitCount(ctx context.Context, key string, bitCount *BitCount) *IntCmd
		func (c ClusterClient) BitField(ctx context.Context, key string, args ...interface{}) *IntSliceCmd
		func (c ClusterClient) BitOpAnd(ctx context.Context, destKey string, keys ...string) *IntCmd
		func (c ClusterClient) BitOpNot(ctx context.Context, destKey string, key string) *IntCmd
		func (c ClusterClient) BitOpOr(ctx context.Context, destKey string, keys ...string) *IntCmd
		func (c ClusterClient) BitOpXor(ctx context.Context, destKey string, keys ...string) *IntCmd
		func (c ClusterClient) BitPos(ctx context.Context, key string, bit int64, pos ...int64) *IntCmd
		func (c ClusterClient) ClientGetName(ctx context.Context) *StringCmd
		func (c ClusterClient) ClientID(ctx context.Context) *IntCmd
		func (c ClusterClient) ClientKill(ctx context.Context, ipPort string) *StatusCmd
		func (c ClusterClient) ClientKillByFilter(ctx context.Context, keys ...string) *IntCmd
		func (c ClusterClient) ClientList(ctx context.Context) *StringCmd
		func (c ClusterClient) ClientPause(ctx context.Context, dur time.Duration) *BoolCmd
		func (c ClusterClient) ClientUnblock(ctx context.Context, id int64) *IntCmd
		func (c ClusterClient) ClientUnblockWithError(ctx context.Context, id int64) *IntCmd
		func (c *ClusterClient) Close() error
		func (c ClusterClient) ClusterAddSlots(ctx context.Context, slots ...int) *StatusCmd
		func (c ClusterClient) ClusterAddSlotsRange(ctx context.Context, min, max int) *StatusCmd
		func (c ClusterClient) ClusterCountFailureReports(ctx context.Context, nodeID string) *IntCmd
		func (c ClusterClient) ClusterCountKeysInSlot(ctx context.Context, slot int) *IntCmd
		func (c ClusterClient) ClusterDelSlots(ctx context.Context, slots ...int) *StatusCmd
		func (c ClusterClient) ClusterDelSlotsRange(ctx context.Context, min, max int) *StatusCmd
		func (c ClusterClient) ClusterFailover(ctx context.Context) *StatusCmd
		func (c ClusterClient) ClusterForget(ctx context.Context, nodeID string) *StatusCmd
		func (c ClusterClient) ClusterGetKeysInSlot(ctx context.Context, slot int, count int) *StringSliceCmd
		func (c ClusterClient) ClusterInfo(ctx context.Context) *StringCmd
		func (c ClusterClient) ClusterKeySlot(ctx context.Context, key string) *IntCmd
		func (c ClusterClient) ClusterMeet(ctx context.Context, host, port string) *StatusCmd
		func (c ClusterClient) ClusterNodes(ctx context.Context) *StringCmd
		func (c ClusterClient) ClusterReplicate(ctx context.Context, nodeID string) *StatusCmd
		func (c ClusterClient) ClusterResetHard(ctx context.Context) *StatusCmd
		func (c ClusterClient) ClusterResetSoft(ctx context.Context) *StatusCmd
		func (c ClusterClient) ClusterSaveConfig(ctx context.Context) *StatusCmd
		func (c ClusterClient) ClusterSlaves(ctx context.Context, nodeID string) *StringSliceCmd
		func (c ClusterClient) ClusterSlots(ctx context.Context) *ClusterSlotsCmd
		func (c ClusterClient) Command(ctx context.Context) *CommandsInfoCmd
		func (c ClusterClient) ConfigGet(ctx context.Context, parameter string) *SliceCmd
		func (c ClusterClient) ConfigResetStat(ctx context.Context) *StatusCmd
		func (c ClusterClient) ConfigRewrite(ctx context.Context) *StatusCmd
		func (c ClusterClient) ConfigSet(ctx context.Context, parameter, value string) *StatusCmd
		func (c *ClusterClient) Context() context.Context
		func (c *ClusterClient) DBSize(ctx context.Context) *IntCmd
		func (c ClusterClient) DebugObject(ctx context.Context, key string) *StringCmd
		func (c ClusterClient) Decr(ctx context.Context, key string) *IntCmd
		func (c ClusterClient) DecrBy(ctx context.Context, key string, decrement int64) *IntCmd
		func (c ClusterClient) Del(ctx context.Context, keys ...string) *IntCmd
		func (c *ClusterClient) Do(ctx context.Context, args ...interface{}) *Cmd
		func (c ClusterClient) Dump(ctx context.Context, key string) *StringCmd
		func (c ClusterClient) Echo(ctx context.Context, message interface{}) *StringCmd
		func (c ClusterClient) Eval(ctx context.Context, script string, keys []string, args ...interface{}) *Cmd
		func (c ClusterClient) EvalSha(ctx context.Context, sha1 string, keys []string, args ...interface{}) *Cmd
		func (c ClusterClient) Exists(ctx context.Context, keys ...string) *IntCmd
		func (c ClusterClient) Expire(ctx context.Context, key string, expiration time.Duration) *BoolCmd
		func (c ClusterClient) ExpireAt(ctx context.Context, key string, tm time.Time) *BoolCmd
		func (c ClusterClient) FlushAll(ctx context.Context) *StatusCmd
		func (c ClusterClient) FlushAllAsync(ctx context.Context) *StatusCmd
		func (c ClusterClient) FlushDB(ctx context.Context) *StatusCmd
		func (c ClusterClient) FlushDBAsync(ctx context.Context) *StatusCmd
		func (c *ClusterClient) ForEachMaster(ctx context.Context, fn func(ctx context.Context, client *Client) error) error
		func (c *ClusterClient) ForEachShard(ctx context.Context, fn func(ctx context.Context, client *Client) error) error
		func (c *ClusterClient) ForEachSlave(ctx context.Context, fn func(ctx context.Context, client *Client) error) error
		func (c ClusterClient) GeoAdd(ctx context.Context, key string, geoLocation ...*GeoLocation) *IntCmd
		func (c ClusterClient) GeoDist(ctx context.Context, key string, member1, member2, unit string) *FloatCmd
		func (c ClusterClient) GeoHash(ctx context.Context, key string, members ...string) *StringSliceCmd
		func (c ClusterClient) GeoPos(ctx context.Context, key string, members ...string) *GeoPosCmd
		func (c ClusterClient) GeoRadius(ctx context.Context, key string, longitude, latitude float64, ...) *GeoLocationCmd
		func (c ClusterClient) GeoRadiusByMember(ctx context.Context, key, member string, query *GeoRadiusQuery) *GeoLocationCmd
		func (c ClusterClient) GeoRadiusByMemberStore(ctx context.Context, key, member string, query *GeoRadiusQuery) *IntCmd
		func (c ClusterClient) GeoRadiusStore(ctx context.Context, key string, longitude, latitude float64, ...) *IntCmd
		func (c ClusterClient) Get(ctx context.Context, key string) *StringCmd
		func (c ClusterClient) GetBit(ctx context.Context, key string, offset int64) *IntCmd
		func (c ClusterClient) GetRange(ctx context.Context, key string, start, end int64) *StringCmd
		func (c ClusterClient) GetSet(ctx context.Context, key string, value interface{}) *StringCmd
		func (c ClusterClient) HDel(ctx context.Context, key string, fields ...string) *IntCmd
		func (c ClusterClient) HExists(ctx context.Context, key, field string) *BoolCmd
		func (c ClusterClient) HGet(ctx context.Context, key, field string) *StringCmd
		func (c ClusterClient) HGetAll(ctx context.Context, key string) *StringStringMapCmd
		func (c ClusterClient) HIncrBy(ctx context.Context, key, field string, incr int64) *IntCmd
		func (c ClusterClient) HIncrByFloat(ctx context.Context, key, field string, incr float64) *FloatCmd
		func (c ClusterClient) HKeys(ctx context.Context, key string) *StringSliceCmd
		func (c ClusterClient) HLen(ctx context.Context, key string) *IntCmd
		func (c ClusterClient) HMGet(ctx context.Context, key string, fields ...string) *SliceCmd
		func (c ClusterClient) HMSet(ctx context.Context, key string, values ...interface{}) *BoolCmd
		func (c ClusterClient) HScan(ctx context.Context, key string, cursor uint64, match string, count int64) *ScanCmd
		func (c ClusterClient) HSet(ctx context.Context, key string, values ...interface{}) *IntCmd
		func (c ClusterClient) HSetNX(ctx context.Context, key, field string, value interface{}) *BoolCmd
		func (c ClusterClient) HVals(ctx context.Context, key string) *StringSliceCmd
		func (c ClusterClient) Incr(ctx context.Context, key string) *IntCmd
		func (c ClusterClient) IncrBy(ctx context.Context, key string, value int64) *IntCmd
		func (c ClusterClient) IncrByFloat(ctx context.Context, key string, value float64) *FloatCmd
		func (c ClusterClient) Info(ctx context.Context, section ...string) *StringCmd
		func (c ClusterClient) Keys(ctx context.Context, pattern string) *StringSliceCmd
		func (c ClusterClient) LIndex(ctx context.Context, key string, index int64) *StringCmd
		func (c ClusterClient) LInsert(ctx context.Context, key, op string, pivot, value interface{}) *IntCmd
		func (c ClusterClient) LInsertAfter(ctx context.Context, key string, pivot, value interface{}) *IntCmd
		func (c ClusterClient) LInsertBefore(ctx context.Context, key string, pivot, value interface{}) *IntCmd
		func (c ClusterClient) LLen(ctx context.Context, key string) *IntCmd
		func (c ClusterClient) LPop(ctx context.Context, key string) *StringCmd
		func (c ClusterClient) LPos(ctx context.Context, key string, value string, a LPosArgs) *IntCmd
		func (c ClusterClient) LPosCount(ctx context.Context, key string, value string, count int64, a LPosArgs) *IntSliceCmd
		func (c ClusterClient) LPush(ctx context.Context, key string, values ...interface{}) *IntCmd
		func (c ClusterClient) LPushX(ctx context.Context, key string, values ...interface{}) *IntCmd
		func (c ClusterClient) LRange(ctx context.Context, key string, start, stop int64) *StringSliceCmd
		func (c ClusterClient) LRem(ctx context.Context, key string, count int64, value interface{}) *IntCmd
		func (c ClusterClient) LSet(ctx context.Context, key string, index int64, value interface{}) *StatusCmd
		func (c ClusterClient) LTrim(ctx context.Context, key string, start, stop int64) *StatusCmd
		func (c ClusterClient) LastSave(ctx context.Context) *IntCmd
		func (c ClusterClient) MGet(ctx context.Context, keys ...string) *SliceCmd
		func (c ClusterClient) MSet(ctx context.Context, values ...interface{}) *StatusCmd
		func (c ClusterClient) MSetNX(ctx context.Context, values ...interface{}) *BoolCmd
		func (c *ClusterClient) MasterForKey(ctx context.Context, key string) (*Client, error)
		func (c ClusterClient) MemoryUsage(ctx context.Context, key string, samples ...int) *IntCmd
		func (c ClusterClient) Migrate(ctx context.Context, host, port, key string, db int, timeout time.Duration) *StatusCmd
		func (c ClusterClient) Move(ctx context.Context, key string, db int) *BoolCmd
		func (c ClusterClient) ObjectEncoding(ctx context.Context, key string) *StringCmd
		func (c ClusterClient) ObjectIdleTime(ctx context.Context, key string) *DurationCmd
		func (c ClusterClient) ObjectRefCount(ctx context.Context, key string) *IntCmd
		func (c *ClusterClient) Options() *ClusterOptions
		func (c ClusterClient) PExpire(ctx context.Context, key string, expiration time.Duration) *BoolCmd
		func (c ClusterClient) PExpireAt(ctx context.Context, key string, tm time.Time) *BoolCmd
		func (c ClusterClient) PFAdd(ctx context.Context, key string, els ...interface{}) *IntCmd
		func (c ClusterClient) PFCount(ctx context.Context, keys ...string) *IntCmd
		func (c ClusterClient) PFMerge(ctx context.Context, dest string, keys ...string) *StatusCmd
		func (c *ClusterClient) PSubscribe(ctx context.Context, channels ...string) *PubSub
		func (c ClusterClient) PTTL(ctx context.Context, key string) *DurationCmd
		func (c ClusterClient) Persist(ctx context.Context, key string) *BoolCmd
		func (c ClusterClient) Ping(ctx context.Context) *StatusCmd
		func (c *ClusterClient) Pipeline() Pipeliner
		func (c *ClusterClient) Pipelined(ctx context.Context, fn func(Pipeliner) error) ([]Cmder, error)
		func (c *ClusterClient) PoolStats() *PoolStats
		func (c *ClusterClient) Process(ctx context.Context, cmd Cmder) error
		func (c ClusterClient) PubSubChannels(ctx context.Context, pattern string) *StringSliceCmd
		func (c ClusterClient) PubSubNumPat(ctx context.Context) *IntCmd
		func (c ClusterClient) PubSubNumSub(ctx context.Context, channels ...string) *StringIntMapCmd
		func (c ClusterClient) Publish(ctx context.Context, channel string, message interface{}) *IntCmd
		func (c ClusterClient) Quit(ctx context.Context) *StatusCmd
		func (c ClusterClient) RPop(ctx context.Context, key string) *StringCmd
		func (c ClusterClient) RPopLPush(ctx context.Context, source, destination string) *StringCmd
		func (c ClusterClient) RPush(ctx context.Context, key string, values ...interface{}) *IntCmd
		func (c ClusterClient) RPushX(ctx context.Context, key string, values ...interface{}) *IntCmd
		func (c ClusterClient) RandomKey(ctx context.Context) *StringCmd
		func (c ClusterClient) ReadOnly(ctx context.Context) *StatusCmd
		func (c ClusterClient) ReadWrite(ctx context.Context) *StatusCmd
		func (c *ClusterClient) ReloadState(ctx context.Context)
		func (c ClusterClient) Rename(ctx context.Context, key, newkey string) *StatusCmd
		func (c ClusterClient) RenameNX(ctx context.Context, key, newkey string) *BoolCmd
		func (c ClusterClient) Restore(ctx context.Context, key string, ttl time.Duration, value string) *StatusCmd
		func (c ClusterClient) RestoreReplace(ctx context.Context, key string, ttl time.Duration, value string) *StatusCmd
		func (c ClusterClient) SAdd(ctx context.Context, key string, members ...interface{}) *IntCmd
		func (c ClusterClient) SCard(ctx context.Context, key string) *IntCmd
		func (c ClusterClient) SDiff(ctx context.Context, keys ...string) *StringSliceCmd
		func (c ClusterClient) SDiffStore(ctx context.Context, destination string, keys ...string) *IntCmd
		func (c ClusterClient) SInter(ctx context.Context, keys ...string) *StringSliceCmd
		func (c ClusterClient) SInterStore(ctx context.Context, destination string, keys ...string) *IntCmd
		func (c ClusterClient) SIsMember(ctx context.Context, key string, member interface{}) *BoolCmd
		func (c ClusterClient) SMembers(ctx context.Context, key string) *StringSliceCmd
		func (c ClusterClient) SMembersMap(ctx context.Context, key string) *StringStructMapCmd
		func (c ClusterClient) SMove(ctx context.Context, source, destination string, member interface{}) *BoolCmd
		func (c ClusterClient) SPop(ctx context.Context, key string) *StringCmd
		func (c ClusterClient) SPopN(ctx context.Context, key string, count int64) *StringSliceCmd
		func (c ClusterClient) SRandMember(ctx context.Context, key string) *StringCmd
		func (c ClusterClient) SRandMemberN(ctx context.Context, key string, count int64) *StringSliceCmd
		func (c ClusterClient) SRem(ctx context.Context, key string, members ...interface{}) *IntCmd
		func (c ClusterClient) SScan(ctx context.Context, key string, cursor uint64, match string, count int64) *ScanCmd
		func (c ClusterClient) SUnion(ctx context.Context, keys ...string) *StringSliceCmd
		func (c ClusterClient) SUnionStore(ctx context.Context, destination string, keys ...string) *IntCmd
		func (c ClusterClient) Save(ctx context.Context) *StatusCmd
		func (c ClusterClient) Scan(ctx context.Context, cursor uint64, match string, count int64) *ScanCmd
		func (c ClusterClient) ScanType(ctx context.Context, cursor uint64, match string, count int64, keyType string) *ScanCmd
		func (c ClusterClient) ScriptExists(ctx context.Context, hashes ...string) *BoolSliceCmd
		func (c ClusterClient) ScriptFlush(ctx context.Context) *StatusCmd
		func (c ClusterClient) ScriptKill(ctx context.Context) *StatusCmd
		func (c ClusterClient) ScriptLoad(ctx context.Context, script string) *StringCmd
		func (c ClusterClient) Set(ctx context.Context, key string, value interface{}, expiration time.Duration) *StatusCmd
		func (c ClusterClient) SetBit(ctx context.Context, key string, offset int64, value int) *IntCmd
		func (c ClusterClient) SetEX(ctx context.Context, key string, value interface{}, expiration time.Duration) *StatusCmd
		func (c ClusterClient) SetNX(ctx context.Context, key string, value interface{}, expiration time.Duration) *BoolCmd
		func (c ClusterClient) SetRange(ctx context.Context, key string, offset int64, value string) *IntCmd
		func (c ClusterClient) SetXX(ctx context.Context, key string, value interface{}, expiration time.Duration) *BoolCmd
		func (c ClusterClient) Shutdown(ctx context.Context) *StatusCmd
		func (c ClusterClient) ShutdownNoSave(ctx context.Context) *StatusCmd
		func (c ClusterClient) ShutdownSave(ctx context.Context) *StatusCmd
		func (c *ClusterClient) SlaveForKey(ctx context.Context, key string) (*Client, error)
		func (c ClusterClient) SlaveOf(ctx context.Context, host, port string) *StatusCmd
		func (c ClusterClient) SlowLogGet(ctx context.Context, num int64) *SlowLogCmd
		func (c ClusterClient) Sort(ctx context.Context, key string, sort *Sort) *StringSliceCmd
		func (c ClusterClient) SortInterfaces(ctx context.Context, key string, sort *Sort) *SliceCmd
		func (c ClusterClient) SortStore(ctx context.Context, key, store string, sort *Sort) *IntCmd
		func (c ClusterClient) StrLen(ctx context.Context, key string) *IntCmd
		func (c *ClusterClient) Subscribe(ctx context.Context, channels ...string) *PubSub
		func (c ClusterClient) Sync(ctx context.Context)
		func (c ClusterClient) TTL(ctx context.Context, key string) *DurationCmd
		func (c ClusterClient) Time(ctx context.Context) *TimeCmd
		func (c ClusterClient) Touch(ctx context.Context, keys ...string) *IntCmd
		func (c *ClusterClient) TxPipeline() Pipeliner
		func (c *ClusterClient) TxPipelined(ctx context.Context, fn func(Pipeliner) error) ([]Cmder, error)
		func (c ClusterClient) Type(ctx context.Context, key string) *StatusCmd
		func (c ClusterClient) Unlink(ctx context.Context, keys ...string) *IntCmd
		func (c ClusterClient) Wait(ctx context.Context, numSlaves int, timeout time.Duration) *IntCmd
		func (c *ClusterClient) Watch(ctx context.Context, fn func(*Tx) error, keys ...string) error
		func (c *ClusterClient) WithContext(ctx context.Context) *ClusterClient
		func (c ClusterClient) XAck(ctx context.Context, stream, group string, ids ...string) *IntCmd
		func (c ClusterClient) XAdd(ctx context.Context, a *XAddArgs) *StringCmd
		func (c ClusterClient) XClaim(ctx context.Context, a *XClaimArgs) *XMessageSliceCmd
		func (c ClusterClient) XClaimJustID(ctx context.Context, a *XClaimArgs) *StringSliceCmd
		func (c ClusterClient) XDel(ctx context.Context, stream string, ids ...string) *IntCmd
		func (c ClusterClient) XGroupCreate(ctx context.Context, stream, group, start string) *StatusCmd
		func (c ClusterClient) XGroupCreateMkStream(ctx context.Context, stream, group, start string) *StatusCmd
		func (c ClusterClient) XGroupDelConsumer(ctx context.Context, stream, group, consumer string) *IntCmd
		func (c ClusterClient) XGroupDestroy(ctx context.Context, stream, group string) *IntCmd
		func (c ClusterClient) XGroupSetID(ctx context.Context, stream, group, start string) *StatusCmd
		func (c ClusterClient) XInfoGroups(ctx context.Context, key string) *XInfoGroupsCmd
		func (c ClusterClient) XInfoStream(ctx context.Context, key string) *XInfoStreamCmd
		func (c ClusterClient) XLen(ctx context.Context, stream string) *IntCmd
		func (c ClusterClient) XPending(ctx context.Context, stream, group string) *XPendingCmd
		func (c ClusterClient) XPendingExt(ctx context.Context, a *XPendingExtArgs) *XPendingExtCmd
		func (c ClusterClient) XRange(ctx context.Context, stream, start, stop string) *XMessageSliceCmd
		func (c ClusterClient) XRangeN(ctx context.Context, stream, start, stop string, count int64) *XMessageSliceCmd
		func (c ClusterClient) XRead(ctx context.Context, a *XReadArgs) *XStreamSliceCmd
		func (c ClusterClient) XReadGroup(ctx context.Context, a *XReadGroupArgs) *XStreamSliceCmd
		func (c ClusterClient) XReadStreams(ctx context.Context, streams ...string) *XStreamSliceCmd
		func (c ClusterClient) XRevRange(ctx context.Context, stream, start, stop string) *XMessageSliceCmd
		func (c ClusterClient) XRevRangeN(ctx context.Context, stream, start, stop string, count int64) *XMessageSliceCmd
		func (c ClusterClient) XTrim(ctx context.Context, key string, maxLen int64) *IntCmd
		func (c ClusterClient) XTrimApprox(ctx context.Context, key string, maxLen int64) *IntCmd
		func (c ClusterClient) ZAdd(ctx context.Context, key string, members ...*Z) *IntCmd
		func (c ClusterClient) ZAddCh(ctx context.Context, key string, members ...*Z) *IntCmd
		func (c ClusterClient) ZAddNX(ctx context.Context, key string, members ...*Z) *IntCmd
		func (c ClusterClient) ZAddNXCh(ctx context.Context, key string, members ...*Z) *IntCmd
		func (c ClusterClient) ZAddXX(ctx context.Context, key string, members ...*Z) *IntCmd
		func (c ClusterClient) ZAddXXCh(ctx context.Context, key string, members ...*Z) *IntCmd
		func (c ClusterClient) ZCard(ctx context.Context, key string) *IntCmd
		func (c ClusterClient) ZCount(ctx context.Context, key, min, max string) *IntCmd
		func (c ClusterClient) ZIncr(ctx context.Context, key string, member *Z) *FloatCmd
		func (c ClusterClient) ZIncrBy(ctx context.Context, key string, increment float64, member string) *FloatCmd
		func (c ClusterClient) ZIncrNX(ctx context.Context, key string, member *Z) *FloatCmd
		func (c ClusterClient) ZIncrXX(ctx context.Context, key string, member *Z) *FloatCmd
		func (c ClusterClient) ZInterStore(ctx context.Context, destination string, store *ZStore) *IntCmd
		func (c ClusterClient) ZLexCount(ctx context.Context, key, min, max string) *IntCmd
		func (c ClusterClient) ZPopMax(ctx context.Context, key string, count ...int64) *ZSliceCmd
		func (c ClusterClient) ZPopMin(ctx context.Context, key string, count ...int64) *ZSliceCmd
		func (c ClusterClient) ZRange(ctx context.Context, key string, start, stop int64) *StringSliceCmd
		func (c ClusterClient) ZRangeByLex(ctx context.Context, key string, opt *ZRangeBy) *StringSliceCmd
		func (c ClusterClient) ZRangeByScore(ctx context.Context, key string, opt *ZRangeBy) *StringSliceCmd
		func (c ClusterClient) ZRangeByScoreWithScores(ctx context.Context, key string, opt *ZRangeBy) *ZSliceCmd
		func (c ClusterClient) ZRangeWithScores(ctx context.Context, key string, start, stop int64) *ZSliceCmd
		func (c ClusterClient) ZRank(ctx context.Context, key, member string) *IntCmd
		func (c ClusterClient) ZRem(ctx context.Context, key string, members ...interface{}) *IntCmd
		func (c ClusterClient) ZRemRangeByLex(ctx context.Context, key, min, max string) *IntCmd
		func (c ClusterClient) ZRemRangeByRank(ctx context.Context, key string, start, stop int64) *IntCmd
		func (c ClusterClient) ZRemRangeByScore(ctx context.Context, key, min, max string) *IntCmd
		func (c ClusterClient) ZRevRange(ctx context.Context, key string, start, stop int64) *StringSliceCmd
		func (c ClusterClient) ZRevRangeByLex(ctx context.Context, key string, opt *ZRangeBy) *StringSliceCmd
		func (c ClusterClient) ZRevRangeByScore(ctx context.Context, key string, opt *ZRangeBy) *StringSliceCmd
		func (c ClusterClient) ZRevRangeByScoreWithScores(ctx context.Context, key string, opt *ZRangeBy) *ZSliceCmd
		func (c ClusterClient) ZRevRangeWithScores(ctx context.Context, key string, start, stop int64) *ZSliceCmd
		func (c ClusterClient) ZRevRank(ctx context.Context, key, member string) *IntCmd
		func (c ClusterClient) ZScan(ctx context.Context, key string, cursor uint64, match string, count int64) *ScanCmd
		func (c ClusterClient) ZScore(ctx context.Context, key, member string) *FloatCmd
		func (c ClusterClient) ZUnionStore(ctx context.Context, dest string, store *ZStore) *IntCmd
	
	# type ClusterNode struct {
			ID   string
			Addr string
		}
	# type ClusterOptions struct {
			Addrs []string
			NewClient func(opt *Options) *Client
			MaxRedirects int
			ReadOnly bool
			RouteByLatency bool
			RouteRandomly bool
			ClusterSlots func(context.Context) ([]ClusterSlot, error)
			Dialer func(ctx context.Context, network, addr string) (net.Conn, error)
			OnConnect func(ctx context.Context, cn *Conn) error
			Username string
			Password string
			MaxRetries      int
			MinRetryBackoff time.Duration
			MaxRetryBackoff time.Duration
			DialTimeout  time.Duration
			ReadTimeout  time.Duration
			WriteTimeout time.Duration
			PoolSize           int
			MinIdleConns       int
			MaxConnAge         time.Duration
			PoolTimeout        time.Duration
			IdleTimeout        time.Duration
			IdleCheckFrequency time.Duration
			TLSConfig *tls.Config
		}

	# type ClusterSlot struct {
			Start int
			End   int
			Nodes []ClusterNode
		}

	# type ClusterSlotsCmd struct {
		}
		func NewClusterSlotsCmd(ctx context.Context, args ...interface{}) *ClusterSlotsCmd
		func NewClusterSlotsCmdResult(val []ClusterSlot, err error) *ClusterSlotsCmd
		func (cmd *ClusterSlotsCmd) Args() []interface{}
		func (cmd *ClusterSlotsCmd) Err() error
		func (cmd *ClusterSlotsCmd) FullName() string
		func (cmd *ClusterSlotsCmd) Name() string
		func (cmd *ClusterSlotsCmd) Result() ([]ClusterSlot, error)
		func (cmd *ClusterSlotsCmd) SetErr(e error)
		func (cmd *ClusterSlotsCmd) String() string
		func (cmd *ClusterSlotsCmd) Val() []ClusterSlot
	
	# type Cmd struct {
		}
		func NewCmd(ctx context.Context, args ...interface{}) *Cmd
		func NewCmdResult(val interface{}, err error) *Cmd
		func (cmd *Cmd) Args() []interface{}
		func (cmd *Cmd) Bool() (bool, error)
		func (cmd *Cmd) Err() error
		func (cmd *Cmd) Float32() (float32, error)
		func (cmd *Cmd) Float64() (float64, error)
		func (cmd *Cmd) FullName() string
		func (cmd *Cmd) Int() (int, error)
		func (cmd *Cmd) Int64() (int64, error)
		func (cmd *Cmd) Name() string
		func (cmd *Cmd) Result() (interface{}, error)
		func (cmd *Cmd) SetErr(e error)
		func (cmd *Cmd) String() string
		func (cmd *Cmd) Text() (string, error)
		func (cmd *Cmd) Uint64() (uint64, error)
		func (cmd *Cmd) Val() interface{}

	# type Cmdable interface {
			Pipeline() Pipeliner
			Pipelined(ctx context.Context, fn func(Pipeliner) error) ([]Cmder, error)

			TxPipelined(ctx context.Context, fn func(Pipeliner) error) ([]Cmder, error)
			TxPipeline() Pipeliner

			Command(ctx context.Context) *CommandsInfoCmd
			ClientGetName(ctx context.Context) *StringCmd
			Echo(ctx context.Context, message interface{}) *StringCmd
			Ping(ctx context.Context) *StatusCmd
			Quit(ctx context.Context) *StatusCmd
			Del(ctx context.Context, keys ...string) *IntCmd
			Unlink(ctx context.Context, keys ...string) *IntCmd
			Dump(ctx context.Context, key string) *StringCmd
			Exists(ctx context.Context, keys ...string) *IntCmd
			Expire(ctx context.Context, key string, expiration time.Duration) *BoolCmd
			ExpireAt(ctx context.Context, key string, tm time.Time) *BoolCmd
			Keys(ctx context.Context, pattern string) *StringSliceCmd
			Migrate(ctx context.Context, host, port, key string, db int, timeout time.Duration) *StatusCmd
			Move(ctx context.Context, key string, db int) *BoolCmd
			ObjectRefCount(ctx context.Context, key string) *IntCmd
			ObjectEncoding(ctx context.Context, key string) *StringCmd
			ObjectIdleTime(ctx context.Context, key string) *DurationCmd
			Persist(ctx context.Context, key string) *BoolCmd
			PExpire(ctx context.Context, key string, expiration time.Duration) *BoolCmd
			PExpireAt(ctx context.Context, key string, tm time.Time) *BoolCmd
			PTTL(ctx context.Context, key string) *DurationCmd
			RandomKey(ctx context.Context) *StringCmd
			Rename(ctx context.Context, key, newkey string) *StatusCmd
			RenameNX(ctx context.Context, key, newkey string) *BoolCmd
			Restore(ctx context.Context, key string, ttl time.Duration, value string) *StatusCmd
			RestoreReplace(ctx context.Context, key string, ttl time.Duration, value string) *StatusCmd
			Sort(ctx context.Context, key string, sort *Sort) *StringSliceCmd
			SortStore(ctx context.Context, key, store string, sort *Sort) *IntCmd
			SortInterfaces(ctx context.Context, key string, sort *Sort) *SliceCmd
			Touch(ctx context.Context, keys ...string) *IntCmd
			TTL(ctx context.Context, key string) *DurationCmd
			Type(ctx context.Context, key string) *StatusCmd
			Append(ctx context.Context, key, value string) *IntCmd
			Decr(ctx context.Context, key string) *IntCmd
			DecrBy(ctx context.Context, key string, decrement int64) *IntCmd
			Get(ctx context.Context, key string) *StringCmd
			GetRange(ctx context.Context, key string, start, end int64) *StringCmd
			GetSet(ctx context.Context, key string, value interface{}) *StringCmd
			Incr(ctx context.Context, key string) *IntCmd
			IncrBy(ctx context.Context, key string, value int64) *IntCmd
			IncrByFloat(ctx context.Context, key string, value float64) *FloatCmd
			MGet(ctx context.Context, keys ...string) *SliceCmd
			MSet(ctx context.Context, values ...interface{}) *StatusCmd
			MSetNX(ctx context.Context, values ...interface{}) *BoolCmd
			Set(ctx context.Context, key string, value interface{}, expiration time.Duration) *StatusCmd
			SetEX(ctx context.Context, key string, value interface{}, expiration time.Duration) *StatusCmd
			SetNX(ctx context.Context, key string, value interface{}, expiration time.Duration) *BoolCmd
			SetXX(ctx context.Context, key string, value interface{}, expiration time.Duration) *BoolCmd
			SetRange(ctx context.Context, key string, offset int64, value string) *IntCmd
			StrLen(ctx context.Context, key string) *IntCmd

			GetBit(ctx context.Context, key string, offset int64) *IntCmd
			SetBit(ctx context.Context, key string, offset int64, value int) *IntCmd
			BitCount(ctx context.Context, key string, bitCount *BitCount) *IntCmd
			BitOpAnd(ctx context.Context, destKey string, keys ...string) *IntCmd
			BitOpOr(ctx context.Context, destKey string, keys ...string) *IntCmd
			BitOpXor(ctx context.Context, destKey string, keys ...string) *IntCmd
			BitOpNot(ctx context.Context, destKey string, key string) *IntCmd
			BitPos(ctx context.Context, key string, bit int64, pos ...int64) *IntCmd
			BitField(ctx context.Context, key string, args ...interface{}) *IntSliceCmd

			Scan(ctx context.Context, cursor uint64, match string, count int64) *ScanCmd
			ScanType(ctx context.Context, cursor uint64, match string, count int64, keyType string) *ScanCmd
			SScan(ctx context.Context, key string, cursor uint64, match string, count int64) *ScanCmd
			HScan(ctx context.Context, key string, cursor uint64, match string, count int64) *ScanCmd
			ZScan(ctx context.Context, key string, cursor uint64, match string, count int64) *ScanCmd

			HDel(ctx context.Context, key string, fields ...string) *IntCmd
			HExists(ctx context.Context, key, field string) *BoolCmd
			HGet(ctx context.Context, key, field string) *StringCmd
			HGetAll(ctx context.Context, key string) *StringStringMapCmd
			HIncrBy(ctx context.Context, key, field string, incr int64) *IntCmd
			HIncrByFloat(ctx context.Context, key, field string, incr float64) *FloatCmd
			HKeys(ctx context.Context, key string) *StringSliceCmd
			HLen(ctx context.Context, key string) *IntCmd
			HMGet(ctx context.Context, key string, fields ...string) *SliceCmd
			HSet(ctx context.Context, key string, values ...interface{}) *IntCmd
			HMSet(ctx context.Context, key string, values ...interface{}) *BoolCmd
			HSetNX(ctx context.Context, key, field string, value interface{}) *BoolCmd
			HVals(ctx context.Context, key string) *StringSliceCmd

			BLPop(ctx context.Context, timeout time.Duration, keys ...string) *StringSliceCmd
			BRPop(ctx context.Context, timeout time.Duration, keys ...string) *StringSliceCmd
			BRPopLPush(ctx context.Context, source, destination string, timeout time.Duration) *StringCmd
			LIndex(ctx context.Context, key string, index int64) *StringCmd
			LInsert(ctx context.Context, key, op string, pivot, value interface{}) *IntCmd
			LInsertBefore(ctx context.Context, key string, pivot, value interface{}) *IntCmd
			LInsertAfter(ctx context.Context, key string, pivot, value interface{}) *IntCmd
			LLen(ctx context.Context, key string) *IntCmd
			LPop(ctx context.Context, key string) *StringCmd
			LPos(ctx context.Context, key string, value string, args LPosArgs) *IntCmd
			LPosCount(ctx context.Context, key string, value string, count int64, args LPosArgs) *IntSliceCmd
			LPush(ctx context.Context, key string, values ...interface{}) *IntCmd
			LPushX(ctx context.Context, key string, values ...interface{}) *IntCmd
			LRange(ctx context.Context, key string, start, stop int64) *StringSliceCmd
			LRem(ctx context.Context, key string, count int64, value interface{}) *IntCmd
			LSet(ctx context.Context, key string, index int64, value interface{}) *StatusCmd
			LTrim(ctx context.Context, key string, start, stop int64) *StatusCmd
			RPop(ctx context.Context, key string) *StringCmd
			RPopLPush(ctx context.Context, source, destination string) *StringCmd
			RPush(ctx context.Context, key string, values ...interface{}) *IntCmd
			RPushX(ctx context.Context, key string, values ...interface{}) *IntCmd

			SAdd(ctx context.Context, key string, members ...interface{}) *IntCmd
			SCard(ctx context.Context, key string) *IntCmd
			SDiff(ctx context.Context, keys ...string) *StringSliceCmd
			SDiffStore(ctx context.Context, destination string, keys ...string) *IntCmd
			SInter(ctx context.Context, keys ...string) *StringSliceCmd
			SInterStore(ctx context.Context, destination string, keys ...string) *IntCmd
			SIsMember(ctx context.Context, key string, member interface{}) *BoolCmd
			SMembers(ctx context.Context, key string) *StringSliceCmd
			SMembersMap(ctx context.Context, key string) *StringStructMapCmd
			SMove(ctx context.Context, source, destination string, member interface{}) *BoolCmd
			SPop(ctx context.Context, key string) *StringCmd
			SPopN(ctx context.Context, key string, count int64) *StringSliceCmd
			SRandMember(ctx context.Context, key string) *StringCmd
			SRandMemberN(ctx context.Context, key string, count int64) *StringSliceCmd
			SRem(ctx context.Context, key string, members ...interface{}) *IntCmd
			SUnion(ctx context.Context, keys ...string) *StringSliceCmd
			SUnionStore(ctx context.Context, destination string, keys ...string) *IntCmd

			XAdd(ctx context.Context, a *XAddArgs) *StringCmd
			XDel(ctx context.Context, stream string, ids ...string) *IntCmd
			XLen(ctx context.Context, stream string) *IntCmd
			XRange(ctx context.Context, stream, start, stop string) *XMessageSliceCmd
			XRangeN(ctx context.Context, stream, start, stop string, count int64) *XMessageSliceCmd
			XRevRange(ctx context.Context, stream string, start, stop string) *XMessageSliceCmd
			XRevRangeN(ctx context.Context, stream string, start, stop string, count int64) *XMessageSliceCmd
			XRead(ctx context.Context, a *XReadArgs) *XStreamSliceCmd
			XReadStreams(ctx context.Context, streams ...string) *XStreamSliceCmd
			XGroupCreate(ctx context.Context, stream, group, start string) *StatusCmd
			XGroupCreateMkStream(ctx context.Context, stream, group, start string) *StatusCmd
			XGroupSetID(ctx context.Context, stream, group, start string) *StatusCmd
			XGroupDestroy(ctx context.Context, stream, group string) *IntCmd
			XGroupDelConsumer(ctx context.Context, stream, group, consumer string) *IntCmd
			XReadGroup(ctx context.Context, a *XReadGroupArgs) *XStreamSliceCmd
			XAck(ctx context.Context, stream, group string, ids ...string) *IntCmd
			XPending(ctx context.Context, stream, group string) *XPendingCmd
			XPendingExt(ctx context.Context, a *XPendingExtArgs) *XPendingExtCmd
			XClaim(ctx context.Context, a *XClaimArgs) *XMessageSliceCmd
			XClaimJustID(ctx context.Context, a *XClaimArgs) *StringSliceCmd
			XTrim(ctx context.Context, key string, maxLen int64) *IntCmd
			XTrimApprox(ctx context.Context, key string, maxLen int64) *IntCmd
			XInfoGroups(ctx context.Context, key string) *XInfoGroupsCmd
			XInfoStream(ctx context.Context, key string) *XInfoStreamCmd

			BZPopMax(ctx context.Context, timeout time.Duration, keys ...string) *ZWithKeyCmd
			BZPopMin(ctx context.Context, timeout time.Duration, keys ...string) *ZWithKeyCmd
			ZAdd(ctx context.Context, key string, members ...*Z) *IntCmd
			ZAddNX(ctx context.Context, key string, members ...*Z) *IntCmd
			ZAddXX(ctx context.Context, key string, members ...*Z) *IntCmd
			ZAddCh(ctx context.Context, key string, members ...*Z) *IntCmd
			ZAddNXCh(ctx context.Context, key string, members ...*Z) *IntCmd
			ZAddXXCh(ctx context.Context, key string, members ...*Z) *IntCmd
			ZIncr(ctx context.Context, key string, member *Z) *FloatCmd
			ZIncrNX(ctx context.Context, key string, member *Z) *FloatCmd
			ZIncrXX(ctx context.Context, key string, member *Z) *FloatCmd
			ZCard(ctx context.Context, key string) *IntCmd
			ZCount(ctx context.Context, key, min, max string) *IntCmd
			ZLexCount(ctx context.Context, key, min, max string) *IntCmd
			ZIncrBy(ctx context.Context, key string, increment float64, member string) *FloatCmd
			ZInterStore(ctx context.Context, destination string, store *ZStore) *IntCmd
			ZPopMax(ctx context.Context, key string, count ...int64) *ZSliceCmd
			ZPopMin(ctx context.Context, key string, count ...int64) *ZSliceCmd
			ZRange(ctx context.Context, key string, start, stop int64) *StringSliceCmd
			ZRangeWithScores(ctx context.Context, key string, start, stop int64) *ZSliceCmd
			ZRangeByScore(ctx context.Context, key string, opt *ZRangeBy) *StringSliceCmd
			ZRangeByLex(ctx context.Context, key string, opt *ZRangeBy) *StringSliceCmd
			ZRangeByScoreWithScores(ctx context.Context, key string, opt *ZRangeBy) *ZSliceCmd
			ZRank(ctx context.Context, key, member string) *IntCmd
			ZRem(ctx context.Context, key string, members ...interface{}) *IntCmd
			ZRemRangeByRank(ctx context.Context, key string, start, stop int64) *IntCmd
			ZRemRangeByScore(ctx context.Context, key, min, max string) *IntCmd
			ZRemRangeByLex(ctx context.Context, key, min, max string) *IntCmd
			ZRevRange(ctx context.Context, key string, start, stop int64) *StringSliceCmd
			ZRevRangeWithScores(ctx context.Context, key string, start, stop int64) *ZSliceCmd
			ZRevRangeByScore(ctx context.Context, key string, opt *ZRangeBy) *StringSliceCmd
			ZRevRangeByLex(ctx context.Context, key string, opt *ZRangeBy) *StringSliceCmd
			ZRevRangeByScoreWithScores(ctx context.Context, key string, opt *ZRangeBy) *ZSliceCmd
			ZRevRank(ctx context.Context, key, member string) *IntCmd
			ZScore(ctx context.Context, key, member string) *FloatCmd
			ZUnionStore(ctx context.Context, dest string, store *ZStore) *IntCmd

			PFAdd(ctx context.Context, key string, els ...interface{}) *IntCmd
			PFCount(ctx context.Context, keys ...string) *IntCmd
			PFMerge(ctx context.Context, dest string, keys ...string) *StatusCmd

			BgRewriteAOF(ctx context.Context) *StatusCmd
			BgSave(ctx context.Context) *StatusCmd
			ClientKill(ctx context.Context, ipPort string) *StatusCmd
			ClientKillByFilter(ctx context.Context, keys ...string) *IntCmd
			ClientList(ctx context.Context) *StringCmd
			ClientPause(ctx context.Context, dur time.Duration) *BoolCmd
			ClientID(ctx context.Context) *IntCmd
			ConfigGet(ctx context.Context, parameter string) *SliceCmd
			ConfigResetStat(ctx context.Context) *StatusCmd
			ConfigSet(ctx context.Context, parameter, value string) *StatusCmd
			ConfigRewrite(ctx context.Context) *StatusCmd
			DBSize(ctx context.Context) *IntCmd
			FlushAll(ctx context.Context) *StatusCmd
			FlushAllAsync(ctx context.Context) *StatusCmd
			FlushDB(ctx context.Context) *StatusCmd
			FlushDBAsync(ctx context.Context) *StatusCmd
			Info(ctx context.Context, section ...string) *StringCmd
			LastSave(ctx context.Context) *IntCmd
			Save(ctx context.Context) *StatusCmd
			Shutdown(ctx context.Context) *StatusCmd
			ShutdownSave(ctx context.Context) *StatusCmd
			ShutdownNoSave(ctx context.Context) *StatusCmd
			SlaveOf(ctx context.Context, host, port string) *StatusCmd
			Time(ctx context.Context) *TimeCmd
			DebugObject(ctx context.Context, key string) *StringCmd
			ReadOnly(ctx context.Context) *StatusCmd
			ReadWrite(ctx context.Context) *StatusCmd
			MemoryUsage(ctx context.Context, key string, samples ...int) *IntCmd

			Eval(ctx context.Context, script string, keys []string, args ...interface{}) *Cmd
			EvalSha(ctx context.Context, sha1 string, keys []string, args ...interface{}) *Cmd
			ScriptExists(ctx context.Context, hashes ...string) *BoolSliceCmd
			ScriptFlush(ctx context.Context) *StatusCmd
			ScriptKill(ctx context.Context) *StatusCmd
			ScriptLoad(ctx context.Context, script string) *StringCmd

			Publish(ctx context.Context, channel string, message interface{}) *IntCmd
			PubSubChannels(ctx context.Context, pattern string) *StringSliceCmd
			PubSubNumSub(ctx context.Context, channels ...string) *StringIntMapCmd
			PubSubNumPat(ctx context.Context) *IntCmd

			ClusterSlots(ctx context.Context) *ClusterSlotsCmd
			ClusterNodes(ctx context.Context) *StringCmd
			ClusterMeet(ctx context.Context, host, port string) *StatusCmd
			ClusterForget(ctx context.Context, nodeID string) *StatusCmd
			ClusterReplicate(ctx context.Context, nodeID string) *StatusCmd
			ClusterResetSoft(ctx context.Context) *StatusCmd
			ClusterResetHard(ctx context.Context) *StatusCmd
			ClusterInfo(ctx context.Context) *StringCmd
			ClusterKeySlot(ctx context.Context, key string) *IntCmd
			ClusterGetKeysInSlot(ctx context.Context, slot int, count int) *StringSliceCmd
			ClusterCountFailureReports(ctx context.Context, nodeID string) *IntCmd
			ClusterCountKeysInSlot(ctx context.Context, slot int) *IntCmd
			ClusterDelSlots(ctx context.Context, slots ...int) *StatusCmd
			ClusterDelSlotsRange(ctx context.Context, min, max int) *StatusCmd
			ClusterSaveConfig(ctx context.Context) *StatusCmd
			ClusterSlaves(ctx context.Context, nodeID string) *StringSliceCmd
			ClusterFailover(ctx context.Context) *StatusCmd
			ClusterAddSlots(ctx context.Context, slots ...int) *StatusCmd
			ClusterAddSlotsRange(ctx context.Context, min, max int) *StatusCmd

			GeoAdd(ctx context.Context, key string, geoLocation ...*GeoLocation) *IntCmd
			GeoPos(ctx context.Context, key string, members ...string) *GeoPosCmd
			GeoRadius(ctx context.Context, key string, longitude, latitude float64, query *GeoRadiusQuery) *GeoLocationCmd
			GeoRadiusStore(ctx context.Context, key string, longitude, latitude float64, query *GeoRadiusQuery) *IntCmd
			GeoRadiusByMember(ctx context.Context, key, member string, query *GeoRadiusQuery) *GeoLocationCmd
			GeoRadiusByMemberStore(ctx context.Context, key, member string, query *GeoRadiusQuery) *IntCmd
			GeoDist(ctx context.Context, key string, member1, member2, unit string) *FloatCmd
			GeoHash(ctx context.Context, key string, members ...string) *StringSliceCmd
		}
	
------------------------
func
------------------------