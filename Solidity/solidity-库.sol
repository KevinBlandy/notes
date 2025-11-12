-----------------------
库
-----------------------
    # 库
        * 库类似于合约，但其设计目的是仅在特定地址部署一次，并通过 EVM 的 DELEGATECALL 功能复用代码。
        * 这意味着当调用库函数时，其代码将在调用合约的上下文中执行——即 this 指向调用合约，尤其能访问调用合约的存储空间。
        * 由于库是独立的源代码片段，除非显式提供，否则它无法访问调用合约的状态变量。
        * 若库函数不修改状态（即为 view 函数或 pure 函数），则可直接调用（无需使用 DELEGATECALL），因为库被视为无状态。
        * 不能有状态变量，不能继承，也不能被继承，不能接收以太币，不能被销毁。
    
    # 函数签名和 selector

        * 虽然可以调用 public 和 external 库函数，但这类调用是 SOL 的内部机制
        * 与常规合约 ABI 规范不同。external 库函数支持比 external 合约函数更丰富的参数类型，例如递归结构体和存储指针。
        * 因此，用于计算 4 字节选择器的函数签名遵循 internal 命名规范，对于合约 ABI 不支持的参数类型则采用 internal 编码方案。
        * 参数编码与常规合约 ABI 相同。
    
    # Using For
        * 使用 A for B 指令可将 A 作为运算符附加到用户定义的值类型上，或作为成员函数附加到任意类型 B上。、
        * 成员函数将被调用的对象作为其第一个参数接收（类似于 Python 中的 self 变量）。运算符函数则将操作数作为参数接收。
        
            library Foo{
                function double (uint self)public pure returns (uint) {
                    return self * self;
                }
                
                function repeat(string memory self, uint count) public  pure returns(string memory){
                    string memory ret;      
                    for (uint i = 0; i < count; i ++) {
                        ret = string.concat(ret, self);
                    } 
                    return ret;
                }
            }

        * 可在文件级别使用
            * B 必须显示指定类型，不能包含位置说明符
            
                using  Foo for uint;            // 绑定 Foo 中的 uint 方法到 uint
                using { Foo.repeat } for string; // 只绑定 repeat 方法到 string

        * 可在合约内部以合约级别使用
            * 可以使用 * 代替 B ，表示把 A 附加到所有类型上

                contract Test {
                    using Foo for *; // Foo 库中的定义的所有方法，其类型都在这里生效
                    function test(uint x, string  memory str) public pure returns (uint, string memory) {
                        return (x.double(), str.repeat(x));
                    }
                }

        * A 可以是一个库

            * 该库中所有非私有函数均会被绑定，即使首参数类型与对象类型不匹配。类型检查在函数调用时进行，并执行函数重载解析。

        * A 可以是一个/组函数：
            
            using { Foo.double } for uint;

            using { f, g as +, h, L.t } for B;

            * 如果是一组函数，那么类型 B 必须可以隐式地转换为这组函数中的第一个参数，即使某些函数没被调用，也会执行这个检查。
            * 如果附加的函数是 private 的，那么 using for 只能在这个库/合约内部指定。
    
            * 没有为函数指定运算符，那么该函数可以是：

                * 库函数：即定义在外部库中的函数，合约可以调用这些库函数。
                * 自由函数：即不属于任何特定合约或类型的独立函数，但可以在全局范围内或合约内部调用。

            * 指定了运算符时：如果指定了某个运算符（通过 as 指定），则：

                * 该函数必须是 “自由函数”（即不能是库函数，也不能是成员函数）。
                * 该函数将作为指定运算符在类型上的 “操作函数”，即定义了运算符的行为。
                * 类型 B 必须是用户自定义的值类型，附加的函数必须是 pure 类型的，并且运算符定义必须是全局的。

                    type Number is uint; // 自定义类型

                    function add (Number v1, Number v2) pure returns(Number){ // 自由函数 
                        return Number.wrap(Number.unwrap(v1)+ Number.unwrap(v2));
                    }

                    using {add as + } for Number global; // 自定义 + 操作

                    contract Test {
                        function test(Number x, Number y) public pure returns (uint) {
                            return Number.unwrap(x + y); // 测试
                        }
                    } 
        
                * 运算符定义，必须是全局的，可定义的运算符如下：
            
                    * 位运算
                        &   function (T, T) pure returns (T)
                        |   function (T, T) pure returns (T)
                        ^   function (T, T) pure returns (T)
                        ~   function (T) pure returns (T)

                    * 数学
                        +   function (T, T) pure returns (T)
                        -   function (T, T) pure returns (T)
                            function (T) pure returns (T)
                        *   function (T, T) pure returns (T)
                        /   function (T, T) pure returns (T)
                        %   function (T, T) pure returns (T)


                    * 比较
                        ==  function (T, T) pure returns (bool)
                        !=  function (T, T) pure returns (bool)
                        <   function (T, T) pure returns (bool)
                        <=  function (T, T) pure returns (bool)
                        >   function (T, T) pure returns (bool)
                        >=  function (T, T) pure returns (bool)
                    
                    * 注意，一元运算符和二元运算符需要分别定义。编译器将根据运算符的调用方式选择正确的定义。

        * 指令默认仅在当前作用域内有效（合约/源文件）
            * 当该指令在文件级别使用，且应用于同一文件中定义的用户自定义类型时，可在末尾添加 global 关键字。
            * 此操作将使函数和运算符在类型可用的所有位置（包括其他文件）都与该类型关联，而不仅限于使用语句的作用域内。


