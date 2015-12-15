package views;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import models.AbstractViewModel;
import models.StatisticalModelModel;

public class StatisticalModelView extends JPanel implements ViewInterface{
	private static final long 	serialVersionUID = 1L;
	private StatisticalModelModel model;
	private final String 		total = "total";
	
	public StatisticalModelView(){
		super(new BorderLayout());
		
		this.createLayout();
		this.model      = new StatisticalModelModel(null, null);
	}

	@Override
	public void createLayout() {
		removeAll();
		
		repaint();
		revalidate();
	}
	
	@Override
	public void refresh() {

		createLayout();
	}

	@Override
	public void setModel(AbstractViewModel baseData) {
		model.setBaseData(baseData);
		refresh();
	}
}
