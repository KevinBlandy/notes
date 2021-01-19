-------------------------
并查集					 |
-------------------------
	# UnionFind
	# 可以高效的处理连接问题
		* 迷宫图中,两个点是否能相连
	
	# 网络中间那个节点的连接状态
		* 社交网络(用户之间形成的网络)
		* A 和 B是否可以通过网络认识...
	
	# 数学中的集合类实现
		
	# 主要支持俩动作
		union(p,q);
		isConneted(p,q);

		
-------------------------
java实现				 |
-------------------------

public class UnionFind {
	
	private int[] parent;
	
	private int[] rank;	//rank[i] 表示以i为根的集合中元素个数

	public UnionFind(int size) {
		super();
		this.parent = new int[size];
		this.rank = new int[size];
		for (int i = 0; i < this.parent.length; i++) {
			this.parent[i] = i;
			this.rank[i] = 1;
		}
	}
	
	public int size() {
		return this.parent.length;
	}
	
//	private int find(int p) {
//		if(p < 0 || p >= this.parent.length) {
//			throw new IllegalArgumentException("非法索引");
//		}
//		while(p != this.parent[p]) {
//			this.parent[p] = this.parent[this.parent[p]];
//			p = this.parent[p];
//		}
//		return p;
//	}
	
	private int find(int p) {
		if(p < 0 || p >= this.parent.length) {
			throw new IllegalArgumentException("非法索引");
		}
		if(p != this.parent[p]) {
			this.parent[p] = this.find(this.parent[p]);
		}
		return this.parent[p];
	}
	
	public boolean isConnected(int p,int q) {
		return this.find(p) == this.find(q);
	}
	
	public void unionElements(int p,int q) {
		int pRoot = this.find(p);
		int qRoot = this.find(q);
		if(pRoot == qRoot) {
			return;
		}
		if(this.rank[pRoot] < this.rank[qRoot]) {
			this.parent[pRoot] = qRoot;	
		}else if(this.rank[pRoot] > this.rank[qRoot]){
			this.parent[qRoot] = pRoot;	
		}else{
			this.parent[qRoot] = pRoot;
			this.rank[pRoot] += 1;
		}
	}
}
