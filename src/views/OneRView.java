package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
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
	private final String 		total = "total";
	
	public OneRView(){
		super(new BorderLayout());
		
		this.createLayout();
		this.model      = new OneRModel(null, null);
	}

	@Override
	public void createLayout() {
		removeAll();
		
		JTabbedPane tabPanel = new JTabbedPane();
		
		JPanel firstContainer = buildTabOne();
		//add the container with the tables to the main layout embedded in a ScrollPane
		tabPanel.addTab("Confusion tables", new JScrollPane(firstContainer));

		JPanel secondContainer = buildTabTwo();
		//add the container with the tables to the main layout embedded in a ScrollPane
		tabPanel.addTab("Smallest error", new JScrollPane(secondContainer));
		add(tabPanel, BorderLayout.CENTER);
		
		repaint();
		revalidate();
	}

	private JPanel buildTabOne() {
		JPanel container = new JPanel(new GridLayout(0, 2));
		
		// If we have a model and counts we can print a table with the data
		if(this.model != null && this.model.getOneRMap() != null) {
			String targetColumn = this.model.getTargetColumn();
			Map<String, Map<String, Map<String, Integer>>> oneRMap = this.model.getOneRMap();	
			
			//loop through the predictors and create tables 
			for(Entry<String, Map<String, Map<String, Integer>>> predictor : oneRMap.entrySet()){
				if(predictor.getKey().equals(targetColumn)) {
					continue;
				}
				
				//initialize the columns
				ArrayList<String> columns = new ArrayList<String>(this.model.getColumns().get(targetColumn));
				columns.add(0, "Predictor name");
				columns.add(1, "Predictor value");
				columns.add(total);
				
				Object[] colNames = columns.toArray();
				Object[][] data = new Object[predictor.getValue().size()][columns.size() + 2];
				data[0][0] = predictor.getKey();
				
				// Fill the table contents
				int i = 0;
				for(Entry<String, Map<String, Integer>> valueSet : predictor.getValue().entrySet()){
					data[i][1] = valueSet.getKey();
					for(int j = 2; j < columns.size(); j++){
						Map<String, Integer> x = valueSet.getValue();
							//data[i][j + 2] = x.get(columns.get(j+2));
						data[i][j] = x.get(columns.get(j));
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
			    
			    // IF {currentColumnName} = {UniqueValue} THEN {TargetColumn} = {HighestCount}
			    // IF vooruitzicht = zonnig THEN Play= Nee
				JPanel gridCellContainer = new JPanel();
				gridCellContainer.setLayout(new BoxLayout(gridCellContainer, BoxLayout.PAGE_AXIS));
				
			    gridCellContainer.add(pane, BorderLayout.PAGE_START);
			    
			    for(Entry<String, Map<String, Integer>> valueSet : predictor.getValue().entrySet()){
				    JLabel conclusionLabel = new JLabel();
				    int highestValue = 0;
				    String highestNames = "";
				    
				    for(Entry<String, Integer> valueEntry : valueSet.getValue().entrySet()) {
				    	if(valueEntry.getKey().equals(total)) {
				    		continue;
				    	}
				    	
				    	if(valueEntry.getValue() >= highestValue) {
				    		highestValue = valueEntry.getValue();
				    	}
				    }
				    
				    for(Entry<String, Integer> valueEntry : valueSet.getValue().entrySet()) {
				    	if(valueEntry.getKey().equals(total)) {
				    		continue;
				    	}
				    	
				    	if(valueEntry.getValue() == highestValue) {
				    		if(highestNames != "") {
				    			highestNames += "of ";
				    		}
				    		
				    		highestNames += String.format("%s ", valueEntry.getKey());
				    	}
				    }
				    
			    	String text = String.format("IF %1$s = %2$s THEN %3$s = %4$s", predictor.getKey(), valueSet.getKey(), targetColumn, highestNames.trim());
			    	
				    conclusionLabel.setText(text);
				    gridCellContainer.add(conclusionLabel, BorderLayout.PAGE_END);
			    }
			    
			    //add the gridCellContainer to the container
			    container.add(gridCellContainer);
			}	
		} else {
	        //Create the log first, because the action listeners
	        //need to refer to it.
			JTextArea logTextArea = new JTextArea();
			logTextArea.setEditable(false);
			logTextArea.setText("Please select a column as target column!");
	        JScrollPane logScrollPane = new JScrollPane(logTextArea);
	        
	        //Add the buttons and the log to this panel.
	        container.add(logScrollPane);
		}
		
		return container;
	}
	
	private JPanel buildTabTwo() {
		JPanel container = new JPanel(new BorderLayout());

		if(this.model != null && this.model.getOneRMap() != null) {
			ArrayList<String> columns = new ArrayList<String>();
			columns.add("Predictor");
			columns.add("Rule");
			columns.add("Error");
			columns.add("Total error");

			int dataSize = this.model.getData().size();
			
			// If we have a model and counts we can print a table with the data
			String targetColumn = this.model.getTargetColumn();
			Map<String, Map<String, Map<String, Integer>>> oneRMap = this.model.getOneRMap();	
			
			Object[] colNames = columns.toArray();
			Object[][] data = new Object[this.model.getUniqueValuesCount() + (oneRMap.size()-2) - this.model.getUniqueValuesByColumn(targetColumn).size()][columns.size()];

			int i = 0;
			int lowestFault = this.model.getUniqueValuesCount();
			Map<String, Integer> lowestAttributes = new HashMap<String, Integer>();
			
			//loop through the predictors and create tables 
			for(Entry<String, Map<String, Map<String, Integer>>> predictor : oneRMap.entrySet()){
				if(predictor.getKey().equals(targetColumn)) {
					continue;
				}
				//initialize the columns
				data[i][0] = predictor.getKey();
				
				// Fill the table contents
				int leftCounter = 0;
				
				for(Entry<String, Map<String, Integer>> valueSet : predictor.getValue().entrySet()){
					String regel = valueSet.getKey();
					Entry<String, Integer> highestValue = null;
					
					for(Entry<String, Integer> valueEntry : valueSet.getValue().entrySet()) {
						if(valueEntry.getKey().equals(total)) {
							continue;
						}
						
						if(highestValue == null || valueEntry.getValue() > highestValue.getValue()) {
							highestValue = valueEntry;
				    	}
					}
					
					
					data[i][1] = String.format("%1$s -> %2$s", regel, highestValue.getKey());
					int lowestValue = this.model.getUniqueValuesCount();
					for(Entry<String, Integer> valueEntry : valueSet.getValue().entrySet()) {
						if(valueEntry.getKey().equals(total)) {
							continue;
						}
						
						if(valueEntry.getValue() < lowestValue) {
							lowestValue = valueEntry.getValue();
				    	}
					}
					data[i][2] = String.format("%1$d / %2$d", lowestValue, valueSet.getValue().get(total));
					leftCounter += lowestValue;
					i++;
				}
				data[i-1][3] = String.format("%1$d / %2$d", leftCounter, dataSize);
				i++;
				
				if(leftCounter <= lowestFault) {
					lowestFault = leftCounter;
					lowestAttributes.put(predictor.getKey(), leftCounter);
				}
			}	

			JLabel conclusionOneR = new JLabel();
			String text = "<html>Best predictor(s): <br>";
			for(Entry<String, Integer> lowestAttr : lowestAttributes.entrySet()) {
				if(lowestAttr.getValue() <= lowestFault){
					text += String.format("%1$s = (%2$d/%3$d) = %4$f<br>", lowestAttr.getKey(), lowestAttr.getValue(), dataSize, (float) lowestAttr.getValue()/dataSize);
				}
			}
			text = text.substring(0, text.length() - "<br>".length()) + "</html>";
			conclusionOneR.setText(text);

			//create table
			DefaultTableModel dataModel = new DataTableModel(data, colNames);
			JTable table = new JTable(dataModel);

			// Add the table to a scrollpane
			table.setGridColor(Color.BLACK);
		    JScrollPane pane = new JScrollPane(table);
		    
		    container.add(pane, BorderLayout.CENTER);
		    container.add(conclusionOneR, BorderLayout.PAGE_END);
		}
	    
		return container;
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
