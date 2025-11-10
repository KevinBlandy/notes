------------------------
错误处理
------------------------
    # 异常
        * SOL 使用状态回滚来处理异常，这会撤销当前调用，以及所有子调用的状态更改，并给调用方返回错误
        * 千万要注意的是低级调用：call、delegatecall 和 staticcall 如果出现异常只是返回 false 而不会抛出异常（目标地址不存在，也会返回 true）
        * 异常可以包含一个描述错误信息的实例，有两个内置的异常，由特殊函数使用：

            Error(string) 用于处理 “常规” 错误情况
            Panic(uint256) 用于处理在无错误代码中不应出现的错误。

    # assert(bool)
        * 会创建一个类型为 Panic(uint256) 的异常，应该仅用于测试内部错误，失败会消耗掉所有 gas（版本差异）。
        * 一般的业务代码不应该创建 Panic。
        * 以下情况将触发Panic异常

            0x00：用于通用编译器插入的 Panic。
            0x01：当调用 assert 时，其参数评估结果为false。
            0x11：在 unchecked {...} 代码块外，算术运算导致下溢或溢出。
            0x12：进行除零或取模零操作时（如 5 / 0 或 23 % 0）。
            0x21：将过大或负值转换为枚举类型时。
            0x22：访问编码错误的存储字节数组时。
            0x31：对空数组调用 .pop() 方法时。
            0x32: 访问数组、bytesN 或数组切片时索引超界或为负值（即 x[i] 中 i >= x.length 或 i < 0）。
            0x41: 分配过多内存或创建过大数组。
            0x51: 调用内部函数类型的零初始化变量。

    # require 
        * 一般用于验证外部条件是否满足，如果不满足，则回滚交易并退还 gas
        * 有三个重载
            require(bool)   
                * 如果条件不通过，直接回滚，连错误信息都不给。
            require(bool, string)
                * 如果条件不通过，返回包含错误字符串的 Error 对象。
                * 返回的数据即 abi 编码后的 Error(string) 调用。
            require(bool, error)
                * 如果条件不通过，返回自定义的 Error 对象（第二个参数）。
        
        * require 的参数始终都会执行
            require(<condition>, foo()); // 无论 condition 的结果是什么， foo 都会执行

        
        * 在以下情况，编译器会生成一个 Error(string) 异常

            * 调用 require(x) 时，x 评估结果为 false。
            * 使用 revert() 或 revert("description") 时。
            * 调用外部函数时，目标合约内未包含任何代码。
            * 合约通过未添加 payable 修饰符的公共函数接收以太币（包括构造函数和 fallback 函数）。
            * 合约通过 public getter 函数接收以太币。
    
    # revert
        * 用来执行回滚，并且返回错误信息
        * 有两个重载方法
            revert()
                * 中止执行并回滚状态更改

            revert(string memory reason)
                * 中止执行并回滚状态更改，提供字符串信息
                * 字符串信息会被 ABI 编码为对函数 Error(string) 的调用，即创建 Error(string) 错误实例
        
        * 甚至可以直接接受一个自定义 Error 作为参数，而不需要 () 调用声明  
            revert MyError("fuck off");
        



    # try-catch 语句
        * 支持在 external （外部调用）以及合约创建调用中使用 try - catch 语句

            // 外部调用
            try this.bar() returns(uint v) {
                // TODO 在这里获取到返回值 v ，进行业务处理
            } catch Error(string memory reason){
                // 捕获 revert/require，类似于 go 的 Error
            } catch Panic(uint errorCode){
                // 捕获 assert / 除 0 等异常，类似于 go 的 panic
            } catch (bytes memory lowLevelData) {
                // 捕获最低级的字节码，通用的
            } catch {
                // 不需要错误信息，通用的
            }
        
        * try 后必须是外部函数调用或者是合约创建语句
            try new MyContract() returns (MyContract deployed) {}

        * returns 可选，声明和外部调用一致的返回值

        * 如果 try 表达式比较复杂，例如包含了嵌套调用，那些 “内部” 的异常不能被捕获，只能捕获 “外部” 调用过程中的异常
            try foo(bar()) {} // 只能捕获到 foo 调用中的异常，bar 调用的异常捕获不到
        
        * returns 和 catch 子句中声明的变量仅在后面的块中有效。

        * try 代码块中出现了异常，不会被当前的 catch 捕获，因为这是当前合约的错误，不是外部调用的。
            
            try foo() returns(uint) {
                // 例如，如果 foo 返回的是 string，这里确写的 uint，则会异常而不会被捕获
            }
        
        * catch 中的参数，如果解码失败，则会尝试进入 catch {} 代码块，即低级的 catch/catch(bytes memory raw) 代码块
            catch Error(string memory reason) {
                // 解码 reason 异常，会尝试进入更低级的catcha块 
            }
        
        * 一旦进入了 catch 块，则表示 try 调用已经回滚了状态，如果没有匹配的 catch 块来处理异常，那么整个 try/catch 都会回滚
        * catch 到的异常也不一定是来自于目标合约，也可能来自于目标合约的下游调用
        * 出现异常，也不一定就是程序抛出的，也可能是 gas 耗尽，调用方在执行外部调用的时候，EVM 通常会保留 1/64 的 gas，来保证出现异常后 catch 块可以被执行
    
    # 自定义 error
        * 使用 error 关键字定义，可以包含多个参数

            /// 转账余额不足。需要 `required` 但只有 `available` 可用。
            /// @param available 可用余额。
            /// @param required 请求转账的金额。
            error InsufficientBalance(uint256 available, uint256 required);
        
        * 不支持重载，不支持覆写，支持继承

        * 必须和 require 或 revert 一起使用，返回错误信息给调用者，并且回滚

            // condition 为 false, 抛出异常
            require(<condition>, InsufficientBalance(100, 1000));

            // 直接抛出异常
            revert InsufficientBalance(100, 1000);
        
        
        * 错误数据
            * 前 4 字节是签名，如果不提供错误参数，那么错误信息只占 4 字节。
            * 自定义错误和函数使用相同的 ABI 机制，但是数据不会上链。

    
    # 成员
        selector
            * 返回错误的签名，bytes4
            * 但是代码里面没法访问？


