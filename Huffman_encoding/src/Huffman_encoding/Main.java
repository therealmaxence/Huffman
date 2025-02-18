package Huffman_encoding;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// Path to all the used files
		String path1 = "../data/textesimple.txt";
		String path2 = "../data/test.txt";

		// Building files
		TextFile file1 = new TextFile(path1);
		TextFile file2 = new TextFile(path2);
		
		// Tests
		file1.readFile();
		file1.countCharacters();
		file1.printMap(file1.createSortedMap());
		
		file2.createOccurenceFile(file1);
	}

}
