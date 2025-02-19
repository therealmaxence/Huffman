package Huffman_encoding;

public class Node {
	private Character tag;
	private int value;
	private Node left, right;
	
	public Node(Character tag, int value) {
		this.tag = tag;
		this.value = value;
		this.right = this.left = null;
	}
	
	
	public Character getTag() {
		return tag;
	}
	
	public int getValue() {
		return value;
	}

	public Node getLeft() {
		return left;
	}
	
	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}
	
	public void setRight(Node right) {
		this.right = right;
	}
}
