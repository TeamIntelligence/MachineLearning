package models;

import java.util.List;
import java.util.Map;

public class StatisticalModelModel extends AbstractViewModel {
	
	private AbstractViewModel 	 baseData;
	
	public StatisticalModelModel(AbstractViewModel baseData) {
		super(baseData.getData(), baseData.getColumns());
		
		this.baseData = baseData;
	}
	
	public StatisticalModelModel(List<Map<String, String>> data, Map<String, List<String>> columns) {
		super(data, columns);
	}
	


	public void setBaseData(AbstractViewModel baseData) {
		this.baseData = baseData;
		
		//this.setColumns(baseData.getColumns());
		//this.setData(baseData.getData());
		//this.setTargetColumn(baseData.getTargetColumn());
	}
}
