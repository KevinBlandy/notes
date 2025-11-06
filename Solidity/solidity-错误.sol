------------------------
错误处理
------------------------
    # try-catch 语句
        * 支持在 external 以及合约创建调用中使用 try {} catch(){} 语句


    # 处理函数

        assert(bool condition)
            * 如果条件不满足，则会导致 Panic 错误，从而使状态更改回滚 - 用于内部错误。

        require(bool condition)
            * 如果条件不满足，则回滚 - 用于输入或外部组件的错误。

        require(bool condition, string memory message)
            * 如果条件不满足，则回滚 - 用于输入或外部组件的错误。还提供错误消息
    
        revert()
            * 中止执行并回滚状态更改

        revert(string memory reason)
            * 中止执行并回滚状态更改，提供解释字符串
