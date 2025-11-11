-----------------------
继承
-----------------------
    # 使用 is 继承合约

        contract Bar {}
        contract Foo {}
        contract Demo is Foo, Bar {}

        * 支持多继承和多态
        * 继承的父合约，会被编译到当前的合约中，这意味着对父合约函数的所有 internal 调用均采用 internal 函数调用（super.f(..) 将使用 JUMP 指令而非消息调用）。
        * 整个继承体系不允许变量覆盖，即不能出现同名且状态可见的元素（只要父类的元素可见，那么子类中就不能出现相同名称的，哪怕子类中是 private 的）。
    
    # 继承的规则
        * 关键的是 is 指令中基类的排列顺序：必须按 “最基础”（远） 到 “最派生”（近） 的顺序列出直接基类合约。

            contract A {}
            contract B is A {}

            contract C is A, B {} // 没问题，先远，后近
            contract D is B, A {} // 有问题，先近，后远。异常：Linearization of inheritance graph impossible

            // D 要求 A 覆盖 B（通过指定 B, A 顺序），但 B 本身又要求覆盖 A，这形成了无法化解的矛盾。

    # 构造函数
        * 如果父类有构造函数，则需要在继承时传递参数，有两种方式（不同同时用）：

            // 构造函数带参数的父类
            contract Bar {
                constructor(string memory name){}
            }
            // 声明时时候，传递参数
            contract Demo is Bar("Demo") {}

            // 在构造时，传递参数，可以读取到构造函数中的参数以及当前合约中的状态变量
            contract Simple is Bar {
                constructor(string memory title) Bar(title){
                }
            }
        
        * 如果子类，没有为所有父类的构造函数都提供参数，那么子类必须声明为 abstract，即抽象的。
        * 其他类继承当前类的时候，必须补齐缺少的构造函数，否则这个类也是个抽象类
            abstract contract A {
                constructor(string memory title){ }
            }
            abstract contract B is A {} // 继承 A 但是不初始化构造函数，为抽象类
            contract C is B {
                constructor() A("default"){} // 继承抽象类 B，补齐 B 继承 A 时缺少的构造函数
            }
        
        * 构造函数的初始化顺序是和线性顺序一致，和构造函数中声明的初始化顺序无关

    # 方法覆写

        * 使用 virtual 关键字声明方法，表示该方法可以被子类覆写。
        * 子类覆写的时候，使用 override 修饰
        
            contract Foo  {
                // 可被覆写
                function func() virtual internal  pure {}
            }
            contract Demo is Foo, Bar {
                // 覆写
                function func() override  internal  pure {}
            }
        
        * 覆写的函数只能将被父类函数的可见性从 external 更改为 public，可见性为 private 的不能声明 virtual
        * 可变性可以按照以下顺序更改为更严格的：
            * nonpayable 可以被 view 和 pure 重写。 
            * view 可以被 pure 重写。 
            * payable 不可被重写。

        * 如果希望被覆写的方法， 还能继续被子类覆写，则需要再次添加 virtual 关键字
            function func() override virtual internal pure {}

        * 对于多重继承，必须在 override 中定义相同函数的 “最派生” 基类合约

            contract Bar {
                function func() virtual external pure {}
            }
            contract Foo  {
                function func() virtual external pure {}
            }
            contract Demo is Foo, Bar{
                function func() override(Foo, Bar) virtual public  pure  {}
            }   

            * 在继承链上，凡是还没有被别的合约重写掉的那个同名函数版本，都要在 override(...) 的括号里显式列出
            * 即指定所有定义相同函数的 Base 合约并且尚未被另一个基合约重写（在继承图的某些路径上）。 
            * 此外，继承的两个父合约彼此没有继承关系（即“无关的基类”），但它们都定义了一个同名函数，那么在派生合约中，必须自己显式地重写它，不能依赖 Solidity 自动帮你决定使用哪个。
            * 原则：只有在函数定义存在多条潜在冲突路径时，才必须显式写 override(...)。
        
        * 可以用 public 成员生成的 getter 方法来进行覆写，只要符合规范即可
        * 但是生成的 getter 方法，不能被重写
            abstract contract  Foo  {
                function balance(address) virtual external view  returns (uint);
            }
            contract Demo is Foo{
                // 用 getter 来覆写父类中的方法
                mapping(address => uint)  public override balance;
            }
    
    # 访问父类状态成员
        * 可以直接访问，或通过父类名称明确访问
            // 访问父类中属性
            string memory title = Foo.title;

    # 访问父类函数成员
        * 直接访问，或者是通过父类名称访问

            // 调用的是从父类继承到自身合约的方法
            // 这个函数一定是整个继承链上唯一的方法，所以加不加父类名称都无所谓
            fn(); 
            Parent.fn();

        * 使用 super 根据线性化继承顺序，找到 “下一个” 要执行的父合约函数。
            // 需要调用到继承链上，某个父类合约的这个 fn 方法
            super.fn();
        
        * demo
            contract A {
                function call() virtual  internal  pure returns(string memory) {
                    return "A";
                }
            }
            contract B is A {
                function call() override internal pure  returns(string memory) {
                    // call(); 这是调用自身的 call，自身覆写了 call，在当前环境下，即递归
                    return super.call(); // 调用的是，继承链上最近的 call 方法，即 A.call
                }
            }
            contract C is A {
                function foo()  public pure returns(string memory) {
                    call();  // 这是调用自身的 call，自身并没覆写 call，所以最终执行的还是 A.call
                    return super.call(); // 调用的是，继承链上最近的 call 方法，即 A.call
                }
            }

    # modifier 覆写
        * 与函数覆写类似，需要声明 virtual 和 override。
        * 多重继承下，必须明确指定所有 Base 合约

            contract A {
                modifier Foo() virtual {_;}
            }
            contract B is A {
                // 单继承，覆写
                modifier Foo() override virtual {_;}
            }
            contract C is A,B {
                // 多继承，覆写
                modifier Foo() override(A, B)  virtual {_;}
            }

    # SOl 的继承线性化顺序（Linearization）
        * Solidity 会根据继承线性化顺序（C3 Linearization） 来确定函数调用的顺序。
        * “最派生合约” 是线性化顺序中的 起点（最顶层）；它是最终部署、执行的合约。
        * 所有 super 调用、构造器执行，都是从它开始向上解析，它并不代表某个父类，而是继承链。
        * 调用 super 的时候，按照线性顺序往上找，找到就执行。如果上级所有调用方法里都带了 super() 调用，那么会完整的执行整个继承链。
        * 另一种简化解释是：
            * 当调用在不同合约中多次定义的函数时，系统会以深度优先方式从右向左（Python中为左向右）遍历给定基类，首次匹配即停止。
            * 若某基类合约已被检索过，则直接跳过。



-----------------------
抽象合约
-----------------------
	# 抽象合约的定义
		* 使用 abstract 关键字来定义抽象合约。
		* 如果合约里面，有一个以上的为实现的函数或者是未给父合约的构造函数提供参数，则当前合约必须标识为抽象的。
		* 包含了完整实现方法的普通合约也可以标记为抽象。
		* 如果构造函数中包含了 storage 引用参数，也必须要声明为抽象合约。
		* 抽象合约不能创建实力，只能是作为父合约，被继承后创建实例。
	
	# TODO 抽象合约也可以继承自其他合约

	# TODO 抽象合约不能用未实现的函数覆盖已实现的虚函数
