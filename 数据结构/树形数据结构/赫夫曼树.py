# 赫夫曼树

	* 也被翻译为： 霍夫曼树
	
	* 给定n个权值作为n个叶子节点，构造一个二叉树
	* 如果该树的 '带权路径长度(wpl)' 达到最小，那么这样的二叉树为最优二叉树，就是霍夫曼树

	* 霍夫曼树是'带权路径长度'最短的树，权值较大的节点离根比较近
	

	* 通俗的理解就是，一个二叉树。每个子节点，都有一个值
		从根节点到该子节点的路径长度 * 该节点的值 = 节点的'带权路径长度'
	
	* 一棵树的带权路径长度(wpl) = 所有叶子节点的'带权路径长度'之和

	* wpl最小的，就是赫夫曼树


# 赫夫曼树的构建过程
	1. 从小到大的排序权值, 每一个数据都是一个节点，每个节点可以当做一个最简单的二叉树
	2. 取出根节点权值最小的两颗二叉树
	3. 组成一颗新的二叉树，该新的二叉树的根节点权值是前面两颗二叉树根节点权值的和
	4. 再把这颗心的二叉树，以根节点的权值大小，再次排序，不断重复:1,2,3,4 的步骤，直到数列中所有的数据都被处理
	5. 最后就得到了一颗霍夫曼树



# py实现



class Node(object):
    def __init__(self, value, left, right):
        self.value = value    # 节点权值
        self.left = left    # 左子节点
        self.right = right  # 右子节点

    def __str__(self):
        return "Node[value=%s]" % self.value

    # 递归前序遍历树
    def pre_foreach(self):
        print(self)
        if self.left is not None:
            self.left.pre_foreach()
        if self.right is not None:
            self.right.pre_foreach()


# 遍历树
def foreach_tree(node):
    queue = [node]
    while len(queue) > 0:
        item = queue.pop(0)
        print(item)
        if item.left is not None:
            queue.append(item.left)
        if item.right is not None:
            queue.append(item.right)


# 创建霍夫曼树
def create_huffman_tree(array):
    # 把数据封装为node集合
    nodes = []
    for i in array:
        nodes.append(Node(i, None, None))

    while len(nodes) > 1:
        # 排序node
        nodes = sorted(nodes, key=lambda x:  x.value)

        # 取出权值最小的两个节点，构建出一颗新的二叉树
        left = nodes.pop(0)
        right = nodes.pop(0)
        root = Node(left.value + right.value, left, right)

        # 把新元素添加到集合
        nodes.append(root)

    return nodes[0]


tree = create_huffman_tree([13, 7, 8, 3, 29, 6, 1])

# foreach_tree(tree)
tree.pre_foreach()