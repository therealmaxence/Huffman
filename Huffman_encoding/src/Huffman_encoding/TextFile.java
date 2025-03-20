package Huffman_encoding;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A utility class for handling text files.
 * Provides methods for reading, writing, counting characters,
 * and analyzing character frequency in a file.
 */
public class TextFile {
    private File file;

    /**
     * Constructs a TextFile object with the given file path.
     * @param path The path of the file.
     */
    public TextFile(String path) {
        this.file = new File(path);
    }

    /**
     * Reads the contents of the file and returns it as a string.
     * @return The file content or an error message if reading fails.
     */
    public String readFile() {
        StringBuilder content = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
            return content.toString();
        } catch (IOException e) {
            return "Error reading file: " + e.getMessage();
        }
    }

    /**
     * Writes the given data to a file in the "../data/" directory.
     * @param filename The name of the file to write.
     * @param data The content to be written.
     * @return true if writing was successful, false otherwise.
     */
    public boolean writeFile(String filename, String data) {
        File outputFile = new File(filename);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write(data);
            return true;
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Counts the number of characters in the file.
     * @return The total number of characters in the file.
     */
    public int countCharacters() {
        int count = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            while (bufferedReader.read() != -1) {
                count++;
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return count;
    }

    /**
     * Creates a sorted map of character occurrences in the file.
     * @return A list of character-frequency entries, sorted in descending order.
     */
    public List<Map.Entry<Character, Integer>> createSortedMap() {
        Map<Character, Integer> map = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            int ch;
            while ((ch = bufferedReader.read()) != -1) {
                char character = (char) ch;
                map.put(character, map.getOrDefault(character, 0) + 1);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return map.entrySet()
                  .stream()
                  .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                  .collect(Collectors.toList());
    }

    /**
     * Formats and prints a sorted map of character occurrences.
     * @param map The sorted character-frequency map.
     * @return A formatted string representation of the map.
     */
    public StringBuilder printMap(List<Map.Entry<Character, Integer>> map) {
        StringBuilder str = new StringBuilder();
        for (Map.Entry<Character, Integer> entry : map) {
        	if (entry.getKey() == '\n') {
        		str.append("'").append("\\n").append("' : ")
                   .append(entry.getValue()).append("\n");
        		}
        	else if (entry.getKey() == '\r') {
        		str.append("'").append("\\r").append("' : ")
                .append(entry.getValue()).append("\n");
        	}
        	else {
	            str.append("'").append(entry.getKey()).append("' : ")
	               .append(entry.getValue()).append("\n");
            }
        }
        System.out.println(str);
        return str;
    }

    /**
     * Creates an occurrence file containing character frequencies and total character count.
     * @param inputFile The TextFile object to analyze.
     */
    public void createOccurrenceFile(TextFile inputFile) {
        List<Map.Entry<Character, Integer>> map = inputFile.createSortedMap();
        StringBuilder strbd = new StringBuilder();
        strbd.append(inputFile.countCharacters()).append("\n").append(printMap(map));
        writeFile(file.getName(), strbd.toString());
    }

    /**
     * Gets the absolute path of the file.
     * @return The absolute file path.
     */
    public String getPath() {
        return file.getAbsolutePath();
    }

    /**
     * Gets the name of the file.
     * @return The file name.
     */
    public String getName() {
        return file.getName();
    }
}
