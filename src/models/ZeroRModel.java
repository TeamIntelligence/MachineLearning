package models;

import java.util.List;
import java.util.Map;

public class ZeroRModel extends AbstractViewModel {
	
	private AbstractViewModel baseData;
	
	public ZeroRModel(AbstractViewModel baseData) {
		super(baseData.getData(), baseData.getColumns());
		
		this.baseData = baseData;
	}
	
	public ZeroRModel(List<Map<String, String>> data, Map<String, List<String>> columns) {
		super(data, columns);
	}

	public AbstractViewModel getBaseData() {
		return baseData;
	}

	public void setBaseData(AbstractViewModel baseData) {
		this.baseData = baseData;
	}
}
