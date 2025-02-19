package Huffman_encoding;

import java.io.*;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class TextFile {
    private String path;

    
    public TextFile(String path) {
        this.path = path;
    }

    
    public String readFile() {
        StringBuilder content = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
            System.out.println(content);
            return content.toString();
        } catch (IOException e) {
            return "Error reading file: " + e.getMessage();
        }
    }

    
    public boolean writeFile(String filename, String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("../data/" + filename))) {
            writer.write(data);
            return true;
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
            return false;
        }
    }
    
    public int countCharacters() {
        int count = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            while ( bufferedReader.read() != -1) {
                count++;
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        System.out.println(count);
        return count;
    }
    
    public List<Entry<Character, Integer>> createSortedMap() { 
        Map<Character, Integer> map = new HashMap<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
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


    
    public StringBuilder printMap(List<Map.Entry<Character, Integer>> map) {
        StringBuilder str = new StringBuilder();

        for (Map.Entry<Character, Integer> entry : map) {
            String key = (entry.getKey() == '\n') ? "'\\n'" : "'" + entry.getKey() + "'";
            str.append(key).append(" : ").append(entry.getValue()).append("\n");
        }

        System.out.println(str);
        return str;
    }

    
    public void createOccurenceFile(TextFile entryfile) {
    	
    	List<Entry<Character, Integer>> map = entryfile.createSortedMap();
    	
    	StringBuilder strbd = new StringBuilder();	
    	strbd.append(entryfile.countCharacters()).append(printMap(map));
    	String str = new String(strbd);
    	
    	writeFile(path, str);
    }
    
    public String getPath() {
    	return path;
    }
}
