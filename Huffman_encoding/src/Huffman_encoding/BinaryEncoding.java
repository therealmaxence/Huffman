package Huffman_encoding;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class BinaryEncoding {
    private HuffmanTree ht;
    private Map<Character, byte[]> encodingTable;

    public BinaryEncoding(HuffmanTree ht) {
        this.ht = ht;
        this.encodingTable = new HashMap<>();
        generateBinaryCodes(ht.getRoot(), new ArrayList<>());
    }

    /**
     * Recursively generates binary codes for each character in the Huffman tree.
     *
     * @param node The current node in the Huffman tree.
     * @param code The binary code constructed so far.
     */
    private void generateBinaryCodes(Node node, List<Byte> code) {
        if (node == null) {
            return;
        }

        if (node.getTag() != null) {
            byte[] binaryCode = new byte[code.size()];
            for (int i = 0; i < code.size(); i++) {
                binaryCode[i] = code.get(i);
            }
            encodingTable.put(node.getTag(), binaryCode);
        }

        code.add((byte) 0);
        generateBinaryCodes(node.getLeft(), code);
        code.remove(code.size() - 1);

        code.add((byte) 1);
        generateBinaryCodes(node.getRight(), code);
        code.remove(code.size() - 1);
    }

    
    /**
     * Writes the encoding table to a file for reference.
     *
     * @param file The file to write the encoding table to.
     */
    public void createEncodingFile(TextFile file) {
        StringBuilder sb = new StringBuilder();
        for (Entry<Character, byte[]> entry : encodingTable.entrySet()) {
            sb.append("'").append(entry.getKey()).append("' → ");
            for (byte b : entry.getValue()) {
                sb.append(b);
            }
            sb.append('\n');
        }

        file.writeFile(file.getName(), sb.toString());
    }

    
    /**
     * Encodes a string into a byte array using the Huffman encoding table.
     *
     * @param str The string to encode.
     * @return The encoded byte array.
     */
    public byte[] encodeString(String str) {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        for (char character : str.toCharArray()) {
            byte[] binaryCode = encodingTable.get(character);
            if (binaryCode != null) {
                for (byte b : binaryCode) {
                    byteStream.write(b);
                }
            } else {
                System.err.println("Warning: Character '" + character + "' not found in encoding table.");
            }
        }
        return byteStream.toByteArray();
    }

    /**
     * Encodes the content of an input file and writes the result to an output file.
     *
     * @param inputFile  The input text file.
     * @param outputFile The output binary file.
     */
    public void createEncodedFile(TextFile inputFile, TextFile outputFile) {
    	
        String fileContent = inputFile.readFile();
        byte[] encodedData = encodeString(fileContent);

        try (FileOutputStream fos = new FileOutputStream(outputFile.getPath())) {
            fos.write(encodedData);
        } catch (IOException e) {
            System.err.println("Error writing encoded file: " + e.getMessage());
        }
    }

    /**
     * Decodes the content of a binary file and writes the result to an output file.
     *
     * @param inputFile  The input binary file.
     * @param outputFile The output text file.
     */
    public void createDecodedFile(TextFile inputFile, TextFile outputFile) {
    	
        byte[] encodedData = readBinaryFile(inputFile);
        System.out.println(encodedData);
        if (encodedData == null) {
            System.err.println("Error: Could not read input file.");
            return;
        }

        StringBuilder decodedText = new StringBuilder();
        Node currentNode = ht.getRoot();

        for (byte b : encodedData) {
	        currentNode = (b == 1) ? currentNode.getRight() : currentNode.getLeft();
	        if (currentNode.isLeaf()) {
	            decodedText.append(currentNode.getTag());
	            currentNode = ht.getRoot();
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile.getPath()))) {
            writer.write(decodedText.toString());
        } catch (IOException e) {
            System.err.println("Error writing decoded file: " + e.getMessage());
        }
    }
    
    

    /**
     * Reads a binary file and returns its content as a byte array.
     *
     * @param filePath The path to the binary file.
     * @return The content of the file as a byte array, or null if an error occurs.
     */
    public byte[] readBinaryFile(TextFile file) {
    	
        try (FileInputStream fis = new FileInputStream(file.getPath());
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
        	
            byte[] buffer = new byte[4096];
            int bytesRead;
            
            while ((bytesRead = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            return bos.toByteArray();
            
        } catch (IOException e) {
            System.err.println("Error reading binary file: " + e.getMessage());
            return null;
        }
    }
    

    /**
     * Returns the encoding table for testing or debugging purposes.
     *
     * @return The encoding table.
     */
    public Map<Character, byte[]> getEncodingTable() {
        return encodingTable;
    }
}