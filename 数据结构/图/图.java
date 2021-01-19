---------------------------
图						   |
---------------------------
	# 为什么要有图
		1. 线性表局限于一个直接前驱和一个直接后继的关系
		2. 树也只能有一个直接前驱(也就是一个父节点)
		3. 当需要表示多对多的关系时, 就用到了图
	
	
	# 图
		* 它是一种数据结构, 节点可以具有0个或者多个相邻的元素
		* 两个节点之间的连接成为边, 节点也可以成为定点
	
	# 图的概念
		1 顶点
			* 某个节点
		2 边
			* 某个节点和另一个节点的关联
		3 路径
			* 从一个节点到另一个节点需要经过的节点和边
		4 无向图
			* 节点和节点之间的连接没有方向的概念
			* 可以是 A -> B 也可以是 B -> A
		5 有向图
			* 节点与节点之间的连接有方向
			* 只能是 A -> B 不能是 B -> A
		6 带权图
			* 节点与节点之间的连接(边), 带有权值
			* 例如, 可以用边的长度来表示权值
		
	# 图的表示, 有两种
		1. 二维数组表示(邻接矩阵)
			* 表示图形中顶点之间相邻关系的矩阵
			* 对于N个顶点的图而言, 矩阵的row和col表示的是1...N个点、
			
			* 通俗理解就是把图中的所有节点, 都以行, 列形式形成一个二维数组
			* 它们对应的关系: 0表示无关系, 1表示有关系(可以直连)
				  0 1 2 3 4 5
				0 0 1 1 1 1 0 
				1
				2
				3
				4
				5

				* 图的节点有: 0,1,2,3,4,5
				* 拿第一行来说, 表示0和0节点没有关系(自己与自己是0), 0和1节点有关系(可以直接连接到)
				* 以此类推
			



		2. 链表表示(邻接表)
			* 邻接矩阵需要为每个节点都分配n个边的空间, 其实有很多边都是不存在的, 会造成空间的一定损失
			* 邻接表的实现, 只关心存在的边, 不关心不存在的边, 因此没有空间浪费
			* 邻接表由数组 + 链表组成

				0 -> 1 -> 1 -> 2 -> 3 -> 4  // 标号为0的节点和关联的节点为 1 2 3 4
				1 -> 0 -> 4					// 标号为1的节点的相关联节点为 0 4
				2 -> 0 -> 4 -> 5			// 标号为2的节点相关联的节点为 0 4 5
				3 -> 0 -> 5
				4 -> 0 -> 1 -> 2 -> 5
				5 -> 2 -> 3 -> 4
			
			* 只有可以直连的节点, 才会加入链表
		





---------------------------
Java创建图				   |
---------------------------

import java.util.ArrayList;
import java.util.Arrays;

public class Graph {
	
	// 存储顶点
	private ArrayList<String> vertex;
	
	// 图的邻接矩阵
	private int[][] edges;
	
	// 边的数目
	private int numberOfEdge;
	
	/**
	 * 顶点个数
	 * @param numberOfVertex
	 */
	public Graph(int numberOfVertex) {
		// 初始化edges和vertex、
		this.edges = new int[numberOfVertex][numberOfVertex];
		this.vertex = new ArrayList<String>(numberOfVertex);
		this.numberOfEdge = 0;
	}
	
	/**
	 * 插入节点
	 * @param vertex
	 */
	public void insertVertex(String vertex) {
		this.vertex.add(vertex);
	}
	
	/**
	 * 添加边
	 * @param v1	顶点的下标
	 * @param v2	顶点的下标
	 * @param wight
	 */
	public void insertEdge(int v1, int v2, int wight) {
		this.edges[v1][v2] = wight;
		this.edges[v2][v1] = wight; // 无向图
		this.numberOfEdge ++;
	}
	
	/**
	 * 返回顶点的数目
	 * @return
	 */
	public int numberOfVertex() {
		return this.vertex.size();
	}

	/**
	 * 返回边的数目
	 * @return
	 */
	public int numberOfEdge() {
		return this.numberOfEdge;
	}
	
	/**
	 * 返回指定节点对应的数据
	 * @param index
	 * @return
	 */
	public String getValueByIndex(int index) {
		return this.vertex.get(index);
	}
	
	/**
	 * 获取两个节点的权值
	 * @param v1
	 * @param v2
	 * @return
	 */
	public int getWight(int v1, int v2) {
		return this.edges[v1][v2];
	}
	
	/**
	 * 打印图结构
	 */
	public void showGraph() {
		for (int[] link : this.edges) {
			System.out.println(Arrays.toString(link));
		}
	}
	
	public static void main(String[] args) {
		
		// 预定义节点
		String[] vertexVal = {"A", "B", "C", "D", "E"};
		
		Graph graph = new Graph(vertexVal.length);
		
		// 初始化节点
		for (String vertex : vertexVal) {
			graph.insertVertex(vertex);
		}
		
		
		// 创建关系
		graph.insertEdge(0, 1, 1);
		graph.insertEdge(0, 2, 1);
		graph.insertEdge(1, 2, 1);
		graph.insertEdge(1, 3, 1);
		graph.insertEdge(1, 4, 1);
		
		// 显示图的关系
		graph.showGraph();
	}
}
