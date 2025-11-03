------------------------------
Struct
------------------------------
    # 结构体
        struct Foo {
            string title;
        }

        * 可以在合约外声明，以在多个合约之间共享
        * 在合约内部声明的结构体只能在合约内部、子合约中访问
        * 不能声明空结构体，必须要有成员
        * 结构体可以包含其他结构体，但是不能嵌套包含自己
        * 数组、映射可以存储结构体，结构体也可以包含它们，可以嵌套

            contract Demo {
                struct Foo {
                    string title;
                    Bar bar; // 非法 Recursive struct definition
                    mapping (uint => Bar) bars;  // 合法
                }    
                struct Bar {
                    Zoo zoo;
                }
                struct Zoo {
                    Foo foo;
                }
            }
    
    # 结构体的初始化

        contract Demo  {
            struct Foo {
                int id;
                string title;
            }

            function Bar ()external pure returns(Foo memory) {
                
                // 直接声明类
                Foo memory f;
                f.id = 100;
                f.title = "Hello";

                // 使用 {} 分别指定初始化的各个参数，不能省略
                Foo memory foo = Foo({title: "I", id: 1});

                // 使用 () 依次指定每个参数，不能省略
                foo = Foo(1, "L");
                return foo;
            }
        }

    # 包含 mapping 的结构体
        * 在 Solidity 0.6.x 版本之前，允许创建包含 mapping 的结构体，并且可以当作内存结构体来使用
        * 但是，在把这种结构体赋值给变量的时候，编译器会跳过对 mapping 成员的初始化，可能会导致意外
        * Solidity 0.7.0 开始，如果结构体包含了 mapping 则不允许把它作为内存结构体来使用，会报错

            contract Demo  {
                struct Foo {
                    int id;
                    string title;
                    mapping (address => uint) balance;
                }

                mapping (uint => Foo) foos; 

                function Bar ()external   {

                    // 无法构造包含 mapping 的结构体
                    // Foo memory foo = Foo(1, "t");  // TypeError: Struct containing a (nested) mapping cannot be constructed.
                    
                    // foos 是 storage，包含了 Foo
                    // Foo 已经可以直接访问，不需要初始化
                    foos[1].balance[msg.sender] = 100;
                }
            }