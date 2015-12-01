package views;

import models.AbstractViewModel;

public interface ViewInterface {
	
	public void createLayout();
	public void refresh();
	public void setModel(AbstractViewModel baseData);
}
