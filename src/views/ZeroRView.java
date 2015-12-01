package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.RowSorter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import models.AbstractViewModel;
import models.ZeroRModel;
import ux.DataTableModel;
import controllers.ZeroRController;

public class ZeroRView extends JPanel implements ViewInterface {

	private static final long 	serialVersionUID = 1L;
	private ZeroRController 	controller;
	private ZeroRModel 			model;
	
	public ZeroRView() {
		super(new BorderLayout());
		
		this.createLayout();
		this.model      = new ZeroRModel(null, null);
		this.controller = new ZeroRController(this, model); 
	}
	
	@Override
	public void createLayout() {
		removeAll();
		
		// If we have a model and counts we can print a table with the data
		if(this.model != null && this.model.getCounts() != null) {
			String targetColumn = this.model.getTargetColumn();
			Map<String, Integer> counts = this.model.getCounts();
			Map<String, Double> probs 	= this.model.getProbs();
			
			// Get all the columns
			ArrayList<String> columns = new ArrayList<String>(this.model.getColumns().get(targetColumn));
			columns.add(0, "");
			Object[] colNames = columns.toArray();
			Object[][] data = new Object[2][counts.size() + 1];
			
			// Add cell at the beginning of the table
			data[0][0] = "Count";
			data[1][0] = "Probability";
			
			// Add the values to the data object for the table
			int i = 1;
			for(String colName : this.model.getColumns().get(targetColumn)) {
				data[0][i] = counts.get(colName);
				data[1][i] = probs.get(colName);
				i++;
			}

			// Create a dataModel for the table
			TableModel dataModel = new DataTableModel(data, colNames);
			
			// Print the table
			JTable table = new JTable(dataModel);
			table.setGridColor(Color.BLACK);
			RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(dataModel);
		    table.setRowSorter(sorter);
			add(new JScrollPane(table), BorderLayout.CENTER);
			

			// Create textbox showing the highest probabilities
			JTextArea logTextArea = new JTextArea();
			logTextArea.setEditable(false);
			List<String> highestProbs = model.getHighestProbs();
			// Multiple highest probabilities
			if(highestProbs.size() > 1) {
				// All are equal
				if(highestProbs.size() == counts.size()) {
					logTextArea.setText("All columns have an equal probability, no conclusion can be made");
				
				// Some are highest
				} else {
					String text = "";
					i = 0;
					for(String prob : highestProbs) {
						if(i==0) {
							text += prob;
						} else {
							text += ", " + prob;
						}
						i++;
					}
					
					logTextArea.setText("The highest probabilities are: " + text);
				}
			
			// Only one highest probability
			} else {
				logTextArea.setText("The highest probability is " + highestProbs.get(0));
			}
			
	        add(logTextArea, BorderLayout.PAGE_END);
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public ZeroRController getController() {
		return controller;
	}

	public ZeroRModel getModel() {
		return model;
	}

	@Override
	public void refresh() {
		// Recalculate the counts and the probabilities
		model.createCountMap();
		model.createProbsMap();
		createLayout();
	}

	@Override
	public void setModel(AbstractViewModel baseData) {
		model.setBaseData(baseData);
		refresh();
	}
}
