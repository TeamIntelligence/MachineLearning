package views;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import models.ZeroRModel;
import controllers.ZeroRController;

public class ZeroRView extends JPanel {

	private static final long serialVersionUID = 1L;
	private ZeroRController controller;
	private ZeroRModel model;
	
	// View items
    private JButton openFileBtn;
	private JTextArea logTextArea;
    private JFileChooser fileChooser;
	
	public ZeroRView() {
		super(new BorderLayout());
		
		this.createGui();
		
		this.controller = new ZeroRController(this); 
	}
	
	private void createGui() {
        //Create the log first, because the action listeners
        //need to refer to it.
		logTextArea = new JTextArea(20,100);
		logTextArea.setMargin(new Insets(5,5,5,5));
		logTextArea.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(logTextArea);
 
        //Create a file chooser
        fileChooser = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir") + "/resources");
        fileChooser.setCurrentDirectory(workingDirectory);
        openFileBtn = new JButton("Open a File...");
        // openFileBtn.addActionListener(this);
        
        //For layout purposes, put the buttons in a separate panel
        JPanel buttonPanel = new JPanel(); //use FlowLayout
        buttonPanel.add(openFileBtn); 
        
        //Add the buttons and the log to this panel.
        add(buttonPanel, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);
	}

	public ZeroRController getController() {
		return controller;
	}

	public ZeroRModel getModel() {
		return model;
	}

	public JButton getOpenFileBtn() {
		return openFileBtn;
	}

	public JTextArea getLogTextArea() {
		return logTextArea;
	}

	public JFileChooser getFileChooser() {
		return fileChooser;
	}
}
