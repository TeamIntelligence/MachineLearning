package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import models.AbstractViewModel;
import models.ZeroRModel;
import services.CsvReader;
import views.TopBarView;

public class TopBarViewController implements ActionListener{

	private TopBarView 			view;
	private AbstractViewModel 	model;
	
	public TopBarViewController(TopBarView view) {
		this.view = view;
		view.getOpenFileBtn().addActionListener(this);
		view.getColumnSelector().addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton 		  openFileBtn    = view.getOpenFileBtn();
		JComboBox<String> columnSelector = view.getColumnSelector();
		
		// Handle open button action.
		if (e.getSource() == openFileBtn) {
		    JFileChooser fileChooser = view.getFileChooser();
			int returnVal = fileChooser.showOpenDialog(view);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				CsvReader csvReader = new CsvReader(file.getPath());
				model = new ZeroRModel(csvReader.readCsv());
				ArrayList<String> values = new ArrayList<String>(model.getColumns().keySet());
				
				values.add(0, "Select a column");
				columnSelector.setModel(new DefaultComboBoxModel(values.toArray()));
				columnSelector.setVisible(true);
				
				/*
				Map<String, Map<String, Map<String, Integer>>> counts = MapMaker.makeCountMap(model.getData(), "wait");
				for (Entry<String, Map<String, Map<String, Integer>>> entry : counts.entrySet()) {
					logTextArea.append(entry.getKey() +  "\n");
					for (Entry<String, Map<String, Integer>> wait : entry.getValue().entrySet()) {
						logTextArea.append("\t" + wait + "\n");
					}
				}
				*/
			} /*else {
				logTextArea.append("Open command cancelled by user.\n");
			}
			
			logTextArea.setCaretPosition(logTextArea.getDocument().getLength());*/
		} else if(e.getSource() == columnSelector) {
			String value = (String) columnSelector.getSelectedItem();
			
			// If the user dont know we the target column, use the last column
			if(value.equals("Select a column")) {
				value = columnSelector.getItemAt(columnSelector.getItemCount()-1);
			}
			
			
			
			System.out.println(value);
		}
		
	}

	
}
