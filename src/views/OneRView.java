package views;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import models.AbstractViewModel;
import models.OneRModel;

public class OneRView extends JPanel implements ViewInterface{
	private static final long 	serialVersionUID = 1L;
	private OneRModel 			model;
	
	public OneRView(){
		super(new BorderLayout());
		
		this.createLayout();
		this.model      = new OneRModel(null, null);
	}

	@Override
	public void createLayout() {
		removeAll();
		
		// If we have a model and counts we can print a table with the data
		if(this.model != null && this.model.getOneRMap() != null) {
			
			System.out.println(model.getOneRMap());

		} else {
			
	        //Create the log first, because the action listeners
	        //need to refer to it.
			JTextArea logTextArea = new JTextArea();
			logTextArea.setEditable(false);
			logTextArea.setText("Please select a column as target column!");
	        JScrollPane logScrollPane = new JScrollPane(logTextArea);
	        
	        //Add the buttons and the log to this panel.
	        add(logScrollPane, BorderLayout.CENTER);
		}
		
		repaint();
		revalidate();
	}


	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		model.createOneRMap();
		createLayout();
	}

	@Override
	public void setModel(AbstractViewModel baseData) {
		// TODO Auto-generated method stub
		model.setBaseData(baseData);
		refresh();
	}
}
