----------------------
代理合约
----------------------
    # 合约部署后就不可以更改了，可以通过代理的方式来实现升级。

    # 通过 delegatecall 可以实现代理调用

        https://github.com/OpenZeppelin/openzeppelin-contracts/blob/master/contracts/proxy/Proxy.sol

        * 核心原理就是，逻辑和业务独立实现，在代理合约中通过 delegatecall 来实现对逻辑的调用
        * 在代理合约中，仅仅只需要维护逻辑实现合约的地址即可

----------------------
示例
----------------------
// SPDX-License-Identifier: GPL-3.0

pragma solidity ^0.8.0;

import { Proxy } from "https://github.com/OpenZeppelin/openzeppelin-contracts/blob/master/contracts/proxy/Proxy.sol";

// 代理合约
contract App is Proxy {
     address impl;  // 代理地址，当需要更改逻辑的时候，修改这个状态变量即可
     constructor(address _impl){
         impl = _impl; // 构建合约的时候，初始化代理地址
     }
     function _implementation() internal view override  virtual returns (address){
          return impl;  // 实现 Proxy 中的唯一抽象方法，返回代理地址
     }
}

// 实现业务逻辑的合约
contract Logic {
     address impl; // 代理地址，保持和代理合约相同的布局
    /*
        Proxy 合约没声明过这个变量，即 Proxy 合约的 storage 域所对应位置值为 0）
        可以理解为 Proxy 合约的 x 变量的值是0，所以通过 Proxy 调用 increment() 会对自身这个位置的值进行 + 1
    */
     uint val;  // 状态变量，逻辑需要  
     function incre () external returns(uint){
          return ++val; // 修改状态变量后返回
     }
}

// 调用者合约
contract Caller {
     function callApp(address app) external returns(uint){
          // 传入的是 Proxy App 的合约地址，调用代理合约
          return Logic(app).incre();

          // 通过 call 调用目标合约
          // (, bytes memory data) = app.call(abi.encodeWithSignature("incre()"));
          // // 解码返回值
          // (uint ret) =  abi.decode(data, (uint));
          // // 返回
          // return ret;
     } 
}



----------------------
Proxy 源码
----------------------
// SPDX-License-Identifier: MIT
// OpenZeppelin Contracts (last updated v5.5.0) (proxy/Proxy.sol)

pragma solidity ^0.8.20;

/**
 * @dev This abstract contract provides a fallback function that delegates all calls to another contract using the EVM
 * instruction `delegatecall`. We refer to the second contract as the _implementation_ behind the proxy, and it has to
 * be specified by overriding the virtual {_implementation} function.
 *
 * Additionally, delegation to the implementation can be triggered manually through the {_fallback} function, or to a
 * different contract through the {_delegate} function.
 *
 * The success and return data of the delegated call will be returned back to the caller of the proxy.
 */
abstract contract Proxy {
    /**
     * @dev Delegates the current call to `implementation`.
     *
     * This function does not return to its internal call site, it will return directly to the external caller.
     */
    function _delegate(address implementation) internal virtual {
        assembly {
            // Copy msg.data. We take full control of memory in this inline assembly
            // block because it will not return to Solidity code. We overwrite the
            // Solidity scratch pad at memory position 0.
            calldatacopy(0x00, 0x00, calldatasize())

            // Call the implementation.
            // out and outsize are 0 because we don't know the size yet.
            let result := delegatecall(gas(), implementation, 0x00, calldatasize(), 0x00, 0x00)

            // Copy the returned data.
            returndatacopy(0x00, 0x00, returndatasize())

            switch result
            // delegatecall returns 0 on error.
            case 0 {
                revert(0x00, returndatasize())
            }
            default {
                return(0x00, returndatasize())
            }
        }
    }

    /**
     * @dev This is a virtual function that should be overridden so it returns the address to which the fallback
     * function and {_fallback} should delegate.
     */
    function _implementation() internal view virtual returns (address);

    /**
     * @dev Delegates the current call to the address returned by `_implementation()`.
     *
     * This function does not return to its internal call site, it will return directly to the external caller.
     */
    function _fallback() internal virtual {
        _delegate(_implementation());
    }

    /**
     * @dev Fallback function that delegates calls to the address returned by `_implementation()`. Will run if no other
     * function in the contract matches the call data.
     */
    fallback() external payable virtual {
        _fallback();
    }
}