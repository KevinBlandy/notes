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



