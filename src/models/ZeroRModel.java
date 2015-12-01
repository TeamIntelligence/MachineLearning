package models;

import java.util.List;
import java.util.Map;

public class ZeroRModel extends AbstractViewModel {
	
	public ZeroRModel(AbstractViewModel model) {
		super(model.getData(), model.getColumns());
	}
	
	public ZeroRModel(List<Map<String, String>> data, List<String> columns) {
		super(data, columns);
	}
}
