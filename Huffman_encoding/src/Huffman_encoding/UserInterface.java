package Huffman_encoding;

import javax.swing.*;
import javax.swing.TransferHandler.TransferSupport;
import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.awt.Font;
import java.awt.datatransfer.*;
import java.io.File;
import java.util.List;
import java.util.Map.Entry;

public class UserInterface extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BinaryEncoding binaryTable;
	private HuffmanTree ht;
	private TextFile inputFile;
	private TextFile BinaryOutputFile;
	private TextFile TextOutputFile;
	private TextFile OccurrenceFile = new TextFile("Occurrence.txt");
	private TextFile HuffmanTreeFile = new TextFile("HuffmanTree.txt");
	

    public UserInterface() {
        setTitle("Huffman Compression");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel for file dropping
        JPanel dropPanel = new JPanel();
        TitledBorder border = BorderFactory.createTitledBorder("Drop your file here");
        border.setTitleColor(Color.WHITE);
        dropPanel.setBorder(border);
        dropPanel.setBackground(Color.DARK_GRAY);

        
        // Set up the TransferHandler for drag-and-drop
        dropPanel.setTransferHandler(new TransferHandler() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public boolean canImport(TransferSupport support) {
                return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
            }

            @Override
            public boolean importData(TransferSupport support) {
                if (!canImport(support)) return false;
                try {
                    Transferable t = support.getTransferable();
                    List<TextFile> files = ((List<File>) t.getTransferData(DataFlavor.javaFileListFlavor))
                    		.stream()
                            .map(f -> new TextFile(f.getPath()))
                            .toList();
                    
                    if (!files.isEmpty()) {
                        inputFile = files.get(0);
                        JOptionPane.showMessageDialog(dropPanel, "File dropped: " + inputFile.getName());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return false;
                }
                return true;
            }
        });
        
        JButton treeButton = new JButton("Create HuffmanTree");
        treeButton.addActionListener(e -> {
            if (inputFile != null && inputFile.getName().endsWith(".txt")) {
            	
            	// Create the Huffman Tree
                List<Entry<Character, Integer>> sortedmap = inputFile.createSortedMap();
        		ht = new HuffmanTree(sortedmap);
        		OccurrenceFile.createOccurrenceFile(inputFile);
        		binaryTable = new BinaryEncoding(ht);
        		binaryTable.createEncodingFile(HuffmanTreeFile);
    			
                JOptionPane.showMessageDialog(this, "Huffman Tree has successfully been created! ");
            } else {
                JOptionPane.showMessageDialog(this, "Please drop a valid .txt file for creating the Tree.");
            }
        });
        
        JButton encodeButton = new JButton("Encode");
        encodeButton.addActionListener(e -> {
            if (inputFile != null && inputFile.getName().endsWith(".txt")) {
            
            	// Choose the name of the output file
            	String filename = JOptionPane.showInputDialog(null, 
            	        "Choose the name of the output file:", 
            	        "File Name Input", 
            	        JOptionPane.PLAIN_MESSAGE);
            	BinaryOutputFile = new TextFile("../data/" + filename + ".bin");
            	
            	
        		// Encode the File
    			binaryTable.createEncodedFile(inputFile, BinaryOutputFile);
    			
                JOptionPane.showMessageDialog(this, "Encoded file: " + BinaryOutputFile.getPath());
            } else if (ht == null){
                JOptionPane.showMessageDialog(this, "Huffman Tree not created.");
            } else {
                JOptionPane.showMessageDialog(this, "Please drop a valid .txt file for encoding.");
            }
        });

        JButton decodeButton = new JButton("Decode");
        decodeButton.addActionListener(e -> {
            if (inputFile != null && inputFile.getName().endsWith(".bin")) {
            	
            	// Choose the name of the output file
            	String filename = JOptionPane.showInputDialog(null, 
            	        "Choose the name of the output file:", 
            	        "File Name Input", 
            	        JOptionPane.PLAIN_MESSAGE);
            	TextOutputFile = new TextFile("../data/" + filename + ".txt");
            	
        		// Decode the file
    			binaryTable.createDecodedFile(inputFile, TextOutputFile);
    			
                JOptionPane.showMessageDialog(this, "Decoded file: " + TextOutputFile.getName());
            } else if (ht == null){
                JOptionPane.showMessageDialog(this, "Huffman Tree not created.");
            } else {
                JOptionPane.showMessageDialog(this, "Please drop a valid .bin file for decoding.");
            }
        });
        
        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.DARK_GRAY);
        
        JButton[] buttons = {treeButton, encodeButton, decodeButton};
        
        for (JButton btn : buttons) {
            btn.setBackground(Color.WHITE);
            btn.setForeground(Color.BLACK);
            btn.setFont(new Font("Arial", Font.BOLD, 12));
            buttonPanel.add(btn);
        }
        

        getContentPane().add(dropPanel, "Center");
        getContentPane().add(buttonPanel, "South");
    }
}
