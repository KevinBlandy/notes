------------------------
深度优先的遍历			|
------------------------
	# 深度遍历
		* 纵向深入, 这是一个地柜的过程
	
	# 算法步骤
		1. 访问初始节点v, 并且标记为已访问
		2. 查找节点v的第一个邻接节点w
		3. 如果w存在, 执行4, 如果不存在, 返回1, 从v的下一个节点继续
		4. 如果w未被访问, 对w进行深度优先遍历递归(把w当作v, 重复执行123)
		5. 查找节点w的邻接节点的下一个邻接节点, 转到步骤3

------------------------
广度优先的遍历			|
------------------------
	# 广度遍历
		* 分层搜索, 需要使用一个队列来维护访问过的节点熟顺序
		* 以便按这个顺序来访问这写节点的邻接节点
	
	# 算法步骤
		1.  访问初始节点v, 并且标记为已访问
		2.  节点v入队列
		3.  队列非空, 继续执行, 否则算法结束
		4.  出队列, 取得头节点u
		5.  查找节点u的第一个邻接节点w
		6.  如果节点u的邻接节点w不存在, 则重复步骤3, 否则执行下3个步骤
		6.1 如果节点w未被访问, 则标记w节点已经被访问
		6.2 节点w入队列
		6.3 查询节点u的继w邻接节点后的下一个邻接节点w, 重复步骤6

------------------------
代码实现				|
------------------------
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Graph {
	
	// 存储顶点
	private ArrayList<String> vertex;
	
	// 图的邻接矩阵
	private int[][] edges;
	
	// 边的数目
	private int numberOfEdge;
	
	private boolean[] visited;
	
	/**
	 * 顶点个数
	 * @param numberOfVertex
	 */
	public Graph(int numberOfVertex) {
		// 初始化edges和vertex、
		this.edges = new int[numberOfVertex][numberOfVertex];
		this.vertex = new ArrayList<String>(numberOfVertex);
		this.numberOfEdge = 0;
		this.visited = new boolean[numberOfVertex];
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
	
	/**
	 * 得到第一个邻接节点的下标
	 * @param index
	 * @return
	 */
	public int getFirstNeighbor(int index) {
		for (int x = 0;x < this.vertex.size(); x ++) {
			if (this.edges[index][x] > 0) {
				return x;
			}
		}
		return -1;
	}
	
	/**
	 * 根据前一个邻接节点的下标来获取下一个邻接节点
	 * @param v1
	 * @param v2
	 * @return
	 */
	public int getNextNeighbor(int v1, int v2) {
		for (int x = v2 + 1; x < this.vertex.size() ; x ++) {
			if (this.edges[v1][x] > 0) {
				return x;
			}
		}
		return -1;
	}
	
	public void dfs(boolean[] visited, int i) {
		System.out.print(this.getValueByIndex(i) + "->");
		visited[i] = true;
		
		int w = this.getFirstNeighbor(i);
		
		while (w != -1) {
			if (!this.visited[w]) {
				this.dfs(visited, w);
			}
			w = this.getNextNeighbor(i, w);
		}
	}
	
	/**
	 * 深度优先遍历
	 * @param visited
	 * @param i
	 */
	public void dfs() {
		for (int i = 0; i < this.numberOfVertex(); i ++) {
			if (!visited[i]) {
				dfs(visited, i);
			}
		}
	}
	
	private void bfs(boolean[] visited, int i) {
		
		int u; // 队列的头节点对应的下标
		
		int w; // 邻接节点的下标
		
		LinkedList<Integer> queue = new LinkedList<>();
		
		System.out.print(this.getValueByIndex(i) + "->");
		// 标记为访问
		visited[i] = true;
		// 添加到队列
		queue.addLast(i);
		
		while (!queue.isEmpty()) {
			// 取出队列头
			u = queue.removeFirst();
			w = this.getFirstNeighbor(u);
			while (w != -1) {
				// 是否访问过
				if (!visited[w]) {
					System.out.print(this.getValueByIndex(w) + "->");
					visited[w] = true;
					// 入队列
					queue.addLast(w);
				}
				// 广度优先
				w = getNextNeighbor(u, w);
			}
		}
	}

	/**
	 * 广度优先遍历
	 */
	public void bfs() {
		for (int i = 0; i < this.numberOfVertex(); i ++) {
			if (!visited[i]) {
				bfs(visited, i);
			}
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
		
		// 深度优先遍历
		// graph.dfs();
		
		// 广度优先遍历
		graph.bfs();
	}
}
