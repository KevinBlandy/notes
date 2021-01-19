--------------------------------
前缀树							|
--------------------------------
	# trie,也叫做字典树
	# 它不是二叉树,是一个多叉树
	# 它一般只用来处理字符串

	# 每个节点有多个指向下个节点的指针
		* 26个英文字母(拼音)
		* 如果考虑大小写的话,需要52个子节点

	# 节点的设计
		class Node {
			char c;
			Map<Character,Node> next;
		}

	# 局限性
		* 浪费了太多的空间
	
	# 扩展
		* 压缩字典树



--------------------------------
Java实现						|
--------------------------------
import java.util.Map;
import java.util.TreeMap;

public class Trie {

	private class Node {
		// 是否是单词
		private boolean isWord;
		// 子单词树
		private TreeMap<Character, Node> next;

		public Node(boolean isWord) {
			this.isWord = isWord;
			this.next = new TreeMap<>();
		}

		public Node() {
			this(false);
		}
	}

	private Node root;
	// 有多少个词儿
	private int size;

	public Trie() {
		this.root = new Node();
		this.size = 0;
	}

	public int size() {
		return this.size;
	}

	public boolean empty() {
		return this.size == 0;
	}

	public void add(String word) {
		Node current = this.root;
		// 遍历字符串
		for (int i = 0; i < word.length(); i++) {
			Character character = word.charAt(i);
			if (!current.next.containsKey(character)) {
				current.next.put(character, new Node(false));
			}
			current = current.next.get(character);
		}
		if (!current.isWord) {
			current.isWord = true;
			this.size++;
		}
	}

	// trie是否包含某个单词
	public boolean contains(String word) {
		Node current = this.root;
		for (int i = 0; i < word.length(); i++) {
			Character character = word.charAt(i);
			if (!current.next.containsKey(character)) {
				return false;
			}
			current = current.next.get(character);
		}
		return current.isWord;
	}

	// 是否有指定前缀的单词
	public boolean prefix(String prefix) {
		Node current = this.root;
		for (int i = 0; i < prefix.length(); i++) {
			Character character = prefix.charAt(i);
			if (!current.next.containsKey(character)) {
				return false;
			}
			current = current.next.get(character);
		}
		return true;
	}

	/**
	 * . 可以表示任意的一个字符
	 * 
	 * @param regex
	 * @return
	 */
	public boolean match(String regex) {
		return this.match(this.root, regex, 0);
	}

	private boolean match(Node node, String regex, int index) {
		if (index == regex.length()) {
			return node.isWord; // 找到单词
		}
		Character character = regex.charAt(index);
		if (!character.equals('.')) {
			if (!node.next.containsKey(character)) {
				return false;
			} else {
				return this.match(node.next.get(character), regex, index + 1);
			}
		} else {
			for (Map.Entry<Character, Node> entry : node.next.entrySet()) {
				if (this.match(entry.getValue(), regex, index + 1)) {
					return true;
				}
			}
			return false;
		}
	}
}