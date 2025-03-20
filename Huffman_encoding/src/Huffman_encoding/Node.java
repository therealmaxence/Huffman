package Huffman_encoding;

/**
 * Represents a node in the Huffman tree.
 * Each node contains a character (tag), a frequency value, and pointers to left and right children.
 */
public class Node {
    private Character tag; // The character stored in the node (null for internal nodes)
    private int value; // Frequency of the character or sum of child node frequencies
    private Node left, right; // Left and right children

    /**
     * Constructs a new node with a given character and frequency.
     *
     * @param tag   The character associated with this node (null for internal nodes).
     * @param value The frequency of the character or combined frequency for internal nodes.
     */
    public Node(Character tag, int value) {
        this.tag = tag;
        this.value = value;
        this.right = this.left = null;
    }

    /**
     * Checks if the node is a leaf (i.e., has no children).
     *
     * @return True if the node is a leaf, false otherwise.
     */
    public boolean isLeaf() {
        return (this.getLeft() == null && this.getRight() == null);
    }

    /**
     * Gets the character associated with this node.
     *
     * @return The character stored in this node, or null for internal nodes.
     */
    public Character getTag() {
        return tag;
    }

    /**
     * Gets the frequency value of this node.
     *
     * @return The frequency of the character or combined frequency of child nodes.
     */
    public int getValue() {
        return value;
    }

    /**
     * Gets the left child of this node.
     *
     * @return The left child node.
     */
    public Node getLeft() {
        return left;
    }

    /**
     * Sets the left child of this node.
     *
     * @param left The left child node to set.
     */
    public void setLeft(Node left) {
        this.left = left;
    }

    /**
     * Gets the right child of this node.
     *
     * @return The right child node.
     */
    public Node getRight() {
        return right;
    }

    /**
     * Sets the right child of this node.
     *
     * @param right The right child node to set.
     */
    public void setRight(Node right) {
        this.right = right;
    }
}
