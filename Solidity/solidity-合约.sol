---------------------
合约
---------------------
    # 合约 Contract 
        * 类似于 OOP 中的类，包含了状态和行为
        * 状态只能通过函数进行修改
        * 合约方法只能是通过交易被动调用，不能主动执行，所有没有 cron 之类的定时执行机制
    

    # 合约的创建
        * 合约创建的时候，构造函数会执行，合约只能有一个构造函数，没有重载
        * 构造函数执行完毕后，合约的最终代码就会被存储在区块链上：
            * 包括：合约中所有的公共函数，外部函数，以及函数调用到的其他函数
            * 不包括：构造函数，以及仅仅会被构造函数调用到的函数
        * 不允许循环创建合约，因为创建合约的时候，创建方必须知道被创建合约的源码以及二进制代码
    
    # 构造函数
        * 格式固定： constructor() {}
            contract Name {
                constructor() {}
            }

        * 如果没有主动声明，系统会默认生成一个空的构造函数
        * 构造函数可以有参数，引用类型只能是： 
            * memory 
            * storage 指针，此时合约必须标记为 abstract，因为这些参数无法从外部赋予有效值，只能通过子合约的构造函数进行赋值。
        
                abstract contract A {
                    uint[] private ids;
                    constructor(uint[] storage _ids){
                        ids = _ids;
                    }
                }
                contract B is A {
                    uint[] ids;
                    constructor() A(ids) {}
                }
    
    # 状态变量的可见性
        public
            * 所有人都可以访问，并且还会生成 getter 方法

        internal
            * 合约内部和子合约可见
            * 默认值

        private
            * 仅合约内部可见

        * 声明在类型只之后，变量名称之前
        * 可见性，只是对于合约调用的读写而言，区块链上一切透明，任何人都可以查看任何数据

    # getter 函数
        * 编译器会自动为 public 状态生成 getter 函数，自动标记为 view。
        * public 状态有两种访问方式：
            * 内部访问，直接访问变量
            * 外部访问，通过 this，以方法形式访问
        
            contract Demo  {
                uint public id;

                function foo () public view returns(uint){
                    // 外部，即通过 this 形式的 getter 访问
                    uint x = this.id();
                    // 直接访问
                    return id * x;
                }
            }

        * 数组 getter，参数是索引，只能返回的是单个元素

            uint[] public ids;
            // 生成的 getter 函数签名为 function ids(uint index) public view returns(uint);
            function foo (uint index)public view returns(uint){
                // 访问getter
                return this.ids(index);
            }
        
        * mapping getter，参数就是 key，返回单个元素

            mapping(address => uint) public balance;
            function foo() public view returns(uint) {
                return this.balance(msg.sender);
            }
        
        * 结构体 getter，无参数，返回的是结构体的解构参数

            struct Member {
                uint id;
                string name;
                string[] hobbys;
                mapping (address => uint) balance;
                bool status;
            }
            contract Demo  {

                Member public memeber;
                
                function foo() public view returns(uint, string memory) {
                    // 解构出所有可解构的字段，省略了最后一个 bool
                    (uint id, string memory name, ) = this.memeber();
                    return  (id, name);
                }
            }

            * 结构体中的数组和 mapping 不能被解构出来
    
        * 复杂的结构，getter 允许有多个参数，本质上就是逐层解析

            struct Member {
                uint id;
                string name;
                string[] hobbys;
                mapping (address => uint) balancel;
                bool status;
            }
            contract Demo  {

                // Map<Address, Map<Uint, Member>>[]
                mapping (address => mapping (uint => Member))[] public memeber;
                
                function foo() public view returns(uint, string memory) {
                    // 第一个参数表示数组索引
                    // 第二个参数表示外层 mapping 的 key
                    // 第三个参数表示内存 mapping 的 key
                    // 最终结解构出来的内容，就是内层 mapping 的 value 值
                    (uint id, string memory name, ) = this.memeber(0, msg.sender, 1);
                    return (id, name);
                }
            }
        


    # modifier 
        * 类似于装饰器设计模式，可以在函数执行前后，执行一些逻辑、校验
            modifier mustOwner {
                require(msg.sender == owner);
                _;
            }
        
        * 不能重载，没有可见性的概念，本质上是一种 语法模板（code wrapper），在编译阶段会被 内联展开（inline expansion） 到函数中。
        * 可以定义参数
            
            // 定义俩参数
            modifier minValue(uint min,uint max) {
                uint unused = 0; // 函数中不能访问到 modifier 中的局部变量
                require(min < max);
                _;
            }

            // 声明的时候，可以访问函数的参数作为参数传递
            function foo(uint x, uint y) public payable  minValue(x, y){}
        

        * 多个 modifier 用分号隔开，依次执行。
        * modifier 中的 _; 是必须的，表示被 wrap 的函数，可以多次声明 _; 表示多次调用，最后一次调用的返回值为最终的返回值。
    
    # transient 瞬时存储

        * 类似于 storage, 但是在每次调用后都会重置，可以理解为每次调用的 ThreadLocal，当前调用的所有帧，访问到的都是同一个瞬时存储
        * 目前只支持基本的值类型，不支持数组、mapping、结构体
        * 声明时不能初始化值，只能是默认值，且不能是 constant 或 immutable 的。
        * transient 的顺序不会影响到合约的内存布局
            // 声明一个 bool 的瞬时存储
            bool transient locked;

        * transient 也支持权限修饰符，public 权限也会生成 getter 
        * 瞬时存储的读写操作和持久存储一样，遵循可变性规则，因此读不能在 pure，写不能 view 函数中进行。
        * 若帧发生回滚，则在进入该帧至返回期间对临时存储的所有写入操作均将被撤销，包括在内部调用中发生的写入，可以在外部通过 try/catch 来阻止   
        * DELEGATECALL 场景中，由于当前不支持引用临时存储变量，因此无法将此类变量传递至库调用。
            * ELEGATECALL 调用会把上下文转移到调用合约中，而瞬时变量是特定于合约的，所以瞬时存储不能被传递
            * 例如：合约 A 使用 ELEGATECALL 调用 B，如果试图传递瞬时存储，就可能会遇到问题。因为 B 的瞬时存储在 ELEGATECALL 不能访问

        * 在库中访问临时存储仅可通过内联汇编实现。
        
    # constant 和 immutable 状态变量

        * constant
            * 必须在编译时初始化，编译时内联
                bool constant public ok = !false;

            * 赋值给它的表达式会被复制到所有访问它的地方，并且每次访问都会重新求值。
        
        * immutable
            * 可以在构造函数中进行初始话，也可以在声明时直接初始化
                bool immutable public ok;
                constructor(){
                    ok = true;
                    ok = false; // 在构造时可以被赋值多次
                }

            * 甚至是不初始化也可以，那么值就是类型的默认值
            * 在构造时只求值一次，其值会被复制到代码中所有访问它的地方
            * 即使这些值可以用更少的字节容纳，也会为它们预留 32 字节

        * 在源代码中，每次出现这样的变量时，它都会被替换为其底层值，并且编译器不会为其分配存储槽（storage slot）。
        * 目前仅支持字符串（仅限 constant）和值类型。

    # receive () external  payable {} 函数

        * 不需要 function 关键字，不能有参数，不能有返回值，必须是 external payable 的。
        * 可以是虚函数，可以被重写，可以添加 modifier，一个合约最多只能声明一个。
        * 当合约被调用且 calldata 为空时，receive 函数将被执行，该函数用于处理纯以太坊转账（如通过 .send() 或 .transfer() 调用）。
        * 若未声明 receive 函数但存在可支付 payable fallback 函数，则纯转账时将调用 fallback 函数。
        * 若同时缺失 receive 函数和 payable fallback 函数，合约将无法通过非 payable 函数调用的交易接收以太坊，并抛出异常、退款。
        * receive 函数只有 2300 gas 额度，除基本日志记录外几乎无法执行其他操作。

            // 声明日志事件
            event Received(address, uint);

            receive() external payable {
                // 一般只能发布个日志
                emit Received(msg.sender, msg.value);
            }
        
        * 注意，合约无法拒绝 coinbase 交易，就算是没 receive 函数，也会入账。

    # fallback 函数
        * 支持多种声明形式
            fallback () external [payable]
            fallback (bytes calldata input) external [payable] returns (bytes memory output)

            * 不需要 function 关键字，必须是 external 的，payable 可选。
            * 可以有 bytes 参数，可以有 bytes 返回值。可以是虚函数，可以被重写，可以添加 modifier，一个合约最多只能声明一个。
            * input 参数等同于 msg.data，返回的 output 就是原样返回，不会进行 ABI 编码（甚至都不会填充）。
        
        * 当调用合约时，找不到匹配的方法，或 calldata 为空且无 receive 函数，则执行 fallback 函数。
        * 若需接收以太币，必须标记为 payable。
        * 若使用带参数的版本，input将包含发送至合约的完整数据（等同于msg.data），并可在output中返回数据。返回的数据不会进行ABI编码，而是原样返回（甚至不添加填充）。
        * gas 限制的问题
            * 如果是通过 .send() 或 .transfer() 调用到 fallback 时（未定义 receive 函数），由于 2300 gas 限制，只能执行非常简单的操作（发个日志）。
            * 如果通过其他方法调用回退函数并提供更多的 gas，它可以执行更复杂的操作。


    
    # 重载
        * 支持函数重载，参数个数、类型不同即可，和返回值无关，支持重载继承的函数。
        * 需要考虑外部类型，如果外部类型相同，会重载失败。
        
            // 看似参数不不一样，其实在外部，都是地址类型，所以，重载失败
            function f(B value);            // 合约类型，本质上就是地址类型
            function f(address value);      // 地址类型

        * 也要考虑到隐式转换的问题，如果调用参数可以隐式转换为多个方法的参数，也会重载失败

            // 使用 f(40) 调用，就会异常，因为 40 可以转换为 uint8 或是 uint256
            // 使用 f(256) 调用，就可以，因为 256 可以隐式转换为 uint256，不能隐式转换为 uint8
            function f(uint8 val) public pure returns (uint8 out) 
            function f(uint256 val) public pure returns (uint256 out) 
    

