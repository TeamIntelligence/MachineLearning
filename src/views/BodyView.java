package views;

import javax.swing.JTabbedPane;

import models.AbstractViewModel;

public class BodyView extends JTabbedPane implements ViewInterface  {

	private static final long serialVersionUID = 1L;
	
	private ZeroRView 				zeroRView;
	private OneRView  				oneRView;
	private StatisticalModelView  	statModel;
	
	public BodyView() {
		super();
    	
    	this.createLayout();
    }

	@Override
	public void createLayout() {
		removeAll();
		
		zeroRView = new ZeroRView();
		oneRView  = new OneRView();
		statModel = new StatisticalModelView();
		
		// Build the layouts
		this.addTab("ZeroR", zeroRView);
		this.addTab("OneR", oneRView);
		this.addTab("Statistical Modeling", statModel);
		this.addTab("Decision Tree", new ZeroRView());
		
		repaint();
		revalidate();
	}

	@Override
	public void refresh() {
		
	}

	@Override
	public void setModel(AbstractViewModel baseData) {
		this.zeroRView.setModel(baseData);
		this.oneRView.setModel(baseData);
	}
}
