---------------------
Contract
---------------------
	# 合约类型
		* 每个合约都有自己的类型，可以隐式地将合约转换为父合约（多态）。
		
	
	# 合约数据层面（例如赋值、传参、存储）中，它的底层表示与 address 是相同的。
	
		* 合约可以显式地转换为 address 类型和从 address 类型转换。
		* 只有当合约类型具有接收或可支付的回退函数时，才能显式转换为 address payable 类型。
	
			MyContract c = ...;
			payable(c);					// 具有可支付功能的合约，可以直接转换为 payable address
			payable(address(c));		// 没有可支付功能的合约，需要先转换为 address
		
		* 合约类型在 EVM 中的底层表示 = address，因此，它也可以作为 ABI 中的地址字段使用。
		* 地址转换为合约类型
			
			address addr = ...;
			MyContract c = MyContract(addr); // address 必须是与 MyContract 类型匹配的地址。
	

	# 成员
		public 状态
			* 即合约的外部函数，包括任何标记为 public 的状态变量。
		
		this
			* 当前合约，可以转换为 address 类型
		
		super
			* 父级合约
		
		selfdestruct(address payable recipient)
			* 销毁当前合约，将其资金发送到给定的 recipient 并结束执行。 
			* 接收合约的接收函数不会被执行。
			* 合约只在交易结束时被销毁，revert 可能会 “撤销” 销毁。
			* 当前合约的所有函数都可以直接调用。

	# 使用 type(C) 可以访问有关合约的类型信息

	# 使用 New 创建合约
		* 合约可通过 new 关键字创建其他合约。创建合约在编译时必须已知被创建合约的完整代码，因此无法实现递归创建依赖关系。
		* 创建合约的时候可以使用 value modifier 来转账(构造函数必须是 payable 的): new <T>{value: 1 ether}(....), 但是不能用 gas。
		* 合约创建失败（栈溢出、余额不足、GAS 不足），会抛出异常。
	

	# salt 提前计算合约的地址值
		* 默认情况下，合约地址由创建合约的地址与一个计数器（nonce）共同计算得出，该计数器在每次创建合约时递增。
		* 若指定 salt 选项（bytes32 值），则合约创建将采用不同机制生成新合约。
			* 计数器（nonce）会被忽略
			* 根据：创建合约的地址、给定 salt 值、创建合约的字节码（创建阶段）及构造函数参数进行地址计算。

			// SPDX-License-Identifier: GPL-3.0
			pragma solidity >=0.7.0 <0.9.0;
			contract D {
				uint public x;
				constructor(uint a) {		// 合约的构造函数参数
					x = a;
				}
			}

			contract C {
				function createDSalted(bytes32 salt, uint arg) public {
					// 预计算出合约的地址
					address predictedAddress = address(uint160(uint(keccak256(abi.encodePacked(
						bytes1(0xff),		// 1. nonce 为 0xff
						address(this),		// 2. 创建者地址
						salt,				// 3. salt 值
						keccak256(abi.encodePacked( // 4. 被创建的合约的字节码以及构造函数参数值的 keccak256 哈希值
							type(D).creationCode, 	// 被创建合约的字节码
							abi.encode(arg)			// 被创建合约的构造函数参数
						))
					)))));

					D d = new D{salt: salt}(arg);
					require(address(d) == predictedAddress);
				}
			}

		* 合约被销毁后可以在相同的地址，再创建合约。然后就算是新创建的合约和旧的合约的字节码一模一样。但最后部署出来的字节码，可能会有变化。
		* 因为在两次部署期间，外部状态可能发生了变化，构造函数会把这些变化纳入到构造函数中再进行存储。
