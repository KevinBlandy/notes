--------------------
snowflake ID
--------------------
	# go实现的雪花ID

		github.com/bwmarrin/snowflake

--------------------
snowflake
--------------------
	import (
		"github.com/bwmarrin/snowflake"
		"github.com/google/uuid"
		"log"
		"strings"
		"time"
	)

	var node *snowflake.Node

	func init() {
		//纪元：2022-01-01 00:00:00.99999999
		snowflake.Epoch = time.Date(2022, 1, 1, 0, 0, 0, 999999999, time.UTC).UnixMilli()
		// 创建节点，节点编号从 0 到 1023 
		// 创建的每个节点都必须具有唯一的节点编号
		ret, err := snowflake.NewNode(0)
		if err != nil {
			log.Panicln(err.Error())
		}
		node = ret
	}

	// GenId 生成下一个雪花ID
	func GenId() int64 {
		return node.Generate().Int64()
	}

	// UUId 生成无符号的UUID
	func UUId() string {
		return strings.ReplaceAll(uuid.New().String(), "-", "")
	}

	// Base58 转换ID为base58编码，仅取最后6位
	func Base58(id int64) string {
		ret := snowflake.ID(id).Base58()
		return ret[len(ret)-6:]
	}
