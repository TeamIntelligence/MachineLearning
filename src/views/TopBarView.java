package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import models.AbstractViewModel;
import controllers.TopBarViewController;

public class TopBarView extends JPanel implements ViewInterface  {

	private static final long serialVersionUID = 1L;

    private TopBarViewController controller;
    
    // Views
    private JButton 	 		openFileBtn;
    private JFileChooser 		fileChooser;
    private JComboBox<String> 	columnSelector;
    
    
    public TopBarView(BodyView bodyView) {
		super(new FlowLayout());
    	
    	this.createLayout();
		this.controller = new TopBarViewController(this, bodyView); 
    }

	@Override
    public void createLayout() {
		removeAll();
		
        //Create a file chooser
        fileChooser = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir") + "/resources");
        fileChooser.setCurrentDirectory(workingDirectory);
        openFileBtn = new JButton("Open a File...");
        
        columnSelector = new JComboBox<String>();
        columnSelector.setVisible(false);
        
        add(openFileBtn, BorderLayout.WEST);
        add(columnSelector, BorderLayout.EAST);
		
		repaint();
		revalidate();
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
	
	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setModel(AbstractViewModel baseData) {
		// TODO Auto-generated method stub
		
	}
	
}
