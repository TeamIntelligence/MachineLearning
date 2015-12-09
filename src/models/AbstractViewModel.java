package models;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class AbstractViewModel {

	private List<Map<String, String>> data;
	private Map<String, List<String>> columns;
	private Integer				 	uniqueValuesCount = 0;
	private String targetColumn;
	private String targetValue;


	public AbstractViewModel(List<Map<String, String>> data, Map<String, List<String>> columns) {
		this.data = data;
		this.columns = columns;
		this.setUniqueValuesCount();
	}
	
	public AbstractViewModel(List<Map<String, String>> data, Map<String, List<String>> columns, String targetColumn) {
		this.data = data;
		this.columns = columns;
		this.targetColumn = targetColumn;
		this.setUniqueValuesCount();
	}

	public List<Map<String, String>> getData() {
		return data;
	}

	public Map<String, List<String>> getColumns() {
		return columns;
	}

	public String getTargetColumn() {
		return targetColumn;
	}

	public Integer getUniqueValuesCount() {
		return uniqueValuesCount;
	}

	public void setData(List<Map<String, String>> data) {
		this.data = data;
	}

	public void setColumns(Map<String, List<String>> columns) {
		this.columns = columns;
	}

	public void setTargetColumn(String targetColumn) {
		this.targetColumn = targetColumn;
	}
	
	public String getTargetValue() {
		return targetValue;
	}

	public void setTargetValue(String targetValue) {
		this.targetValue = targetValue;
	}
	
	public void setUniqueValuesCount() {
		if(columns != null) {
			uniqueValuesCount = 0;
			for(Entry<String, List<String>> uniqueVals : columns.entrySet()) 
				uniqueValuesCount += uniqueVals.getValue().size();
		}
	}
	
	public List<String> getUniqueValuesByColumn(String col) {
		return this.columns.get(col);
	}
}
