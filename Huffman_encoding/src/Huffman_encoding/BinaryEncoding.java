package Huffman_encoding;

import java.util.*;
import java.util.Map.Entry;

public class BinaryEncoding {
    private HuffmanTree ht;
    private List<Entry<Character, byte[]>> table; 

    public BinaryEncoding(HuffmanTree ht) {
        this.ht = ht;
        this.table = new ArrayList<>();
        generateBinaryCodes(ht.getRoot(), new ArrayList<>());
    }

    private void generateBinaryCodes(Node node, List<Byte> code) {
        if (node != null) {
        	
            if (node.getTag() != null) { 
                byte[] binaryCode = new byte[code.size()];
                for (int i = 0; i < code.size(); i++) {
                    binaryCode[i] = code.get(i);
                }
                table.add(Map.entry(node.getTag(), binaryCode)); 
            }

            code.add((byte) 0);
            generateBinaryCodes(node.getLeft(), code);
            code.remove(code.size() - 1);

            code.add((byte) 1);
            generateBinaryCodes(node.getRight(), code);
            code.remove(code.size() - 1);
        }
    }

    public void createEncodingFile(TextFile file) {
    	StringBuilder strbd = new StringBuilder();
        for (Entry<Character, byte[]> entry : table) {
        	strbd.append("'").append(entry.getKey()).append("' → ");
            System.out.print("'" + entry.getKey() + "' → " );
            for (byte b : entry.getValue()) {
            	strbd.append(b);
                System.out.print(b);
            }
            strbd.append('\n');
            System.out.println();
            String str = new String(strbd);
            file.writeFile(file.getPath(), str);
        }
    }

    public List<Entry<Character, byte[]>> getEncodingTable() {
        return table;
    }
}
