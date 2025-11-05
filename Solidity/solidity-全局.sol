--------------------------
全局
--------------------------

--------------------------
全局单位
--------------------------
    # 以太单位
        * 数值后面可以用 wei\gwei\ether 来表示以太的单位，默认 wei 

        assert(1 == 1 wei);
        assert(1 gwei == 1e9);
        assert(1 ether == 1e18);
    
    # 时间单位
        * 数值后面可以用 seconds/minutes/hours/days/weeks 来指定时间单位，默认为 seconds

        assert(1 == 1 seconds);
        assert(1 minutes == 60 seconds);
        assert(1 hours == 60 minutes);
        assert(24 hours == 1 days);
        assert(7 days == 1 weeks);


    # 单位不能直接用于变量赋值

    

--------------------------
区块和交易相关
--------------------------


    blockhash(uint blockNumber) returns (bytes32)
        * 当块编号属于最近256个区块之一时，返回该区块的哈希值；否则返回零。

    blobhash(uint index) returns (bytes32)
        * 当前事务关联的第i个二进制对象的版本化哈希值。
        * 版本化哈希由单字节版本标识符（当前为0x01）开头，后接KZG承诺（EIP-4844）的SHA256哈希值最后31字节组成。
        * 若不存在指定索引的二进制对象，则返回零。
    
    gasleft() returns (uint256)
        * 剩余 gas 
    
    # block

        block.basefee (uint)
            * 当前区块的基础费用（EIP-3198 和 EIP-1559）

        block.blobbasefee (uint)
            * 当前区块的blob基础费用（EIP-7516和EIP-4844）
            
        block.chainid (uint)
            * 当前链 ID

        block.coinbase (address payable)
            * 当前区块矿工的地址

        block.difficulty (uint)
            * 当前区块难度（EVM < Paris）。对于其他EVM版本，该参数作为block.prevrandao的弃用别名存在（EIP-4399）。

        block.gaslimit (uint)
            * 当前区块的 gaslimit

        block.number (uint)
            * 当前区块号

        block.prevrandao (uint)
            * beacon 链提供的随机数（EVM >= Paris）

        block.timestamp (uint)
            * 当前区块时间戳（以秒为单位，自Unix纪元起算）

    # msg
        msg.data (bytes calldata)
            * 完整的调用参数

        msg.sender (address)
            * 消息发送者（当前调用）

        msg.sig (bytes4)
            * 当前调用的前 4 字节，即方法的签名

        msg.value (uint)
            * 随消息发送的wei的个数
        
        * msg 的所有成员的值，包括 msg.sender 和 msg.value 可以在每次 external 函数调用中变化。 这包括对库函数的调用。

    # tx
        tx.gasprice (uint)
            * 交易的 gas 费

        tx.origin (address)
            * 交易发起方（完整调用链）

--------------------------
ABI 相关
--------------------------

    abi.decode(bytes memory encodedData, (...)) returns (...)
        * 解码 abi 数据，的一个参数是原始数据，后续的参数是 abi 参数的类型
            // 解析 data 参数为 uint, uint[2], bytes 数据
            (uint a, uint[2] memory b, bytes memory c) = abi.decode(data, (uint, uint[2], bytes))

    abi.encode(...) returns (bytes memory)
        * ABI 编码指定的数据，返回编码后的内容

    abi.encodePacked(...) returns (bytes memory)
        * 对给定的参数执行紧凑编码。注意，紧凑编码可能存在歧义！

    abi.encodeWithSelector(bytes4 selector, ...) returns (bytes memory)
        * 编码指定的数据，并且在开头添加 4 字节的 selector

    abi.encodeWithSignature(string memory signature, ...) returns (bytes memory)
        * 计算 signature 字节形式的 keccak256 签名，取前 4 个字节作为 selector 添加到编码后的 abi 数据后面
        * 相当于 abi.encodeWithSelector(bytes4(keccak256(bytes(signature))), ...)

    abi.encodeCall(function functionPointer, (...)) returns (bytes memory)
        * 调用函数指针 functionPointer，其参数来自元组。
        * 执行完整类型检查，确保类型与函数签名匹配。结果等同于 abi.encodeWithSelector(functionPointer.selector, (...))。


--------------------------
数学和加密
--------------------------
    addmod(uint x, uint y, uint k) returns (uint)
        * 计算 (x + y) % k，其中加法采用任意精度执行且不会在 2**256 处溢出。从版本 0.5.0 开始，断言 k != 0。
    
    mulmod(uint x, uint y, uint k) returns (uint)
        * 计算 (x * y) % k，其中乘法运算采用任意精度执行且不会在 2**256 处溢出。从版本 0.5.0 开始，断言 k != 0。
    
    keccak256(bytes memory) returns (bytes32)
        * 计算参数的 keccak 256 Hash 值

    sha256(bytes memory) returns (bytes32)
        * 计算给定数据的 SHA 256 值
    
    ripemd160(bytes memory) returns (bytes20)
        * 计算给定数据的 RIPEMD-160 HASH 值
    
    ecrecover(bytes32 hash, uint8 v, bytes32 r, bytes32 s) returns (address)
        * 从椭圆曲线签名中恢复与公钥关联的地址，或在出错时返回零。函数参数对应于签名的ECDSA值：

            r = 签名的前32字节
            s = 签名的后32字节
            v = 签名的最后1字节

        * ecrecover 返回的是地址，而非可支付地址。若需向恢复的地址转账，需要进行转换


        

