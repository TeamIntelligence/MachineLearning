package models;

import java.util.List;
import java.util.Map;

public class AbstractViewModel {

	private List<Map<String, String>> data;
	private Map<String, List<String>> columns;
	private String targetColumn;
	
	public AbstractViewModel(List<Map<String, String>> data, Map<String, List<String>> columns) {
		this.data = data;
		this.columns = columns;
	}
	
	public AbstractViewModel(List<Map<String, String>> data, Map<String, List<String>> columns, String targetColumn) {
		this.data = data;
		this.columns = columns;
		this.targetColumn = targetColumn;
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

	public void setData(List<Map<String, String>> data) {
		this.data = data;
	}

	public void setColumns(Map<String, List<String>> columns) {
		this.columns = columns;
	}

	public void setTargetColumn(String targetColumn) {
		this.targetColumn = targetColumn;
	}
}
