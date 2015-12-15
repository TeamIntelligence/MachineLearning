package controllers;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import models.StatisticalModelModel;
import views.StatisticalModelView;

public class StatisticalModelController implements ActionListener{
	
	
	private StatisticalModelModel model;
	private StatisticalModelView view;
	
	public StatisticalModelController(StatisticalModelModel model, StatisticalModelView view){
		this.view = view;
		this.model = model;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("Calculate")){
			
			JPanel selectorContainer = view.getSelectorContainer();
			Map<String, String> valMap = new HashMap<String, String>(); 
			
			for (Component comp : selectorContainer.getComponents()){
				if(comp.getClass().getName().endsWith("JComboBox")){
					JComboBox comboBox = (JComboBox) comp;
					if(comboBox.getSelectedItem().equals(StatisticalModelView.noneSelected)){
						continue;
					}
					
					valMap.put(comboBox.getActionCommand(), (String)comboBox.getSelectedItem());
				}
			}
			
			this.model.createStatisticalModel(valMap);			
			this.view.createResultPanel();
		}
	}

	public void setModel(StatisticalModelModel model) {
		this.model = model;
	}

	public void setView(StatisticalModelView view) {
		this.view = view;
	}

}
