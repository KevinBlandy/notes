
# Hash一致性算法
	* 算法目的:将数据均匀的分散到各个节点中,并且尽量的在加减节点时能使受影响的数据最少

	* 客户端在N多服务端节点中选择一个进行连接
	* 普通的 hash(key) % nodes.size 会带来一些问题
		* 当节点新增,删除后,会有大量的客户端会被重新分配

	* 一致 Hash 算法是将所有的哈希值构成了一个环,其范围在 0 ~ 2^32-1(顺时针方向)
		* 0 - 4294967295
		* 可以用节点的 IP,hostname 这样的唯一性字段作为 Key 进行 hash(key)
		* 把节点散步到哈希环上
	
	* 之后需要将数据定位到对应的节点上,使用同样的 hash 函数 将 Key 也映射到这个环上
		* 遍历这个数组直到找到一个数据大于等于当前客户端的 hash 值,就将当前节点作为该客户端所路由的节点
		* 如果没有发现比客户端大的数据就返回第一个节点(满足环的特性)
		* 顺时针方向
	
	* 使用虚拟节点,使用数据分布更加的均匀
		* 将每一个节点都进行多次 hash,生成多个节点放置在环上称为虚拟节点
		* 可以在 IP 后加上编号来生成哈希值

# Java Demo

import java.util.SortedMap;
import java.util.TreeMap;


public class HashDemo {
	public static void main(String[] args) {
		System.out.println(consistentHashAlgorithmVirtual());
	}
	
	// 虚拟节点,解决环行圈儿中分布不均的问题
	public static String consistentHashAlgorithmVirtual() {
		// 每个节点的虚拟节点数量
		int virtualCount = 5;
		// 节点容器
		TreeMap<Integer,String> treeMap = new TreeMap<>();
		// 节点
		String[] servers = new String[] {
			"192.168.0.1:1024",
			"192.168.0.2:1024",
			"192.168.0.3:1024",
			"192.168.0.4:1024",
			"192.168.0.5:1024"
		};
		for(String server : servers) {
			// 为每个节点生成虚拟节点
			for(int x = 0 ;x < virtualCount ; x ++) {
				String finalName = server + "&&" + x;
				// 计算出每个节点的hash
				int hashCode = Math.abs(finalName.hashCode());
				treeMap.put(hashCode, finalName);
				System.out.println("hashCode=" + hashCode +   " server=" + finalName);
			}
		}

		// 客户端表标识以及hashCode
		String key = "client";
		int hashCode = Math.abs(key.hashCode());
		
		System.out.println("hashCode=" + hashCode);
		
		// 大于该hash的map
		SortedMap<Integer, String> tailMap = treeMap.tailMap(hashCode);
		if(!tailMap.isEmpty()) {
			// 有，则使用第一个(大于该hash的第一个)
			return tailMap.get(tailMap.firstKey());
		}else {
			//没则使用所有节点的第一个
			return treeMap.get(treeMap.firstKey());
		}
	}
	
	public static String consistentHashAlgorithm() {
		// 节点容器
		TreeMap<Integer,String> treeMap = new TreeMap<>();
		// 节点
		String[] servers = new String[] {
			"192.168.0.1:1024",
			"192.168.0.2:1024",
			"192.168.0.3:1024",
			"192.168.0.4:1024",
			"192.168.0.5:1024"
		};
		for(String server : servers) {
			int hashCode = Math.abs(server.hashCode());
			treeMap.put(hashCode, server);
			// 计算出每个节点的hash
			System.out.println("hashCode=" + hashCode +   " server=" + server);
		}

		// 客户端表标识以及hashCode
		String key = "client";
		int hashCode = Math.abs(key.hashCode());
		
		System.out.println("hashCode=" + hashCode);
		
		
		// 大于该hash的map
		SortedMap<Integer, String> tailMap = treeMap.tailMap(hashCode);
		if(!tailMap.isEmpty()) {
			// 有，则使用第一个(大于该hash的第一个)
			return tailMap.get(tailMap.firstKey());
		}else {
			//没则使用所有节点的第一个
			return treeMap.get(treeMap.firstKey());
		}
	}
}
