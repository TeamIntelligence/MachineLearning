package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class OneRModel extends AbstractViewModel {
	private AbstractViewModel 	 							baseData;
	private Map<String, Double>  							probs;
	private double 				 							highestProb;
	private List<String> 		 							highestProbs;
	private Map<String, Map<String, Map<String, Integer>>>  oneRMap;
	
	public OneRModel(List<Map<String, String>> data, Map<String, List<String>> columns) {
		super(data, columns);
	}
	
	public OneRModel(AbstractViewModel baseData) {
		super(baseData.getData(), baseData.getColumns());
		this.baseData = baseData;
	}
	
	
	public void createOneRMap() {
		
		Map<String, List<String>> columns = this.getColumns();
		String targetColumn = this.getTargetColumn();
		List<String> targetColumnValues = columns.get(targetColumn);
		List<Map<String, String>> data = this.getData();		
		
		//initialise the OneR Map with 0 values
		
		//Map<String, Map<> result
		//String == Predictor Name
		//Map<String, > == Predictor Value <String, Map<>
		//Predictor Value <String, Map<>
		//String == Value name (key)
		
		Map<String, Map<String, Map<String, Integer>>> result = new HashMap<String, Map<String, Map<String, Integer>>>();
		
		for(Entry<String, List<String>> predictor : columns.entrySet()){
			Map<String, Map<String, Integer>> cols = new HashMap<String, Map<String, Integer>>();
			
			for(String val : predictor.getValue()){	
				Map<String, Integer> predictorValues = new HashMap<String, Integer>();
				
				for(String targetValue : targetColumnValues){
					predictorValues.put(targetValue, 0);
				}
				
				predictorValues.put("total", 0);
				cols.put(val, predictorValues);
			}
			result.put(predictor.getKey(), cols);
		}
		
		//loop through rows
		for(Map<String, String> dataRow : data){
			String rowTargetValue = dataRow.get(targetColumn);
			
			// loop through columns
			for(Entry<String, String> dataRowEntry : dataRow.entrySet()){
				// dataRowEntry.getKey   == predictor (column) name
				// dataRowEntry.getValue == predictor value
				Map<String, Integer> row = result.get(dataRowEntry.getKey()).get(dataRowEntry.getValue());
				
				Integer count = row.get(rowTargetValue);
				count++;
				row.replace(rowTargetValue, count);
				row.replace("total", row.get("total")+1);
			}
			
		}
		this.setOneRMap(result);
	}
	
	public Map<String, Map<String, Map<String, Integer>>> getOneRMap() {
		return oneRMap;
	}

	public void setOneRMap(Map<String, Map<String, Map<String, Integer>>> oneRMap) {
		this.oneRMap = oneRMap;
	}

	public AbstractViewModel getBaseData() {
		return baseData;
	}
	
	public void setBaseData(AbstractViewModel baseData) {
		this.baseData = baseData;
		
		this.setColumns(baseData.getColumns());
		this.setData(baseData.getData());
		this.setTargetColumn(baseData.getTargetColumn());
		this.setUniqueValuesCount();
	}
	
}
