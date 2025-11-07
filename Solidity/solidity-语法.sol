-------------------------
语法
-------------------------
    # 和 JavaScript 一样的语句结构
        if、else、while、do、for、break、continue、return

        * 条件表达式后面的括号不可省略，如果只有单语句，可以省略大括号
        * Solidity 中没有 bool 类型的自动转换，也就是说 if (1) {...} 是无效的，会提示语法异常
    
    # 解构，即元祖

        * 即一个支持不同参数类型的列表，长度在编译时确定（常量）
        * 无需中间变量的变量交换

            uint x = 12;
            uint y = 23;
            (x, y) = (y, x);
        
        * 对函数返回的多个值进行解构，数量必须一致，对于不需要的参数，可以忽略，但是要保留 , 
        
            function demo(function () external returns (int, uint, string memory) func) public   {
                // 忽略前两个参数
                (,, string memory name) = func();
            }
        
        * 不能混合变量声明和非声明赋值

            int x = 123;
            (x, int y) = (10,20);  // 异常

            (int x, int y) = (10,20); // OK
    
    # 作用域
        * 没有变量提升，内部作用域会隐藏外部作用域的相同名称变量
    

-----------------------------
算术检查
-----------------------------
    # 在对不受限制的整数执行计算的时候，可能会出现上溢和下溢的情况

    # 默认情况下如果发生了溢出，则会回滚
        ++, --, +, 二元 -, 一元 -, *, /, %, **
        +=, -=, *=, /=, %=

        * 位运算不会溢出

    # 如果需要不回退，可以使用 unchecked {} 代码块包裹

        function f(uint a, uint b) pure public returns (uint) {
            // 减法溢出会返回“截断”的结果
            //f(2, 3) =>  返回 2**256-1
            unchecked { 
                return a - b; 
            }
        }

        function g(uint a, uint b) pure public returns (uint) {
            // 断言失败，在下溢时将回滚
            return a - b;
        }
    
        * unchecked 中不能使用 _ 符号

-----------------------------
运算符
-----------------------------
    # 算术

        * 如果右操作数的类型可以隐式转换为左操作数的类型，则使用左操作数的类型
        * 如果左操作数的类型可以隐式转换为右操作数的类型，则使用右操作数的类型
        * 否则，操作是不允许的。   
        * 字面量之间的计算是不会有精度丢失的，直到转换为需要的类型
    
    # 三元运算
        
        <exp> ? <trueCondition> : <falseCondition>

        * 和算术运算一样，编译器会检查左右两边能否隐式转换，并选出“最宽”的公共类型。
    

    # 复合和增量/减量运算符
        
         int x = 12;
         x += 23; // x = x + 23;

         * 对于大部分运算符都支持

    # delete
        * 分配初始化值，对于基本类型，就是设置为 0
        * 对于动态数组，分配一个长度为0的数组
        * 对于静态数据，长度不变，所有成员的值为 0 值
        * 删除数组中的某个元素，把这个元素的值设置为 0 值
        * 对于结构体，会把结构体中的所有成员重置。
            * 如果结构体包含了其他结构体，会递归重置
            * 如果结构体包哈了 mapping ，不会清空 mapping 内容，因为没法知道 mapping 里面到底有啥

        * 对于 mapping 可以删除其中单个的key，不能删除整个 mapping 
        
            mapping(address => uint) balance;
            delete balance;  // 不能删除 mapping
            delete balance[msg.sender]; // 可以删除单个元素

        * 不能 delete storage 的引用

            uint[] storage y = members; // 引用 storage 
            delete y;  // 异常，不能删除 storage 指针，因为 storage 指针的值，只能指向 storage 
            delete members; // 直接删除 storage 是可以的，并且会影响到 y，它俩指向一样

-----------------------------
类型转换
-----------------------------
    # 强制转换
        * integer 小转大，没有问题，大转小，可能会导致精度丢失
            uint32 a = 0x12345678;
            uint16 b = uint16(a); // 大转小，高位截断：b 为 0x5678

        * 定长字节，小转大，在右侧添加 0，大转小，截断

            bytes2 x = 0x1234;
            bytes4 y = bytes4(x); // 0x12340000
            bytes2 z = bytes2(y);  // 0x1234

        * 整数和定长字节，必须在两者大小相同、无符号的情况下进行转换

            uint8 x = 0xff;
            bytes2 y = bytes2(x); // 异常，uint8 和 bytes2 大小不一致
            bytes2 z = bytes1(x); //ok 0x00ff
        
        * bytes 转换为定长字节，bytes 过长截断，过短，在末尾添加 0

    # 字面量隐式转换

        * 十进制和十六进制，在不截断的情况下支持隐式转换为 integer，超出范围则会异常
            uint8 a = 12; // 可行
            uint32 b = 1234; // 可行
            uint16 c = 0x123456; // 失败，因为它必须截断为 0x3456
        
        * 十进制字面量不能转换为定长字节，十六进制可以，且要求大小完全符合数组长度

            bytes2 a = 54321; // 十进制，不可以
            bytes2 b = 0x12; // 字节数不匹配，不可以
            bytes2 c = 0x123; // 字节数不匹配，不可以
            bytes2 d = 0x1234; // 可以
            bytes2 e = 0x0012; // 可以
            bytes4 f = 0; // 可以，初始化为 0 值
            bytes4 g = 0x0; // 可以，初始化为 0 值
        
        * 字符串和十六进制字符串，可以转换为定长字节，前提是字节数量小于数组长度

            bytes2 a = hex"1234"; // 两个十六进制字节，可以
            bytes2 b = "xy"; // 两个ascii字节，可以
            bytes2 c = hex"12"; // 一个十六进制字节，小于数组长度，可以
            bytes2 e = "x"; // 一个ascii字节，小于数组长度，可以
            bytes2 f = "xyz"; // 三个ascii字节，大于数组长度，不可以
        