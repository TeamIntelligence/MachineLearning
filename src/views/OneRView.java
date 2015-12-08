package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import models.AbstractViewModel;
import models.OneRModel;
import ux.ColumnGroup;
import ux.DataTableModel;
import ux.GroupableTableHeader;

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
		JPanel container = new JPanel(new GridLayout(0, 2));
		
		// If we have a model and counts we can print a table with the data
		if(this.model != null && this.model.getOneRMap() != null) {
			String targetColumn = this.model.getTargetColumn();
			Map<String, Map<String, Map<String, Integer>>> oneRMap = this.model.getOneRMap();		    
		    
			//loop through the predictors and create tables 
			for(Entry<String, Map<String, Map<String, Integer>>> predictor : oneRMap.entrySet()){
				//initialize the columns
				ArrayList<String> columns = new ArrayList<String>(this.model.getColumns().get(targetColumn));
				columns.add(0, "Predictor name");
				columns.add(1, "Predictor value");
				
				Object[] colNames = columns.toArray();
				Object[][] data = new Object[predictor.getValue().size()][columns.size() + 2];
				data[0][0] = predictor.getKey();
				
				// Fill the table contents
				int i = 0;
				for(Entry<String, Map<String, Integer>> valueSet : predictor.getValue().entrySet()){
					data[i][1] = valueSet.getKey();
					for(int j = 0; j < columns.size() -2; j++){
						Map<String, Integer> x = valueSet.getValue();
						data[i][j + 2] = x.get(columns.get(j+2));
					}
					i++;
				}
				
				//create table
				DefaultTableModel dataModel = new DataTableModel(data, colNames);
				JTable table = new JTable(dataModel);
				
				//create the grouped header
				TableColumnModel cm = table.getColumnModel();
			    ColumnGroup g_target = new ColumnGroup(targetColumn);
			    
			    for(int k = 2; k < columns.size(); k++){
			    	g_target.add(cm.getColumn(k));
			    }

			    GroupableTableHeader header = new GroupableTableHeader(table.getTableHeader());
			    header.addColumnGroup(g_target);
			    table.setTableHeader(header);

				// Add the table to a scrollpane
				table.setGridColor(Color.BLACK);
				RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(dataModel);
			    table.setRowSorter(sorter);
			    JScrollPane pane = new JScrollPane(table);
			    
			    //resize the scrollpane
			    Dimension d = table.getPreferredSize();
			    pane.setPreferredSize(new Dimension(d.width, d.getSize().height +100));
			    //add the scrollpane to the container
			    container.add(pane);
			}	
			//add the container with the tables to the main layout embedded in a ScrollPane
			add(new JScrollPane(container), BorderLayout.CENTER);
			
			// TODO FIX DE CONCLUSIE
			JTextArea logTextArea = new JTextArea();
			logTextArea.setEditable(false);
			logTextArea.setText("The highest probability is ");
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


	@Override
	public void refresh() {
		model.createOneRMap();
		createLayout();
	}

	@Override
	public void setModel(AbstractViewModel baseData) {
		model.setBaseData(baseData);
		refresh();
	}
}
