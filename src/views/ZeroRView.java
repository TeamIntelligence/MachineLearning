package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import models.ZeroRModel;
import controllers.ZeroRController;

public class ZeroRView extends JPanel {

	private static final long 	serialVersionUID = 1L;
	private ZeroRController 	controller;
	private ZeroRModel 			model;
	private JFrame				rootWindow;
	
	// View items
	private JTextArea 	 logTextArea;
    private JPanel 		 columnPanel;
	
	public ZeroRView() {
		super(new BorderLayout());
		
		this.createGui();
		
		this.controller = new ZeroRController(this); 
	}
	
	private void createGui() {
        //Create the log first, because the action listeners
        //need to refer to it.
		logTextArea = new JTextArea();
		logTextArea.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(logTextArea);
        
        //Add the buttons and the log to this panel.
        add(logScrollPane, BorderLayout.CENTER);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public ZeroRController getController() {
		return controller;
	}

	public ZeroRModel getModel() {
		return model;
	}

	public JTextArea getLogTextArea() {
		return logTextArea;
	}

	public JPanel getColumnPanel() {
		return columnPanel;
	}

	public JFrame getRootWindow() {
		if(rootWindow == null) {
			rootWindow = (JFrame) SwingUtilities.getRoot(this);
		}
		
		return rootWindow;
	}
}
