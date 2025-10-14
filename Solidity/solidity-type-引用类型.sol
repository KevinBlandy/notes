-------------------------
引用类型
-------------------------
	# 值类型在进行传递的时候会复制副本，引用类型则传递的是引用
		
		struct
		数组
		mapping
	
	# 数据区域
		
		* 使用引用类型，必须明确提供存储该类型的数据区域

			memory
				* 函数运行时的临时存储，存在于 EVM 的内存（RAM）中。
				* 函数结束后自动释放。

			calldata
				* 存在于交易输入数据（calldata）中，只读，不能修改。
				* calldata 只能引用现有的、从外部传入的数组。
				* 比 memory 更省 Gas，因为不会复制数组内容。

			storage
				* 持久化的链上存储，位于区块链的永久存储中，写入非常贵（需要消耗 Gas）。
				* 合约成员默认为 storage，这是唯一一个可以省略数据区域关键字的地方

				* 局部 storage 引用变量只能指向已有的 storage 对象。

				* Solidity 中只有复合类型（数组、结构体、映射）才支持 storage 引用。
				* 对值类型 (uint, bool, address 等) 不允许声明 storage 引用变量，

					contract C {
						uint x = 42;
						function f() public {
							// 编译错误
							// “对值类型的引用” 没意义，因为对它的操作永远只是修改值，而不是操作复合数据结构。
							// 修改 y 不会影响 x
							uint storage y = x; 
						}
					}


		* 任何 “改变” 数据位置的赋值或类型转换都会自动引发复制操作
			
			赋值方向			行为		可修改性	成本	用途
			---------------------------------------------------------
			storage ← storage	引用		共享		低		修改状态
			storage ← memory	拷贝		独立		高		写入链上
			storage ← calldata	拷贝		独立		高		外部输入上链
			memory ← storage	拷贝		独立		中		临时读
			memory ← calldata	拷贝		独立		中		外部输入临时使用
			calldata ← *		❌ 不允许	—			—		—

		* 在同一数据位置内的赋值仅在某些情况下会对存储类型进行复制
			
			* 局部 storage << >> 局部 storage -> 引用
				contract Demo{
					uint[] arr;
					function foo() public   {
						// y 和 x 是引用，指向同一个 arr
						uint[] storage x = arr;
						uint[] storage y = x;
						y[0] = 123; 
					}
	 
				}

			* 状态变量（storage）<< 任何 -> 复制

				contract Demo{
					uint[] x;
					uint[] y;
					function foo() public   {
						// 复制 y 的值到 x
						x = y;
					}
				}


	