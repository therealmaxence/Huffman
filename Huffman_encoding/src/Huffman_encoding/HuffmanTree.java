package Huffman_encoding;

import java.util.*;
import java.util.Map.Entry;

public class HuffmanTree {
    private Node root;

    public HuffmanTree(List<Entry<Character, Integer>> sortedList) {
    	
        // List to Queue
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

    private void insertSorted(Queue<Node> queue, Node newNode) {
        List<Node> tempList = new ArrayList<>(queue);
        tempList.add(newNode);

        tempList.sort(Comparator.comparingInt(Node::getValue));

        queue.clear();
        queue.addAll(tempList);
    }

    public void inorderTraversal(Node node) {
        if (node != null) {
            inorderTraversal(node.getLeft());
            if (node.getTag() != null) 
            	System.out.println("'" + node.getTag() + "' : " + node.getValue());
            inorderTraversal(node.getRight());
        }
    }

    public void printTree() {
        inorderTraversal(root);
    }

    public Node getRoot() {
        return root;
    }
}
