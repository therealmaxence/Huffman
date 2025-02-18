package Huffman_encoding;

public class Node {
	private String tag;
	private int value;
	private Node left, right;
	
	public Node(String tag) {
		this.tag = tag;
	}
	
	public Node(String tag, Node left, Node right) {
		this.tag = tag;
		this.left = this.right = null;
	}
	
	public String getTag() {
		return tag;
	}
	
	public int getValue() {
		return value;
	}
}
