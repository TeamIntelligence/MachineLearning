package controllers;

import models.ZeroRModel;
import views.ZeroRView;

public class ZeroRController {
	
	private ZeroRModel model;
	private ZeroRView view;
	
	public ZeroRController(ZeroRView view, ZeroRModel model) {
		this.view = view;
		this.model = model;
	}
}
