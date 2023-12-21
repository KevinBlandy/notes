package cn.springdoc.demo.test;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


class Node {
	private Integer id;
	private Integer parentId;
	private String name;
	private List<Node> nodes;
	
	public Node(Integer id, Integer parentId, String name, List<Node> nodes) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.nodes = nodes;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public List<Node> getNodes() {
		return nodes;
	}
	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}


public class Main {

	public static void main(String[] args) throws Exception {
		List<Node> ret = tree(List.of(
				new Node(1, 0, "重庆", null)
				,new Node(2, 0, "北京", null)
				,new Node(3, 1, "綦江", null)
				,new Node(4, 3, "扶欢", null)
				,new Node(5, 0, "四川", null)
				,new Node(6, 5, "达州", null)
				,new Node(7, 6, "渠县", null)
				,new Node(8, 4, "长榜", null)
				));
		
		ObjectMapper objectMapper =  new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		
		String content = objectMapper.writeValueAsString(ret);
		
		System.out.println(content);
	}
	
	public static List<Node> tree (List<Node> nodes){

		// 迭代出根节点
		List<Node> ret = nodes.stream().filter(node -> node.getParentId().equals(0)).toList();
		
		// 计算出每个节点的子节点
		ret.stream().forEach(node -> {
			suNodes(nodes, node);
		});
		return ret;
	}
	// 从 nodes 节点中检索出 node 的字节节点，递归
	public static void suNodes (List<Node> nodes, Node node) {
		node.setNodes(nodes.stream().filter(n -> n.getParentId().equals(node.getId())).toList());
		if (!node.getNodes().isEmpty()) {
			node.getNodes().forEach(n -> suNodes(nodes, n));
		}
	}
}
