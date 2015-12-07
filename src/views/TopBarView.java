package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
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
    private JLabel				columnSelectorLabel;
    private JComboBox<String> 	valueSelector;
    private JLabel				valueSelectorLabel;


    
    
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
        
        columnSelectorLabel = new JLabel("Select the target column");
        columnSelectorLabel.setVisible(false);
        columnSelector = new JComboBox<String>();
        columnSelector.setVisible(false);
        
        valueSelectorLabel = new JLabel("Select the target value");
        valueSelectorLabel.setVisible(false);
        valueSelector = new JComboBox<String>();
        valueSelector.setVisible(false);
        
        add(openFileBtn, BorderLayout.WEST);
        add(columnSelectorLabel, BorderLayout.EAST);
        add(columnSelector, BorderLayout.EAST);
        add(valueSelectorLabel);
        add(valueSelector);
		
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
	
	public JComboBox<String> getValueSelector() {
		return valueSelector;
	}
	
	public JLabel getColumnSelectorLabel() {
		return columnSelectorLabel;
	}

	public JLabel getValueSelectorLabel() {
		return valueSelectorLabel;
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
