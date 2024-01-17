----------------------------
Redis 原生命令
----------------------------
	XACK key group id [id ...]
		* 确认一条消息，返回成功确认的消息数
	
			key		Stream 名称
			group	消费组
			id		消息 ID
		
		* 本质上就是从消费组的 pending 消息列表中删除消息
			

	XADD key [NOMKSTREAM] [<MAXLEN | MINID> [= | ~] threshold [LIMIT count]] <* | id> field value [field value ...]
		* 添加消息到 Stream，返回消息的 ID
		
			key			Steam 名称，如果 Stream 不存在则会创建
			NOMKSTREAM	（No Make Stream）如果设置了这个指令，在 Stream 不存在的时候不会创建，而返回 null
			MAXLEN		
			<* | id>	指定ID，或者是 *（表示由系统生成 ID），不建议自动设置，推荐使用系统生成的
			field value	键值对，一条消息可以有多个键值对
			
		* 添加消息到 Stream
			XADD orders * order_id 10086 memeber_id 10010
			"1705399633485-0"
		
		
		* 自动设置的的消息ID由时间戳和序列组成，这可以很容易地按照时间范围检索记录
	

	
	XAUTOCLAIM key group consumer min-idle-time start [COUNT count] [JUSTID]
		* 自动转移符合指定条件的待处理消息的所有权，以数组形式返回已转移的消息以及一个流 ID，作为后续调用的 <start> 参数，用于类似游标
			
			key				Stream名称
			group			转移给哪个消费组
			consumer		消费组中的哪个消费者
			min-idle-time	条件：消息空闲时间
			start			条件：大于或等于 start 值的 ID
			COUNT			指定转移消息的数量，默认为 100
			JUSTID			只返回已成功转移消息的 ID 数组，不包含实际消息内容
		
		* XAUTOCLAIM 会重置其闲置时间
		* 当没有剩余的 PEL （待处理）消息时，命令会返回特殊的: 0-0 ID，并不表示全部消息处理完了，因为可能在执行 XAUTOCLAIM 的期间又有新的待处理消息。

		* 在遍历 PEL 时，如果 XAUTOCLAIM 发现在 Stream 中已不存在的消息（被 XDEL 修剪或删除），则不会对其进行转移，而是将其从 PEL 中删除，但是会返回消息的 ID
		* 使用 XAUTOCLAIM 还会累计消息的 “尝试投递次数”，除非指定了 JUSTID 选项（该选项只递送消息 ID，而非消息本身）
	
		* 把 orders 中，超过 1 个小时（3600000 毫秒）且 ID 大于等于 0-0 的消息，转移到消费组 group_1 中的 consumer_1 消费者上，最多转移 25 个消息
			XAUTOCLAIM orders group_1 consumer_1 3600000 0-0 COUNT 25


	XCLAIM key group consumer min-idle-time id [id ...] [IDLE ms] [TIME unix-time-milliseconds] [RETRYCOUNT count] [FORCE] [JUSTID] [LASTID lastid]
		* 在流消费组中，更改待处理消息的所有权，为其指定新的的消费者
			key				Stream名称
			group			哪个消费组中
			consumer		把待处理消息重新分配给消费组中的哪个消费者
			min-idle-time	条件：消息空闲时间
			id				条件：指定 ID，可以有多个
			IDLE			设置消息的空闲时间（最后一次传送时间）。如果未指定 IDLE，则假定 IDLE 为 0，也就是说，由于消息现在有了新的所有者在尝试处理，因此时间计数将被重置。
			[TIME unix-time-milliseconds]	
							与 IDLE 相同，但不是相对的毫秒数，而是将空闲时间设置为特定的 Unix 时间（毫秒）。这对重写生成 XCLAIM 命令的 AOF 文件非常有用。
			RETRYCOUNT
							将重试计数器设置为指定值。每次消息再次传送时，该计数器都会递增。通常情况下，XCLAIM 不会改变这个计数器
			FORCE
							强制在 PEL 中创建待处理的消息，即使指定的 ID 的消息不存在于其他客户端的 PEL 中。不过，信息必须存在于 Stream 中，否则将被忽略。
			JUSTID			只返回已成功转移消息的 ID 数组，不包含实际消息内容
		
		* XCLAIM 会重置空闲时间，这可以避免同一个消息被并发转移到不同的消费者导致重复消费（第一个客户端转移后修改了空闲时间，第二个客户端转移时空闲时间条件不成立了）
		* 同样， XCLAIM 也会累计消息的“尝试投递次数”，除非指定了 JUSTID 选项（该选项只递送消息 ID，而非消息本身）
		* 如果消息在 Stream 中已经不存在（被删除/修剪），则不会对其进行转移，而是将其从 PEL 中删除，但是会返回消息的 ID
		
		* 在 orders 中，group_1 组中未处理的消息 1526569498055-0，如果它闲置时间超过了 1 个小时（3600000 毫秒），就转移给 consumer_1。
		
			XCLAIM orders group_1 consumer_1 3600000 1526569498055-0
	
	XDEL key id [id ...]
		* 删除 ID，返回成功删除的数量
			key		Stream名称
			id		要删除的 ID，可以指定多个
		
		* Redis 底层使用 radix tree 来索引宏节点，宏节点以线性方式打包数十个 Stream 条目。通常情况下，从流中删除条目时，条目并不会真正被驱逐，而只是被标记为已删除。
		* 最终，如果宏节点中的所有条目都被标记为已删除，整个节点就会被销毁，内存也会被回收。
	

	XGROUP CREATE key group <id | $> [MKSTREAM] [ENTRIESREAD entries-read]
		* 为指定 Stream 创建一个新的消费组，组名称必须唯一，如果不唯一则会返回错误
			key				Stream名称
			group			消费组名称
			<id | $>		这个新的消费组从哪个ID开始消费
							$ 表示消费从创建时起后的消息，如果需要这个消费组从头开始消费，可以指定 id 为起始 id: 0
			MKSTREAM		如果流不存在，则强制性创建流。
			ENTRIESREAD		回溯消费的开始 ID
							entries_read 参数指定一个任意的ID。任意的ID是指不是流的第一个条目、最后一个条目或零（"0-0"）ID的任何ID。
							使用它来查找在任意ID（不包括它）和流的最后一个条目之间有多少条目。将 entries_read 设置为流的 entries_added 减去条目数即可。


	XGROUP CREATECONSUMER key group consumer
		* 在指定的消费者组下创建消费者

			key				Stream名称
			group			消费组名称
			consumer		消费者名称
		

		* 当操作（例如 XREADGROUP ）引用一个不存在的消费者时，消费者也会自动创建。这仅在流中存在数据时对 XREADGROUP 有效
	
	XGROUP DELCONSUMER key group consumer
		* 在指定的消费者组下删除消费者

			key				Stream名称
			group			消费组名称
			consumer		消费者名称
		
		* 注意。删除消费者后，该消费者的任何待处理消息都不能被“转移”了。因此，强烈建议在从消费组中删除消费者之前，先对其未确认的消息进行转移或确认。
	
	XGROUP DESTROY key group
		* 从指定的流中删除消费者组
	
			key				Stream名称
			group			消费组名称
		
		* 注意，即使有活动的消费者和待处理消息，消费组也会被销毁，因此确保只有在真正需要时才调用该命令。
	
	XGROUP SETID key group <id | $> [ENTRIESREAD entries-read]
		* 设置消费组的最后交付 ID
			key				Stream名称
			group			消费组名称
			<id | $>		指定 ID
			ENTRIESREAD		回溯消费的开始 ID

		
		* 通常，消费组的最后交付 ID 是在使用 XGROUP CREATE 创建该组时设置的
		* 该命令允许修改组的最后交付 ID，而无需删除和重新创建该组。例如，如果需要重新消费所有消息，可以设置为：0
	
	XINFO CONSUMERS key group
		* 返回指流消费组中消费者列表

			key				Stream名称
			group			消费组名称
		
		* 返回的消息如下
			> XINFO CONSUMERS mystream mygroup
			1) 1) name
			   2) "Alice"				// 消费者名称
			   3) pending
			   4) (integer) 1			// PEL 中的条目数：消费者的待处理信息，即已发送但尚未确认的信息
			   5) idle
			   6) (integer) 9104628		// 自消费者上次尝试交互（例如：XREADGROUP、XCLAIM、XAUTOCLAIM）以来已过去的毫秒数。
			   7) inactive
			   8) (integer) 18104698	// 消费者上次成功交互后已过去的毫秒数（例如：XREADGROUP、XCLAIM、XAUTOCLAIM）： XREADGROUP 实际读取了 PEL 中的某些条目，XCLAIM/XAUTOCLAIM 实际转移了某些条目）。
	
	XINFO GROUPS key
		* 返回 Stream 中的消费组列表

				key				Stream名称

		* 返回如下
			> XINFO GROUPS mystream
			1)  1) "name"
				2) "mygroup"			// 消费组名称
				3) "consumers"
				4) (integer) 2			// 消费组中的消费者数量
				5) "pending"
				6) (integer) 2			// 消费组待处理条目列表（PEL）的长度，即已发送但尚未确认的信息
				7) "last-delivered-id"
				8) "1638126030001-0"	// 最后发送到消费组的条目的 ID
				9) "entries-read"
			   10) (integer) 2			// 逻辑 "读取计数器"，用于计算最后发送到消费组的条目。
			   11) "lag"
			   12) (integer) 0			// Stream 中仍在等待传送给组中消费者的条目的数量，如果无法确定该数量，则用 NULL 值表示。
	
	
	XINFO STREAM key [FULL [COUNT count]]
		* 返回 Stream 信息

			key				Stream名称
			FULL			返回完整信息，包括流中群组的信息，以及 PEL 列表
			COUNT			COUNT 选项可用于限制返回的数据流和 PEL 条目的数量（返回前 <count> 条目）。
							默认 COUNT 为 10，COUNT 为 0 表示将返回所有条目（如果流条目较多，执行时间可能较长）。


		* 返回如下
			> XINFO STREAM mystream
			 1) "length"
			 2) (integer) 2				// 流中的条目数（参见 XLEN）
			 3) "radix-tree-keys"		
			 4) (integer) 1				// 底层 radix 数据结构中 Key 的个数
			 5) "radix-tree-nodes"		
			 6) (integer) 2				// 底层 radix 数据结构中的节点数
			 7) "last-generated-id"		
			 8) "1638125141232-0"		// 最后添加到流中的条目的 ID
			 9) "max-deleted-entry-id"
			10) "0-0"					// 从流中删除的最大条目 ID
			11) "entries-added"
			12) (integer) 2				 // 流的生命周期内添加到流中的所有条目的计数
			13) "groups"
			14) (integer) 1				// 流中定义的消费组数量
			15) "first-entry"
			16) 1) "1638125133432-0"	// 流中第一个条目的 ID 和字段值元组
				2) 1) "message"
				   2) "apple"
			17) "last-entry"
			18) 1) "1638125141232-0"	// 流中最后一个条目的 ID 和字段值元组
				2) 1) "message"
				   2) "banana"

	XLEN key
		* 返回 Stream 中消息数量

			key				Stream名称
		
		* 如果 key 不存在，则返回 0，注意 0 长度的流可能是存在的，所以推荐使用  TYPE 或 EXISTS 来检查 Stream Key 是否存在
		* 流如果没有条目，也不会被删除，因为可能有关联的消费者组
	
	XPENDING key group [[IDLE min-idle-time] start end count [consumer]]
		* 检查待处理消息列表（PDL）

			key				Stream名称
			group			消费组名称
			IDLE			只统计超过指定空闲时间的消息
			start
			end				指定最大ID和最小ID
			count			指定返回的待处理消息数量
			consumer		指定消费者
		
		* 汇总查询
			> XPENDING mystream group55
			1) (integer) 1				// 消费组中待处理消息
			2) 1526984818136-0			// 待处理消息中的最小消息 ID
			3) 1526984818136-0			// 待处理消息中的最大消息 ID
			4) 1) 1) "consumer-123"		// 消费者
				  2) "1"				// 消费者中的待处理消息
			
		
		* 详细的待处理消息查询
			> XPENDING mystream group55 IDLE 9000 - + 10
			1) 1) 1526984818136-0			// 消息的ID
			   2) "consumer-123"			// 消息所属的消费者
			   3) (integer) 196415			// 自上次向该消费者发送此信息以来所经过的毫秒数
			   4) (integer) 1				// 该消息被传递的次数。
		
		* 可用于迭代待处理消息，ID 前加上 '(' 字符，表示开放（独占）范围的括号，用于迭代
	
	XRANGE key start end [COUNT count]
		* 检索 Stream 中的条目
			key				Stream名称
			start			开始ID
			end				结束 ID
			COUNT			数量
		

		* 特殊的消息ID：'-' 和 '+'，表示最小 ID 和 最大 ID
		* ID 也可以只指定时间戳部分，不指定序列号部分
			> XRANGE orders 1526985054069 1526985055069
		

		* 默认情况下，开始和结束 ID 都是包含在结果中的，可以使用 '(' 来排除开始 ID，用于迭代
			> XRANGE orders (1526985685298-0 + COUNT 2
		

		* Redis 未提供根据 ID 获取单个消息的命令，但这可以通过 XRANGE 来实现，开始和结束 ID 都为指定 ID 即可
			> XRANGE orders 1526984818136-0 1526984818136-0
	
	XREVRANGE key end start [COUNT count]
		* 逆向检索 Stream 中的条目
		* 和 XRANGE 一模一样，只是它是从后往前遍历，end 参数在前，start 参数在后
	
	
	XREAD [COUNT count] [BLOCK milliseconds] STREAMS key [key ...] id [id ...]
		* 从一个或多个流中读取数据
		
			COUNT		读取多少个
			BLOCK		阻塞时间，毫秒，如果为 0 表示不超时。该参数决定了是否是阻塞的
			STREAMS		指定多个流
			id			消费大于该 ID 的消息
		
		* 如果指定了多个 STREAMS 那么每个 STREAMS 必须要有对应的 ID，ID 也可以仅指定时间戳

		* 特殊 ID：'$'，表示只接收从阻塞开始通过 XADD 添加到数据流中的条目
		* 注意，使用 '$' 阻塞消费，可能会错过流中的条目，你在消费的时候流中可能已经新增了N条记录。当你消费完毕后，再次阻塞消费，只能消费到阻塞后新增的条目
		* 因此在使用 '$' 消费后，应该使用非阻塞的方式，从上次消费 ID 开始进行消费

		* 非阻塞模式
			* '$' 参数是无意义的, 读取的消息总是为空

		* 阻塞模式
			* 如果参数 ID 指定为 '$', 那么 COUNT 参数不起作用，只要有有数据, 就会立即返回，阻塞结束
			* 如果指定的 ID 不是'$', 比如是 0，并且 stream 中有数据可读, 此时阻塞模式可以简单地认为退化成非阻塞模式，'COUNT' 参数起作用
	
		* 如果同时监听了多个 stream，在集群环境下, 要注意所有的 stream  key 必须在同一个 slot 上否则异常
	

	XREADGROUP GROUP group consumer [COUNT count] [BLOCK milliseconds] [NOACK] STREAMS key [key ...] id [id ...]
		* 以消费组的形式从一个流或者多个流中消费消息

			group		消费组
			consumer	消费组中的消费者
			COUNT		读取多少个
			BLOCK		阻塞时间，毫秒，如果为 0 表示不超时。该参数决定了是否是阻塞的
			NOACK		不需要 ACK（即，自动 ACK）
			STREAMS		指定多个流，但是有一个前提条件: 所有的 stream 都预先创建了同名的消费者组
			id			消费大于该 ID 的消息
		
		* 基本上和 XREAD 相同

		* 一条消息只能被消费组中的一个消费者消费，且需要 ACK 消息
		* 也就是说存一种可能，即消费者崩溃，导致消息未 ACK，此时需要通过 XAUTOCLAIM 等命令把未 ACK 消息转移给其他消费者进行处理

		* 消费组只有一个特殊 ID：'>'，仅在消费组模式下有效，个特殊的ID其实就是 last_delivered_id
		* 当ID不是特殊字符 '>' 时, XREADGROUP 不再是从消息队列中读取消息, 而是从消费者的 pending 消息列表中读取历史消息。在这种情况下，BLOCK 和 NOACK 都会被忽略。
		* 如果把ID设为 0-0，表示读取所有的 pending 消息以及自 last_delivered_id 之后的新消息。
		* 使用非 '>' 作为 ID，通常用于读取那些未被 ACK 的消息

		* XREADGROUP 虽然是个读取操作，本质上是一个写命令。
		* 因为在读取的时候，XREADGROUP 内部会把读取到的消息添加到消费者的pending消息队列，并且会修改消费者组中的 last_delivered_id 等数据结构
		* 所以这是一个写命令，如果在开启了读写分离的环境中, 这个命令只能在 master 节点上进行操作
		
		* 如果 PEL 中的条目被从 Stream 中删除了（XDEL 或者是修剪），那么 PEL 中的条目 ID 仍然存在，但是键值对是 NULL
	
	
	XSETID key last-id [ENTRIESADDED entries-added] [MAXDELETEDID max-deleted-id]
		* 内部命令，用于复制 Stream 的 last_delivered_id

	
	XTRIM key <MAXLEN | MINID> [= | ~] threshold [LIMIT count]
		* 修剪 Stream 长度，删除 ID 较低的条目来实现修剪

			key				Stream名称
			MAXLEN			最大长度（正整数），超过这个长度就会被修剪
			MINID			删除 ID 小于 MINID 值的条目
			[= | ~]			'=' 表示精确长度，'~' 表示非精确
		
		* 非精确修剪，效率大大提高
			> XTRIM orders MAXLEN ~ 1000
		
