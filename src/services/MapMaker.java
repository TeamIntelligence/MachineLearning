package services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MapMaker {
	
	/**
	 *	Does the count calculation 
	 * @return 
	 */
	public static Map<String, Integer> createTargetColCountMap(List<Map<String, String>> data, 
		Map<String, List<String>> columns, String targetColumn) {
		List<String> uniqueValues = columns.get(targetColumn);
		Map<String, Integer> result = new HashMap<String, Integer>();
		
		// Create 0 counts for all the unique values of a column
		for(String value : uniqueValues) {
			result.put(value, 0);
		}
		
		// Make a count for each unique value
		for(Map<String, String> row : data) {
			String val = row.get(targetColumn);
			result.put(val, result.get(val) + 1);
		}
		
		return result;
	}
	
	public static Map<String, Map<String, Map<String, Integer>>> createTotalCountMap(List<Map<String, String>> data, 
			Map<String, List<String>> columns, String targetColumn) {
		List<String> targetColumnValues = columns.get(targetColumn);
		
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
		return result;
	}
}
