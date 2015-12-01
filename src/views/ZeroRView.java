package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import models.AbstractViewModel;
import models.ZeroRModel;
import controllers.ZeroRController;

public class ZeroRView extends JPanel implements ViewInterface {

	private static final long 	serialVersionUID = 1L;
	private ZeroRController 	controller;
	private ZeroRModel 			model;
	private JFrame				rootWindow;
	
	// View items
	private JTextArea 	 logTextArea;
    private JPanel 		 columnPanel;
	
	public ZeroRView() {
		super(new BorderLayout());
		
		this.createLayout();
		this.model      = new ZeroRModel(null, null);
		this.controller = new ZeroRController(this, model); 
	}
	
	@Override
	public void createLayout() {
		removeAll();
		
		if(this.model != null && this.model.getCounts() != null) {
			String targetColumn = this.model.getTargetColumn();
			Map<String, Integer> counts = this.model.getCounts();
			Map<String, Double> probs 	= this.model.getProbs();
			
			DefaultTableModel dataModel = new DefaultTableModel();
			ArrayList<String> columns = (ArrayList<String>) this.model.getColumns().get(targetColumn);
			columns.add(0, "");
			Object[] colNames = columns.toArray();
			Object[][] data = new Object[2][counts.size() + 1];
			
			data[0][0] = "Count";
			data[1][0] = "Probability";
			
			int i = 1;
			for(Entry<String, Integer> count : counts.entrySet()) {
				data[0][i] = count.getValue();
				data[1][i] = probs.get(count.getKey());
				i++;
			}
			
			dataModel.setDataVector(data, colNames);
			
			JTable table = new JTable(dataModel);
			table.setGridColor(Color.BLACK);
			RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(dataModel);
		    table.setRowSorter(sorter);
			add(new JScrollPane(table), BorderLayout.CENTER);
		} else {
			
	        //Create the log first, because the action listeners
	        //need to refer to it.
			logTextArea = new JTextArea();
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

	public JTextArea getLogTextArea() {
		return logTextArea;
	}

	public JPanel getColumnPanel() {
		return columnPanel;
	}

	@Override
	public void refresh() {
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
