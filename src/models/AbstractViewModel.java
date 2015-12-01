package models;

import java.util.List;
import java.util.Map;

public class AbstractViewModel {

	private List<Map<String, String>> data;
	private List<String> columns;
	
	public AbstractViewModel(List<Map<String, String>> data, List<String> columns) {
		this.data = data;
		this.columns = columns;
	}

	public List<Map<String, String>> getData() {
		return data;
	}

	public List<String> getColumns() {
		return columns;
	}

	public void setData(List<Map<String, String>> data) {
		this.data = data;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}
}
