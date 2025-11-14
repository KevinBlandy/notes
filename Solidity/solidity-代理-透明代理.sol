-------------------------
透明代理
-------------------------
    # 代理合约可能存在的问题：selector 冲突

        * 两个不同方法签名，产生了相同的 hash（毕竟只有 4 字节，冲突的概率还是很大）。
        * 例如：burn(uint256) 和 collate_propagate_storage(bytes16) 的 selector 都为 0x42966c68。
        * 在这种情况下，EVM无法通过函数选择器分辨用户调用哪个函数，因此该合约无法通过编译。

            // Function signature hash collision for collate_propagate_storage(bytes16)
            contract Demo {
                function burn(uint256) external {}
                function collate_propagate_storage(bytes16) external {}
            }
        

        * 如果代理合约和实现合约中，存在 selector 冲突的方法，这会导致意想不到的后果。
        * 明明目的是调用实现合约的方法，但是确调用到了代理合约的方法，特别是和代理合约的 “升级” 方法冲突，更是灾难。
    
    # 透明代理实现方案

        * 限制代理合约中方法调用人：只能是管理员调用
            require(msg.sender == admin);

        * 限制逻辑合约中方法调用人：只能是非管理员调用
            require(msg.sender != admin);
        
        * 缺点：每次用户调用函数时，都会多一步是否为管理员的检查，消耗更多 gas

