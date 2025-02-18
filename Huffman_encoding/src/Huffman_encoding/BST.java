package Huffman_encoding;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BST {
    private Node root;

    public BST(Stream<Entry<Character, Integer>> data) {
    	
        List<Entry<Character, Integer>> sortedList = data.collect(Collectors.toList());

        root = buildBST(sortedList, 0, sortedList.size() - 1);
    }

    private Node buildBST(List<Entry<Character, Integer>> sortedList, int start, int end) {
        if (start > end) return null;

        int mid = (start + end) / 2;
        Entry<Character, Integer> midEntry = sortedList.get(mid);

        Node node = new Node(midEntry.getTag(), midEntry.getValue());
        node.left = buildBST(sortedList, start, mid - 1);
        node.right = buildBST(sortedList, mid + 1, end);

        return node;
    }

    public void inorderTraversal(Node node) {
        if (node != null) {
            inorderTraversal(node.left);
            System.out.println("'" + node.key + "' : " + node.value);
            inorderTraversal(node.right);
        }
    }

    public void printTree() {
        inorderTraversal(root);
    }
}

