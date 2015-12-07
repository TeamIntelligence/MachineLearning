package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

import models.AbstractViewModel;
import services.CsvReader;
import views.BodyView;
import views.TopBarView;

public class TopBarViewController implements ActionListener{

	private TopBarView 			view;
	private AbstractViewModel 	model;
	
    // Reference to other view
    private BodyView 			bodyView;
	
	public TopBarViewController(TopBarView view, BodyView bodyView) {
		this.view 		= view;
		this.bodyView 	= bodyView;
		
		view.getOpenFileBtn().addActionListener(this);
		view.getColumnSelector().addActionListener(this);
		view.getValueSelector().addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton 		  openFileBtn    = view.getOpenFileBtn();
		JLabel 			  columnSelectorLabel = view.getColumnSelectorLabel();
		JComboBox<String> columnSelector = view.getColumnSelector();
		JLabel 			  valueSelectorLabel = view.getValueSelectorLabel();
		JComboBox<String> valueSelector  = view.getValueSelector();

		
		// Handle open file button action.
		if (e.getSource() == openFileBtn) {
			// Show a file chooser dialog
		    JFileChooser fileChooser = view.getFileChooser();
			int returnVal = fileChooser.showOpenDialog(view);

			// If a value is selected
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				// Get the filepath and read the csv
				File file = fileChooser.getSelectedFile();
				model = (new CsvReader(file.getPath())).readCsv();
				ArrayList<String> values = new ArrayList<String>(model.getColumns().keySet());
				
				// Create a list for the combobox
				values.add(0, "Select a column");
				columnSelector.setModel(new DefaultComboBoxModel(values.toArray()));
				columnSelectorLabel.setVisible(true);
				columnSelector.setVisible(true);
			} 
		} 
		// If the column selector is changed
		else if(e.getSource() == columnSelector) {
			// Get the selected value
			String value = (String) columnSelector.getSelectedItem();
			
			// If the user don't know we the target column, use the last column
			if(value.equals("Select a column")) {
				value = columnSelector.getItemAt(columnSelector.getItemCount()-1);
			}
			
			// Set the target column in the model and refresh all the layouts
			model.setTargetColumn(value);
			bodyView.setModel(model);
			
			ArrayList<String> values = new ArrayList<String>(model.getColumns().get(value));
			values.add(0, "Select a column");
			
			valueSelector.setModel(new DefaultComboBoxModel(values.toArray()));
			valueSelectorLabel.setVisible(true);
			valueSelector.setVisible(true);
		}
		
		// If the value selector is changed
		else if(e.getSource() == valueSelector) {
			// Get the selected value
			String value = (String) columnSelector.getSelectedItem();
			
			// If the user don't know we the target column, use the last column
			if(value.equals("Select a column")) {
				value = valueSelector.getItemAt(valueSelector.getItemCount()-1);
			}
			
			// Set the target column in the model and refresh all the layouts
			model.setTargetValue(value);
			bodyView.setModel(model);
		}
		
	}

	
}
