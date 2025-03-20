package Huffman_encoding;

import java.io.*;
import java.util.*;

/**
 * Handles Huffman encoding and binary file writing for text compression.
 */
public class BinaryEncoding {
    private HuffmanTree ht;
    private Map<Character, String> encodingTable;

    /**
     * Constructs a BinaryEncoding object with a given Huffman tree.
     *
     * @param ht The Huffman tree used for encoding.
     */
    public BinaryEncoding(HuffmanTree ht) {
        this.ht = ht;
        this.encodingTable = new HashMap<>();
        generateBinaryCodes(ht.getRoot(), "");
    }

    /**
     * Recursively generates binary codes for each character in the Huffman tree.
     *
     * @param node The current node in the Huffman tree.
     * @param code The binary code constructed so far.
     */
    private void generateBinaryCodes(Node node, String code) {
        if (node == null) return;

        if (node.getTag() != null) {
            encodingTable.put(node.getTag(), code);
        }

        generateBinaryCodes(node.getLeft(), code + "0");
        generateBinaryCodes(node.getRight(), code + "1");
    }

    /**
     * Writes the encoding table to a file for reference.
     *
     * @param file The file to write the encoding table to.
     */
    public void createEncodingFile(TextFile file) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Character, String> entry : encodingTable.entrySet()) {
            if (entry.getKey() == '\n') {
                sb.append("'").append("\\n").append("' → ");
            } else if (entry.getKey() == '\r') {
                sb.append("'").append("\\r").append("' → ");
            } else {
                sb.append("'").append(entry.getKey()).append("' → ");
            }
            sb.append(entry.getValue()).append('\n');
        }
        file.writeFile(file.getName(), sb.toString());
    }

    /**
     * Creates a binary-encoded file from the given input text file.
     *
     * @param inputFile  The input text file to encode.
     * @param outputFile The output binary file to write the compressed data.
     */
    public void createEncodedFile(TextFile inputFile, TextFile outputFile) {
        String fileContent = inputFile.readFile();
        StringBuilder bitString = new StringBuilder();

        for (char character : fileContent.toCharArray()) {
            bitString.append(encodingTable.get(character)); 
        }

        byte[] packedBytes = packBits(bitString.toString()); 

        try (FileOutputStream fos = new FileOutputStream(outputFile.getPath())) {
            fos.write(packedBytes);
        } catch (IOException e) {
            System.err.println("Error writing encoded file: " + e.getMessage());
        }
    }

    /**
     * Packs a binary string into a byte array to save space.
     *
     * @param bitString The binary string representation of the encoded text.
     * @return A packed byte array representing the encoded data.
     */
    private byte[] packBits(String bitString) {
        int length = (bitString.length() + 7) / 8; 
        byte[] byteArray = new byte[length];

        for (int i = 0; i < bitString.length(); i++) {
            if (bitString.charAt(i) == '1') {
                byteArray[i / 8] |= (1 << (7 - (i % 8))); 
            }
        }
        return byteArray;
    }

    /**
     * Reads a binary file and decodes it back into text using the Huffman tree.
     *
     * @param inputFile  The binary file containing compressed Huffman data.
     * @param outputFile The output text file to store the decoded content.
     */
    public void createDecodedFile(TextFile inputFile, TextFile outputFile) {
        byte[] encodedData = readBinaryFile(inputFile);
        if (encodedData == null) {
            System.err.println("Error: Could not read input file.");
            return;
        }

        StringBuilder decodedText = new StringBuilder();
        Node currentNode = ht.getRoot();

        for (byte b : decodeBits(encodedData)) {
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
     * Reads a binary file into a byte array.
     *
     * @param file The binary file to read.
     * @return The file content as a byte array.
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
     * Decodes a byte array back into individual bits for decompression.
     *
     * @param byteArray The byte array to decode.
     * @return A list of individual bits (0s and 1s).
     */
    private List<Byte> decodeBits(byte[] byteArray) {
        List<Byte> bitList = new ArrayList<>();
        for (byte b : byteArray) {
            for (int i = 7; i >= 0; i--) {
                bitList.add((byte) ((b >> i) & 1)); 
            }
        }
        return bitList;
    }

    /**
     * Returns the encoding table for debugging purposes.
     *
     * @return The Huffman encoding table.
     */
    public Map<Character, String> getEncodingTable() {
        return encodingTable;
    }
}
