-------------------------
链路层
-------------------------
	# 祯定义

		+-------+-----------------------+-------+
		|SOH	|	MTU(IP 数据报)		|EOT	|
		+-------+-----------------------+-------+

		* SOH(0x01)	00000001	Start Of Header
		* EOT(0x04)	00000100	End Of Transmission
	
	
	# 转义问题

		* 如果遇到数据报文中直接就包含了 00000100 (EOT) 数据，数据链路层就会错误地“找到帧的边界”​。
		* 要解决这个问题，就需要对数据报中的控制字符 SOH / EOT 进行转义

		* 在控制字符前插入转义字符: ESC(0x1B) 00011011
		* 接收端的数据链路层在把数据送往网络层之前删除这个插入的转义字符。这种方法称为字节填充(byte stuffing)或字符填充(character stuffing)
		* 如果转义字符也出现在数据当中，那么解决方法仍然是在转义字符的前面插入一个转义字符

	
	# 差错检查
		
		* 传输过程中可能会出现比特差错，即 1 变 0 或 0 变 1。
		* 在一段时间内，传输错误的比特占所传输比特总数的比率称为误码率BER (Bit Error Rate)。

		* 数据链路层广泛使用了循环冗余检验 CRC (Cyclic Redundancy Check) 的检错技术。
		* CRC 是一种检错方法，而 FCS 是添加在数据后面的冗余码，在检错方法上可以选用 CRC，但也可不选用 CRC。

		* 在数据链路层，发送端帧检验序列 FCS 的生成和接收端的 CRC 检验都是用硬件完成的，处理很迅速，因此并不会延误数据的传输。

-------------------------
点对点协议PPP
-------------------------

	# PPP 协议

		* https://datatracker.ietf.org/doc/html/rfc1661
		* https://www.rfc-editor.org/rfc/rfc1548.txt

		* 用户计算机和 ISP 进行通信时所使用的数据链路层协议


	# PPP 帧
		
		* https://datatracker.ietf.org/doc/html/rfc1331#section-3.1 

			+----------+----------+----------+----------+---------------+----------+----------+-----------------+
			|   Flag   | Address  | Control  | Protocol | Information	|   FCS    |   Flag   | Inter-frame Fill|
			| 01111110 | 11111111 | 00000011 | 16 bits  |      *		| 16 bits  | 01111110 | or next Address	|
			+----------+----------+----------+----------+---------------+----------+----------+-----------------+
		
			* Flag: 0x7E
				* 标志字段表示一个帧的开始或结束
				* 如果出现连续两个标志字段，就表示这是一个空帧，应当丢弃。
			
			* Address: 0xFF
				* 地址字段规定为0xFF
			
			* Control: 0x03
				* 控制字段规定为0x03
			
			* Protocol
				* 协议号，2 字节
					0x0021	信息字段就是IP数据报
					0xC021	信息字段是PPP链路控制协议 LCP 的数据
						* 链路创建/配置协商/认证鉴权
					0x8021	信息字段是网络层的控制协议 NCP 数据
						* 协议协商
				
			* Information
				* 信息，长度是可变的，不超过 1500 字节。
			

			* FCS
				* 使用 CRC 的帧检验序列 FCS，2 字节。
		
		* 字节填充
		* 零比特填充
			
	
	# PPPoE(PPP over Ethernet)
		
		* https://datatracker.ietf.org/doc/html/rfc2516

		* 为宽带上网的主机使用的链路层协议。这个协议把 PPP 帧再封装在以太网帧中（当然还要增加一些能够识别各用户的功能）​。
		

-------------------------
以太网
-------------------------
	# 网卡(NIC/网络适配器)
		* 网卡带有处理器和存储器(RAM 和 ROM)

		* NIC 有一定的缓存
			* 适配器和局域网之间的通信是通过电缆或双绞线以串行传输方式进行的。
			* 适配器和计算机之间的通信则是通过计算机主板上的I/O总线以并行传输方式进行的。
			* 网络上的数据率和计算机总线上的数据率并不相同，需要缓存临时存储

	# MAC 地址
		* 也称为硬件地址或物理地址。
		* 一种 48位的全球地址（一般都简称为“地址”​）​，指局域网上的每一台计算机中固化在适配器的 ROM 中的地址。
	
	# IEEE

		* IEEE 的注册管理机构 RA (Registration Authority) 是局域网全球地址的法定管理机构[W-IEEERA]。
		* 它负责分配地址字段的6个字节中的前三个字节(即高位24位)。
		* 世界上凡要生产局域网适配器的厂家都必须向 IEEE 购买由这三个字节构成的这个号（即地址块）​，这个号的正式名称是组织唯一标识符 OUI (Organizationally Unique Identifier)，通常也叫做公司标识符(company_id)。
		* 地址字段中的后三个字节(即低位24位)则是由厂家自行指派，称为扩展标识符(extended identifier)，只要保证生产出的适配器没有重复地址即可。

	
		* IEEE 把地址字段的第一字节的最低位(最左边/第一位)为I/G位（Individual/Group）。
			* 当I/G位为0时，地址字段表示一个单个站地址
			* 当I/G位为1时，表示组地址，用来进行多播（以前曾译为组播）
		
			* 因此，IEEE 只分配地址字段前三个字节中的23位。
		
		* IEEE 把地址字段第1字节的最低第二位规定为G/L位（Global/Local）。
			* 当G/L位为0时是全球管理（保证在全球没有相同的地址）​，厂商向IEEE购买的OUI都属于全球管理。
			* 当G/L位为1时是本地管理，这时用户可任意分配网络上的地址。采用2字节地址字段时全都是本地管理。
			* 以太网几乎不理会这个G/L位
	
	# MAC 帧过滤

		* 单播(unicast)帧（一对一）​，即收到的帧的MAC地址与本站的硬件地址相同。
		* 广播(broadcast)帧（一对全体）​，即发送给本局域网上所有站点的帧（全1地址）​。
		* 多播(multicast)帧（一对多）​，即发送给本局域网上一部分站点的帧。

	
	# MAC 帧 Ethernet II（使用最多）	
								+---------------------------------------------------------+
								|						以太网MAC帧						  |				
			+-----------+-------+-----------+-----------+-----------+-------------+-------+
			|前导码		|起始帧	|目的MAC	|源MAC		|类型		| 数据        | FCS   |
			|7 bytes	|1 byte	|6 bytes	|6 bytes	|2 bytes	|46-1500 bytes|4 bytes|
			+-----------+-------+-----------+-----------+-----------+-------------+-------+

				* 前导码
					* 前同步码（1和0交替码） 7 字节，每个字节定义为 10101010
					* 使接收端的适配器在接收MAC帧时能够迅速调整其时钟频率，使它和发送端的时钟同步，也就是“实现位同步”​（位同步就是比特同步的意思）
				
				* 起始帧
					* 帧开始定界符，定义为 10101011
					* 它的前六位的作用和前同步码一样，最后的两个连续的1就是告诉接收端适配器：​“MAC帧的信息马上就要来了，请适配器注意接收”​。
					
				
				* 类型，标志上一层使用的是什么协议，以便把收到的MAC帧的数据上交给上一层的这个协议。

					0x0800	IPV4
					0x86DD	IPv6
					0x0806	ARP
					0x8137	则表示该帧是由 Novell IPX 发过来的。
				
				* 数据
					* 最少46字节是这样得出的：最小长度64字节（MAC帧最低长度为 64 字节）减去18字节的首部和尾部（目的MAC + 源MAC + 类型 + FCS）就得出数据字段的最小长度
				
				* FCS
					* 帧检验序列FCS（使用CRC检验）​。
				
				* 数据长度？
					* 首部没有帧长度（或数据长度）字段，因为使用了曼彻斯特编码
					* 在曼彻斯特编码的每一个码元（不管码元是1或0）的正中间一定有一次电压的转换（从高到低或从低到高）​。
					* 当发送方把一个以太网帧发送完毕后，就不再发送其他码元了（既不发送1，也不发送0）​。因此，发送方网络适配器的接口上的电压也就不再变化了。
					* 这样，接收方就可以很容易地找到以太网帧的结束位置，即这个位置往前数4字节（FCS字段长度是4字节）。
			
		* MAC 帧填充
			* MAC 帧最低 64 字节，如果不足则会填充到 64 字节，上层协议需要丢弃掉填充的数据（可见，上层协议必须具有识别有效的数据字段长度的功能）。
			* 当上层使用IP协议时，其首部就有一个 “总长度”字段，因此，​“总长度” 加上填充字段的长度，应当等于MAC帧数据字段的长度。
			* 例如，当 IP 数据报的总长度为 42 字节时，填充字段共有 4 字节。当 MAC 帧把 46 字节的数据上交给 IP 层后，IP 层就把其中最后4字节的填充字段丢弃。
			* 一句话，随便 MAC 帧如何填充，IP 协议头本身有长度字段 LEN，上层 IP 协议只读取 LEN 字节，剩下的就全是 MAC 帧填充，可以丢弃了。
		
		* 帧结束定界符
			* 在以太网上传送数据时是以帧为单位传送。
			* 以太网在传送帧时，各帧之间还必须有一定的间隙。因此，接收端只要找到帧开始定界符，其后面的连续到达的比特流就都属于同一个MAC帧。
			* 可见以太网不需要使用帧结束定界符，也不需要使用字节插入来保证透明传输。

	# MAC 帧 IEEE802.3
	


-------------------------
以太网扩展 - 集线器
-------------------------
	# 在物理层扩展以太网（扩展后的以太网仍然是一个网络）​。


-------------------------
以太网扩展 - 网桥
-------------------------
	# 在数据链路层扩展以太网（扩展后的以太网仍然是一个网络）​
		* 网桥在转发帧时，不改变帧的源地址。
		* 网桥是按存储转发方式工作的，一定是先把整个帧收下来（但集线器或转发器是逐比特转发）再进行处理，而不管其目的地址是什么。
		* 网桥会丢弃 CRC 检验有差错的帧以及帧长过短和过长的无效帧。


	# 透明网桥
		* 当网桥刚刚连接到以太网时，其转发表是空的
		* 这时若网桥收到一个帧，网桥就按照以下自学习(self-learning)算法处理收到的帧（这样就逐步建立起转发表）​，并且按照转发表把帧转发出去。

			1. 收到帧
				1.1. 先查表，看看记录了源 “地址” 和 入站 “接口” 没
					1.1.1. 没找到，记录下这个帧的源 “地址”，入站 “接口” 到表中(这就表示，以后要发给源 “地址” 的帧，都通过这个接口转发)
					1.1.2. 找到了，更新源地址对应的 “接口” 和入站 “时间” 信息
				1.2. 再查表，看看记录了目标 “地址” 和对应的 “接口” 没 
					1.2.1. 没找到，转发该帧到所有除了入站 “接口” 的接口去(其他接口收到帧后，就进入了第 1 步)
					1.2.2. 找到了
						1.2.2.1. 目标地址的 “接口” 和当前帧入站 “接口” 不一样，转发帧到目标 “地址” 对应的 “接口”。
						1.2.2.2. 目标地址的 “接口” 和当前帧入站 “接口” 一样，丢弃帧，因为入站 “接口” 和 目标地址的 “接口” 一样，不用转发对方也能收到。


			* 原理很简单，从某个站 A 发出的帧从接口 x 进入了某网桥，那么从这个接口出发沿相反方向一定可把一个帧传送到 A。
			* 不得不使用这种方式进行盲目转发，才能逐步弄清所连接的网络拓扑，建立起自己的转发表。
			* 如果网络上的每一个站都发送过帧，那么每一个站的地址最终都会记录在网桥的转发表上。

		* 在网桥的转发表中写入的信息除了地址和接口外，还有帧进入该网桥的时间
		* 因为以太网的拓扑可能经常会发生变化，站点也可能会更换适配器，所以需要有周期性地扫描转发表中的项目。
		* 只要是在一定时间（例如几分钟）以前登记的都要删除。这样就使得网桥中的转发表能反映当前网络的最新拓扑状态。
		* 所以，网桥中的转发表并非总是包含所有站点的信息。只要某个站点从来都不发送数据，那么在网桥的转发表中就没有这个站点的项目。
		* 如果站点A在一段时间内不发送数据，那么在转发表中地址为A的项目就被删除了。


			
		* 生成树(spanning tree)算法
			* 透明网桥还使用了一个生成树(spanning tree)算法，即互连在一起的网桥在进行彼此通信后，就能找出原来的网络拓扑的一个子集。
			* 在这个子集里，整个连通的网络中不存在回路，即在任何两个站之间只有一条路径。
			* 为了避免产生转发的帧在网络中不断地兜圈子
	
	# 源路由网桥
	

	# 多接口网桥 — 以太网交换机
		* 交换式集线器常称为以太网交换机(switch)或第二层交换机，表明这种交换机工作在数据链路层。
		* “交换机” 并无准确的定义和明确的概念，而现在的很多交换机已混杂了网桥和路由器的功能。
		
		* 以太网交换机实质上就是一个多接口的网桥。
