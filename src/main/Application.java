package main;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Application extends JPanel implements ActionListener {
	static private final String newline = "\n";
    JButton openButton;
    JTextArea log;
    JFileChooser fc;
    
    
	public static void main(String[] args){
		/*	
		String csvPath = "resources/restaurant.csv";
		Application app = new Application();
		List<Map<String, String>> data = app.readCsv(csvPath, ",");
					
 		Map<String, Map<String, Map<String, Integer>>> counts = MapMaker.makeCountMap(data, "wait");
		for(Entry<String, Map<String, Map<String, Integer>>> entry : counts.entrySet()){
			System.out.println(entry.getKey());
			for(Entry<String, Map<String, Integer>> wait : entry.getValue().entrySet()){
				System.out.println("\t" + wait);
			}
		} 
		*/
		
	    //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE); 
                createAndShowGUI();
            }
        });
	}
	
	Application(){
		super(new BorderLayout());
        //Create the log first, because the action listeners
        //need to refer to it.
        log = new JTextArea(20,100);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);
 
        //Create a file chooser
        fc = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir") + "/resources");
        fc.setCurrentDirectory(workingDirectory);
        openButton = new JButton("Open a File...");
        openButton.addActionListener(this);
        
        //For layout purposes, put the buttons in a separate panel
        JPanel buttonPanel = new JPanel(); //use FlowLayout
        buttonPanel.add(openButton); 
        //Add the buttons and the log to this panel.
        add(buttonPanel, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);
	}
	
	
	private List<Map<String, String>> readCsv(String csvPath, String csvSplitBy) {
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		List<String> columns = new ArrayList<String>();
		
		BufferedReader br = null;
		String line = "";
		try {

			br = new BufferedReader(new FileReader(csvPath));
			boolean firstRow = true;
			
			while ((line = br.readLine()) != null) {
				Map<String, String> row = new HashMap<String, String>();
				
				String[] args = line.split(csvSplitBy);
				for(int i = 0; i < args.length; i++){
					args[i] = args[i].trim();
				}
				
				if(firstRow) {
					for(String colName : args) {
						columns.add(colName);
					}
					
					firstRow = false;
					continue;
				} 
				
				int j = 0;
				for(String colName : columns) {
					String value = CheckStringType(args[j]);
					row.put(colName, value);
					j++;
				}
				
				data.add(row);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return data;
	}
	
	private String CheckStringType(String value) {
		String val = value.toUpperCase();
		
		if(val.equals("YES") || val.equals("NO")) {
			return YesNoToBool(val); 
		}
		
		return value;
	}
	
	private String YesNoToBool(String in){
		if(in.toUpperCase().equals("YES")){
			return "true";
		}
		return "false";
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		 //Handle open button action.
        if (e.getSource() == openButton) {
            int returnVal = fc.showOpenDialog(Application.this);
 
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                
            	File file = fc.getSelectedFile();
                List<Map<String, String>> data = readCsv(file.getPath(), ",");
        		Map<String, Map<String, Map<String, Integer>>> counts = MapMaker.makeCountMap(data, "wait");
        		
        		for(Entry<String, Map<String, Map<String, Integer>>> entry : counts.entrySet()){
        			log.append(entry.getKey() + newline);
        			for(Entry<String, Map<String, Integer>> wait : entry.getValue().entrySet()){
        				log.append("\t" + wait + newline);
        			}
        		}
            } else {
                log.append("Open command cancelled by user." + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());
        } 
	}

    /**
     * Create the GUI and show it.  
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Application Machine Learning");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Add content to the window.
        frame.add(new Application());
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
