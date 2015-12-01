package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ZeroRModel extends AbstractViewModel {
	
	private AbstractViewModel baseData;
	private Map<String, Integer> counts;
	private Map<String, Double>  probs;
	
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
	
	public Map<String, Integer> getCounts() {
		return counts;
	}

	public Map<String, Double> getProbs() {
		return probs;
	}

	public void setCounts(Map<String, Integer> counts) {
		this.counts = counts;
	}

	public void setProbs(Map<String, Double> probs) {
		this.probs = probs;
	}

	public void setBaseData(AbstractViewModel baseData) {
		this.baseData = baseData;
		
		this.setColumns(baseData.getColumns());
		this.setData(baseData.getData());
		this.setTargetColumn(baseData.getTargetColumn());
	}
	
	public void createCountMap() {
		String targetColumn = this.getTargetColumn();
		List<String> uniqueValues = this.getColumns().get(targetColumn);
		List<Map<String, String>> data = this.getData();
		Map<String, Integer> result = new HashMap<String, Integer>();
		
		for(String value : uniqueValues) {
			result.put(value, 0);
		}
		
		for(Map<String, String> row : data) {
			String val = row.get(targetColumn);
			result.put(val, result.get(val) + 1);
		}
		
		this.counts = result;
	}
	
	public void createProbsMap() {
		if(this.counts == null) {
			createCountMap();
		}
		
		Map<String, Double> result = new HashMap<String, Double>();
		
		for(Entry<String, Integer> entry : counts.entrySet()) {
			double value = (double) entry.getValue();
			double size = (double) this.getData().size();
			result.put(entry.getKey(), value / size);
		}
		
		System.out.println(result);
		
		this.probs = result;
	}
}
