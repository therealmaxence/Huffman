package Huffman_encoding;

import java.util.*;
import java.util.Map.Entry;

/**
 * This class represents a Huffman Tree used for text compression.
 * It builds a tree from a sorted list of character frequencies and provides traversal methods.
 */
public class HuffmanTree {
    private Node root;

    /**
     * Constructs a Huffman Tree from a sorted list of character-frequency pairs.
     *
     * @param sortedList A list of character-frequency pairs sorted in ascending order.
     */
    public HuffmanTree(List<Entry<Character, Integer>> sortedList) {
        // Convert List to Queue
        Queue<Node> queue = new LinkedList<>();

        for (Entry<Character, Integer> entry : sortedList) {
            queue.add(new Node(entry.getKey(), entry.getValue()));
        }

        while (queue.size() > 1) {
            Node left = queue.poll();
            Node right = queue.poll();

            Node parent = new Node(null, left.getValue() + right.getValue());
            parent.setLeft(left);
            parent.setRight(right);

            insertSorted(queue, parent);
        }

        root = queue.poll();
    }

    /**
     * Inserts a node into the queue while maintaining the sorted order.
     *
     * @param queue  The priority queue of nodes.
     * @param newNode The new node to insert.
     */
    private void insertSorted(Queue<Node> queue, Node newNode) {
        List<Node> tempList = new ArrayList<>(queue);
        tempList.add(newNode);

        tempList.sort(Comparator.comparingInt(Node::getValue));

        queue.clear();
        queue.addAll(tempList);
    }

    /**
     * Performs an inorder traversal of the Huffman Tree and prints character frequencies.
     *
     * @param node The current node being visited.
     */
    public void inorderTraversal(Node node) {
        if (node != null) {
            inorderTraversal(node.getLeft());
            if (node.getTag() != null) 
                System.out.println("'" + node.getTag() + "' : " + node.getValue());
            inorderTraversal(node.getRight());
        }
    }

    /**
     * Prints the Huffman Tree in inorder traversal format.
     */
    public void printTree() {
        inorderTraversal(root);
    }

    /**
     * Retrieves the root node of the Huffman Tree.
     *
     * @return The root node.
     */
    public Node getRoot() {
        return root;
    }
}
