----------------------------
事件
----------------------------
    # 事件
        * SOL 事件在 EVM 日志功能之上提供了一层抽象
        * 可以定义在文件级别，也可以定义在合约中
        * 日志触发的时候，会把日志数据和关联的合约信息写入到区块链，永久保存
        * 合约内部不能访问日志数据，只能是外部实体（客户端）进行查阅。
        * 支持重载

        event Transfer(address indexed from, address indexed to, uint amount);
        event Transfer(address indexed from, address indexed to, uint amount, uint timestamp);


    # indexed 参数
        * 事件最多可以定义 3 个 indexed 参数，将其存储到名为 topics 的特殊数据结构中。
        * 每个 Topic 仅能容纳 32 字节数据，因此，如果 indexed 参数是引用类型，则会取该值的 keccak256 哈希值进行存储。

    # 非 indexed 参数
        * 会被以 ABI 形式编码到日志的 data 部分。

    # 匿名事件
        * 在事件最后使用 anonymous 进行修饰
            event Transfer(address indexed from, address indexed to, uint256 value, uint256 timestamp) anonymous;

        * 匿名事件，没有签名哈希，所以无法通过事件名称来过滤。它最多支持 4 个 indexed 参数。


    # 使用 emit 发布事件

        * 如果有参数，则传入参数

            event Deposit(
                address indexed from,
                bytes32 indexed id,
                uint value
            );

            function deposit(bytes32 id) public payable {
                emit Deposit(msg.sender, id, msg.value);
            }
    
    # 事件的数据结构
        {
            "data": "0x7f…91385",
            "topics": ["0xfd4…b4ead7", "0x7f…1a91385"]
        }

        * 如果是非匿名事件，则 topics[0] 就是事件的签名
        * 后续的参数就是事件的 indexed 参数值
        * data 是非 indexed 参数值的 abi 编码
        * 需要注意的是，匿名事件可以伪造非匿名事件，即通过 “第一个” 参数来冒充非匿名事件的签名。

    # 成员
        selector
            * 对于非匿名事件，这是一个 bytes32 值 包含事件签名的 keccak256 哈希。
            * 但是代码里面没法访问？


