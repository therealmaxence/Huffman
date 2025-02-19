package Huffman_encoding;

import java.util.List;
import java.util.Map.Entry;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// Path to all the used files
		String path1 = "../data/alice.txt";
		String path2 = "../data/test.txt";
		String path3 = "../data/binary.txt";

		// Building files
		TextFile file1 = new TextFile(path1);
		TextFile file2 = new TextFile(path2);
		TextFile file3 = new TextFile(path3);
		
		System.out.println("_________________Test__________________");
		file1.readFile();
		file1.countCharacters();
		
			// Sorted map
			List<Entry<Character, Integer>> sortedmap = file1.createSortedMap();
			file1.printMap(sortedmap);
			
			// Occurrence File
		file2.createOccurenceFile(file1);
		
		System.out.println("_________________HuffmanTree__________________");
		HuffmanTree ht = new HuffmanTree(sortedmap);
		ht.printTree();
		
		System.out.println("_________________Binary__________________");
		BinaryEncoding binaryTable = new BinaryEncoding(ht);
		binaryTable.createEncodingFile(file3);

	}

}
