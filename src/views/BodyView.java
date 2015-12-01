package views;

import javax.swing.JTabbedPane;

public class BodyView extends JTabbedPane {

	private static final long serialVersionUID = 1L;
	
	public BodyView() {
		super();
    	
    	this.createGui();
    }

	public void createGui() {
		
		this.addTab("ZeroR", new ZeroRView());
		this.addTab("OneR", new ZeroRView());
		this.addTab("Statistical Modeling", new ZeroRView());
		this.addTab("Decision Tree", new ZeroRView());
		
	}
}
