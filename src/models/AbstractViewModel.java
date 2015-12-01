package models;

import java.util.List;
import java.util.Map;

public class AbstractViewModel {

	private List<Map<String, String>> data;
	private Map<String, List<String>> columns;
	
	public AbstractViewModel(List<Map<String, String>> data, Map<String, List<String>> columns) {
		this.data = data;
		this.columns = columns;
	}

	public List<Map<String, String>> getData() {
		return data;
	}

	public Map<String, List<String>> getColumns() {
		return columns;
	}

	public void setData(List<Map<String, String>> data) {
		this.data = data;
	}

	public void setColumns(Map<String, List<String>> columns) {
		this.columns = columns;
	}
}
