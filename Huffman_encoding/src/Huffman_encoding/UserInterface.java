package Huffman_encoding;

import javax.swing.*;
import javax.swing.TransferHandler.TransferSupport;
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
	private TextFile inputFile;
	private TextFile BinaryOutputFile= new TextFile("../data/TEST.bin");
	private TextFile TextOutputFile= new TextFile("../data/TEST.txt");
	private TextFile occurrenceFile = new TextFile("../data/occurrence.txt");
	private TextFile HuffmanTreeFile = new TextFile("../data/HuffmanTree.txt");
	

    public UserInterface() {
        setTitle("Huffman Compression");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel for file dropping
        JPanel dropPanel = new JPanel();
        dropPanel.setBorder(BorderFactory.createTitledBorder("Drop your file here"));
        
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
        		HuffmanTree ht = new HuffmanTree(sortedmap);
        		inputFile.createOccurrenceFile(occurrenceFile);
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
            
        		// Encode the File
    			binaryTable.createEncodedFile(inputFile, BinaryOutputFile);
    			
                JOptionPane.showMessageDialog(this, "Encoding file: " + inputFile.getName());
            } else {
                JOptionPane.showMessageDialog(this, "Please drop a valid .txt file for encoding.");
            }
        });

        JButton decodeButton = new JButton("Decode");
        decodeButton.addActionListener(e -> {
            if (inputFile != null && inputFile.getName().endsWith(".bin")) {
        		
        		// Decode the file
    			binaryTable.createDecodedFile(inputFile, TextOutputFile);
    			
                JOptionPane.showMessageDialog(this, "Decoding file: " + inputFile.getName());
            } else {
                JOptionPane.showMessageDialog(this, "Please drop a valid .bin file for decoding.");
            }
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(treeButton);
        buttonPanel.add(encodeButton);
        buttonPanel.add(decodeButton);
        
        getContentPane().add(dropPanel, "Center");
        getContentPane().add(buttonPanel, "South");
    }
}
