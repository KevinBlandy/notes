-------------------
mapping
-------------------
    # Map 映射
        mapping(<key> <keyNanme?> <value> <valueName?>) m;

        // 实例
        mapping(address account => uint balanace) balanceMap;

        * key 可以是任意类型，除了自定义类型，如：mapping、数组、结构体
        * value 可以是任意类型
        * key 和 value 的名称可以省略
        * 每个 key 都隐式初始化为类型的默认值（如 uint 默认为 0，address 默认为 0x0）。但是映射本身不会存储这些键，它们只存储已被显式赋值的键的值。与哈希表不同，
        * 使用 keccak256 来计算 key 的存储位置，并且这个位置是不可直接访问的，mapping 只记录了与键相关的值的位置，而不存储键本身。
        * 不可枚举、不能获取到 length
        * 只能在 storage 位置，不能作为公开函数的参数或返回值（包括包含了mapping的结构体和数组）
    
    # getter 
        * 把合约的 mapping 声明为 public，sol 会自动创建其 getter 
        * 参数就是key，返回值就是value（如果有keyName和valueName，那么就是 getter 中的参数和返回值名称）
        
            // 定义 
            mapping(address account => uint balanace) public balanceMap;
            // 外部访问
            balanaceMap(0x5B38Da6a701c568545dCfcB03FcB875f56beddC4);
        
        

    

