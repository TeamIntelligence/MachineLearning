package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import controllers.TopBarViewController;

public class TopBarView extends JPanel {

	private static final long serialVersionUID = 1L;

    private TopBarViewController controller;
    
    // Views
    private JButton 	 		openFileBtn;
    private JFileChooser 		fileChooser;
    private JComboBox<String> 	columnSelector;
    
    
    public TopBarView() {
		super(new FlowLayout());
    	
    	this.createGui();

		this.controller = new TopBarViewController(this); 
    }
    
    private void createGui() {
        //Create a file chooser
        fileChooser = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir") + "/resources");
        fileChooser.setCurrentDirectory(workingDirectory);
        openFileBtn = new JButton("Open a File...");
        
        columnSelector = new JComboBox<String>();
        columnSelector.setVisible(false);
        
        add(openFileBtn, BorderLayout.WEST);
        add(columnSelector, BorderLayout.EAST);
    }

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public TopBarViewController getController() {
		return controller;
	}
	
	public JButton getOpenFileBtn() {
		return openFileBtn;
	}

	public JFileChooser getFileChooser() {
		return fileChooser;
	}

	public JComboBox<String> getColumnSelector() {
		return columnSelector;
	}
	
}
