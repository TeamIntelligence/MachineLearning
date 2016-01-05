package views;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controllers.StatisticalModelController;
import models.AbstractViewModel;
import models.OneRModel;
import models.StatisticalModelModel;

public class StatisticalModelView extends JPanel implements ViewInterface{
	private static final long 			serialVersionUID 	= 1L;
	public static final String 			noneSelected 		= "Select no value";			
	private final String 				total		 		= "total";
	private StatisticalModelModel 		model;
	private StatisticalModelController 	controller;
	private JPanel 						selectorContainer;
	private JPanel 						centerContainer;
	
	public StatisticalModelView(){
		super(new BorderLayout());
		
		this.createLayout();
		this.model      = new StatisticalModelModel(null, null);
		this.controller = new StatisticalModelController(null, this);
		this.createResultPanel();
	}

	@Override
	public void createLayout() {
		removeAll();
		
		if(this.model != null){
			
			JPanel topContainer = new JPanel(new BorderLayout());
			selectorContainer = new JPanel(new GridLayout(2, this.model.getColumns(null).size()));
			
			String targetColumn = this.model.getTargetColumn();
			
			for(Entry<String, List<String>> col : this.model.getColumns(null).entrySet()){
		        if (col.getKey().equals(targetColumn)){
		        	continue;
		        }
		        
				JLabel columnSelectorLabel = new JLabel(col.getKey());
		        
		        
		        
		        selectorContainer.add(columnSelectorLabel);
		        
			}
			for(Entry<String, List<String>> col : this.model.getColumns(null).entrySet()){
				if (col.getKey().equals(targetColumn)){
		        	continue;
		        }
				
				JComboBox<String> columnSelector = new JComboBox<String>();	
		        columnSelector.setActionCommand(col.getKey());
		        
		        List<String> vals = new ArrayList<String>();
		        vals.addAll(col.getValue());
		        
		        vals.add(0, noneSelected);
		        columnSelector.setModel(new DefaultComboBoxModel(vals.toArray()));
		        
				selectorContainer.add(columnSelector);
			}
			
			JButton submitButton = new JButton("Calculate");
			submitButton.setActionCommand("Calculate");
			submitButton.addActionListener(this.controller);
			
			topContainer.add(selectorContainer, BorderLayout.NORTH);
			topContainer.add(submitButton, BorderLayout.CENTER);
			
			add(topContainer, BorderLayout.NORTH);
		}
		
		repaint();
		revalidate();
	}
	
	public void createResultPanel() {
		if(centerContainer != null){
			remove(centerContainer);	
		}
		
		if(this.model.getLabelStrings() != null){
			Map<String, String> labelStrings = model.getLabelStrings();
			Map<String, Double> results = model.getResults();
			centerContainer = new JPanel(new GridLayout(labelStrings.size(), 1));
			
			for (Entry<String,String> labelString : labelStrings.entrySet()){
				centerContainer.add(new JLabel(labelString.getValue()));
			}
			
			add(centerContainer, BorderLayout.CENTER);
		}
		repaint();
		revalidate();
	}

	@Override
	public void refresh() {

		createLayout();
	}

	@Override
	public void setModel(AbstractViewModel baseData) {

	}
	
	public void setModel(OneRModel oneRData) {
		model.setBaseData(oneRData.getBaseData());
		this.model.setOneRModel(oneRData);
		this.controller.setModel(model);
		refresh();
	}
	
	public JPanel getSelectorContainer() {
		return selectorContainer;
	}

}
